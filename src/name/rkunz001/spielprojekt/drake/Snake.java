package name.rkunz001.spielprojekt.drake;

import java.util.ArrayList;
import java.util.List;

import name.panitz.game.framework.Vertex;

public class Snake<I> extends LeftRightgImage<I> {

  private final static String HEAD_IMAGE = "player.png";
  private final static String BODY_IMAGE = "fass.gif";
  private final static String TAIL_IMAGE = "heart.png";

  enum Turn {
    NONE, LEFT, RIGHT;
  }

  int blockSize;
  int width;
  int height;
  private int bodySize;
  List<LeftRightgImage<I>> body = new ArrayList<>();
  List<LeftRightgImage<I>> tail = new ArrayList<>();

  boolean stopped = false;
  Turn nextTurn = Turn.NONE;
  double turnX;
  double turnY;

  public Snake(Vertex corner, Vertex velocity, int bodySize, int blockSize, int width, int height) {
    super(HEAD_IMAGE, corner, velocity);
    this.bodySize = bodySize;
    this.blockSize = blockSize;
    this.width = width;
    this.height = height;

    for (int i = 0; i < bodySize; i++) {
      body.add(
          new LeftRightgImage<I>(BODY_IMAGE, new Vertex(corner.x - (i + 1) * blockSize, corner.y), new Vertex(1, 0)));
    }
    tail.add(new LeftRightgImage<I>(TAIL_IMAGE, new Vertex(corner.x - (bodySize + 1) * blockSize, corner.y),
        new Vertex(1, 0)));
  }

  public List<LeftRightgImage<I>> getBody() {
    return body;
  }

  public List<LeftRightgImage<I>> getTail() {
    return tail;
  }

  public void grow() {
    LeftRightgImage<I> pre = body.get(bodySize - 1);
    bodySize++;
    body.add(new LeftRightgImage<I>(BODY_IMAGE, new Vertex(pre.getPos().x - (bodySize + 1) * blockSize, pre.getPos().y),
        new Vertex(1, 0)));
  }

  public void shrink() {
    LeftRightgImage<I> del = body.remove(--bodySize);
    LeftRightgImage<I> pre = body.get(bodySize - 1);
  }

  @Override
  public void move() {
    System.out.println("Pos: " + getPos().x + ", " + getPos().y);
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
        if (getPos().y > turnY || getPos().x > height - blockSize - speed) {
          turn = true;
        }
        break;
      }
      if (turn) {
        System.out.println("turnX: " + turnX + ", turnY: " + turnY + ", left: " + getPos().x + ", " + getPos().y);

        getPos().x = turnX;
        getPos().y = turnY;
        if (nextTurn == Turn.LEFT) {
          super.turnLeft();
        } else {
          super.turnRight();
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

    moveBodyOrTail(tail.get(0), pre);
  }

  synchronized protected void moveBodyOrTail(LeftRightgImage<I> obj, LeftRightgImage<I> pre) {
    double absX = Math.abs(pre.getPos().x - obj.getPos().x);
    double absY = Math.abs(pre.getPos().y - obj.getPos().y);
    if (absX > blockSize || absY > blockSize) {
      switch (pre.getDirection()) {
      case LEFT:
        obj.left();
        obj.getPos().x = pre.getPos().x + blockSize;
        obj.getPos().y = pre.getPos().y;
        break;
      case RIGHT:
        obj.right();
        obj.getPos().x = pre.getPos().x - blockSize;
        obj.getPos().y = pre.getPos().y;
        break;
      case UP:
        obj.up();
        obj.getPos().x = pre.getPos().x;
        obj.getPos().y = pre.getPos().y + blockSize;
        break;
      case DOWN:
        obj.down();
        obj.getPos().x = pre.getPos().x;
        obj.getPos().y = pre.getPos().y - blockSize;
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
