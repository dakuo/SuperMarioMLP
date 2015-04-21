/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package marioIA;

import java.awt.Point;
import java.util.Scanner;
import org.encog.engine.network.activation.ActivationTANH;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.basic.BasicMLData;
import org.encog.ml.data.basic.BasicMLDataPair;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.neural.networks.training.propagation.resilient.ResilientPropagation;
import org.encog.util.arrayutil.NormalizationAction;
import org.encog.util.arrayutil.NormalizedField;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 *
 * @author Lope
 */
public class MarioIA extends BasicGame{

    /**
     * @param args the command line arguments
     */
    
     
      Luigi luigi;
      Mario mario;
      Seta seta;
      Seta setaMuerta;
      Bala bala;
      BasicNetwork redEntrenada = null;
      boolean flagEntrenamiento = false;
      int flagSpeed = 0;
      
      long puntuacion = 0;
      long iteraciones = 0;
      boolean flag = true;
      MLDataSet trainingSet = new BasicMLDataSet();
      Image fondo;
    
      GraficoRNA graficoRNA;
      
    public MarioIA(){
        super("MarioIA");
    }
    
    public static BasicNetwork createNetwork(){
            
            BasicNetwork network = new BasicNetwork();
            network.addLayer(new BasicLayer(null,true,3));
		network.addLayer(new BasicLayer(new ActivationTANH(),true,15));
		network.addLayer(new BasicLayer(new ActivationTANH(),false,3));
                network.getStructure().finalizeStructure();
		network.reset();
 
           
      
            return network;
           }
    
   
    
    public static void main(String[] args) {
        try
        {
            
            AppGameContainer app = new AppGameContainer(new MarioIA());
            app.setDisplayMode(800, 400, false);
            app.setMaximumLogicUpdateInterval(60);			
            app.setTargetFrameRate(60);
            app.setAlwaysRender(true);
            app.setVSync(true);
            
            app.start();
                      
            
        }
        catch (SlickException e)
        {
            e.printStackTrace();
        }
    }
    
  public void acabarEntrenamiento(){
      flagEntrenamiento = false;
      luigi.tipo = 2;
      luigi.jump = -2;
      luigi.jumpstate = 1;
      luigi.red = redEntrenada;
      mario.tipo = 3;
  
  }
    
