package com.piconemarc.personalaccountmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.piconemarc.model.PAMIconButtons
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

        val appViewModel : AppViewModel by viewModels()

        setContent {
            PersonalAccountManagerTheme {
                Surface(
                    color = MaterialTheme.colors.secondaryVariant,
                    modifier = Modifier.fillMaxSize()
                ) {
                    BackHandler(true) {
                        //todo check if pop up is open, in case close popUp
                        //have to pass with abstraction in function, certainly have to review
                        // popUp Vm State

                        when(appViewModel.appUiState.selectedInterlayerButton){
                            is PAMIconButtons.Home ->
                                when(appViewModel.appUiState.interLayerTitle){
                                    com.piconemarc.model.R.string.detail ->{
                                        appViewModel.dispatchAction(
                                            AppActions.BaseAppScreenAction.SelectInterlayer(PAMIconButtons.Home)
                                        )
                                    }
                                    com.piconemarc.model.R.string.myAccountsInterLayerTitle -> {
                                        this.finish()
                                        }
                                }
                            else -> appViewModel.dispatchAction(
                                AppActions.BaseAppScreenAction.SelectInterlayer(PAMIconButtons.Home)
                            )
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
}