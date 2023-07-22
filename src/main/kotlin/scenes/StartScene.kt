package scenes

import SceneManager
import javafx.application.Platform
import javafx.collections.ObservableList
import javafx.geometry.Insets
import javafx.scene.Node
import javafx.scene.control.*
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.scene.text.Font

class StartScene : SceneBase() {

    private val _vbox: VBox = VBox().apply {
        spacing = 5.0
        alignment = javafx.geometry.Pos.CENTER
    }

    private val content: ObservableList<Node> get() = _vbox.children

    init {
        // Create root pane
        (root as BorderPane).apply {
            top = HBox().apply {
                children.add(ImageView(Image("images/logo.png"))).apply {
                    padding = Insets(100.0, 25.0, 0.0, 25.0)
                }
                alignment = javafx.geometry.Pos.CENTER
            }
            center = _vbox
            bottom = HBox().apply {
                children.add(Label("Implemented by Anirban Dutta (a9dutta / 20812066)").apply {
                    font = Font.font("Lucida Console", 12.0)
                    padding = Insets(5.0, 5.0, 10.0, 5.0)
                    textFill = Color.WHITE
                })
                alignment = javafx.geometry.Pos.CENTER
            }
            background = Background(
                BackgroundImage(
                    Image("images/StarfieldSimulation.gif"),
                    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                    BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
                )
            )
        }

        // Add content
        content.addAll(
            Label("INSTRUCTIONS").apply {
                font = Font.font("Lucida Console", 60.0)
                textFill = Color.WHITE
                padding = Insets(10.0, 10.0, 10.0, 10.0)
            },
            Separator(javafx.geometry.Orientation.HORIZONTAL),
            Label("[ENTER] - Start Game").apply {
                styleClass.addAll("label-base", "label-title")
            },
            Label("[A] or ◀ - Move ship left").apply {
                styleClass.addAll("label-base", "label-title")
            },
            Label("[D] or ▶ - Move ship right").apply {
                styleClass.addAll("label-base", "label-title")
            },
            Label("[SPACE BAR] - Fire").apply {
                styleClass.addAll("label-base", "label-title")
            },
            Label("[1] or [2] or [3] - Start Game at a specific level").apply {
                styleClass.addAll("label-base", "label-title")
            },
            Label("[Q] or [ESCAPE] - Quit Game").apply {
                styleClass.addAll("label-base", "label-title")
            }
        )
        // Add event handlers
        addEventHandler(KeyEvent.KEY_PRESSED) { event: KeyEvent ->
            when (event.code) {
                KeyCode.ESCAPE, KeyCode.Q -> {
                    Platform.exit()
                }

                KeyCode.ENTER, KeyCode.DIGIT1 -> {
                    SceneManager.state.level = 1
                    SceneManager.switchScene(GameScene())
                }

                KeyCode.DIGIT2 -> {
                    SceneManager.state.level = 2
                    SceneManager.switchScene(GameScene())
                }

                KeyCode.DIGIT3 -> {
                    SceneManager.state.level = 3
                    SceneManager.switchScene(GameScene())
                }

                else -> {}
            }
        }
    }
}
