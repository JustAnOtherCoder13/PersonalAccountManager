package com.piconemarc.personalaccountmanager.ui.baseComponent.popUp.addOperationPopUp

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.piconemarc.personalaccountmanager.R
import com.piconemarc.personalaccountmanager.ui.IconButtons
import com.piconemarc.personalaccountmanager.ui.baseComponent.addOperationPopUpAnimation
import com.piconemarc.personalaccountmanager.ui.baseComponent.expandCollapseTransferAnimation
import com.piconemarc.personalaccountmanager.ui.baseComponent.popUp.*
import com.piconemarc.personalaccountmanager.ui.baseComponent.selectorOffsetAnimation
import com.piconemarc.personalaccountmanager.ui.theme.BigMarge
import com.piconemarc.personalaccountmanager.ui.theme.Black
import com.piconemarc.personalaccountmanager.ui.theme.RegularMarge

enum class AddOperationPopUpState {
    COLLAPSED,
    EXPANDED
}

@Composable
fun AddOperationPopUpLeftSideIcon(
    onIconButtonClicked: (operationType: Int) -> Unit,
    operationType: String
) {
    Box {
        Box(
            modifier = Modifier
                .selectorOffsetAnimation(selectedOperationType = operationType)
                .background(
                    color = MaterialTheme.colors.primaryVariant,
                    shape = RoundedCornerShape(topStart = BigMarge, bottomStart = BigMarge)
                )
                .height(48.dp)
                .width(48.dp)
        )
        Column {
            PAMIconButton(
                iconButton = IconButtons.OPERATION,
                onIconButtonClicked = { onIconButtonClicked(it) }
            )
            Spacer(modifier = Modifier.height(RegularMarge))
            PAMIconButton(
                iconButton = IconButtons.PAYMENT,
                onIconButtonClicked = { onIconButtonClicked(it) }
            )
            Spacer(modifier = Modifier.height(RegularMarge))
            PAMIconButton(
                iconButton = IconButtons.TRANSFER,
                onIconButtonClicked = { onIconButtonClicked(it) }
            )
        }
    }
}

@Composable
fun TransferOptionPanel(
    modifier: Modifier,
    accountList: List<String>,
    senderSelectedAccount: String,
    beneficiarySelectedAccount: String,
    onSenderAccountSelected: (senderAccount: String) -> Unit,
    onBeneficiaryAccountSelected: (beneficiaryAccount: String) -> Unit
) {
    Column(modifier = modifier) {
        BaseDropDownMenuWithBackground(
            selectedItem = senderSelectedAccount,
            itemList = accountList,
            onItemSelected = onSenderAccountSelected
        )
        BaseDropDownMenuWithBackground(
            selectedItem = beneficiarySelectedAccount,
            itemList = accountList,
            onItemSelected = onBeneficiaryAccountSelected
        )
    }
}

@SuppressLint("ModifierParameter")
@Composable
fun AddOperationPopUp(
    addOperationPopUpState: AddOperationPopUpState,
    popUpPunctualOrRecurrentSwitchButtonModifier: Modifier,
    popUpTitle: String,
    popUpCategory: String,
    popUpOperationName: String,
    popUpOperationAmount: String,
    popUpCategoryList: List<String>,
    popUpAccountList: List<String>,
    popUpSenderSelectedAccount: String,
    popUpBeneficiarySelectedAccount: String,
    popUpSelectedMonth: String,
    popUpSelectedYear: String,
    popUpOnAddOperation: () -> Unit,
    popUpOnDismiss: () -> Unit,
    popUpOnIconButtonClicked: (operationType: Int) -> Unit,
    popUpOnCategorySelected: (category: String) -> Unit,
    popUpOnEnterOperationName: (operationName: String) -> Unit,
    popUpOnEnterOperationAmount: (operationAmount: String) -> Unit,
    popUpOnRecurrentOrPunctualSwitched: (Boolean) -> Unit,
    popUpOnMonthSelected: (String) -> Unit,
    popUpOnYearSelected: (String) -> Unit,
    popUpOnSenderAccountSelected: (senderAccount: String) -> Unit,
    popUpOnBeneficiaryAccountSelected: (beneficiaryAccount: String) -> Unit
) {
    val transition = addOperationPopUpAnimation(addOperationPopUpState = addOperationPopUpState)
    Column(
        modifier = Modifier
            .background(Black.copy(alpha = transition.alpha))
            .fillMaxSize()

    ) {
        Row(
            modifier = Modifier
                .height(transition.size)
                .offset(y = transition.position)
                .padding(horizontal = RegularMarge, vertical = RegularMarge)
        ) {
            //Left menu-----------------------------------------------------
            AddOperationPopUpLeftSideIcon(
                onIconButtonClicked = popUpOnIconButtonClicked,
                operationType = popUpTitle
            )
            //Pop up Body --------------------------------------------
            LazyColumn {
                item {
                    BasePopUp(
                        title = popUpTitle,
                        onAcceptButtonClicked = popUpOnAddOperation,
                        onCancelButtonClicked = popUpOnDismiss
                    ) {
                        //category drop down -----------------------------------
                        BaseDropDownMenuWithBackground(
                            selectedItem = popUpCategory,
                            itemList = popUpCategoryList,
                            onItemSelected = popUpOnCategorySelected
                        )
                        // operation and amount text field--------------------------
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            BasePopUpTextFieldItem(
                                title = stringResource(R.string.operationName),
                                onTextChange = popUpOnEnterOperationName,
                                textValue = popUpOperationName
                            )
                            BasePopUpAmountTextFieldItem(
                                title = stringResource(R.string.operationAmount),
                                onTextChange = popUpOnEnterOperationAmount,
                                amountValue = popUpOperationAmount
                            )
                            //Payment Operation option--------------------------
                            PunctualOrRecurrentSwitchButton(
                                modifier = popUpPunctualOrRecurrentSwitchButtonModifier,
                                isRecurrent = popUpOnRecurrentOrPunctualSwitched,
                                selectedYear = popUpSelectedYear,
                                selectedMonth = popUpSelectedMonth,
                                onYearSelected = popUpOnYearSelected,
                                onMonthSelected = popUpOnMonthSelected
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
    }
}