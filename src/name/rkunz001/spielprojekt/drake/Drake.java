package name.rkunz001.spielprojekt.drake;

import name.panitz.game.framework.Vertex;

public class Drake<I> extends Snake<I> {

  public Drake(Vertex corner, int bodySize, int blockSize, int width, int height) {
    super(corner, new Vertex(1, 0), bodySize, blockSize, width, height);
    this.blockSize = blockSize;
  }

}
