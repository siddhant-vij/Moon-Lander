package com.moonlander;

import java.util.List;

import com.codegym.engine.cell.*;

public class RocketFire extends GameObject {
  private List<int[][]> frames;
  private int frameIndex;
  private boolean isVisible;
  
  public RocketFire(List<int[][]> frameList) {
    super(0, 0, frameList.get(0));
    this.frames = frameList;  
  }

  private void nextFrame() {
    frameIndex++;
    if (frameIndex >= frames.size()) {
      frameIndex = 0;
    }
    this.matrix = frames.get(frameIndex);
  }

  @Override
  public void draw(Game game) {
    if (!isVisible) {
      return;
    }
    nextFrame();
    super.draw(game);
  }

  public void show() {
    isVisible = true;
  }

  public void hide() {
    isVisible = false;
  }
}
