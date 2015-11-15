# fxsprite

fxsprite is a javafx utility designed to allow users to easily load and animate sprite sheets.

Sprite inherits from ImageView, which allows it to be easily manipulated like any other javafx node.

## Usage

Getting started is as easy as:

```java
// loads a sprite sheet, and specifies the size of one frame/cell
Sprite s = new Sprite("megaman.png", 50, 49); //searches for the image file in the classpath
s.setFPS(5); // animation will play at 5 frames per second
s.play(); // animates the first row of the sprite sheet
s.pause()

s.label(4, "powerup"); // associates the fourth (zero-indexed) row of the sheet with "powerup"
s.playTimes("powerup", 10); // plays "powerup" animation 10 times;
```

Check the included Main.java file for some examples.

When dealing with sprites, the user must either spend time preparing sprite sheets in a specific way, or alternatively, spend time measuring sprite sheets and inputting these measurements into code or configuration files. In this implementation, I chose to require a well formatted (think, a grid) sprite sheet to be prepared in advance. This saves time coding, but necessarily requires more time preparing the sprite sheet in a graphics program.

![Sprite Layout](https://raw.githubusercontent.com/collinbachi/fxsprite/master/spritelayout.png)

Additionally, there is no support for different sized sprites within the same sprite sheet. This is almost always a use-case specific problem, and having different sized sprites in a single animation introduces many complexities (ex. collision detection.)


