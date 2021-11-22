package com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.piconemarc.model.PAMIconButtons
import com.piconemarc.model.entity.AccountUiModel
import com.piconemarc.personalaccountmanager.R
import com.piconemarc.personalaccountmanager.ui.animation.*
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.base.BaseIconButton
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.base.ErrorMessage
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.base.PAMBaseDropDownMenu
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.base.PAMBaseDropDownMenuWithBackground
import com.piconemarc.personalaccountmanager.ui.theme.*

@Composable
fun AddOperationPopUpLeftSideMenuIconPanel(
    onIconButtonClicked : (iconButton : PAMIconButtons)-> Unit,
    selectedIcon : PAMIconButtons
) {
    Box {
        Box(
            modifier = Modifier
                .offset(
                    y = pAMAddOperationPopUpIconMenuPanelSelectorAnimation(
                        selectedIcon
                    ).offset.y.dp
                )
                .background(
                    color = BrownLight,
                    shape = RoundedCornerShape(topStart = BigMarge, bottomStart = BigMarge)
                )
                .height(AddPopUpSelectorSize)
                .width(AddPopUpSelectorSize)
        )
        Column(
            modifier = Modifier
                .width(AddPopUpSelectorSize - 5.dp)
                .padding(start = 5.dp)

        ) {
            BaseIconButton(
                iconButton = PAMIconButtons.Operation,
                onIconButtonClicked = onIconButtonClicked
            )
            Spacer(modifier = Modifier.height(RegularMarge))
            BaseIconButton(
                iconButton = PAMIconButtons.Payment,
                onIconButtonClicked = onIconButtonClicked
            )
            Spacer(modifier = Modifier.height(RegularMarge))
            BaseIconButton(
                iconButton = PAMIconButtons.Transfer,
                onIconButtonClicked = onIconButtonClicked
            )
        }
    }
}

@Composable
fun AddOperationPopUpPunctualOrRecurrentSwitchButton(
    isRecurrentOptionExpanded: Boolean,
    isPaymentOptionExpanded: Boolean,
    onPunctualButtonSelected : ()-> Unit,
    onRecurrentButtonSelected : ()-> Unit,
    endDateSelectedMonth : String,
    endDateSelectedYear : String,
    onMonthSelected: (month: String) -> Unit,
    onYearSelected: (year: String) -> Unit,
    selectableMonthList : List<String>,
    selectableYearList : List<String>,
    isRecurrentSwitchError: Boolean
) {
    val transition = pAMRecurrentOptionButtonAnimation(isRecurrentOptionExpanded)
    Column(
        modifier =
        Modifier
            .height(
                pAMExpandCollapsePaymentAnimation(
                    isPaymentOptionExpanded = isPaymentOptionExpanded,
                    isRecurrentOptionExpanded = isRecurrentOptionExpanded
                ).value
            )

            .padding(bottom = RegularMarge)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = RegularMarge)
        ) {
            AddOperationPopUpSwitchButton(
                onButtonSelected = onPunctualButtonSelected,
                isSelected = !isRecurrentOptionExpanded,
                title = stringResource(R.string.punctualSwitchButton),
                switchShape = LeftSwitchShape
            )
            AddOperationPopUpSwitchButton(
                onButtonSelected = onRecurrentButtonSelected,
                isSelected = isRecurrentOptionExpanded,
                title = stringResource(R.string.recurrentSwitchButton),
                switchShape = RightSwitchShape.copy(bottomStart = CornerSize(transition.leftBottomCornerSize)),
                bottomPadding = transition.buttonSize
            )
        }
        //Recurrent options
        AddOperationPopUpRecurrentOptionPanel(
            modifier = Modifier.height(pAMExpandCollapseEndDatePanel(isRecurrentOptionSelected = isRecurrentOptionExpanded).value),
            onMonthSelected = onMonthSelected,
            onYearSelected = onYearSelected,
            selectedMonth = endDateSelectedMonth,
            selectedYear = endDateSelectedYear,
            selectableMonthList = selectableMonthList,
            selectableYearList = selectableYearList,
            isRecurrentSwitchError = isRecurrentSwitchError
        )
    }
}

