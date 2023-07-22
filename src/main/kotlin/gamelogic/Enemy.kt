package gamelogic

import SceneManager
import com.sun.javafx.geom.Vec2d
import javafx.beans.property.SimpleIntegerProperty

class Enemy(layerX: Int, layerY: Int) : GameObject(
    when(layerY) {
        0 -> "images/enemy3.png"
        1,2 -> "images/enemy2.png"
        else -> "images/enemy1.png"
    }
) {
    private val spriteWidth = 60.0
    private val spriteHeight = 50.0
    private val minPosX: Double = layerX*spriteWidth + (if(layerY == 0) 10 else 0)
    private val maxPosX: Double = SceneManager.screenWidth - (11.0-layerX)*spriteWidth + (if(layerY == 0) 10 else 0)

    val deathScore: Int = when(layerY) {
        0 -> 3
        1,2 -> 2
        else -> 1
    } * 100 * SceneManager.state.level
    private var velxsgn = 1
    private val baseVelocityX = 80.0 * SceneManager.state.level
    val enemiesDead = SimpleIntegerProperty(0)
    val enemyTier = when(layerY) {
        0 -> 2
        1,2 -> 1
        else -> 0
    }

    init {
        sprite.scaleX = 0.4
        sprite.scaleY = 0.4
        posX = minPosX
        posY = layerY * spriteHeight
        velocity = Vec2d(0.0, 1.0)
    }

    override fun update(deltaTime: Double) {
        velocity.x = (baseVelocityX + Resources.ENEMYSPEEDMOD * enemiesDead.value) * velxsgn
        super.update(deltaTime)
        if(posX < minPosX || posX > maxPosX) {
            velxsgn = -velxsgn
            velocity.y += 1.0
            posY += 20.0
        }
        posX = kotlin.math.max(kotlin.math.min(posX, maxPosX), minPosX)
    }
}
