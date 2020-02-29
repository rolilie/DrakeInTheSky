package name.rkunz001.spielprojekt.drake.game;

import name.panitz.game.framework.ImageObject;
import name.panitz.game.framework.Vertex;

public class ScoreObject<I> extends ImageObject<I> {

  int score;

  public ScoreObject(String imageName, Vertex corner, Vertex velocity,
      int score) {
    super(imageName, corner, velocity);
    this.score = score;
  }

  public int getScore() {
    return score;
  }

  public void setScore(int score) {
    this.score = score;
  }

}
