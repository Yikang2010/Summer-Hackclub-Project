package _2D_Shooter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Bullet {
    public double x, y, angle;
    private double speed = 8;
    public int lifetime = 100; // Removes bullet after a while
    private BufferedImage sprite;

    public Bullet(double x, double y, double angle) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        try {
            sprite = ImageIO.read(new File("bullet.png"));
        } catch (Exception e) {}
    }

    public void tick() {
        x += Math.cos(angle) * speed;
        y += Math.sin(angle) * speed;
        lifetime--;
    }

    public void render(Graphics g) {
        if (sprite != null) {
            g.drawImage(sprite, (int)x, (int)y, 16, 16, null);
        } else {
            g.setColor(Color.YELLOW);
            g.fillOval((int)x, (int)y, 8, 8);
        }
    }
    
    
//     public int (String title) {
//    	 String theB = holds.get(i);
//    	 if(th)
//    	 
//     }
//    
//     public void remove(String t) {
//    	 for(int i = 0; i < h.size(); i++) {
//    		 if(holds.get(i).equals(t)) {
//    			 holds.remove(i);
//    			 i--;
//    		 }
//    	 }
//      
//     }
//    
    
    
    

//   a) 
//
//   private ArrayList<String> wordList; 
//    wordlist = new ArrayList<String>;
//
//    public boolean isWordChain() {
//    	for(int i = 1; i < wordList.size(); i++) {
//    		if(wordList.get(i).indexOf(i-1) == -1) {
//    			return false;
//    		} else {
//    			return true;
//    		}
//    	}
//    }
//    
//     
//    public ArrayList<String> createList(String target) {
//    	private ArrayList<String> newList = new ArrayList<String>; 
//    	for (int i = 0; i < wordList.size(); i++) {
//    		if(wordList.get(i).indexOf(target) != -1 || wordList.get(i).indexOf(target) <= target.length()) {
//    			newList.add(wordlist.get(i).substring(target.length());
//    		}
//    	}
//    	return newList;
//    	
//    	
//    	
//    	
//    }
//    
//    b)
//    			public ArrayList<String> createList(String target){
//
//    			  ArrayList<String> result = new ArrayList<String>();
//
//    			  for(String word : wordList){
//    			    if(word.indexOf(target) == 0 ){
//
//    			      result.add(word.substring(target.length()));
//    			    }
//    			  }
//    			  return result;
//
//    			}

    
//
//	public boolean isWordChain () {
//		for(int i = 0; i < wordList.size(); i++) {
//			int index = wordList.add(0);
//			int firstSize = wordList.size
//			for(int i = wordList.get(i)) {
//				
//			}
//			i dont get how jsut doing f(wordList.get(i).indexOf(wordList.get(i-1)) == -1 ){ makes the thing work like if your trying to find if the next word in an array dont you do something else like i dont even know like scanner for 
    	//ex wordlist.get(i) ok so the first letter WAIT I JUST UNDERSTOOD IT NOW, SO IS IT THAT IT SEES IF wait so wtf so it checks if for ex if the first word is ban next word is
    	//band so it checks if wordlist.get(1) (wich is a).index of (wrod
//			a)
//			public boolean isWordChain(){
//
//			  for(int i = 1; i < wordList.size(); i++){
//			    if(wordList.get(i).indexOf(wordList.get(i-1)) == -1 ){
//			      return false;
//			    }
//			  }
//			  return true;
//
//			}
//		}
//	}
//    
    
    
    
    public Rectangle getBounds() { return new Rectangle((int)x, (int)y, 8, 8); }
}