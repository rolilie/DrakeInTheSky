package name.rkunz001.spielprojekt.drake;

import java.util.ArrayList;
import java.util.List;

import name.panitz.game.framework.AbstractGame;
import name.panitz.game.framework.AbstractGameObject;
import name.panitz.game.framework.GameObject;
import name.panitz.game.framework.GraphicsTool;
import name.panitz.game.framework.ImageObject;
import name.panitz.game.framework.KeyCode;
import name.panitz.game.framework.Vertex;

public class DrakeInTheSky<I, S> extends AbstractGame<I, S> {

  private static int blockSize = 35;
  private static int blocksX = 35;
  private static int blocksY = 23;
  private static int cloudWidth = 105;
  private static int width = blocksX * blockSize;
  private static int height = blocksY * blockSize;

  int speedUp = 1;
  KeyCode keyPressed;
  boolean dangereousBody = false;

  int score = 0;
  int collectedObjects = 0;

  boolean gameOver = false;
  boolean getHelp = true;

  Drake<I> drake;
  List<ImageObject<I>> cloud = new ArrayList<>();
  List<ScoreObject<I>> scoreObjects = new ArrayList<>();
  ImageObject<I> background;
  ImageObject<I> help;

  enum ScoreObjects {
    APPLE, BANANA, CHERRY;
  }

  public DrakeInTheSky() {
    super(new Drake<>(new Vertex(blocksX / 2 * blockSize, blocksY / 2 * blockSize), 5, blockSize, width, height), width,
        height);
    this.drake = (Drake<I>) getPlayer();

    newScoreObject();
    createCloud();
    getGOss().add(drake.getBody());
    getGOss().add(drake.getTail());
    getGOss().add(cloud);

    background = new LeftRightgImage<I>("background.png", new Vertex(0, 0), new Vertex(0, 0));
    help = new LeftRightgImage<I>("start-screen.png", new Vertex(0, 0), new Vertex(0, 0));

    pause();

  }

  private List<? extends GameObject<I>> createCloud() {
    cloud.add(new ImageObject<I>("cloud-top-left.png", new Vertex(0, 0)));
    for (int i = blockSize; i < width - blockSize; i = i + cloudWidth) {
      cloud.add(new ImageObject<I>("cloud-top.png", new Vertex(i, 0)));
    }
    cloud.add(new ImageObject<I>("cloud-top-right.png", new Vertex(width - blockSize, 0)));
    for (int i = blockSize; i < height - blockSize; i = i + cloudWidth) {
      cloud.add(new ImageObject<I>("cloud-right.png", new Vertex(width - blockSize, i)));
    }
    cloud.add(new ImageObject<I>("cloud-bottom-right.png", new Vertex(width - blockSize, height - blockSize)));
    for (int i = blockSize; i < width - blockSize; i = i + cloudWidth) {
      cloud.add(new ImageObject<I>("cloud-bottom.png", new Vertex(i, height - blockSize)));
    }
    cloud.add(new ImageObject<I>("cloud-bottom-left.png", new Vertex(0, height - blockSize)));
    for (int i = blockSize; i < height - blockSize; i = i + cloudWidth) {
      cloud.add(new ImageObject<I>("cloud-left.png", new Vertex(0, i)));
    }
    return cloud;
  }

  @Override
  public void paintTo(GraphicsTool<I> g) {
    if (getHelp) {
      help.paintTo(g);
    } else if (gameOver) {
      help.paintTo(g);
      g.drawString(50, 40, "Points   : " + score);
      g.drawString(50, 60, "Body size: " + drake.getBody().size());
      g.drawString(50, 80, "Collected: " + collectedObjects);
      if (gameOver) {
        g.drawString(50, 100, "Game over");
      }
      pause();
    } else {
      background.paintTo(g);
      super.paintTo(g);
      g.drawString(50, 40, "Points   : " + score);
      g.drawString(50, 60, "Body size: " + drake.getBody().size());
      g.drawString(50, 80, "Collected: " + collectedObjects);
    }

  }

  @Override
  public void doChecks() {
    for (ImageObject<I> c : cloud) {
      if (checkTolerantTouch(c, 5)) {
        gameOver = true;
        return;
      }
    }

    if (dangereousBody) {
      if (checkTouchBody()) {
        gameOver = true;
        return;
      }
    }

    if (scoreObjects.size() > 0 && drake.touches(scoreObjects.get(0))) {
      if (checkTolerantTouch(scoreObjects.get(0), 15)) {
        if (!checkScoreObject()) {
          gameOver = true;
          return;
        }

        drake.grow();
        collectedObjects++;
        newScoreObject();
      }
    }
  }

