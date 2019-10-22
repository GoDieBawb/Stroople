package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.renderer.RenderManager;

/**
 * test
 * @author normenhansen
 */
public class Main extends SimpleApplication {

    public static void main(String[] args) {
        Main app = new Main();
        app.start();
      }

    @Override
    public void simpleInitApp() {
      this.setShowSettings(false);
      this.setDisplayFps(false);
      this.setDisplayStatView(false);
      this.getStateManager().attach(new GuiManager());
      this.getStateManager().attach(new ColorManager());
      this.getStateManager().attach(new InteractionManager());
      this.getStateManager().attach(new AchievementManager());
      }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
      }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
