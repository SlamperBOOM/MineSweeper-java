package controller;

import view.MessageType;

public interface Informer {
    public int showMessage(String message, MessageType type);

    public String getAbout();
}
