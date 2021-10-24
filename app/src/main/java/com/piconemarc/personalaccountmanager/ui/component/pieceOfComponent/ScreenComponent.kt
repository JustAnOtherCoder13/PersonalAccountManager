package com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent

import android.annotation.SuppressLint
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
import com.piconemarc.model.PAMIconButtons
import com.piconemarc.model.entity.AccountModel
import com.piconemarc.personalaccountmanager.R
import com.piconemarc.personalaccountmanager.ui.animation.PAMUiDataAnimations
import com.piconemarc.personalaccountmanager.ui.animation.pAMInterlayerAnimation
import com.piconemarc.personalaccountmanager.ui.theme.*
import com.piconemarc.viewmodel.viewModel.AppActions
import com.piconemarc.viewmodel.viewModel.AppViewModel
import com.piconemarc.viewmodel.viewModel.reducer.AppSubscriber.AppUiState.baseAppScreenUiState

@SuppressLint("ModifierParameter")
@Composable
fun VerticalDispositionSheet(
    header: @Composable () -> Unit = {},
    body: @Composable () -> Unit,
    footer: @Composable () -> Unit = {},
    sheetModifier: Modifier = Modifier.fillMaxSize(),
    headerAndBodyColumnModifier: Modifier = Modifier.fillMaxSize(),
    headerModifier: Modifier = Modifier.fillMaxWidth(),
    bodyModifier: Modifier = Modifier.fillMaxWidth(),
    footerModifier: Modifier = Modifier.wrapContentWidth(),
    columnHorizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    columnVerticalArrangement: Arrangement.HorizontalOrVertical = Arrangement.SpaceBetween,
) {
    Column(
        modifier = sheetModifier,
        horizontalAlignment = columnHorizontalAlignment,
        verticalArrangement = columnVerticalArrangement
    ) {
        Column(
            modifier = headerAndBodyColumnModifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Row(modifier = headerModifier) {
                header()
            }
            Row(modifier = bodyModifier) {
                body()
            }
        }
        Row(
            modifier = footerModifier,
        ) {
            footer()
        }
    }
}

@Composable
fun BaseScreen(
    header: @Composable () -> Unit,
    body: @Composable () -> Unit,
    footer: @Composable () -> Unit
) {
    VerticalDispositionSheet(
        header = header,
        body = body,
        footer = footer,
        bodyModifier = Modifier.padding(vertical = RegularMarge)
    )
}

@Composable
fun AccountPostIt(
    account: AccountModel,
    onDeleteAccountButtonClicked: (account: AccountModel) -> Unit,
    onAccountClicked: (account: AccountModel) -> Unit,
) {
    Box(modifier = Modifier
        .size(width = AccountPostItWidth, height = AccountPostItHeight)
        .padding(bottom = BigMarge)
        .clickable { onAccountClicked(account) }
    ) {
        AccountPostItBackground(this)
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
        ) {
            AccountPostItTitle(
                account = account.name,
                onDeleteAccountButtonClicked = { onDeleteAccountButtonClicked(account) }
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = XlMarge, start = RegularMarge),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                AccountPostItValue(
                    valueTitle = stringResource(R.string.balanceTitle),
                    value = account.accountBalance.toString()
                )
                AccountPostItValue(
                    valueTitle = stringResource(R.string.restTitle),
                    value = account.rest.toString()
                )
            }
        }
    }
}

@Composable
private fun AccountPostItValue(
    valueTitle: String,
    value: String
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
            text = value,
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(start = LittleMarge)
        )
    }
}

@Composable
private fun AccountPostItTitle(
    account: String,
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
            text = account,
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
    viewModel : AppViewModel,
    body: @Composable () -> Unit
) {
    val transition: PAMUiDataAnimations.InterlayerAnimationData = pAMInterlayerAnimation(
        baseAppScreenUiState.selectedInterlayerButton
    )
    Row(Modifier.fillMaxWidth()) {
        Folder(
            sheetHole = { SheetHoleColumn(transition.interlayerColor) },
            interlayers = {
                Sheet(
                    interlayerBackgroundColor = transition.interlayerColor,
                    header = { InterLayerTitle(title = stringResource(id = baseAppScreenUiState.interLayerTitle)) },
                    body = { body() }
                    )
            },
            interLayerIconPanel = {
                InterlayerIconPanel(
                    transition = transition,
                    onInterlayerIconClicked = {
                        viewModel.dispatchAction(
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
    footerAccountName: String,
    footerAccountBalance: String,
    footerAccountRest: String
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
                text = footerAccountName,
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
                text = stringResource(R.string.restTitle) + footerAccountRest,
                style = MaterialTheme.typography.body1
            )
            Text(
                text = stringResource(R.string.balanceTitle) + footerAccountBalance,
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