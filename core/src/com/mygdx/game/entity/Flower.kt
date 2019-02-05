package com.mygdx.game.entity

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.Intersector
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Rectangle

class Flower(private var floorTexture: TextureRegion, private var ceilingTexture: TextureRegion) {
    companion object {
        private const val COLLISION_RECTANGLE_WIDTH = 13f
        private const val COLLISION_RECTANGLE_HEIGHT = 447f
        private const val HEIGHT_OFFSET = -400f
        private const val COLLISION_CIRCLE_RADIUS = 33f
        private const val MAX_SPEED_PER_SECOND = 100f
        private const val DISTANCE_BETWEEN_FLOOR_AND_CEILING = 225f
        const val WIDTH = COLLISION_CIRCLE_RADIUS * 2f
    }

    private lateinit var floorCollisionCircle: Circle
    private lateinit var floorCollisionRectangle: Rectangle
    private lateinit var ceilingCollisionCircle: Circle
    private lateinit var ceilingCollisionRectangle: Rectangle


    var pointClaimed = false

    var x = 0f
        set(value) {
            field = value
            updateCollisionCircle()
            updateCollisionRectangle()
        }
    var y = 0f

    init {

        y = MathUtils.random(HEIGHT_OFFSET)

        floorCollisionRectangle = Rectangle(x,
                y,
                COLLISION_RECTANGLE_WIDTH,
                COLLISION_RECTANGLE_HEIGHT)

        floorCollisionCircle = Circle(x + floorCollisionRectangle.width / 2,
                y + floorCollisionRectangle.height,
                COLLISION_CIRCLE_RADIUS)

        ceilingCollisionRectangle = Rectangle(x,
                floorCollisionCircle.y + DISTANCE_BETWEEN_FLOOR_AND_CEILING,
                COLLISION_RECTANGLE_WIDTH,
                COLLISION_RECTANGLE_HEIGHT)

        ceilingCollisionCircle = Circle(x + ceilingCollisionRectangle.width,
                ceilingCollisionRectangle.y,
                COLLISION_CIRCLE_RADIUS)

    }

    private fun updateCollisionCircle() {
        floorCollisionCircle.x = x + floorCollisionRectangle.width / 2f
        ceilingCollisionCircle.x = x + ceilingCollisionRectangle.width / 2f
    }

    private fun updateCollisionRectangle() {
        floorCollisionRectangle.x = x
        ceilingCollisionRectangle.x = x
    }

    private fun drawFloorFlower(batch: SpriteBatch) {
        val textureX = floorCollisionCircle.x - floorTexture.regionWidth / 2
        val textureY = floorCollisionRectangle.y + COLLISION_CIRCLE_RADIUS
        batch.draw(floorTexture, textureX, textureY)
    }

    private fun drawCeilingFlower(batch: SpriteBatch) {
        val textureX = ceilingCollisionCircle.x - ceilingTexture.regionWidth / 2
        val textureY = ceilingCollisionRectangle.y - COLLISION_CIRCLE_RADIUS
        batch.draw(ceilingTexture, textureX, textureY)
    }

    fun drawDebug(shapeRenderer: ShapeRenderer) {
        shapeRenderer.circle(floorCollisionCircle.x,
                floorCollisionCircle.y,
                floorCollisionCircle.radius)

        shapeRenderer.rect(floorCollisionRectangle.x,
                floorCollisionRectangle.y,
                floorCollisionRectangle.width,
                floorCollisionRectangle.height)

        shapeRenderer.circle(ceilingCollisionCircle.x,
                ceilingCollisionCircle.y,
                ceilingCollisionCircle.radius)

        shapeRenderer.rect(ceilingCollisionRectangle.x,
                ceilingCollisionRectangle.y,
                ceilingCollisionRectangle.width,
                ceilingCollisionRectangle.height)
    }

    fun draw(batch: SpriteBatch) {
        drawFloorFlower(batch)
        drawCeilingFlower(batch)
    }

    fun update(delta: Float) {
        x -= MAX_SPEED_PER_SECOND * delta
    }

    fun isFlappeeColliding(flappee: Flappee): Boolean {
        val flappeeCollisionCircle = flappee.boundCircle

        return Intersector.overlaps(flappeeCollisionCircle, ceilingCollisionCircle) ||
                Intersector.overlaps(flappeeCollisionCircle, floorCollisionCircle) ||
                Intersector.overlaps(flappeeCollisionCircle, ceilingCollisionRectangle) ||
                Intersector.overlaps(flappeeCollisionCircle, floorCollisionRectangle)
    }


}