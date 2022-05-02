package view;

import model.Model;

public interface Subscriber {
    void notifyView(Model model, boolean isNewGame, boolean isTime);
}
