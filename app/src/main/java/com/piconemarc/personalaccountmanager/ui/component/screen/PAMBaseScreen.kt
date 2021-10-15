package com.piconemarc.personalaccountmanager.ui.component.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.piconemarc.model.entity.PresentationDataModel
import com.piconemarc.personalaccountmanager.R
import com.piconemarc.personalaccountmanager.ui.animation.PAMUiDataAnimations
import com.piconemarc.personalaccountmanager.ui.animation.pAMInterlayerAnimation
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.*
import com.piconemarc.personalaccountmanager.ui.component.popUp.PAMAddOperationPopUp
import com.piconemarc.personalaccountmanager.ui.theme.*
import com.piconemarc.viewmodel.viewModel.AccountDetailViewModel

@Composable
fun BaseScreen(
    header: @Composable () -> Unit,
    body: @Composable () -> Unit,
    footer: @Composable () -> Unit
) {
    Column(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.SpaceBetween) {
        Row() { header() }
        Row(
            modifier = Modifier
                .weight(1f)
                .padding(vertical = RegularMarge)
        ) {
            body()
        }
        Row() { footer() }
    }
}

@Composable
fun PAMMainScreen(
    accountDetailViewModel: AccountDetailViewModel,
    body: @Composable () -> Unit,
) {
    var isPaymentSelected by remember {
        mutableStateOf(false)
    }
    var isChartSelected by remember {
        mutableStateOf(false)
    }
    var title by remember {
        mutableStateOf("My Accounts")
    }

    val transition: PAMUiDataAnimations.InterlayerAnimationData = pAMInterlayerAnimation(
        Pair(isPaymentSelected, isChartSelected)
    )
    BaseScreen(
        header = { PAMAppHeader() },
        body = {
            //folder
            Row(Modifier.fillMaxWidth()) {
                Folder {
                    SheetHoleColumn(transition.interlayerColor)
                    Column(modifier = Modifier.weight(1f)) {
                        Sheet(interlayerBackgroundColor = transition.interlayerColor) {
                            Text(
                                text = title,
                                modifier = Modifier
                                    .fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.h2
                            )
                            body()
                        }
                    }
                    InterlayerIconPanel(
                        transition = transition,
                        onHomeButtonClicked = {
                            title = "My Accounts"
                        },
                        onPaymentButtonClicked = {
                            title = "My Payments"
                        },
                        onChartButtonClicked = {
                            title = "Chart"
                        },
                        flowUpTuple = {
                            isPaymentSelected = it.first
                            isChartSelected = it.second
                        },
                        isPaymentSelected = isPaymentSelected,
                        isChartSelected = isChartSelected
                    )
                }
            }

        },
        footer = {
            PAMAppFooter(
                footerAccountBalance = PresentationDataModel(stringValue = "+100"),//---------------------------
                footerAccountName = PresentationDataModel(stringValue = "testName"),  //todo pass with VM
                footerAccountRest = PresentationDataModel(stringValue = "200")  //-----------------------------
            )
        }
    )
    PAMAddOperationPopUp(accountDetailViewModel = accountDetailViewModel)
}

