package com.piconemarc.core.domain.entityDTO

import com.piconemarc.model.entity.BaseUiModel

interface DTO<M:BaseUiModel> {

    val id : Long
    val name: String
    fun<D : DTO<M>> fromUiModel(model : M) : D
    fun toUiModel() : M
}