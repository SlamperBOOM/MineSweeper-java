package view;

import model.Field;
import model.Timer;


public interface UserInterface {
    int showMessage(String message, MessageType type);

    void sendCommand(String command);

    void drawField(Field field);

    void drawTime(Timer timer);
}
