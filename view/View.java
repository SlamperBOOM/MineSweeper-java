package view;

import model.*;
import model.highscores.TableRow;

import java.util.List;

public interface View{
    Field getField(Model model);

    void setMode();

    void init();

    void sendCommand(Commands command, List<Integer> arguments);

    int showMessage(String message, MessageType type);

    int showMessage(MessageType type, List<TableRow> scores);

    void redrawField(Field field);

    void redrawTime(Timer timer);
}
