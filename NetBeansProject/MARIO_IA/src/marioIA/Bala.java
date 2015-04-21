/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package marioIA;


import org.encog.neural.networks.BasicNetwork;

import org.encog.util.arrayutil.NormalizedField;
import org.newdawn.slick.Animation;
import org.newdawn.slick.SpriteSheet;

/**
 *
 * @author Lope
 */
public class Bala {
    
    public Animation BalaI;
    
    
    int x,y;
    float xf;
    int state;
    
   
    
    public Bala(SpriteSheet spriteBalaI){
        
        
        if(spriteBalaI != null){
      
        BalaI = new Animation(spriteBalaI,1000);

        }
        
     
        
        state = -1;
        
        x = (int)(900);
        xf = x;
        y = 124;
             
       
    }
    
    public void  draw(){
     
       BalaI.draw(x,y);
         
        
    };
    
    
    public void update(){
        
        
        xf = (float) (xf + 2*state);
        x = (int) xf;
        
        if(x < -64){
            state = -1;
            xf = 900;
        }
        
        
    }
    
    
   
}
