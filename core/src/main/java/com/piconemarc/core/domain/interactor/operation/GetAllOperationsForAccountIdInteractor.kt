package com.piconemarc.core.domain.interactor.operation

import com.piconemarc.core.data.operation.OperationRepository
import com.piconemarc.core.domain.entityDTO.OperationDTO
import com.piconemarc.model.entity.OperationUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllOperationsForAccountIdInteractor @Inject constructor(private val operationRepository: OperationRepository) {

    fun getAllOperationsForAccountId(
        accountId: Long,
    ): Flow<List<OperationUiModel>> =
        operationRepository.getOperationsForAccountId(accountId).map {
            mapAllOperationsDtoForAccountIdToOperationModel(it)
        }

    private fun mapAllOperationsDtoForAccountIdToOperationModel(
        allOperationsDtoForAccountId: List<OperationDTO>
    ): List<OperationUiModel> {
        return allOperationsDtoForAccountId.map {
            it.toOperationUiModel()
        }
    }
}