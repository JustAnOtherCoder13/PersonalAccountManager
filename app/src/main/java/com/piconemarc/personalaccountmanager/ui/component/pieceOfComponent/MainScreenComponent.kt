package com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.piconemarc.model.PAMIconButtons
import com.piconemarc.personalaccountmanager.R
import com.piconemarc.personalaccountmanager.currentLocale
import com.piconemarc.personalaccountmanager.getCurrencySymbolForLocale
import com.piconemarc.personalaccountmanager.toStringWithTwoDec
import com.piconemarc.personalaccountmanager.ui.animation.PAMUiDataAnimations
import com.piconemarc.personalaccountmanager.ui.animation.pAMInterlayerAnimation
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.base.ChartButton
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.base.HomeButton
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.base.PaymentButton
import com.piconemarc.personalaccountmanager.ui.theme.*
import java.util.*

@Composable
fun MainScreenHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(appScreenHeaderHeight)
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

@Composable
fun MainScreenBody(
    onInterLayerButtonClick: (pamIconButton: PAMIconButtons) -> Unit,
    selectedInterLayerButton: PAMIconButtons,
    interlayerTitle: Int,
    body: @Composable () -> Unit
) {
    val transition: PAMUiDataAnimations.InterlayerAnimationData = pAMInterlayerAnimation(
        selectedInterLayerButton
    )
    Row(Modifier.fillMaxWidth()) {
        MainScreenFolder(
            sheetHole = { MainScreenSheetHoleColumn(transition.interlayerColor) },
            interlayers = {
                MainScreenSheet(
                    interlayerBackgroundColor = transition.interlayerColor,
                    header = { MainScreenInterLayerTitle(title = stringResource(id = interlayerTitle)) },
                    body = { body() }
                )
            },
            interLayerIconPanel = {
                MainScreenInterlayerIconPanel(
                    transition = transition,
                    onInterlayerIconClicked = onInterLayerButtonClick,
                    selectedInterlayerIconButton = selectedInterLayerButton
                )
            },
        )
    }
}

@Composable
fun MainScreenFooter(
    mainScreenFooterTitle: String,
    footerAccountRest: Double
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
        MainScreenFooterTitle(mainScreenFooterTitle)
        MainScreenFooterInformation(footerAccountRest)
    }
}

@Composable
private fun MainScreenFooterTitle(mainScreenFooterTitle: String) {
    Row {
        Text(
            text = mainScreenFooterTitle,
            style = MaterialTheme.typography.h2,
            color = MaterialTheme.colors.onPrimary
        )
    }
}

@Composable
private fun MainScreenFooterInformation(
    footerAccountRest: Double,
) {
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
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        Row {
            Text(
                text = footerAccountRest.toStringWithTwoDec() + getCurrencySymbolForLocale(
                    currentLocale
                ),
                style = MaterialTheme.typography.body2,
            )
            Text(
                text = "/Month",
                style = MaterialTheme.typography.body2,
            )
        }
        Row {
            Text(
                text = (footerAccountRest / (Calendar.getInstance()
                    .get(Calendar.WEEK_OF_MONTH) - Calendar.getInstance()
                    .getActualMaximum(Calendar.WEEK_OF_MONTH)) * -1).toStringWithTwoDec() + getCurrencySymbolForLocale(
                    currentLocale
                ),
                style = MaterialTheme.typography.body2,
            )
            Text(
                text = "/Week",
                style = MaterialTheme.typography.body2,
            )
        }

        Row {
            Text(
                text = (footerAccountRest / (Calendar.getInstance()
                    .get(Calendar.DAY_OF_MONTH) - Calendar.getInstance()
                    .getActualMaximum(Calendar.DAY_OF_MONTH)) * -1).toStringWithTwoDec() + getCurrencySymbolForLocale(
                    currentLocale
                ),
                style = MaterialTheme.typography.body2,
            )
            Text(
                text = "/Day",
                style = MaterialTheme.typography.body2,
            )
        }
    }
}

