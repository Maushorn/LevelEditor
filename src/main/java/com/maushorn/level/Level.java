package com.maushorn.level;

import javafx.scene.image.ImageView;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@XmlRootElement
public class Level {
    private String name;
    private HashMap<ImageView, LevelElement> levelelements;
    private List<LevelElement> levelElementsForXML;

    public Level(){
        this.name = "";
        this.levelelements = new HashMap<>();
    }

    public Level(String name) {
        this.name = name;
        this.levelelements = new HashMap<>();
    }


    public HashMap<ImageView, LevelElement> getLevelelements() {
        return levelelements;
    }

    public void setLevelElementsForXML(List<LevelElement> levelElementsForXML) {
        this.levelElementsForXML = levelElementsForXML;
    }

    @XmlElementWrapper(name = "elementlist")
    @XmlElement(name = "element")
    public List<LevelElement> getLevelleementsForXML(){
        List<LevelElement> list = new ArrayList<>();
        this.levelelements.forEach((imageView, levelElement) -> {
            list.add(levelElement);
        });
        return list;
    }

    public void setLevelelements(HashMap<ImageView, LevelElement> levelelements) {
        this.levelelements = levelelements;
    }

    @XmlElement
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
