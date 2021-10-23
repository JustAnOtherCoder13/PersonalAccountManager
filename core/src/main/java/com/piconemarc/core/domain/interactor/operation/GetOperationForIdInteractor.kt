package com.piconemarc.core.domain.interactor.operation

import com.piconemarc.core.data.operation.OperationRepository
import com.piconemarc.core.domain.entityDTO.OperationDTO
import com.piconemarc.model.entity.EndDate
import com.piconemarc.model.entity.OperationModel
import java.util.*
import javax.inject.Inject

class GetOperationForIdInteractor @Inject constructor(private val operationRepository: OperationRepository) {

    suspend fun getOperationForId(operationId: Long, accountId : Long): OperationModel {
        return mapOperationDtoToOperationModel(operationRepository.getOperationForAccountIdWithOperationId(operationId, accountId))
    }

    private fun mapOperationDtoToOperationModel(operationDTO: OperationDTO): OperationModel {
        return OperationModel(
            id = operationDTO.id,
            name = operationDTO.name,
            amount = operationDTO.amount,
            endDate = EndDate(operationDTO.endDateMonth, operationDTO.endDateYear),
            isRecurrent = operationDTO.isRecurrent,
            accountId = operationDTO.accountId,
            categoryId = operationDTO.categoryId,
            emitDate = operationDTO.emitDate ?: Date(),
            senderAccountId = operationDTO.senderAccountId,
            beneficiaryAccountId = operationDTO.beneficiaryAccountId,
        )
    }
}