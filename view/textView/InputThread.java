package view.textView;

import java.util.Scanner;

public class InputThread extends Thread{
    Scanner input;
    private ConsoleUI ui;

    public InputThread(ConsoleUI ui){
        input = new Scanner(System.in);
        this.ui = ui;
    }

    public String read(){
        return input.nextLine();
    }

    @Override
    public void run(){
        while(true){
            String command = input.nextLine();
            ui.sendCommand(command);
        }
    }
}
