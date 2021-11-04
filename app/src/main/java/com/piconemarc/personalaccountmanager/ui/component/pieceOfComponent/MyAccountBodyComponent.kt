package com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.piconemarc.model.PAMIconButtons
import com.piconemarc.model.entity.AccountUiModel
import com.piconemarc.model.entity.OperationUiModel
import com.piconemarc.personalaccountmanager.*
import com.piconemarc.personalaccountmanager.R
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.base.BaseDeleteIconButton
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.base.BaseIconButton
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.base.PostItBackground
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.base.PostItTitle
import com.piconemarc.personalaccountmanager.ui.theme.*
import com.piconemarc.viewmodel.viewModel.reducer.AppSubscriber
import java.text.DateFormatSymbols
import java.util.*

@Composable
fun MyAccountBodyRecyclerView(
    onDeleteAccountButtonClicked: (accountToDelete: AccountUiModel) -> Unit,
    onAccountClicked: (selectedAccount: AccountUiModel) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .padding(bottom = LittleMarge)
    ) {
        items(AppSubscriber.AppUiState.myAccountScreenUiState.allAccounts) { account ->
            MyAccountPostIt(
                account = account,
                onDeleteAccountButtonClicked = onDeleteAccountButtonClicked,
                onAccountClicked = onAccountClicked
            )
        }
    }
}

@Composable
fun MyAccountDetailSheet(
    allOperations: List<OperationUiModel>,
    onDeleteItemButtonCLick: (operation: OperationUiModel) -> Unit,
    accountRest: Double,
    accountBalance: Double,
    onOperationNameClick: (operation: OperationUiModel) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {

        Box(modifier = Modifier.weight(1f)) {
            MyAccountDetailSheetTitleAndDivider()
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 40.dp)
            ) {
                Divider(
                    color = MaterialTheme.colors.onSecondary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .width(ThinBorder)
                        .offset(y = 30.dp)
                )
                MyAccountDetailOperationRecyclerView(
                    accountMonthlyOperations = allOperations,
                    onDeleteItemButtonCLick = onDeleteItemButtonCLick,
                    onOperationNameClick = onOperationNameClick
                )
            }
        }
        MyAccountDetailFooter(
            accountRest = accountRest,
            accountBalance = accountBalance
        )
    }
}

@Composable
private fun MyAccountDetailFooter(
    accountRest: Double,
    accountBalance: Double
) {
    Row(
        Modifier
            .padding(horizontal = LittleMarge)
            .background(
                color = MaterialTheme.colors.secondary,
                shape = RoundedCornerShape(BigMarge)
            )
            .border(
                width = ThinBorder,
                color = MaterialTheme.colors.onSecondary,
                shape = RoundedCornerShape(BigMarge)
            )
            .padding(horizontal = RegularMarge, vertical = RegularMarge)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            Text(
                text = stringResource(R.string.restTitle),
                style = MaterialTheme.typography.body1,
            )
            Text(
                text = " ${accountRest.toStringWithTwoDec()} ${getCurrencySymbolForLocale(currentLocale)}",
                style = MaterialTheme.typography.body1,
                color = getBlackOrNegativeColor(amount = accountRest)
            )
        }
        Row {
            Text(
                text = stringResource(R.string.balanceTitle),
                style = MaterialTheme.typography.body1,
            )
            Text(
                text = " ${accountBalance.toStringWithTwoDec()} ${getCurrencySymbolForLocale(currentLocale)}",
                style = MaterialTheme.typography.body1,
                color = getBlackOrNegativeColor(amount = accountBalance)
            )
        }
    }
}

@Composable
private fun MyAccountDetailOperationRecyclerView(
    accountMonthlyOperations: List<OperationUiModel>,
    onDeleteItemButtonCLick: (operation: OperationUiModel) -> Unit,
    onOperationNameClick: (operation: OperationUiModel) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .offset(y = 35.dp)
    ) {
        items(accountMonthlyOperations) { operation ->
            OperationItem(
                operation = operation,
                onDeleteItemButtonCLick = onDeleteItemButtonCLick,
                onOperationNameClick = onOperationNameClick
            )
        }
    }
}

