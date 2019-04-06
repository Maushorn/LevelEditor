package com.maushorn.level;

import javax.xml.bind.annotation.XmlElement;

public class LevelElement {
    private String name;
    private Coordinates coordinates;

    public LevelElement(String name) {
        this.name = name;
        this.coordinates = new Coordinates(.0f, .0f);
    }

    @XmlElement
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement
    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }
}
