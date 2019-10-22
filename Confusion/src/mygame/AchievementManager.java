/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.input.event.MouseButtonEvent;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import tonegod.gui.controls.buttons.ButtonAdapter;
import tonegod.gui.core.Screen;

/**
 *
 * @author Bob
 */
public class AchievementManager extends AbstractAppState {
    
  public  SimpleApplication   app;
  public  AppStateManager     stateManager;
  public  Screen              screen;
  public  AssetManager        assetManager;
  public  Player              player;
  
  private ButtonAdapter       exitButton;
  
  private ButtonAdapter       longRunOne;
  private ButtonAdapter       longRunTwo;
  private ButtonAdapter       longRunThree;
  
  private ButtonAdapter       timeAttackOne;
  private ButtonAdapter       timeAttackTwo;
  private ButtonAdapter       timeAttackThree;
  
  private ButtonAdapter        stopperOne;
  private ButtonAdapter        stopperTwo;
  private ButtonAdapter        stopperThree;
  
  private BitmapText           infoText;
  private BitmapFont           infoFont;
  private boolean              isAttached;
  private boolean               achAtt;
    
  @Override
  public void initialize(AppStateManager stateManager, Application app) {
    super.initialize(stateManager, app);
    this.app                 = (SimpleApplication) app;
    this.stateManager        = this.app.getStateManager();
    this.screen              = this.app.getStateManager().getState(GuiManager.class).screen;
    this.assetManager        = this.app.getAssetManager();
    this.player              = this.stateManager.getState(ColorManager.class).player;
    achAtt                  = this.stateManager.getState(GuiManager.class).achAtt;
    infoFont                 = assetManager.loadFont("Interface/Fonts/Impact.fnt");
    infoText                 = new BitmapText(infoFont, false);
    infoText.setColor(ColorRGBA.Black);
    isAttached = false;
    createAchievements();
    }
  
