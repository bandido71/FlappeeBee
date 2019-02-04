package com.mygdx.game

import com.badlogic.gdx.Screen
import com.badlogic.gdx.assets.AssetManager
import com.mygdx.game.screen.GameScreen
import com.mygdx.game.screen.LoadingScreen
import com.mygdx.game.screen.StartScreen
import ktx.app.KtxGame

class FlappeeBeeGame : KtxGame<Screen>() {

    val assetManager = AssetManager()

    override fun create() {

        val startScreen = StartScreen(this)

//        val gameScreen = GameScreen(this)

//        addScreen(loadingScreen)
        addScreen(startScreen)

        setScreen<StartScreen>()

    }

    override fun dispose() {
        assetManager.dispose()
    }
}
