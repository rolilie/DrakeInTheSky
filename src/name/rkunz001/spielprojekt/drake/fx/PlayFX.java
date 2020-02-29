package name.rkunz001.spielprojekt.drake.fx;

import name.panitz.game.framework.fx.GameApplication;
import name.rkunz001.spielprojekt.drake.game.DrakeInTheSky;

public class PlayFX extends GameApplication {

  public PlayFX() {
    super(new DrakeInTheSky<>());
  }

  public static void main(String[] args) {
    PlayFX.launch();
  }
}
