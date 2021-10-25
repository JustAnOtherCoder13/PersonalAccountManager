package com.piconemarc.core.domain.interactor.operation

import com.piconemarc.core.data.operation.OperationRepository
import com.piconemarc.core.domain.entityDTO.OperationDTO
import com.piconemarc.model.entity.OperationUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllOperationsInteractor @Inject constructor(private val operationRepository: OperationRepository) {

    fun getAllOperations(): Flow<List<OperationUiModel>> =
        operationRepository.getAllOperations().map {
            mapAllOperationsDtoToOperationsModel(it)
        }

    private fun mapAllOperationsDtoToOperationsModel(
        operationsDtoList: List<OperationDTO>
    ): List<OperationUiModel> {
        return operationsDtoList.map {
            it.toOperationUiModel()
              }
    }
}
