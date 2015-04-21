/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package marioIA;

import org.encog.engine.network.activation.ActivationTANH;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.pattern.FeedForwardPattern;
import org.encog.util.arrayutil.NormalizationAction;
import org.encog.util.arrayutil.NormalizedField;
import org.newdawn.slick.Animation;
import org.newdawn.slick.SpriteSheet;

/**
 *
 * @author Lope
 */
public class Mario {
    
    public Animation marioAndarD;
    public Animation marioAndarI;
    public Animation marioSaltar1I;
    public Animation marioSaltar2I;
    public Animation marioSaltar1D;
    public Animation marioSaltar2D;
    public Animation marioAgacharI;
    public Animation marioAgacharD;
    
    public BasicNetwork red;
    public BasicNetwork redSaltar;
    
    int x,y;float xf;
    int state;
    int agachar;
    int jumpstate;
    int onAir;
    float jump;
    int tipo;
    
    private NormalizedField xNormalicer;
    private NormalizedField yNormalicer;
    
    public Mario(int _tipo, SpriteSheet spriteMarioAndarI, SpriteSheet spriteMarioAndarD, SpriteSheet spriteMarioSaltar1I,
             SpriteSheet spriteMarioSaltar2I,SpriteSheet spriteMarioSaltar1D, SpriteSheet spriteMarioSaltar2D,SpriteSheet spriteMarioAgacharI){
        
        tipo = _tipo;
        
        if(spriteMarioAndarI != null){
        marioAndarD = new Animation(spriteMarioAndarD,80);
        marioAndarI = new Animation(spriteMarioAndarI,80);
        
        
        marioSaltar1D = new Animation(spriteMarioSaltar1D,1000);
        marioSaltar2D = new Animation(spriteMarioSaltar2D,1000);
        marioSaltar1I = new Animation(spriteMarioSaltar1I,1000);
        marioSaltar2I = new Animation(spriteMarioSaltar2I,1000);
        marioAgacharI = new Animation(spriteMarioAgacharI,1000);
        marioAgacharD = new Animation(spriteMarioAgacharI,1000);
        
        }
        
        
        
        
        state = -1;
        agachar = 0;
        jumpstate = -1;
        onAir = -1;
        jump = -7;
        
        x = (int)(40+Math.random()*400);
        xf = x;
        y = 176;
        
       
      
       
    }
    
    public void  draw(){
        if(onAir == -1){
        
        if(agachar == 1) marioAgacharI.draw(x,y+11);
        else if(state == 1) marioAndarD.draw(x,y);
        else if(state == -1) marioAndarI.draw(x,y);
        
        
        }else{
           
        if(agachar == 1) marioAgacharI.draw(x,y+11);    
        else if(state == 1 && jump < 0) marioSaltar1D.draw(x,y);
        else if(state == -1 && jump < 0) marioSaltar1I.draw(x,y);
        else if(state == 1 && jump > 0) marioSaltar2D.draw(x,y);
        else if(state == -1 && jump > 0) marioSaltar2I.draw(x,y);
        
        
        
        }
        
    };
    
    
    public void update(int xSeta, int xBala){
       
        if(agachar != 1)
            xf = (float) (xf + 1.3*state);
        x = (int) xf;

        if(jumpstate == 1){
            jumpstate = -1;
            onAir = 1;
        }
        
        if(onAir == 1){ jump+=0.17;
        y = (int) (y + jump);
        }
        
        if(y >= 176){
            y = 176;
            jump = -4;
            onAir = -1;
        }
        
        
        if(tipo == 0){
        
            if(x > 480) state = -1;
            if(x < 0)  state = 1;
        
        } else if(tipo == 1){
            
            if(x<xSeta) state = 1;
            else state = -1;
            
            
            
            if(Math.abs(x-xSeta) < 55 )
            jumpstate = 1;
            else
            jumpstate = -1;
            
            if(Math.abs(x-xBala) < 80){
            jumpstate = -1;
            agachar = 1;
            }else{
            agachar = 0;
            }
            
            if(x > 480) state = -1;
            if(x < 0)  state = 1;
               
        
        } 
        
        
    }
    
    public void reset(){
    
        state = -1;
        onAir = -1;
        jump = -4;
        
        x = 200;
        y = 176;
    } 
}
