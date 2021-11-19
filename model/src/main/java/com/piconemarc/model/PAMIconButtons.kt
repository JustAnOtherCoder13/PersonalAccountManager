package com.piconemarc.model

sealed class PAMIconButtons {

    open val iconContentDescription: Int = 0
    open val vectorIcon: Int = 0
    open val iconName: Int = 0

    object Operation : PAMIconButtons() {
        override val iconContentDescription: Int = R.string.operationIconContentDescription
        override val vectorIcon: Int = R.drawable.ic_outline_payments_24
        override val iconName: Int = R.string.operation
    }

    object Payment : PAMIconButtons() {
        override val iconContentDescription: Int = R.string.paymentIconContentDescription
        override val vectorIcon: Int = R.drawable.ic_outline_payment_24
        override val iconName: Int = R.string.payment
    }

    object Transfer : PAMIconButtons() {
        override val iconContentDescription: Int = R.string.transferIconContentDescription
        override val vectorIcon: Int = R.drawable.ic_baseline_swap_horiz_24
        override val iconName: Int = R.string.transfer
    }

    object Home : PAMIconButtons() {
        override val iconContentDescription: Int = R.string.homeIconContentDescription
        override val vectorIcon: Int = R.drawable.ic_outline_home_24
        override val iconName: Int = R.string.homeInterlayerTitle
    }

    object Chart : PAMIconButtons() {
        override val iconContentDescription: Int = R.string.chartIconContentDescription
        override val vectorIcon: Int = R.drawable.ic_outline_bar_chart_24
        override val iconName: Int = R.string.chartInterlayerTitle
    }

    object Add : PAMIconButtons() {
        override val iconContentDescription: Int = R.string.addIconContentDescription
        override val vectorIcon: Int = R.drawable.ic_baseline_add_24
    }

    object Minus : PAMIconButtons() {
        override val iconContentDescription: Int = R.string.minusIconContentDescription
        override val vectorIcon: Int = R.drawable.ic_outline_minus_24
    }
    object Delete : PAMIconButtons() {
        override val iconContentDescription: Int = R.string.deleteIconContentDescription
        override val vectorIcon: Int = R.drawable.ic_outline_delete_24
    }
    object Back : PAMIconButtons(){
        override val iconContentDescription: Int = R.string.backIconButtonContentDescription
        override val vectorIcon: Int = R.drawable.ic_outline_arrow_back_24
    }
    object UpdatePayment : PAMIconButtons(){
        override val iconContentDescription: Int = R.string.updatePaymentContentDescription
        override val vectorIcon: Int = R.drawable.ic_payment_sync_24
    }
}