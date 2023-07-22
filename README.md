# Anirban Dutta
### Student ID: 20813366 / a9dutta
```
kotlinc-jvm 1.8.21-release-380 (JRE 1.8.0_371-b11)
Windows 10 Home 64-bit 22H2 (Build 19044.1387)
```

# Space Invaders: Project Specification

## 1. Home Screen
The Home Screen contains the following components:
- Tite + Logo: At the top of the screen. The logo is a Space Invaders icon.
- Instructions: At the centre of the screen, there is a list of instructions provided for the player.
  - `ENTER`: Starts the game
  - `A` or ◀: Moves ship left
  - `D` or ▶: Moves ship right
  - `SPACE BAR` - Lets the player fire/shoot at the invaders/enemies/aliens
  - `1` or `2` or `3` - Starts the game at a specific level
  - `ESC` or `Q` - Quits the game

## 2. Game Screen
The initial Game Screen contains the following components:

### a. Aliens / Enemies / Invaders
- The aliens are arranged in a 5 x 10 grid, and are initially in the top left corner of the
  screen.
- They move together as a group. They start moving from their starting position towards the right of the screen.
  When they reach the edge of the screen, the following happens:
  - The aliens all descend one row 
  - One of the aliens fires a missile straight down
  - The aliens reverse their direction and start moving in the opposite direction.
  The aliens continue moving until they reach the left-edge of the screen, and the pattern repeats, and they start
  moving right again. They repeat this pattern, descending and alternating directions, until they reach the bottom of
  the screen or the game ends.
- Every time the aliens move, there is a random chance that one of them fires a missile. The rate-of-fire is designed so
  that there are never more than a few missiles on-screen at a given time.
- Every time the player destroys an alien, the remaining aliens speed up. Effectively the game starts out fairly slow,
  and gets progressively faster as aliens are destroyed.

### b. Player-controlled ship
This is initially in the bottom centre of the screen. The player can move the ship left and right using the `A` and `D`
keys, and the `LEFT` and `RIGHT` keys, respectively. The ship can fire bullets at the aliens using the `SPACE BAR`.
- The ship can only fire two bullets in a second.
- The player has three ships at the start of the game: the ship they are using and two additional ships, indicated on 
  the screen.
- If the player has ships remaining and their ship is destroyed (either by contacting an alien or being struck by a
  missile), one of the extra ships is removed and the player’s ship is respawned in a random, unoccupied location
  on-screen.
- If they die for a third time (i.e. they’ve exhausted all ships), then they lose the game.
- If all aliens are destroyed successfully, the next level is launched. Subsequent levels all have the same layout, but
  the ships and missiles move faster than previous levels, and there may be a slightly higher chance of missiles firing
  in later levels.
- The level indicator clearly indicates the level in play. 
- The game has three levels, and pressing `1`, `2` or `3` from the start screen will launch the game at that level

> **Note**
> This is not normal gameplay, but is provided for testing purposes.

- If the player succeeds in clearing all three levels, a message is shown mentioning that they have won the game and
  their score is displayed. They are prompted to restart or quit the game.
- If the player’s ships are all destroyed, you should display a message telling them that they have lost the game, along with their score. You should prompt them to restart or quit the game.

### c. Scoreboard
The score is displayed in the top left corner of the screen. The score is initially 0, and increases by:
  - Low-tier alien deaths: 100 points
  - Mid-tier alien deaths: 200 points
  - High-tier alien deaths: 300 points
  The points are multiplied by the level the player is in.

### d. Lives
The number of lives the player has left is displayed in the top right corner of the screen. The player starts
  with 3 lives, and loses a life when the player is hit by an alien bullet. The player loses immediately when:
- An alien reaches the bottom of the screen
- An alien collides with the player's ship.
- The player runs out of lives.

### e. Level
The level the player is in is displayed at the top centre of the screen. The player starts at level 1, and progresses to
the next level when all the aliens are killed. The player wins the game when they finish level 3.


### 3. Other Game Details
- Screen Width: 1200 pixels
- Screen Height: 810 pixels
- Non-resizable
- Provides audio feedback for the following events:
  - Player ship firing
  - Player ship being destroyed
  - Alien being destroyed
  - Alien firing
  - Alien reaching the bottom of the screen

## Attributions:
All image and sound assets used in this project are from [CS349 S23 Assignment 3](https://student.cs.uwaterloo.ca/~cs349/1235/schedule/assignments/a3/).
A link to the resources is provided [here](https://student.cs.uwaterloo.ca/~cs349/1235/schedule/assignments/a3/space-invaders-assets.zip).
