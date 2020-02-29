package name.rkunz001.spielprojekt.drake.game;

import name.panitz.game.framework.Vertex;

public class EnergyOrb<I> extends ScoreObject<I> {

  int speedUp = 0;

  public EnergyOrb(Vertex corner, Vertex velocity, int score, int speedUp) {
    super("energy-orb.png", corner, velocity, score);
    this.score = score;
    this.speedUp = speedUp;
  }

  public int getSpeedUp() {
    return speedUp;
  }

  public void setSpeedUp(int speed) {
    this.speedUp = speed;
  }
}
