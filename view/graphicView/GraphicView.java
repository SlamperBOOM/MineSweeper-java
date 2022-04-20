package view.graphicView;

import controller.Controller;
import model.Commands;
import model.Field;
import model.Model;
import model.Timer;
import model.highscores.TableRow;
import view.MessageType;
import view.View;
import view.Subscriber;

import java.util.*;


public class GraphicView implements View, Subscriber{
    private WindowUI gameWindow;
    private Controller controller;

    public GraphicView(Controller controller){
        this.controller = controller;
        gameWindow = new WindowUI(this);
    }

    @Override
    public void setMode(){
        gameWindow.setMode();
    }

    @Override
    public void init() {
        controller.init();
    }

    @Override
    public Field getField(Model model){
        return model.getField();
    }

    @Override
    public void sendCommand(Commands command, List<Integer> arguments){
        controller.processCommand(command, arguments);
    }

    @Override
    public int showMessage(String message, MessageType type){
        return gameWindow.showMessage(message, type);
    }

    @Override
    public int showMessage(MessageType type, List<TableRow> scores) {
        return gameWindow.showMessage(type, scores);
    }

    @Override
    public void redrawField(Field field){
        gameWindow.drawField(field);
    }

    @Override
    public void redrawTime(Timer timer) {
        gameWindow.drawTime(timer);
    }

    @Override
    public void notifyView(Model model, boolean isNewGame){
        if(isNewGame){
            gameWindow.setFieldSize(model.getField());
        }
        if(model.getField().isUpdated()) {
            redrawField(model.getField());
        }
        redrawTime(model.getTimer());
    }
}
