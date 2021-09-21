package com.piconemarc.personalaccountmanager.ui.baseComponent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import com.piconemarc.personalaccountmanager.R
import com.piconemarc.personalaccountmanager.ui.theme.Black
import com.piconemarc.personalaccountmanager.ui.theme.RegularMarge

var isRecurrent: MutableState<Boolean> = mutableStateOf(false)

enum class AddOperationPopUpState {
    COLLAPSED,
    EXPANDED
}
enum class AddOperationPanelState{
    INITIAL_STATE,
    OPERATION_EXPANDED,
    PAYMENT_EXPANDED,
    TRANSFER_EXPANDED
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

 data class AddOperationPopUpStateValues(
     val popUpTitle : String
)

@Composable
fun updatePopUpStateValues(addOperationPanelState: AddOperationPanelState):AddOperationPopUpStateValues{
    val popUpTitle: String = when(addOperationPanelState){
        AddOperationPanelState.INITIAL_STATE->stringResource(id = R.string.operation)
        AddOperationPanelState.OPERATION_EXPANDED -> stringResource(id = R.string.operation)
        AddOperationPanelState.TRANSFER_EXPANDED -> stringResource(id = R.string.transfer)
        AddOperationPanelState.PAYMENT_EXPANDED ->stringResource(id = R.string.payment)
    }
    return remember {
        AddOperationPopUpStateValues(popUpTitle = popUpTitle)
    }
}

@Composable
fun AddOperationPopUp(
    addOperationPopUpState: AddOperationPopUpState,
    onAddOperation: () -> Unit,
    onDismiss: () -> Unit
) {
    val operationTitle = stringResource(id = R.string.operation)
    var popUpTitle: String by remember { mutableStateOf(operationTitle) }
    val transition = addOperationPopUpAnimation(addOperationPopUpState = addOperationPopUpState)
    val addOperationPanelState by remember {
        mutableStateOf(AddOperationPanelState.INITIAL_STATE)
    }

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
            OperationPopUpLeftSideIcon(
                onIconButtonClicked = { popUpTitle_ ->
                    popUpTitle = popUpTitle_
                },
            )
            //Pop up Body --------------------------------------------
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
                                modifier = Modifier.height(expandCollapsePaymentAnimation(popUpTitle).value),
                                isRecurrent = { isRecurrent.value = it }
                            )

                            //Transfer Operation option---------------------------
                            TransferOptionPanel(
                                modifier = Modifier.height(expandCollapseTransferAnimation(popUpTitle).value),
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