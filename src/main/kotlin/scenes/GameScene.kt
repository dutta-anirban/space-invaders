package scenes

import gamelogic.GameModel
import SceneManager
import gamelogic.GameObject
import javafx.application.Platform
import javafx.beans.binding.Bindings
import javafx.geometry.Insets
import javafx.scene.Node
import javafx.scene.control.*
import javafx.scene.image.Image
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.layout.*
import javafx.scene.paint.Color

class GameScene: IGameScene, SceneBase() {
    private val model: GameModel
    private val gamePane: Pane = Pane()
    private val dialogPane: Pane = Pane()

    init {
        // Replace root pane with a StackPane
        root = StackPane()
        (root as StackPane).children.addAll(BorderPane().apply {
            top = HBox().apply {
                spacing = 20.0
                padding = Insets(10.0, 10.0, 10.0, 10.0)
                children.addAll(
                    Label().apply {
                        textProperty().bind(SceneManager.state.scoreProperty.asString("Score: %d"))
                        styleClass.addAll("label-base", "label-info")
                    },
                    HBox().apply { HBox.setHgrow(this, Priority.ALWAYS) },
                    Label().apply {
                        textProperty().bind(SceneManager.state.levelProperty.asString("LEVEL %d"))
                        styleClass.addAll("label-base", "label-info")
                    },
                    HBox().apply { HBox.setHgrow(this, Priority.ALWAYS) },
                    Label().apply {
                        textProperty().bind(SceneManager.state.livesProperty.asString("Lives: %d"))
                        styleClass.addAll("label-base", "label-info")
                    }
                )
            }
            center = gamePane
            background = Background(BackgroundFill(Color.BLACK, CornerRadii(0.0), Insets(0.0)))
        }, dialogPane)

        // Register event handlers
        addEventHandler(KeyEvent.KEY_PRESSED) { event: KeyEvent -> onKeyPressed(event.code) }
        addEventHandler(KeyEvent.KEY_RELEASED) { event: KeyEvent -> onKeyReleased(event.code) }

        // NOTE: Put this last, after we have inited everything!
        model = GameModel(this)
        model.startGame()
    }

    private fun onKeyPressed(code: KeyCode) = when (code) {
        KeyCode.LEFT, KeyCode.A -> model.notifyInput(GameModel.PlayerInput.LEFT)
        KeyCode.RIGHT, KeyCode.D -> model.notifyInput(GameModel.PlayerInput.RIGHT)
        KeyCode.UP, KeyCode.SPACE -> model.notifyInput(GameModel.PlayerInput.SHOOT)
        KeyCode.HOME, KeyCode.H -> {
            when(model.gameState) {
                GameModel.GameState.PLAYING -> {}
                else -> SceneManager.switchScene(StartScene())
            }
        }
        KeyCode.ESCAPE, KeyCode.Q -> {
            when(model.gameState) {
                GameModel.GameState.PLAYING -> SceneManager.switchScene(StartScene())
                else -> Platform.exit()
            }
        }
        KeyCode.ENTER -> {
            when(model.gameState) {
                GameModel.GameState.PLAYING -> {}
                GameModel.GameState.WIN -> {
                    if (SceneManager.state.level < 3) {
                        SceneManager.state.level++
                    } else {
                        SceneManager.state.lives = 3
                        SceneManager.state.level = 1
                        SceneManager.state.score = 0
                    }
                    SceneManager.switchScene(GameScene())
                }
                GameModel.GameState.LOSE -> {
                    SceneManager.state.lives = 3
                    SceneManager.state.level = 1
                    SceneManager.state.score = 0
                    SceneManager.switchScene(GameScene())
                }
            }
        }
        else -> { }
    }

    private fun onKeyReleased(code: KeyCode) = when(code) {
        KeyCode.LEFT, KeyCode.A -> model.clearInput(GameModel.PlayerInput.LEFT)
        KeyCode.RIGHT, KeyCode.D -> model.clearInput(GameModel.PlayerInput.RIGHT)
        KeyCode.UP, KeyCode.SPACE -> model.clearInput(GameModel.PlayerInput.SHOOT)
        else -> { }
    }

    private fun getDialogTemplate(vararg elements: Node) = VBox().apply {
        spacing = 5.0
        alignment = javafx.geometry.Pos.CENTER
        styleClass.add("popup-vbox")
        background = Background(
            BackgroundImage(
                Image("images/StarfieldSimulation.gif"),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
            )
        )
        layoutXProperty().bind(
            Bindings.createDoubleBinding({ (SceneManager.screenWidth - width) / 2 }, widthProperty()))
        layoutYProperty().bind(
            Bindings.createDoubleBinding({ (SceneManager.screenHeight - height) / 2 }, heightProperty()))
        children.addAll(*elements)
    }

    override fun showEndGameDialog() { when(model.gameState) {
        GameModel.GameState.WIN -> dialogPane.children.add(getDialogTemplate(
            Label("YOU WON").apply {
                styleClass.addAll("label-base", "label-header")
                textFill = Color.GREENYELLOW
            },
            Label().apply {
                text = if (SceneManager.state.level == 3) {
                    "FINAL SCORE: ${SceneManager.state.score}"
                } else {
                    "CURRENT SCORE: ${SceneManager.state.score}"
                }
                styleClass.addAll("label-base", "label-scoreboard")
            },
            Separator(javafx.geometry.Orientation.HORIZONTAL),
            Label().apply {
                text = if (SceneManager.state.level == 3) {
                    "[ENTER] - Play Game from Level 1"
                } else {
                    "[ENTER] - Go to Level ${SceneManager.state.level+1}"
                }
                styleClass.addAll("label-base", "label-title")
            },
            Label("[H] or [HOME] - Home Screen").apply {
                styleClass.addAll("label-base", "label-title")
            },
            Label("[Q] or [ESCAPE] - Quit Game").apply {
                styleClass.addAll("label-base", "label-title")
            }
        ))
        GameModel.GameState.LOSE -> dialogPane.children.add(getDialogTemplate(
            Label("GAME OVER").apply {
                styleClass.addAll("label-base", "label-header")
                textFill = Color.RED
            },
            Label("FINAL SCORE: ${SceneManager.state.score}").apply {
                styleClass.addAll("label-base", "label-scoreboard")
            },
            Separator(javafx.geometry.Orientation.HORIZONTAL),
            Label("[ENTER] - Restart Game from Level 1").apply {
                styleClass.addAll("label-base", "label-title")
            },
            Label("[H] or [HOME] - Home Screen").apply {
                styleClass.addAll("label-base", "label-title")
            },
            Label("[Q] or [ESCAPE] - Quit Game").apply { styleClass.addAll("label-base", "label-title") }
        ))
        else -> { }
    }}

    override fun dispose() {
        model.pauseGame()
    }

    override fun addGameObject(gameObject: GameObject) {
        gamePane.children.add(gameObject.sprite)
    }

    override fun removeGameObject(gameObject: GameObject) {
        gamePane.children.remove(gameObject.sprite)
    }
}
