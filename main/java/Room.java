package main.java;

import javafx.scene.image.Image;

public class Room {
    private Monster monster;
    private Door door;
    private Image backgroundImage;
    private int id;

    public Room(int id) {
        this.id = id;
        monster = new Monster(id);
        door = new Door(id);
        String roomURL;

        if (id < 7) {
            roomURL = "/main/resources/templerooms.png";
        } else if (id == 7) {
            roomURL = "/main/resources/cave.png";
        } else {
            roomURL = "/main/resources/underwater.png";
        }
        backgroundImage = new Image(getClass().getResource(roomURL).toExternalForm());
    }

    public Monster getMonster() {
        return monster;
    }

    public Door getDoor() {
        return door;
    }

    public Image getBackgroundImage() {
        return backgroundImage;
    }

    public int getId() {
        return id;
    }

}
