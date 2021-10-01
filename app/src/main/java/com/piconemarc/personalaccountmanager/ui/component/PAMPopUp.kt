package com.piconemarc.personalaccountmanager.ui.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.runtime.getValue
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
import com.piconemarc.personalaccountmanager.R
import com.piconemarc.personalaccountmanager.ui.animation.*
import com.piconemarc.personalaccountmanager.ui.theme.*
import com.piconemarc.viewmodel.viewModel.addOperationPopUp.AddOperationPopUpScreenModel
import com.piconemarc.viewmodel.viewModel.addOperationPopUp.AddOperationPopUpState

@Composable
fun PAMBasePopUp(
    title: String,
    onAcceptButtonClicked: () -> Unit,
    onDismiss: () -> Unit,
    isExpanded: Boolean,
    menuIconPanel : @Composable () -> Unit = {},
    body: @Composable () -> Unit
) {
    val transition = pAMBasePopUpEnterExitAnimation(isExpended = isExpanded)
    if (transition.alpha > 0f)
        Column(
            modifier = Modifier
                .background(Black.copy(alpha = transition.alpha))
                .fillMaxSize()
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
                    backgroundColor = MaterialTheme.colors.secondary,
                    shape = MaterialTheme.shapes.large.copy(topStart = CornerSize(0.dp)),
                    border = BorderStroke(LittleMarge, MaterialTheme.colors.primaryVariant),
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
    elementToDelete: String,
    onAcceptButtonClicked: () -> Unit,
    onDismiss: () -> Unit,
    isExpanded: Boolean,
    body: @Composable () -> Unit
) {
    PAMBasePopUp(
        title = stringResource(R.string.deleteBaseMsg) + elementToDelete,
        onAcceptButtonClicked = onAcceptButtonClicked,
        onDismiss = onDismiss,
        body = body,
        isExpanded = isExpanded
    )
}

@Composable
fun PAMAddOperationPopUpLeftSideMenuIconPanel(screenModel : AddOperationPopUpScreenModel) {
    Box {
        Box(
            modifier = Modifier
                .offset(
                    y = pAMAddOperationPopUpIconMenuPanelSelectorAnimation(
                        addOperationPopUpMenuIconState = screenModel.getState().menuIconPopUpState
                    ).offset.y.dp
                )
                .background(
                    color = MaterialTheme.colors.primaryVariant,
                    shape = RoundedCornerShape(topStart = BigMarge, bottomStart = BigMarge)
                )
                .height(48.dp)
                .width(48.dp)
        )
        Column {
            PAMIconButton(
                iconButton = PAMIconButtons.Operation,
                onIconButtonClicked = { screenModel.closeOption() }
            )
            Spacer(modifier = Modifier.height(RegularMarge))
            PAMIconButton(
                iconButton = PAMIconButtons.Payment,
                onIconButtonClicked = { screenModel.openPaymentOption() }
            )
            Spacer(modifier = Modifier.height(RegularMarge))
            PAMIconButton(
                iconButton = PAMIconButtons.Transfer,
                onIconButtonClicked = { screenModel.openTransferOption() }
            )
        }
    }
}

@Composable
fun PAMBlackBackgroundTextFieldItem(
    title: String,
    onTextChange: (text: String) -> Unit,
    textValue :String
) {
    val focusManager = LocalFocusManager.current
    if (AddOperationPopUpScreenModel().getState() == AddOperationPopUpState.Idle)focusManager.clearFocus()
    Column(modifier = Modifier.popUpClickableItemModifier()) {
        TextField(
            value = textValue,
            onValueChange =  onTextChange,
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
            keyboardActions = KeyboardActions (onNext = { focusManager.moveFocus(FocusDirection.Down)
            } )
        )

    }
}

@Composable
fun PAMAmountTextFieldItem(
    title: String,
    onTextChange: (text: String) -> Unit,
    amountValue : String,
    screenModel: AddOperationPopUpScreenModel
) {
    val focusManager = LocalFocusManager.current
    val transition = pAMAmountTextFieldAnimation(amountValue)
    if (!screenModel.getState().isExpanded)focusManager.clearFocus()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = RegularMarge, bottom = RegularMarge, end = RegularMarge)
            .background(
                color = transition.backgroundColor,
                shape = PopUpFieldBackgroundShape
            )
    ) {
        TextField(
            value = amountValue,
            onValueChange = onTextChange,
            textStyle = MaterialTheme.typography.body1,
            label = { Text(text = title) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            colors = TextFieldDefaults.textFieldColors(
                focusedLabelColor = transition.textColor,
                unfocusedLabelColor = transition.textColor,
                backgroundColor = Color.Transparent,
                textColor = transition.textColor
            ),
            keyboardActions = KeyboardActions(onDone = {focusManager.clearFocus()})
        )
    }
}

