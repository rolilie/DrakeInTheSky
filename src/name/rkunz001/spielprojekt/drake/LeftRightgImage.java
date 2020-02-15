package name.rkunz001.spielprojekt.drake;

import name.panitz.game.framework.ImageObject;
import name.panitz.game.framework.Vertex;

public class LeftRightgImage<I> extends ImageObject<I> {
  double v0;
  int t = 0;

  public boolean isJumping = false;

  public LeftRightgImage(String imageFileName, Vertex corner, Vertex movement) {
    super(imageFileName, corner, movement);
  }

  public void stop() {
    getPos().move(getVelocity().mult(-1.1));
    getVelocity().x = 0;
    getVelocity().y = 0;
    isJumping = false;
  }

  public void restart() {
    double oldX = getVelocity().x;
    getPos().move(getVelocity().mult(-1.1));
    getVelocity().x = -oldX;
    getVelocity().y = 0;
    isJumping = false;
  }

  public void left() {
    Vertex v = getVelocity();

    if (v.x == 1 && v.y == 0) {
      getVelocity().x = 0;
      getVelocity().y = -1;
    } else if (v.x == 0 && v.y == -1) {
      getVelocity().x = -1;
      getVelocity().y = 0;
    } else if (v.x == -1 && v.y == 0) {
      getVelocity().x = 0;
      getVelocity().y = 1;
    } else if (v.x == 0 && v.y == 1) {
      getVelocity().x = 1;
      getVelocity().y = 0;
    }
  }

  public void right() {
    Vertex v = getVelocity();

    if (v.x == 1 && v.y == 0) {
      getVelocity().x = 0;
      getVelocity().y = 1;
    } else if (v.x == 0 && v.y == 1) {
      getVelocity().x = -1;
      getVelocity().y = 0;
    } else if (v.x == -1 && v.y == 0) {
      getVelocity().x = 0;
      getVelocity().y = -1;
    } else if (v.x == 0 && v.y == -1) {
      getVelocity().x = 1;
      getVelocity().y = 0;
    }
  }

  @Override
  public void move() {
    super.move();
  }
}
