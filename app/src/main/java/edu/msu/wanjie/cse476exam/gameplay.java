package edu.msu.wanjie.cse476exam;

import java.util.Random;

/**
 * Created by MrRD on 11/15/16.
 */

public class gameplay {
    private int [][] tiles;
    private int emptycount = 16;
    private int max = 3;
    private int min = 0;
    private int score = 0;

    public gameplay() {

        tiles = new int[4][4];

        for (int x = 0; x<tiles.length; x++) {
            for (int y = 0; y<tiles[x].length; y++) {
                tiles[x][y] = 0;
            }
        }

        generator();
        generator();
    }
    public void set(int num, int x, int y) {
        tiles[x][y] = num;
    }

    public void generator() {

        int numEmpty = isFull();
        if (numEmpty > 0) {

            Random rand = new Random();
            int i = rand.nextInt(numEmpty);
            int count = 0;
            // Initial board

            for (int x = 0; x < tiles.length; x++) {
                for (int y = 0; y < tiles.length; y++) {
                    if (tiles[x][y] == 0) {
                        if (count == i && tiles[x][y] == 0) {
                            //emptycount--;
                            set(2, x, y);
                        }
                        count++;
                    }
                }
            }
        }
    }

    public int[][] getTiles() {
        return tiles;
    }

    public boolean flushUp() {
        boolean moved = false;
        for (int x = 0; x < 4; x++) {
            int count = 0;
            for (int y = 0; y < 4; y++) {
                if (tiles[x][y] != 0) {
                    if (tiles[x][count] == 0) {
                        tiles[x][count] = tiles[x][y];
                        tiles[x][y] = 0;
                        moved = true;
                    }
                    count++;
                }
            }
        }
        return moved;
    }

    public boolean flushDown() {
        boolean moved = false;
        for (int x = 0; x < 4; x++) {
            int count = 3;
            for (int y = 3; y >= 0; y--) {
                if (tiles[x][y] != 0) {
                    if (tiles[x][count] == 0) {
                        tiles[x][count] = tiles[x][y];
                        tiles[x][y] = 0;
                        moved = true;
                    }
                    count--;
                }
            }
        }
        return moved;
    }

    public boolean flushRight() {
        boolean moved = false;
        for (int y = 0; y < 4; y++) {
            int count = 3;
            for (int x = 3; x >= 0; x--) {
                if (tiles[x][y] != 0) {
                    if (tiles[count][y] == 0) {
                        tiles[count][y] = tiles[x][y];
                        tiles[x][y] = 0;
                        moved = true;
                    }
                    count--;
                }
            }
        }
        return moved;
    }

    public boolean flushLeft() {
        boolean moved = false;
        for (int y = 0; y < 4; y++) {
            int count = 0;
            for (int x = 0; x < 4; x++) {
                if (tiles[x][y] != 0) {
                    if (tiles[count][y] == 0) {
                        tiles[count][y] = tiles[x][y];
                        tiles[x][y] = 0;
                        moved = true;
                    }
                    count++;
                }
            }
        }
        return moved;
    }

    public boolean addUp() {
        boolean moved = false;
        for (int x = 0; x < 4; x++) {
            for (int y = 1; y < 4; y++) {
                if (tiles[x][y] != 0) {
                    if (tiles[x][y-1] == tiles[x][y]) {
                        tiles[x][y-1] = tiles[x][y]*2;
                        tiles[x][y] = 0;
                        score+=tiles[x][y-1];
                        moved = true;
                    }
                }
            }
        }
        return moved;
    }

    public boolean addDown() {
        boolean moved = false;
        for (int x = 0; x < 4; x++) {
            for (int y = 2; y >= 0; y--) {
                if (tiles[x][y] != 0) {
                    if (tiles[x][y+1] == tiles[x][y]) {
                        tiles[x][y+1] = tiles[x][y]*2;
                        tiles[x][y] = 0;
                        score+=tiles[x][y+1];
                        moved = true;
                    }
                }
            }
        }
        return moved;
    }

    public boolean addRight() {
        boolean moved = false;
        for (int y = 0; y < 4; y++) {
            for (int x = 2; x >= 0; x--) {
                if (tiles[x][y] != 0) {
                    if (tiles[x+1][y] == tiles[x][y]) {
                        tiles[x+1][y] = tiles[x][y]*2;
                        tiles[x][y] = 0;
                        score+=tiles[x+1][y];
                        moved = true;
                    }
                }
            }
        }
        return moved;
    }

    public boolean addLeft() {
        boolean moved = false;
        for (int y = 0; y < 4; y++) {
            for (int x = 1; x <4; x++) {
                if (tiles[x][y] != 0) {
                    if (tiles[x-1][y] == tiles[x][y]) {
                        tiles[x-1][y] = tiles[x][y]*2;
                        tiles[x][y] = 0;
                        score+=tiles[x-1][y];
                        moved = true;
                    }
                }
            }
        }
        return moved;
    }

    public int isFull() {
        int numEmpty = 0;
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                if (tiles[x][y] == 0) {
                    numEmpty++;
                }
            }
        }
        return numEmpty;
    }

    public boolean checkLose() {

        if (isFull() > 0) {
            return true;
        }
        // Check Add Up
        for (int x = 0; x < 4; x++) {
            for (int y = 1; y < 4; y++) {
                if (tiles[x][y] != 0) {
                    if (tiles[x][y-1] == tiles[x][y]) {
                        return true;
                    }
                }
            }
        }

        // Check Add Down
        for (int x = 0; x < 4; x++) {
            for (int y = 2; y >= 0; y--) {
                if (tiles[x][y] != 0) {
                    if (tiles[x][y+1] == tiles[x][y]) {
                        return true;
                    }
                }
            }
        }

        // Check Add Right
        for (int y = 0; y < 4; y++) {
            for (int x = 2; x >= 0; x--) {
                if (tiles[x][y] != 0) {
                    if (tiles[x+1][y] == tiles[x][y]) {
                        return true;
                    }
                }
            }
        }

        // Check Add Left
        for (int y = 0; y < 4; y++) {
            for (int x = 1; x <4; x++) {
                if (tiles[x][y] != 0) {
                    if (tiles[x-1][y] == tiles[x][y]) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public int getScore() {return score;}
}
