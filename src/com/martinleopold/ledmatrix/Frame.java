/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.martinleopold.ledmatrix;

/**
 * A single frame to be displayed on a LED Matrix
 *
 * @author Martin Leopold
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
			// construct an int out of rgb
			return 0xff000000 | (data[x][y][0] << 16) | (data[x][y][1] << 8) | data[x][y][2];
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
}
