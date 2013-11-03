import hypermedia.net.*;

/* Parameters */
int port = 8888; // listening port
int fps = 30; // frames per second for display update. framebuffer update happens asynchronously whenever data is received.

int widthPx = 18; // pixels in a row
int heightPx = 16; // pixels in a column
int pxSize = 25; // size of a pixel (in this app)


/* Variables */
UDP udp; // define the UDP object
int[] frame; // the current frame. series of int colors, row by row.

void setup() {
  size(pxSize*widthPx, pxSize*heightPx);
  udp = new UDP(this, port);
  udp.listen(true);
  frame = new int[widthPx*heightPx];
  noStroke();
  frameRate(fps);
}

void draw() {
  scale(pxSize);
  // draw the frame
  int idx=0;
  for (int y=0; y<heightPx; y++) {
    for (int x=0; x<widthPx; x++) {
      fill(frame[idx++]);
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
    frame[idx++] = color(int(data[i]), int(data[i+1]), int(data[i+2]));
  }
}
