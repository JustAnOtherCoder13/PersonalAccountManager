package com.piconemarc.personalaccountmanager.ui.baseComponent.addOperationPopUp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.piconemarc.personalaccountmanager.R
import com.piconemarc.personalaccountmanager.ui.baseComponent.*
import com.piconemarc.personalaccountmanager.ui.theme.BigMarge
import com.piconemarc.personalaccountmanager.ui.theme.Black
import com.piconemarc.personalaccountmanager.ui.theme.RegularMarge

interface UiEvent

var isRecurrent: MutableState<Boolean> = mutableStateOf(false)
private var operationType: MutableState<Int> = mutableStateOf(IconButtons.OPERATION.getIconName())

enum class AddOperationPopUpState {
    COLLAPSED,
    EXPANDED
}

class TransitionData(
    alpha: State<Float>,
    size: State<Dp>,
    position: State<Dp>
) {
    val alpha by alpha
    val size by size
    val position by position
}

object AddOperationLeftSideIconEvent : UiEvent {
    data class OnInitPopUp(val initialIcon: IconButtons = IconButtons.OPERATION) : UiEvent
    data class OnIconButtonClicked(val operationName: Int) : UiEvent
}

fun addOperationLeftSideIconEventManager(event: UiEvent) {
    when (event) {
        is AddOperationLeftSideIconEvent.OnInitPopUp -> {
            initIcons(event)
        }
        is AddOperationLeftSideIconEvent.OnIconButtonClicked -> {
            updateSelectedIcon(event)
        }
    }
}

private fun updateSelectedIcon(event: AddOperationLeftSideIconEvent.OnIconButtonClicked) {
    operationType.value = event.operationName
}

private fun initIcons(event: AddOperationLeftSideIconEvent.OnInitPopUp) {
    operationType.value = event.initialIcon.getIconName()
}

@Composable
fun AddOperationPopUpLeftSideIcon(
    onIconButtonClicked: (operationName: Int) -> Unit,
) {
    Box {
        Box(
            modifier = Modifier
                .selectorOffsetAnimation(selectedOperationType =  operationType.value)
                .background(
                    color = MaterialTheme.colors.primaryVariant,
                    shape = RoundedCornerShape(topStart = BigMarge, bottomStart = BigMarge)
                )
                .height(48.dp)
                .width(48.dp)
        )
        Column {
            PAMIconButton(
                iconButton = IconButtons.OPERATION,
                onIconButtonClicked = { onIconButtonClicked(it) }
            )
            Spacer(modifier = Modifier.height(RegularMarge))
            PAMIconButton(
                iconButton = IconButtons.PAYMENT,
                onIconButtonClicked = { onIconButtonClicked(it) }
            )
            Spacer(modifier = Modifier.height(RegularMarge))
            PAMIconButton(
                iconButton = IconButtons.TRANSFER,
                onIconButtonClicked = { onIconButtonClicked(it) }
            )
        }
    }
}

@Composable
fun AddOperationPopUp(
    addOperationPopUpState: AddOperationPopUpState,
    onAddOperation: () -> Unit,
    onDismiss: () -> Unit
) {
    val transition = addOperationPopUpAnimation(addOperationPopUpState = addOperationPopUpState)
    if (addOperationPopUpState == AddOperationPopUpState.EXPANDED)
        Column(
            modifier = Modifier
                .background(Black.copy(alpha = transition.alpha))
                .fillMaxSize()

        ) {
            Row(
                modifier = Modifier
                    .height(transition.size)
                    .offset(y = transition.position)
                    .padding(horizontal = RegularMarge, vertical = RegularMarge)
            ) {
                //Left menu-----------------------------------------------------
                AddOperationPopUpLeftSideIcon(
                    onIconButtonClicked = {
                        addOperationLeftSideIconEventManager(
                            AddOperationLeftSideIconEvent.OnIconButtonClicked(it)
                        )
                    }
                )
                //Pop up Body --------------------------------------------
                LazyColumn {
                    item {
                        BasePopUp(
                            title = stringResource(id = operationType.value),
                            onAcceptButtonClicked = {
                                onDismiss()
                                onAddOperation()
                            },
                            onCancelButtonClicked = {
                                onDismiss()
                            }
                        ) {
                            //category drop down -----------------------------------
                            BaseDropDownMenuWithBackGround(
                                hint = stringResource(R.string.category),
                                itemList = testList,
                                onItemSelected = { category ->

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
                                    modifier = Modifier.height(
                                        expandCollapsePaymentAnimation(
                                            operationType.value
                                        ).value
                                    ),
                                    isRecurrent = { isRecurrent.value = it }
                                )

                                //Transfer Operation option---------------------------
                                TransferOptionPanel(
                                    modifier = Modifier.height(
                                        expandCollapseTransferAnimation(
                                            operationType.value
                                        ).value
                                    ),
                                    onSenderAccountSelected = { senderAccount ->

                                    },
                                    onBeneficiaryAccountSelected = { beneficiaryAccount ->

                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
}