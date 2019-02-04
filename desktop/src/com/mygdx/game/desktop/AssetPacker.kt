package com.mygdx.game.desktop

import com.badlogic.gdx.tools.texturepacker.TexturePacker

object AssetPacker {
    const val DRAW_DEBUG_OUTLINE = false
    const val RAW_ASSETS_PATH = "android/assets"
    const val ASSETS_PATH = "android/assets"
}

fun main(args: Array<String>) {
    val settings = TexturePacker.Settings().apply {
        debug = AssetPacker.DRAW_DEBUG_OUTLINE
    }

    TexturePacker.process(settings,
            "${AssetPacker.RAW_ASSETS_PATH}/",
            "${AssetPacker.ASSETS_PATH}/",
            "flappee_bee_assets")
}