@Composable
private fun OperationItem(
    operation: OperationUiModel,
    onDeleteItemButtonCLick: (operation: OperationUiModel) -> Unit,
    onOperationNameClick: (operation: OperationUiModel) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = operation.name,
            modifier = Modifier
                .padding(start = LittleMarge)
                .weight(1.5f)
                .clickable { onOperationNameClick(operation) },
            style = MaterialTheme.typography.body1
        )
        Text(
            text = when {
                operation.paymentId != null -> stringResource(R.string.myAccountDetailPaymentIndicator)
                operation.transferId != null -> stringResource(R.string.myAccountDetailTransferIndicator)
                else -> ""
            },
            modifier = Modifier
                .padding(end = LittleMarge)
                .weight(0.2f),
            style = MaterialTheme.typography.caption
        )
        Text(
            text = getAddOrMinusFormattedValue(operation.amount),
            modifier = Modifier
                .padding(start = LittleMarge)
                .weight(1f),
            style = MaterialTheme.typography.body1,
            color = getPositiveOrNegativeColor(operation.amount)
        )
        BaseDeleteIconButton(onDeleteItemButtonCLick, operation)
    }
    Divider(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colors.secondaryVariant.copy(alpha = 0.3f),
        thickness = ThinBorder
    )
}

@Composable
private fun MyAccountDetailSheetTitleAndDivider() {
    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(LittleMarge)
        ) {
            Text(
                text = stringResource(id = R.string.operationName),
                modifier = Modifier.weight(accountDetailOperationNameWeight),
                style = MaterialTheme.typography.h3
            )
            Divider(
                color = MaterialTheme.colors.onSecondary,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(ThinBorder)
            )
            Text(
                text = stringResource(R.string.amount),
                modifier = Modifier
                    .padding(start = LittleMarge)
                    .weight(accountDetailOperationAmountWeight),
                style = MaterialTheme.typography.h3
            )
        }
    }
}

@Composable
fun MyAccountDetailTitle(
    onBackIconClick: () -> Unit,
    accountName: String
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BaseIconButton(
                onIconButtonClicked = { onBackIconClick() },
                iconButton = PAMIconButtons.Back,
                iconColor = MaterialTheme.colors.primary,
                backgroundColor = Color.Transparent
            )
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = accountName,
                    style = MaterialTheme.typography.h2
                )
                Text(
                    text = DateFormatSymbols(Locale.FRENCH).months[Calendar.getInstance().time.month],
                    style = MaterialTheme.typography.body1
                )
            }
        }
        Divider(
            modifier = Modifier
                .padding(horizontal = LittleMarge)
                .fillMaxWidth(),
            color = MaterialTheme.colors.onSecondary,
            thickness = ThinBorder
        )
    }
}


@SuppressLint("ModifierParameter")
@Composable
private fun MyAccountPostIt(
    account: AccountUiModel,
    onDeleteAccountButtonClicked: (accountUi: AccountUiModel) -> Unit,
    onAccountClicked: (accountUi: AccountUiModel) -> Unit,
    postItModifier: Modifier = Modifier
        .size(width = AccountPostItWidth, height = AccountPostItHeight)
        .padding(bottom = BigMarge),
    accountBody: @Composable () -> Unit = {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = XlMarge, start = RegularMarge),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            MyAccountPostItValue(
                valueTitle = stringResource(R.string.balanceTitle),
                value = "${account.accountBalance.toStringWithTwoDec()} ${getCurrencySymbolForLocale(currentLocale)}",
                valueColor = getBlackOrNegativeColor(account.accountBalance)
            )
            MyAccountPostItValue(
                valueTitle = stringResource(R.string.restTitle),
                value = "${account.rest.toStringWithTwoDec()} ${getCurrencySymbolForLocale(currentLocale)}",
                valueColor = getBlackOrNegativeColor(account.rest)
            )
        }
    }
) {
    Box(modifier = postItModifier
        .clickable { onAccountClicked(account) }
    ) {
        PostItBackground(this)
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
        ) {
            PostItTitle(
                account = account.name,
                onAccountButtonClicked = { onDeleteAccountButtonClicked(account) },
                iconButton = PAMIconButtons.Delete
            )
            accountBody()
        }
    }
}

@Composable
private fun MyAccountPostItValue(
    valueTitle: String,
    value: String,
    valueColor : Color
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
            modifier = Modifier.padding(start = LittleMarge),
            color = valueColor
        )
    }
}