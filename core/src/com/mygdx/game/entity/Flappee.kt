package com.mygdx.game.entity

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Circle

class Flappee(flappeeTexture: TextureRegion) {
    companion object {
        private const val COLLISION_RADIUS = 24f
        private const val DIVE_ACCEL = 0.3f
        private const val FLY_ACCEL = 5f
        private const val TILE_WIDTH = 118
        private const val TILE_HEIGHT = 118
        private const val FRAME_DURATION = 0.25f
    }

    private var animation: Animation<TextureRegion>
    private var animationTimer = 0f
    private var ySpeed = 0f

    var boundCircle: Circle
        private set

    var x = 0f
        set(value) {
            field = value
            updateCollisionCircle()
        }


    var y = 0f
        set(value) {
            field = value
            updateCollisionCircle()
        }

    init {
        val splitTextureRegion = TextureRegion(flappeeTexture).split(TILE_WIDTH, TILE_HEIGHT)
        animation = Animation(FRAME_DURATION, splitTextureRegion[0][0], splitTextureRegion[0][1])
        animation.playMode = Animation.PlayMode.LOOP

        boundCircle = Circle(x, y, COLLISION_RADIUS)
    }

    fun setPosition(x: Float, y: Float) {
        this.x = x
        this.y = y
    }

    fun update(delta : Float) {
        animationTimer += delta
        ySpeed -= DIVE_ACCEL
        setPosition(x, y + ySpeed)
    }

    fun flyUp() {
        ySpeed = FLY_ACCEL
        y += ySpeed
    }

//    fun getCollisionCircle() = boundCircle

    fun drawDebug(shapeRenderer: ShapeRenderer) {
        shapeRenderer.circle(boundCircle.x, boundCircle.y, boundCircle.radius)
    }

    fun draw(batch: SpriteBatch) {
        val flappeeTextureRegion = animation.getKeyFrame(animationTimer)
        val textureX = boundCircle.x - flappeeTextureRegion.regionWidth / 2
        val textureY = boundCircle.y - flappeeTextureRegion.regionHeight / 2
        batch.draw(flappeeTextureRegion, textureX, textureY)
    }

    private fun updateCollisionCircle() = boundCircle.setPosition(x, y)


}