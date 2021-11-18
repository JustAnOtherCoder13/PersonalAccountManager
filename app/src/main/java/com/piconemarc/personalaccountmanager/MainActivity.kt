package com.piconemarc.personalaccountmanager

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.piconemarc.model.PAMIconButtons
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.base.BaseDeletePopUp
import com.piconemarc.personalaccountmanager.ui.popUp.DeleteOperationPopUp
import com.piconemarc.personalaccountmanager.ui.screen.PAMMainScreen
import com.piconemarc.personalaccountmanager.ui.theme.PersonalAccountManagerTheme
import com.piconemarc.viewmodel.viewModel.AppViewModel
import com.piconemarc.viewmodel.viewModel.utils.AppActions
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.ActivityScoped

@ActivityScoped
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appViewModel: AppViewModel by viewModels()

        setContent {

            PersonalAccountManagerTheme {
                Surface(
                    color = MaterialTheme.colors.secondaryVariant,
                    modifier = Modifier.fillMaxSize()
                ) {
                    BackHandler(true) {
                        when (appViewModel.popUpStates.filter { it.first.value.isVisible }.size) {
                            //if there is no popUp visible select appropriate interlayer or finish
                            0 -> {
                                when (appViewModel.appUiState.selectedInterlayerButton) {
                                    is PAMIconButtons.Home ->
                                        when (appViewModel.appUiState.interLayerTitle) {
                                            com.piconemarc.model.R.string.detail -> {
                                                appViewModel.dispatchAction(
                                                    AppActions.BaseAppScreenAction.SelectInterlayer(
                                                        PAMIconButtons.Home
                                                    )
                                                )
                                            }
                                            com.piconemarc.model.R.string.myAccountsInterLayerTitle -> {
                                                this.finish()
                                            }
                                        }
                                    else -> appViewModel.dispatchAction(
                                        AppActions.BaseAppScreenAction.SelectInterlayer(
                                            PAMIconButtons.Home
                                        )
                                    )
                                }
                            }
                            else -> {
                                //else close visible popUp
                                appViewModel.dispatchAction(
                                    appViewModel.popUpStates.filter { it.first.value.isVisible }[0].second
                                )
                            }
                        }
                    }
                }
                PAMMainScreen(
                    appViewModel = appViewModel,
                    appUiState = appViewModel.appUiState
                )
            }
        }
    }
}