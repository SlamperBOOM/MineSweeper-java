package model;

import controller.Informer;
import model.highscores.HighScores;
import view.MessageType;
import view.Subscriber;

import java.io.File;
import java.util.*;

public class Model {
    private Field field;
    private Subscriber subscriber;
    private Informer informer;
    private Timer timer;
    private HighScores highScores;
    private boolean pause = true;
    private boolean isNewGame = true;
    private boolean isGame = false;
    private long seconds;

    public Model(Subscriber subscriber, Informer informer){
        field = new Field();
        this.subscriber = subscriber;
        this.informer = informer;
        highScores = new HighScores();
        notifyView(true);
    }

    public Field getField(){
        return field;
    }

    public Timer getTimer(){
        return timer;
    }

    public void setSeconds(long seconds){
        this.seconds = seconds;
    }

    public void notifyView(boolean isNewGame){
        subscriber.notifyView(this, isNewGame);
    }

    public void setSubscriber(Subscriber view){
        subscriber = view;
        notifyView(false);
    }

    public void saveGame(){
        if(timer == null){
            GameSaving.saveGame(field, 0);
        }else{
            GameSaving.saveGame(field, timer.getMilliseconds());
        }

    }

    public void loadGame(){
        field = GameSaving.loadGame(this);
        timer = new Timer(this, seconds);
        pause = false;
        isNewGame = false;
        isGame = true;
        timer.start();
        notifyView(true);
    }

    public void processCommand(Commands command, List<Integer> arguments){
        if(isGame) {
            switch (command) {
                case newGame -> {
                    new File("src/model/savedgame.txt").delete();
                    if(arguments.size() != 0) {
                        if(arguments.get(0) < 5 || arguments.get(0) > 30 ||
                        arguments.get(1) < 5 || arguments.get(1) > 30 ||
                        arguments.get(2) > arguments.get(0)*arguments.get(1) * 0.25){
                            informer.showMessage("Read about newgame arguments in \"about\"", MessageType.info);
                        }else {
                            field = new Field(arguments.get(0), arguments.get(1), arguments.get(2));
                        }
                    }else{
                        field = new Field();
                    }
                    timer = new Timer(this);
                    pause = false;
                    isNewGame = true;
                    isGame = true;
                    notifyView(true);
                }
                case setPlate -> {
                    if (isNewGame) {
                        field.fillField(arguments.get(0) - 1, arguments.get(1) - 1);
                        isNewGame = false;
                        timer.start();
                    }
                    int status = setPlate(arguments.get(0) - 1, arguments.get(1) - 1, arguments.get(2));
                    if (status == 1) {
                        new File("src/model/savedgame.txt").delete();
                        field.openField();
                        processCommand(Commands.pause, arguments);
                        isGame = false;
                        informer.showMessage("You lost", MessageType.info);
                    } else if (field.getEstimatedBombs() == 0) {
                        if (checkForWin()) {
                            processCommand(Commands.pause, arguments);
                            informer.showMessage("You won!", MessageType.info);
                            isGame = false;
                            new File("src/model/savedgame.txt").delete();
                            highScores.addHighScore(field.getWidth(), field.getHeight(), field.getBombCount(), seconds / 1000);
                        }
                    }
                    notifyView(false);
                }
                case pause -> {
                    if(!pause) {
                        pause = true;
                        seconds = timer.getMilliseconds();
                        timer.setStopped();
                    }
                }
                case unPause -> {
                    if(pause) {
                        pause = false;
                        timer = new Timer(this, seconds);
                        timer.start();
                    }
                }
                case exit -> {
                    if(!isNewGame) {
                        saveGame();
                    }
                    highScores.writeHighScores();
                    System.exit(0);
                }
                default -> informer.showMessage("You can't use this command in game", MessageType.info);

            }
        }else{
            switch (command){
                case newGame -> {
                    new File("src/model/savedgame.txt").delete();
                    if(arguments.size() != 0) {
                        if(arguments.get(0) < 5 || arguments.get(0) > 30 ||
                                arguments.get(1) < 5 || arguments.get(1) > 30 ||
                                arguments.get(2) > arguments.get(0)*arguments.get(1) * 0.25){
                            informer.showMessage("Read about newgame arguments in \"about\"", MessageType.info);
                            break;
                        }else {
                            field = new Field(arguments.get(0), arguments.get(1), arguments.get(2));
                        }
                    }else{
                        field = new Field();
                    }
                    timer = new Timer(this);
                    pause = false;
                    isNewGame = true;
                    isGame = true;
                    notifyView(true);
                }
                case highScores -> informer.showMessage(MessageType.highScores, highScores.getHighScores());
                case about -> informer.showMessage(informer.getAbout(), MessageType.info);
                case exit -> {
                    highScores.writeHighScores();
                    System.exit(0);
                }
            }
        }
    }

    private int setPlate(int x, int y, int status){
        if(!pause) {
            switch (status){
                case 0 -> {return field.setPlate(x, y, PlateState.CLOSED, false);}
                case 1 -> {
                    if(field.getPlates().get(y * field.getWidth() + x).getState() != PlateState.FLAGGED)
                    return field.setPlate(x, y, PlateState.OPENED, false);
                }
                case 2 -> {
                    if(field.getPlates().get(y * field.getWidth() + x).getState() == PlateState.FLAGGED) {
                        return field.setPlate(x, y, PlateState.CLOSED, false);
                    }else{
                        return field.setPlate(x, y, PlateState.FLAGGED, false);
                    }
                }
            }
        }
        return 0;
    }

    private boolean checkForWin(){
        for(int i=0; i<field.getPlates().size(); ++i){
            if(field.getPlates().get(i).getState() == PlateState.CLOSED){
                return false;
            }
        }
        return true;
    }
}
