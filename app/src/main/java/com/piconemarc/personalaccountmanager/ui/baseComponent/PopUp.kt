package com.piconemarc.personalaccountmanager.ui.baseComponent

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.piconemarc.personalaccountmanager.R
import com.piconemarc.personalaccountmanager.ui.theme.Black
import com.piconemarc.personalaccountmanager.ui.theme.Positive
import com.piconemarc.personalaccountmanager.ui.theme.RegularMarge
import com.piconemarc.personalaccountmanager.ui.theme.deleteOperationTextModifier


@Composable
fun DeleteOperationPopUp(
    showDeleteOperationPopUp: Boolean,
    operationName: String,
    operationAmount: Double,
    onDeleteOperation: () -> Unit,
    onDismiss: () -> Unit
) {

    if (showDeleteOperationPopUp)
        BaseDeletePopUp(
            elementToDelete = stringResource(R.string.operation),
            onAcceptButtonClicked = {
                onDismiss()
                onDeleteOperation()
            },
            onCancelButtonClicked = {
                onDismiss()
            },
            body = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = operationName,
                        modifier = Modifier.deleteOperationTextModifier()
                    )
                    Text(
                        modifier = Modifier.deleteOperationTextModifier(),
                        text = operationAmount.toString(),
                        color = if (operationAmount < 0) MaterialTheme.colors.error else Positive
                    )
                }
            }
        )

}


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AddOperationPopUp(
    showAddOperationPopUp: Boolean,
    onAddOperation: () -> Unit,
    onDismiss: () -> Unit
) {
    val operationTitle = stringResource(id = R.string.operation)
    var popUpTitle: String by remember { mutableStateOf(operationTitle) }

    AnimatedVisibility(
        visible = showAddOperationPopUp,
        enter = fadeIn(
            animationSpec = tween(
                durationMillis = 700
            )
        ),
        exit = fadeOut(
            animationSpec = tween(
                durationMillis = 700
            )
        )
    ) {
        Column(
            modifier = Modifier
                .background(Black.copy(alpha = 0.7f))
                .fillMaxSize()
        ) {

            Row(
                modifier = Modifier
                    .animateEnterExit(
                        enter = expandVertically(
                            animationSpec = tween(delayMillis = 200)
                        ),
                        exit = shrinkVertically(),
                    )
                    .padding(horizontal = RegularMarge, vertical = RegularMarge)
            ) {
                //Left menu-----------------------------------------------------
                OperationPopUpLeftSideIcon(
                    onIconButtonClicked = { popUpTitle_ ->
                        popUpTitle = popUpTitle_
                    }
                )
                //base operation --------------------------------------------
                LazyColumn {
                    item {
                        BasePopUp(
                            title = popUpTitle,
                            onAcceptButtonClicked = {
                                onDismiss()
                                onAddOperation()
                                popUpTitle = operationTitle
                            },
                            onCancelButtonClicked = {
                                onDismiss()
                                popUpTitle = operationTitle
                            }
                        ) {
                            //category drop down -----------------------------------
                            BaseDropDownMenuWithBackGround(
                                hint = stringResource(R.string.category),
                                itemList = testList,
                                onItemSelected = { item ->

                                }
                            )
                            // operation and amount text field--------------------------
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                BasePopUpTextFieldItem(
                                    title = stringResource(R.string.operationName),
                                    onTextChange = { operationName ->

                                    }
                                )
                                BasePopUpAmountTextFieldItem(
                                    title = stringResource(R.string.operationAmount),
                                    onTextChange = { amount ->

                                    },
                                )
                                //Payment Operation option--------------------------
                                PunctualOrRecurrentSwitchButton(
                                    onEndDateSelected = { month_year ->

                                    },
                                    isVisible = popUpTitle != stringResource(R.string.operation)
                                )

                                //Transfer Operation option---------------------------
                                androidx.compose.animation.AnimatedVisibility(
                                    visible = popUpTitle == stringResource(R.string.transfer),
                                    enter = expandVertically(expandFrom = Alignment.Top),
                                    exit = shrinkVertically(shrinkTowards = Alignment.Top),
                                    modifier = Modifier.expandablePopUpOptionAnimation()
                                ) {
                                    Column {
                                        BaseDropDownMenuWithBackGround(
                                            hint = stringResource(R.string.senderAccount),
                                            itemList = testList,
                                            onItemSelected = { item ->

                                            }
                                        )
                                        BaseDropDownMenuWithBackGround(
                                            hint = stringResource(R.string.beneficiaryAccount),
                                            itemList = testList,
                                            onItemSelected = { item ->

                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun PopUpPreview() {
    Column {
        AddOperationPopUp(
            showAddOperationPopUp = true,
            onDismiss = {},
            onAddOperation = {}
        )
    }
}