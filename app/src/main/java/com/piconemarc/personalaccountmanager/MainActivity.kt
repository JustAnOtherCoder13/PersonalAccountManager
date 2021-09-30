package com.piconemarc.personalaccountmanager

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.piconemarc.model.entity.TEST_OPERATION_MODEL
import com.piconemarc.personalaccountmanager.newUi.popUp.PAMAddOperationPopUp
import com.piconemarc.personalaccountmanager.newUi.popUp.PAMDeleteOperationPopUp
import com.piconemarc.personalaccountmanager.ui.baseComponent.stateManager.events.*
import com.piconemarc.personalaccountmanager.ui.theme.PersonalAccountManagerTheme
import com.piconemarc.viewmodel.viewModel.AddOperationPopUpScreenModel
import com.piconemarc.viewmodel.viewModel.ConfirmDeleteOperationPopUpScreenModel

class MainActivity : ComponentActivity() {

    init {
        instance = this

    }

    companion object {
        private var instance: MainActivity? = null
        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            PersonalAccountManagerTheme {
                val testList by remember {
                    mutableStateOf(TEST_OPERATION_MODEL)
                }
                Surface(
                    color = MaterialTheme.colors.secondary
                ) {
                    Column() {


                        LazyColumn() {
                            items(testList) { item ->
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column {
                                        Text(text = item.operationName)
                                        Text(text = item.operationAmount.toString())
                                    }
                                    Button(
                                        onClick = {
                                            ConfirmDeleteOperationPopUpScreenModel().expand(
                                                operationModel = item
                                            )
                                        }
                                    ) { Text(text = "Click") }
                                }
                            }
                        }
                        Button(onClick = { AddOperationPopUpScreenModel().expand() }) {
                            Text(text = "add operation")
                        }
                        PAMDeleteOperationPopUp()
                        PAMAddOperationPopUp(
                            addOperationPopUpState = addOperationPopUpState.value,
                            popUpTitle = PopUpTitleStates.PopUpTitle().addOperationPopUpTitleState,
                            popUpOperationName = popUpOperationName.value,
                            popUpOperationAmount = popUpOperationAmount.value,
                            popUpAccountList = listOf(),
                            popUpSenderSelectedAccount = popUpSenderSelectedAccount.value,
                            popUpBeneficiarySelectedAccount = popUpBeneficiarySelectedAccount.value,
                            popUpSelectedMonth = popUpSelectedMonth.value,
                            popUpSelectedYear = popUpSelectedYear.value,
                            popUpOnEnterOperationName = {},
                            popUpOnEnterOperationAmount = {},
                            popUpOnRecurrentOrPunctualSwitched = {},
                            popUpOnMonthSelected = {},
                            popUpOnYearSelected = {},
                            popUpOnSenderAccountSelected = {},
                            popUpOnBeneficiaryAccountSelected = {},
                            switchButtonState = recurrentSwitchButtonState.value,
                        )
                    }
                }
            }
        }
    }
}