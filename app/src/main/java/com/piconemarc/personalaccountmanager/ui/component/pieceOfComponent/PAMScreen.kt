package com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.piconemarc.model.entity.AccountModel
import com.piconemarc.model.entity.PresentationDataModel
import com.piconemarc.personalaccountmanager.R
import com.piconemarc.personalaccountmanager.ui.animation.PAMUiDataAnimations
import com.piconemarc.personalaccountmanager.ui.animation.pAMInterlayerAnimation
import com.piconemarc.personalaccountmanager.ui.theme.*
import com.piconemarc.viewmodel.PAMIconButtons
import com.piconemarc.viewmodel.viewModel.AppActionDispatcher
import com.piconemarc.viewmodel.viewModel.AppActions
import com.piconemarc.viewmodel.viewModel.AppSubscriber
import com.piconemarc.viewmodel.viewModel.AppSubscriber.GlobalUiState.baseAppScreenUiState

@Composable
fun BaseScreen(
    header: @Composable () -> Unit,
    body: @Composable () -> Unit,
    footer: @Composable () -> Unit
) {
    Column(modifier = Modifier.fillMaxHeight()) {
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
fun AccountPostIt(
    accountName: PresentationDataModel,
    onDeleteAccountButtonClicked: (accountName: PresentationDataModel) -> Unit,
    accountBalanceValue: PresentationDataModel,
    accountRestValue: PresentationDataModel,
    onAccountClicked: (account: AccountModel) -> Unit,
    selectedAccount : AccountModel
) {
    Box(modifier = Modifier
        .size(width = AccountPostItWidth, height = AccountPostItHeight)
        .padding(bottom = BigMarge)
        .clickable { onAccountClicked(selectedAccount) }
    ) {
        AccountPostItBackground(this)
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
        ) {
            AccountPostItTitle(
                accountName = accountName,
                onDeleteAccountButtonClicked = { onDeleteAccountButtonClicked(accountName) }
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = XlMarge, start = RegularMarge),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                AccountPostItValue(
                    valueTitle = stringResource(R.string.balanceTitle),
                    value = accountBalanceValue
                )
                AccountPostItValue(
                    valueTitle = stringResource(R.string.restTitle),
                    value = accountRestValue
                )
            }
        }
    }
}

@Composable
private fun AccountPostItValue(
    valueTitle: String,
    value: PresentationDataModel
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
    ) {
        Text(
            text = valueTitle,
            style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold)
        )
        Text(
            text = value.stringValue,
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(start = LittleMarge)
        )
    }
}

@Composable
private fun AccountPostItTitle(
    accountName: PresentationDataModel,
    onDeleteAccountButtonClicked: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = LittleMarge),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = accountName.stringValue,
            style = MaterialTheme.typography.h3
        )
        IconButton(
            onClick = onDeleteAccountButtonClicked,
        ) {
            Icon(
                imageVector =
                ImageVector.vectorResource(id = PAMIconButtons.Delete.vectorIcon),
                contentDescription =
                stringResource(
                    id = PAMIconButtons.Delete.iconContentDescription
                )
            )
        }
    }
    Divider(
        modifier = Modifier
            .padding(start = LittleMarge, end = LittleMarge, bottom = RegularMarge),
        color = MaterialTheme.colors.onSecondary,
        thickness = ThinBorder
    )
}

@Composable
private fun AccountPostItBackground(boxScope: BoxScope) {
    with(boxScope) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = PastelYellow,
                    shape = CutCornerShape(bottomEnd = BigMarge)
                )
                .border(
                    width = ThinBorder,
                    color = BrownDark_300,
                    shape = CutCornerShape(bottomEnd = BigMarge)
                )
        )
        Box(
            modifier = Modifier
                .size(width = BigMarge, height = BigMarge)
                .align(Alignment.BottomEnd)
                .background(
                    color = PastelYellowLight,
                    shape = CutCornerShape(bottomEnd = BigMarge)
                )
                .border(
                    width = ThinBorder,
                    color = BrownDark_300,
                    shape = CutCornerShape(bottomEnd = BigMarge)
                ),
        )
    }
}

@Composable
fun PAMAppBody(
    actionDispatcher : AppActionDispatcher,
    body: @Composable () -> Unit
) {
    val transition: PAMUiDataAnimations.InterlayerAnimationData = pAMInterlayerAnimation(
        baseAppScreenUiState.selectedInterlayerButton
    )
    Row(Modifier.fillMaxWidth()) {
        Folder(
            sheetHole = { SheetHoleColumn(transition.interlayerColor) },
            interlayers = {
                Sheet(interlayerBackgroundColor = transition.interlayerColor) {
                    InterLayerTitle(
                        title = stringResource(id = baseAppScreenUiState.interLayerTitle)
                    )
                    body()
                }

            },
            interLayerIconPanel = {
                InterlayerIconPanel(
                    transition = transition,
                    onInterlayerIconClicked = {
                        actionDispatcher.dispatchAction(
                            AppActions.BaseAppScreenAction.SelectInterlayer(it)
                        )
                    },
                    selectedInterlayerIconButton = baseAppScreenUiState.selectedInterlayerButton
                )
            },
        )
    }
}

@Composable
private fun InterLayerTitle(title: String) {
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
private fun Folder(
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
        Column() {
            sheetHole()
        }
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            interlayers()
        }
        Column() {
            interLayerIconPanel()
        }

    }
}

@Composable
private fun SheetHoleColumn(
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
fun PAMAppFooter(
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
                text = stringResource(R.string.restTitle) + footerAccountRest.stringValue,
                style = MaterialTheme.typography.body1
            )
            Text(
                text = stringResource(R.string.balanceTitle) + footerAccountBalance.stringValue,
                style = MaterialTheme.typography.body1
            )
        }
    }
}

@Composable
fun PAMAppHeader() {
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