package com.piconemarc.core.domain.interactor.operation

import com.piconemarc.core.data.operation.OperationRepository
import com.piconemarc.core.domain.entityDTO.OperationDTO
import com.piconemarc.model.entity.OperationUiModel
import javax.inject.Inject

class AddNewOperationInteractor @Inject constructor(private val operationRepository: OperationRepository) {

    suspend fun addNewOperation(operationModel: OperationUiModel) : Long {
        return operationRepository.addNewOperation(
            OperationDTO().fromUiModel(operationModel)
        )
    }

}