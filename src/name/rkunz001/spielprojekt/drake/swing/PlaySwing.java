package name.rkunz001.spielprojekt.drake.swing;

import name.panitz.game.framework.swing.SwingGame;
import name.rkunz001.spielprojekt.drake.game.DrakeInTheSky;

public class PlaySwing {

  public static void main(String[] args) {
    SwingGame.startGame(new DrakeInTheSky<>());
  }
}
