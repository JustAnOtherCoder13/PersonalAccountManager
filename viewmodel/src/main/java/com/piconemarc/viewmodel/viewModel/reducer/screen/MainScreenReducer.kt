package com.piconemarc.viewmodel.viewModel.reducer.screen

import android.util.Log
import com.piconemarc.model.PAMIconButtons
import com.piconemarc.model.entity.PresentationDataModel
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
            Log.i("TAG", ": ${action.selectedInterlayerButton}")
            old.copy(
                selectedInterlayerButton = action.selectedInterlayerButton,
                interLayerTitle = action.selectedInterlayerButton.iconName
            )
        }
        is AppActions.BaseAppScreenAction.UpdateAccounts -> old.copy(allAccounts = action.allAccounts)
        is AppActions.BaseAppScreenAction.UpdateFooterBalance -> {
            var allAccountBalance = 0.0
            action.allAccounts.forEach {

                allAccountBalance += it.accountBalance
            }
            old.copy(
                footerBalance = PresentationDataModel(allAccountBalance.toString())
            )
        }
        is AppActions.BaseAppScreenAction.UpdateFooterRest -> {
            var allAccountRest = 0.0
            action.allAccounts.forEach {
                allAccountRest += (it.accountBalance + it.accountOverdraft)
            }
            old.copy(
                footerRest = PresentationDataModel(allAccountRest.toString())
            )
        }
        is AppActions.BaseAppScreenAction.UpdateFooterTitle -> old.copy(footerTitle = action.footerTitle)
        is AppActions.BaseAppScreenAction.UpdateInterlayerTiTle -> old.copy(interLayerTitle = action.interlayerTitle)
        is AppActions.BaseAppScreenAction.CloseApp -> old

    }
}