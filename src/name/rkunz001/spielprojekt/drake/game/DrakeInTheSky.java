package name.rkunz001.spielprojekt.drake.game;

import java.util.ArrayList;
import java.util.List;

import name.panitz.game.framework.AbstractGame;
import name.panitz.game.framework.AbstractGameObject;
import name.panitz.game.framework.Button;
import name.panitz.game.framework.GameObject;
import name.panitz.game.framework.GraphicsTool;
import name.panitz.game.framework.ImageObject;
import name.panitz.game.framework.KeyCode;
import name.panitz.game.framework.Vertex;
import name.rkunz001.spielprojekt.drake.game.Snake.Turn;

public class DrakeInTheSky<I, S> extends AbstractGame<I, S> {

  private static int blockSize = 35;
  private static int blocksX = 35;
  private static int blocksY = 23;
  private static int cloudWidth = 105;
  private static int width = blocksX * blockSize;
  private static int height = blocksY * blockSize;

  private KeyCode actionKeyPressed;
  boolean dangereousBody = false;

  int score = 0;
  int collectedObjects = 0;

  boolean gameOver = false;
  boolean getHelp = true;

  Drake<I> drake;
  List<ImageObject<I>> cloud = new ArrayList<>();
  List<ScoreObject<I>> scoreObjects = new ArrayList<>();
  ImageObject<I> background;
  ImageObject<I> helpScreen;
  ImageObject<I> gameOverScreen;

  MagicOrb<I> magicOrb;
  FireOrb<I> fireOrb;
  EnergyOrb<I> energyOrb;
  ShimmerOrb<I> shimmerOrb;

  enum ScoreObjects {
    APPLE, BANANA, CHERRY;
  }

  public DrakeInTheSky() {
    super(new Drake<>(
        new Vertex(blocksX / 2 * blockSize, blocksY / 2 * blockSize), 5,
        blockSize, width, height), width, height);
    this.drake = (Drake<I>) getPlayer();

    newScoreObject();
    createCloud();
    getGOss().add(drake.getBody());
    getGOss().add(drake.getTail());
    getGOss().add(cloud);
    getGOss().add(scoreObjects);

    background = new LeftRightgImage<I>("background.png", new Vertex(0, 0),
        new Vertex(0, 0));
    helpScreen = new LeftRightgImage<I>("help.png", new Vertex(0, 0),
        new Vertex(0, 0));
    gameOverScreen = new LeftRightgImage<I>("gameover.png", new Vertex(0, 0),
        new Vertex(0, 0));

    getButtons().add(new Button("Start/Pause", () -> startStop()));
    getButtons().add(new Button("Reset", () -> reset()));
    getButtons().add(new Button("Help/Restart", () -> help()));
    getButtons().add(new Button("Exit", () -> System.exit(0)));
    pause();

  }

  private List<? extends GameObject<I>> createCloud() {
    cloud.add(new ImageObject<I>("cloud-top-left.png", new Vertex(0, 0)));
    for (int i = blockSize; i < width - blockSize; i = i + cloudWidth) {
      cloud.add(new ImageObject<I>("cloud-top.png", new Vertex(i, 0)));
    }
    cloud.add(new ImageObject<I>("cloud-top-right.png",
        new Vertex(width - blockSize, 0)));
    for (int i = blockSize; i < height - blockSize; i = i + cloudWidth) {
      cloud.add(new ImageObject<I>("cloud-right.png",
          new Vertex(width - blockSize, i)));
    }
    cloud.add(new ImageObject<I>("cloud-bottom-right.png",
        new Vertex(width - blockSize, height - blockSize)));
    for (int i = blockSize; i < width - blockSize; i = i + cloudWidth) {
      cloud.add(new ImageObject<I>("cloud-bottom.png",
          new Vertex(i, height - blockSize)));
    }
    cloud.add(new ImageObject<I>("cloud-bottom-left.png",
        new Vertex(0, height - blockSize)));
    for (int i = blockSize; i < height - blockSize; i = i + cloudWidth) {
      cloud.add(new ImageObject<I>("cloud-left.png", new Vertex(0, i)));
    }
    return cloud;
  }

  @Override
  public void paintTo(GraphicsTool<I> g) {
    if (getHelp) {
      helpScreen.paintTo(g);
    } else if (gameOver) {
      gameOverScreen.paintTo(g);
      g.drawString(50, 40, "Points   : " + score);
      g.drawString(50, 60, "Body size: " + drake.getBody().size());
      g.drawString(50, 80, "Collected: " + collectedObjects);
      g.drawString(50, 100, "Speed    : " + drake.getSpeed());
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
      g.drawString(50, 100, "Speed   : " + drake.getSpeed());
    }
  }

  private void help() {
    if (getHelp) {
      getHelp = false;
      start();
    } else {
      pause();
      getHelp = true;
    }
  }

  public void startStop() {
    if (isStopped()) {
      getHelp = false;
      super.start();
    } else {
      super.pause();
    }
  }

  @Override
  public void pause() {
    if (isStopped() == false) {
      super.pause();
    }
  }

