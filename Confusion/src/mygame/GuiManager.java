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
import com.jme3.input.InputManager;
import com.jme3.input.event.MouseButtonEvent;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import tonegod.gui.controls.buttons.ButtonAdapter;
import tonegod.gui.core.Screen;


public class GuiManager extends AbstractAppState {
    
  public SimpleApplication   app;
  public AppStateManager     stateManager;
  public InputManager        inputManager;
  public Screen              screen;
  public AssetManager        assetManager;
  
  private ColorManager       colorManager;
  private ButtonAdapter      buttonOne;
  private ButtonAdapter      buttonTwo;
  private ButtonAdapter      buttonThree;
  private ButtonAdapter      buttonFour;
  private ButtonAdapter      longRunButton;
  private ButtonAdapter      timeAttackButton;
  private ButtonAdapter      stopperButton;
  private ButtonAdapter      achievementButton;
  
  private boolean            mainAttached;
  private boolean            colorAttached;
  public  boolean            achAtt;

  @Override
  public void initialize(AppStateManager stateManager, Application app) {
    super.initialize(stateManager, app);
    this.app                 = (SimpleApplication) app;
    this.stateManager        = this.app.getStateManager();
    this.inputManager        = this.app.getInputManager();
    this.assetManager        = this.app.getAssetManager();
    this.colorManager        = this.app.getStateManager().getState(ColorManager.class);
    
    screen = new Screen(app, "tonegod/gui/style/atlasdef/style_map.gui.xml");
    screen.setUseTextureAtlas(true,"tonegod/gui/style/atlasdef/atlas.png");
    screen.setUseMultiTouch(true);
    this.app.getGuiNode().addControl(screen);
    
    colorAttached = false;
    mainAttached  = false;
    app.getViewPort().setBackgroundColor(ColorRGBA.White);
    createMainMenu();
    createColorButtons();
    addMainMenu();
    
    }
  
  //Creates the Main Menu
  private void createMainMenu() {
      
    longRunButton = new ButtonAdapter(screen, "LongRun", new Vector2f(15, 15) ){
      @Override
        public void onButtonMouseLeftUp(MouseButtonEvent evt, boolean toggled) {
          
          if (!achAtt) {
          colorManager.gamemodeSetup("LongRun");
          clearScreen();
          addColorScreen();
          }
          
          else {
            achAtt = false;  
            }
          
          }
      };
    
    timeAttackButton = new ButtonAdapter(screen, "TimeAttack", new Vector2f(15, 15)) {
      @Override
        public void onButtonMouseLeftUp(MouseButtonEvent evt, boolean toggled) {
          
          if (!achAtt) {
            colorManager.gamemodeSetup("TimeAttack");
            clearScreen();
            addColorScreen();
            }
          
          else {
            achAtt = false;
            }
          
          }
      };
    
    stopperButton = new ButtonAdapter(screen, "Stopper", new Vector2f(15, 15)) {
      @Override
        public void onButtonMouseLeftUp(MouseButtonEvent evt, boolean toggle) {
          
          if (!achAtt) {
            colorManager.gamemodeSetup("Stopper");
            clearScreen();
            addColorScreen();
            }
          
          else {
            achAtt = false;
            }
          
          }
        };
    
    achievementButton = new ButtonAdapter(screen, "Achivement", new Vector2f(15, 15)) {
      @Override
        public void onButtonMouseLeftUp(MouseButtonEvent evt, boolean toggle) {
          achAtt = true;
          clearScreen();
          stateManager.getState(AchievementManager.class).addAchievements();
          colorManager.currentWord = "";
          colorManager.setColors();
          }
      };
    
    screen.addElement(longRunButton);
    longRunButton.setWidth(screen.getWidth()/4);
    longRunButton.setHeight(screen.getWidth()/4);
    longRunButton.setPosition(0, 0);
    longRunButton.setText("Long Run");
    longRunButton.setFont("Interface/Fonts/Impact.fnt");
    
    screen.addElement(timeAttackButton);
    timeAttackButton.setWidth(screen.getWidth()/4);
    timeAttackButton.setHeight(screen.getWidth()/4);
    timeAttackButton.setPosition(longRunButton.getX() + timeAttackButton.getWidth(), longRunButton.getY());
    timeAttackButton.setText("Time Attack");
    timeAttackButton.setFont("Interface/Fonts/Impact.fnt");
    
    screen.addElement(stopperButton);
    stopperButton.setWidth(screen.getWidth()/4);
    stopperButton.setHeight(screen.getWidth()/4);
    stopperButton.setPosition(timeAttackButton.getX() + stopperButton.getWidth(), longRunButton.getY());
    stopperButton.setText("Stopper");
    stopperButton.setFont("Interface/Fonts/Impact.fnt");
    
    screen.addElement(achievementButton);
    achievementButton.setWidth(screen.getWidth()/4);
    achievementButton.setHeight(screen.getWidth()/4);
    achievementButton.setPosition(stopperButton.getX() + achievementButton.getWidth(), longRunButton.getY());
    achievementButton.setText("Achievements");
    achievementButton.setFont("Interface/Fonts/Impact.fnt");
    
    //achievementButton.setEffectZOrder(false);
    //longRunButton.setEffectZOrder(false);
    //timeAttackButton.setEffectZOrder(false);
    //stopperButton.setEffectZOrder(false);

    }

