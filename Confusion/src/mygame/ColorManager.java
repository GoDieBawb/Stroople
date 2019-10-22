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
import com.jme3.export.binary.BinaryExporter;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.input.InputManager;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Node;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import tonegod.gui.controls.buttons.ButtonAdapter;
import tonegod.gui.core.Screen;

/**
 *
 * @author Bob
 */
public class ColorManager extends AbstractAppState {

  public ColorRGBA           currentColor;
  public String              currentWord;
  public SimpleApplication   app;
  public AppStateManager     stateManager;
  public InputManager        inputManager;
  public Screen              screen;
  public AssetManager        assetManager;
  public Player              player;
  
  private BitmapFont         font;
  public  BitmapText         wordText;
  public  BitmapText         scoreText;
  public  BitmapText         highScoreText;
  public  BitmapText         finalScoreText;
  
  private int                clickDelay;
  private int                lossDelay;
  private boolean            hasStarted;
  private Node               highScoreNode;
  private boolean            hasLost;
  private boolean            longRun;
  private boolean            timeAttack;
  private boolean            stopper;
  private boolean            wrongPress;
  
  private long               startTime;
  private long               finishTime;

  @Override
  public void initialize(AppStateManager stateManager, Application app) {
    super.initialize(stateManager, app);
    this.app                 = (SimpleApplication) app;
    this.stateManager        = this.app.getStateManager();
    this.inputManager        = this.app.getInputManager();
    this.assetManager        = this.app.getAssetManager();
    this.screen              = stateManager.getState(GuiManager.class).screen;
    font                     = assetManager.loadFont("Interface/Fonts/Impact.fnt");
    wordText                 = new BitmapText(font, false);
    scoreText                = new BitmapText(font, false);
    highScoreText            = new BitmapText(font, false);
    finalScoreText           = new BitmapText(font, false);
    
    finalScoreText.setColor(ColorRGBA.Black);
    highScoreText.setColor(ColorRGBA.Black);
    scoreText.setColor(ColorRGBA.Black);
    
    player           = new Player();
    player.getScoreNode(stateManager);
    player.readScore(stateManager);
    
    System.out.println("Player scores: " + player.bestTime);
    System.out.println("Player scores: " + player.bestStop);
    System.out.println("Player scores: " + player.highScore);
    
    longRun       = false;
    timeAttack    = false;
    stopper       = false;
    wrongPress    = false;
    this.app.getGuiNode().attachChild(wordText);
    currentWord = "Stroople";
    currentColor = ColorRGBA.randomColor().mult(3);
    setColors();
    }
  
  
  //Ran when a button is clicked on the Main Menu
  public void gamemodeSetup(String gameType) {

    this.app.getGuiNode().attachChild(scoreText);
    this.app.getGuiNode().attachChild(wordText);
      
    if (gameType.equals("LongRun")){
      longRun          = true;
      timeAttack       = false;
      stopper          = false;
      lossDelay        = 100;
      }
    
    if (gameType.equals("TimeAttack")) {
      timeAttack       = true;
      longRun          = false;
      stopper          = false;
      startTime        = System.currentTimeMillis() / 1000L;
      }
    
    if (gameType.equals("Stopper")) {
      stopper          = true;
      timeAttack       = false;
      longRun          = false;
      startTime        = System.currentTimeMillis() / 1000L;
      }
    
    player.score     = -1;
    clickDelay       = 11;
    hasStarted       = false;
    wrongPress       = false;
    hasLost          = false;
    changeColor();
    }
  
  //This method is run by the ingame buttons.
  public void buttonCheck(ButtonAdapter button) {
 
    if (hasLost) {
      
      resetGame();
    
      //Stops Double Clicking Issue
      } else if (clickDelay > 10) {
        //Reset the Click Delay
        clickDelay = 0;

        //If the player score is 0 means the game is starting, retrieving the new possible highscore
        if (player.score == 0) {
          hasStarted = true;
          }
        
        //Checks if the correct button was pressed
        if (button.getName().contains(currentColor.toString())) 
        changeColor();
        
        else 
        wrongPress = true;
        }
    }
  
