package gamelogic

import javafx.beans.property.SimpleIntegerProperty

class GameState {
    var levelProperty: SimpleIntegerProperty = SimpleIntegerProperty(1)
    var scoreProperty: SimpleIntegerProperty = SimpleIntegerProperty(0)
    var livesProperty: SimpleIntegerProperty = SimpleIntegerProperty(3)
    var level: Int
        get() {
            return levelProperty.get()
        }
        set(value) {
            levelProperty.set(value)
        }
    var score: Int
        get() {
            return scoreProperty.get()
        }
        set(value) {
            scoreProperty.set(value)
        }
    var lives: Int
        get() {
            return livesProperty.get()
        }
        set(value) {
            livesProperty.set(value)
        }
}
