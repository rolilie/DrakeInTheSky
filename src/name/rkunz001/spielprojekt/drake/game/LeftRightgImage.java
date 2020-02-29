package name.rkunz001.spielprojekt.drake.game;

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

  public LeftRightgImage(String imageFileName, Vertex corner, Vertex movement,
      Direction direction) {
    super(imageFileName, corner, movement);
    this.direction = direction;
  }

  public void turnLeft() {
    Vertex v = getVelocity();
    if (direction == Direction.RIGHT) {
      getVelocity().y = v.x * -1;
      getVelocity().x = 0;
      direction = Direction.UP;
    } else if (direction == Direction.UP) {
      getVelocity().x = v.y;
      getVelocity().y = 0;
      direction = Direction.LEFT;
    } else if (direction == Direction.LEFT) {
      getVelocity().y = v.x * -1;
      getVelocity().x = 0;
      direction = Direction.DOWN;
    } else if (direction == Direction.DOWN) {
      getVelocity().x = v.y;
      getVelocity().y = 0;
      direction = Direction.RIGHT;
    }
  }

  public void turnRight() {
    Vertex v = getVelocity();
    if (direction == Direction.RIGHT) {
      getVelocity().y = v.x;
      getVelocity().x = 0;
      direction = Direction.DOWN;
    } else if (direction == Direction.DOWN) {
      getVelocity().x = v.y * -1;
      getVelocity().y = 0;
      direction = Direction.LEFT;
    } else if (direction == Direction.LEFT) {
      getVelocity().y = v.x;
      getVelocity().x = 0;
      direction = Direction.UP;
    } else if (direction == Direction.UP) {
      getVelocity().x = v.y * -1;
      getVelocity().y = 0;
      direction = Direction.RIGHT;
    }
  }

  public Direction getDirection() {
    return direction;
  }

}
