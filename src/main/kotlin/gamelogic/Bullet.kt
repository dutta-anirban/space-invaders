package gamelogic

import com.sun.javafx.geom.Vec2d

private fun getBulletImage(owner: GameObject): String = when (owner) {
    is Player -> "images/player_bullet.png"
    is Enemy -> {
        when(owner.enemyTier) {
            0 -> "images/bullet1.png"
            1 -> "images/bullet2.png"
            2 -> "images/bullet3.png"
            else -> TODO()
        }
    }
    else -> TODO()
}

class Bullet(val owner: GameObject) : GameObject(getBulletImage(owner)) {
    init {
        posX = owner.posX + owner.width/2 - width/2
        when (owner) {
            is Player -> {
                posY = owner.posY
                velocity = Vec2d(0.0, -Resources.PLAYERBULLETSPEED)
            }
            is Enemy -> {
                posY = owner.posY + owner.height/2 - height/2
                velocity = Vec2d(0.0, Resources.ENEMYBULLETSPEED)
            }
            else -> {
                TODO()
            }
        }
    }
}
