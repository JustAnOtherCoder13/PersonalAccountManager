package com.piconemarc.personalaccountmanager.ui.component.popUp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.piconemarc.model.entity.EndDate
import com.piconemarc.model.entity.OperationModel
import com.piconemarc.model.entity.PresentationDataModel
import com.piconemarc.personalaccountmanager.R
import com.piconemarc.personalaccountmanager.ui.animation.pAMAddOperationPopUpBackgroundColor
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.*
import com.piconemarc.viewmodel.viewModel.AppActionDispatcher
import com.piconemarc.viewmodel.viewModel.AppActions
import com.piconemarc.viewmodel.viewModel.AppSubscriber.GlobalUiState.addOperationPopUpUiState
import com.piconemarc.viewmodel.viewModel.AppSubscriber.GlobalUiState.myAccountDetailScreenUiState

@Composable
fun PAMAddOperationPopUp(
    actionDispatcher: AppActionDispatcher
) {
    //Pop up Body --------------------------------------------
    PAMBasePopUp(
        title = addOperationPopUpUiState.addPopUpTitle,
        onAcceptButtonClicked = {
            actionDispatcher.dispatchAction(
                AppActions.AddOperationPopUpAction.AddNewOperation(
                    OperationModel(
                        name = addOperationPopUpUiState.operationName.stringValue,
                        amount = try {
                            addOperationPopUpUiState.operationAmount.stringValue.toDouble()
                        } catch (e: NumberFormatException) {
                            0.0
                        },
                        accountId = myAccountDetailScreenUiState.accountName.objectIdReference,
                        categoryId = addOperationPopUpUiState.selectedCategory.objectIdReference,
                        isRecurrent = addOperationPopUpUiState.isRecurrentOptionExpanded,
                        endDate = if (!addOperationPopUpUiState.isRecurrentEndDateError) EndDate(
                            month = addOperationPopUpUiState.enDateSelectedMonth.stringValue,
                            year = addOperationPopUpUiState.endDateSelectedYear.stringValue
                        ) else null,
                        senderAccountId = if(!addOperationPopUpUiState.isSenderAccountError)
                            addOperationPopUpUiState.senderAccount.objectIdReference else null,
                        beneficiaryAccountId = if(!addOperationPopUpUiState.isBeneficiaryAccountError)
                            addOperationPopUpUiState.beneficiaryAccount.objectIdReference else null

                    )
                )
            )

        },
        onDismiss = { actionDispatcher.dispatchAction(AppActions.AddOperationPopUpAction.ClosePopUp) },
        isExpanded = addOperationPopUpUiState.isPopUpExpanded,
        menuIconPanel = {
            PAMAddOperationPopUpLeftSideMenuIconPanel(
                onIconButtonClicked = {
                    actionDispatcher.dispatchAction(
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
            AddOrMinusSwitchButton(
                onAddOrMinusClicked = { isAddClicked ->
                    actionDispatcher.dispatchAction(
                        AppActions.AddOperationPopUpAction.SelectAddOrMinus(
                            isAddClicked
                        )
                    )
                },
                isAddOperation = addOperationPopUpUiState.isAddOperation,
                isEnable = addOperationPopUpUiState.isAddOrMinusEnable
            )
            // operation name--------------------------
            PAMBrownBackgroundTextFieldItem(
                title = PresentationDataModel(stringValue = stringResource(R.string.operationName)),
                onTextChange = { operationName ->
                    actionDispatcher.dispatchAction(
                        AppActions.AddOperationPopUpAction.FillOperationName(
                            operationName
                        )
                    )
                },
                textValue = addOperationPopUpUiState.operationName,
                isPopUpExpanded = addOperationPopUpUiState.isPopUpExpanded,
                isError = addOperationPopUpUiState.isOperationNameError,
                errorMsg = PresentationDataModel("Please, fill operation name")
            )
            // operation Amount--------------------------
            PAMBrownBackgroundAmountTextFieldItem(
                title = PresentationDataModel(stringValue = stringResource(R.string.operationAmount)),
                onTextChange = { operationAmount ->
                    actionDispatcher.dispatchAction(
                        AppActions.AddOperationPopUpAction.FillOperationAmount(
                            operationAmount
                        )
                    )
                },
                amountValue = addOperationPopUpUiState.operationAmount,
                isPopUpExpanded = addOperationPopUpUiState.isPopUpExpanded,
                isError = addOperationPopUpUiState.isOperationAmountError,
                errorMsg = PresentationDataModel("Please, fill an amount for this operation")
            )
            //category drop down -----------------------------------
            PAMBaseDropDownMenuWithBackground(
                selectedItem = addOperationPopUpUiState.selectedCategory,
                itemList = addOperationPopUpUiState.allCategories,
                onItemSelected = { category ->
                    actionDispatcher.dispatchAction(
                        AppActions.AddOperationPopUpAction.SelectCategory(
                            category
                        )
                    )
                }
            )

            //Payment Operation option--------------------------
            PAMPunctualOrRecurrentSwitchButton(
                isRecurrentOptionExpanded = addOperationPopUpUiState.isRecurrentOptionExpanded,
                isPaymentOptionExpanded = addOperationPopUpUiState.isPaymentExpanded,
                onPunctualButtonSelected = {
                    actionDispatcher
                        .dispatchAction(AppActions.AddOperationPopUpAction.CloseRecurrentOption)
                },
                onRecurrentButtonSelected = {
                    actionDispatcher
                        .dispatchAction(AppActions.AddOperationPopUpAction.ExpandRecurrentOption)
                },
                onMonthSelected = { endDateMonth ->
                    actionDispatcher.dispatchAction(
                        AppActions.AddOperationPopUpAction.SelectEndDateMonth(
                            endDateMonth
                        )
                    )
                },
                onYearSelected = { endDateYear ->
                    actionDispatcher.dispatchAction(
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
            PAMTransferOptionPanel(
                isTransferOptionExpanded = addOperationPopUpUiState.isTransferExpanded,
                senderAccountSelectedItem = addOperationPopUpUiState.senderAccount,
                allAccountsList = addOperationPopUpUiState.allAccounts,
                beneficiaryAccountSelectedItem = addOperationPopUpUiState.beneficiaryAccount,
                onSenderAccountSelected = { senderAccount ->
                    actionDispatcher.dispatchAction(
                        AppActions.AddOperationPopUpAction.SelectSenderAccount(
                            senderAccount
                        )
                    )
                },
                onBeneficiaryAccountSelected = { beneficiaryAccount ->
                    actionDispatcher.dispatchAction(
                        AppActions.AddOperationPopUpAction.SelectBeneficiaryAccount(
                            beneficiaryAccount
                        )
                    )
                },
                isSenderAccountError = addOperationPopUpUiState.isSenderAccountError,
                isBeneficiaryAccountError = addOperationPopUpUiState.isBeneficiaryAccountError
            )
        }
    }
}

