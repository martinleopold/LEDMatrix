import hypermedia.net.*;

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
UDP udp; // define the UDP object
int[] buffer; // the current frame buffer. series of int colors, row by row.
int[][] frame; // the current frame [width][height]

void setup() {
  size(pxSize*widthPx, pxSize*widthPx);
  udp = new UDP(this, port);
  udp.listen(true);
  buffer = new int[widthPx*heightPx];
  frame = new int[widthPx][heightPx];
  noStroke();
  frameRate(fps);
}

void draw() {
  background(128);
  if (portrait != 0) {
    // rotate the matrix around 90° to the right
    rotate(HALF_PI);
    translate(0, -height);
  }
  
  scale(pxSize);
  translate(0, 1);
  // draw the frame
  for (int y=0; y<heightPx; y++) {
    for (int x=0; x<widthPx; x++) {
      fill(frame[x][y]);
      rect(x, y, 1, 1);
    }
  }
}

// callback for packet received
void receive( byte[] data, String ip, int port ) {
  // check data length
  if (data.length != widthPx*heightPx*3) {
    println( "data length mismatch: " + (data.length - widthPx*heightPx*3) );
    return;
  }
  // update the frame.
  int idx = 0;
  for (int i=0; i<data.length; i+=3) {
    buffer[idx++] = color(int(data[i]), int(data[i+1]), int(data[i+2]));
  }
  // transform buffer to frame
  idx = 0;
  for (int y=0; y<heightPx; y++) {
    for (int x=0; x<widthPx; x++) {
      if (y % 2 == 0) { // even lines. reverse x
        frame[widthPx-1-x][heightPx-1-y] = buffer[idx++];
      } else { // odd lines. 
        frame[x][heightPx-1-y] = buffer[idx++];
      }
    }
  }
}

void keyPressed() {
  switch (key) {
    case ' ':
      portrait ^= 1; // fancy xor way to toggle between 0 and 1
      println("portrait:" + portrait);
      break;
  }
}
