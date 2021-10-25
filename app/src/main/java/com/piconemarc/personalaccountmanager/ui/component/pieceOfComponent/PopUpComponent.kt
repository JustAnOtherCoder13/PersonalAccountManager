package com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.piconemarc.model.PAMIconButtons
import com.piconemarc.model.entity.AccountUiModel
import com.piconemarc.personalaccountmanager.R
import com.piconemarc.personalaccountmanager.ui.animation.*
import com.piconemarc.personalaccountmanager.ui.theme.*

@Composable
fun PAMBasePopUp(
    title: String,
    onAcceptButtonClicked: () -> Unit,
    onDismiss: () -> Unit,
    isExpanded: Boolean,
    popUpBackgroundColor : Color = MaterialTheme.colors.secondary,
    menuIconPanel: @Composable () -> Unit = {},
    body: @Composable () -> Unit
) {
    val transition = pAMBasePopUpEnterExitAnimation(isExpanded = isExpanded)
    if (transition.alpha > 0f)
        Column(
            modifier = Modifier
                .background(Black.copy(alpha = transition.alpha))
                .fillMaxHeight()
                .clickable { onDismiss() }

        ) {
            Row(
                modifier = Modifier
                    .height(transition.size)
                    .offset(y = transition.position)
                    .padding(horizontal = RegularMarge, vertical = RegularMarge)
            ) {
                menuIconPanel()
                Card(
                    elevation = BigMarge,
                    backgroundColor = popUpBackgroundColor,
                    shape = MaterialTheme.shapes.large.copy(topStart = CornerSize(0.dp)),
                    border = BorderStroke(LittleMarge, BrownDark_300),
                    modifier = Modifier.clickable {
                        //to disable dismiss when click on card
                    }
                ) {
                    Column {
                        PopUpTitle(title)
                        body()
                        PAMAcceptOrDismissButtons(
                            onAcceptButtonClicked = onAcceptButtonClicked,
                            onDismissButtonClicked = onDismiss
                        )
                    }
                }
            }
        }
}

@Composable
fun PAMBaseDeletePopUp(
    deletePopUpTitle: String,
    onAcceptButtonClicked: () -> Unit,
    onDismiss: () -> Unit,
    isExpanded: Boolean,
    body: @Composable () -> Unit
) {
    PAMBasePopUp(
        title = deletePopUpTitle,
        onAcceptButtonClicked = onAcceptButtonClicked,
        onDismiss = onDismiss,
        body = body,
        isExpanded = isExpanded
    )
}

@Composable
fun PAMAddOperationPopUpLeftSideMenuIconPanel(
    onIconButtonClicked : (iconButton : PAMIconButtons)-> Unit,
    selectedIcon : PAMIconButtons
) {
    Box() {
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
            PAMIconButton(
                iconButton = PAMIconButtons.Operation,
                onIconButtonClicked = onIconButtonClicked
            )
            Spacer(modifier = Modifier.height(RegularMarge))
            PAMIconButton(
                iconButton = PAMIconButtons.Payment,
                onIconButtonClicked = onIconButtonClicked
            )
            Spacer(modifier = Modifier.height(RegularMarge))
            PAMIconButton(
                iconButton = PAMIconButtons.Transfer,
                onIconButtonClicked = onIconButtonClicked
            )
        }
    }
}

@Composable
fun PAMBrownBackgroundTextFieldItem(
    title: String,
    onTextChange: (text: String) -> Unit,
    textValue: String,
    isPopUpExpanded : Boolean,
    isError : Boolean,
    errorMsg : String
) {
    val focusManager = LocalFocusManager.current
    if (!isPopUpExpanded) focusManager.clearFocus()
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(top = RegularMarge, bottom = RegularMarge, end = RegularMarge)
        .background(
            color = MaterialTheme.colors.primary,
            shape = PopUpFieldBackgroundShape
        )) {
        TextField(
            value = textValue,
            onValueChange = { onTextChange(it) },
            textStyle = MaterialTheme.typography.body1.copy(
                color = MaterialTheme.colors.onPrimary
            ),
            label = { Text(text = title) },
            colors = TextFieldDefaults.textFieldColors(
                focusedLabelColor = MaterialTheme.colors.onPrimary,
                unfocusedLabelColor = MaterialTheme.colors.onPrimary,
                cursorColor = MaterialTheme.colors.onPrimary,
                backgroundColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }
            ),
            isError = isError
        )
        ErrorMessage(isError, errorMsg)

    }
}

