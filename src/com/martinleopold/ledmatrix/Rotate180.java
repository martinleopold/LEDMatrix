/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.martinleopold.ledmatrix;

/**
 *
 * @author mlg
 */
class Rotate180 implements Transformation {
    @Override
    public Frame transform(Frame frame) {
        Frame rotated = new Frame(frame.width, frame.height);
        for (int y=0; y<frame.height; y++) {
            for (int x=0; x<frame.width; x++) {
                int flippedX = frame.width - 1 - x;
                int flippedY = frame.height - 1 - y;
                rotated.data[x][y][0] = frame.data[flippedX][flippedY][0];
                rotated.data[x][y][1] = frame.data[flippedX][flippedY][1];
                rotated.data[x][y][2] = frame.data[flippedX][flippedY][2];
            }
        }
        return rotated;
    }
}
