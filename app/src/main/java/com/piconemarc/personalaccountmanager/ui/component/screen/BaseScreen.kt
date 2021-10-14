package com.piconemarc.personalaccountmanager.ui.component.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.piconemarc.model.entity.PresentationDataModel
import com.piconemarc.personalaccountmanager.R
import com.piconemarc.personalaccountmanager.ui.component.PAMIconButtons
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
                .padding(vertical = BigMarge)
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
    BaseScreen(
        header = { PAMAppHeader() },
        body = {
            //folder
            Row(Modifier.fillMaxWidth()) {
                Folder {
                    SheetHoleColumn(PastelGreen)
                    Column(modifier = Modifier.weight(1f)) {
                        Sheet(interlayerBackgroundColor = PastelGreen) {
                            body()
                        }
                    }
                    InterlayerIcons(
                        onPaymentButtonClicked = {},
                        onChartButtonClicked = {},
                        onHomeButtonClicked = {}
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
private fun InterlayerIcons(
    onHomeButtonClicked : ()-> Unit,
    onPaymentButtonClicked : ()-> Unit,
    onChartButtonClicked : ()-> Unit
) {
    Box(
        modifier = Modifier
            .width(50.dp)
            .fillMaxHeight()
    ) {
        InterlayerIcon(
            backGroundColor = PastelPurple,
            iconButton = PAMIconButtons.Chart,
            onIconFolderClicked = onHomeButtonClicked,
            yOffset = 90.dp
        )

        InterlayerIcon(
            backGroundColor = PastelBlue,
            iconButton = PAMIconButtons.Payment,
            onIconFolderClicked = onPaymentButtonClicked,
            yOffset = 45.dp
        )
        InterlayerIcon(
            backGroundColor = PastelGreen,
            iconButton = PAMIconButtons.Home,
            onIconFolderClicked = onChartButtonClicked,
            yOffset = 0.dp
        )
    }
}

@Composable
private fun InterlayerIcon(
    backGroundColor: Color,
    iconButton: PAMIconButtons,
    onIconFolderClicked: () -> Unit,
    yOffset : Dp

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
            .clickable { onIconFolderClicked() }
            ,
        verticalAlignment = Alignment.Bottom
    ) {
        Surface(
            modifier = Modifier
                .padding(LittleMarge),
            shape = CircleShape,
            color = Color.Transparent,
            border = BorderStroke(ThinMarge, MaterialTheme.colors.primary),
        )
        {
            Icon(
                imageVector = ImageVector.vectorResource(iconButton.vectorIcon),
                contentDescription = stringResource(iconButton.iconContentDescription),
                modifier = Modifier
                    .background(Color.Transparent, CircleShape)
                    .padding(LittleMarge),
                tint = MaterialTheme.colors.primary
            )
        }
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
                        PinkDark,
                        PinkDark,
                        MaterialTheme.colors.primaryVariant,
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
                        GrayDark,
                        Black,
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
                        MaterialTheme.colors.primaryVariant,
                        PinkDark
                    )
                ),
                shape = RoundedCornerShape(topStart = XlMarge, topEnd = XlMarge)
            )
            .border(
                width = ThinMarge,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        PinkDark,
                        MaterialTheme.colors.primaryVariant
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
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        PinkDark,
                        MaterialTheme.colors.primaryVariant
                    )
                ),
                shape = RoundedCornerShape(bottomStart = XlMarge, bottomEnd = XlMarge)
            )
            .border(
                width = ThinMarge,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colors.primaryVariant,
                        PinkDark
                    )
                ),
                shape = RoundedCornerShape(bottomStart = XlMarge, bottomEnd = XlMarge)
            ),
        contentAlignment = Alignment.Center

    ) {
        Text(
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.h1,
            color = MaterialTheme.colors.onPrimary
        )
    }
}