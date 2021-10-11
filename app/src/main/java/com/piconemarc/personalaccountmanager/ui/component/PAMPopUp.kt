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
import com.piconemarc.model.entity.DataUiModel
import com.piconemarc.personalaccountmanager.R
import com.piconemarc.personalaccountmanager.ui.animation.*
import com.piconemarc.personalaccountmanager.ui.theme.*
import com.piconemarc.viewmodel.viewModel.AddOperationPopUpEvent
import com.piconemarc.viewmodel.viewModel.AddOperationPopUpState
import com.piconemarc.viewmodel.viewModel.AccountDetailViewModel

@Composable
fun PAMBasePopUp(
    title: String,
    onAcceptButtonClicked: () -> Unit,
    onDismiss: () -> Unit,
    isExpanded: Boolean,
    menuIconPanel: @Composable () -> Unit = {},
    body: @Composable () -> Unit
) {
    val transition = pAMBasePopUpEnterExitAnimation(isExpended = isExpanded)
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
fun PAMAddOperationPopUpLeftSideMenuIconPanel(
    isTransferOptionExpanded: Boolean,
    isPaymentOptionExpanded: Boolean,
    operationDetailViewModel: AccountDetailViewModel
) {
    Box {
        Box(
            modifier = Modifier
                .offset(
                    y = pAMAddOperationPopUpIconMenuPanelSelectorAnimation(
                        Pair(isPaymentOptionExpanded, isTransferOptionExpanded)
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
                onIconButtonClicked = {
                    operationDetailViewModel.dispatchEvent(
                        AddOperationPopUpEvent.CollapseOptions
                    )
                }
            )
            Spacer(modifier = Modifier.height(RegularMarge))
            PAMIconButton(
                iconButton = PAMIconButtons.Payment,
                onIconButtonClicked = {
                    operationDetailViewModel.dispatchEvent(
                        AddOperationPopUpEvent.ExpandPaymentOptions
                    )
                }
            )
            Spacer(modifier = Modifier.height(RegularMarge))
            PAMIconButton(
                iconButton = PAMIconButtons.Transfer,
                onIconButtonClicked = {
                    operationDetailViewModel.dispatchEvent(
                        AddOperationPopUpEvent.ExpandTransferOptions
                    )
                }
            )
        }
    }
}

@Composable
fun PAMBlackBackgroundTextFieldItem(
    title: String,
    onTextChange: (text: String) -> Unit,
    textValue: String
) {
    val focusManager = LocalFocusManager.current
    if (AddOperationPopUpState.isPopUpExpanded) focusManager.clearFocus()
    Column(modifier = Modifier.popUpClickableItemModifier()) {
        TextField(
            value = textValue,
            onValueChange = onTextChange,
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
            keyboardActions = KeyboardActions(onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            })
        )

    }
}

@Composable
fun PAMAmountTextFieldItem(
    title: String,
    onTextChange: (text: String) -> Unit,
    amountValue: String,
) {
    val focusManager = LocalFocusManager.current
    val transition = pAMAmountTextFieldAnimation(amountValue)
    if (!AddOperationPopUpState.isPopUpExpanded) focusManager.clearFocus()
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
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
        )
    }
}

@Composable
fun PAMPunctualOrRecurrentSwitchButton(
    isRecurrentOptionExpanded: Boolean,
    isPaymentOptionExpanded: Boolean,
    operationDetailViewModel: AccountDetailViewModel
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
                onButtonSelected = { operationDetailViewModel.dispatchEvent(AddOperationPopUpEvent.CollapseRecurrentOptions) },
                isSelected = !isRecurrentOptionExpanded,
                title = stringResource(R.string.punctualSwitchButton),
                switchShape = LeftSwitchShape
            )
            SwitchButton(
                onButtonSelected = {  operationDetailViewModel.dispatchEvent(AddOperationPopUpEvent.ExpandRecurrentOptions) },
                isSelected = isRecurrentOptionExpanded,
                title = stringResource(R.string.recurrentSwitchButton),
                switchShape = RightSwitchShape.copy(bottomStart = CornerSize(transition.leftBottomCornerSize)),
                bottomPadding = transition.buttonSize
            )
        }
        //Recurrent options
        PAMRecurrentOptionPanel(
            modifier = Modifier.height(pAMExpandCollapseEndDatePanel(isSelected = isRecurrentOptionExpanded).value),
            onMonthSelected = {},
            onYearSelected = {},
            selectedMonth = "",
            selectedYear = "",
        )
    }
}

@Composable
fun PAMRecurrentOptionPanel(
    modifier: Modifier,
    onMonthSelected: (month: String) -> Unit,
    onYearSelected: (year: String) -> Unit,
    selectedMonth: String,
    selectedYear: String
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
                selectedItem = DataUiModel( selectedMonth, 0),
                itemList = mutableListOf(),
                onItemSelected = { month -> onMonthSelected(month.stringValue) })
            PAMBaseDropDownMenu(
                selectedItem = DataUiModel(selectedYear, 0 ),
                itemList = mutableListOf(),
                onItemSelected = { year -> onYearSelected(year.stringValue) })
        }
    }
}

@Composable
fun PAMTransferOptionPanel(
    accountDetailViewModel : AccountDetailViewModel
    ) {
    Column(
        modifier = Modifier.height(
            pAMExpandCollapseTransferPanelAnimation(
                isTransferOptionExpanded = AddOperationPopUpState.isTransferExpanded
            ).value
        )
    ) {
        PAMBaseDropDownMenuWithBackground(
            selectedItem = AddOperationPopUpState.senderAccount,
            itemList = AddOperationPopUpState.accountList,
            onItemSelected = {}
        )

        PAMBaseDropDownMenuWithBackground(
            selectedItem = AddOperationPopUpState.beneficiaryAccount,
            itemList = AddOperationPopUpState.accountList,
            onItemSelected = {}
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
