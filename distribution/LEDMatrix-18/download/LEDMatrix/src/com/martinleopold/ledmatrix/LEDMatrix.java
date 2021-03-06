package com.martinleopold.ledmatrix;

/**
 * LEDMatrix
 * Control a LED Matrix driven by PixelPi or similar via UDP
 * http://martinleopold.com
 *
 * Copyright (C) 2013 Martin Leopold http://martinloepold.com
 *
 * This library is licensed under a Creative Commons Attribution-NonCommercial 3.0 Unported License.
 * http://creativecommons.org/licenses/by-nc/3.0/deed.en_US
 * 
 * @author      Martin Leopold http://martinloepold.com
 * @modified    06/25/2013
 * @version     0.2 (18)
 */
import processing.core.*;
import hypermedia.net.UDP;
import java.util.ArrayList;

/**
 * Control a LED Matrix driven by PixelPi or similar via UDP.
 * @example ledmatrix_send
 * @example ledmatrix_receive
 * @example ledmatrix_sim
 *
 */
public class LEDMatrix {
    public final static String VERSION = "0.2 (18)";
	PApplet p;
    UDP udp;
    Frame frame;
    String host; // destination host name or ip
    int port; // destination port
	ArrayList<Transformation> presetTransformations;
    ArrayList<Transformation> transformations;
    boolean isTestPattern = false;
    TestPattern testPattern;
    
	/**
	 * Preset for a landscape 18 x 16 LED Matrix with a LED strip layed out in a snake-like pattern.
	 * Applies {@link #rotate180()} and {@link #snakeX()} which cannot be removed by {@link #clearTransformations()}.
	 * Use with {@link #LEDMatrix(processing.core.PApplet, java.lang.String, int, int)}.
	 */
	public static final int LANDSCAPE = 1;
	
	/**
	 * Preset for a portrait 16 x 18 LED Matrix with a LED strip layed out in a snake-like pattern.
	 * Applies {@link #rotate90()} and {@link #snakeX()} which cannot be removed by {@link #clearTransformations().
	 * Use with {@link #LEDMatrix(processing.core.PApplet, java.lang.String, int, int)}.
	 */
	public static final int PORTRAIT = 2;

	
    private LEDMatrix(PApplet p) {
		welcome();
        this.p = p;
    }
	
	/**
	 * A LED Matrix configured from a preset.
	 * @param p the parent PApplet
     * @param host network IP or host name to send data
     * @param port destination port (UDP)
	 * @param preset Choose from either {@link #LANDSCAPE} or {@link #PORTRAIT}
	 */
	public LEDMatrix(PApplet p, String host, int port, int preset) {
		this(p);
		switch (preset) {
			case LANDSCAPE:
			default:
				setup(18, 16, host, port);
				presetTransformations.add(Transformations.ROTATE180);
				presetTransformations.add(Transformations.SNAKEX);
				break;
			case PORTRAIT:
				setup(16, 18, host, port);
				presetTransformations.add(Transformations.ROTATE90);
				presetTransformations.add(Transformations.SNAKEX);
				break;
		}
	}
	
    /**
     * A LED Matrix.
     *
     * @param p the parent PApplet
     * @param width width of the LED Matrix in pixels
     * @param height height of the LED Matrix in pixels
     * @param host network IP or host name to send data
     * @param port destination port (UDP)
     */
    public LEDMatrix(PApplet p, int width, int height, String host, int port) {
		this(p);
		setup(width, height, host, port);
    }
	
	void setup(int width, int height, String host, int port) {
		transformations = new ArrayList<Transformation>();
		presetTransformations = new ArrayList<Transformation>();
        this.host = host;
        this.port = port;
        // initialize frame
        frame = new Frame(width, height);
        // initialize udp
        udp = new UDP(p);
	}

    private void welcome() {
        System.out.println("LEDMatrix v0.2 by Martin Leopold http://martinloepold.com");
    }

    /**
     * Set a pixel to a color value.
     *
     * @param x horizontal coordinate
     * @param y vertical coordinate
     * @param rgb color value
     */
    public void set(int x, int y, int rgb) {
        frame.set(x, y, rgb);
    }

    /**
     * Set a pixel to a color value.
     *
     * @param x horizontal coordinate
     * @param y vertical coordinate
     * @param r red color component
     * @param g green color component
     * @param b blue color component
     */
    public void set(int x, int y, int r, int g, int b) {
        frame.set(x, y, r, g, b);
    }

    /**
     * Set all pixels to a color value.
     *
     * @param rgb color value
     */
    public void clear(int rgb) {
        for (int y = 0; y < frame.height; y++) {
            for (int x = 0; x < frame.width; x++) {
                frame.set(x, y, rgb);
            }
        }
    }

    /**
     * Set all pixels to a color value.
     *
     * @param r red color component
     * @param g green color component
     * @param b blue color component
     */
    public void clear(int r, int g, int b) {
        for (int y = 0; y < frame.height; y++) {
            for (int x = 0; x < frame.width; x++) {
                frame.set(x, y, r, g, b);
            }
        }
    }
    
