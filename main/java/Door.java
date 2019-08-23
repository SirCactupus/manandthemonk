package main.java;

import javafx.scene.image.Image;

public class Door {
    private Boolean lockedState;
    private Image sprite;
    private int id;

    public Door(int id) {
        this.id = id;
        String doorURL;
        if (id < 7) {
            doorURL = "/main/resources/door.png";
        } else if (id == 7) {
            doorURL = "/main/resources/cavedoor.png";
        } else {
            doorURL = "/main/resources/underwaterdoor.png";
        }
        sprite = new Image(getClass().getResource(doorURL).toExternalForm());
        if (id != 1) lockedState = true;
        else lockedState = false;
    }

    public Boolean getLockedState() {
        return lockedState;
    }

    public void setLockedState(Boolean lockedState) {
        this.lockedState = lockedState;
    }

    public Image getSprite() {
        return sprite;
    }

    public void setSprite(Image sprite) {
        this.sprite = sprite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
