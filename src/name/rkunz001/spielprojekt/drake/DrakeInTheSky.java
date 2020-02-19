package name.rkunz001.spielprojekt.drake;

import java.util.ArrayList;
import java.util.List;

import name.panitz.game.framework.AbstractGame;
import name.panitz.game.framework.GraphicsTool;
import name.panitz.game.framework.KeyCode;
import name.panitz.game.framework.Vertex;

public class DrakeInTheSky<I, S> extends AbstractGame<I, S> {

  static final int GRID_WIDTH = 40;
  static final int GRID_HEIGHT = 28;
  static final int BLOCK_WIDTH = 35;
  static final int BLOCK_HEIGHT = 35;
  int points = 0;
  int collectedFruits = 0;

  List<Apple<I>> apples = new ArrayList<>();
  Drake<I> drake;

  public DrakeInTheSky() {
    super(new Drake<>(new Vertex(BLOCK_WIDTH * GRID_WIDTH / 2, BLOCK_HEIGHT * GRID_HEIGHT / 2), BLOCK_WIDTH),
        BLOCK_WIDTH * GRID_WIDTH, BLOCK_HEIGHT * GRID_HEIGHT);
    drake = (Drake<I>) getPlayer();

    getGOss().add(drake.getBody());
    getGOss().add(drake.getTail());

    newFruit();
  }

  @Override
  public void paintTo(GraphicsTool<I> g) {
    super.paintTo(g);
    g.drawString(50, 10, "Points: " + points);
    g.drawString(50, 30, "Body size: " + drake.getBody().size());
    g.drawString(50, 50, "Fruits: " + collectedFruits);
  }

  @Override
  public void doChecks() {
    if (drake.touches(apples.get(0))) {
      drake.grow();
      collectedFruits++;
      points += 1;
      moveFruit();
    }
  }

  @Override
  public void keyPressedReaction(KeyCode keycode) {
    if (keycode != null) {
      switch (keycode) {
      case LEFT_ARROW:
        drake.turnLeft();
        break;
      case RIGHT_ARROW:
        drake.turnRight();
        break;
      default:
        ;
      }
    }
  }

  private void newFruit() {
    double x = (int) (Math.random() * (GRID_WIDTH - 1) * BLOCK_WIDTH);
    double y = (int) (Math.random() * (GRID_HEIGHT - 1) * BLOCK_HEIGHT);
    Apple<I> apple = new Apple<I>(new Vertex(x, y));
    apples.add(apple);
    getGOss().add(apples);
  }

  private void moveFruit() {
    double x = (int) (Math.random() * (GRID_WIDTH - 1) * BLOCK_WIDTH);
    double y = (int) (Math.random() * (GRID_HEIGHT - 1) * BLOCK_HEIGHT);
    apples.get(0).getPos().x = x;
    apples.get(0).getPos().y = y;
  }
}
