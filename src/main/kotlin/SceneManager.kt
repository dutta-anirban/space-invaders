import gamelogic.GameState
import javafx.scene.Scene
import javafx.stage.Stage
import scenes.SceneBase

// Global singleton instance of SceneManager
object SceneManager {
    // Global constants
    const val screenWidth = 1600.0 * (0.75)
    const val screenHeight = 1080.0 * (0.75)

    // Global game state
    val state: GameState = GameState()

    // Stage for main to modify
    internal var stage: Stage? = null

    // Switches the scene to the given scene
    fun switchScene(scene: Scene) {
        (stage!!.scene as SceneBase).dispose()
        stage!!.scene = scene
    }
}
