import javafx.animation.AnimationTimer
import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.canvas.GraphicsContext
import javafx.scene.control.Label
import javafx.scene.image.Image
import javafx.scene.input.KeyCode
import javafx.stage.Stage

class SpaceInvaders : Application() {

    private lateinit var gameScene: Scene
    private lateinit var graphicsContext: GraphicsContext

    // Game properties
    private var screenWidth = 800.0
    private var screenHeight = 600.0
    private val playerSpeed = 5.0
    private var playerX = 0.0
    private val playerY = screenHeight - 50.0
    private var playerBulletX = 0.0
    private var playerBulletY = 0.0
    private var alienSpeed = 2.0

    // Game state
    private var playerScore = 0
    private var level = 1
    private var numShips = 3
    private var aliens = mutableListOf<Enemy>()

    // Keyboard input state
    private val keys = BitSet()

    override fun start(stage: Stage) {
        val root = Group()
        val canvas = Canvas(screenWidth, screenHeight)
        graphicsContext = canvas.graphicsContext2D
        root.children.add(canvas)
        gameScene = Scene(root)
        stage.apply {
            scene = gameScene
            icons.add(Image("logo.png"))
            width = 1000.0
            height = 800.0
            minWidth = 320.0
            minHeight = 240.0
            maxWidth = 1600.0
            maxHeight = 1200.0
        }.show()

        // Set up keyboard input handlers
        gameScene.setOnKeyPressed { event ->
            keys.set(event.code.ordinal, true)
        }
        gameScene.setOnKeyReleased { event ->
            keys.set(event.code.ordinal, false)
        }

        // Game loop
        object : AnimationTimer() {
            override fun handle(now: Long) {
                update()
                render()
            }
        }.start()
    }


    private fun update() {
        // Handle player input
        if (isKeyPressed(KeyCode.A)) {
            playerX -= playerSpeed
        }
        if (isKeyPressed(KeyCode.D)) {
            playerX += playerSpeed
        }
        if (isKeyPressed(KeyCode.SPACE)) {
            // Fire bullet
            if (playerBulletY <= 0) {
                playerBulletX = playerX + 25.0 // Adjust bullet position based on player sprite
                playerBulletY = playerY - 10.0 // Adjust bullet position based on player sprite
            }
        }

        // Update player bullet position
        if (playerBulletY > 0) {
            playerBulletY -= 5.0 // Adjust bullet speed
        }

        // Update alien positions
        for (alien in aliens) {
            alien.x += alienSpeed
            if (alien.y >= screenHeight) {
                // Handle alien reaching the bottom of the screen
                // Reduce player ships and reset alien positions
            }
        }

        // Check for collisions

        // Update game state based on collisions and player actions

        // Check game over conditions
    }
}

fun main(args: Array<String>) {
    Application.launch(SpaceInvaders::class.java, *args)
}