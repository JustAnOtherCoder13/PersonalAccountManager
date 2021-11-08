package com.piconemarc.viewmodel.viewModel.reducer.screen

import com.piconemarc.model.PAMIconButtons
import com.piconemarc.viewmodel.viewModel.utils.Reducer
import com.piconemarc.viewmodel.viewModel.utils.AppActions
import com.piconemarc.viewmodel.viewModel.utils.ViewModelInnerStates

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
            action.allAccounts.map { it.accountBalance }.forEach { allAccountBalance+=it}
            old.copy(footerBalance = allAccountBalance)
        }
        is AppActions.BaseAppScreenAction.UpdateFooterRest -> {
            var allAccountRest = 0.0
            action.allAccountUis.map { it.rest }.forEach { allAccountRest += it }
            old.copy(footerRest = allAccountRest)
        }
        is AppActions.BaseAppScreenAction.UpdateInterlayerTiTle -> old.copy(interLayerTitle = action.interlayerTitle)
        is AppActions.BaseAppScreenAction.CloseApp -> old

    }
}