  public  BasicNetwork entrenar(BasicNetwork red){
    
     
    
      
     long A = System.currentTimeMillis();
     ResilientPropagation train = new ResilientPropagation(red, trainingSet);

		int epoch = 1;
               
		do {
			train.iteration();
			if(epoch%20 == 0)System.out.println("Epoch #" + epoch + " Error:" + train.getError());
			epoch++;
                        if((System.currentTimeMillis() - A) > 200)  
                            break;
                        
		} while(train.getError() > 0.01); 
                
                
                if(train.getError() < 0.01 ){this.acabarEntrenamiento();}
		train.finishTraining();   
                
        
        return red;
};
  
    
    @Override
    public void init(GameContainer gc) throws SlickException {
      SpriteSheet spriteMarioAndarD =  new SpriteSheet("data/marioAndarD.bmp",20,28);
      SpriteSheet spriteMarioAndarI =  new SpriteSheet("data/marioAndarI.bmp",20,28);
      SpriteSheet spriteLuigiAndarD =  new SpriteSheet("data/LuigiAndarD.bmp",21,32);
      SpriteSheet spriteLuigiAndarI =  new SpriteSheet("data/LuigiAndarI.bmp",21,32);
      SpriteSheet spriteSetaAndarI =  new SpriteSheet("data/setaAndarI.bmp",20,17);
      SpriteSheet spriteSetaAndarD =  new SpriteSheet("data/setaAndarD.bmp",20,17);
      
      SpriteSheet spriteMarioSaltar1D =  new SpriteSheet("data/marioSaltar1D.png",22,31);
      SpriteSheet spriteMarioSaltar2D =  new SpriteSheet("data/marioSaltar2D.png",22,31);
      SpriteSheet spriteMarioSaltar1I =  new SpriteSheet("data/marioSaltar1I.png",22,31);
      SpriteSheet spriteMarioSaltar2I =  new SpriteSheet("data/marioSaltar2I.png",22,31);
      
      SpriteSheet spriteLuigiSaltar1D =  new SpriteSheet("data/LuigiSaltar1D.png",22,31);
      SpriteSheet spriteLuigiSaltar2D =  new SpriteSheet("data/LuigiSaltar2D.png",22,31);
      SpriteSheet spriteLuigiSaltar1I =  new SpriteSheet("data/LuigiSaltar1I.png",22,31);
      SpriteSheet spriteLuigiSaltar2I =  new SpriteSheet("data/LuigiSaltar2I.png",22,31);
      
      SpriteSheet spriteMarioAgachar =  new SpriteSheet("data/marioAgacharI.png",20,17);
      SpriteSheet spriteLuigiAgachar =  new SpriteSheet("data/luigiAgacharD.png",19,19);
      
      SpriteSheet spriteSetaMorir =  new SpriteSheet("data/setaMorir.png",20,17);
      SpriteSheet spriteLuigiAprender =  new SpriteSheet("data/LuigiAprender.png",20,30);
      SpriteSheet spriteBalaI =  new SpriteSheet("data/bala.png",64,64);
      
     seta = new Seta(spriteSetaAndarI,spriteSetaAndarD,spriteSetaMorir); 
     fondo = new Image("data/marioBack.png");
     
     
     luigi = new Luigi(0, spriteLuigiAndarI, spriteLuigiAndarD, spriteLuigiSaltar1I,
             spriteLuigiSaltar2I, spriteLuigiSaltar1D, spriteLuigiSaltar2D, spriteLuigiAprender,spriteLuigiAgachar);
     luigi.red = createNetwork();
    
     mario =  new Mario(1,spriteMarioAndarI, spriteMarioAndarD, spriteMarioSaltar1I,
             spriteMarioSaltar2I, spriteMarioSaltar1D, spriteMarioSaltar2D,spriteMarioAgachar);
     
     graficoRNA = new GraficoRNA(luigi.red, new Point(500, 0), new Point(800, 249));
     bala = new Bala(spriteBalaI);
     
    }
    /* TODO: Calcular el salto perfecto, entada adicional a la red, direccion de la seta.
     *       Luigii aprendiendo
     *      Bala periodica
     */
    
    
    @Override
    public void update(GameContainer gc, int i) throws SlickException {
        
    
     if(flagEntrenamiento == true) return;
     iteraciones++; 

     seta.update();
     if(seta.state == 0){
         setaMuerta = seta;
         SpriteSheet spriteSetaAndarI =  new SpriteSheet("data/setaAndarI.bmp",20,17);
         SpriteSheet spriteSetaAndarD =  new SpriteSheet("data/setaAndarD.bmp",20,17);      
         SpriteSheet spriteSetaMorir =  new SpriteSheet("data/setaMorir.png",20,17);
         seta = new Seta(spriteSetaAndarI,spriteSetaAndarD,spriteSetaMorir);
     } 
     if(setaMuerta != null && setaMuerta.morir==0) setaMuerta = null;
     
     if(setaMuerta != null) setaMuerta.update();
      
     luigi.update(seta.x, bala.x);
     mario.update(seta.x, bala.x);
     bala.update();
     
        
     if( (Math.pow(((luigi.x+16)-(seta.x+12)),2)+Math.pow(((luigi.y+16)-(seta.y+12)),2)) < 300 && luigi.jump>0){
                seta.kill();

     }
     
     if( (Math.pow(((mario.x+16)-(seta.x+12)),2)+Math.pow(((mario.y+16)-(seta.y+12)),2)) < 300 && mario.jump>0){
                seta.kill();

     }
            
            
       
        
        
       
            double[] in = {0.0,0.0,0.0};
            double[] out = {0.0,0.0,0.0};
     
            
            if(mario.tipo != 3){
            NormalizedField xNormalicer = new NormalizedField(NormalizationAction.Normalize, "X", 560, 0, -0.9, 0.9);
            NormalizedField xNormalicerBala = new NormalizedField(NormalizationAction.Normalize, "X",  560, 0, -0.9, 0.9);
            
            in[0] = xNormalicer.normalize(mario.x);
            in[1] = xNormalicer.normalize(seta.x);
            in[2] = xNormalicerBala.normalize(bala.x);
            
            out[0] = mario.state;
            out[1] = mario.tipo == 0? mario.onAir: mario.jumpstate;
            out[2] = mario.agachar == 1? 1:-1;
            
            trainingSet.add(new BasicMLDataPair(new BasicMLData(in), new BasicMLData(out)));
            };
            
            if(flagSpeed != 0){
                if(flagSpeed == 1){
                   gc.setMaximumLogicUpdateInterval(30);			
                    gc.setTargetFrameRate(60);
                    gc.setAlwaysRender(true);
                    gc.setVSync(true);
                    flagSpeed = 0;
                    
                }else{
                  
                    gc.setMaximumLogicUpdateInterval(0);			
                    gc.setTargetFrameRate(2000);
                    gc.setAlwaysRender(false);
                    gc.setVSync(false);
                    flagSpeed = 0;
                
                }
                
                
                
            
            }
        
            
    }

