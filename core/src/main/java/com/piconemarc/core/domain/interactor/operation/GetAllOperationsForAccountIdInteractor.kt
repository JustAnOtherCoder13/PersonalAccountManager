package com.piconemarc.core.domain.interactor.operation

import com.piconemarc.core.data.operation.OperationRepository
import com.piconemarc.model.entity.OperationUiModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class GetAllOperationsForAccountIdInteractor @Inject constructor(private val operationRepository: OperationRepository) {

    suspend fun getAllOperationsForAccountIdFlow(accountId: Long, scope: CoroutineScope): StateFlow<List<OperationUiModel>> =
        operationRepository.getOperationsForAccountIdFlow(accountId).map {
                allOperationsDTO -> allOperationsDTO.map { it.toUiModel() }
        }.stateIn(scope)
}