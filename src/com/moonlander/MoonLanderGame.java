package com.moonlander;

import com.codegym.engine.cell.*;

public class MoonLanderGame extends Game {
  public static final int WIDTH = 64;
  public static final int HEIGHT = 64;
  private Rocket rocket;
  private GameObject landscape;
  private GameObject platform;
  private boolean isUpPressed;
  private boolean isLeftPressed;
  private boolean isRightPressed;
  private boolean isGameStopped;
  private int score;

  @Override
  public void initialize() {
    setScreenSize(WIDTH, HEIGHT);
    createGame();
    showGrid(false);
  }

  private void createGame() {
    createGameObjects();
    drawScene();
    setTurnTimer(50);
    isUpPressed = false;
    isLeftPressed = false;
    isRightPressed = false;
    isGameStopped = false;
    score = 1000;
  }

  private void drawScene() {
    for (int i = 0; i < WIDTH; i++) {
      for (int j = 0; j < HEIGHT; j++) {
        setCellColor(i, j, Color.BLACK);
      }
    }
    rocket.draw(this);
    landscape.draw(this);
  }

  private void createGameObjects() {
    rocket = new Rocket(WIDTH / 2, 0);
    landscape = new GameObject(0, 25, ShapeMatrix.LANDSCAPE);
    platform = new GameObject(23, HEIGHT - 1, ShapeMatrix.PLATFORM);
  }

  @Override
  public void setCellColor(int x, int y, Color color) {
    if (x < 0 || y < 0 || x >= WIDTH || y >= HEIGHT) {
      return;
    }
    super.setCellColor(x, y, color);
  }

  @Override
  public void onTurn(int step) {
    rocket.move(isUpPressed, isLeftPressed, isRightPressed);
    check();
    if (score > 0) {
      score--;
    }
    setScore(score);
    drawScene();
  }

  @Override
  public void onKeyPress(Key key) {
    if (key == Key.UP) {
      isUpPressed = true;
    } else if (key == Key.LEFT) {
      isLeftPressed = true;
      isRightPressed = false;
    } else if (key == Key.RIGHT) {
      isRightPressed = true;
      isLeftPressed = false;
    } else if (key == Key.SPACE && isGameStopped) {
      createGame();
      return;
    }
  }

  @Override
  public void onKeyReleased(Key key) {
    if (key == Key.UP) {
      isUpPressed = false;
    } else if (key == Key.LEFT) {
      isLeftPressed = false;
    } else if (key == Key.RIGHT) {
      isRightPressed = false;
    }
  }

  private void check() {
    if (rocket.isCollision(platform) && rocket.isStopped()) {
      win();
    } else if (rocket.isCollision(landscape)) {
      gameOver();
    }
  }

  private void win() {
    rocket.land();
    isGameStopped = true;
    showMessageDialog(Color.GREEN, "YOU WIN", Color.BLACK, 100);
    stopTurnTimer();
  }

  private void gameOver() {
    score = 0;
    setScore(score);
    rocket.crash();
    isGameStopped = true;
    showMessageDialog(Color.RED, "GAME OVER", Color.BLACK, 100);
    stopTurnTimer();
  }
}
