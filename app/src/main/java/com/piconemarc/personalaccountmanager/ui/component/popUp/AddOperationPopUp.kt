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
import com.piconemarc.viewmodel.viewModel.AppActions
import com.piconemarc.viewmodel.viewModel.AppViewModel
import com.piconemarc.viewmodel.viewModel.reducer.AppSubscriber.AppUiState.addOperationPopUpUiState
import java.util.*

@Composable
fun AddOperationPopUp(
    viewModel: AppViewModel
) {
    //Pop up Body --------------------------------------------
    BasePopUp(
        title = addOperationPopUpUiState.addPopUpTitle,
        onAcceptButtonClicked = {
            viewModel.dispatchAction(
                AppActions.AddOperationPopUpAction.AddNewOperation(
                    //if not on payment screen add operation
                    if (!addOperationPopUpUiState.isOnPaymentScreen)
                        OperationUiModel(
                            name = addOperationPopUpUiState.operationName,
                            amount = try {
                                addOperationPopUpUiState.operationAmount.toDouble()
                            } catch (e: NumberFormatException) {
                                0.0
                            },
                            accountId = addOperationPopUpUiState.selectedAccountId,
                            categoryId = addOperationPopUpUiState.selectedCategory.id,
                            emitDate = Calendar.getInstance().time,
                            isAddOperation = addOperationPopUpUiState.isAddOperation
                        )
                    else {
                        //else add payment
                        PaymentUiModel(
                            name = addOperationPopUpUiState.operationName,
                            amount = try {
                                addOperationPopUpUiState.operationAmount.toDouble()
                            } catch (e: NumberFormatException) {
                                0.0
                            },
                            accountId = addOperationPopUpUiState.selectedAccountId,
                        )
                    }
                )
            )
        },
        onDismiss = { viewModel.dispatchAction(AppActions.AddOperationPopUpAction.ClosePopUp) },
        isExpanded = addOperationPopUpUiState.isPopUpExpanded,
        menuIconPanel = {
            //if on payment screen no need to show left side icon panel, cause could only be payment
            if (!addOperationPopUpUiState.isOnPaymentScreen)
                AddOperationPopUpLeftSideMenuIconPanel(
                    onIconButtonClicked = {
                        viewModel.dispatchAction(
                            AppActions.AddOperationPopUpAction.SelectOptionIcon(it)
                        )
                    },
                    selectedIcon = addOperationPopUpUiState.addPopUpOptionSelectedIcon
                )
        },
        popUpBackgroundColor = pAMAddOperationPopUpBackgroundColor(
            isAddOperation = addOperationPopUpUiState.isAddOperation,
            isAddOrMinusEnable = addOperationPopUpUiState.isAddOrMinusEnable
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
                isAddOperation = addOperationPopUpUiState.isAddOperation,
                isEnable = addOperationPopUpUiState.isAddOrMinusEnable
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
                textValue = addOperationPopUpUiState.operationName,
                isPopUpExpanded = addOperationPopUpUiState.isPopUpExpanded,
                isError = addOperationPopUpUiState.isOperationNameError,
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
                amountValue = addOperationPopUpUiState.operationAmount,
                isPopUpExpanded = addOperationPopUpUiState.isPopUpExpanded,
                isError = addOperationPopUpUiState.isOperationAmountError,
                errorMsg = stringResource(R.string.AddOperationPopUpAmountErrorMessage)
            )
            //category drop down -----------------------------------
            PAMBaseDropDownMenuWithBackground(
                selectedItem = addOperationPopUpUiState.selectedCategory,
                itemList = addOperationPopUpUiState.allCategories,
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
                isRecurrentOptionExpanded = addOperationPopUpUiState.isRecurrentOptionExpanded,
                isPaymentOptionExpanded = addOperationPopUpUiState.isPaymentExpanded,
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
                endDateSelectedMonth = addOperationPopUpUiState.enDateSelectedMonth,
                endDateSelectedYear = addOperationPopUpUiState.endDateSelectedYear,
                selectableMonthList = addOperationPopUpUiState.selectableEndDateMonths,
                selectableYearList = addOperationPopUpUiState.selectableEndDateYears,
                isRecurrentSwitchError = addOperationPopUpUiState.isRecurrentEndDateError
            )
            //Transfer Operation option---------------------------
            AddOperationPopUpTransferOptionPanel(
                isTransferOptionExpanded = addOperationPopUpUiState.isTransferExpanded,
                //todo pass with state
                senderAccount = AccountUiModel(),
                allAccountsList = addOperationPopUpUiState.allAccounts,
                beneficiaryAccountUiSelectedItem = addOperationPopUpUiState.beneficiaryAccount,

                onBeneficiaryAccountSelected = { beneficiaryAccount ->
                    viewModel.dispatchAction(
                        AppActions.AddOperationPopUpAction.SelectBeneficiaryAccount(
                            beneficiaryAccount
                        )
                    )
                },
                isBeneficiaryAccountError = addOperationPopUpUiState.isBeneficiaryAccountError
            )
            if (addOperationPopUpUiState.isOnPaymentScreen)
                OptionCheckBox(
                    onCheckedChange = {
                        viewModel.dispatchAction(
                            AppActions.AddOperationPopUpAction.UpdateIsPaymentStartThisMonth(
                                it
                            )
                        )
                    },
                    isChecked = addOperationPopUpUiState.isPaymentStartThisMonth,
                    optionText = stringResource(R.string.AddOperationPopUpAddPaymentOptionMessage)
                )
        }
    }
}