@Composable
private fun InterlayerIconPanel(
    isPaymentSelected: Boolean,
    isChartSelected: Boolean,
    transition: PAMUiDataAnimations.InterlayerAnimationData,
    onHomeButtonClicked: () -> Unit,
    onPaymentButtonClicked: () -> Unit,
    onChartButtonClicked: () -> Unit,
    flowUpTuple: (Pair<Boolean, Boolean>) -> Unit
) {

    Box(
        modifier = Modifier
            .width(50.dp)
            .fillMaxHeight()
    ) {
        if (isPaymentSelected) {
            PaymentInterlayerIconDisposition(
                onHomeButtonClicked = {
                    onHomeButtonClicked()
                    flowUpTuple(
                        Pair(
                            first = false, second = false
                        )
                    )
                },
                onPaymentButtonClicked = {
                    onPaymentButtonClicked()
                    flowUpTuple(
                        Pair(
                            first = true, second = false
                        )
                    )
                },
                onChartButtonClicked = {
                    onChartButtonClicked()
                    flowUpTuple(
                        Pair(
                            first = false, second = true
                        )
                    )
                },
                transition = transition
            )
        } else if (isChartSelected) {
            ChartInterlayerIconDisposition(
                onHomeButtonClicked = {
                    onHomeButtonClicked()
                    flowUpTuple(
                        Pair(
                            first = false, second = false
                        )
                    )
                },
                onPaymentButtonClicked = {
                    onPaymentButtonClicked()
                    flowUpTuple(
                        Pair(
                            first = true, second = false
                        )
                    )
                },
                onChartButtonClicked = {
                    onChartButtonClicked()
                    flowUpTuple(
                        Pair(
                            first = false, second = true
                        )
                    )
                },
                transition = transition
            )
        } else {
            HomeInterlayerIconDisposition(
                onHomeButtonClicked = {
                    onHomeButtonClicked()
                    flowUpTuple(
                        Pair(
                            first = false, second = false
                        )
                    )
                },
                onPaymentButtonClicked = {
                    onPaymentButtonClicked()
                    flowUpTuple(
                        Pair(
                            first = true, second = false
                        )
                    )
                },
                onChartButtonClicked = {
                    onChartButtonClicked()
                    flowUpTuple(
                        Pair(
                            first = false, second = true
                        )
                    )
                },
                transition = transition
            )
        }
    }
}


@Composable
fun HomeInterlayerIconDisposition(
    onHomeButtonClicked: () -> Unit,
    onPaymentButtonClicked: () -> Unit,
    onChartButtonClicked: () -> Unit,
    transition: PAMUiDataAnimations.InterlayerAnimationData
) {
    InterLayerIconsSelector(
        topButton = {
            PAMHomeButton(
                onHomeButtonClicked = onHomeButtonClicked,
                iconYOffset = transition.homeIconVerticalPosition
            )
        },
        middleButton = {
            PAMPaymentButton(
                onPaymentButtonClicked = onPaymentButtonClicked,
                iconYOffset = transition.paymentIconVerticalPosition
            )
        },
        backgroundButton = { PAMChartButton(onChartButtonClicked = onChartButtonClicked) })
}

@Composable
fun PaymentInterlayerIconDisposition(
    onHomeButtonClicked: () -> Unit,
    onPaymentButtonClicked: () -> Unit,
    onChartButtonClicked: () -> Unit,
    transition: PAMUiDataAnimations.InterlayerAnimationData
) {
    InterLayerIconsSelector(
        topButton = {
            PAMPaymentButton(
                onPaymentButtonClicked = onPaymentButtonClicked,
                iconYOffset = transition.paymentIconVerticalPosition
            )
        },
        middleButton = {
            PAMHomeButton(
                onHomeButtonClicked = onHomeButtonClicked,
                iconYOffset = transition.homeIconVerticalPosition
            )
        },
        backgroundButton = { PAMChartButton(onChartButtonClicked = onChartButtonClicked) })
}

@Composable
fun ChartInterlayerIconDisposition(
    onHomeButtonClicked: () -> Unit,
    onPaymentButtonClicked: () -> Unit,
    onChartButtonClicked: () -> Unit,
    transition: PAMUiDataAnimations.InterlayerAnimationData
) {
    InterLayerIconsSelector(
        topButton = { PAMChartButton(onChartButtonClicked = onChartButtonClicked) },
        middleButton = {
            PAMPaymentButton(
                onPaymentButtonClicked = onPaymentButtonClicked,
                iconYOffset = transition.paymentIconVerticalPosition
            )
        },
        backgroundButton = {
            PAMHomeButton(
                onHomeButtonClicked = onHomeButtonClicked,
                iconYOffset = transition.homeIconVerticalPosition
            )
        })
}

@Composable
private fun InterLayerIconsSelector(
    topButton: @Composable () -> Unit,
    middleButton: @Composable () -> Unit,
    backgroundButton: @Composable () -> Unit
) {
    backgroundButton()
    middleButton()
    topButton()
}

