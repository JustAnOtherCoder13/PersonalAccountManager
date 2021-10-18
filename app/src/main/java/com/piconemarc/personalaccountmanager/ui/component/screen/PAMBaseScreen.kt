package com.piconemarc.personalaccountmanager.ui.component.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.piconemarc.model.entity.AccountModel
import com.piconemarc.model.entity.GeneratedAccount
import com.piconemarc.model.entity.PresentationDataModel
import com.piconemarc.personalaccountmanager.R
import com.piconemarc.personalaccountmanager.ui.animation.PAMUiDataAnimations
import com.piconemarc.personalaccountmanager.ui.animation.pAMInterlayerAnimation
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.*
import com.piconemarc.personalaccountmanager.ui.component.popUp.PAMAddOperationPopUp
import com.piconemarc.personalaccountmanager.ui.theme.*
import com.piconemarc.viewmodel.viewModel.AccountDetailViewModel
import com.piconemarc.viewmodel.viewModel.addOperationPopUp.AddOperationPopUpUtilsProvider

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
) {
    //todo pass with state
    var selectedInterlayerIconButton: PAMIconButtons by remember {
        mutableStateOf(PAMIconButtons.Home)
    }
    var allAccountBalance : Double by remember {
        mutableStateOf(0.0)
    }
    var allAccountRest : Double by remember {
        mutableStateOf(0.0)
    }
   GeneratedAccount.forEach {
            allAccountBalance += it.accountBalance
            allAccountRest += it.accountOverdraft+it.accountBalance
        }

    BaseScreen(
        header = { PAMAppHeader() },
        body = {
            PAMAppBody(
                onInterlayerIconClicked = { selectedInterlayerIconButton = it },
                selectedInterlayerIconButton = selectedInterlayerIconButton,
                body = {
                    when (selectedInterlayerIconButton) {
                        is PAMIconButtons.Payment -> {
                        }
                        is PAMIconButtons.Chart -> {
                        }
                        else -> {
                            MyAccountsBody(
                                onAddAccountButtonClicked = {
                                    accountDetailViewModel.dispatchAction(
                                        AddOperationPopUpUtilsProvider.AddOperationPopUpAction.InitPopUp
                                    )
                                },
                                onDeleteAccountButtonClicked = {account ->
                                    //todo launch delete PopUp
                                },
                                onAccountClicked = {account ->
                                    //todo go to detail
                                },
                                //todo pass with state
                                allAccounts = GeneratedAccount
                            )
                        }
                    }
                }

            )
        },
        footer = {
            PAMAppFooter(
                footerAccountBalance = PresentationDataModel(stringValue = allAccountBalance.toString()),//---------------------------
                footerAccountName = PresentationDataModel(stringValue = stringResource(R.string.footerAllAccountsTitle)),  //todo pass with VM
                footerAccountRest = PresentationDataModel(stringValue = allAccountRest.toString())  //-----------------------------
            )
        }
    )
    PAMAddOperationPopUp(accountDetailViewModel = accountDetailViewModel)
}

@Composable
private fun MyAccountsBody(
    onAddAccountButtonClicked: () -> Unit,
    onDeleteAccountButtonClicked: (account: PresentationDataModel) -> Unit,
    onAccountClicked: (account: PresentationDataModel) -> Unit,
    allAccounts: List<AccountModel>
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        LazyColumn() {
            items(allAccounts) { account ->
                val accountName = PresentationDataModel(
                    stringValue = account.name,
                    objectIdReference = account.id
                )
                AccountPostIt(
                    accountName = accountName,
                    onDeleteAccountButtonClicked = { onDeleteAccountButtonClicked(accountName) },
                    accountBalanceValue = PresentationDataModel(
                        stringValue = account.accountBalance.toString(),
                        objectIdReference = account.id
                    ),
                    accountRestValue = PresentationDataModel(
                        stringValue = (account.accountOverdraft + (account.accountBalance)).toString(),
                        objectIdReference = account.id
                    ),
                    onAccountClicked = onAccountClicked
                )
            }
        }
        PAMAddButton(
            onAddButtonClicked = onAddAccountButtonClicked
        )
    }
}

