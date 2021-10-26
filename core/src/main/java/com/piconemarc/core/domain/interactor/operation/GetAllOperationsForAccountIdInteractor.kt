package com.piconemarc.core.domain.interactor.operation

import com.piconemarc.core.data.operation.OperationRepository
import com.piconemarc.model.entity.OperationUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllOperationsForAccountIdInteractor @Inject constructor(private val operationRepository: OperationRepository) {

    fun getAllOperationsForAccountId(
        accountId: Long,
    ): Flow<List<OperationUiModel>> =
        operationRepository.getOperationsForAccountId(accountId).map {
                allOperationsDTO -> allOperationsDTO.map { it.toUiModel() }
        }
}