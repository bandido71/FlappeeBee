package com.mygdx.game.screen

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.viewport.FitViewport
import com.mygdx.game.FlappeeBeeGame
import com.mygdx.game.config.GameConfig
import ktx.app.KtxScreen
import ktx.app.clearScreen
import ktx.assets.load
import ktx.assets.toInternalFile

class LoadingScreen(private val game: FlappeeBeeGame) : KtxScreen {
    companion object {
        private const val PROGRESS_BAR_WIDTH = 100f
        private const val PROGRESS_BAR_HEIGHT = 25f
    }

    private var camera = OrthographicCamera()
    private var viewport = FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT, camera)
    private var shapeRenderer = ShapeRenderer()

    private val assetManager = game.assetManager
    private var progress = 0f

    init {
        camera.position.set(GameConfig.WORLD_WIDTH / 2, GameConfig.WORLD_HEIGHT / 2, 0f)
        camera.update()
        assetManager.load<BitmapFont>("font/score.fnt")
        assetManager.load<TextureAtlas>("images/flappee_bee_assets.atlas")
    }

    override fun render(delta: Float) {
        update(delta)
        clearScreen(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a)
        draw()
    }

    private fun update(delta: Float) {
        if (assetManager.update()) {
            game.addScreen(GameScreen(game))
            game.setScreen<GameScreen>()

        } else {
            progress = assetManager.progress
        }
    }

    private fun draw() {
        viewport.apply()
        shapeRenderer.projectionMatrix = camera.combined

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)

        shapeRenderer.color = Color.WHITE
        shapeRenderer.rect((GameConfig.WORLD_WIDTH - PROGRESS_BAR_WIDTH) / 2f,
                (GameConfig.WORLD_HEIGHT - PROGRESS_BAR_HEIGHT) / 2f,
                progress * PROGRESS_BAR_WIDTH,
                PROGRESS_BAR_HEIGHT)

        shapeRenderer.end()
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height, true)
    }

    override fun dispose() {
        shapeRenderer.dispose()
    }

}