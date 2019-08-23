package main.java;

import java.util.ArrayList;

public class PlayGame {
    private Character character;
    private ArrayList<Room> roomList;
    private int dState, dState2, prevDiaState;


    public PlayGame(Character c){
        character = c;
        roomList = new ArrayList<>();
        roomList.add(new Room(1));
        roomList.add(new Room(2));
        roomList.add(new Room(3));
        roomList.add(new Room(4));
        roomList.add(new Room(5));
        roomList.add(new Room(6));
        roomList.add(new Room(7));
        roomList.add(new Room(8));
        roomList.add(new Room(9));
        roomList.add(new Room(10));
        dState = 0;
        dState2 = -1;
        prevDiaState = 0;
    }

    public Character getCharacter() {
        return character;
    }

    public ArrayList<Room> getRoomList() {
        return roomList;
    }

    public String nextDialogue() {
        for (Item i: character.getInventory().getItems()) {
            dState = i.getId() - 1;
        }
        if(prevDiaState != dState){
            dState2 = -1;
            prevDiaState = dState;
        }

        String[] firstDialogue = {"I cannot tell you the key to enlightenment, but I can show you",
                                    "Enter the door to my right and you will find what you seek."};
        String[] secondDialogue = {"Ah, you have slime.", "I can use that to make a key to the second door", "Give me the slime"};
        String[] thirdDialogue = {"You can continue through the second door"};
        String[] fourthDialogue = {"Ah, you have boar skin.", "I can use that to make a key to the third door", "Give me the boar skin"};
        String[] fifthDialogue = {"You can continue through the fourth door"};
        String[] sixthDialogue = {"Ah, you have tattered clothes.", "I can use that to make a key to the fourth door", "Give me the tattered clothes"};
        String[] seventhDialogue = {"You can continue through the fourth door"};
        String[] eighthDialogue = {"Ah, you have a bone.", "I can use that to make a key to the fifth door", "Give me the bone."};
        String[] ninthDialogue = {"You can continue through the fifth door"};
        String[] tenthDialogue = {"Ah, you have a golem stone.", "I can use that to make a key to the sixth door", "Give me the golem stone"};
        String[] eleventhDialogue = {"You can continue through the sixth door"};
        String[] twelfthDialogue = {"You want to explore the pool in the cave?", "You'll need this gear to dive in."};
        String[] thirteenthDialogue = {"You can explore the pool"};
        String[] fourteenthDialogue = {"Ah, you have jellyfish slime.", "I can use that to strengthen your diving gear.", "Give me the jellyfish slime"};
        String[] fifteenthDialogue = {"You can dive deeper"};
        String[] sixteenthDialogue = {"Ah, you have eel.", "I can use that to strengthen your diving gear", "Give me that eel"};
        String[] seventeenthDialogue = {"You can dive deeper"};
        String[] eighteenthDialogue = {"Ah you have a tentacle.", "I can use that to strengthen your diving gear.", "Give me that tentacle"};
        String[] nineteenthDialogue = {"You can dive deeper"};

        ArrayList<String[]> diaList = new ArrayList<>();
        String[][] diaList2 = {firstDialogue, secondDialogue, thirdDialogue, fourthDialogue, fifthDialogue, sixthDialogue,
                seventhDialogue, eighthDialogue, ninthDialogue, tenthDialogue, eleventhDialogue, twelfthDialogue,
                thirteenthDialogue, fourteenthDialogue, fifteenthDialogue, sixteenthDialogue, seventeenthDialogue,
                eighteenthDialogue, nineteenthDialogue};
        for (String[] dia: diaList2) {
            diaList.add(dia);
        }

        try{
            dState2++;
            return diaList.get(dState)[dState2];
        } catch (IndexOutOfBoundsException e){
            int itemID = -1;
            switch (dState){
                case 1: itemID = 2;
                        character.getInventory().addItem(new Item(3));
                        break;
                case 3: itemID = 4;
                    character.getInventory().addItem(new Item(5));
                    break;
                case 5: itemID = 6;
                    character.getInventory().addItem(new Item(7));
                    break;
                case 7: itemID = 8;
                    character.getInventory().addItem(new Item(9));
                    break;
                case 9: itemID = 10;
                    character.getInventory().addItem(new Item(11));
                    break;
                case 11: itemID = 12;
                    character.getInventory().addItem(new Item(13));
                    break;
                case 13: itemID = 14;
                    character.getInventory().addItem(new Item(15));
                    break;
                case 15: itemID = 16;
                    character.getInventory().addItem(new Item(17));
                    break;
                case 17: itemID = 18;
                    character.getInventory().addItem(new Item(19));
                    break;
            }
            if(itemID != -1){
                for(int i = 0; i < character.getInventory().getItems().size(); i++){
                    if(character.getInventory().getItems().get(i).getId() == itemID){
                        character.getInventory().getItems().remove(i);
                    }
                }
            }

            if(dState == 2 || dState == 4 || dState == 6 || dState == 8 ||
                    dState == 10 || dState == 12 || dState == 14 || dState == 16 || dState == 18){
                return diaList.get(dState)[0];
            } else if(dState == 0) {
                return diaList.get(dState)[1];
            } else {
                dState++;
                return  diaList.get(dState)[0];
            }
        }
    }

    public void fight(int cAttack) {
        int currentLocation = character.getLocation() - 1;
        Monster monster = roomList.get(currentLocation).getMonster();
        if(cAttack == 1){ //attack
            monster.damage((int) Math.round(character.attack(monster)));
            monsterAttack(monster);
        } else if(cAttack == 2){ //parry
            if(character.getSpeed() >= monster.getTempSpeed() && monster.getNextAttack() != 3){
                monster.damage((int) Math.round(character.parry(monster)));
            } else { //characters speed is less than monster speed or monster did heavy attack
                monsterAttack(monster);
            }
        } else { //cAttack == 3
            if(monster.getTempSpeed() > character.getSpeed()){
                monsterAttack(monster);
            }
        }
        monster.nextAttack();
    }

    private void monsterAttack(Monster monster) {
        switch (monster.getNextAttack()){
            case 1: character.damage((int) Math.round(monster.attack(character)));
                break;
            case 2: character.damage((int) Math.round(monster.lightAttack(character)));
                break;
            case 3: character.damage((int) Math.round(monster.heavyAttack(character)));
                break;
        }
    }
}
