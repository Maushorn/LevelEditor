package com.maushorn.level;

import javafx.scene.image.ImageView;

import javax.xml.bind.annotation.XmlElement;
import java.net.URI;

public class LevelElement {
    private String name;
    private Coordinates coordinates;
    private URI spriteURI;

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

    public URI getSpriteURI() {
        return spriteURI;
    }

    public void setSpriteURI(URI spriteURI) {
        this.spriteURI = spriteURI;
    }

    @Override
    public String toString() {
        return "LevelElement{" +
                "name='" + name + '\'' +
                ", coordinates=" + coordinates +
                '}';
    }
}
