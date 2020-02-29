package name.rkunz001.spielprojekt.drake.game;

import name.panitz.game.framework.Vertex;

public class ShimmerOrb<I> extends ScoreObject<I> {

  int width;
  int height;
  int blockSize;

  public ShimmerOrb(Vertex corner, Vertex velocity, int score, int width,
      int height, int blockSize) {
    super("shimmer-orb.png", corner, velocity, score);
    this.width = width;
    this.height = height;
    this.blockSize = blockSize;
    getVelocity().x = getRandomSpeed();
    getVelocity().y = getRandomSpeed();
  }

  @Override
  public void move() {

    if (getPos().x < blockSize) {
      getVelocity().x = Math.abs(getVelocity().x);
      getPos().x = blockSize;
    } else if (getPos().x > width - blockSize) {
      getVelocity().x = Math.abs(getVelocity().x) * -1;
      getPos().x = width - blockSize;
    }

    if (getPos().y < blockSize) {
      getVelocity().y = Math.abs(getVelocity().y);
      getPos().y = blockSize;
    } else if (getPos().y > height - blockSize) {
      getVelocity().y = Math.abs(getVelocity().y) * -1;
      getPos().y = height - blockSize;
    }

    super.move();
  }

  double getRandomSpeed() {
    double speed = 0.0;
    speed = (Math.random() * 3);
    if (Math.random() > 0.5) {
      speed *= -1;
    }
    return speed;
  }
}
