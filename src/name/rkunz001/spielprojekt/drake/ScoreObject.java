package name.rkunz001.spielprojekt.drake;

import name.panitz.game.framework.ImageObject;
import name.panitz.game.framework.KeyCode;
import name.panitz.game.framework.Vertex;

public class ScoreObject<I> extends ImageObject<I> {

  int score;
  KeyCode keyCode;
  int speedUp = 0;
  boolean toggle = false;

  public ScoreObject(String imageName, Vertex corner, Vertex velocity, int score) {
    super(imageName, corner, velocity);
    this.score = score;
  }

  public ScoreObject(String imageName, Vertex corner, Vertex velocity, int score, KeyCode keyCode) {
    super(imageName, corner, velocity);
    this.score = score;
    this.keyCode = keyCode;
  }

  public ScoreObject(String imageName, Vertex corner, Vertex velocity, int score, int speedUp) {
    super(imageName, corner, velocity);
    this.score = score;
    this.speedUp = speedUp;
  }

  public ScoreObject(String imageName, Vertex corner, Vertex velocity, int score, boolean toggle) {
    super(imageName, corner, velocity);
    this.score = score;
    this.toggle = toggle;
  }

  public ScoreObject(String imageName, Vertex corner, Vertex velocity, int score, KeyCode keyCode, int speedUp,
      boolean toggle) {
    super(imageName, corner, velocity);
    this.score = score;
    this.keyCode = keyCode;
    this.speedUp = speedUp;
    this.toggle = toggle;
  }

  public int getScore() {
    return score;
  }

  public void setScore(int score) {
    this.score = score;
  }

  public KeyCode getKeyCode() {
    return keyCode;
  }

  public void setKeyCode(KeyCode keyCode) {
    this.keyCode = keyCode;
  }

  public int getSpeedUp() {
    return speedUp;
  }

  public void setSpeedUp(int speed) {
    this.speedUp = speed;
  }

  public boolean isToggle() {
    return toggle;
  }

  public void setToggle(boolean toggle) {
    this.toggle = toggle;
  }

}
