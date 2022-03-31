package controller;

import controller.about.About;
import controller.about.TextAbout;
import model.Commands;
import view.MessageType;
import view.Subscriber;
import view.View;
import view.graphicView.GraphicView;
import model.Model;
import view.textView.TextView;

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
        if(showMessage("Do you want to play in Graphic mode?", MessageType.yesNo) == 0){
            view = new GraphicView(this);
            model = new Model((Subscriber) view, this);
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
    public String getAbout() {
        return about.getAbout();
    }

}
