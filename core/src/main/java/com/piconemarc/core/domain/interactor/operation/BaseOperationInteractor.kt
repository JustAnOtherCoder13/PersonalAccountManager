package com.piconemarc.core.domain.interactor.operation

import com.piconemarc.core.data.operation.OperationRepository
import javax.inject.Inject

open class BaseOperationInteractor @Inject constructor(operationRepository: OperationRepository)