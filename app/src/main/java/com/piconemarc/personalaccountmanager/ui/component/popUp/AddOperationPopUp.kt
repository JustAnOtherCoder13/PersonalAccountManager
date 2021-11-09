package com.piconemarc.personalaccountmanager.ui.component.popUp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.piconemarc.model.entity.AccountUiModel
import com.piconemarc.model.entity.OperationUiModel
import com.piconemarc.model.entity.PaymentUiModel
import com.piconemarc.personalaccountmanager.R
import com.piconemarc.personalaccountmanager.ui.animation.pAMAddOperationPopUpBackgroundColor
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.AddOperationPopUpLeftSideMenuIconPanel
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.AddOperationPopUpPunctualOrRecurrentSwitchButton
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.AddOperationPopUpTransferOptionPanel
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.base.*
import com.piconemarc.viewmodel.viewModel.AppViewModel
import com.piconemarc.viewmodel.viewModel.utils.AppActions
import java.util.*

@Composable
fun AddOperationPopUp(
    viewModel: AppViewModel
) {
    //Pop up Body --------------------------------------------
    BasePopUp(
        title = viewModel.addOperationPopUpState.addPopUpTitle,
        onAcceptButtonClicked = {
            viewModel.dispatchAction(
                AppActions.AddOperationPopUpAction.AddNewOperation(
                    isOnPaymentScreen = viewModel.addOperationPopUpState.isOnPaymentScreen,
                    //if not on payment screen add operation
                    operation =
                    if (!viewModel.addOperationPopUpState.isOnPaymentScreen)
                        OperationUiModel(
                            name = viewModel.addOperationPopUpState.operationName,
                            amount = try {
                                viewModel.addOperationPopUpState.operationAmount.toDouble()
                            } catch (e: NumberFormatException) {
                                0.0
                            },
                            accountId = viewModel.addOperationPopUpState.selectedAccountId,
                            categoryId = viewModel.addOperationPopUpState.selectedCategory.id,
                            emitDate = Calendar.getInstance().time,
                            isAddOperation = viewModel.addOperationPopUpState.isAddOperation
                        )
                    else {
                        //else add payment
                        PaymentUiModel(
                            name = viewModel.addOperationPopUpState.operationName,
                            amount = try {
                                viewModel.addOperationPopUpState.operationAmount.toDouble()
                            } catch (e: NumberFormatException) {
                                0.0
                            },
                            accountId = viewModel.addOperationPopUpState.selectedAccountId,
                        )
                    }
                )
            )
        },
        onDismiss = { viewModel.dispatchAction(AppActions.AddOperationPopUpAction.ClosePopUp) },
        isExpanded = viewModel.addOperationPopUpState.isPopUpExpanded,
        menuIconPanel = {
            //if on payment screen no need to show left side icon panel, cause could only be payment
            if (!viewModel.addOperationPopUpState.isOnPaymentScreen)
                AddOperationPopUpLeftSideMenuIconPanel(
                    onIconButtonClicked = {
                        viewModel.dispatchAction(
                            AppActions.AddOperationPopUpAction.SelectOptionIcon(it)
                        )
                    },
                    selectedIcon = viewModel.addOperationPopUpState.addPopUpOptionSelectedIcon
                )
        },
        popUpBackgroundColor = pAMAddOperationPopUpBackgroundColor(
            isAddOperation = viewModel.addOperationPopUpState.isAddOperation,
            isAddOrMinusEnable = viewModel.addOperationPopUpState.isAddOrMinusEnable
        ).value
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //add or minus switch
            AddOperationPopUpAddOrMinusSwitchButton(
                onAddOrMinusClicked = { isAddClicked ->
                    viewModel.dispatchAction(
                        AppActions.AddOperationPopUpAction.SelectAddOrMinus(
                            isAddClicked
                        )
                    )
                },
                isAddOperation = viewModel.addOperationPopUpState.isAddOperation,
                isEnable = viewModel.addOperationPopUpState.isAddOrMinusEnable
            )
            // operation name--------------------------
            BrownBackgroundTextFieldItem(
                title = stringResource(R.string.operationName),
                onTextChange = { operationName ->
                    viewModel.dispatchAction(
                        AppActions.AddOperationPopUpAction.FillOperationName(
                            operationName
                        )
                    )
                },
                textValue = viewModel.addOperationPopUpState.operationName,
                isPopUpExpanded = viewModel.addOperationPopUpState.isPopUpExpanded,
                isError = viewModel.addOperationPopUpState.isOperationNameError,
                errorMsg = stringResource(R.string.AddOperationPopUpOperationNameErrorMessage)
            )
            // operation Amount--------------------------
            BrownBackgroundAmountTextFieldItem(
                title = stringResource(R.string.operationAmount),
                onTextChange = { operationAmount ->
                    viewModel.dispatchAction(
                        AppActions.AddOperationPopUpAction.FillOperationAmount(
                            operationAmount
                        )
                    )
                },
                amountValue = viewModel.addOperationPopUpState.operationAmount,
                isPopUpExpanded = viewModel.addOperationPopUpState.isPopUpExpanded,
                isError = viewModel.addOperationPopUpState.isOperationAmountError,
                errorMsg = stringResource(R.string.AddOperationPopUpAmountErrorMessage)
            )
            //category drop down -----------------------------------
            PAMBaseDropDownMenuWithBackground(
                selectedItem = viewModel.addOperationPopUpState.selectedCategory,
                itemList = viewModel.addOperationPopUpState.allCategories,
                onItemSelected = { category ->
                    viewModel.dispatchAction(
                        AppActions.AddOperationPopUpAction.SelectCategory(
                            category
                        )
                    )
                }
            )
            //Payment Operation option--------------------------
            AddOperationPopUpPunctualOrRecurrentSwitchButton(
                isRecurrentOptionExpanded = viewModel.addOperationPopUpState.isRecurrentOptionExpanded,
                isPaymentOptionExpanded = viewModel.addOperationPopUpState.isPaymentExpanded,
                onPunctualButtonSelected = {
                    viewModel
                        .dispatchAction(AppActions.AddOperationPopUpAction.CloseRecurrentOption)
                },
                onRecurrentButtonSelected = {
                    viewModel
                        .dispatchAction(AppActions.AddOperationPopUpAction.ExpandRecurrentOption)
                },
                onMonthSelected = { endDateMonth ->
                    viewModel.dispatchAction(
                        AppActions.AddOperationPopUpAction.SelectEndDateMonth(
                            endDateMonth
                        )
                    )
                },
                onYearSelected = { endDateYear ->
                    viewModel.dispatchAction(
                        AppActions.AddOperationPopUpAction.SelectEndDateYear(
                            endDateYear
                        )
                    )
                },
                endDateSelectedMonth = viewModel.addOperationPopUpState.enDateSelectedMonth,
                endDateSelectedYear = viewModel.addOperationPopUpState.endDateSelectedYear,
                selectableMonthList = viewModel.addOperationPopUpState.selectableEndDateMonths,
                selectableYearList = viewModel.addOperationPopUpState.selectableEndDateYears,
                isRecurrentSwitchError = viewModel.addOperationPopUpState.isRecurrentEndDateError
            )
            //Transfer Operation option---------------------------
            AddOperationPopUpTransferOptionPanel(
                isTransferOptionExpanded = viewModel.addOperationPopUpState.isTransferExpanded,
                //todo pass with state
                senderAccount = AccountUiModel(),
                allAccountsList = viewModel.addOperationPopUpState.allAccounts,
                beneficiaryAccountUiSelectedItem = viewModel.addOperationPopUpState.beneficiaryAccount,

                onBeneficiaryAccountSelected = { beneficiaryAccount ->
                    viewModel.dispatchAction(
                        AppActions.AddOperationPopUpAction.SelectBeneficiaryAccount(
                            beneficiaryAccount
                        )
                    )
                },
                isBeneficiaryAccountError = viewModel.addOperationPopUpState.isBeneficiaryAccountError
            )
            if (viewModel.addOperationPopUpState.isOnPaymentScreen)
                OptionCheckBox(
                    onCheckedChange = {
                        viewModel.dispatchAction(
                            AppActions.AddOperationPopUpAction.UpdateIsPaymentStartThisMonth(
                                it
                            )
                        )
                    },
                    isChecked = viewModel.addOperationPopUpState.isPaymentStartThisMonth,
                    optionText = stringResource(R.string.AddOperationPopUpAddPaymentOptionMessage)
                )
        }
    }
}