@Composable
private fun MainScreenInterlayerIconPanel(
    transition: PAMUiDataAnimations.InterlayerAnimationData,
    onInterlayerIconClicked: (iconButton: PAMIconButtons) -> Unit,
    selectedInterlayerIconButton: PAMIconButtons
) {
    Box(
        modifier = Modifier
            .width(InterlayerIconPanelWidth)
            .fillMaxHeight()
    ) {
        when (selectedInterlayerIconButton) {
            is PAMIconButtons.Payment ->
                MainScreenPaymentInterlayerIconDisposition(
                    onInterlayerIconClicked = onInterlayerIconClicked,
                    transition = transition
                )
            is PAMIconButtons.Chart ->
                MainScreenChartInterlayerIconDisposition(
                    onInterlayerIconClicked = onInterlayerIconClicked,
                    transition = transition
                )
            else -> MainScreenHomeInterlayerIconDisposition(
                onInterlayerIconClicked = onInterlayerIconClicked,
                transition = transition
            )
        }
    }
}

@Composable
private fun MainScreenInterLayerTitle(title: String) {
    Text(
        text = title,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = RegularMarge),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.h2
    )
}

@Composable
private fun MainScreenSheet(
    interlayerBackgroundColor: Color,
    header: @Composable () -> Unit,
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
        Row {
            header()
        }
        Row(modifier = Modifier.weight(1f)) {
            body()
        }

    }
}

@Composable
private fun MainScreenFolder(
    sheetHole: @Composable () -> Unit,
    interlayers: @Composable () -> Unit,
    interLayerIconPanel: @Composable () -> Unit
) {
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
        Column {
            sheetHole()
        }
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            interlayers()
        }
        Column {
            interLayerIconPanel()
        }

    }
}

@Composable
private fun MainScreenSheetHoleColumn(
    backGroundColor: Color
) {
    Column(
        modifier = Modifier
            .width(sheetHoleColumnWidth)
            .fillMaxHeight()
            .background(
                color = backGroundColor
            )
            .padding(vertical = BigMarge),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MainScreenSheetHole()
        MainScreenSheetHole()
        MainScreenSheetHole()
        MainScreenSheetHole()
    }
}

@Composable
private fun MainScreenSheetHole() {
    Box(
        modifier = Modifier
            .size(sheetHoleSize)
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
private fun MainScreenHomeInterlayerIconDisposition(
    onInterlayerIconClicked: (iconButton: PAMIconButtons) -> Unit,
    transition: PAMUiDataAnimations.InterlayerAnimationData
) {
    MainScreenInterLayerIconsSelector(
        topButton = {
            HomeButton(
                onHomeButtonClicked = onInterlayerIconClicked,
                iconYOffset = transition.homeIconVerticalPosition
            )
        },
        middleButton = {
            PaymentButton(
                onPaymentButtonClicked = onInterlayerIconClicked,
                iconYOffset = transition.paymentIconVerticalPosition
            )
        },
        backgroundButton = { ChartButton(onChartButtonClicked = onInterlayerIconClicked) })
}

@Composable
private fun MainScreenPaymentInterlayerIconDisposition(
    onInterlayerIconClicked: (iconButton: PAMIconButtons) -> Unit,
    transition: PAMUiDataAnimations.InterlayerAnimationData
) {
    MainScreenInterLayerIconsSelector(
        topButton = {
            PaymentButton(
                onPaymentButtonClicked = onInterlayerIconClicked,
                iconYOffset = transition.paymentIconVerticalPosition
            )
        },
        middleButton = {
            HomeButton(
                onHomeButtonClicked = onInterlayerIconClicked,
                iconYOffset = transition.homeIconVerticalPosition
            )
        },
        backgroundButton = { ChartButton(onChartButtonClicked = onInterlayerIconClicked) })
}

@Composable
private fun MainScreenChartInterlayerIconDisposition(
    onInterlayerIconClicked: (iconButton: PAMIconButtons) -> Unit,
    transition: PAMUiDataAnimations.InterlayerAnimationData
) {
    MainScreenInterLayerIconsSelector(
        topButton = { ChartButton(onChartButtonClicked = onInterlayerIconClicked) },
        middleButton = {
            PaymentButton(
                onPaymentButtonClicked = onInterlayerIconClicked,
                iconYOffset = transition.paymentIconVerticalPosition
            )
        },
        backgroundButton = {
            HomeButton(
                onHomeButtonClicked = onInterlayerIconClicked,
                iconYOffset = transition.homeIconVerticalPosition
            )
        })
}

@Composable
private fun MainScreenInterLayerIconsSelector(
    topButton: @Composable () -> Unit,
    middleButton: @Composable () -> Unit,
    backgroundButton: @Composable () -> Unit
) {
    backgroundButton()
    middleButton()
    topButton()
}