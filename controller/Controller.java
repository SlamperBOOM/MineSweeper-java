package controller;

import model.about.GraphicAbout;
import model.about.TextAbout;
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

    public Controller(){
        view = new GraphicView(this);
        model = new Model((Subscriber) view, this);
        model.setAbout(new GraphicAbout());
        view.setMode();
        init();
    }

    public void init(){
        File save = new File("src/model/savedgame.txt");
        if(save.exists()) {
            if (showMessage("Do you want to continue?", MessageType.yesNo) == 0) {
                model.loadGame(false);
            }
        }
        view.init();
    }

    public void processCommand(Commands command, List<Integer> arguments){
        if(command == Commands.switchMode){
            model.saveGame();
            if(arguments.get(0) == 1) {
                view = new GraphicView(this);
                model.setSubscriber((Subscriber) view);
                model.setAbout(new GraphicAbout());
            }else{
                view = new TextView(this);
                model.setSubscriber((Subscriber) view);
                model.setAbout(new TextAbout());
                view.init();
            }
            model.loadGame(true);
        }else if(command == Commands.initialize){
            if(arguments.get(0) == 1){
                view = new GraphicView(this);
                model.setSubscriber((Subscriber) view);
                model.setAbout(new GraphicAbout());
            }else{
                view = new TextView(this);
                model.setSubscriber((Subscriber) view);
                model.setAbout(new TextAbout());
            }
        } else {
            model.processCommand(command, arguments);
        }
    }

    @Override
    public int showMessage(String message, MessageType type){
        return view.showMessage(message, type);
    }

    @Override
    public int showMessage(MessageType type, List<TableRow> scores) {
        return view.showMessage(type, scores);
    }
}
