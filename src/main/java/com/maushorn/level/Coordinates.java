package com.maushorn.level;

import javax.xml.bind.annotation.XmlElement;

public class Coordinates {
    private float x;
    private float y;

    public Coordinates() {
        this.x = .0f;
        this.y = .0f;
    }

    public Coordinates(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @XmlElement
    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    @XmlElement
    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
