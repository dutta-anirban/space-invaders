package gamelogic

import com.sun.javafx.geom.Vec2d
import javafx.beans.property.SimpleDoubleProperty
import javafx.scene.image.ImageView

open class GameObject(image: String) {
    // Private properties
    private val posXProperty = SimpleDoubleProperty()
    private val posYProperty = SimpleDoubleProperty()
    private val _sprite: ImageView = ImageView(image).apply {
        scaleX = 0.5
        scaleY = 0.5
        layoutXProperty().bind(posXProperty)
        layoutYProperty().bind(posYProperty)
    }

    // Public properties
    var posX: Double
        set(value) {
            posXProperty.set(value)
        }
        get() { return posXProperty.get() }
    var posY: Double
        set(value) {
            posYProperty.set(value)
        }
        get() { return posYProperty.get() }
    val sprite: ImageView get() { return _sprite }
    val height: Double get() { return sprite.image.height }
    val width: Double get() { return sprite.image.width }
    var velocity: Vec2d = Vec2d(0.0, 0.0)
    // Update animations
    open fun update(deltaTime: Double) {
        posX += velocity.x * deltaTime
        posY += velocity.y * deltaTime
    }

    // Intersects
    fun intersects(other: GameObject): Boolean {
        return sprite.boundsInParent.intersects(other.sprite.boundsInParent)
    }
}