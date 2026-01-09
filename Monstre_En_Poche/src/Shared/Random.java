package Shared;

public class Random {
    static public int randInt(java.util.Random rand, int min, int max) {
        return rand.nextInt((max - min) + 1) + min;
    }
}
