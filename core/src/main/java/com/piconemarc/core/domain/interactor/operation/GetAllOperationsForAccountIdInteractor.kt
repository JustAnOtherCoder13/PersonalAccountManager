package com.piconemarc.core.domain.interactor.operation

import com.piconemarc.core.data.operation.OperationRepository
import com.piconemarc.core.domain.entityDTO.OperationDTO
import com.piconemarc.model.entity.OperationModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*
import javax.inject.Inject

class GetAllOperationsForAccountIdInteractor @Inject constructor(private val operationRepository: OperationRepository) {

    fun getAllOperationsForAccountId(
        accountId: Long,
    ): Flow<List<OperationModel>> =
        operationRepository.getOperationsForAccountId(accountId).map {
            mapAllOperationsDtoForAccountIdToOperationModel(it)
        }

    private fun mapAllOperationsDtoForAccountIdToOperationModel(
        allOperationsDtoForAccountId: List<OperationDTO>
    ): List<OperationModel> {
        return allOperationsDtoForAccountId.map {
            OperationModel(
                id = it.id,
                name = it.name,
                amount_ = it.amount,
                categoryId = it.categoryId,
                emitDate = it.emitDate ?: Date(),
                accountId = it.accountId
            )
        }
    }
}