package name.rkunz001.spielprojekt.drake;

import name.panitz.game.framework.Vertex;

public class Drake<I> extends Snake<I> {

  static String HEAD_IMAGE = "drake-right.png";
  static String BODY_IMAGE = "drake-body.gif";
  static String TAIL_IMAGE = "drake-body.png";

  public Drake(Vertex corner, int bodySize, int blockSize, int width, int height) {
    super(corner, new Vertex(1, 0), bodySize, blockSize, width, height, "drake-right.png", "drake-body.png",
        "tail-left.png");
    this.blockSize = blockSize;

    headLeft = "drake-left.png";
    headUp = "drake-up.png";
    headDown = "drake-down.png";
    tailRight = "tail-right.png";
    tailUp = "tail-up.png";
    tailDown = "tail-down.png";
  }

}
