import javafx.application.Application
import javafx.scene.image.Image
import javafx.stage.Stage
import scenes.GameScene
import scenes.StartScene

class SpaceInvaders : Application() {
    override fun start(stage: Stage) {
        // Initialize SceneManager
        SceneManager.stage = stage
        stage.apply {
            title = "Space Invaders - Anirban Dutta - a9dutta - 20813366"
            scene = StartScene()
            //scene = GameScene()
            icons.add(Image("images/logo.png"))
            width = SceneManager.screenWidth
            height = SceneManager.screenHeight
            isResizable = false
        }.show()
    }
}

fun main(args: Array<String>) {
    Application.launch(SpaceInvaders::class.java, *args)
}