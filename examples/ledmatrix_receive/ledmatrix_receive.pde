import com.martinleopold.ledmatrix.*;

/* Parameters */
int port = 8888; // listening port
int fps = 30; // frames per second for display update. framebuffer update happens asynchronously whenever data is received.

int widthPx = 18; // pixels in a row
int heightPx = 16; // pixels in a column
int pxSize = 25; // size of a pixel (in this app)


/* Variables */
Receiver r;

void setup() {
  size(pxSize*widthPx, pxSize*heightPx);
  frameRate(fps);
  
  r = new Receiver(port, LEDMatrix.PUSHPIXEL_LANDSCAPE);
  //r = new Receiver(port, LEDMatrix.PUSHPIXEL_PORTRAIT);
  
  r.setFrameHandler(new Receiver.FrameHandler() {
    public void receive( Frame frame, String ip, int port ) {
      System.out.println("received a Frame from " + ip + ":" + port);
    }
  });
}

void draw() {
  //image(r.frame().image(pxSize), 0, 0);
}
