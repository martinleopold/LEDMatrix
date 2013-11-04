import com.martinleopold.ledmatrix.*;

/* Parameters */
String host = "localhost"; // destination host name or ip
int port = 8888; // destination port
int fps = 15; // frames per second to send.
int widthPx = 18;
int heightPx = 16;
int pxSize = 10;

LEDMatrix lm;

void setup() {
  size(widthPx*pxSize, heightPx*pxSize);
  
  lm = new LEDMatrix(host, port, LEDMatrix.PUSHPIXEL_LANDSCAPE);
  //lm = new LEDMatrix(host, port, LEDMatrix.PUSHPIXEL_PORTRAIT);
  //lm = new LEDMatrix(host, port, widthPx, heightPx);
  
  lm.testPattern();
  frameRate(fps);
}

void draw() {
  image( lm.frame().image(pxSize), 0, 0 ); // draw the frame
  lm.update();
}

void keyPressed() {
    switch (key) {
      case BACKSPACE:
        lm.clearTransformations();
        break;
      case 'x':
        lm.toggleFlipX();
        break;
      case 'y':
        lm.toggleFlipY();
        break;
      case 'r':
        lm.toggleRotate180();
        break;
      case 't':
        lm.toggleRotate90();
        break;
      case 's':
        lm.toggleSnakeX();
        break;
      case 'a':
        lm.toggleSnakeY();
        break;
    }
}
