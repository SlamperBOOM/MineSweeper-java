package view.textView;

import controller.Controller;
import model.Commands;
import model.Field;
import model.Model;
import model.Timer;
import model.highscores.TableRow;
import view.MessageType;
import view.Subscriber;
import view.View;

import java.util.*;

public class TextView implements View, Subscriber {
    private ConsoleUI gameConsole;
    private Controller controller;

    public TextView(Controller controller){
        this.controller = controller;
        gameConsole = new ConsoleUI(this);
    }

    @Override
    public void notifyView(Model model, boolean isNewGame) {
        redrawTime(model.getTimer());
        if(isNewGame || model.getField().isUpdated() ){
            redrawField(model.getField());
        }
    }

    @Override
    public Field getField(Model model) {
        return model.getField();
    }

    @Override
    public void setMode() {
        //nothing, because initially we create graphic view and call this method from there
    }

    @Override
    public void init() {
        gameConsole.input();
    }

    @Override
    public void sendCommand(Commands command, List<Integer> arguments) {
       controller.processCommand(command, arguments);
    }

    @Override
    public int showMessage(String message, MessageType type) {
        return gameConsole.showMessage(message, type);
    }

    @Override
    public int showMessage(MessageType type, List<TableRow> scores) {
        return gameConsole.showMessage(type, scores);
    }

    @Override
    public void redrawField(Field field) {
        gameConsole.drawField(field);
    }

    @Override
    public void redrawTime(Timer timer) {
        gameConsole.drawTime(timer);
    }
}
