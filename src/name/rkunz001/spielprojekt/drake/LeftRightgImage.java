package name.rkunz001.spielprojekt.drake;

import name.panitz.game.framework.ImageObject;
import name.panitz.game.framework.Vertex;

public class LeftRightgImage<I> extends ImageObject<I> {

  Direction direction = Direction.RIGHT;

  enum Direction {
    LEFT, RIGHT, UP, DOWN;
  }

  public LeftRightgImage(String imageFileName, Vertex corner, Vertex movement) {
    super(imageFileName, corner, movement);
  }

  public void stop() {
    getPos().move(getVelocity().mult(-1.1));
    getVelocity().x = 0;
    getVelocity().y = 0;
  }

  public void restart() {
    double oldX = getVelocity().x;
    getPos().move(getVelocity().mult(-1.1));
    getVelocity().x = -oldX;
    getVelocity().y = 0;
  }

  public void turnLeft() {
    Vertex v = getVelocity();

    if (v.x == 1 && v.y == 0) {
      up();
    } else if (v.x == 0 && v.y == -1) {
      left();
    } else if (v.x == -1 && v.y == 0) {
      down();
    } else if (v.x == 0 && v.y == 1) {
      right();
    }
  }

  public void turnRight() {
    Vertex v = getVelocity();

    if (v.x == 1 && v.y == 0) {
      down();
    } else if (v.x == 0 && v.y == 1) {
      left();
    } else if (v.x == -1 && v.y == 0) {
      up();
    } else if (v.x == 0 && v.y == -1) {
      right();
    }
  }

  public Direction getDirection() {
    return direction;
  }

  public void left() {
    getVelocity().x = -1;
    getVelocity().y = 0;
    direction = Direction.LEFT;
  }

  public void right() {
    getVelocity().x = 1;
    getVelocity().y = 0;
    direction = Direction.RIGHT;
  }

  public void up() {
    getVelocity().x = 0;
    getVelocity().y = -1;
    direction = Direction.UP;
  }

  public void down() {
    getVelocity().x = 0;
    getVelocity().y = 1;
    direction = Direction.DOWN;
  }

  @Override
  public void move() {
    super.move();
  }
}
