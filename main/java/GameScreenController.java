package main.java;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.IOException;

public class GameScreenController {
    @FXML
    private ImageView backgroundImageIV, playerImageIV;
    @FXML
    private Pane gamePane;
    @FXML
    private TextArea promptTextArea;
    @FXML
    private Button interactButton;
    @FXML
    private ImageView selected, monster, door;
    @FXML
    private GridPane gameGridPane;
    @FXML
    private Tab gameTab, statusTab;
    @FXML
    private Label nameLabel, healthLabel, strengthLabel, toughnessLabel, speedLabel;

    private DropShadow ds;
    private PlayGame playGame;
    private Button returnButton;


    public void setPlayGame(PlayGame playGame) {
        this.playGame = playGame;
    }


    public void changeBackgroundImage() {
        Room room = playGame.getRoomList().get(playGame.getCharacter().getLocation() - 1);
        backgroundImageIV.setImage(room.getBackgroundImage());
        backgroundImageIV.setFitWidth(gamePane.getWidth());
        backgroundImageIV.setFitHeight(gamePane.getHeight());
    }

    public void populatePane() {
        gamePane.getChildren().clear();
        selected = null;

        int location = playGame.getCharacter().getLocation();
        gamePane.getChildren().add(backgroundImageIV);
        playGame.getRoomList().set(location - 1, new Room(location));
        Room room = playGame.getRoomList().get(location - 1);
        changeBackgroundImage();
        ds = new DropShadow(20, Color.AQUA);
        selected = new ImageView();
        monster = new ImageView(room.getMonster().getSprite());
        door = new ImageView(room.getDoor().getSprite());
        monster.setFitHeight(300);
        monster.setFitWidth(300);
        if (room.getId() < 7) {
            door.setFitHeight(300);
            door.setFitWidth(300);
        } else if (room.getId() >= 7) {
            door.setFitHeight(100);
        }

        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(0);

        monster.setEffect(colorAdjust);
        door.setEffect(colorAdjust);
        backgroundImageIV.setEffect(colorAdjust);

        returnButton = new Button("Return to monk");

        if (room.getId() == 1) {
            monster.setX(100);
            monster.setY(0);
            door.setX(500);
            door.setY(0);
            gamePane.getChildren().add(monster);
            gamePane.getChildren().add(door);
            gameGridPane.getChildren().remove(returnButton);
        } else if (playGame.getCharacter().getLocation() != 7) {
            monster.setX(250);
            monster.setY(0);
            gamePane.getChildren().add(monster);
        } else { //room ID = 7
            door.setX(30);
            door.setY(200);
            gamePane.getChildren().add(door);
        }

        monster.setOnMouseClicked(event -> {
            selected.setEffect(null);
            selected = monster;
            selected.setEffect(ds);
            promptTextArea.setText("Monster is selected");
        });

        door.setOnMouseClicked(event -> {
            selected.setEffect(null);
            selected = door;
            selected.setEffect(ds);
            promptTextArea.setText("Door is selected");
        });

        returnButton.setOnAction(e -> {
            playGame.getCharacter().setLocation(1);

            populatePane();
        });
    }

