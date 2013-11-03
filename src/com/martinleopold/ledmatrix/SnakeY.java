/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.martinleopold.ledmatrix;

/**
 *
 * @author mlg
 */
class SnakeY implements Transformation {
    @Override
    public Frame transform(Frame frame) {
        Frame snaked = new Frame(frame.width, frame.height);
        for (int y=0; y<frame.height; y++) {
            for (int x=0; x<frame.width; x++) {
                int snakeY = x % 2 == 0 ? y : frame.height-1-y;
                snaked.data[x][y][0] = frame.data[x][snakeY][0];
                snaked.data[x][y][1] = frame.data[x][snakeY][1];
                snaked.data[x][y][2] = frame.data[x][snakeY][2];
            }
        }
        return snaked;
    }  
}
