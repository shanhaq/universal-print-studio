package com.zyvo.universalprint.data.mapper

import com.zyvo.universalprint.data.db.entity.PaperSizeEntity
import com.zyvo.universalprint.domain.model.PaperCategory
import com.zyvo.universalprint.domain.model.PaperDimensions
import com.zyvo.universalprint.domain.model.PaperSize

fun PaperSizeEntity.toDomain(): PaperSize = PaperSize(
    id = id,
    name = name,
    dimensions = PaperDimensions(widthMm, heightMm),
    category = PaperCategory.valueOf(category),
    version = version,
    isCustom = isCustom
)

fun PaperSize.toEntity(): PaperSizeEntity = PaperSizeEntity(
    id = id,
    name = name,
    widthMm = dimensions.widthMm,
    heightMm = dimensions.heightMm,
    category = category.name,
    version = version,
    isCustom = isCustom
)