  //Resets the main screen bringing back up the main menu
  private void resetGame() {

    this.app.getGuiNode().detachChild(finalScoreText);
    this.app.getGuiNode().detachChild(highScoreText);
    this.app.getGuiNode().detachChild(scoreText);
    
    stateManager.getState(GuiManager.class).clearScreen();
    stateManager.getState(GuiManager.class).addMainMenu();
    
    currentWord = "Stroople";
    currentColor = ColorRGBA.randomColor().mult(3);
    setColors();
    }
  
  //Lose Method
  private void Lose() {
      
    hasStarted        = false;
    hasLost           = true;
    currentColor      = ColorRGBA.Black;
    clickDelay        = 0;
    currentWord       = "You Lose!";
    player.finalScore = player.score;
    stopper           = false;
    
    //If long run just run this message
    if (longRun){
      longRunFinish();
      }

    setColors();
    changeScoreDisplay();
    
    //Sets the location of the stats on the loss screen
    finalScoreText.setLocalTranslation(screen.getWidth()/2 - finalScoreText.getLineWidth()/2, screen.getWidth()/2.6f, 0);
    highScoreText.setLocalTranslation(screen.getWidth()/2 - highScoreText.getLineWidth()/2, screen.getWidth()/3.1f, 0);
    }
  
  //Runs when the player makes a mistake in longRun
  private void longRunFinish() {
     
    //Setting the scores before checking the high scores
    this.app.getGuiNode().attachChild(finalScoreText);
    this.app.getGuiNode().attachChild(highScoreText);
    finalScoreText.setText(String.valueOf("Final Score: " + player.finalScore));
    highScoreText.setText(String.valueOf("High Score: " + player.highScore));

    //If players final score is higher than the highscore, update the .j3o
    if(player.finalScore > player.highScore) {
      player.highScore = player.finalScore;
      player.saveScore(stateManager, "BestScore", player.finalScore);
      highScoreText.setText(String.valueOf("New High Score: " + player.highScore));
      highScoreText.setColor(ColorRGBA.Red);
      }
    
    finalScoreText.setLocalTranslation(screen.getWidth()/2 - finalScoreText.getLineWidth()/2, screen.getWidth()/2.8f, -1);
    highScoreText.setLocalTranslation(screen.getWidth()/2 - highScoreText.getLineWidth()/2, screen.getWidth()/3.1f, -1);
    }
  
  //Ran when Time Attack has reached 20 
  private void timeAttackWin() {
     
    //Has lost is used here to signify the end also sets the finish time, then maths it 
    hasStarted        = false;
    hasLost           = true;
    finishTime        =  System.currentTimeMillis() / 1000L;
    currentColor      = ColorRGBA.Black;
    currentWord       = "Finish!";
    player.finalTime  = finishTime - startTime;
    
    
    //Sets the final text for the time attack completion
    finalScoreText.setText(String.valueOf("Final Time: " + player.finalTime + "s"));
    highScoreText.setText(String.valueOf("Best Time: " + player.bestTime + "s"));
    this.app.getGuiNode().attachChild(finalScoreText);
    this.app.getGuiNode().attachChild(highScoreText);
    
    
    //If players final time is lower than the best time update the .j3o and rewrite final text
    if(player.bestTime > player.finalTime) {
      player.bestTime = (int) player.finalTime;
      int finalTime   = (int) player.finalTime;
      player.saveScore(stateManager, "BestTime", finalTime);
      highScoreText.setText(String.valueOf("New Best Time: " + player.bestTime + "s"));
      highScoreText.setColor(ColorRGBA.Red);
      }
    
    
    //Resets proper things to zero and updates the display to show the above set text
    player.finalTime = 0;
    clickDelay       = 0;
    setColors();
    changeScoreDisplay();
    
    //Sets the location of the stats on the loss screen
    finalScoreText.setLocalTranslation(screen.getWidth()/2 - finalScoreText.getLineWidth()/2, screen.getWidth()/2.7f, 0);
    highScoreText.setLocalTranslation(screen.getWidth()/2 - highScoreText.getLineWidth()/2, screen.getWidth()/3.1f, 0);
    
    }
 