    @Override
    public void render(GameContainer gc, Graphics grphcs) throws SlickException {
     
        
     
       
            grphcs.setLineWidth(2);
            fondo.draw(0,0);
            bala.draw();
            grphcs.setAntiAlias(true);
            
            
            seta.draw();
            if(setaMuerta!= null) setaMuerta.draw();
            luigi.draw();
            mario.draw();
            
            // if(mario.jumpstate == 1) grphcs.drawLine(mario.x+10, mario.y+27, mario.x+10, 200);
          
            grphcs.setColor(Color.black);
            grphcs.drawString((mario.tipo==0?"":"Auto"), mario.x+21, mario.y-9);       
            grphcs.setColor(Color.red);
            grphcs.drawString((mario.tipo==0?"":"Auto"), mario.x+20, mario.y-10);
            
            
            
            graficoRNA.draw(grphcs);
            
      
       
       grphcs.setColor(Color.red);
       if(mario.tipo != 3) grphcs.drawString("Muestras: "+iteraciones, 110, 8);
       
       
        
        if(flagEntrenamiento == true){
        redEntrenada = this.entrenar(redEntrenada);
        graficoRNA.red = redEntrenada;
        
        grphcs.drawString("Entrenando, error: "+ Math.round(1000*redEntrenada.calculateError(trainingSet))/1000.0d, 515, 220);
        
        }
       
    }
    
    @Override
    public void keyPressed(int key, char c){
        if(c == 'd') mario.state = 1;
        if(c == 'a') mario.state = -1;
        if(c == 'w') mario.jumpstate = 1;
 
        mario.agachar = 0;
        if(c == 's') mario.agachar = mario.agachar = 1;

        if(c == 'l') {
                seta.kill();
                
        }
        
        if(key == 29) {
                mario.tipo = mario.tipo == 0? 1:0;
        }
        
        
        if(c == 'e') {
            
                if(flagEntrenamiento == false){
            
                redEntrenada = createNetwork();
                this.entrenar(redEntrenada);
                
                    flagEntrenamiento = true;
                }else{
                    acabarEntrenamiento();
                }
        
        
                
        }
        
        if(c == '1')            
                flagSpeed = 1;

        if(c == '2')
                flagSpeed = 2;

       
        
        
    }
    
}

