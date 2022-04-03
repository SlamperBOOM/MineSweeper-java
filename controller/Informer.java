package controller;

import model.highscores.TableRow;
import view.MessageType;

import java.util.List;

public interface Informer {
    int showMessage(String message, MessageType type);

    int showMessage(MessageType type, List<TableRow> scores);

    String getAbout();
}