  //Ran when stopper is finished
  private void stopperFinish(){
    //Setting the scores before checking the high scores
    finalScoreText.setText(String.valueOf("Final Stop: " + player.score));
    highScoreText.setText(String.valueOf("Best Stop: " + player.bestStop));
    
    currentWord = "Finish!";
    currentColor = ColorRGBA.Black;
    player.finalStop = player.score;
    this.app.getGuiNode().attachChild(finalScoreText);
    this.app.getGuiNode().attachChild(highScoreText);

    //If players final score is higher than the highscore, update the .j3o
    if(player.finalStop > player.bestStop) {
      player.bestStop = player.finalStop;
      player.saveScore(stateManager, "BestStop", player.finalStop);
      highScoreText.setText(String.valueOf("New Best Stop: " + player.bestStop));
      highScoreText.setColor(ColorRGBA.Red);
      }

    player.score = 0;
    clickDelay   = 0;
    hasLost = true;
    setColors();
    changeScoreDisplay();

    //Sets the location of the stats on the loss screen
    finalScoreText.setLocalTranslation(screen.getWidth()/2 - finalScoreText.getLineWidth()/2, screen.getWidth()/2.7f, 0);
    highScoreText.setLocalTranslation(screen.getWidth()/2 - highScoreText.getLineWidth()/2, screen.getWidth()/3.1f, 0);
   }
  
  //Method for randomizing color and word
  private void changeColor() {
    player.score++;
    changeWord();
    changeFontColor();
    setColors();
    changeScoreDisplay();
    highScoreText.setColor(ColorRGBA.Black);
    }
  
  //Randomizes the name of the color
  private void changeWord() {
    Random rand = new Random();
    int colorChance = rand.nextInt(4) + 1;
    if (colorChance == 1 )
    currentWord = "Blue";
    if (colorChance == 2)
    currentWord = "Red";
    if (colorChance == 3)
    currentWord = "Green";
    if (colorChance == 4)
    currentWord = "Yellow";
    }
  
  //Randomizes the color of the word
  private void changeFontColor() {
    Random rand = new Random();
    rand.nextInt(4);
    int colorChance = rand.nextInt(4) + 1;
    if (colorChance == 1 )
    currentColor = ColorRGBA.Blue;
    if (colorChance == 2)
    currentColor = ColorRGBA.Red;
    if (colorChance == 3)
    currentColor = ColorRGBA.Green;
    if (colorChance == 4)
    currentColor = ColorRGBA.Yellow;
    }
  
  //Applies the set word and color
  public void setColors() {
    wordText.setColor(currentColor);
    wordText.setText(currentWord);
    wordText.setSize(screen.getWidth()/6);
    wordText.setLocalTranslation(screen.getWidth()/2 - wordText.getLineWidth()/2, screen.getWidth()/1.8f, -1);
    }
  
  //Updates the score display to the new score
  private void changeScoreDisplay() {
    scoreText.setText(String.valueOf(player.score));
    scoreText.setLocalTranslation(screen.getWidth()/1.1f - scoreText.getLineWidth()/2f, screen.getWidth()/1.9f, 0);
    }
  
  //Update loops checks to see if the game has started, and waiting too long caueses loss
  @Override
  public void update(float tpf) {
    if (hasStarted)
    clickDelay++;
    
    if (clickDelay > lossDelay && longRun && !hasLost)
    Lose();
    
    if (wrongPress && !hasLost)
    Lose();
    
    
    //Time attack adds one to the score to not continously run timeAttackWin
    if (player.score == 20 && timeAttack) {
      timeAttackWin();
      player.score++;
      }
    
    if (stopper) {
      Long currentTime = System.currentTimeMillis() / 1000L;
      if ((currentTime - startTime) > 9) {
        stopperFinish();
        stopper = false;
        }
      }
    }
  }
