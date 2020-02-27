package name.rkunz001.spielprojekt.drake;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import name.panitz.game.framework.Vertex;

public class Snake<I> extends LeftRightgImage<I> {

  String headRight = "head.png";
  String headLeft = "head.png";
  String headUp = "head.png";
  String headDown = "head.png";
  String bodyImage = "body.png";
  String tailRight = "tail.png";
  String tailLeft = "tail.png";
  String tailUp = "tail.png";
  String tailDown = "tail.png";

  enum Turn {
    NONE, LEFT, RIGHT;
  }

  Vertex startCorner = new Vertex(0, 0);;
  Vertex startVelocity = new Vertex(0, 0);;
  int startBodySize;
  int blockSize;
  int width;
  int height;
  List<LeftRightgImage<I>> body = Collections.synchronizedList(new ArrayList<>());
  List<LeftRightgImage<I>> tail = new ArrayList<>();

  boolean stopped = false;
  Turn nextTurn = Turn.NONE;
  double turnX;
  double turnY;
  boolean growing = false;

  public Snake(Vertex corner, Vertex velocity, int bodySize, int blockSize, int width, int height, String headRight,
      String bodyImage, String tailLeft) {
    super(headRight, corner, velocity);
    this.startCorner.moveTo(corner);
    this.startVelocity.moveTo(velocity);
    this.startBodySize = bodySize;
    this.headRight = headRight;
    this.bodyImage = bodyImage;
    this.tailLeft = tailLeft;
    this.blockSize = blockSize;
    this.width = width;
    this.height = height;

    createBodyAndTail(bodySize);

  }

  public void reset() {
    direction = Direction.RIGHT;
    this.setImageFileName(headRight);
    this.getPos().moveTo(startCorner);
    this.getVelocity().moveTo(startVelocity);
    body.clear();
    tail.clear();
    this.speed = 1;
    createBodyAndTail(startBodySize);
  }

  private void createBodyAndTail(int bodySize) {
    for (int i = 0; i < bodySize; i++) {
      body.add(new LeftRightgImage<I>(bodyImage, new Vertex(startCorner.x - (i + 1) * blockSize, startCorner.y),
          new Vertex(1, 0)));
    }
    tail.add(new LeftRightgImage<I>(tailLeft, new Vertex(startCorner.x - (bodySize + 1) * blockSize, startCorner.y),
        new Vertex(1, 0)));
  }

  public List<LeftRightgImage<I>> getBody() {
    return body;
  }

  public List<LeftRightgImage<I>> getTail() {
    return tail;
  }

  public void grow() {
    growing = true;
  }

  public LeftRightgImage<I> newBody(LeftRightgImage<I> pre) {

    LeftRightgImage<I> t = tail.get(0);
    LeftRightgImage<I> obj = null;

    switch (direction) {
    case DOWN:
      obj = new LeftRightgImage<I>(bodyImage, new Vertex(pre.getPos().x, pre.getPos().y - blockSize), new Vertex(0, 1));
      obj.down();
      t.getPos().y = obj.getPos().y - blockSize;
      break;
    case LEFT:
      obj = new LeftRightgImage<I>(bodyImage, new Vertex(pre.getPos().x + blockSize, pre.getPos().y),
          new Vertex(-1, 01));
      obj.left();
      t.getPos().x = obj.getPos().x + blockSize;
      break;
    case RIGHT:
      obj = new LeftRightgImage<I>(bodyImage, new Vertex(pre.getPos().x, pre.getPos().y - blockSize), new Vertex(1, 0));
      obj.right();
      t.getPos().x = obj.getPos().x - blockSize;
      break;
    case UP:
      obj = new LeftRightgImage<I>(bodyImage, new Vertex(pre.getPos().x, pre.getPos().y + blockSize),
          new Vertex(0, -1));
      obj.up();
      t.getPos().y = obj.getPos().y + blockSize;
      break;
    default:
      break;
    }
    obj.setSpeed(pre.getSpeed());
    return obj;
  }

  public boolean shrink() {
    if (body.size() > 0) {
      LeftRightgImage<I> del = body.remove(body.size() - 1);
      LeftRightgImage<I> pre = null;
      if (body.size() > 1) {
        pre = body.get(body.size() - 1);
      } else {
        pre = this;
      }
      tail.get(0).getPos().x = pre.getPos().x;
      tail.get(0).getPos().y = pre.getPos().y;
      pre.getPos().x = del.getPos().x;
      pre.getPos().y = del.getPos().y;
      del = null;
      return true;
    }

    return false;
  }

