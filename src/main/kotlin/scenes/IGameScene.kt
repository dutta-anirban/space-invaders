package scenes
import gamelogic.GameObject

interface IGameScene {
    fun addGameObject(gameObject: GameObject)
    fun removeGameObject(gameObject: GameObject)
    fun showEndGameDialog()
}