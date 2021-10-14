package com.piconemarc.personalaccountmanager

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.piconemarc.model.entity.GeneratedOperation
import com.piconemarc.personalaccountmanager.ui.component.PAMIconButtons
import com.piconemarc.personalaccountmanager.ui.component.popUp.PAMAddOperationPopUp
import com.piconemarc.personalaccountmanager.ui.component.screen.PAMMainScreen
import com.piconemarc.personalaccountmanager.ui.theme.*
import com.piconemarc.viewmodel.viewModel.AccountDetailViewModel
import com.piconemarc.viewmodel.viewModel.addOperationPopUp.AddOperationPopUpUtilsProvider
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.ActivityScoped

@ActivityScoped
@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    // private val deleteOperationPopUpScreenModel : ConfirmDeleteOperationPopUpScreenModel

    init {
        instance = this
        //deleteOperationPopUpScreenModel =  ConfirmDeleteOperationPopUpScreenModel()
    }

    companion object {
        private var instance: MainActivity? = null
        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       val accountDetailViewModel : AccountDetailViewModel by viewModels()

        setContent {
            PersonalAccountManagerTheme {
                Surface(color = MaterialTheme.colors.secondaryVariant, modifier = Modifier.fillMaxSize()) {
                    PAMMainScreen(accountDetailViewModel){
                    TestBody(accountDetailViewModel)
                    }
                }
            }
        }
    }

    @Composable
    private fun TestBody(operationDetailViewModel: AccountDetailViewModel) {
        Column(modifier = Modifier.fillMaxSize(),horizontalAlignment = Alignment.CenterHorizontally,verticalArrangement = Arrangement.Bottom) {
            Button(
                modifier = Modifier
                    .border(
                        width = ThinMarge,
                        color = MaterialTheme.colors.primary,
                        shape = RoundedCornerShape(BigMarge)
                    )
                    .width(100.dp)
                    .height(50.dp)
                ,
                shape = RoundedCornerShape(BigMarge),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = PinkDark,
                ),
                elevation = ButtonDefaults.elevation(
                    defaultElevation = 10.dp
                ),
                onClick = {
                operationDetailViewModel.dispatchAction(AddOperationPopUpUtilsProvider.AddOperationPopUpAction.Init)
            }) {
                Surface(
                    modifier = Modifier
                        .padding(LittleMarge),
                    shape = CircleShape,
                    color = Color.Transparent,
                    border = BorderStroke(ThinMarge, MaterialTheme.colors.onPrimary),
                )
                {
                    Icon(
                        imageVector = ImageVector.vectorResource(PAMIconButtons.Add.vectorIcon),
                        contentDescription = stringResource(PAMIconButtons.Add.iconContentDescription),
                        modifier = Modifier
                            .background(Color.Transparent, CircleShape)
                            .padding(LittleMarge),
                        tint = MaterialTheme.colors.onPrimary
                    )
                }
            }
        }
    }
}