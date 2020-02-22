package name.rkunz001.spielprojekt.drake;

import java.util.ArrayList;
import java.util.List;

import name.panitz.game.framework.AbstractGame;
import name.panitz.game.framework.GraphicsTool;
import name.panitz.game.framework.KeyCode;
import name.panitz.game.framework.Vertex;

public class DrakeInTheSky<I, S> extends AbstractGame<I, S> {

  int blockSize;
  int width;
  int height;
  int blocksX;
  int blocksY;
  boolean xPressed = false;
  boolean gameOver = false;

  int score = 0;
  int collectedObjects = 0;

  enum ScoreObjects {
    APPLE, BANANA, CHERRY;
  }

  List<ScoreObject<I>> scoreObjects = new ArrayList<>();
  Drake<I> drake;

  public DrakeInTheSky(int blocksX, int blocksY, int blockSize) {
    super(new Drake<>(new Vertex(blocksX / 2 * blockSize, blocksY / 2 * blockSize), 5, blockSize, blocksX * blockSize,
        blocksY * blockSize), blocksX * blockSize, blocksY * blockSize);
    this.blockSize = blockSize;
    this.blocksX = blocksX;
    this.blocksY = blocksY;
    this.width = blocksX * blockSize;
    this.height = blocksY * blockSize;
    this.drake = (Drake<I>) getPlayer();

    getGOss().add(drake.getBody());
    getGOss().add(drake.getTail());

    newScoreObject();
  }

  @Override
  public void paintTo(GraphicsTool<I> g) {
    super.paintTo(g);
    g.drawString(50, 20, "Points   : " + score);
    g.drawString(50, 40, "Body size: " + drake.getBody().size());
    g.drawString(50, 60, "Collected: " + collectedObjects);
    if (gameOver) {
      g.drawString(50, 80, "Game over");
    }
  }

  @Override
  public void doChecks() {
    if (drake.getPos().x >= width - blockSize + 1 || drake.getPos().y >= height - blockSize + 1
        || drake.getPos().x < 0.0 || drake.getPos().y < 0.0) {
      drake.stop();
      gameOver = true;
    }

    if (scoreObjects.size() > 0 && drake.touches(scoreObjects.get(0))) {
      ScoreObject<I> so = scoreObjects.remove(0);
      getGOss().remove(so);
      if (so.getScore() < 0 && !xPressed || so.getScore() > 0 && xPressed) {
        drake.stop();
        gameOver = true;
        return;
      } else {
        xPressed = false;
      }
      drake.grow();
      collectedObjects++;
      score += so.getScore();
      so = null;
      newScoreObject();
    }
  }

  @Override
  public void keyPressedReaction(KeyCode keycode) {
    System.out.println("key pressed: " + keycode);
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
      case VK_SPACE:
        System.out.println("space key pressed: " + keycode);
        if (drake.isStopped()) {
          drake.restart();
        } else {
          drake.stop();
        }
        break;
      case VK_X:
        System.out.println("space key pressed: " + keycode);
        xPressed = true;
        break;
      default:
        ;
      }
    }
  }

  private void newScoreObject() {
    int randomObj = (int) (Math.random() * 100);
    ScoreObject<I> obj = null;
    if (randomObj < 50) {
      obj = new ScoreObject<I>("apple.png", new Vertex(getRandomXY(blocksX), getRandomXY(blocksY)), new Vertex(0, 0),
          1);
    } else if (randomObj < 80) {
      obj = new ScoreObject<I>("dot.png", new Vertex(getRandomXY(blocksX), getRandomXY(blocksY)), new Vertex(0, 0), 5);
    } else if (randomObj < 95) {
      obj = new ScoreObject<I>("heart.png", new Vertex(getRandomXY(blocksX), getRandomXY(blocksY)), new Vertex(0, 0),
          20);
    } else if (randomObj <= 100) {
      obj = new ScoreObject<I>("wall.png", new Vertex(getRandomXY(blocksX), getRandomXY(blocksY)), new Vertex(0, 0),
          -1);
    }
    if (obj != null) {
      scoreObjects.add(obj);
      getGOss().add(scoreObjects);
    }
  }

  private void moveFruit() {
    scoreObjects.get(0).getPos().x = getRandomXY(blocksX);
    scoreObjects.get(0).getPos().y = getRandomXY(blocksY);
  }

  double getRandomXY(int base) {
    return (int) (Math.random() * base) * blockSize;
  }
}
