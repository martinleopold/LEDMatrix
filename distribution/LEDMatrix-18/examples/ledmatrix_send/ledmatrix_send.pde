import com.martinleopold.ledmatrix.*;

/* Parameters */
String host = "localhost"; // destination host name or ip
int port = 8888; // destination port
int fps = 15; // frames per second to send.
int widthPx = 18;
int heightPx = 16;
LEDMatrix lm;

void setup() {
  lm = new LEDMatrix(this, widthPx, heightPx, host, port);
  //lm = new LEDMatrix(this, host, port, LEDMatrix.LANDSCAPE);
  //lm = new LEDMatrix(this, host, port, LEDMatrix.PORTRAIT);
  
  lm.testPattern();
  frameRate(fps);
}

void draw() {
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