    @FXML
    public void interactClicked() {
        Monster m = playGame.getRoomList().get(playGame.getCharacter().getLocation() - 1).getMonster();
        Door d = playGame.getRoomList().get(playGame.getCharacter().getLocation() - 1).getDoor();
        Character c = playGame.getCharacter();
        try {
            if (selected.equals(monster)) {
                if (m.getName().equals("Monk")) {
                    promptTextArea.setText(playGame.nextDialogue());
                } else {
                    HBox buttonList = new HBox(15);
                    Button attack = new Button("Attack");
                    Button parry = new Button("Parry");
                    Button dodge = new Button("Dodge");
                    buttonList.setAlignment(Pos.CENTER);

                    VBox monsterStats = new VBox(15);
                    monsterStats.setStyle("-fx-background-color: #fff; -fx-border-color: #000;");
                    Label monsterName = new Label(m.getName());
                    Label monsterHealth = new Label("Health: " + m.getHealth());
                    Label monsterStrength = new Label("Strength: " + m.getStrength());
                    Label monsterSpeed = new Label("Speed: " + m.getTempSpeed());
                    Label monsterToughness = new Label("Toughness: " + m.getTempToughness());
                    monsterStats.getChildren().addAll(monsterName, monsterHealth, monsterStrength,
                            monsterSpeed, monsterToughness);
                    monsterStats.setTranslateX(600);
                    monsterStats.setTranslateY(50);

                    VBox playerStats = new VBox(15);
                    playerStats.setStyle("-fx-background-color: #fff; -fx-border-color: #000");
                    Label playerName = new Label(c.getName());
                    Label playerHealth = new Label("Health: " + c.getHealth());
                    Label playerStrength = new Label("Strength: " + c.getStrength());
                    Label playerSpeed = new Label("Speed: " + c.getSpeed());
                    Label playerToughness = new Label("Toughness: " + c.getToughness());
                    playerStats.getChildren().addAll(playerName, playerHealth, playerStrength,
                            playerSpeed, playerToughness);
                    playerStats.setTranslateX(50);
                    playerStats.setTranslateY(50);


                    gamePane.getChildren().addAll(monsterStats, playerStats);

                    buttonList.getChildren().addAll(attack, parry, dodge);
                    TextArea fightInformation = new TextArea();

                    gameGridPane.getChildren().remove(promptTextArea);
                    gameGridPane.getChildren().remove(interactButton);
                    gameGridPane.add(buttonList, 0, 2);
                    gameGridPane.add(fightInformation, 2, 2);

                    attack.setOnAction(e -> {
                        playGame.fight(1);
                        fightTextUpdate(m, fightInformation, monsterStats, playerStats, c);
                        death(c, m, buttonList, fightInformation, monsterStats, playerStats);
                    });
                    parry.setOnAction(e -> {
                        playGame.fight(2);
                        fightTextUpdate(m, fightInformation, monsterStats, playerStats, c);
                        death(c, m, buttonList, fightInformation, monsterStats, playerStats);
                    });
                    dodge.setOnAction(e -> {
                        playGame.fight(3);
                        fightTextUpdate(m, fightInformation, monsterStats, playerStats, c);
                        death(c, m, buttonList, fightInformation, monsterStats, playerStats);
                    });
                }
            } else if (selected.equals(door)) {

                for (Item i : playGame.getCharacter().getInventory().getItems()) {
                    if (i.getId() == d.getId() * 2 - 1) {
                        d.setLockedState(false);
                    }
                }
                if (d.getLockedState()) {

                    if (d.getId() == 7) {
                        playGame.getCharacter().getInventory().addItem(new Item(12));
                    }
                    promptTextArea.setText("The door is locked");
                    gameGridPane.getChildren().remove(interactButton);
                    gameGridPane.add(returnButton, 2, 2);
                    returnButton.setOnAction(e -> {
                        playGame.getCharacter().setLocation(1);
                        playGame.getCharacter().revive();
                        gameGridPane.getChildren().remove(returnButton);
                        populatePane();
                        gameGridPane.add(interactButton, 2, 2);
                    });

                } else {
                    if (d.getId() == 10) {
                        Main.reload(new FXMLLoader(getClass().getResource("/main/fxml/text.fxml")));
                    } else {
                        playGame.getCharacter().setLocation(playGame.getCharacter().getLocation() + 1);
                        if(playGame.getCharacter().getLocation() == 7){
                            gameTab.setText("Cave");
                        } else if(playGame.getCharacter().getLocation() > 7){
                            gameTab.setText("Underwater " + (playGame.getCharacter().getLocation() - 7));
                        } else {
                            gameTab.setText("Temple " + playGame.getCharacter().getLocation());
                        }
                        populatePane();
                    }
                }
            }
        } catch (NullPointerException e) {
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fightTextUpdate(Monster m, TextArea fightInformation, VBox monsterStats, VBox playerStats, Character c) {
        if (m.getNextAttack() == 1) {
            fightInformation.setText("The " + m.getName() + " is preparing to strike");
        } else if (m.getNextAttack() == 2) {
            fightInformation.setText("The " + m.getName() + " is moving quickly");
        } else {
            fightInformation.setText("The " + m.getName() + " is charging up");
        }

        Label playerHealth = (Label) playerStats.getChildren().get(1);
        playerHealth.setText("Health: " + c.getHealth());
        Label monsterHealth = (Label) monsterStats.getChildren().get(1);
        monsterHealth.setText("Health: " + m.getHealth());
        Label monsterSpeed = (Label) monsterStats.getChildren().get(3);
        monsterSpeed.setText("Speed: " + m.getTempSpeed());
        Label monsterToughness = (Label) monsterStats.getChildren().get(4);
        monsterToughness.setText("Toughness: " + m.getTempToughness());
    }

    private void death(Character c, Monster m, HBox buttonList, TextArea fightInformation, VBox monsterStats, VBox playerStats) {
        if (c.getHealth() <= 0) {
            c.setLocation(1);
            c.getInventory().clearInventory();
            c.revive();
            populatePane();
            gamePane.getChildren().remove(monsterStats);
            gamePane.getChildren().remove(playerStats);
            gameGridPane.getChildren().remove(buttonList);
            gameGridPane.getChildren().remove(fightInformation);
            gamePane.getChildren().remove(monsterStats);
            gameGridPane.add(promptTextArea, 0, 2);
            gameGridPane.add(interactButton, 2, 2);
        } else if (m.getHealth() <= 0) {
            gamePane.getChildren().remove(monsterStats);
            gamePane.getChildren().remove(playerStats);
            gameGridPane.getChildren().remove(buttonList);
            gameGridPane.getChildren().remove(fightInformation);
            gameGridPane.add(promptTextArea, 0, 2);
            gameGridPane.add(interactButton, 2, 2);

            c.getInventory().addItem(m.getDropItem());
            c.gainExp(m.getExp());

            gamePane.getChildren().remove(monster);
            if (playGame.getRoomList().get(c.getLocation() - 1).getId() < 7) {
                door.setX(250);
                door.setY(0);
            } else {
                door.setX(-50);
                door.setY(200);
            }
            gamePane.getChildren().add(door);
        }
    }

    @FXML
    private void statusUpdate(){
        Character c = playGame.getCharacter();
        playerImageIV.setImage(c.getSprite());
        playerImageIV.setFitHeight(100);
        playerImageIV.setFitWidth(100);

        nameLabel.setText(c.getName());
        healthLabel.setText("HP: " + c.getHealth() + '/' + c.getMaxHealth());
        strengthLabel.setText("Strength: " + c.getStrength());
        toughnessLabel.setText("Toughness: " + c.getToughness());
        speedLabel.setText("Speed: " + c.getSpeed());
    }
}
