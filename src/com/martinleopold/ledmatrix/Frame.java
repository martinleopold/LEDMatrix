package com.martinleopold.ledmatrix;

import processing.core.PApplet;
import processing.core.PImage;

/**
 * A single frame to be displayed on a LED Matrix
 *
 * @author Martin Leopold <m@martinleopold.com>
 */
public class Frame {

	int width;
	int height;
	byte[][][] data; // [x][y][rgb]
	// TODO is it better to use an int[][] ?? 

	public Frame(int width, int height) {
		this.width = width;
		this.height = height;
		data = new byte[width][height][3]; // this inits to all zeroes i.e. black
	}

	public void set(int x, int y, int rgb) {
		this.set(x, y, ((rgb >> 16) & 0xff), ((rgb >> 8) & 0xff), (rgb & 0xff));
	}

	public void set(int x, int y, int r, int g, int b) {
		if (x >= 0 && x < width && y >= 0 && y < height) {
			data[x][y][0] = (byte) r;
			data[x][y][1] = (byte) g;
			data[x][y][2] = (byte) b;
		}
	}

	public void set(Frame f) {
		if (f.width == width && f.height == height) {
			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					data[x][y][0] = f.data[x][y][0];
					data[x][y][1] = f.data[x][y][1];
					data[x][y][2] = f.data[x][y][2];
				}
			}
		}
	}
	
	/**
	 * Set the frame from a byte array containing the rows (from left to right) in sequence from top to bottom.
	 * @param data 
	 */
	public void set(byte[] data) {
		// check data length 
		if (data.length != width*height*3) return;
		
		int idx = 0;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				for (int i = 0; i < 3; i++) {
					this.data[x][y][i] = data[idx++];
				}
			}
		}
	}

	public void clear(int r, int g, int b) {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				set(x, y, r, g, b);
			}
		}
	}

	public void clear(int rgb) {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				set(x, y, rgb);
			}
		}
	}

	/**
	 * Get the color at a location in the Frame.
	 *
	 * @param x x-coordinate
	 * @param y y-coordinate
	 * @return the color
	 */
	public int get(int x, int y) {
		if (x >= 0 && x < width && y >= 0 && y < height) {
			// un-sign those bytes!
			int r = data[x][y][0] & 0xff;
			int g = data[x][y][1] & 0xff;
			int b = data[x][y][2] & 0xff;
			// construct an int out of rgb
			return 0xff000000 | (r << 16) | (g << 8) | (b);
		} else {
			throw new IllegalArgumentException("coordinates out of bounds: x=" + x + " y=" + y);
		}
	}

	/**
	 * Get a copy of this frame.
	 *
	 * @return the copy
	 */
	public Frame copy() {
		Frame copy = new Frame(this.width, this.height);
		copy.set(this);
		return copy;
	}

	/**
	 * Get a byte array containing the rows (from left to right) in sequence from top to bottom.
	 *
	 * @return the flattened frame
	 */
	public byte[] flattened() {
		byte[] flattened = new byte[width * height * 3];
		int idx = 0;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				for (int i = 0; i < 3; i++) {
					flattened[idx++] = data[x][y][i];
				}
			}
		}
		return flattened;
	}
	
	/**
	 * Get this frame as PImage for display.
	 * @return the image
	 */
	public PImage image() {
		return image(1);
	}
	
	/**
	 * Get this frame as scaled PImage for display.
	 * @param scale the size of a frame pixel in the image
	 * @return the image
	 */
	public PImage image(int scale) {
		PImage img = new PImage(width*scale, height*scale);
		img.loadPixels();
		int idx = 0;
		for (int y = 0; y < height; y++) {
			for (int j=0; j<scale; j++) {
				for (int x = 0; x < width; x++) {
					for (int i=0; i<scale; i++) {
						img.pixels[idx++] = get(x,y);
					}
				}
			}
		}
		img.updatePixels();
		return img;
	}
}
