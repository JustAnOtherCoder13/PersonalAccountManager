package com.piconemarc.personalaccountmanager

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.piconemarc.personalaccountmanager.ui.*
import com.piconemarc.personalaccountmanager.ui.baseComponent.expandCollapsePaymentAnimation
import com.piconemarc.personalaccountmanager.ui.baseComponent.popUp.addOperationPopUp.AddOperationPopUp
import com.piconemarc.personalaccountmanager.ui.theme.PersonalAccountManagerTheme

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
        val testList = mutableListOf("test 1", "test 2", "test 3")
        setContent {
            PersonalAccountManagerTheme {
                Surface(
                    color = MaterialTheme.colors.secondary
                ) {
                    Button(
                        onClick = { popUpEventHandler(AddPopUpUiEvent.InitPopUp) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    ) { Text(text = "Click") }

                    AddOperationPopUp(
                        addOperationPopUpState = addOperationPopUpState.value,
                        popUpOnDismiss = { popUpEventHandler(AddPopUpUiEvent.OnDismiss) },
                        popUpTitle = stringResource(id = popUpOperationType.value),
                        popUpAccountList = listOf("Account1", "Account2", "Account3"),
                        popUpCategory = popUpCategory.value,
                        popUpCategoryList = testList,
                        popUpOperationAmount = popUpOperationAmount.value,
                        popUpOperationName = popUpOperationName.value,
                        popUpSenderSelectedAccount = popUpSenderSelectedAccount.value,
                        popUpBeneficiarySelectedAccount = popUpBeneficiarySelectedAccount.value,
                        popUpSelectedMonth = popUpSelectedMonth.value,
                        popUpSelectedYear = popUpSelectedYear.value,
                        popUpPunctualOrRecurrentSwitchButtonModifier = Modifier.height(
                            expandCollapsePaymentAnimation(
                                getString(popUpOperationType.value),
                                popUpIsRecurrent.value
                            ).value
                        ),
                        popUpOnRecurrentOrPunctualSwitched = { isRecurrent ->
                            popUpEventHandler(
                                AddPopUpUiEvent.OnRecurrentOrPunctualSwitched(isRecurrent = isRecurrent)
                            )
                        },
                        popUpOnBeneficiaryAccountSelected = { beneficiaryAccount ->
                            popUpEventHandler(
                                AddPopUpUiEvent.OnBeneficiaryAccountSelected(beneficiaryAccount = beneficiaryAccount)
                            )
                        },
                        popUpOnCategorySelected = { category ->
                            popUpEventHandler(
                                AddPopUpUiEvent.OnCategorySelected(
                                    selectedCategory = category
                                )
                            )
                        },
                        popUpOnEnterOperationAmount = { amount ->
                            popUpEventHandler(
                                AddPopUpUiEvent.OnEnterOperationAmount(
                                    operationAmount = amount
                                )
                            )
                        },
                        popUpOnEnterOperationName = { name ->
                            popUpEventHandler(
                                AddPopUpUiEvent.OnEnterOperationName(
                                    operationName = name
                                )
                            )
                        },
                        popUpOnMonthSelected = { selectedMonth ->
                            popUpEventHandler(
                                AddPopUpUiEvent.OnMonthSelected(selectedMonth = selectedMonth)
                            )
                        },
                        popUpOnYearSelected = { selectedYear ->
                            popUpEventHandler(AddPopUpUiEvent.OnYearSelected(selectedYear = selectedYear))
                        },
                        popUpOnSenderAccountSelected = { senderAccount ->
                            popUpEventHandler(
                                AddPopUpUiEvent.OnSenderAccountSelected(senderAccount = senderAccount)
                            )
                        },
                        popUpOnAddOperation = { popUpEventHandler(AddPopUpUiEvent.OnAddOperation) },
                        popUpOnIconButtonClicked = { operationType ->
                            popUpEventHandler(
                                AddPopUpUiEvent.OnLeftSideIconButtonClicked(
                                    operationType = operationType
                                )
                            )
                        },
                    )
                }
            }
        }
    }
}