package com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent

import android.util.Log
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.piconemarc.model.entity.PresentationDataModel
import com.piconemarc.personalaccountmanager.R
import com.piconemarc.personalaccountmanager.ui.theme.*

@Composable
fun PAMBaseDropDownMenu(
    selectedItem: PresentationDataModel,
    itemList: List<PresentationDataModel>,
    onItemSelected: (item: PresentationDataModel) -> Unit
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
            text = selectedItem.stringValue,
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
                        text = item.stringValue,
                        style = MaterialTheme.typography.body1
                    )
                }
            }
        }
    }
}

@Composable
fun PAMBaseDropDownMenuWithBackground(
    selectedItem: PresentationDataModel,
    itemList: List<PresentationDataModel>,
    onItemSelected: (item: PresentationDataModel) -> Unit
) {
    Log.i("TAG", "PAMBaseDropDownMenuWithBackground:$itemList")
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = RegularMarge, bottom = RegularMarge, end = RegularMarge)
            .background(
                color = MaterialTheme.colors.primary,
                shape = PopUpFieldBackgroundShape
            ),
        horizontalArrangement = Arrangement.Center
    ) {
        PAMBaseDropDownMenu(
            selectedItem = selectedItem,
            itemList = itemList,
            onItemSelected = onItemSelected
        )
    }
}