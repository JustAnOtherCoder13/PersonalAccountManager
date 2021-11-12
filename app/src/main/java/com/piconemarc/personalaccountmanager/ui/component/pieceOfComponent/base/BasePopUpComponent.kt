package com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.base

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.piconemarc.personalaccountmanager.ui.animation.pAMBasePopUpEnterExitAnimation
import com.piconemarc.personalaccountmanager.ui.theme.*

@Composable
fun BasePopUp(
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
                        BasePopUpTitle(title)
                        body()
                        AcceptOrDismissButtons(
                            onAcceptButtonClicked = onAcceptButtonClicked,
                            onDismissButtonClicked = onDismiss
                        )
                    }
                }
            }
        }
}

@Composable
fun BaseDeletePopUp(
    deletePopUpTitle: String,
    onAcceptButtonClicked: () -> Unit,
    onDismiss: () -> Unit,
    isExpanded: Boolean,
    body: @Composable () -> Unit
) {
    BasePopUp(
        title = deletePopUpTitle,
        onAcceptButtonClicked = onAcceptButtonClicked,
        onDismiss = onDismiss,
        body = body,
        isExpanded = isExpanded
    )
}

@Composable
fun BrownBackgroundTextFieldItem(
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
            modifier = Modifier
                .padding(start = 16.dp)
                .wrapContentHeight()
        )
    }
}

@Composable
fun BrownBackgroundAmountTextFieldItem(
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
fun BasePopUpTitle(title: String) {
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