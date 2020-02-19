package name.rkunz001.spielprojekt.drake;

import java.util.ArrayList;
import java.util.List;

import name.panitz.game.framework.Vertex;

public class Drake<I> extends LeftRightgImage<I> {

  private final static String HEAD_IMAGE = "player.png";
  private final static String BODY_IMAGE = "fass.gif";
  private final static String TAIL_IMAGE = "heart.png";
  private int bodySize = 5;
  List<LeftRightgImage<I>> body = new ArrayList<>();
  List<LeftRightgImage<I>> tail = new ArrayList<>();

  public Drake(Vertex corner, int blockSize) {
    super(HEAD_IMAGE, corner, new Vertex(1, 0));
    for (int i = 0; i < bodySize; i++) {
      body.add(
          new LeftRightgImage<I>(BODY_IMAGE, new Vertex(corner.x - (i + 1) * blockSize, corner.y), new Vertex(1, 0)));
    }
    tail.add(new LeftRightgImage<I>(TAIL_IMAGE, new Vertex(corner.x - (bodySize + 1) * blockSize, corner.y),
        new Vertex(1, 0)));
  }

  @Override
  public void move() {
    super.move();
    LeftRightgImage<I> pre = this;
    for (LeftRightgImage<I> b : body) {
      moveBodyOrTail(b, pre);
      pre = b;
    }
    moveBodyOrTail(tail.get(0), pre);
  }

  public List<LeftRightgImage<I>> getBody() {
    return body;
  }

  public List<LeftRightgImage<I>> getTail() {
    return tail;
  }

  @Override
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

  @Override
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

  private void moveBodyOrTail(LeftRightgImage<I> obj, LeftRightgImage<I> pre) {
    if (pre.getDirection() != obj.getDirection()) {
      if (pre.getDirection() == Direction.LEFT) {
        if (obj.getDirection() == Direction.UP && obj.getPos().y <= pre.getPos().y
            || obj.getDirection() == Direction.DOWN && obj.getPos().y >= pre.getPos().y) {
          obj.left();
        }
      } else if (pre.getDirection() == Direction.RIGHT) {
        if (obj.getDirection() == Direction.DOWN && obj.getPos().y >= pre.getPos().y
            || obj.getDirection() == Direction.UP && obj.getPos().y <= pre.getPos().y) {
          obj.right();
        }
      } else if (pre.getDirection() == Direction.UP) {
        if (obj.getDirection() == Direction.RIGHT && obj.getPos().x >= pre.getPos().x
            || obj.getDirection() == Direction.LEFT && obj.getPos().x <= pre.getPos().x) {
          obj.up();
        }
      } else if (pre.getDirection() == Direction.DOWN) {
        if (obj.getDirection() == Direction.LEFT && obj.getPos().x <= pre.getPos().x
            || obj.getDirection() == Direction.RIGHT && obj.getPos().x >= pre.getPos().x) {
          obj.down();
        }
      }
    }
  }
}
