package com.piconemarc.viewmodel.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewModelScope
import com.piconemarc.core.domain.interactor.account.GetAllAccountsInteractor
import com.piconemarc.viewmodel.viewModel.reducer.GlobalAction
import com.piconemarc.viewmodel.viewModel.reducer.GlobalVmState
import com.piconemarc.viewmodel.viewModel.reducer.myAccountScreenVMState_
import com.piconemarc.viewmodel.viewModel.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyAccountViewModel @Inject constructor(
    store: DefaultStore<GlobalVmState>,
    private val getAllAccountsInteractor: GetAllAccountsInteractor,
) : BaseViewModel<AppActions.MyAccountScreenAction, ViewModelInnerStates.MyAccountScreenVMState>(store,myAccountScreenVMState_) {

    val myAccountState by uiState
    init {
        //init state
        viewModelScope.launch(block = { state.collect { uiState.value = it } })
    }

    fun onStart(){
        Log.i("TAG", "onStart: my account ")
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
    fun onStop(){
        Log.d("TAG", "onStop: my account")
        viewModelScope.launchOnIOCatchingError(
            block = {
                    dispatchAction(
                        AppActions.MyAccountScreenAction.UpdateAccountList(getAllAccountsInteractor.getAllAccounts())
                    )
            }
        )
    }


    override fun dispatchAction(action: AppActions.MyAccountScreenAction) {
             updateState(GlobalAction.UpdateMyAccountScreenState(action))
    }
}