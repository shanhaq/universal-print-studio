package com.zyvo.universalprint.data.mapper

import com.zyvo.universalprint.data.db.entity.PrinterEntity
import com.zyvo.universalprint.domain.model.ConnectionStatus
import com.zyvo.universalprint.domain.model.Printer
import com.zyvo.universalprint.domain.model.PrinterType

fun PrinterEntity.toDomain(): Printer = Printer(
    id = id,
    name = name,
    type = PrinterType.valueOf(type),
    address = address,
    status = ConnectionStatus.DISCONNECTED
)

fun Printer.toEntity(): PrinterEntity = PrinterEntity(
    id = id,
    name = name,
    type = type.name,
    address = address,
    capabilitiesJson = null
)
