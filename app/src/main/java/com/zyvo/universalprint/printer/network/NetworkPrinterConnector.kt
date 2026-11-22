package com.zyvo.universalprint.printer.network

import com.zyvo.universalprint.domain.model.ConnectionStatus
import com.zyvo.universalprint.domain.model.PrintJob
import com.zyvo.universalprint.domain.model.Printer
import com.zyvo.universalprint.domain.model.PrinterType
import com.zyvo.universalprint.printer.core.PrinterConnector
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext
import java.net.InetSocketAddress
import java.net.Socket
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkPrinterConnector @Inject constructor() : PrinterConnector {
    override val type = PrinterType.NETWORK

    private val states = mutableMapOf<String, MutableStateFlow<ConnectionStatus>>()
    private val addressCache = mutableMapOf<String, String>()

    override suspend fun discover(): List<Printer> = emptyList() // mDNS discovery optional; manual IP entry used

    fun registerPrinter(id: String, address: String) {
        addressCache[id] = address
    }

    override suspend fun connect(printer: Printer): Result<Printer> {
        val addr = printer.address ?: return Result.failure(IllegalArgumentException("No address"))
        addressCache[printer.id] = addr
        val (host, port) = parseAddress(addr)
        return try {
            withContext(Dispatchers.IO) {
                Socket().use { s ->
                    s.connect(InetSocketAddress(host, port), 3000)
                }
            }
            setState(printer.id, ConnectionStatus.CONNECTED)
            Result.success(printer.copy(status = ConnectionStatus.CONNECTED))
        } catch (e: Exception) {
            setState(printer.id, ConnectionStatus.ERROR)
            Result.failure(e)
        }
    }

    override suspend fun disconnect(printer: Printer): Result<Unit> {
        setState(printer.id, ConnectionStatus.DISCONNECTED)
        return Result.success(Unit)
    }

    override suspend fun send(job: PrintJob, payload: ByteArray): Result<Unit> {
        val addr = addressCache[job.printerId]
            ?: return Result.failure(IllegalArgumentException("Printer address unknown; connect first"))
        val (host, port) = parseAddress(addr)
        return try {
            withContext(Dispatchers.IO) {
                Socket().use { s ->
                    s.connect(InetSocketAddress(host, port), 5000)
                    s.getOutputStream().write(payload)
                    s.getOutputStream().flush()
                }
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun connectionState(printer: Printer): Flow<ConnectionStatus> =
        states.getOrPut(printer.id) { MutableStateFlow(printer.status) }

    private fun parseAddress(addr: String): Pair<String, Int> {
        val parts = addr.split(":")
        val host = parts.getOrElse(0) { addr }
        val port = parts.getOrElse(1) { "9100" }.toIntOrNull() ?: 9100
        return host to port
    }

    private fun setState(id: String, s: ConnectionStatus) {
        states.getOrPut(id) { MutableStateFlow(s) }.value = s
    }
}
