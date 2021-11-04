package com.piconemarc.core.domain.entityDTO

import com.piconemarc.model.entity.BaseUiModel

interface BaseDTO

interface DTO<M:BaseUiModel, D : BaseDTO> : BaseDTO {
    val id : Long
    val name: String
    fun fromUiModel(model : M) : D
    fun toUiModel() : M
}