package name.rkunz001.spielprojekt.drake.game;

import name.panitz.game.framework.Vertex;

public class FireOrb<I> extends ScoreObject<I> {

  public FireOrb(Vertex corner, Vertex velocity, int score) {
    super("fire-orb.png", corner, velocity, score);
  }
}
