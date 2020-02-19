package name.rkunz001.spielprojekt.drake;

import name.panitz.game.framework.AbstractGame;
import name.panitz.game.framework.KeyCode;
import name.panitz.game.framework.Vertex;

public class DrakeInTheSky<I, S> extends AbstractGame<I, S> {

  static final int GRID_WIDTH = 40;
  static final int GRID_HEIGHT = 28;
  static final int BLOCK_WIDTH = 35;
  static final int BLOCK_HEIGHT = 35;

  Drake<I> drake;

  public DrakeInTheSky() {
    super(new Drake<>(new Vertex(BLOCK_WIDTH * GRID_WIDTH / 2, BLOCK_HEIGHT * GRID_HEIGHT / 2), BLOCK_WIDTH),
        BLOCK_WIDTH * GRID_WIDTH, BLOCK_HEIGHT * GRID_HEIGHT);
    drake = (Drake<I>) getPlayer();

    getGOss().add(drake.getBody());
    getGOss().add(drake.getTail());
  }

  @Override
  public void doChecks() {
    // TODO Auto-generated method stub

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

}
