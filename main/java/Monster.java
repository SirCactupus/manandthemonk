package main.java;

import javafx.scene.image.Image;

public class Monster {
    private int health, strength, toughness, speed, id, exp, tempSpeed, tempToughness;
    private String name;
    private Image sprite;
    private Item dropItem;
    private int nextAttack;


    public Monster(int id) {
        this.id = id;
        health = 20;
        strength = 5;
        toughness = 5;
        speed = 5;
        String monsterURL = "/main/resources/monster" + id + ".png";
        sprite = new Image(getClass().getResource(monsterURL).toExternalForm());

        String[] nameList = {"Monk", "Slime", "Boar", "Goblin", "Skeleton", "Golem", "Empty",
                "Jellyfish", "Electric Eel", "Squid"};
        int[] itemIDs = {2, 4, 6, 8, 10, 12, 14, 16, 18};
        int[] speedList = {2, 2, 3, 4, 4, 0, 5, 6, 6};
        int[] toughnessList = {0, 1, 2, 3, 4, 0, 5, 6, 7};
        int[] healthList = {20, 30, 40, 50, 70, 0, 90, 110, 150};
        int[] expList = {5, 10, 20, 40, 60, 0, 80, 100, 150};
        int[] strengthList = {10, 20, 30, 40, 50, 0, 60, 70, 80};

        if (id == 1) {
            name = "Monk";
        } else {
            dropItem = new Item(itemIDs[id - 2]);
            name = nameList[id - 1];
            exp = expList[id - 2];
            speed = speedList[id - 2];
            toughness = toughnessList[id - 2];
            health = healthList[id - 2];
            strength = strengthList[id - 2];
        }

        nextAttack = 1;
        tempSpeed = speed;
        tempToughness = toughness;
    }

    public double attack(Character character) {
        return strength * (1 - (0.1 * character.getToughness()));
    }

    public int getExp() {
        return exp;
    }

    public double heavyAttack(Character character) {
        return attack(character) * 1.5;
    }

    public double lightAttack(Character character) {
        return attack(character) * 0.5;
    }

    public int getNextAttack() {
        return nextAttack;
    }

    public int getTempSpeed() {
        return tempSpeed;
    }

    public int getTempToughness() {
        return tempToughness;
    }

    public int nextAttack() {
        nextAttack = (int) (Math.random() * 3 + 1);
        if (nextAttack == 2) {
            tempSpeed = speed * 2;
        } else if (nextAttack == 3) {
            tempSpeed = speed / 4;
            tempToughness = toughness * 2;
        } else {
            tempSpeed = speed;
            tempToughness = toughness;
        }
        return nextAttack;
    }

    public int getHealth() {
        return health;
    }

    public void damage(int d) {
        this.health = this.health - d;
    }

    public int getStrength() {
        return strength;
    }

    public int getToughness() {
        return toughness;
    }

    public int getId() {
        return id;
    }

    public Image getSprite() {
        return sprite;
    }


    public Item getDropItem() {
        return dropItem;
    }


    public String getName() {
        return name;
    }
}
