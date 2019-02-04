package com.mygdx.game.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.utils.viewport.FitViewport
import com.mygdx.game.FlappeeBeeGame
import com.mygdx.game.config.GameConfig
import com.mygdx.game.entity.Flappee
import com.mygdx.game.entity.Flower
import com.mygdx.game.util.Images
import ktx.app.KtxScreen
import ktx.app.clearScreen
import ktx.assets.getAsset
import ktx.assets.load
import ktx.collections.GdxArray
import ktx.graphics.use

class GameScreen(game: FlappeeBeeGame) : KtxScreen {

    private var shapeRendederer = ShapeRenderer()
    private var camera = OrthographicCamera()
    private var viewport = FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT, camera)
    private var batch = SpriteBatch()
    private var bitmapFont = BitmapFont()
    private var glyphLayout = GlyphLayout()
    private val assetManager = game.assetManager

    private var background: Texture
    private var flowerBottom: Texture
    private var flowerTop: Texture
    private var flappeeTexture: Texture

    private var flappee : Flappee
    private var flowers = GdxArray<Flower>()

    private var score = 0


    init {

        background = assetManager["bg.png"]
        flowerBottom = assetManager["flowerBottom.png"]
        flowerTop = assetManager["flowerTop.png"]
        flappeeTexture = assetManager["bee.png"]

        flappee = Flappee(flappeeTexture)
        flappee.setPosition(GameConfig.WORLD_WIDTH / 4, GameConfig.WORLD_HEIGHT / 2)

    }

    override fun render(delta: Float) {

        update(delta)
        clearScreen(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a)
        draw()
//        drawDebug()
    }

    private fun update(delta: Float) {
        updateFlappee(delta)
        updateFlowers(delta)
        updateScore()

        if (checkForCollision()) restart()
    }

    private fun restart() {
        flappee.setPosition(GameConfig.WORLD_WIDTH / 4, GameConfig.WORLD_HEIGHT / 2)
        flowers.clear()
        score = 0
    }

    private fun blockFlappeeLeavingTheWorld() {
        flappee.y = MathUtils.clamp(flappee.y, 0f, GameConfig.WORLD_HEIGHT)
    }

    private fun createNewFlower() {
        val newFlower = Flower(flowerBottom, flowerTop)
        newFlower.x = GameConfig.WORLD_WIDTH + Flower.WIDTH
        flowers.add(newFlower)
    }

    private fun checkIfNewFlowerIsNeeded() {
        if (flowers.size == 0) {
            createNewFlower()
        } else {
            val flower = flowers.peek()

            if (flower.x < GameConfig.WORLD_WIDTH - GameConfig.GAP_BETWEEN_FLOWERS) {
                createNewFlower()
            }
        }
    }

    private fun removeFlowerIfPassed() {
        if (flowers.size > 0) {
            val firstFlower = flowers.first()

            if (firstFlower.x < -Flower.WIDTH) {
                flowers.removeValue(firstFlower, true)
            }
        }
    }

    private fun updateFlowers(delta: Float) {
        flowers.forEach {
            it.update(delta)
        }

        checkIfNewFlowerIsNeeded()
        removeFlowerIfPassed()
    }

    private fun updateFlappee(delta: Float) {
        flappee.update(delta)

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) flappee.flyUp()
        blockFlappeeLeavingTheWorld()
    }

    private fun checkForCollision(): Boolean {
        flowers.forEach {
            if (it.isFlappeeColliding(flappee)) return true
        }
        return false
    }

    private fun updateScore() {
        val flower = flowers.first()
        if (flower.x < flappee.x && !flower.pointClaimed) {
            flower.pointClaimed = true
            score++
        }
    }

    private fun drawScore(){
        var scoreText = score.toString()
        glyphLayout.setText(bitmapFont, scoreText)
        bitmapFont.draw(batch,
                scoreText,
                (viewport.worldWidth - glyphLayout.width) / 2,
                (4 * viewport.worldHeight / 5) - glyphLayout.height / 2)
    }

    private fun drawDebug() {
        viewport.apply()
        shapeRendederer.projectionMatrix = camera.combined

        shapeRendederer.begin(ShapeRenderer.ShapeType.Line)

        flowers.forEach { it.drawDebug(shapeRendederer) }
        flappee.drawDebug(shapeRendederer)

        shapeRendederer.end()
    }

    private fun draw() {
        viewport.apply()
        batch.projectionMatrix = camera.combined
        batch.use {
            batch.draw(background, 0f, 0f)
            drawFlowers()
            flappee.draw(batch)
            drawScore()
        }

    }

    private fun drawFlowers() {
        flowers.forEach {
            it.draw(batch)
        }
    }

    override fun resize(width: Int, height: Int) {
        //camera.position.set(WORLD_WIDTH / 2f, WORLD_HEIGHT / 2f, 0f)
        viewport.update(width, height, true)
    }

    override fun dispose() {
        batch.dispose()
    }

}