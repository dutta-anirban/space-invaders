package gamelogic

import SceneManager
import com.sun.javafx.geom.Vec2d

class Player : GameObject("images/player.png") {
    private val minPosX: Double = 0.0
    private val maxPosX: Double = SceneManager.screenWidth - 125

    init {
        // posX = SceneManager.screenWidth * 0.5 - width/2
        posX = Math.random() * (maxPosX - minPosX) + minPosX
        posY = SceneManager.screenHeight - 200.0
        velocity = Vec2d(0.0, 0.0)
    }

    override fun update(deltaTime: Double) {
        super.update(deltaTime)
        posX = kotlin.math.min(kotlin.math.max(posX, minPosX), maxPosX)
    }
}
