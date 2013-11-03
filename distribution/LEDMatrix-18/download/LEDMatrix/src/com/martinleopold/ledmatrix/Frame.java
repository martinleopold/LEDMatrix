/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.martinleopold.ledmatrix;

/**
 *
 * @author mlg
 */
class Frame {
    int width;
    int height;
    byte[][][] data;
    
    public Frame(int width, int height) {
        this.width = width;
        this.height = height;
        data = new byte[width][height][3];
    }
    
    public void set(int x, int y, int rgb) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            data[x][y][0] = (byte) ((rgb >> 16) & 0xff);
            data[x][y][1] = (byte) ((rgb >> 8) & 0xff);
            data[x][y][2] = (byte) (rgb & 0xff);
        }
    }
    
    public void set(int x, int y, int r, int g, int b) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            data[x][y][0] = (byte) r;
            data[x][y][1] = (byte) g;
            data[x][y][2] = (byte) b;
        }
    }
    
    public byte[] flattened() {
        byte[] flattened = new byte[width * height * 3];
        int idx = 0;
        for (int y=0; y<height; y++) {
            for (int x=0; x<width; x++) {
                for (int i=0; i<3; i++) {
                    flattened[idx++] = data[x][y][i];
                }
            }
        }
        return flattened;
    }
    
   public void set(Frame f) {
       if (f.width == width && f.height == height) {
            for (int x=0; x<width; x++) {
                for (int y=0; y<height; y++) {
                    data[x][y][0] = f.data[x][y][0];
                    data[x][y][1] = f.data[x][y][1];
                    data[x][y][2] = f.data[x][y][2];
                }
            }
       }
   }
}
