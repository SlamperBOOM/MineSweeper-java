package view;

import model.*;

import java.util.List;

public interface View{
    Field getField(Model model);

    void sendCommand(Commands command, List<Integer> arguments);

    int showMessage(String message, MessageType type);

    void redrawField(Field field);

    void redrawTime(Timer timer);
}
