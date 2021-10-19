package com.piconemarc.viewmodel.viewModel.baseAppScreen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import com.piconemarc.model.entity.AccountModel
import com.piconemarc.viewmodel.viewModel.Reducer
import com.piconemarc.viewmodel.viewModel.StoreSubscriber
import com.piconemarc.viewmodel.viewModel.UiAction
import com.piconemarc.viewmodel.viewModel.VMState
import com.piconemarc.viewmodel.viewModel.baseAppScreen.BaseAppScreenUtilProvider.BaseAppScreenMutableState.allAccounts_
import com.piconemarc.viewmodel.viewModel.baseAppScreen.BaseAppScreenUtilProvider.BaseAppScreenMutableState.selectedInterlayerButton_

class BaseAppScreenUtilProvider {


    data class BaseAppScreenVmState(
        val selectedInterlayerButton : String = "My Accounts",
        val allAccounts : List<AccountModel> =  listOf(),
    ) : VMState


    sealed class BaseAppScreenAction : UiAction{
        object InitScreen : BaseAppScreenAction()
        data class SelectInterlayer(val selectedInterlayerButton: String) : BaseAppScreenAction()
        data class UpdateAccounts(val allAccounts: List<AccountModel>) : BaseAppScreenAction()
    }


    val appScreenReducer : Reducer<BaseAppScreenVmState> = {
        old, action ->
        action as BaseAppScreenAction
        when (action){
            is BaseAppScreenAction.InitScreen -> old.copy(
                selectedInterlayerButton = "My Accounts",
                allAccounts = listOf()
            )
            is BaseAppScreenAction.SelectInterlayer -> old.copy(selectedInterlayerButton = action.selectedInterlayerButton)
            is BaseAppScreenAction.UpdateAccounts -> old.copy(allAccounts = action.allAccounts)
        }
    }

    private object BaseAppScreenMutableState {
        val selectedInterlayerButton_ : MutableState<String> = mutableStateOf("My Accounts")
        val allAccounts_ : MutableState<List<AccountModel>> = mutableStateOf(listOf())
    }

    val baseAppScreenSubscriber : StoreSubscriber<BaseAppScreenVmState> = {baseAppScreenVmState ->

     selectedInterlayerButton_.value = baseAppScreenVmState.selectedInterlayerButton
     allAccounts_.value = baseAppScreenVmState.allAccounts

    }

    object BaseAppScreenUiState {
        val selectedInterlayerButton by selectedInterlayerButton_
        val allAccounts by allAccounts_
    }
}