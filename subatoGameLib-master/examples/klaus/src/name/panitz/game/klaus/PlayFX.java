package name.panitz.game.klaus;


import name.panitz.game.framework.fx.GameApplication;

public class PlayFX extends GameApplication {
  public PlayFX(){
    super(new HeartsOfKlaus<>());
  }
  public static void main(String[] args) {
    PlayFX.launch();
  }
}
