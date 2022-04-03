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
        gameWindow = new WindowUI(this);
        this.controller = controller;
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
        return gameWindow.showMessage(message, MessageType.info);
    }

    @Override
    public int showMessage(MessageType type, List<TableRow> scores) {
        return 0;
    }

    @Override
    public void redrawField(Field field){

    }

    @Override
    public void redrawTime(Timer timer) {

    }

    @Override
    public void notifyView(Model model, boolean isNewGame){

    }
}
