package com.matthew.track.model.activity;

public enum Rating {
    POOR(0),
    BELOW_AVERAGE(1),
    AVERAGE(2),
    GOOD(3),
    EXCELLENT(4);

    private int ratingNum;

    private Rating(int ratingNum) {
        this.ratingNum = ratingNum;
    }

    public  int getRatingNum() {
        return this.ratingNum;
    }
}
