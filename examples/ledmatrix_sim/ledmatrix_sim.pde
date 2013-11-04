import com.martinleopold.ledmatrix.*;

/*
simulate an 18 by 16 px LED matrix
composed of a led strip beginning in the lower right corner
layed out in a snake like pattern
press space to rotate the simulated device by 90° to the right
*

/* Parameters */
int port = 8888; // listening port
int fps = 30; // frames per second for display update. framebuffer update happens asynchronously whenever data is received.

int widthPx = 18; // pixels in a row
int heightPx = 16; // pixels in a column
int pxSize = 25; // size of a pixel (in this app)
int portrait = 0; // rotate the matrix around 90° to the right?

/* Variables */
Receiver r;

void setup() {
  size(pxSize*widthPx, pxSize*widthPx);
  frameRate(fps);
  r = new Receiver(port, LEDMatrix.PUSHPIXEL_LANDSCAPE);
}

void draw() {
  background(128);
  if (portrait != 0) {
    // rotate the matrix around 90° to the right
    rotate(HALF_PI);
    translate(0, -height);
  }
  
  translate(0, pxSize);
  // draw the frame
  image(r.frame().image(pxSize), 0, 0);
}

void keyPressed() {
  switch (key) {
    case ' ':
      portrait ^= 1; // fancy xor way to toggle between 0 and 1
      println("portrait:" + portrait);
      break;
  }
}
