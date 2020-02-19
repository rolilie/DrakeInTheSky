package name.rkunz001.spielprojekt.drake;

import name.panitz.game.framework.ImageObject;
import name.panitz.game.framework.Vertex;

public class Apple<I> extends ImageObject<I> {

  public Apple(Vertex corner) {
    super("apple.png", corner, new Vertex(0, 0));
  }

}
