package com.zyvo.universalprint.domain.model

enum class PrinterType { USB, BLUETOOTH, NETWORK, SYSTEM_PRINT, PDF_EXPORT }

enum class ConnectionStatus { DISCONNECTED, CONNECTING, CONNECTED, ERROR }

data class Printer(
    val id: String,
    val name: String,
    val type: PrinterType,
    val address: String? = null, // USB device path / BT MAC / IP:port
    val status: ConnectionStatus = ConnectionStatus.DISCONNECTED,
    val capabilities: PrinterCapabilities = PrinterCapabilities()
)

data class PrinterCapabilities(
    val supportsEscPos: Boolean = false,
    val supportsPdf: Boolean = false,
    val maxPaperWidthMm: Double? = null,
    val maxPaperHeightMm: Double? = null,
    val printableAreaMm: PaperDimensions? = null, // printable (not paper) area
    val dpi: Int? = null,
    val color: Boolean = true
)

data class PrintJob(
    val id: String,
    val documentId: String,
    val printerId: String?,
    val paperSize: PaperSize,
    val copies: Int = 1,
    val createdAt: Long = System.currentTimeMillis(),
    val status: PrintJobStatus = PrintJobStatus.PENDING
)

enum class PrintJobStatus { PENDING, SENDING, COMPLETED, FAILED, CANCELLED }
