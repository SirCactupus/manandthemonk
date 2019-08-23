package main.java;

import javafx.scene.image.Image;

public class Character {
    private int health, strength, toughness, speed, level, location, exp, maxHealth;
    private Image sprite;
    private Inventory inventory;
    private String name;

    public Character(String name) {
        this.name = name;
        maxHealth = 20;
        health = maxHealth;
        strength = 10;
        toughness = 5;
        speed = 5;
        level = 1;
        sprite = new Image("/main/resources/monster1.png");
        inventory = new Inventory();
        location = 1;
        exp = 0;
    }

    public void gainExp(int exp) {
        int[] levelExp = {10, 30, 50, 80, 100, 150};
        this.exp += exp;
        try {
            if (this.exp >= levelExp[level - 1]) {
                level++;
                strength = 5 + 5 * level;
                maxHealth = 10 + 10 * level;
                speed = 5 + level / 3;
                if(toughness < 8) {
                    toughness = 5 + level / 3;
                } else {
                    toughness = 8;
                }
            }
        } catch (IndexOutOfBoundsException e) {
            if (this.exp >= levelExp[levelExp.length - 1]) {
                level++;
                strength = 5 + 5 * level;
                maxHealth = 10 * level + 10;
                speed = 5 + level / 3;
                if(toughness < 8) {
                    toughness = 5 + level / 3;
                } else {
                    toughness = 8;
                }
            }
        }
    }

    public double attack(Monster monster) {
        return strength * (1 - (0.1 * monster.getToughness()));
    }

    public double parry(Monster monster) {
        return 1.25 * attack(monster);
    }

    public int getHealth() {
        return health;
    }

    public void damage(int d) {
        this.health -= d;
    }

    public void revive() {
        health = maxHealth;
    }

    public int getStrength() {
        return strength;
    }

    public int getToughness() {
        return toughness;
    }

    public int getSpeed() {
        return speed;
    }

    public int getLevel() {
        return level;
    }

    public Image getSprite() {
        return sprite;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public int getExp() {
        return exp;
    }

    public int toNextLevel(){
        int[] levelExp = {10, 30, 50, 80, 100, 150};
        try{
            return levelExp[level - 1];
        } catch (IndexOutOfBoundsException e){
            return levelExp[levelExp.length - 1];
        }
    }

    public String getName() {
        return name;
    }

    public int getMaxHealth() {
        return maxHealth;
    }
}