  //Creates the Achievement Button Adapters
  private void createAchievements() {
      
    longRunOne= new ButtonAdapter(screen, "LongRunOne", new Vector2f(15, 15) ){
      @Override
        public void onButtonMouseLeftUp(MouseButtonEvent evt, boolean toggled) {
        infoText.setText("Get at least 50 in Long Run!");
        infoText.setLocalTranslation(screen.getWidth()/2 - infoText.getLineWidth()/2, screen.getHeight()/2 - screen.getHeight()/3, 0);
        }
      };
    
    longRunTwo = new ButtonAdapter(screen, "LongRunTwo", new Vector2f(15, 15)) {
      @Override
        public void onButtonMouseLeftUp(MouseButtonEvent evt, boolean toggle) {
        infoText.setText("Get at least 100 in Long Run!");
        infoText.setLocalTranslation(screen.getWidth()/2 - infoText.getLineWidth()/2, screen.getHeight()/2 - screen.getHeight()/3, 0);
        }
      };
    
    longRunThree = new ButtonAdapter(screen, "LongRunThree", new Vector2f(15, 15)) {
      @Override
        public void onButtonMouseLeftUp(MouseButtonEvent evt, boolean toggle) {
        infoText.setText("Get at least 300 in Long Run!");
        infoText.setLocalTranslation(screen.getWidth()/2 - infoText.getLineWidth()/2, screen.getHeight()/2 - screen.getHeight()/3, 0);
        }
      };
    
    timeAttackOne = new ButtonAdapter(screen, "TimeAttackOne", new Vector2f(15, 15)) {
      @Override
        public void onButtonMouseLeftUp(MouseButtonEvent evt, boolean toggle) {
        infoText.setText("Finish in less than 15 seconds!");
        infoText.setLocalTranslation(screen.getWidth()/2 - infoText.getLineWidth()/2, screen.getHeight()/2 - screen.getHeight()/3, 0);
        }
      };
    
    timeAttackTwo = new ButtonAdapter(screen, "TimeAttackTwo", new Vector2f(15, 15)) {
      @Override
        public void onButtonMouseLeftUp(MouseButtonEvent evt, boolean toggle) {
        infoText.setText("Finish in less than 12 seconds!");
        infoText.setLocalTranslation(screen.getWidth()/2 - infoText.getLineWidth()/2, screen.getHeight()/2 - screen.getHeight()/3, 0);
        }     
      };
    
    timeAttackThree = new ButtonAdapter(screen, "TimeAttackThree", new Vector2f(15, 15)) {
      @Override
        public void onButtonMouseLeftUp(MouseButtonEvent evt, boolean toggle) {
        infoText.setText("Finish in less than 8 seconds!");
        infoText.setLocalTranslation(screen.getWidth()/2 - infoText.getLineWidth()/2, screen.getHeight()/2 - screen.getHeight()/3, 0);
        }     
      };

    stopperOne = new ButtonAdapter(screen, "StopperOne", new Vector2f(15, 15)) {
      @Override
        public void onButtonMouseLeftUp(MouseButtonEvent evt, boolean toggle) {
        infoText.setText("Get at least 12 in Stopper!");
    infoText.setLocalTranslation(screen.getWidth()/2 - infoText.getLineWidth()/2, screen.getHeight()/2 - screen.getHeight()/3, 0);
        }     
      };

    stopperTwo = new ButtonAdapter(screen, "StopperTwo", new Vector2f(15, 15)) {
      @Override
        public void onButtonMouseLeftUp(MouseButtonEvent evt, boolean toggle) {
        infoText.setText("Get at least 20 in Stopper!");
        infoText.setLocalTranslation(screen.getWidth()/2 - infoText.getLineWidth()/2, screen.getHeight()/2 - screen.getHeight()/3, 0);
        }     
      };

    stopperThree = new ButtonAdapter(screen, "StopperThree", new Vector2f(15, 15)) {
      @Override
        public void onButtonMouseLeftUp(MouseButtonEvent evt, boolean toggle) {
        infoText.setText("Get at last 30 in Stopper!");
        infoText.setLocalTranslation(screen.getWidth()/2 - infoText.getLineWidth()/2, screen.getHeight()/2 - screen.getHeight()/3, 0);
        }     
      };
    
    exitButton = new ButtonAdapter(screen, "ExitButton", new Vector2f(15, 15)) {
      @Override
        public void onButtonMouseLeftUp(MouseButtonEvent evt, boolean toggle) {
        hideAchievementButtons();
        achAtt = false;
        stateManager.getState(GuiManager.class).addMainMenu();
        stateManager.getState(ColorManager.class).currentWord = "Stroople";
        stateManager.getState(ColorManager.class).setColors();
        }   
      };

    infoText.setText("Tap for more information!");
    this.app.getGuiNode().attachChild(infoText);
    infoText.setLocalTranslation(screen.getWidth()/2 - infoText.getLineWidth()/2, screen.getHeight()/2 - screen.getHeight()/3, 0);
      
    screen.addElement(longRunOne);
    longRunOne.setDimensions(screen.getWidth()/10, screen.getWidth()/10);
    longRunOne.setPosition(screen.getWidth()/2 - screen.getWidth()/5 - longRunOne.getWidth()/2, screen.getHeight()/2 + screen.getHeight()/5 - longRunOne.getWidth()/2);
    
    screen.addElement(longRunTwo);
    longRunTwo.setDimensions(screen.getWidth()/10, screen.getWidth()/10);
    longRunTwo.setPosition(screen.getWidth()/2 - longRunOne.getWidth()/2, screen.getHeight()/2 + screen.getHeight()/5 - longRunOne.getWidth()/2);

    screen.addElement(longRunThree);
    longRunThree.setDimensions(screen.getWidth()/10, screen.getWidth()/10);
    longRunThree.setPosition(screen.getWidth()/2 + screen.getWidth()/5 - longRunOne.getWidth()/2, screen.getHeight()/2 + screen.getHeight()/5 - longRunOne.getWidth()/2);

    screen.addElement(timeAttackOne);
    timeAttackOne.setDimensions(screen.getWidth()/10, screen.getWidth()/10);
    timeAttackOne.setPosition(screen.getWidth()/2 - screen.getWidth()/5 - longRunOne.getWidth()/2, screen.getHeight()/2 - longRunOne.getWidth()/2);

    screen.addElement(timeAttackTwo);
    timeAttackTwo.setDimensions(screen.getWidth()/10, screen.getWidth()/10);
    timeAttackTwo.setPosition(screen.getWidth()/2 - longRunOne.getWidth()/2, screen.getHeight()/2 - longRunOne.getWidth()/2);

    screen.addElement(timeAttackThree);
    timeAttackThree.setDimensions(screen.getWidth()/10, screen.getWidth()/10);
    timeAttackThree.setPosition(screen.getWidth()/2 + screen.getWidth()/5 - longRunOne.getWidth()/2, screen.getHeight()/2 - longRunOne.getWidth()/2);

    screen.addElement(stopperOne);
    stopperOne.setDimensions(screen.getWidth()/10, screen.getWidth()/10);
    stopperOne.setPosition(screen.getWidth()/2 - screen.getWidth()/5 - longRunOne.getWidth()/2, screen.getHeight()/2 - screen.getHeight()/5 - longRunOne.getWidth()/2);

    screen.addElement(stopperTwo);
    stopperTwo.setDimensions(screen.getWidth()/10, screen.getWidth()/10);
    stopperTwo.setPosition(screen.getWidth()/2 - longRunOne.getWidth()/2, screen.getHeight()/2 - screen.getHeight()/5 - longRunOne.getWidth()/2);

    screen.addElement(stopperThree);
    stopperThree.setDimensions(screen.getWidth()/10, screen.getWidth()/10);
    stopperThree.setPosition(screen.getWidth()/2 + screen.getWidth()/5 - longRunOne.getWidth()/2, screen.getHeight()/2 - screen.getHeight()/5 - longRunOne.getWidth()/2);

    screen.addElement(exitButton);
    exitButton.setDimensions(screen.getWidth()/15, screen.getWidth()/15);
    exitButton.setPosition(0, screen.getHeight() - exitButton.getWidth());
    exitButton.setFont("Interface/Fonts/Impact.fnt");
    exitButton.setText("Back");
    hideAchievementButtons();
    }
  
