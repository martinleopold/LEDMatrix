/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.martinleopold.ledmatrix;

/**
 *
 * @author mlg
 */
class Transformations {
    final static Transformation FLIPX = new FlipX();
    final static Transformation FLIPY = new FlipY();
    final static Transformation SNAKEX = new SnakeX();
    final static Transformation SNAKEY = new SnakeY();
    final static Transformation ROTATE180 = new Rotate180();
    final static Transformation ROTATE90 = new Rotate90();
    
    private Transformations() {}
}
