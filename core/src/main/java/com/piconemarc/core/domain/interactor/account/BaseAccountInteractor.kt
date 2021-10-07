package com.piconemarc.core.domain.interactor.account

import com.piconemarc.core.data.account.AccountRepository
import javax.inject.Inject

open class BaseAccountInteractor @Inject constructor(private val accountRepository: AccountRepository)