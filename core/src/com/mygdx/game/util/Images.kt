package com.mygdx.game.util

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import ktx.assets.getAsset
import ktx.assets.load

enum class Images {
    bee,
    bg,
    flowerBottom,
    flowerTop,
    play,
    playPress,
    title;

    val path = "images/${name}.png"
    fun load() = manager.load<Texture>(path)
    operator fun invoke() = manager.getAsset<Texture>(path)

    companion object {
        lateinit var manager: AssetManager
    }
}