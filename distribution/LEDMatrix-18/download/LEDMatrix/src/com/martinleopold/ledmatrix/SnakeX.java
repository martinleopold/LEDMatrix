/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.martinleopold.ledmatrix;

/**
 *
 * @author mlg
 */
class SnakeX implements Transformation {
    @Override
    public Frame transform(Frame frame) {
        Frame snaked = new Frame(frame.width, frame.height);
        for (int y=0; y<frame.height; y++) {
            for (int x=0; x<frame.width; x++) {
                int snakeX = y % 2 == 0 ? x : frame.width-1-x;
                snaked.data[x][y][0] = frame.data[snakeX][y][0];
                snaked.data[x][y][1] = frame.data[snakeX][y][1];
                snaked.data[x][y][2] = frame.data[snakeX][y][2];
            }
        }
        return snaked;
    }  
}
