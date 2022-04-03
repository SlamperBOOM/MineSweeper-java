package view;

import model.Field;
import model.Timer;
import model.highscores.TableRow;

import java.util.List;


public interface UserInterface {
    int showMessage(String message, MessageType type);

    int showMessage(MessageType type, List<TableRow> scores);

    void sendCommand(String command);

    void drawField(Field field);

    void drawTime(Timer timer);

    void setFieldSize(Field field);
}
