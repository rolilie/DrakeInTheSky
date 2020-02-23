package name.rkunz001.spielprojekt.drake;

import name.panitz.game.framework.ImageObject;
import name.panitz.game.framework.Vertex;

public class LeftRightgImage<I> extends ImageObject<I> {

  Direction direction = Direction.RIGHT;
  double restartVelocityX;
  double restartVelocityY;

  public int getSpeed() {
    return speed;
  }

  public void setSpeed(int speed) {
    this.speed = speed;
  }

  int speed = 1;

  enum Direction {
    LEFT, RIGHT, UP, DOWN;
  }

  public LeftRightgImage(String imageFileName, Vertex corner, Vertex movement) {
    super(imageFileName, corner, movement);
    getVelocity().x = getVelocity().x * speed;
    getVelocity().y = getVelocity().y * speed;
  }

  public void stop() {
    restartVelocityX = getVelocity().x;
    restartVelocityY = getVelocity().y;
    getVelocity().x = 0;
    getVelocity().y = 0;
  }

  public void restart() {
    getVelocity().x = restartVelocityX;
    getVelocity().y = restartVelocityY;
  }

  public void turnLeft() {
    Vertex v = getVelocity();
    if (v.x > 0 && v.y == 0) {
      up();
    } else if (v.x == 0 && v.y < 0) {
      left();
    } else if (v.x < 0 && v.y == 0) {
      down();
    } else if (v.x == 0 && v.y > 0) {
      right();
    }
  }

  public void turnRight() {
    Vertex v = getVelocity();
    if (v.x > 0 && v.y == 0) {
      down();
    } else if (v.x == 0 && v.y > 0) {
      left();
    } else if (v.x < 0 && v.y == 0) {
      up();
    } else if (v.x == 0 && v.y < 0) {
      right();
    }
  }

  public Direction getDirection() {
    return direction;
  }

  public void left() {
    getVelocity().x = -1 * speed;
    getVelocity().y = 0;
    direction = Direction.LEFT;
  }

  public void right() {
    getVelocity().x = 1 * speed;
    getVelocity().y = 0;
    direction = Direction.RIGHT;
  }

  public void up() {
    getVelocity().x = 0;
    getVelocity().y = -1 * speed;
    direction = Direction.UP;
  }

  public void down() {
    getVelocity().x = 0;
    getVelocity().y = 1 * speed;
    direction = Direction.DOWN;
  }

  @Override
  public void move() {
    super.move();
  }
}
