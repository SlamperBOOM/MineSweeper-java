package controller;

import controller.about.About;
import controller.about.GraphicAbout;
import controller.about.TextAbout;
import model.Commands;
import model.highscores.TableRow;
import view.MessageType;
import view.Subscriber;
import view.View;
import view.graphicView.GraphicView;
import model.Model;
import view.textView.TextView;

import java.io.File;
import java.util.List;

public class Controller implements Informer{
    private Model model;
    private View view;
    private About about;

    public Controller(){
        view = new TextView(this);
        model = new Model((Subscriber) view, this);
        about = new TextAbout();
    }

    public void init(){
        File save = new File("src/model/savedgame.txt");
        if(save.exists()) {
            if (showMessage("Do you want to continue?", MessageType.yesNo) == 0) {
                model.loadGame();
            }
        }
    }

    public void processCommand(Commands command, List<Integer> arguments){
        model.processCommand(command, arguments);
    }

    @Override
    public int showMessage(String message, MessageType type){
        return view.showMessage(message, type);
    }

    @Override
    public int showMessage(MessageType type, List<TableRow> scores) {
        return view.showMessage(type, scores);
    }

    @Override
    public String getAbout() {
        return about.getAbout();
    }

}
