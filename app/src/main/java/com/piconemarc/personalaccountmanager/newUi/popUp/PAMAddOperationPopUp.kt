package com.piconemarc.personalaccountmanager.newUi.popUp

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.piconemarc.personalaccountmanager.R
import com.piconemarc.personalaccountmanager.newUi.component.PAMAddOperationPopUpLeftSideMenuIconPanel
import com.piconemarc.personalaccountmanager.newUi.component.PAMBaseDropDownMenuWithBackground
import com.piconemarc.personalaccountmanager.newUi.component.PAMBasePopUp
import com.piconemarc.personalaccountmanager.ui.baseComponent.popUp.BasePopUpAmountTextFieldItem
import com.piconemarc.personalaccountmanager.ui.baseComponent.popUp.BasePopUpTextFieldItem
import com.piconemarc.personalaccountmanager.ui.baseComponent.popUp.addOperationPopUp.AddOperationPopUpLeftSideIcon
import com.piconemarc.personalaccountmanager.ui.baseComponent.popUp.addOperationPopUp.PunctualOrRecurrentSwitchButton
import com.piconemarc.personalaccountmanager.ui.baseComponent.popUp.addOperationPopUp.TransferOptionPanel
import com.piconemarc.personalaccountmanager.ui.baseComponent.popUp.animation.expandCollapseTransferAnimation
import com.piconemarc.personalaccountmanager.ui.baseComponent.stateManager.states.UiStates
import com.piconemarc.personalaccountmanager.ui.theme.RegularMarge
import com.piconemarc.viewmodel.viewModel.AddOperationPopUpScreenModel

@SuppressLint("ModifierParameter")
@Composable
fun PAMAddOperationPopUp(
    addOperationPopUpState: UiStates.AddOperationPopUpState,
    popUpTitle: String,
    popUpOperationName: String,
    popUpOperationAmount: String,
    popUpAccountList: List<String>,
    popUpSenderSelectedAccount: String,
    popUpBeneficiarySelectedAccount: String,
    popUpSelectedMonth: String,
    popUpSelectedYear: String,
    popUpOnEnterOperationName: (operationName: String) -> Unit,
    popUpOnEnterOperationAmount: (operationAmount: String) -> Unit,
    popUpOnRecurrentOrPunctualSwitched: (UiStates.SwitchButtonState) -> Unit,
    popUpOnMonthSelected: (String) -> Unit,
    popUpOnYearSelected: (String) -> Unit,
    popUpOnSenderAccountSelected: (senderAccount: String) -> Unit,
    popUpOnBeneficiaryAccountSelected: (beneficiaryAccount: String) -> Unit,
    switchButtonState: UiStates.SwitchButtonState,
) {
    val screenModel = AddOperationPopUpScreenModel()

    //Pop up Body --------------------------------------------
    LazyColumn {
        item {
            PAMBasePopUp(
                title = screenModel.getState().popUpTitle,
                onAcceptButtonClicked = { screenModel.addOperation() },
                onDismiss = { screenModel.closeAddPopUp() },
                isExpanded = screenModel.getState().isExpanded,
                menuIconPanel = { PAMAddOperationPopUpLeftSideMenuIconPanel() }
            ) {
                //category drop down -----------------------------------
                PAMBaseDropDownMenuWithBackground(
                    selectedItem = screenModel.getState().category.name,
                    itemList = screenModel.getState().allCategories,
                    onItemSelected = {
                        screenModel.selectCategory(it)
                    }
                )
                // operation and amount text field--------------------------
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BasePopUpTextFieldItem(
                        title = stringResource(R.string.operationName),
                        onTextChange = popUpOnEnterOperationName,
                        textValue = popUpOperationName,
                        addOperationPopUpState = addOperationPopUpState
                    )
                    BasePopUpAmountTextFieldItem(
                        title = stringResource(R.string.operationAmount),
                        onTextChange = popUpOnEnterOperationAmount,
                        amountValue = popUpOperationAmount,
                        addOperationPopUpState = addOperationPopUpState
                    )
                    //Payment Operation option--------------------------
                    PunctualOrRecurrentSwitchButton(
                        updateSwitchButtonState = popUpOnRecurrentOrPunctualSwitched,
                        selectedYear = popUpSelectedYear,
                        selectedMonth = popUpSelectedMonth,
                        onYearSelected = popUpOnYearSelected,
                        onMonthSelected = popUpOnMonthSelected,
                        switchButtonState = switchButtonState
                    )
                    //Transfer Operation option---------------------------
                    TransferOptionPanel(
                        modifier = Modifier.height(
                            expandCollapseTransferAnimation(
                                popUpTitle
                            ).value
                        ),
                        accountList = popUpAccountList,
                        senderSelectedAccount = popUpSenderSelectedAccount,
                        beneficiarySelectedAccount = popUpBeneficiarySelectedAccount,
                        onSenderAccountSelected = popUpOnSenderAccountSelected,
                        onBeneficiaryAccountSelected = popUpOnBeneficiaryAccountSelected
                    )
                }
            }

        }
    }
}