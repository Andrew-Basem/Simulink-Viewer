package com.example.simulink.datamodels;

import java.util.ArrayList;

public class NewLine {
    private int src;
    private int dst;
    private ArrayList<int[]> points;
    private ArrayList<Branch> branches;

    public NewLine(int src, int dst, ArrayList<int[]> points, ArrayList<Branch> branches) {
        this.src = src;
        this.dst = dst;
        this.points = points;
        this.branches = branches;
    }

    public int getSrc() {
        return src;
    }

    public void setSrc(int src) {
        this.src = src;
    }

    public int getDst() {
        return dst;
    }

    public void setDst(int dst) {
        this.dst = dst;
    }

    public ArrayList<int[]> getPoints() {
        return points;
    }

    public void setPoints(ArrayList<int[]> points) {
        this.points = points;
    }

    public ArrayList<Branch> getBranches() {
        return branches;
    }

    public void setBranches(ArrayList<Branch> branches) {
        this.branches = branches;
    }
}