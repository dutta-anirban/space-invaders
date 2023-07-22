package gamelogic

import javafx.scene.media.Media
import javafx.scene.media.MediaPlayer

object Resources {

    val ENEMYFIRERATE = 5.0 // Unit: (s) between shot
    val PLAYERFIRERATE = 0.8 // Unit: (s) between shot
    val ENEMYSPEEDMOD = 5.0 // Unit: px/s/enemy killed
    val ENEMYBULLETSPEED = 80.0 // Unit: px/s
    val PLAYERBULLETSPEED = 80.0 // Unit: px/s
    val PLAYERSPEED = 500.0 // Unit: px/s

    val invaderKilledSound = MediaPlayer(Media(javaClass.getResource("/sounds/invaderkilled.wav")?.toExternalForm()))
    val explosionSound = MediaPlayer(Media(javaClass.getResource("/sounds/explosion.wav")?.toExternalForm()))
    val fastInvader1Sound = MediaPlayer(Media(javaClass.getResource("/sounds/fastinvader1.wav")?.toExternalForm()))
    // FIXME: Add other invader sounds
    val shootSound = MediaPlayer(Media(javaClass.getResource("/sounds/shoot.wav")?.toExternalForm()))
}
