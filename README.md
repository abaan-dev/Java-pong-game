# Java 2D Pong Game

Just a classic 2D Pong game built from scratch in Java using Swing. Made it with a smooth ~120 FPS render loop, a CPU opponent, score tracking up to 10, and clean UI buttons when the game ends.

## Features
- **Player vs CPU:** You control the left paddle with the arrow keys, CPU handles the right side.
- **Dynamic Speed:** The ball gets slightly faster every time it bounces off a paddle to keep rallies interesting.
- **Fixed Collisions:** Bulletproof directional checks so the ball never gets trapped or stuck inside paddles.
- **Game Over Screen:** Clean buttons to restart or quit once someone hits 10 points.

## How to Run It

1. Download or clone this repo.
2. Open your Java IDE (like Eclipse or IntelliJ).
3. Drop all the `.java` files (`Main.java`, `GamePanel.java`, `Paddle.java`, `Ball.java`) into your project's `src` or default package folder.
4. Open **`Main.java`** and hit run.

## Controls
- **Up Arrow / Down Arrow:** Move your paddle up and down
- **Mouse:** Click the Restart or Quit buttons on the Game Over screen
