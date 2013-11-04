package com.martinleopold.ledmatrix;

import hypermedia.net.UDP;
import java.util.ArrayList;

/**
 *
 * @author Martin Leopold <m@martinleopold.com>
 */
public class Receiver {
	UDP udp;
	FrameHandler frameHandler;
	
	String multicastGroup;
	int port;
	
	int width, height; // dimensions of the frame
	
	ArrayList<Transformation> presetTransformations, transformations;
	
	Frame frame;
	
	public Receiver(int port, int width, int height) {
		setup(width, height, "", port);
	}
	
	public Receiver(String multicastGroup, int port, int width, int height) {
		setup(width, height, multicastGroup, port);
	}
	
	public Receiver(int port, int preset) {
		setupPreset(preset, "", port);
	}
	
	public Receiver(String multicastGroup, int port, int preset) {
		setupPreset(preset, multicastGroup, port);
	}
	
	void setup(int width, int height, String host, int port) {
		this.width = width;
		this.height = height;
		transformations = new ArrayList<Transformation>();
		presetTransformations = new ArrayList<Transformation>();
        this.multicastGroup = host;
        this.port = port;
		frame = new Frame(width, height);
        // initialize udp
		initSocket();
	}
	
	void setupPreset(int preset, String host, int port) {
		switch (preset) {
			case LEDMatrix.PUSHPIXEL_LANDSCAPE:
			default:
				setup(18, 16, host, port);
				presetTransformations.add(Transformations.SNAKEX);
				presetTransformations.add(Transformations.ROTATE180);
				break;
			case LEDMatrix.PUSHPIXEL_PORTRAIT:
				setup(16, 18, host, port);
				presetTransformations.add(Transformations.SNAKEX);
				presetTransformations.add(Transformations.ROTATE90);
				break;
		}
	}
	
	void initSocket() {
		if (multicastGroup != "") udp = new UDP(this, port, multicastGroup);
		else udp = new UDP(this, port);
		udp.setReceiveHandler("receivePacket");
		udp.listen(true);
	}
	
	/**
	 * Reinitialize the socket.
	 */
	public void reset() {
		if (udp != null) {
			udp.dispose();
		}
		initSocket();
	}
	
	public void receivePacket( byte[] data, String ip, int port ) {
		//System.out.println("received " + data.length + " bytes from " + ip + ":" + port);
		
		// check data length
		if (data.length != width*height*3) {
			System.err.println( "data length mismatch: " + (data.length - width*height*3) );
			return;
		}
		// construct the Frame object out of the incoming data
		frame = new Frame(width, height);
		frame.set(data);
		
		// apply transformations
		for (Transformation t : presetTransformations) {
            frame = t.transform(frame);
        }
        for (Transformation t : transformations) {
            frame = t.transform(frame);
        }
		// call handler
		receive(frame, ip, port);
	}
	
	/**
	 * It's possible to overwrite this method to handle incoming data. Or just use a FrameHandler.
	 * @param frame
	 * @param ip
	 * @param port 
	 */
	protected void receive( Frame frame, String ip, int port ) {
		if ( frameHandler != null ) {
			frameHandler.receive(frame, ip, port);
		}
	}
	
	public void setFrameHandler(FrameHandler frameHandler) {
		this.frameHandler = frameHandler;
	}
	
	static public interface FrameHandler {
		void receive( Frame frame, String ip, int port );
	}
	
	public Frame frame() {
		return frame;
	}
}
