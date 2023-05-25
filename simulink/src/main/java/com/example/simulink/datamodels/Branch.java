package com.example.simulink.datamodels;

import java.util.ArrayList;

public class Branch {
    private ArrayList<int[]> points;
    private int dst;

    public Branch(ArrayList<int[]> points, int dst) {
        this.points = points;
        this.dst = dst;
    }

    public ArrayList<int[]> getPoints() {
        return points;
    }

    public void setPoints(ArrayList<int[]> points) {
        this.points = points;
    }

    public int getDst() {
        return dst;
    }

    public void setDst(int dst) {
        this.dst=dst;
    }
}