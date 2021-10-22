package com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent

import android.util.Log
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
import com.piconemarc.model.entity.PresentationDataModel
import com.piconemarc.personalaccountmanager.R
import com.piconemarc.personalaccountmanager.ui.animation.*
import com.piconemarc.personalaccountmanager.ui.theme.*
import com.piconemarc.viewmodel.PAMIconButtons

@Composable
fun PAMBasePopUp(
    title: PresentationDataModel,
    onAcceptButtonClicked: () -> Unit,
    onDismiss: () -> Unit,
    isExpanded: Boolean,
    popUpBackgroundColor : Color = MaterialTheme.colors.secondary,
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
    deletePopUpTitle: PresentationDataModel,
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
    title: PresentationDataModel,
    onTextChange: (text: PresentationDataModel) -> Unit,
    textValue: PresentationDataModel,
    isPopUpExpanded : Boolean,
    isError : Boolean,
    errorMsg : PresentationDataModel
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
            value = textValue.stringValue,
            onValueChange = { onTextChange(textValue.copy(stringValue = it)) },
            textStyle = MaterialTheme.typography.body1.copy(
                color = MaterialTheme.colors.onPrimary
            ),
            label = { Text(text = title.stringValue) },
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
        TextFieldErrorMessage(isError, errorMsg)

    }
}

@Composable
private fun TextFieldErrorMessage(
    isError: Boolean,
    errorMsg: PresentationDataModel
) {
    if (isError) {
        Text(
            text = errorMsg.stringValue,
            color = MaterialTheme.colors.error,
            style = MaterialTheme.typography.caption,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

@Composable
fun PAMBrownBackgroundAmountTextFieldItem(
    title: PresentationDataModel,
    onTextChange: (text: PresentationDataModel) -> Unit,
    amountValue: PresentationDataModel,
    isPopUpExpanded : Boolean,
    isError: Boolean,
    errorMsg: PresentationDataModel
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
            value = amountValue.stringValue,
            onValueChange = { onTextChange(amountValue.copy(stringValue = it)) },
            textStyle = MaterialTheme.typography.body1,
            label = { Text(text = title.stringValue)  },
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
        TextFieldErrorMessage(isError = isError, errorMsg = errorMsg)
    }
}

@Composable
fun PAMPunctualOrRecurrentSwitchButton(
    isRecurrentOptionExpanded: Boolean,
    isPaymentOptionExpanded: Boolean,
    onPunctualButtonSelected : ()-> Unit,
    onRecurrentButtonSelected : ()-> Unit,
    endDateSelectedMonth : PresentationDataModel,
    endDateSelectedYear : PresentationDataModel,
    onMonthSelected: (month: PresentationDataModel) -> Unit,
    onYearSelected: (year: PresentationDataModel) -> Unit,
    selectableMonthList : List<PresentationDataModel>,
    selectableYearList : List<PresentationDataModel>

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
            selectableYearList = selectableYearList
        )
    }
}

@Composable
fun PAMRecurrentOptionPanel(
    modifier: Modifier,
    onMonthSelected: (month: PresentationDataModel) -> Unit,
    onYearSelected: (year: PresentationDataModel) -> Unit,
    selectedMonth: PresentationDataModel,
    selectedYear: PresentationDataModel,
    selectableMonthList : List<PresentationDataModel>,
    selectableYearList : List<PresentationDataModel>
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
    }
}

@Composable
fun PAMTransferOptionPanel(
    isTransferOptionExpanded : Boolean,
    senderAccountSelectedItem : PresentationDataModel,
    allAccountsList : List<PresentationDataModel>,
    beneficiaryAccountSelectedItem : PresentationDataModel,
    onSenderAccountSelected : (senderAccount : PresentationDataModel) -> Unit,
    onBeneficiaryAccountSelected : ( beneficiaryAccount : PresentationDataModel) -> Unit


) {
    Column(
        modifier = Modifier.height(
            pAMExpandCollapseTransferPanelAnimation(
                isTransferOptionExpanded = isTransferOptionExpanded
            ).value
        )
    ) {
        PAMBaseDropDownMenuWithBackground(
            selectedItem = senderAccountSelectedItem,
            itemList = allAccountsList,
            onItemSelected = onSenderAccountSelected
        )

        PAMBaseDropDownMenuWithBackground(
            selectedItem = beneficiaryAccountSelectedItem ,
            itemList = allAccountsList,
            onItemSelected = onBeneficiaryAccountSelected
        )
    }
}

@Composable
fun PopUpTitle(title: PresentationDataModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colors.primaryVariant,
                shape = MaterialTheme.shapes.large.copy(topStart = CornerSize(0.dp))
            )
    ) {
        Text(
            text = title.stringValue,
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