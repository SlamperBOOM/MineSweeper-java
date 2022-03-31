package model;

import controller.Informer;
import view.MessageType;
import view.Subscriber;
import java.util.*;

public class Model {
    private Field field;
    private Subscriber subscriber;
    private Informer informer;
    private boolean pause = true;
    private Timer timer;
    private boolean isNewGame = true;
    private boolean isGame = false;
    private long seconds;

    public Model(Subscriber subscriber, Informer informer){
        field = new Field();
        this.subscriber = subscriber;
        this.informer = informer;
        notifyView();
    }

    public void setNewField(int width, int height, int bombCount){
        field = new Field(width, height, bombCount);
    }

    public Field getField(){
        return field;
    }

    public Timer getTimer(){
        return timer;
    }

    public void notifyView(){
        subscriber.notifyView(this);
    }

    public void processCommand(Commands command, List<Integer> arguments){
        if(isGame) {
            switch (command) {
                case newGame -> {
                    timer = new Timer(this);
                    pause = false;
                    isNewGame = true;
                    isGame = true;
                    if(arguments.size() != 0) {
                        field = new Field(arguments.get(0),arguments.get(1), arguments.get(2));
                    }else{
                        field = new Field();
                    }
                    notifyView();
                }
                case setPlate -> {
                    if (isNewGame) {
                        field.fillField(arguments.get(0) - 1, arguments.get(1) - 1);
                        isNewGame = false;
                        timer.start();
                    }
                    int status = setPlate(arguments.get(0) - 1, arguments.get(1) - 1, arguments.get(2));
                    if (status == 1) {
                        field.openField();
                        processCommand(Commands.pause, arguments);
                        informer.showMessage("You lost", MessageType.info);
                    } else if (field.getEstimatedBombs() == 0) {
                        if (checkForWin()) {
                            processCommand(Commands.pause, arguments);
                            informer.showMessage("You won!", MessageType.info);
                            isGame = false;
                        }
                    }
                    notifyView();
                }
                case pause -> {
                    if(!pause) {
                        pause = true;
                        seconds = timer.getAlternateSeconds();
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
                default -> informer.showMessage("You can't use this command in game", MessageType.info);

            }
        }else{
            switch (command){
                case newGame -> {
                    timer = new Timer(this);
                    pause = false;
                    isNewGame = true;
                    isGame = true;
                    field = new Field();
                    notifyView();
                }
                case highScores -> {

                }
                case about -> informer.showMessage(informer.getAbout(), MessageType.info);
            }
        }
    }

    private int setPlate(int x, int y, int status){
        if(!pause) {
            switch (status){
                case 0, 3 -> {return field.setPlate(x, y, PlateState.CLOSED, false);}
                case 1 -> {return field.setPlate(x, y, PlateState.OPENED, false);}
                case 2 -> {return field.setPlate(x, y, PlateState.FLAGGED, false);}
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
