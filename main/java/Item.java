package main.java;

import javafx.scene.image.Image;

public class Item {
    private Image sprite;
    private int id;
    private String description;

    public Item(int id){
        this.id = id;
        sprite = new Image("/main/resources/sky.png");
        description = "";
    }

    public Image getSprite() {
        return sprite;
    }

    public int getId(){
        return id;
    }

    public String getDescription(){
        return description;
    }
}