    /**
     * Update the LED display. Sends the frame via UDP.
     */
    public void update() {
        if (isTestPattern) {
            frame.set(testPattern.nextFrame());
        }
        
        // apply transformations
        Frame transformed = frame;
		for (Transformation t : presetTransformations) {
            transformed = t.transform(transformed);
        }
        for (Transformation t : transformations) {
            transformed = t.transform(transformed);
        }
        // send via udp
        udp.send(transformed.flattened(), host, port);
    }
	
	/**
	 * Retrieve the width of the LED Matrix,
	 * @return width of the led matrix in pixels.
	 */
	public int width() {
		return frame.width;
	}
	
	/**
	 * Retrieve the height of the LED Matrix,
	 * @return height of the led matrix in pixels.
	 */
	public int height() {
		return frame.height;
	}

    /*
     * TRANSFORMATIONS
     */
    /**
     * Clear the transformation list.
     */
    public void clearTransformations() {
        transformations.clear();
    }

    /**
     * Flip the x axis of LED Matrix.
     */
    public void flipX() {
        if (!transformations.contains(Transformations.FLIPX)) {
            transformations.add(Transformations.FLIPX);
        }
    }

    /**
     * Don't flip the x axix.
     */
    public void noFlipX() {
        transformations.remove(Transformations.FLIPX);
    }

    /**
     * Toggle flipping the x axis.
     */
    public void toggleFlipX() {
        if (transformations.contains(Transformations.FLIPX)) {
            noFlipX();
        } else {
            flipX();
        }
    }

    /**
     * Flip the y axis of the LED Matrix.
     */
    public void flipY() {
        if (!transformations.contains(Transformations.FLIPY)) {
            transformations.add(Transformations.FLIPY);
        }
    }

    /**
     * Don't flip the y axis.
     */
    public void noFlipY() {
        transformations.remove(Transformations.FLIPY);
    }

    /**
     * Toggle flipping the y axis.
     */
    public void toggleFlipY() {
        if (transformations.contains(Transformations.FLIPY)) {
            noFlipY();
        } else {
            flipY();
        }
    }

    /**
     * Rotate the LED Matrix by 180 degrees. This is equal to {@link #flipX()}
     * combined with {@link #flipY()}.
     */
    public void rotate180() {
        if (!transformations.contains(Transformations.ROTATE180)) {
            transformations.add(Transformations.ROTATE180);
        }
    }

    /**
     * Don't rotate.
     */
    public void noRotate180() {
        transformations.remove(Transformations.ROTATE180);
    }

    /**
     * Toggle rotating.
     */
    public void toggleRotate180() {
        if (transformations.contains(Transformations.ROTATE180)) {
            noRotate180();
        } else {
            rotate180();
        }
    }
    
    /**
     * Rotate the LED Matrix by 90 degrees. 
     */
    public void rotate90() {
        if (!transformations.contains(Transformations.ROTATE90)) {
            transformations.add(Transformations.ROTATE90);
        }
    }

    /**
     * Don't rotate.
     */
    public void noRotate90() {
        transformations.remove(Transformations.ROTATE90);
    }

    /**
     * Toggle rotating.
     */
    public void toggleRotate90() {
        if (transformations.contains(Transformations.ROTATE90)) {
            noRotate90();
        } else {
            rotate90();
        }
    }

    /**
     * Transform the output in a snake-like pattern along the x-axis.
     */
    public void snakeX() {
        if (!transformations.contains(Transformations.SNAKEX)) {
            transformations.add(Transformations.SNAKEX);
        }
    }

    public void noSnakeX() {
        transformations.remove(Transformations.SNAKEX);
    }

    public void toggleSnakeX() {
        if (transformations.contains(Transformations.SNAKEX)) {
            noSnakeX();
        } else {
            snakeX();
        }
    }

    /**
     * Transform the output in a snake-like pattern along the y-axis;
     */
    public void snakeY() {
        if (!transformations.contains(Transformations.SNAKEY)) {
            transformations.add(Transformations.SNAKEY);
        }
    }

    public void noSnakeY() {
        transformations.remove(Transformations.SNAKEY);
    }

    public void toggleSnakeY() {
        if (transformations.contains(Transformations.SNAKEY)) {
            noSnakeY();
        } else {
            snakeY();
        }
    }
    
    /**
     * Enable the test pattern. This completely overwrites the frame data each time {@link #update()} is called. 
     * The Pattern consists of a fading color going from red to black, then green to black, then blue to black, written line by line from left to right, top to bottom.
     */
    public void testPattern() {
        isTestPattern = true;
        testPattern = new TestPattern(frame.width, frame.height);
    }
    
    /**
     * Disable the test pattern.
     */
    public void noTestPattern() {
        isTestPattern = false;
    }
    
    /**
     * Toggle the test pattern on and off.
     */
    public void toggleTestPattern() {
        isTestPattern = !isTestPattern;
    }
}