  @Override
  public void doChecks() {

    if (drake.getPos().x < blockSize - 1
        || drake.getPos().x > width - (blockSize - 1)
        || drake.getPos().y < blockSize - 1
        || drake.getPos().y > height - (blockSize + 1)) {
      gameOver = true;
      return;
    }

    if (dangereousBody) {
      if (checkTouchBody()) {
        gameOver = true;
        return;
      }
    }

    if (scoreObjects.size() > 0
        && checkTolerantTouch(scoreObjects.get(0), 15)) {

      // drake has to be grow before ScoreObject checks otherwise movement isn't
      // nice after speedUp from EnergyOrb
      drake.grow();
      if (!checkScoreObject()) {
        gameOver = true;
        return;
      }

      collectedObjects++;
      newScoreObject();
    }
  }

  private boolean checkTouchBody() {
    boolean first = true;
    for (LeftRightgImage<I> b : drake.getBody()) {
      if (drake.touches(b) && !first) {
        return checkTolerantTouch(b, 10);
      }
      first = false;
    }
    return checkTolerantTouch(drake.getTail().get(0), 15);
  }

  // touch detection is too sensitive, make it more tolerant
  private boolean checkTolerantTouch(AbstractGameObject<I> b, int tolerance) {

    if (drake.touches(b)) {
      double x = Math.abs(b.getPos().x - drake.getPos().x);
      double y = Math.abs(drake.getPos().y - b.getPos().y);

      switch (drake.getDirection()) {
      case DOWN:
      case UP:
        if (y < blockSize - tolerance && x < blockSize - tolerance) {
          return true;
        }
        break;
      case LEFT:
      case RIGHT:
        if (x < blockSize - tolerance && y < blockSize - tolerance) {
          return true;
        }
        break;
      }
    }
    return false;
  }

  private boolean checkScoreObject() {
    ScoreObject<I> so = removeScoreObject();

    if (magicOrb != null) {
      KeyCode k = magicOrb.getKeyCode();
      magicOrb = null;
      if (k != actionKeyPressed) {
        return false;
      }
    }

    if (energyOrb != null) {
      drake.speedUp(energyOrb.getSpeedUp());
      energyOrb = null;
    }

    if (shimmerOrb != null) {
      shimmerOrb = null;
    }

    if (fireOrb != null) {
      dangereousBody = true;
      fireOrb = null;
    } else {
      dangereousBody = false;
    }

    actionKeyPressed = null;
    score += so.getScore();
    so = null;
    return true;
  }

  @Override
  public void keyPressedReaction(KeyCode keycode) {
    if (keycode != null) {
      switch (keycode) {
      case LEFT_ARROW:
        drake.turn(Turn.LEFT);
        break;
      case RIGHT_ARROW:
        drake.turn(Turn.RIGHT);
        break;
      case VK_S:
        startStop();
        break;
      case VK_P:
        startStop();
        break;
      case VK_H:
        help();
        break;
      case VK_R:
        reset();
        break;
      case VK_SPACE:
        actionKeyPressed = keycode;
        break;
      case VK_Q:
        // Very simpel Close function
        System.exit(0);
        ;
        break;
      default:
        ;
      }
    }
  }

  private void reset() {
    score = 0;
    collectedObjects = 0;
    removeScoreObject();

    drake.reset();
    newScoreObject();

    getHelp = false;
    gameOver = false;

    start();
  }

  private void newScoreObject() {
    int randomObj = (int) (Math.random() * 100);
//    energyOrb = new EnergyOrb<I>(
//        new Vertex(getRandomXY(blocksX), getRandomXY(blocksY)),
//        new Vertex(0, 0), 1, 1);
//    scoreObjects.add(energyOrb);
    if (randomObj < 25) {
      energyOrb = new EnergyOrb<I>(
          new Vertex(getRandomXY(blocksX), getRandomXY(blocksY)),
          new Vertex(0, 0), 1, 1);
      scoreObjects.add(energyOrb);
    } else if (randomObj < 45) {
      magicOrb = new MagicOrb<I>(
          new Vertex(getRandomXY(blocksX), getRandomXY(blocksY)),
          new Vertex(0, 0), 5, KeyCode.VK_SPACE);
      scoreObjects.add(magicOrb);
    } else if (randomObj < 80) {
      fireOrb = new FireOrb<I>(
          new Vertex(getRandomXY(blocksX), getRandomXY(blocksY)),
          new Vertex(0, 0), 10);
      scoreObjects.add(fireOrb);
    } else if (randomObj < 100) {
      shimmerOrb = new ShimmerOrb<I>(
          new Vertex(getRandomXY(blocksX), getRandomXY(blocksY)),
          new Vertex(0, 0), 15, width, height, blockSize);
      scoreObjects.add(shimmerOrb);
    }
  }

  private ScoreObject<I> removeScoreObject() {
    if (scoreObjects.size() > 0) {
      ScoreObject<I> o = scoreObjects.remove(0);
      return o;
    }
    return null;
  }

  double getRandomXY(int base) {
    base -= 2;
    return (int) (Math.random() * base) * blockSize + blockSize;
  }
}
