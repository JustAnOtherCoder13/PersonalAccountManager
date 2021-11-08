package com.piconemarc.viewmodel.viewModel

import android.util.Log
import androidx.compose.runtime.MutableState
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
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MyAccountViewModel @Inject constructor(
    store: DefaultStore<GlobalVmState>,
    private val getAllAccountsInteractor: GetAllAccountsInteractor,
) : BaseViewModel<AppActions.MyAccountScreenAction, ViewModelInnerStates.MyAccountScreenVMState>(store,myAccountScreenVMState_) {

    override fun dispatchAction(action: AppActions.MyAccountScreenAction) {
        //updateState(GlobalAction.UpdateMyAccountScreenState(action))
        Log.e("TAG", "dispatchAction: $action")
        viewModelScope.launch(block = { state.collect {
            uiState.value = it } })
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
                            dispatchAction(
                                    AppActions.MyAccountScreenAction.UpdateAccountList(it)
                                )
                        }
                    }
                )
            }
            else -> { updateState(GlobalAction.UpdateMyAccountScreenState(action))}
        }
    }
}