@Composable
fun ErrorMessage(
    isError: Boolean,
    errorMsg: String
) {
    if (isError) {
        Text(
            text = errorMsg,
            color = MaterialTheme.colors.error,
            style = MaterialTheme.typography.caption,
            modifier = Modifier.padding(start = 16.dp).wrapContentHeight()
        )
    }
}

@Composable
fun PAMBrownBackgroundAmountTextFieldItem(
    title: String,
    onTextChange: (text: String) -> Unit,
    amountValue: String,
    isPopUpExpanded : Boolean,
    isError: Boolean,
    errorMsg: String
) {
    val focusManager = LocalFocusManager.current
    if (!isPopUpExpanded) focusManager.clearFocus()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = RegularMarge, bottom = RegularMarge, end = RegularMarge)
            .background(
                color = MaterialTheme.colors.primary,
                shape = PopUpFieldBackgroundShape
            )
    ) {
        TextField(
            value = amountValue,
            onValueChange = { onTextChange(it) },
            textStyle = MaterialTheme.typography.body1,
            label = { Text(text = title)  },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            colors = TextFieldDefaults.textFieldColors(
                focusedLabelColor = MaterialTheme.colors.onPrimary,
                unfocusedLabelColor = MaterialTheme.colors.onPrimary,
                cursorColor = MaterialTheme.colors.onPrimary,
                backgroundColor = Color.Transparent,
                textColor = MaterialTheme.colors.onPrimary ),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            isError = isError
        )
        ErrorMessage(isError = isError, errorMsg = errorMsg)
    }
}

@Composable
fun PAMPunctualOrRecurrentSwitchButton(
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
            SwitchButton(
                onButtonSelected = onPunctualButtonSelected,
                isSelected = !isRecurrentOptionExpanded,
                title = stringResource(R.string.punctualSwitchButton),
                switchShape = LeftSwitchShape
            )
            SwitchButton(
                onButtonSelected = onRecurrentButtonSelected,
                isSelected = isRecurrentOptionExpanded,
                title = stringResource(R.string.recurrentSwitchButton),
                switchShape = RightSwitchShape.copy(bottomStart = CornerSize(transition.leftBottomCornerSize)),
                bottomPadding = transition.buttonSize
            )
        }
        //Recurrent options
        PAMRecurrentOptionPanel(
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
fun PAMRecurrentOptionPanel(
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
            errorMsg ="Please, choose end date or recurrence be forever"
        )
    }
}

@Composable
fun PAMTransferOptionPanel(
    isTransferOptionExpanded : Boolean,
    senderAccountUiSelectedItem : AccountUiModel,
    allAccountsList : List<AccountUiModel>,
    beneficiaryAccountUiSelectedItem : AccountUiModel,
    onSenderAccountSelected : (senderAccountUi : AccountUiModel) -> Unit,
    onBeneficiaryAccountSelected : (beneficiaryAccountUi : AccountUiModel) -> Unit,
    isSenderAccountError : Boolean,
    isBeneficiaryAccountError : Boolean
) {
    Column(
        modifier = Modifier.height(
            pAMExpandCollapseTransferPanelAnimation(
                isTransferOptionExpanded = isTransferOptionExpanded
            ).value
        )
    ) {
        PAMBaseDropDownMenuWithBackground(
            selectedItem = senderAccountUiSelectedItem,
            itemList = allAccountsList,
            onItemSelected = onSenderAccountSelected,
            isError = isSenderAccountError ,
            errorMessage = "Please select Sender account for transfer"
        )

        PAMBaseDropDownMenuWithBackground(
            selectedItem = beneficiaryAccountUiSelectedItem ,
            itemList = allAccountsList,
            onItemSelected = onBeneficiaryAccountSelected,
            isError = isBeneficiaryAccountError,
            errorMessage = "Please select Beneficiary account for transfer"
        )
    }
}

@Composable
fun PopUpTitle(title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colors.primaryVariant,
                shape = MaterialTheme.shapes.large.copy(topStart = CornerSize(0.dp))
            )
    ) {
        Text(
            text = title,
            color = MaterialTheme.colors.onPrimary,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = RegularMarge),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h2
        )
    }
}

@Composable
fun SwitchButton(
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