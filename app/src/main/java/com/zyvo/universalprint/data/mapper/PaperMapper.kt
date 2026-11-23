package com.zyvo.universalprint.data.mapper

import com.zyvo.universalprint.data.db.entity.PaperSizeEntity
import com.zyvo.universalprint.domain.model.PaperCategory
import com.zyvo.universalprint.domain.model.PaperDimensions
import com.zyvo.universalprint.domain.model.PaperOrientation
import com.zyvo.universalprint.domain.model.PaperSize

fun PaperSizeEntity.toDomain(): PaperSize = PaperSize(
    id = id,
    name = name,
    dimensions = PaperDimensions(widthMm, heightMm),
    category = runCatching { PaperCategory.valueOf(category) }.getOrDefault(PaperCategory.CUSTOM),
    standardCode = standardCode,
    region = region,
    sourceName = sourceName,
    sourceUrl = sourceUrl,
    revision = revision,
    isStandard = isStandard,
    isCustom = isCustom,
    isFavorite = isFavorite,
    isRollMedia = isRollMedia,
    defaultOrientation = runCatching { PaperOrientation.valueOf(defaultOrientation) }.getOrDefault(PaperOrientation.EITHER),
    version = version
)

fun PaperSize.toEntity(): PaperSizeEntity = PaperSizeEntity(
    id = id,
    name = name,
    widthMm = dimensions.widthMm,
    heightMm = dimensions.heightMm,
    category = category.name,
    standardCode = standardCode,
    region = region,
    sourceName = sourceName,
    sourceUrl = sourceUrl,
    revision = revision,
    isStandard = isStandard,
    isCustom = isCustom,
    isFavorite = isFavorite,
    isRollMedia = isRollMedia,
    defaultOrientation = defaultOrientation.name,
    version = version
)
