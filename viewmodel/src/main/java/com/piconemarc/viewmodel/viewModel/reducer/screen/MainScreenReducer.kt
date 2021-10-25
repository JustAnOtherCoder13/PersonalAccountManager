package com.piconemarc.viewmodel.viewModel.reducer.screen

import com.piconemarc.model.PAMIconButtons
import com.piconemarc.viewmodel.Reducer
import com.piconemarc.viewmodel.viewModel.AppActions
import com.piconemarc.viewmodel.viewModel.ViewModelInnerStates

internal val appBaseScreenReducer: Reducer<ViewModelInnerStates.BaseAppScreenVmState> = { old, action ->
    action as AppActions.BaseAppScreenAction
    when (action) {
        is AppActions.BaseAppScreenAction.InitScreen -> old.copy(
            selectedInterlayerButton = PAMIconButtons.Home
        )
        is AppActions.BaseAppScreenAction.SelectInterlayer -> {
            old.copy(
                selectedInterlayerButton = action.selectedInterlayerButton,
                interLayerTitle = action.selectedInterlayerButton.iconName
            )
        }
        is AppActions.BaseAppScreenAction.UpdateAccounts -> old.copy(allAccountUis = action.allAccountUis)
        is AppActions.BaseAppScreenAction.UpdateFooterBalance -> {
            var allAccountBalance = 0.0
            action.allAccountUis.forEach {

                allAccountBalance += it.accountBalance
            }
            old.copy(
                footerBalance = allAccountBalance.toString()
            )
        }
        is AppActions.BaseAppScreenAction.UpdateFooterRest -> {
            var allAccountRest = 0.0
            action.allAccountUis.forEach {
                allAccountRest += it.rest
            }
            old.copy(
                footerRest = allAccountRest.toString()
            )
        }
        is AppActions.BaseAppScreenAction.UpdateFooterTitle -> old.copy(footerTitle = action.footerTitle)
        is AppActions.BaseAppScreenAction.UpdateInterlayerTiTle -> old.copy(interLayerTitle = action.interlayerTitle)
        is AppActions.BaseAppScreenAction.CloseApp -> old

    }
}