  private boolean checkTouchBody() {
    boolean first = true;
    for (LeftRightgImage<I> b : drake.getBody()) {
      if (drake.touches(b) && !first) {
        return checkTolerantTouch(b, 15);
      }
      first = false;
    }
    if (drake.touches(drake.getTail().get(0))) {
      return checkTolerantTouch(drake.getTail().get(0), 15);
    }

    return false;
  }

  // touch detection is to sensitive, make it more tolerant
  private boolean checkTolerantTouch(AbstractGameObject<I> b, int tolerance) {

    double c = Math.abs(b.getPos().y - drake.getPos().y);
    double d = Math.abs(b.getPos().x - drake.getPos().x);
    double e = Math.abs(drake.getPos().x - b.getPos().x);
    double f = Math.abs(drake.getPos().y - b.getPos().y);

    System.out.println(c + ", " + d + ", " + e + ", " + f);
    if (Math.abs(drake.getPos().x - b.getPos().x) < blockSize - tolerance
        && Math.abs(drake.getPos().y - b.getPos().y) < blockSize - tolerance) {
      return true;
    } else {
      return false;
    }
//    switch (drake.getDirection()) {
//    case DOWN:
//    case UP:
//      if (Math.abs(b.getPos().y - drake.getPos().y) < blockSize - 15
//          && Math.abs(b.getPos().x - drake.getPos().x) < blockSize - 15) {
//        return true;
//      }
//      break;
//    case LEFT:
//    case RIGHT:
//      if (Math.abs(drake.getPos().x - b.getPos().x) < blockSize - 15
//          && Math.abs(drake.getPos().y - b.getPos().y) < blockSize - 15) {
//        return true;
//      }
//      break;
//    }
//    return false;
  }

  private boolean checkScoreObject() {
    ScoreObject<I> so = scoreObjects.remove(0);
    removeGameObject(scoreObjects, so);

    if (so.getKeyCode() != null && so.getKeyCode() != keyPressed) {
      return false;
    }

    if (so.getSpeedUp() > 0) {
      int speed = drake.getSpeed() + so.getSpeedUp();
      drake.setSpeed(speed);
      for (LeftRightgImage<I> b : drake.getBody()) {
        b.setSpeed(speed);
      }
      drake.getTail().get(0).setSpeed(speed);
    }

    if (so.isToggle()) {
      dangereousBody = true;
    } else {
      dangereousBody = false;
    }
    score += so.getScore();
    return true;
  }

  private void removeGameObject(List<? extends GameObject<I>> list, GameObject<I> obj) {
    int i = getGOss().indexOf(list);
    getGOss().get(i).remove(obj);
    obj = null;
  }

  @Override
  public void keyPressedReaction(KeyCode keycode) {
    if (keycode != null) {
      switch (keycode) {
      case LEFT_ARROW:
        System.out.println("left arrow key pressed: " + keycode);
        drake.turnLeft();
        break;
      case RIGHT_ARROW:
        System.out.println("right arrow key pressed: " + keycode);
        drake.turnRight();
        break;
      case VK_H:
        System.out.println("space key pressed: " + keycode);
        if (getHelp) {
          getHelp = false;
          start();
        } else {
          pause();
          getHelp = true;
        }
        break;
      default:
        ;
      }
    }

    keyPressed = keycode;
  }

  private void newScoreObject() {
    int randomObj = (int) (Math.random() * 100);
    ScoreObject<I> obj = null;
    if (randomObj < 30) {
      obj = new ScoreObject<I>("fire-orb.png", new Vertex(getRandomXY(blocksX), getRandomXY(blocksY)), new Vertex(0, 0),
          10, true);
//      obj = new ScoreObject<I>("energy-orb.png", new Vertex(getRandomXY(blocksX), getRandomXY(blocksY)),
//          new Vertex(0, 0), 1, speedUp);
    } else if (randomObj < 70) {
      obj = new ScoreObject<I>("fire-orb.png", new Vertex(getRandomXY(blocksX), getRandomXY(blocksY)), new Vertex(0, 0),
          10, true);
//      obj = new ScoreObject<I>("magic-orb.png", new Vertex(getRandomXY(blocksX), getRandomXY(blocksY)),
//          new Vertex(0, 0), 5, KeyCode.VK_SPACE);
    } else if (randomObj < 100) {
      obj = new ScoreObject<I>("fire-orb.png", new Vertex(getRandomXY(blocksX), getRandomXY(blocksY)), new Vertex(0, 0),
          10, true);
    }
    if (obj != null) {
      scoreObjects.add(obj);
      getGOss().add(scoreObjects);
    }
  }

  double getRandomXY(int base) {
    base -= 2;
    return (int) (Math.random() * base) * blockSize + blockSize;
  }
}