  //Creates the in game buttons
  private void createColorButtons() {

    buttonOne = new ButtonAdapter( screen, ColorRGBA.Blue.toString(), new Vector2f(15, 15) ) {
       @Override
        public void onButtonMouseLeftUp(MouseButtonEvent evt, boolean toggled) {
        ButtonAdapter button = buttonOne;
        colorManager.buttonCheck(button);
        }
      };

    buttonTwo = new ButtonAdapter( screen, ColorRGBA.Red.toString(), new Vector2f(15, 15) ) {
       @Override
        public void onButtonMouseLeftUp(MouseButtonEvent evt, boolean toggled) {
         ButtonAdapter button = buttonTwo;
         colorManager.buttonCheck(button);
        }
      };

    buttonThree = new ButtonAdapter( screen, ColorRGBA.Green.toString(), new Vector2f(15, 15) ) {
       @Override
        public void onButtonMouseLeftUp(MouseButtonEvent evt, boolean toggled) {
        ButtonAdapter button = buttonThree;
        colorManager.buttonCheck(button);
        }
      };

    buttonFour = new ButtonAdapter( screen, ColorRGBA.Yellow.toString(), new Vector2f(15, 15) ) {
       @Override
        public void onButtonMouseLeftUp(MouseButtonEvent evt, boolean toggled) {
        ButtonAdapter button = buttonFour;
        colorManager.buttonCheck(button);
        }
      };
    
    screen.addElement(buttonOne);
    buttonOne.setWidth(screen.getWidth()/4);
    buttonOne.setHeight(screen.getWidth()/4);
    buttonOne.setPosition(0, 0);
    
    screen.addElement(buttonTwo);
    buttonTwo.setWidth(screen.getWidth()/4);
    buttonTwo.setHeight(screen.getWidth()/4);
    buttonTwo.setPosition(buttonOne.getX() + buttonTwo.getWidth(), buttonOne.getY());
    
    screen.addElement(buttonThree);
    buttonThree.setWidth(screen.getWidth()/4);
    buttonThree.setHeight(screen.getWidth()/4);
    buttonThree.setPosition(buttonTwo.getX() + buttonThree.getWidth(), buttonOne.getY());
    
    screen.addElement(buttonFour);
    buttonFour.setWidth(screen.getWidth()/4);
    buttonFour.setHeight(screen.getWidth()/4);
    buttonFour.setPosition(buttonThree.getX() + buttonFour.getWidth(), buttonOne.getY());
    buttonOne.getMaterial().setColor("Color", ColorRGBA.Blue.mult(3));
    buttonTwo.getMaterial().setColor("Color", ColorRGBA.Red.mult(3));
    buttonThree.getMaterial().setColor("Color", ColorRGBA.Green.mult(3));
    buttonFour.getMaterial().setColor("Color", ColorRGBA.Yellow.mult(3));
    
    //buttonOne.setEffectZOrder(false);
    //buttonTwo.setEffectZOrder(false);
    //buttonThree.setEffectZOrder(false);
   // buttonFour.setEffectZOrder(false);
    }
  
  //Adds the Main Menu to the screen
  public void addMainMenu() {
  if (!mainAttached) {
    clearScreen(); 
    mainAttached = true;
    longRunButton.show();
    timeAttackButton.show();
    stopperButton.show();
    achievementButton.show();
    }
    }

  //Adds the Color to the screen
  private void addColorScreen() {
  if (!colorAttached) {
    clearScreen();  
    colorAttached = true;
    buttonOne.show();
    buttonTwo.show();
    buttonThree.show();
    buttonFour.show();
    }
    }
  
  //Clears the elements from the screen
  public void clearScreen() {
    buttonOne.hide();
    buttonTwo.hide();
    buttonThree.hide();
    buttonFour.hide();
    longRunButton.hide();
    stopperButton.hide();
    timeAttackButton.hide();
    achievementButton.hide();
    /**screen.removeElement(buttonOne);
    screen.removeElement(buttonTwo);
    screen.removeElement(buttonThree);
    screen.removeElement(buttonFour);   
    screen.removeElement(longRunButton);
    screen.removeElement(stopperButton);
    screen.removeElement(timeAttackButton);
    screen.removeElement(achievementButton);
    */
    mainAttached  = false;
    colorAttached = false;
    }
  
  }
