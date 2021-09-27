package com.piconemarc.personalaccountmanager.ui.baseComponent.popUp.animation

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

interface TransitionData

object TransitionsData : TransitionData {

    class AddOperationPopUpTransitionData(
        alpha: State<Float>,
        size: State<Dp>,
        position: State<Dp>,
    ) : TransitionData {
        val alpha by alpha
        val size by size
        val position by position
    }


    class AmountTextFieldTransitionData(
        backgroundColor: State<Color>,
        textColor: State<Color>,
    ) : TransitionData {
        val backgroundColor by backgroundColor
        val textColor by textColor
    }

    class RecurrentButtonTransitionData(
        buttonSize: State<Dp>, leftBottomCornerSize: State<Dp>
    ) : TransitionData {
        val buttonSize by buttonSize
        val leftBottomCornerSize by leftBottomCornerSize
    }

}