@Composable
fun PAMPunctualOrRecurrentSwitchButton(
    screenModel: AddOperationPopUpScreenModel
) {
    val transition = pAMRecurrentOptionButtonAnimation(screenModel)

    Column(
        modifier =
        Modifier
            .height(
                pAMExpandCollapsePaymentAnimation(screenModel).value
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
                onButtonSelected = { screenModel.selectPunctualOption() },
                isSelected = screenModel.getState().recurrentSwitchButtonState == AddOperationPopUpState.RecurrentSwitchButtonState.Punctual,
                title = stringResource(R.string.punctualSwitchButton),
                switchShape = LeftSwitchShape
            )
            SwitchButton(
                onButtonSelected = { screenModel.selectRecurrentOption() },
                isSelected = screenModel.getState().recurrentSwitchButtonState == AddOperationPopUpState.RecurrentSwitchButtonState.Recurrent,
                title = stringResource(R.string.recurrentSwitchButton),
                switchShape = RightSwitchShape.copy(bottomStart = CornerSize(transition.leftBottomCornerSize)),
                bottomPadding = transition.buttonSize
            )
        }
        //Recurrent options
        PAMRecurrentOptionPanel(
            modifier = Modifier.height(pAMExpandCollapseEndDatePanel(isSelected = screenModel.getState().recurrentSwitchButtonState == AddOperationPopUpState.RecurrentSwitchButtonState.Recurrent).value),
            onMonthSelected = {screenModel.selectEndDateMonth(it)},
            onYearSelected = {screenModel.selectEndDateYear(it)},
            selectedMonth = screenModel.getState().endDateMonth,
            selectedYear = screenModel.getState().endDateYear,
        )
    }
}

@Composable
fun PAMRecurrentOptionPanel(
    modifier: Modifier,
    onMonthSelected: (month: String) -> Unit,
    onYearSelected: (year: String) -> Unit,
    selectedMonth : String,
    selectedYear : String
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
                itemList = mutableListOf("Janvier", "Fevrier"),
                onItemSelected = { month -> onMonthSelected(month) })
            PAMBaseDropDownMenu(
                selectedItem = selectedYear,
                itemList = mutableListOf("2000", "2001"),
                onItemSelected = { year -> onYearSelected(year) })
        }
    }
}

@Composable
fun PAMTransferOptionPanel(screenModel: AddOperationPopUpScreenModel) {
    Column(modifier = Modifier.height(pAMExpandCollapseTransferPanelAnimation().value)) {
        PAMBaseDropDownMenuWithBackground(
            selectedItem = screenModel.getState().senderAccount,
            itemList = listOf("1","2"),
            onItemSelected = {screenModel.selectSenderAccount(it)}
        )

        PAMBaseDropDownMenuWithBackground(
            selectedItem = screenModel.getState().beneficiaryAccount,
            itemList = listOf("1","2"),
            onItemSelected = {screenModel.selectBeneficiaryAccount(it)}
        )
    }
}

@Composable
fun PopUpTitle(title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colors.primary, shape = MaterialTheme.shapes.large)
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
    val buttonColor by animateColorAsState(
        targetValue = if (isSelected) MaterialTheme.colors.primary else MaterialTheme.colors.primaryVariant,
        animationSpec = if (!isSelected) tween(delayMillis = 120) else tween(delayMillis = 0)
    )
    val textColor by animateColorAsState(
        targetValue = if (isSelected) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onSecondary,
        animationSpec = if (!isSelected) tween(delayMillis = 120) else tween(delayMillis = 0)
    )
    Button(
        modifier = Modifier
            .wrapContentSize()
            .background(
                color = buttonColor,
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
            color = textColor
        )
    }
}

