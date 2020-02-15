package name.rkunz001.spielprojekt.drake;

import name.panitz.game.framework.Vertex;

public class Drake<I> extends LeftRightgImage<I> {
  public Drake(Vertex corner) {
    super("player.png", corner, new Vertex(1, 0));
  }
}
