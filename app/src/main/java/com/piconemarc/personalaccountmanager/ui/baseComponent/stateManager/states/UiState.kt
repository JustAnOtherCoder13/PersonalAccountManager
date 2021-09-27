package com.piconemarc.personalaccountmanager.ui.baseComponent.stateManager.states

import com.piconemarc.personalaccountmanager.R
import com.piconemarc.personalaccountmanager.ui.baseComponent.stateManager.UiState


object UiStates : UiState {

    enum class AmountTextFieldState : UiState {
        NAN,
        POSITIVE,
        NEGATIVE
    }

    enum class AddOperationPopUpState :UiState {
        COLLAPSED,
        EXPANDED
    }

    enum class SwitchButtonState : UiState {
        PUNCTUAL,
        RECURRENT
    }

    enum class AddOperationPopUpLeftSideIconState : UiState {
        OPERATION {
            override fun getIcon(): PAMIconButtons {
                return PAMIconButtons.OPERATION
            }
        },
        PAYMENT {
            override fun getIcon(): PAMIconButtons {
                return PAMIconButtons.PAYMENT
            }
        },
        TRANSFER {
            override fun getIcon(): PAMIconButtons {
                return PAMIconButtons.TRANSFER
            }
        };

        abstract fun getIcon () : PAMIconButtons
    }

}

enum class PAMIconButtons{
    OPERATION {
        override fun getIconContentDescription(): Int {
            return R.string.operationIconContentDescription
        }

        override fun getVectorIcon(): Int {
            return R.drawable.ic_outline_payments_24
        }

        override fun getIconName(): Int {
            return R.string.operation
        }
    },
    PAYMENT {
        override fun getIconContentDescription(): Int {
            return R.string.paymentIconContentDescription
        }

        override fun getVectorIcon(): Int {
            return R.drawable.ic_outline_ios_share_24
        }

        override fun getIconName(): Int {
            return R.string.payment
        }
    },
    TRANSFER {
        override fun getIconContentDescription(): Int {
            return R.string.transferIconContentDescription
        }

        override fun getVectorIcon(): Int {
            return R.drawable.ic_baseline_swap_horiz_24
        }

        override fun getIconName(): Int {
            return R.string.transfer
        }
    },
    HOME {
        override fun getIconContentDescription(): Int {
            return R.string.homeIconContentDescription
        }

        override fun getVectorIcon(): Int {
            return R.drawable.ic_outline_home_24
        }

        override fun getIconName(): Int {
            return R.string.homeIconContentDescription
        }
    },
    CHART {
        override fun getIconContentDescription(): Int {
            return R.string.chartIconContentDescription
        }

        override fun getVectorIcon(): Int {
            return R.drawable.ic_outline_bar_chart_24
        }

        override fun getIconName(): Int {
            return 0
        }
    },
    ADD {
        override fun getIconContentDescription(): Int {
            return R.string.addIconContentDescription
        }

        override fun getVectorIcon(): Int {
            return R.drawable.ic_baseline_add_24
        }

        override fun getIconName(): Int {
            return 0
        }
    };

    abstract fun getIconContentDescription(): Int
    abstract fun getVectorIcon(): Int
    abstract fun getIconName(): Int
}