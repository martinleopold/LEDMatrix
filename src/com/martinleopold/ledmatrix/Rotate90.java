package com.martinleopold.ledmatrix;

/**
 *
 * @author stefan lendl
 */
class Rotate90 implements Transformation {
    @Override
    public Frame transform(Frame frame) {
        Frame rotated = new Frame(frame.height, frame.width);
        for (int y=0; y<frame.height; y++) {
            for (int x=0; x<frame.width; x++) {
            	
				int newX = frame.height-1-y;
				int newY = x;
                rotated.data[newX][newY][0] = frame.data[x][y][0];
                rotated.data[newX][newY][1] = frame.data[x][y][1];
                rotated.data[newX][newY][2] = frame.data[x][y][2];
            }
        }
		return rotated;
    }
}
