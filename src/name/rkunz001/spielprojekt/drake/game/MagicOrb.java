package name.rkunz001.spielprojekt.drake.game;

import name.panitz.game.framework.KeyCode;
import name.panitz.game.framework.Vertex;

public class MagicOrb<I> extends ScoreObject<I> {

  KeyCode keyCode;

  public MagicOrb(Vertex corner, Vertex velocity, int score, KeyCode keyCode) {
    super("magic-orb.png", corner, velocity, score);
    this.keyCode = keyCode;
  }

  public KeyCode getKeyCode() {
    return keyCode;
  }

  public void setKeyCode(KeyCode keyCode) {
    this.keyCode = keyCode;
  }
}
