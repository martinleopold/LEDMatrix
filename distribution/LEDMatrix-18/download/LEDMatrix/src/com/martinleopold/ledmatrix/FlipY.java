/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.martinleopold.ledmatrix;

/**
 *
 * @author mlg
 */
class FlipY implements Transformation {
    @Override
    public Frame transform(Frame frame) {
        Frame flipped = new Frame(frame.width, frame.height);
        for (int y=0; y<frame.height; y++) {
            for (int x=0; x<frame.width; x++) {
                int flippedY = frame.height - 1 - y;
                flipped.data[x][y][0] = frame.data[x][flippedY][0];
                flipped.data[x][y][1] = frame.data[x][flippedY][1];
                flipped.data[x][y][2] = frame.data[x][flippedY][2];
            }
        }
        return flipped;
    }
}
