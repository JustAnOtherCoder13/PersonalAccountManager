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
import com.piconemarc.viewmodel.viewModel.utils.AppActions
import com.piconemarc.viewmodel.viewModel.AppViewModel
import com.piconemarc.viewmodel.viewModel.reducer.AppSubscriber.AppUiState.addOperationPopUpUiState
import java.util.*

@Composable
fun AddOperationPopUp(
    viewModel: AppViewModel
) {
    //Pop up Body --------------------------------------------
    BasePopUp(
        title = addOperationPopUpUiState.value.addPopUpTitle,
        onAcceptButtonClicked = {
            viewModel.dispatchAction(
                AppActions.AddOperationPopUpAction.AddNewOperation(
                    //if not on payment screen add operation
                    if (!addOperationPopUpUiState.value.isOnPaymentScreen)
                        OperationUiModel(
                            name = addOperationPopUpUiState.value.operationName,
                            amount = try {
                                addOperationPopUpUiState.value.operationAmount.toDouble()
                            } catch (e: NumberFormatException) {
                                0.0
                            },
                            accountId = addOperationPopUpUiState.value.selectedAccountId,
                            categoryId = addOperationPopUpUiState.value.selectedCategory.id,
                            emitDate = Calendar.getInstance().time,
                            isAddOperation = addOperationPopUpUiState.value.isAddOperation
                        )
                    else {
                        //else add payment
                        PaymentUiModel(
                            name = addOperationPopUpUiState.value.operationName,
                            amount = try {
                                addOperationPopUpUiState.value.operationAmount.toDouble()
                            } catch (e: NumberFormatException) {
                                0.0
                            },
                            accountId = addOperationPopUpUiState.value.selectedAccountId,
                        )
                    }
                )
            )
        },
        onDismiss = { viewModel.dispatchAction(AppActions.AddOperationPopUpAction.ClosePopUp) },
        isExpanded = addOperationPopUpUiState.value.isPopUpExpanded,
        menuIconPanel = {
            //if on payment screen no need to show left side icon panel, cause could only be payment
            if (!addOperationPopUpUiState.value.isOnPaymentScreen)
                AddOperationPopUpLeftSideMenuIconPanel(
                    onIconButtonClicked = {
                        viewModel.dispatchAction(
                            AppActions.AddOperationPopUpAction.SelectOptionIcon(it)
                        )
                    },
                    selectedIcon = addOperationPopUpUiState.value.addPopUpOptionSelectedIcon
                )
        },
        popUpBackgroundColor = pAMAddOperationPopUpBackgroundColor(
            isAddOperation = addOperationPopUpUiState.value.isAddOperation,
            isAddOrMinusEnable = addOperationPopUpUiState.value.isAddOrMinusEnable
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
                isAddOperation = addOperationPopUpUiState.value.isAddOperation,
                isEnable = addOperationPopUpUiState.value.isAddOrMinusEnable
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
                textValue = addOperationPopUpUiState.value.operationName,
                isPopUpExpanded = addOperationPopUpUiState.value.isPopUpExpanded,
                isError = addOperationPopUpUiState.value.isOperationNameError,
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
                amountValue = addOperationPopUpUiState.value.operationAmount,
                isPopUpExpanded = addOperationPopUpUiState.value.isPopUpExpanded,
                isError = addOperationPopUpUiState.value.isOperationAmountError,
                errorMsg = stringResource(R.string.AddOperationPopUpAmountErrorMessage)
            )
            //category drop down -----------------------------------
            PAMBaseDropDownMenuWithBackground(
                selectedItem = addOperationPopUpUiState.value.selectedCategory,
                itemList = addOperationPopUpUiState.value.allCategories,
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
                isRecurrentOptionExpanded = addOperationPopUpUiState.value.isRecurrentOptionExpanded,
                isPaymentOptionExpanded = addOperationPopUpUiState.value.isPaymentExpanded,
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
                endDateSelectedMonth = addOperationPopUpUiState.value.enDateSelectedMonth,
                endDateSelectedYear = addOperationPopUpUiState.value.endDateSelectedYear,
                selectableMonthList = addOperationPopUpUiState.value.selectableEndDateMonths,
                selectableYearList = addOperationPopUpUiState.value.selectableEndDateYears,
                isRecurrentSwitchError = addOperationPopUpUiState.value.isRecurrentEndDateError
            )
            //Transfer Operation option---------------------------
            AddOperationPopUpTransferOptionPanel(
                isTransferOptionExpanded = addOperationPopUpUiState.value.isTransferExpanded,
                //todo pass with state
                senderAccount = AccountUiModel(),
                allAccountsList = addOperationPopUpUiState.value.allAccounts,
                beneficiaryAccountUiSelectedItem = addOperationPopUpUiState.value.beneficiaryAccount,

                onBeneficiaryAccountSelected = { beneficiaryAccount ->
                    viewModel.dispatchAction(
                        AppActions.AddOperationPopUpAction.SelectBeneficiaryAccount(
                            beneficiaryAccount
                        )
                    )
                },
                isBeneficiaryAccountError = addOperationPopUpUiState.value.isBeneficiaryAccountError
            )
            if (addOperationPopUpUiState.value.isOnPaymentScreen)
                OptionCheckBox(
                    onCheckedChange = {
                        viewModel.dispatchAction(
                            AppActions.AddOperationPopUpAction.UpdateIsPaymentStartThisMonth(
                                it
                            )
                        )
                    },
                    isChecked = addOperationPopUpUiState.value.isPaymentStartThisMonth,
                    optionText = stringResource(R.string.AddOperationPopUpAddPaymentOptionMessage)
                )
        }
    }
}

