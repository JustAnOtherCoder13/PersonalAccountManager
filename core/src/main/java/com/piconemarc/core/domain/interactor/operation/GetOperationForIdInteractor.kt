package com.piconemarc.core.domain.interactor.operation

import com.piconemarc.core.data.operation.OperationRepository
import com.piconemarc.core.domain.entityDTO.OperationDTO
import com.piconemarc.model.entity.OperationUiModel
import javax.inject.Inject

class GetOperationForIdInteractor @Inject constructor(private val operationRepository: OperationRepository) {

    suspend fun getOperationForId(operationId: Long, accountId : Long): OperationUiModel {
        return mapOperationDtoToOperationModel(operationRepository.getOperationForAccountIdWithOperationId(operationId, accountId))
    }

    private fun mapOperationDtoToOperationModel(operationDTO: OperationDTO): OperationUiModel {
        return operationDTO.toOperationUiModel()
    }
}