  //Adds the Achievement Button Adapters to the screen
  public void addAchievements() {
    if (!isAttached) {
    isAttached = true;
    exitButton.show();
    stopperOne.show();
    stopperTwo.show();
    stopperThree.show();
    timeAttackOne.show();
    timeAttackTwo.show();
    timeAttackThree.show();
    longRunOne.show();
    longRunTwo.show();
    longRunThree.show();
    this.app.getGuiNode().attachChild(infoText);
    infoText.setText("Tap for more information!");
    infoText.setLocalTranslation(screen.getWidth()/2 - infoText.getLineWidth()/2, screen.getHeight()/2 - screen.getHeight()/3, -1);
    
    setAchievementColors();
    }
  }
  
  //Sets the Achievement's color based on the player's highscores
  private void setAchievementColors() {
    int  highScore = player.highScore;
    int  bestStop =  player.bestStop;
    int  bestTime =  player.bestTime;
    
    System.out.println(highScore);
    System.out.println(bestStop);
    System.out.println(bestTime);
    
    stopperOne.getMaterial().setColor("Color",ColorRGBA.Black);
    stopperTwo.getMaterial().setColor("Color",ColorRGBA.Black);
    stopperThree.getMaterial().setColor("Color",ColorRGBA.Black);

    longRunOne.getMaterial().setColor("Color",ColorRGBA.Black);
    longRunTwo.getMaterial().setColor("Color",ColorRGBA.Black);
    longRunThree.getMaterial().setColor("Color",ColorRGBA.Black);

    timeAttackOne.getMaterial().setColor("Color",ColorRGBA.Black);
    timeAttackTwo.getMaterial().setColor("Color",ColorRGBA.Black);
    timeAttackThree.getMaterial().setColor("Color",ColorRGBA.Black);
    
    if (highScore >= 300)
    longRunThree.getMaterial().setColor("Color", ColorRGBA.Green.mult(3));
    
    if (highScore >= 100)
    longRunTwo.getMaterial().setColor("Color", ColorRGBA.Blue.mult(3));
    
    if (highScore >= 50)
    longRunOne.getMaterial().setColor("Color", ColorRGBA.Magenta.mult(3));

    if (bestStop >= 30)
    stopperThree.getMaterial().setColor("Color", ColorRGBA.Yellow.mult(3));

    if (bestStop >= 20)
    stopperTwo.getMaterial().setColor("Color", ColorRGBA.Orange.mult(3));

    if (bestStop >= 12)
    stopperOne.getMaterial().setColor("Color", ColorRGBA.Red.mult(3));

    if (bestTime <= 8)
    timeAttackThree.getMaterial().setColor("Color", ColorRGBA.Cyan.mult(3));

    if (bestTime <= 12)
    timeAttackTwo.getMaterial().setColor("Color", ColorRGBA.Pink.mult(3));

    if (bestTime <= 15)
    timeAttackOne.getMaterial().setColor("Color", ColorRGBA.White.mult(3));
    
    }
  
  //Clears the screan and goes back to the main menu
  private void hideAchievementButtons(){
    exitButton.hide();
    stopperOne.hide();
    stopperTwo.hide();
    stopperThree.hide();
    timeAttackOne.hide();
    timeAttackTwo.hide();
    timeAttackThree.hide();
    longRunOne.hide();
    longRunTwo.hide();
    longRunThree.hide();
    this.app.getGuiNode().detachChild(infoText);  
    isAttached = false;
    
    }

  }
