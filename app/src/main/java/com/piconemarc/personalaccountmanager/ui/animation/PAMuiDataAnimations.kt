package com.piconemarc.personalaccountmanager.ui.animation

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.piconemarc.viewmodel.viewModel.utils.UiDataAnimation

object PAMUiDataAnimations : UiDataAnimation {

    //Base------------------------------------------------------------------
    class BasePopUpAnimationData(
        alpha: State<Float>,
        size: State<Dp>,
        position: State<Dp>,
    ) : UiDataAnimation {
        val alpha by alpha
        val size by size
        val position by position
    }

    class BaseSwitchButtonData(
        buttonColor : State<Color>,
        textColor : State<Color>
    ){
        val buttonColor by buttonColor
        val textColor by textColor
    }

    //AddPopUp ----------------------------------------------------------------
    class AddOperationPopUpIconMenuPanelAnimationData(
        offset: State<Offset>
    ) : UiDataAnimation {
        val offset by offset
    }

    class AddOperationPopUpRecurrentButtonTransitionData(
        buttonSize: State<Dp>, leftBottomCornerSize: State<Dp>
    ) : UiDataAnimation {
        val buttonSize by buttonSize
        val leftBottomCornerSize by leftBottomCornerSize
    }

    class AddOperationPopUpAddOrMinusTransitionData(
        addIconColor : State<Color>,
        addBackgroundColor : State<Color>,
        minusIconColor : State<Color>,
        minusBackgroundColor : State<Color>,
    ){
        val addIconColor by addIconColor
        val addBackgroundColor by addBackgroundColor
        val minusIconColor by minusIconColor
        val minusBackgroundColor by minusBackgroundColor
    }
    //BaseAppScreen -----------------------------------------------------------
    class InterlayerAnimationData(
        interlayerColor: State<Color>,
        homeIconVerticalPosition : State<Dp>,
        paymentIconVerticalPosition : State<Dp>
    ) : UiDataAnimation {
        val interlayerColor by interlayerColor
        val homeIconVerticalPosition by homeIconVerticalPosition
        val paymentIconVerticalPosition by paymentIconVerticalPosition
    }
}