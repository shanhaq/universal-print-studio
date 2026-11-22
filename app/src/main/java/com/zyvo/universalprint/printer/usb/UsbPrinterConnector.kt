package com.zyvo.universalprint.printer.usb

import android.content.Context
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbManager
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
class UsbPrinterConnector @Inject constructor(
    @ApplicationContext private val context: Context
) : PrinterConnector {
    override val type = PrinterType.USB

    private val usbManager: UsbManager =
        context.getSystemService(Context.USB_SERVICE) as UsbManager

    private val states = mutableMapOf<String, MutableStateFlow<ConnectionStatus>>()

    override suspend fun discover(): List<Printer> {
        return usbManager.deviceList.values.map { dev ->
            Printer(
                id = "usb_${dev.deviceId}",
                name = dev.productName ?: "USB Device ${dev.deviceId}",
                type = PrinterType.USB,
                address = dev.deviceName
            )
        }
    }

    override suspend fun connect(printer: Printer): Result<Printer> {
        val dev = usbManager.deviceList.values.firstOrNull { "usb_${it.deviceId}" == printer.id }
            ?: return Result.failure(IllegalStateException("USB device not found"))
        if (!usbManager.hasPermission(dev)) {
            return Result.failure(SecurityException("USB permission not granted"))
        }
        setState(printer.id, ConnectionStatus.CONNECTED)
        return Result.success(printer.copy(status = ConnectionStatus.CONNECTED))
    }

    override suspend fun disconnect(printer: Printer): Result<Unit> {
        setState(printer.id, ConnectionStatus.DISCONNECTED)
        return Result.success(Unit)
    }

    override suspend fun send(job: PrintJob, payload: ByteArray): Result<Unit> {
        // Real bulk transfer requires claiming interface + endpoint discovery.
        // Implemented as a best-effort bulk OUT transfer to the first OUT endpoint.
        val dev = usbManager.deviceList.values.firstOrNull { "usb_${it.deviceId}" == job.printerId }
            ?: return Result.failure(IllegalStateException("USB device not found"))
        val intf = dev.getInterface(0)
        val conn = usbManager.openDevice(dev) ?: return Result.failure(IllegalStateException("Cannot open USB device"))
        return try {
            if (!conn.claimInterface(intf, true)) {
                return Result.failure(IllegalStateException("Cannot claim USB interface"))
            }
            val ep = (0 until intf.endpointCount).map { intf.getEndpoint(it) }
                .firstOrNull { it.direction == android.hardware.usb.UsbConstants.USB_DIR_OUT }
                ?: return Result.failure(IllegalStateException("No OUT endpoint"))
            val sent = conn.bulkTransfer(ep, payload, payload.size, 5000)
            if (sent < 0) Result.failure(IllegalStateException("Bulk transfer failed"))
            else Result.success(Unit)
        } finally {
            conn.releaseInterface(intf)
            conn.close()
        }
    }

    override fun connectionState(printer: Printer): Flow<ConnectionStatus> =
        states.getOrPut(printer.id) { MutableStateFlow(printer.status) }

    private fun setState(id: String, s: ConnectionStatus) {
        states.getOrPut(id) { MutableStateFlow(s) }.value = s
    }
}
