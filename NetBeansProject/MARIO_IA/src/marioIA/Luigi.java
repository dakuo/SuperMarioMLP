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
public class Luigi {
    
    public Animation luigiAndarD;
    public Animation luigiAndarI;
    public Animation luigiSaltar1I;
    public Animation marioSaltar2I;
    public Animation marioSaltar1D;
    public Animation marioSaltar2D;
    public Animation marioAprender;
    public Animation luigiAgachar;
    
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
    private NormalizedField xNormalicerBala;
    
    public Luigi(int _tipo, SpriteSheet spriteMarioAndarI, SpriteSheet spriteMarioAndarD, SpriteSheet spriteMarioSaltar1I,
             SpriteSheet spriteMarioSaltar2I,SpriteSheet spriteMarioSaltar1D, SpriteSheet spriteMarioSaltar2D, SpriteSheet spriteMarioAprender,
             SpriteSheet spriteLuigiAgachar){
        
        tipo = _tipo;
        
        if(spriteMarioAndarI != null){
        luigiAndarD = new Animation(spriteMarioAndarD,80);
        luigiAndarI = new Animation(spriteMarioAndarI,80);
        
        
        marioSaltar1D = new Animation(spriteMarioSaltar1D,1000);
        marioSaltar2D = new Animation(spriteMarioSaltar2D,1000);
        luigiSaltar1I = new Animation(spriteMarioSaltar1I,1000);
        marioSaltar2I = new Animation(spriteMarioSaltar2I,1000);
        
        marioAprender = new Animation(spriteMarioAprender,300);
        luigiAgachar = new Animation(spriteLuigiAgachar,1000);
        
        }
    
        
        state = 0;
        jumpstate = -1;
        onAir = -1;
        jump = -7;
        agachar = 0;
        
        x = 290;
        xf = x;
        y = 86;
        
        xNormalicer = new NormalizedField(NormalizationAction.Normalize, "X",  560, 0, -0.9, 0.9);
        xNormalicerBala = new NormalizedField(NormalizationAction.Normalize, "X",  560, 0, -0.9, 0.9);
       
    }
    
    public void  draw(){
        
        if(state == 0){
            marioAprender.draw(x,y);
        }else if(onAir == -1){
            if(agachar == 1)luigiAgachar.draw(x,y+12);
            else if(state == 1) luigiAndarD.draw(x,y);
            else if(state == -1) luigiAndarI.draw(x,y);
            
        }else{
            
            if(agachar == 1)luigiAgachar.draw(x,y+12);
            else if(state == 1 && jump < 0) marioSaltar1D.draw(x,y);
            else if(state == -1 && jump < 0) luigiSaltar1I.draw(x,y);
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
        
        if(y >= 173){
            y = 173;
            jump = -4;
            onAir = -1;
        }
        
        
        
        if(tipo == 0){
                 
        
        } else if(tipo == 2){
            
          
            double[] input = {this.xNormalicer.normalize(x),this.xNormalicer.normalize(xSeta),this.xNormalicerBala.normalize(xBala)};
            double[] output = new double[3];
            
            red.compute(input, output);
            if(output[0] > 0) state = 1;
            else state = -1;
            
            if(output[2] > 0) agachar = 1;
            else agachar = 0;
            
            
            if(output[1] > 0)  onAir = 1;
          
               
        }
        
        

        
        if(x > 480) state = -1;
        if(x < 0)  state = 1;
    }
    
    public void reset(){
    
        state = -1;
        onAir = -1;
        jump = -4;
        
        x = 200;
        y = 176;
    } 
}
