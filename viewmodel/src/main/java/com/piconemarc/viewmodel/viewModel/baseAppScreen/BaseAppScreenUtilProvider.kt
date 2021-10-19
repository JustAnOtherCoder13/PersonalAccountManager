package com.piconemarc.viewmodel.viewModel.baseAppScreen

import com.piconemarc.model.entity.AccountModel
import com.piconemarc.viewmodel.viewModel.Reducer
import com.piconemarc.viewmodel.viewModel.UiAction
import com.piconemarc.viewmodel.viewModel.VMState

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


}