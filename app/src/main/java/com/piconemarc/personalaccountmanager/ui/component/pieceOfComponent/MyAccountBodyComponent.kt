package com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.unit.dp
import com.piconemarc.model.PAMIconButtons
import com.piconemarc.model.entity.OperationModel
import com.piconemarc.model.entity.PresentationDataModel
import com.piconemarc.personalaccountmanager.R
import com.piconemarc.personalaccountmanager.ui.theme.*
import com.piconemarc.viewmodel.viewModel.AppViewModel
import com.piconemarc.viewmodel.viewModel.AppActions
import com.piconemarc.viewmodel.viewModel.reducer.AppSubscriber
import java.text.DateFormatSymbols
import java.util.*

@Composable
fun MyAccountBodyRecyclerView(viewModel: AppViewModel) {
    LazyColumn(
        modifier = Modifier
            .padding(bottom = LittleMarge)
    ) {
        items(AppSubscriber.AppUiState.myAccountScreenUiState.allAccounts) { account ->
            AccountPostIt(
                accountName = PresentationDataModel(
                    stringValue = account.name,
                    objectIdReference = account.id
                ),
                onDeleteAccountButtonClicked = { accountName ->
                    viewModel.dispatchAction(
                        AppActions.DeleteAccountAction.InitPopUp(accountName = accountName)
                    )
                },
                accountBalanceValue = PresentationDataModel(
                    stringValue = account.accountBalance.toString(),
                    objectIdReference = account.id
                ),
                accountRestValue = PresentationDataModel(
                    stringValue = (account.accountOverdraft + (account.accountBalance)).toString(),
                    objectIdReference = account.id
                ),
                onAccountClicked = { selectedAccount ->
                    viewModel.dispatchAction(
                        AppActions.MyAccountScreenAction.CloseScreen
                    )
                    viewModel.dispatchAction(
                        AppActions.MyAccountDetailScreenAction.InitScreen(selectedAccount = selectedAccount)
                    )
                },
                selectedAccount = account
            )
        }
    }
}

@Composable
fun AccountDetailSheetRecyclerView(
    allOperations: List<OperationModel>,
    onDeleteItemButtonCLick: (operation: OperationModel) -> Unit,
    accountRest: String,
    accountBalance: String
) {
    Column(modifier = Modifier.fillMaxSize()) {

        Box(modifier = Modifier.weight(1f)) {
            AccountDetailTitleAndDivider()
            Box(modifier = Modifier
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
                OperationRecyclerView(
                    accountMonthlyOperations = allOperations,
                    onDeleteItemButtonCLick = onDeleteItemButtonCLick
                )
            }
        }
        AccountDetailFooter(
            accountRest = accountRest,
            accountBalance = accountBalance
        )
    }
}

@Composable
private fun AccountDetailFooter(accountRest: String, accountBalance: String) {
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
        Text(
            text = stringResource(R.string.restTitle) + accountRest,
            style = MaterialTheme.typography.body1
        )
        Text(
            text = stringResource(R.string.balanceTitle) + accountBalance,
            style = MaterialTheme.typography.body1
        )
    }

}

@Composable
private fun OperationRecyclerView(
    accountMonthlyOperations: List<OperationModel>,
    onDeleteItemButtonCLick: (operation: OperationModel) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .offset(y = 35.dp)
    ) {
        items(accountMonthlyOperations) { operation ->
            OperationItem(
                operation = operation,
                onDeleteItemButtonCLick = onDeleteItemButtonCLick
            )
        }
    }
}

@Composable
private fun OperationItem(
    operation: OperationModel,
    onDeleteItemButtonCLick: (operation: OperationModel) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = operation.name,
            modifier = Modifier
                .padding(start = LittleMarge)
                .weight(2f),
            style = MaterialTheme.typography.body1
        )
        Text(
            text = if (operation.amount > 0) "+${operation.amount}" else operation.amount.toString(),
            modifier = Modifier
                .padding(start = LittleMarge)
                .weight(0.9f),
            style = MaterialTheme.typography.body1,
            color = if (operation.amount > 0) PositiveText else NegativeText
        )
        Box(modifier = Modifier.size(35.dp)) {
            PAMIconButton(
                onIconButtonClicked = { onDeleteItemButtonCLick(operation) },
                iconButton = PAMIconButtons.Delete,
                iconColor = MaterialTheme.colors.onSecondary,
                backgroundColor = Color.Transparent
            )
        }
    }
    Divider(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colors.secondaryVariant.copy(alpha = 0.3f),
        thickness = ThinBorder
    )
}

@Composable
private fun AccountDetailTitleAndDivider() {
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
fun AccountDetailTitle(
    onBackIconClick: () -> Unit,
    accountName: PresentationDataModel
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            PAMIconButton(
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
                    text = accountName.stringValue,
                    style = MaterialTheme.typography.h2
                )
                Text(
                    text = DateFormatSymbols(Locale.FRENCH).months.get(
                        Calendar.getInstance().time.month
                    ),
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