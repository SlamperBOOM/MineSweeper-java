package view.graphicView;

import model.Commands;
import model.Field;
import model.Timer;
import model.highscores.TableRow;
import view.MessageType;
import view.UserInterface;

import javax.swing.*;
import java.util.List;

public class WindowUI implements UserInterface {
    private GraphicView view;

    public WindowUI(GraphicView view){
        this.view = view;
    }

    @Override
    public void sendCommand(String command){

    }

    @Override
    public void drawField(Field field) {

    }

    @Override
    public void drawTime(Timer timer) {

    }

    @Override
    public void setFieldSize(Field field) {

    }

    @Override
    public int showMessage(String message, MessageType type){

        return 0;
    }

    @Override
    public int showMessage(MessageType type, List<TableRow> scores) {
        return 0;
    }
}
