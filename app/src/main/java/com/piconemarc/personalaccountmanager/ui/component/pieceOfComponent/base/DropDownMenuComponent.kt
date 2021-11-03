package com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.base

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.piconemarc.model.entity.BaseUiModel
import com.piconemarc.personalaccountmanager.R
import com.piconemarc.personalaccountmanager.ui.theme.*

@Composable
fun PAMBaseDropDownMenu(
    selectedItem: String,
    itemList: List<String>,
    onItemSelected: (item: String) -> Unit
) {
    var expanded: Boolean by remember {
        mutableStateOf(false)
    }
    Row(
        modifier = Modifier
            .wrapContentSize()
            .padding(top = RegularMarge, bottom = RegularMarge)
            .border(
                width = ThinBorder,
                color = MaterialTheme.colors.onPrimary,
                shape = RoundedCornerShape(RegularMarge)
            )
            .clickable { expanded = !expanded }
    ) {
        Text(
            text = selectedItem,
            color = MaterialTheme.colors.onPrimary,
            modifier = Modifier.padding(start = RegularMarge, end = RegularMarge),
            style = MaterialTheme.typography.h3
        )
        Spacer(modifier = Modifier.width(LittleMarge))
        Icon(
            imageVector = if (expanded) Icons.Outlined.KeyboardArrowUp else Icons.Outlined.KeyboardArrowDown,
            contentDescription = stringResource(R.string.collapseIconContentDescription),
            tint = MaterialTheme.colors.onPrimary
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .wrapContentWidth()
                .height(DropDownMenuHeight)

        ) {
            itemList.forEachIndexed { _, item ->
                DropdownMenuItem(onClick = {
                    onItemSelected(item)
                    expanded = false
                }) {
                    Text(
                        text = item,
                        style = MaterialTheme.typography.body1
                    )
                }
            }
        }
    }
}

@Composable
private fun <T:BaseUiModel>PAMBaseDropDownMenu(
    selectedItem: T,
    itemList: List<T>,
    onItemSelected: (item: T) -> Unit
) {
    var expanded: Boolean by remember {
        mutableStateOf(false)
    }
    Row(
        modifier = Modifier
            .wrapContentSize()
            .padding(top = RegularMarge, bottom = RegularMarge)
            .border(
                width = ThinBorder,
                color = MaterialTheme.colors.onPrimary,
                shape = RoundedCornerShape(RegularMarge)
            )
            .clickable { expanded = !expanded }
    ) {
        Text(
            text = selectedItem.name,
            color = MaterialTheme.colors.onPrimary,
            modifier = Modifier.padding(start = RegularMarge, end = RegularMarge),
            style = MaterialTheme.typography.h3
        )
        Spacer(modifier = Modifier.width(LittleMarge))
        Icon(
            imageVector = if (expanded) Icons.Outlined.KeyboardArrowUp else Icons.Outlined.KeyboardArrowDown,
            contentDescription = stringResource(R.string.collapseIconContentDescription),
            tint = MaterialTheme.colors.onPrimary
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .wrapContentWidth()
                .height(DropDownMenuHeight)

        ) {
            itemList.forEachIndexed { _, item ->
                DropdownMenuItem(onClick = {
                    onItemSelected(item)
                    expanded = false
                }) {
                    Text(
                        text = item.name,
                        style = MaterialTheme.typography.body1
                    )
                }
            }
        }
    }
}

@Composable
fun <T:BaseUiModel> PAMBaseDropDownMenuWithBackground(
    selectedItem: T,
    itemList: List<T>,
    onItemSelected: (item: T) -> Unit,
    isError: Boolean = false,
    errorMessage: String = ""
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = RegularMarge, bottom = RegularMarge, end = RegularMarge)
            .background(
                color = MaterialTheme.colors.primary,
                shape = PopUpFieldBackgroundShape
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PAMBaseDropDownMenu(
            selectedItem = selectedItem,
            itemList = itemList,
            onItemSelected = onItemSelected
        )
        ErrorMessage(
            isError = isError,
            errorMsg = errorMessage
        )
    }
}