package com.zyvo.universalprint.printer.bluetooth

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Context
import com.zyvo.universalprint.domain.model.ConnectionStatus
import com.zyvo.universalprint.domain.model.PrintJob
import com.zyvo.universalprint.domain.model.Printer
import com.zyvo.universalprint.domain.model.PrinterType
import com.zyvo.universalprint.printer.core.PrinterConnector
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BluetoothPrinterConnector @Inject constructor(
    @ApplicationContext private val context: Context
) : PrinterConnector {
    override val type = PrinterType.BLUETOOTH

    private val adapter: BluetoothAdapter? =
        (context.getSystemService(Context.BLUETOOTH_SERVICE) as? BluetoothManager)?.adapter

    private val states = mutableMapOf<String, MutableStateFlow<ConnectionStatus>>()

    override suspend fun discover(): List<Printer> {
        val bt = adapter ?: return emptyList()
        return try {
            bt.bondedDevices.map { dev ->
                Printer(
                    id = "bt_${dev.address}",
                    name = dev.name ?: dev.address,
                    type = PrinterType.BLUETOOTH,
                    address = dev.address
                )
            }
        } catch (se: SecurityException) {
            emptyList()
        }
    }

    override suspend fun connect(printer: Printer): Result<Printer> {
        // Connection state tracked; actual RFCOMM socket opened on send().
        setState(printer.id, ConnectionStatus.CONNECTED)
        return Result.success(printer.copy(status = ConnectionStatus.CONNECTED))
    }

    override suspend fun disconnect(printer: Printer): Result<Unit> {
        setState(printer.id, ConnectionStatus.DISCONNECTED)
        return Result.success(Unit)
    }

    override suspend fun send(job: PrintJob, payload: ByteArray): Result<Unit> {
        val bt = adapter ?: return Result.failure(IllegalStateException("Bluetooth unavailable"))
        val address = job.printerId?.removePrefix("bt_") ?: ""
        val device: BluetoothDevice = try {
            bt.getRemoteDevice(address)
        } catch (e: IllegalArgumentException) {
            return Result.failure(e)
        }
        return try {
            val uuid = java.util.UUID.fromString("00001101-0000-1000-8000-00805F9B34FB") // SPP
            val socket = device.createRfcommSocketToServiceRecord(uuid)
            socket.connect()
            socket.outputStream.write(payload)
            socket.outputStream.flush()
            socket.close()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun connectionState(printer: Printer): Flow<ConnectionStatus> =
        states.getOrPut(printer.id) { MutableStateFlow(printer.status) }

    private fun setState(id: String, s: ConnectionStatus) {
        states.getOrPut(id) { MutableStateFlow(s) }.value = s
    }
}
