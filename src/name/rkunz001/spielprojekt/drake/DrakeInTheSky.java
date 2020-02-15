package name.rkunz001.spielprojekt.drake;

import name.panitz.game.framework.AbstractGame;
import name.panitz.game.framework.KeyCode;
import name.panitz.game.framework.Vertex;

public class DrakeInTheSky<I, S> extends AbstractGame<I, S> {

  static final int GRID_WIDTH = 44;
  static final int GRID_HEIGHT = 37;
  static final int BLOCK_WIDTH = 17;
  static final int BLOCK_HEIGHT = 22;

  Drake<I> drake;

  public DrakeInTheSky() {
    super(
        new Drake<>(new Vertex(BLOCK_WIDTH * GRID_WIDTH / 2,
            BLOCK_HEIGHT * GRID_HEIGHT / 2)),
        BLOCK_WIDTH * GRID_WIDTH, BLOCK_HEIGHT * GRID_HEIGHT);
//    new Drake<>(new Vertex(BLOCK_WIDTH * GRID_WIDTH / 2,
//        BLOCK_HEIGHT * GRID_HEIGHT)),
//    BLOCK_WIDTH * GRID_WIDTH, BLOCK_HEIGHT * GRID_HEIGHT);
    drake = (Drake<I>) getPlayer();
  }

  @Override
  public void doChecks() {
    // TODO Auto-generated method stub

  }

  @Override
  public void keyPressedReaction(KeyCode keycode) {
    if (keycode != null)
      switch (keycode) {
      case LEFT_ARROW:
        drake.left();
        break;
      case RIGHT_ARROW:
        drake.right();
        break;
      default:
        ;
      }
  }

}
