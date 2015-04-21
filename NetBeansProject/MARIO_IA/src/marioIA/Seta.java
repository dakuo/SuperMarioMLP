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
public class Seta {
    
    public Animation setaAndarD;
    public Animation setaAndarI;
    public Animation setaMorir;
    
    public BasicNetwork red;
    public BasicNetwork redSaltar;
    
    int x,y;
    float xf;
    int state;
    int morir;
    
    private NormalizedField xNormalicer;
    private NormalizedField yNormalicer;
    
    public Seta(SpriteSheet spriteSetaAndarI, SpriteSheet spriteSetaAndarD, SpriteSheet spriteSetaMorir){
        
        
        if(spriteSetaAndarI != null){
        setaAndarD = new Animation(spriteSetaAndarD,160);
        setaAndarI = new Animation(spriteSetaAndarI,160);
        
        
        setaMorir = new Animation(spriteSetaMorir,1000);

        
        
        }
        
     
        
        state = (Math.random()>0.5)?-1:1;
        
        x = (int)(40+Math.random()*400);
        xf = x;
        y = 187;
             
       
    }
    
    public void  draw(){
     
        if(state == 1) setaAndarD.draw(x,y);
        if(state == -1) setaAndarI.draw(x,y);
        if(state == 0) setaMorir.draw(x,y);  
        
    };
    
    
    public void update(){
        
        
        xf = (float) (xf + 0.3*state);
        x = (int) xf;
        
        if(x > 480) state = -1;
        if(x < 0)  state = 1;
        if(morir > 0) morir--;
    }
    
    public void kill(){
    
        state = 0;
        morir = 60;
    } 
}
