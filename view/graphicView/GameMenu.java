package view.graphicView;

import javax.swing.*;
import java.awt.event.ActionListener;

public class GameMenu {
    private JMenu menu;
    private JMenuItem newGame;
    private JMenuItem pause;
    private JMenuItem unpause;
    private JMenuItem highScores;
    private JMenuItem about;
    private JMenuItem switchMode;
    private JMenuItem exit;

    private ActionListener ui;

    public GameMenu(ActionListener ui){
        this.ui = ui;
    }

    public void createMenu(){
        menu = new JMenu("Game");

        newGame = createItem("New game", "createNewGame");
        menu.add(newGame);

        pause = createItem("Pause", "pause");
        menu.add(pause);

        unpause = createItem("Continue", "unpause");
        menu.add(unpause);

        highScores = createItem("High scores", "highscores");
        menu.add(highScores);

        about = createItem("About", "about");
        menu.add(about);

        switchMode = createItem("Switch mode", "switchmode");
        menu.add(switchMode);

        menu.addSeparator();

        exit = createItem("Exit", "exit");
        menu.add(exit);
    }

    public JMenuBar getMenuBar(){
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(menu);
        menuBar.setVisible(true);
        return menuBar;
    }

    private JMenuItem createItem(String name, String command){
        JMenuItem item = new JMenuItem(name);
        item.addActionListener(ui);
        item.setActionCommand(command);
        return item;
    }
}
