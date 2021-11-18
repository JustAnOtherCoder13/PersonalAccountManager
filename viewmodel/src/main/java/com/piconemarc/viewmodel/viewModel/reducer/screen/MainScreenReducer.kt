package com.piconemarc.viewmodel.viewModel.reducer.screen

import com.piconemarc.model.PAMIconButtons
import com.piconemarc.viewmodel.viewModel.utils.AppActions
import com.piconemarc.viewmodel.viewModel.utils.Reducer
import com.piconemarc.viewmodel.viewModel.utils.ViewModelInnerStates

internal val appBaseScreenReducer: Reducer<ViewModelInnerStates.BaseAppScreenVmState> =
    { old, action ->
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
            is AppActions.BaseAppScreenAction.UpdateAccounts -> {
                var allAccountBalance = 0.0
                action.allAccounts.map { it.accountBalance }.forEach { allAccountBalance += it }
                var allAccountRest = 0.0
                action.allAccounts.map { it.rest }.forEach { allAccountRest += it }
                old.copy(
                    allAccountUis = action.allAccounts,
                    footerBalance = allAccountBalance,
                    footerRest = allAccountRest
                )
            }
            is AppActions.BaseAppScreenAction.UpdateInterlayerTiTle -> old.copy(
                interLayerTitle = action.interlayerTitle)
            is AppActions.BaseAppScreenAction.UpdateObsoletePaymentList -> old.copy(
                obsoletePaymentToDelete = action.obsoletePaymentList
            )
        }
    }