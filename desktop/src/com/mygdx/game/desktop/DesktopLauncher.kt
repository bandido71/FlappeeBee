package com.mygdx.game.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.mygdx.game.FlappeeBeeGame

object DesktopLauncher {
    @JvmStatic
    fun main(arg: Array<String>) {
        val config = LwjglApplicationConfiguration()
        config.height = 320
        config.width = 240
        LwjglApplication(FlappeeBeeGame(), config)
    }
}
