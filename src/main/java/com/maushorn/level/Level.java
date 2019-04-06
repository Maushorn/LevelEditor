package com.maushorn.level;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;


@XmlRootElement
public class Level {
    private String name;
    private ArrayList<LevelElement> gameObjects;

    public Level(){
        this.name = "";
        this.gameObjects = new ArrayList<>();
    }

    public Level(String name) {
        this.name = name;
        this.gameObjects = new ArrayList<>();
    }

    @XmlElement
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElementWrapper(name = "elementlist")
    @XmlElement(name = "element")
    public ArrayList<LevelElement> getGameObjects() {
        return gameObjects;
    }

    public void setGameObjects(ArrayList<LevelElement> gameObjects) {
        this.gameObjects = gameObjects;
    }
}
