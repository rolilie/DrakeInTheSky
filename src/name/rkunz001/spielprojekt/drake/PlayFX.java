package name.rkunz001.spielprojekt.drake;

import name.panitz.game.framework.fx.GameApplication;

public class PlayFX extends GameApplication {

  public PlayFX() {
    super(new DrakeInTheSky<>());
  }

  public static void main(String[] args) {
    PlayFX.launch();
  }
}