@Composable
private fun AccountPostIt(
    accountName: PresentationDataModel,
    onDeleteAccountButtonClicked: () -> Unit,
    accountBalanceValue: PresentationDataModel,
    accountRestValue: PresentationDataModel,
    onAccountClicked: (account: PresentationDataModel) -> Unit
) {
    Box(modifier = Modifier
        .size(width = AccountPostItWidth, height = AccountPostItHeight)
        .padding(bottom = BigMarge)
        .clickable { onAccountClicked(accountName) }
    ) {
        AccountPostItBackground(this)
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
        ) {
            AccountPostItTitle(
                accountName = accountName,
                onDeleteAccountButtonClicked = onDeleteAccountButtonClicked
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = XlMarge, start = RegularMarge),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                AccountPostItValue(
                    valueTitle = stringResource(R.string.accountPostItBalanceTitle),
                    value = accountBalanceValue
                )
                AccountPostItValue(
                    valueTitle = stringResource(R.string.accountPostItRestTitle),
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
private fun PAMAppBody(
    onInterlayerIconClicked: (iconButton: PAMIconButtons) -> Unit,
    selectedInterlayerIconButton: PAMIconButtons,
    body: @Composable () -> Unit
) {
    val transition: PAMUiDataAnimations.InterlayerAnimationData = pAMInterlayerAnimation(
        selectedInterlayerIconButton
    )
    Row(Modifier.fillMaxWidth()) {
        Folder {
            SheetHoleColumn(transition.interlayerColor)
            Column(modifier = Modifier.weight(1f)) {
                Sheet(interlayerBackgroundColor = transition.interlayerColor) {
                    InterLayerTitle(
                        title = when (selectedInterlayerIconButton) {
                            is PAMIconButtons.Payment -> {
                                stringResource(R.string.myPaymentInterlayerTitle)
                            }
                            is PAMIconButtons.Chart -> {
                                stringResource(R.string.chartInterlayerTitle)
                            }
                            else -> {
                                stringResource(R.string.myAccountsInterLayerTitle)
                            }
                        }
                    )
                    body()
                }
            }
            InterlayerIconPanel(
                transition = transition,
                onInterlayerIconClicked = onInterlayerIconClicked,
                selectedInterlayerIconButton = selectedInterlayerIconButton
            )
        }
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
private fun InterlayerIconPanel(
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
                PaymentInterlayerIconDisposition(
                    onInterlayerIconClicked = onInterlayerIconClicked,
                    transition = transition
                )
            is PAMIconButtons.Chart ->
                ChartInterlayerIconDisposition(
                    onInterlayerIconClicked = onInterlayerIconClicked,
                    transition = transition
                )
            else -> HomeInterlayerIconDisposition(
                onInterlayerIconClicked = onInterlayerIconClicked,
                transition = transition
            )
        }
    }
}


@Composable
fun HomeInterlayerIconDisposition(
    onInterlayerIconClicked: (iconButton: PAMIconButtons) -> Unit,
    transition: PAMUiDataAnimations.InterlayerAnimationData
) {
    InterLayerIconsSelector(
        topButton = {
            PAMHomeButton(
                onHomeButtonClicked = onInterlayerIconClicked,
                iconYOffset = transition.homeIconVerticalPosition
            )
        },
        middleButton = {
            PAMPaymentButton(
                onPaymentButtonClicked = onInterlayerIconClicked,
                iconYOffset = transition.paymentIconVerticalPosition
            )
        },
        backgroundButton = { PAMChartButton(onChartButtonClicked = onInterlayerIconClicked) })
}

@Composable
fun PaymentInterlayerIconDisposition(
    onInterlayerIconClicked: (iconButton: PAMIconButtons) -> Unit,
    transition: PAMUiDataAnimations.InterlayerAnimationData
) {
    InterLayerIconsSelector(
        topButton = {
            PAMPaymentButton(
                onPaymentButtonClicked = onInterlayerIconClicked,
                iconYOffset = transition.paymentIconVerticalPosition
            )
        },
        middleButton = {
            PAMHomeButton(
                onHomeButtonClicked = onInterlayerIconClicked,
                iconYOffset = transition.homeIconVerticalPosition
            )
        },
        backgroundButton = { PAMChartButton(onChartButtonClicked = onInterlayerIconClicked) })
}

@Composable
fun ChartInterlayerIconDisposition(
    onInterlayerIconClicked: (iconButton: PAMIconButtons) -> Unit,
    transition: PAMUiDataAnimations.InterlayerAnimationData
) {
    InterLayerIconsSelector(
        topButton = { PAMChartButton(onChartButtonClicked = onInterlayerIconClicked) },
        middleButton = {
            PAMPaymentButton(
                onPaymentButtonClicked = onInterlayerIconClicked,
                iconYOffset = transition.paymentIconVerticalPosition
            )
        },
        backgroundButton = {
            PAMHomeButton(
                onHomeButtonClicked = onInterlayerIconClicked,
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
    onInterlayerIconClicked: (iconButton: PAMIconButtons) -> Unit,
    yOffset: Dp = 0.dp,
    iconYOffset: Dp = InterLayerIconOffsetDown
) {
    Row(
        modifier = Modifier
            .width(InterlayerIconPanelRowWidth)
            .height(InterlayerIconPanelRowHeight)
            .offset(y = yOffset)
            .background(
                color = backGroundColor,
                shape = RoundedCornerShape(
                    topEnd = BigMarge,
                    bottomEnd = BigMarge
                )
            )
            .clickable { onInterlayerIconClicked(iconButton) },
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