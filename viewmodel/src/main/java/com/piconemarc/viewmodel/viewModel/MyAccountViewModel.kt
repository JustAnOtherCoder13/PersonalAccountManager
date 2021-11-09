package com.piconemarc.viewmodel.viewModel

import androidx.lifecycle.viewModelScope
import com.piconemarc.core.domain.interactor.account.GetAllAccountsInteractor
import com.piconemarc.viewmodel.viewModel.utils.DefaultStore
import com.piconemarc.viewmodel.viewModel.utils.launchOnIOCatchingError
import com.piconemarc.viewmodel.viewModel.reducer.GlobalAction
import com.piconemarc.viewmodel.viewModel.reducer.GlobalVmState
import com.piconemarc.viewmodel.viewModel.reducer.myAccountScreenVMState_
import com.piconemarc.viewmodel.viewModel.utils.AppActions
import com.piconemarc.viewmodel.viewModel.utils.BaseViewModel
import com.piconemarc.viewmodel.viewModel.utils.ViewModelInnerStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyAccountViewModel @Inject constructor(
    store: DefaultStore<GlobalVmState>,
    private val getAllAccountsInteractor: GetAllAccountsInteractor,
) : BaseViewModel<AppActions.MyAccountScreenAction, ViewModelInnerStates.MyAccountScreenVMState>(store,myAccountScreenVMState_) {

    init {
        //init state
        viewModelScope.launch(block = { state.collect {
            uiState.value = it } })
        //update interlayer title
        updateState(
            GlobalAction.UpdateBaseAppScreenVmState(
                AppActions.BaseAppScreenAction.UpdateInterlayerTiTle(
                    com.piconemarc.model.R.string.myAccountsInterLayerTitle
                )
            )
        )
        //updateAccountList
        viewModelScope.launchOnIOCatchingError(
            block = {
                getAllAccountsInteractor.getAllAccountsAsFlow(this).collect {
                    dispatchAction(
                        AppActions.MyAccountScreenAction.UpdateAccountList(it)
                    )
                }
            }
        )
    }

    override fun dispatchAction(action: AppActions.MyAccountScreenAction) {
             updateState(GlobalAction.UpdateMyAccountScreenState(action))
    }
}