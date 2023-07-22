package gamelogic

import SceneManager
import com.sun.javafx.geom.Vec2d
import javafx.animation.AnimationTimer
import javafx.beans.property.SimpleIntegerProperty
import javafx.scene.media.MediaPlayer
import javafx.util.Duration
import scenes.IGameScene

private fun playSound(sound: MediaPlayer) {
    sound.seek(Duration.ZERO)
    sound.play()
}

class GameModel(private val scene: IGameScene) {

    // Declare enum classes for PlayerInput and GameState
    enum class PlayerInput { LEFT, RIGHT, SHOOT }
    enum class GameState { PLAYING, WIN, LOSE }

    // Gets time until next enemy bullet
    private val nextEnemyShootTimeout: Double get() {
        return Math.random() * Resources.ENEMYFIRERATE * SceneManager.state.level
    }

    // Timers to rate-limit bullet firing
    private val playerShootTimeout = Resources.PLAYERFIRERATE
    private var enemyShootTimeout = nextEnemyShootTimeout

    // Private variables
    private var playerObj = Player()
    private val entityObjs = mutableListOf<GameObject>()
    private val bulletObjs = mutableListOf<Bullet>()
    private val inputMap = mutableMapOf<PlayerInput, Boolean>()
    private var playerShootTimer = Double.MAX_VALUE
    private var enemyShootTimer = Double.MAX_VALUE
    private var enemiesDead = SimpleIntegerProperty(0)
    var gameState = GameState.PLAYING
        private set

    init {
        addGameObject(playerObj)
        for (y in 0..4) {
            for (x in 0..9) {
                val enemy = Enemy(x, y)
                enemy.enemiesDead.bind(enemiesDead)
                addGameObject(enemy)
            }
        }
    }

    fun notifyInput(input: PlayerInput) { inputMap[input] = true; }
    fun clearInput(input: PlayerInput) { inputMap[input] = false; }
    fun startGame() { animationTimer.start() }
    fun pauseGame() { animationTimer.stop() }

    private fun update(deltaTime: Double) {
        if (gameState != GameState.PLAYING) return

        // Respond to inputs
        if (inputMap[PlayerInput.LEFT] == true)
            playerObj.velocity = Vec2d(-Resources.PLAYERSPEED, 0.0)
        else if (inputMap[PlayerInput.RIGHT] == true)
            playerObj.velocity = Vec2d(Resources.PLAYERSPEED, 0.0)
        else
            playerObj.velocity = Vec2d(0.0, 0.0)
        if (inputMap[PlayerInput.SHOOT] == true && playerShootTimer >= playerShootTimeout) {
            val bullet = Bullet(playerObj)
            addGameObject(bullet)
            playSound(Resources.shootSound)
            playerShootTimer = 0.0
        }

        // Choose a random enemy to shoot a bullet
        if (enemyShootTimer >= enemyShootTimeout) {
            val enemy = entityObjs.filterIsInstance<Enemy>().random()
            val bullet = Bullet(enemy)
            addGameObject(bullet)
            playSound(Resources.fastInvader1Sound)
            enemyShootTimer = 0.0
            enemyShootTimeout = nextEnemyShootTimeout
        }

        // Update bullet timeouts
        playerShootTimer += deltaTime
        enemyShootTimer += deltaTime

        // Update all positions
        entityObjs.forEach { it.update(deltaTime) }
        bulletObjs.forEach { it.update(deltaTime) }

        // Check for collisions between bullets and everything else, and between player and enemies
        val deadBullets = mutableListOf<Bullet>()
        val deadEntities = mutableListOf<GameObject>()
        for (entity in entityObjs) {
            if ((entity is Enemy) && entity.intersects(playerObj)) {
                deadEntities.add(playerObj)
                break
            }
            if((entity is Enemy) && entity.posY > SceneManager.screenHeight - 200.0) {
                gameState = GameState.LOSE
                scene.showEndGameDialog()
                break
            }
            for (bullet in bulletObjs) {
                if(bullet.posY > SceneManager.screenHeight + 20.0 || bullet.posY < -20.0) {
                    deadBullets.add(bullet)
                } else if ((entity is Player && bullet.owner is Player) || (entity is Enemy && bullet.owner is Enemy)) {
                    continue
                } else if (entity.intersects(bullet)) {
                    deadEntities.add(entity)
                    deadBullets.add(bullet)
                }
            }
        }

        // Remove bullets and players that have hit enemy or gone offscreen
        deadBullets.forEach(::removeGameObject)
        deadEntities.forEach(::removeGameObject)

        // Call collision handlers on appropriate entities
        deadEntities.forEach {
            if (it is Player) onPlayerHit() else if (it is Enemy) onEnemyHit(it)
        }
    }

    private fun onPlayerHit() {
        playSound(Resources.explosionSound)
        SceneManager.state.lives--
        if (SceneManager.state.lives <= 0) {
            gameState = GameState.LOSE
            scene.showEndGameDialog()
        } else {
            playerObj = Player()
            addGameObject(playerObj)
        }
    }

    private fun onEnemyHit(enemy: Enemy) {
        playSound(Resources.invaderKilledSound)
        if (entityObjs.size == 1 && entityObjs[0] is Player) {
            gameState = GameState.WIN
            scene.showEndGameDialog()
        } else {
            SceneManager.state.score += enemy.deathScore
            enemiesDead.value++
        }
    }

    private fun addGameObject(obj: GameObject) {
        if(obj is Bullet) {
            bulletObjs.add(obj)
        } else {
            entityObjs.add(obj)
        }
        scene.addGameObject(obj)
    }

    private fun removeGameObject(obj: GameObject) {
        if(obj is Bullet) {
            bulletObjs.remove(obj)
        } else {
            entityObjs.remove(obj)
        }
        scene.removeGameObject(obj)
    }

    private val animationTimer = object : AnimationTimer() {
        private var lastRun: Long = 0
        private val frameNs: Double = 1e9 / 60
        override fun handle(now: Long) {
            // Skip first frame but record its timing
            if (lastRun == 0L) {
                lastRun = now
                return
            }
            // If we had 2 JFX frames for 1 screen frame, save a cycle
            if (now <= lastRun) return
            // Calculate remaining time until next screen frame (next multiple of frameNs)
            val rest: Long = now % frameNs.toLong()
            var nextFrame = now
            if (rest != 0L) //Fix timing to next screen frame
                nextFrame += frameNs.toLong() - rest
            // Animate
            val deltaTime: Double = (nextFrame - lastRun) / 1e9
            update(deltaTime)
            lastRun = nextFrame
        }
    }
}