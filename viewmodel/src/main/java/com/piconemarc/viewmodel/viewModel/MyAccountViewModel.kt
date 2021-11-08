package com.piconemarc.viewmodel.viewModel

import androidx.compose.runtime.getValue
import androidx.lifecycle.viewModelScope
import com.piconemarc.core.domain.interactor.account.GetAllAccountsInteractor
import com.piconemarc.viewmodel.DefaultStore
import com.piconemarc.viewmodel.UiAction
import com.piconemarc.viewmodel.launchOnIOCatchingError
import com.piconemarc.viewmodel.viewModel.reducer.GlobalAction
import com.piconemarc.viewmodel.viewModel.reducer.GlobalVmState
import com.piconemarc.viewmodel.viewModel.reducer.myAccountScreenVMState_
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import javax.inject.Inject


@HiltViewModel
class MyAccountViewModel @Inject constructor(
    store: DefaultStore<GlobalVmState>,
    private val getAllAccountsInteractor: GetAllAccountsInteractor
) : BaseViewModel(store) {

    override val uiState by myAccountScreenVMState_

    override fun dispatchAction(action: UiAction) {
        updateState(GlobalAction.UpdateMyAccountScreenState(action))
        when (action) {
            is AppActions.MyAccountScreenAction.InitScreen -> {
                updateState(
                    GlobalAction.UpdateBaseAppScreenVmState(
                        AppActions.BaseAppScreenAction.UpdateInterlayerTiTle(
                            com.piconemarc.model.R.string.myAccountsInterLayerTitle
                        )
                    )
                )
                viewModelScope.launchOnIOCatchingError(
                    block = {
                        getAllAccountsInteractor.getAllAccountsAsFlow(viewModelScope).collect {
                            updateState(
                                GlobalAction.UpdateMyAccountScreenState(
                                    AppActions.MyAccountScreenAction.UpdateAccountList(it)
                                )
                            )
                        }
                    }
                )
            }
        }
    }
}