  @Override
  public void move() {
    if (stopped) {
      return;
    }
    if (nextTurn != Turn.NONE) {

      boolean turn = false;
      switch (getDirection()) {
      case LEFT:
        if (getPos().x < turnX || getPos().x < speed) {
          turn = true;
        }
        break;
      case RIGHT:
        if (getPos().x > turnX || getPos().x > width - blockSize - speed) {
          turn = true;
        }
        break;
      case UP:
        if (getPos().y < turnY || getPos().y < speed) {
          turn = true;
        }
        break;
      case DOWN:
        if (getPos().y > turnY || getPos().y > height - blockSize - speed) {
          turn = true;
        }
        break;
      }
      if (turn) {
        getPos().x = turnX;
        getPos().y = turnY;
        if (nextTurn == Turn.LEFT) {
          super.turnLeft();
        } else {
          super.turnRight();
        }
        switch (direction) {
        case DOWN:
          setImageFileName(headDown);
          break;
        case LEFT:
          setImageFileName(headLeft);
          break;
        case RIGHT:
          setImageFileName(headRight);
          break;
        case UP:
          setImageFileName(headUp);
          break;
        default:
          break;

        }
        nextTurn = Turn.NONE;
      }
    }
    super.move();

    LeftRightgImage<I> pre = this;
    for (LeftRightgImage<I> b : body) {
      moveBodyOrTail(b, pre);
      pre = b;
    }

    if (growing) {
      growing = false;
      LeftRightgImage<I> b = newBody(pre);
      body.add(b);
      moveBodyOrTail(b, pre);
    }
    moveBodyOrTail(tail.get(0), pre);

  }

  synchronized protected void moveBodyOrTail(LeftRightgImage<I> obj, LeftRightgImage<I> pre) {
    switch (pre.getDirection()) {
    case LEFT:
      if (obj.getPos().x - pre.getPos().x > 35) {
        obj.getPos().x = pre.getPos().x + blockSize;
        obj.getPos().y = pre.getPos().y;
        obj.left();
      }
      break;
    case RIGHT:
      if (pre.getPos().x - obj.getPos().x > 35) {
        obj.getPos().x = pre.getPos().x - blockSize;
        obj.getPos().y = pre.getPos().y;
        obj.right();
      }
      break;
    case UP:
      if (obj.getPos().y - pre.getPos().y > 35) {
        obj.getPos().x = pre.getPos().x;
        obj.getPos().y = pre.getPos().y + blockSize;
        obj.up();
      }
      break;
    case DOWN:
      if (pre.getPos().y - obj.getPos().y > 35) {
        obj.getPos().x = pre.getPos().x;
        obj.getPos().y = pre.getPos().y - blockSize;
        obj.down();
      }
      break;
    }

    fixDirection(obj, pre);
  }

  // keys pressed twice in quick succession it required to correct the moving
  // direction
  private void fixDirection(LeftRightgImage<I> obj, LeftRightgImage<I> pre) {
    if (obj.getDirection() == pre.getDirection()) {
      switch (pre.getDirection()) {
      case LEFT:
        if (obj.getPos().y != pre.getPos().y) {
          if (obj.getPos().y > pre.getPos().y) {
            obj.up();
          } else {
            obj.down();
          }
        }
        break;
      case RIGHT:
        if (obj.getPos().y != pre.getPos().y) {
          if (obj.getPos().y > pre.getPos().y) {
            obj.up();
          } else {
            obj.down();
          }
        }
        break;
      case UP:
        if (obj.getPos().x != pre.getPos().x) {
          if (obj.getPos().x > pre.getPos().x) {
            obj.left();
          } else {
            obj.right();
          }
        }
        break;
      case DOWN:
        if (obj.getPos().x != pre.getPos().x) {
          if (obj.getPos().x > pre.getPos().x) {
            obj.left();
          } else {
            obj.right();
          }
        }
        break;
      }
    }
  }

  @Override
  public void stop() {
    super.stop();
    for (LeftRightgImage<I> b : body) {
      b.stop();
    }
    tail.get(0).stop();
    stopped = true;
  }

  @Override
  public void restart() {
    super.restart();
    for (LeftRightgImage<I> b : body) {
      b.restart();
    }
    tail.get(0).restart();
    stopped = false;
  }

  public boolean isStopped() {
    return stopped;
  }

  @Override
  public void turnLeft() {
    nextTurn = Turn.LEFT;
    calcNextTurnPos();
  }

  @Override
  public void turnRight() {
    nextTurn = Turn.RIGHT;
    calcNextTurnPos();
  }

  private void calcNextTurnPos() {
    switch (getDirection()) {
    case LEFT:
      turnX = getPos().x - getPos().x % blockSize;
      turnY = getPos().y;
      break;
    case RIGHT:
      turnX = getPos().x - getPos().x % blockSize + blockSize;
      turnY = getPos().y;
      break;
    case UP:
      turnX = getPos().x;
      turnY = getPos().y - getPos().y % blockSize;
      break;
    case DOWN:
      turnX = getPos().x;
      turnY = getPos().y - getPos().y % blockSize + blockSize;
      break;
    }
  }

  Direction nextDirection(Direction current) {
    Direction next = null;
    if (current == Direction.LEFT) {
      switch (getDirection()) {
      case LEFT:
        next = Direction.DOWN;
      case RIGHT:
        next = Direction.UP;
      case UP:
        next = Direction.LEFT;
      case DOWN:
        next = Direction.RIGHT;
      }
    } else {
      switch (getDirection()) {
      case LEFT:
        next = Direction.UP;
      case RIGHT:
        next = Direction.DOWN;
      case UP:
        next = Direction.RIGHT;
      case DOWN:
        next = Direction.LEFT;
      }
    }

    return next;
  }

}
