package com.mygdx.game.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.viewport.FitViewport
import com.mygdx.game.FlappeeBeeGame
import com.mygdx.game.config.GameConfig
import ktx.app.KtxScreen

class StartScreen(private val game: FlappeeBeeGame) : KtxScreen {

    private var stage = Stage(FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT))
    private var backgroundTexture = Texture(Gdx.files.internal("images/bg.png"))
    private var titleTexture = Texture(Gdx.files.internal("images/title.png"))
    private var playTexture = Texture(Gdx.files.internal("images/play.png"))
    private var playPressTexture = Texture(Gdx.files.internal("images/playPress.png"))
    private var play = ImageButton(TextureRegionDrawable(TextureRegion(playTexture)),
            TextureRegionDrawable(TextureRegion(playPressTexture)))

    var background = Image(backgroundTexture)
    var title = Image(titleTexture)

    init {

        Gdx.input.inputProcessor = stage
        play.setPosition(GameConfig.WORLD_WIDTH / 2, GameConfig.WORLD_HEIGHT / 4, Align.center)
        title.setPosition(GameConfig.WORLD_WIDTH / 2, 3 * GameConfig.WORLD_HEIGHT / 4, Align.center)
        stage.addActor(background)
        stage.addActor(title)
        stage.addActor(play)

        play.addListener(object : ActorGestureListener() {
            override fun tap(event: InputEvent?, x: Float, y: Float, count: Int, button: Int) {
                super.tap(event, x, y, count, button)
                game.addScreen(LoadingScreen(game))
                game.setScreen<LoadingScreen>()
                dispose()
            }
        })
    }

    override fun resize(width: Int, height: Int) {
        stage.viewport.update(width, height)
    }

    override fun render(delta: Float) {
        stage.act()
        stage.draw()
    }

    override fun dispose() {
        stage.dispose()
        backgroundTexture.dispose()
        playTexture.dispose()
        playPressTexture.dispose()
        titleTexture.dispose()
    }
}