@Composable
fun InterlayerIcon(
    backGroundColor: Color,
    iconButton: PAMIconButtons,
    onIconFolderClicked: () -> Unit,
    yOffset: Dp,
    iconYOffset: Dp = 28.dp
) {
    Row(
        modifier = Modifier
            .width(40.dp)
            .height(70.dp)
            .offset(y = yOffset)
            .background(
                color = backGroundColor,
                shape = RoundedCornerShape(
                    topEnd = BigMarge,
                    bottomEnd = BigMarge
                )
            )
            .clickable { onIconFolderClicked() },
    ) {
        PAMCircleIcon(
            iconButton = iconButton,
            iconColor = MaterialTheme.colors.onSecondary,
            yOffset = iconYOffset
        )
    }
}

@Composable
private fun Sheet(
    interlayerBackgroundColor: Color,
    body: @Composable () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = interlayerBackgroundColor,
                shape = RoundedCornerShape(bottomEnd = XlMarge)
            )
            .padding(LittleMarge)
    ) {
        body()
    }
}

@Composable
private fun Folder(interlayers: @Composable () -> Unit) {
    Row(
        modifier = Modifier
            .background(
                brush = Brush.horizontalGradient(
                    listOf(
                        MaterialTheme.colors.primaryVariant,
                        MaterialTheme.colors.primaryVariant,
                        MaterialTheme.colors.primary
                    )
                ),
                shape = RoundedCornerShape(
                    topEnd = RegularMarge,
                    bottomEnd = RegularMarge
                )
            )
            .fillMaxSize()
            .padding(LittleMarge)

    ) {
        interlayers()
    }
}

@Composable
private fun SheetHoleColumn(
    backGroundColor: Color
) {
    Column(
        modifier = Modifier
            .width(30.dp)
            .fillMaxHeight()
            .background(
                color = backGroundColor
            )
            .padding(vertical = BigMarge),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SheetHole()
        SheetHole()
        SheetHole()
        SheetHole()
    }
}

@Composable
private fun SheetHole() {
    Box(
        modifier = Modifier
            .size(24.dp)
            .background(
                brush = Brush.radialGradient(
                    listOf(
                        MaterialTheme.colors.primaryVariant,
                        BrownDark_300,
                    )
                ),
                shape = CircleShape
            )
    )
}

@Composable
private fun PAMAppFooter(
    footerAccountName: PresentationDataModel,
    footerAccountBalance: PresentationDataModel,
    footerAccountRest: PresentationDataModel
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colors.primary,
                        MaterialTheme.colors.primaryVariant
                    )
                ),
                shape = RoundedCornerShape(topStart = XlMarge, topEnd = XlMarge)
            )
            .border(
                width = ThinMarge,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colors.primaryVariant,
                        MaterialTheme.colors.primary
                    )
                ),
                shape = RoundedCornerShape(topStart = XlMarge, topEnd = XlMarge)
            )
            .padding(RegularMarge),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row() {
            Text(
                text = footerAccountName.stringValue,
                style = MaterialTheme.typography.h2,
                color = MaterialTheme.colors.onPrimary
            )
        }

        Row(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colors.secondary,
                    shape = RoundedCornerShape(BigMarge)
                )
                .border(
                    width = ThinBorder,
                    color = MaterialTheme.colors.primary,
                    shape = RoundedCornerShape(BigMarge)
                )
                .padding(horizontal = RegularMarge, vertical = LittleMarge)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Balance : ${footerAccountBalance.stringValue}",
                style = MaterialTheme.typography.body1
            )
            Text(
                text = "Rest : ${footerAccountRest.stringValue}",
                style = MaterialTheme.typography.body1
            )
        }
    }
}

@Composable
private fun PAMAppHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colors.primaryVariant,
                        MaterialTheme.colors.primary
                    )
                ),
                shape = RoundedCornerShape(bottomStart = XlMarge, bottomEnd = XlMarge)
            )
            .border(
                width = ThinMarge,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colors.primary,
                        MaterialTheme.colors.primaryVariant
                    )
                ),
                shape = RoundedCornerShape(bottomStart = XlMarge, bottomEnd = XlMarge)
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.h1,
            color = MaterialTheme.colors.onPrimary
        )
    }
}