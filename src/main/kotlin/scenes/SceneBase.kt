package scenes

import javafx.scene.Scene
import javafx.scene.layout.BorderPane

open class SceneBase : Scene(BorderPane(), SceneManager.screenWidth, SceneManager.screenHeight) {
    init {
        stylesheets.add(javaClass.getResource("/styles/main.css")?.toExternalForm() ?: "")
    }

    open fun dispose() { }
}