@Composable
private fun AddOperationPopUpRecurrentOptionPanel(
    modifier: Modifier,
    onMonthSelected: (month: String) -> Unit,
    onYearSelected: (year: String) -> Unit,
    selectedMonth: String,
    selectedYear: String,
    selectableMonthList : List<String>,
    selectableYearList : List<String>,
    isRecurrentSwitchError : Boolean
) {
    Column(
        modifier = modifier
            .background(
                color = MaterialTheme.colors.primary,
                shape = RecurrentOptionPanelShape
            )
    ) {
        Text(
            text = stringResource(R.string.endDate),
            style = MaterialTheme.typography.h3,
            color = MaterialTheme.colors.onPrimary,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = RegularMarge),
            textAlign = TextAlign.Center
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = BigMarge),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            PAMBaseDropDownMenu(
                selectedItem = selectedMonth,
                itemList = selectableMonthList,
                onItemSelected = { month -> onMonthSelected(month) })
            PAMBaseDropDownMenu(
                selectedItem = selectedYear,
                itemList = selectableYearList,
                onItemSelected = { year -> onYearSelected(year) })
        }
        ErrorMessage(
            isError = isRecurrentSwitchError,
            errorMsg = stringResource(R.string.addPopUpRecurenceErrorMessage)
        )
    }
}

@Composable
fun AddOperationPopUpTransferOptionPanel(
    isTransferOptionExpanded : Boolean,
    senderAccount : AccountUiModel,
    allAccountsList : List<AccountUiModel>,
    beneficiaryAccountUiSelectedItem : AccountUiModel,
    onBeneficiaryAccountSelected : (beneficiaryAccountUi : AccountUiModel) -> Unit,
    isBeneficiaryAccountError : Boolean
) {
    Column(
        modifier = Modifier.height(
            pAMExpandCollapseTransferPanelAnimation(
                isTransferOptionExpanded = isTransferOptionExpanded
            ).value
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = RegularMarge, bottom = RegularMarge, end = RegularMarge)
                .background(
                    color = MaterialTheme.colors.primary,
                    shape = PopUpFieldBackgroundShape
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(top = RegularMarge, bottom = RegularMarge)
                    .border(
                        width = ThinBorder,
                        color = MaterialTheme.colors.onPrimary,
                        shape = RoundedCornerShape(RegularMarge)
                    )
            ) {
                Text(
                    text = senderAccount.name,
                    color = MaterialTheme.colors.onPrimary,
                    modifier = Modifier.padding(start = RegularMarge, end = RegularMarge),
                    style = MaterialTheme.typography.h3
                )
            }
        }
        PAMBaseDropDownMenuWithBackground(
            selectedItem = beneficiaryAccountUiSelectedItem ,
            itemList = allAccountsList,
            onItemSelected = onBeneficiaryAccountSelected,
            isError = isBeneficiaryAccountError,
            errorMessage = stringResource(R.string.AddOperationPopUpBeneficiaryAccountErrorMessage)
        )
    }
}

@Composable
private fun AddOperationPopUpSwitchButton(
    onButtonSelected: () -> Unit,
    isSelected: Boolean,
    title: String,
    switchShape: Shape,
    bottomPadding: Dp = RegularMarge
) {
    val transition = pAMBaseSwitchButtonTransition(isSelected = isSelected)

    Button(
        modifier = Modifier
            .wrapContentSize()
            .background(
                color = transition.buttonColor,
                shape = switchShape
            )
            .padding(end = RegularMarge),
        onClick = onButtonSelected,
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
        elevation = ButtonDefaults.elevation(0.dp)

    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.h3,
            modifier = Modifier.padding(
                top = RegularMarge,
                bottom = bottomPadding,
                end = RegularMarge
            ),
            color = transition.textColor
        )
    }
}