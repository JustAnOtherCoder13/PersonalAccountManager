package com.piconemarc.personalaccountmanager.ui.component.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.piconemarc.model.entity.GeneratedOperation
import com.piconemarc.model.entity.PresentationDataModel
import com.piconemarc.personalaccountmanager.R
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.PAMAddButton
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.PAMIconButton
import com.piconemarc.personalaccountmanager.ui.theme.*
import com.piconemarc.viewmodel.PAMIconButtons
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun MyAccountDetailBody(
    onBack: () -> Unit,
    accountName: PresentationDataModel
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = RegularMarge)
                .border(
                    width = ThinBorder,
                    color = MaterialTheme.colors.onSecondary,
                    shape = RoundedCornerShape(
                        LittleMarge
                    )
                )
                .background(
                    color = MaterialTheme.colors.onPrimary, shape = RoundedCornerShape(
                        LittleMarge
                    )
                )
                .weight(1f)
        ) {
            AccountDetailTitle(onBack, accountName)
            Box(modifier = Modifier.fillMaxSize()) {
                Divider(
                    color = MaterialTheme.colors.onSecondary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .width(ThinBorder)
                        .offset(y = 30.dp)
                )
                Row(
                    modifier = Modifier
                        .align(alignment = Alignment.BottomStart)
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
                        text = stringResource(R.string.restTitle) + "30.0",
                        style = MaterialTheme.typography.body1
                    )
                    Text(
                        text = stringResource(R.string.balanceTitle) + "50.0",
                        style = MaterialTheme.typography.body1
                    )
                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .offset(y = 35.dp)
                ) {
                    val list = GeneratedOperation
                    items(list) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = it.name,
                                modifier = Modifier
                                    .padding(start = LittleMarge)
                                    .weight(2f),
                                style = MaterialTheme.typography.body1
                            )
                            Text(
                                text =if (it.amount>0) "+${it.amount}" else it.amount.toString(),
                                modifier = Modifier
                                    .padding(start = LittleMarge)
                                    .weight(0.9f),
                                style = MaterialTheme.typography.body1,
                                color = if (it.amount>0) PositiveText else NegativeText
                            )
                            Box(modifier = Modifier.size(35.dp)){
                                PAMIconButton(
                                    onIconButtonClicked = {},
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
                }
                Column(modifier = Modifier.fillMaxSize()) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(LittleMarge)
                    ) {
                        Text(
                            text = "Operation Name",
                            modifier = Modifier.weight(1.8f),
                            style = MaterialTheme.typography.h3
                        )
                        Divider(
                            color = MaterialTheme.colors.onSecondary,
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(ThinBorder)
                                .padding(bottom = 37.dp)
                        )
                        Text(
                            text = "Amount",
                            modifier = Modifier
                                .padding(start = LittleMarge)
                                .weight(1.2f),
                            style = MaterialTheme.typography.h3
                        )
                    }
                }
            }
        }
        PAMAddButton(onAddButtonClicked = {

        })
    }

}

@Composable
private fun AccountDetailTitle(
    onBack: () -> Unit,
    accountName: PresentationDataModel
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            PAMIconButton(
                onIconButtonClicked = {
                    onBack()
                },
                iconButton = PAMIconButtons.Back,
                iconColor = MaterialTheme.colors.primary,
                backgroundColor = Color.Transparent

            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${accountName.stringValue} : ",
                    style = MaterialTheme.typography.h2
                )
                Text(
                    text = SimpleDateFormat(
                        "MM/yyyy",
                        Locale.FRANCE
                    ).format(Calendar.getInstance().time),
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