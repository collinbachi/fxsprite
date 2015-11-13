package fxsprite;

import java.util.HashMap;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Sprite extends ImageView{
	
	private static HashMap<String, Image> images = new HashMap<String, Image>();
	private Rectangle2D rect;
	private HashMap<String, Integer> labels;
	private HashMap<Integer, Integer> rowLengths;
	private int fps = 4;
	private SpriteAnimation currentAnimation;
	
	
	public Sprite(Image sheet, Bounds frame){ // Remove?
		//TODO
	}
	
	public Sprite(Image sheet, int width, int height){ // Remove?
		//TODO
	}
	
	public Sprite(String sheet, int width, int height){
		super(images.containsKey(sheet) ? images.get(sheet) : new Image(Sprite.class.getClassLoader().getResourceAsStream(sheet)));
		if (!images.containsKey(sheet)) images.put(sheet, this.getImage());
		rect = new Rectangle2D(0, 0, width, height);
		this.setViewport(rect);
		labels = new HashMap<String, Integer>();
		rowLengths = new HashMap<Integer, Integer>();
		for(int i=0; i<(int)(this.getImage().getHeight()/this.rect.getHeight()); i++){
			this.limitRowColumns(i, (int)(this.getImage().getWidth()/this.rect.getWidth()));
		}
	}
	
	// Below are all the different API calls for playing, pausing, restarting, etc. animations
	public void play(int row){
		this.currentAnimation = new SpriteAnimation(new Duration(10000/this.fps), row, this.rowLengths.get(row));
		this.currentAnimation.play();
	}
	
	public void play(){ this.play(0); }
	
	public void play(String label){
		this.play(this.labels.get(label));
	}
	
	public void pause(){ this.currentAnimation.pause(); }
	public void replay(){ this.currentAnimation.playFromStart(); }
	
	public void limitRowColumns(int row, int cols){
		this.rowLengths.put(row, cols);
	}
	
	public void label(int row, String lab){
		this.labels.put(lab, row);
	}
	
	public class SpriteAnimation extends Transition {
		
		private int row;
		private int cols;
		
		public SpriteAnimation(Duration duration, int row, int cols){
			setCycleDuration(duration);
			setCycleCount(Animation.INDEFINITE);
	        setInterpolator(Interpolator.LINEAR);
	        this.row = row;
	        this.cols = cols;
		}

		@Override
		protected void interpolate(double dt) {
			double yoffset = Sprite.this.rect.getHeight()*this.row;
			double xoffset = Sprite.this.rect.getWidth()*(int)(Math.round((this.cols-1)*dt));
			//System.out.println(xoffset);
			Sprite.this.setViewport(new Rectangle2D(Sprite.this.rect.getMinX()+xoffset,
												Sprite.this.rect.getMinY()+yoffset,
												Sprite.this.rect.getWidth(),
												Sprite.this.rect.getHeight()));
		}
	}
}
