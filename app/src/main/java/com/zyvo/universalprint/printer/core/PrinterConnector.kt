package com.zyvo.universalprint.printer.core

import com.zyvo.universalprint.domain.model.Printer
import com.zyvo.universalprint.domain.model.PrintJob
import kotlinx.coroutines.flow.Flow

/**
 * Common contract for any printer transport (USB, Bluetooth, Network, System).
 * Implementations must NOT block the main thread.
 */
interface PrinterConnector {
    val type: com.zyvo.universalprint.domain.model.PrinterType
    suspend fun discover(): List<Printer>
    suspend fun connect(printer: Printer): Result<Printer>
    suspend fun disconnect(printer: Printer): Result<Unit>
    suspend fun send(job: PrintJob, payload: ByteArray): Result<Unit>
    fun connectionState(printer: Printer): Flow<com.zyvo.universalprint.domain.model.ConnectionStatus>
}
