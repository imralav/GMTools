package com.imralav.gmtools.gui.utils

import javafx.animation.FadeTransition
import javafx.scene.Node
import javafx.scene.control.Label
import javafx.util.Duration
import lombok.Setter
import org.springframework.stereotype.Service

@Service
class CurtainManager {
    lateinit var curtainNode: Node

    lateinit var curtainMessage: Label

    fun showCurtainFor(message: String?) {
        curtainNode.opacity = 1.0
        curtainMessage.text = message
    }

    fun fadeInCurtainFor(message: String?) {
        curtainMessage.text = message
        val ft = FadeTransition(Duration.millis(FADE_DURATION.toDouble()), curtainNode)
        ft.fromValue = 0.0
        ft.toValue = TARGET_OPACITY
        ft.cycleCount = 1
        ft.isAutoReverse = false
        ft.play()
    }

    fun fadeOutCurrentCurtain() {
        val ft = FadeTransition(Duration.millis(FADE_DURATION.toDouble()), curtainNode)
        ft.fromValue = curtainNode.opacity
        ft.toValue = 0.0
        ft.cycleCount = 1
        ft.isAutoReverse = false
        ft.play()
    }

    companion object {
        private const val FADE_DURATION = 1000
        private const val TARGET_OPACITY = 0.8
    }
}