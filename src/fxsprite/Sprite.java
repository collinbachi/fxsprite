/**
 * CS308 - Team IllegalTeamNameException
 * A javafx utility for loading, slicing up, and animation sprite sheets.
 * Allows for fine grained control over running animations.
 * 
 * @author D. Collin Bachi
 */
package fxsprite;

import java.util.HashMap;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Sprite extends ImageView{
	
	private static HashMap<String, Image> images = new HashMap<String, Image>();
	private Rectangle2D rect;
	private HashMap<String, Integer> labels;
	private HashMap<Integer, Integer> rowLengths;
	private int fps = 8;
	private SpriteAnimation currentAnimation;
	
	/**
	 * Initializes a sprite from a javafx Image. This is not the recommended
	 * approach, since it does not allow for Image objects to be efficiently
	 * shared by multiple ImageViews.
	 * 
	 * @param  sheet the javafx Image object of the sprite sheet
	 * @param  width the width of a single sprite cell
	 * @param height the height of a single sprite cell
	 */
	public Sprite(Image sheet, int width, int height){ // Not recommended
		super(sheet);
		String filename = sheet.toString();
		if (!images.containsKey(filename)) images.put(filename, sheet);
		rect = new Rectangle2D(0, 0, width, height);
		this.setViewport(rect);
		labels = new HashMap<String, Integer>();
		rowLengths = new HashMap<Integer, Integer>();
		for(int i=0; i<(int)(this.getImage().getHeight()/this.rect.getHeight()); i++){
			this.limitRowColumns(i, (int)(this.getImage().getWidth()/this.rect.getWidth()));
		}
	}
	
	/**
	 * Initializes a sprite from a String reference to an image.
	 * The image will be looked for in the classpath
	 * (getClassLoader().getResourceAsStream...)
	 * The sheet will be sliced up in to width x height
	 * cells.
	 * 
	 * @param  sheet a string referencing an image file
	 * @param  width the width of a single sprite cell
	 * @param height the height of a single sprite cell
	 */
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
	
	/**
	 * Animates a series of cells from the specified row of the sprite sheeet.
	 * 
	 * @param  row the row to play
	 */
	public void play(int row){
		this.currentAnimation = new SpriteAnimation(new Duration(10000/this.fps), row, this.rowLengths.get(row));
		this.currentAnimation.play();
	}
	
	/**
	 * Animates a series of cells from the first row of the sprite sheet.
	 */
	public void play(){ this.play(0); }
	
	public void playTimes(int row, int count){
		this.currentAnimation = new SpriteAnimation(new Duration(10000/this.fps), row, this.rowLengths.get(row), count);
		this.currentAnimation.play();
	}
	
	/**
	 * Animates a series of cells from the specified row of the sprite sheet.
	 * The animation is played count times.
	 * 
	 * @param  label the row to play (specified by its label)
	 * @param  count the number of times to play the animation
	 */
	public void playTimes(String label, int count){
		this.playTimes(this.labels.get(label), count);
	}
	
	/**
	 * Animates a series of cells from the specified row of the sprite sheet.
	 * 
	 * @param  label the row to play (specified by its label)
	 */
	public void play(String label){
		this.play(this.labels.get(label));
	}
		
	/**
	 * Pauses the current animation.
	 */
	public void pause(){ this.currentAnimation.pause(); }
	
	/**
	 * Replays the current animation from the start.
	 */
	public void replay(){ this.currentAnimation.playFromStart(); }
	
	// ---
	
	/**
	 * Set the frames-per-second for future animations. This will not take effect
	 * until a new call is made to play or playTimes
	 * 
	 * @param fps the new FPS
	 */
	public void setFPS(int fps){ this.fps = fps; }
	
	/**
	 * Specify that a row of the sprite sheet has less columns than
	 * the default (sheet width / cell width)
	 * 
	 * @param row the row
	 * @param cols the number of cells in the row
	 */
	public void limitRowColumns(int row, int cols){
		this.rowLengths.put(row, cols);
	}
	
	/**
	 * Associates a text label to a row of the sprite sheet.
	 * 
	 * @param row the row
	 * @param lab the label to associate with the row
	 */
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
		
		public SpriteAnimation(Duration duration, int row, int cols, int count){
			setCycleDuration(duration);
			setCycleCount(count);
	        setInterpolator(Interpolator.LINEAR);
	        this.row = row;
	        this.cols = cols;
		}

		@Override
		protected void interpolate(double dt) {
			double yoffset = Sprite.this.rect.getHeight()*this.row;
			double xoffset = Sprite.this.rect.getWidth()*(int)(Math.round((this.cols-1)*dt));
			Sprite.this.setViewport(new Rectangle2D(Sprite.this.rect.getMinX()+xoffset,
												Sprite.this.rect.getMinY()+yoffset,
												Sprite.this.rect.getWidth(),
												Sprite.this.rect.getHeight()));
		}
	}
}
