/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.asset.AssetNotFoundException;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.export.binary.BinaryExporter;
import com.jme3.scene.Node;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bob
 */
public class Player {
  
  public int  score;
  public int  finalScore;
  public long finalTime;
  public int  finalStop;
  public int  highScore;
  public int  bestTime;
  public int  bestStop;
  public Node scoreNode;
  
  
  public void getScoreNode(AppStateManager stateManager) {
    String filePath           = stateManager.getState(AndroidManager.class).filePath;
    AssetManager assetManager = stateManager.getApplication().getAssetManager();
    assetManager.registerLocator(filePath, FileLocator.class);
    System.out.println("Attempting to Read Score File Path is: " + filePath);
    
    try {
      scoreNode = (Node) assetManager.loadModel("scoreNode.j3o");
      System.out.println("File Successfully found!");
      }
    
    //Should save a random 0 score, should come up null and create a new one
    catch (AssetNotFoundException ex) {
      System.out.println("ScoreNode Not Found");  
      scoreNode = createScoreNode();
      exportScore(stateManager);
      }
    
    }
  
  private void exportScore(AppStateManager stateManager) { 
    String filePath           = stateManager.getState(AndroidManager.class).filePath;
    BinaryExporter exporter   = BinaryExporter.getInstance();
    File file                 = new File(filePath + "/scoreNode.j3o");
    AssetManager assetManager = stateManager.getApplication().getAssetManager();
    assetManager.registerLocator(filePath, FileLocator.class);
    
    System.out.println("Attempting to save to: " + filePath + "/scoreNode.j3o"); 
   
    try {
      exporter.save(scoreNode, file);
      System.out.println("Successfully saved to: " + filePath + "/scoreNode.j3o");
      }
    
    catch (IOException e) { 
      Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "Error: Failed to save game!", e);  
      System.out.println("Failure");
      }
    
    }
  
  
  public void saveScore(AppStateManager stateManager, String type, int score) {
    
    //Sets the data to the above node, new or not
    System.out.println("Saving Type: " + type + "Amount: " + score);  
    scoreNode.setUserData(type, score);
    exportScore(stateManager);
    
    }
  
  public void readScore(AppStateManager stateManager) {  
    System.out.println("Reading Score");
    highScore = scoreNode.getUserData("BestScore");
    bestTime  = scoreNode.getUserData("BestTime");
    bestStop  = scoreNode.getUserData("BestStop");    
    }
  
  private Node createScoreNode() {
    System.out.println("Creating Score Node");  
    Node scoreNode = new Node();  
    scoreNode.setUserData("BestScore", 0);
    scoreNode.setUserData("BestTime", 999); 
    scoreNode.setUserData("BestStop", 0);
    return scoreNode;
    }
    
  }
