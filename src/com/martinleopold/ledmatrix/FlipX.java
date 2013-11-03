/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.martinleopold.ledmatrix;

/**
 *
 * @author mlg
 */
class FlipX implements Transformation {
    @Override
    public Frame transform(Frame frame) {
        Frame flipped = new Frame(frame.width, frame.height);
        for (int y=0; y<frame.height; y++) {
            for (int x=0; x<frame.width; x++) {
                int flippedX = frame.width - 1 - x;
                flipped.data[x][y][0] = frame.data[flippedX][y][0];
                flipped.data[x][y][1] = frame.data[flippedX][y][1];
                flipped.data[x][y][2] = frame.data[flippedX][y][2];
            }
        }
        return flipped;
    }
}