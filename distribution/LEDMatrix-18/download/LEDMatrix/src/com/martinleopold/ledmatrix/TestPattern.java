/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.martinleopold.ledmatrix;

/**
 *
 * @author mlg
 */
class TestPattern {

    int x, y;
    ColorCycle cc;
    Frame f;

    TestPattern(int width, int height) {
        f = new Frame(width, height); // this is black by default
        x = 0;
        y = 0;
        cc = new ColorCycle();
    }

    Frame nextFrame() {
        int c = cc.next(); // get next color
        f.set(x, y, c);

        x += 1;
        if (x > f.width - 1) {
            x = 0;
            y += 1;
            if (y > f.height - 1) {
                y = 0;
            }
        }
        return f;
    }

    class ColorCycle {

        int r, g, b;

        ColorCycle() {
            r = 255;
            g = 0;
            b = 0;
        }

        int current() {
            return 0xff000000 | (r << 16) | (g << 8) | b;
        }

        int next() {
            if (r > 0) {
                r -= 1;
                if (r <= 0) {
                    r = 0;
                    g = 255;
                }
            } else if (g > 0) {
                g -= 1;
                if (g <= 0) {
                    g = 0;
                    b = 255;
                }
            } else if (b > 0) {
                b -= 1;
                if (b <= 0) {
                    b = 0;
                    r = 255;
                }
            }

            return current();
        }
    }
}
