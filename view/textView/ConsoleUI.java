package view.textView;

import model.Commands;
import model.Field;
import model.Plate;
import model.Timer;
import model.highscores.TableRow;
import view.MessageType;
import view.UserInterface;

import java.util.ArrayList;
import java.util.List;


public class ConsoleUI implements UserInterface {
    private StringBuilder timeArea;
    private int bombCount;
    private StringBuilder fieldArea = new StringBuilder();
    private InputThread inputThread;

    private TextView view;

    public ConsoleUI(TextView view){
        this.view = view;
        inputThread = new InputThread(this);
    }

    private void drawGameField(){
        String gameView = bombCount + " " +
                timeArea + "\n" +
                fieldArea + "\n";
        System.out.print(gameView.toString());
    }

    private void drawMessage(String message){
        System.out.print(message + "\n");
    }

    @Override
    public void drawField(Field field){
        int width = field.getWidth();
        int height = field.getHeight();
        bombCount = field.getEstimatedBombs();
        List<Plate> plateList = field.getPlates();
        StringBuilder fieldLine = new StringBuilder();
        for(int y=0;y<height;++y){
            for(int x=0;x<width;++x){
                switch (plateList.get(y*width + x).getState()){
                    case CLOSED -> fieldLine.append("\u2B1B");
                    case OPENED -> {
                        int bombsAround = plateList.get(y*width + x).getBombsAround();
                        if(plateList.get(y*width + x).isBomb()){
                            fieldLine.append("\u24B7");
                        }else {
                            switch (bombsAround){
                                case 0 -> fieldLine.append("\u2B1C");
                                case 1 -> fieldLine.append("\u2460");
                                case 2 -> fieldLine.append("\u2461");
                                case 3 -> fieldLine.append("\u2462");
                                case 4 -> fieldLine.append("\u2463");
                                case 5 -> fieldLine.append("\u2464");
                                case 6 -> fieldLine.append("\u2465");
                                case 7 -> fieldLine.append("\u2466");
                                case 8 -> fieldLine.append("\u2467");
                            }
                        }
                    }
                    case FLAGGED -> fieldLine.append("\u24BB");
                }
            }
            if(y <= height - 1) {
                fieldLine.append("\n");
            }
        }
        fieldArea = fieldLine;
        drawGameField();
    }

    @Override
    public void drawTime(Timer timer) {
        if(timer == null) {
            timeArea = new StringBuilder("0 sec.");
        } else {
            timeArea = new StringBuilder(timer.getSeconds() + " sec.");
        }
    }

    @Override
    public int showMessage(String message, MessageType type){
        if(type == MessageType.info) {
            drawMessage(message);
            return 0;
        }else{
            drawMessage(message + "(yes/no)");
            String option;
            while(true){
                option = inputThread.read();
                if(option.equals("y") || option.equals("yes")){
                    return 0;
                } else if(option.equals("n") || option.equals("no")){
                    return 1;
                } else{
                    showMessage("Choose yes/no (y/n)", MessageType.info);
                }
            }
        }
    }

    @Override
    public int showMessage(MessageType type, List<TableRow> scores) {
        System.out.println("  " + " Size " + " Bombs " + " Time");
        for(int i=0;i<15;++i){
            if(scores.size() > i) {
                TableRow row = scores.get(i);
                System.out.print(i + 1);
                if ((i < 9)) {
                    System.out.print("  ");
                } else {
                    System.out.print(" ");
                }
                System.out.print(row.getWidth() + "x" + row.getHeight());
                System.out.print("  " + row.getBombs() + "  ");
                System.out.println(row.getTime());
            }else{
                System.out.println(i+1);
            }
        }
        return 0;
    }

    @Override
    public void sendCommand(String command){
        List<Integer> arguments;
        arguments = new ArrayList<>();
        command = command.toLowerCase();
        String[] splitedCommand = command.split(" ");
        switch1:
        switch (splitedCommand[0]){
            case "newgame" -> {
                if(splitedCommand.length == 4) {
                    try {
                        arguments.add(Integer.valueOf(splitedCommand[1]));
                        arguments.add(Integer.valueOf(splitedCommand[2]));
                        arguments.add(Integer.valueOf(splitedCommand[3]));
                    }catch (NumberFormatException e){
                        showMessage("Wrong command", MessageType.info);
                    }
                    view.sendCommand(Commands.newGame, arguments);
                }else if(splitedCommand.length == 1){
                    view.sendCommand(Commands.newGame, arguments);
                }else{
                    showMessage("Add more arguments or use \"newgame\" instead", MessageType.info);
                }
            }
            case "pause" -> {
                view.sendCommand(Commands.pause, arguments);
                drawGameField();
            }
            case "unpause" -> view.sendCommand(Commands.unPause, arguments);
            case "about" -> view.sendCommand(Commands.about, arguments);
            case "highscores" -> view.sendCommand(Commands.highScores, arguments);
            case "exit" -> view.sendCommand(Commands.exit, arguments);
            case "switchmode" -> {
                arguments.add(1);
                view.sendCommand(Commands.switchMode, arguments);
                inputThread.stop();
            }
            default -> {
                if(splitedCommand.length < 3){
                    showMessage("Wrong command", MessageType.info);
                    break;
                }
                try {
                    arguments.add(Integer.valueOf(splitedCommand[0]));
                    arguments.add(Integer.valueOf(splitedCommand[1]));
                }catch (NumberFormatException e){
                    showMessage("Wrong command", MessageType.info);
                }
                switch (splitedCommand[2]) {
                    case "f":
                    case "flag":
                    case "uf":
                    case "unflag":
                        arguments.add(2);
                        break;
                    case "o":
                    case "open":
                        arguments.add(1);
                        break;
                    default:
                        showMessage("Wrong command", MessageType.info);
                        break switch1;
                }
                view.sendCommand(Commands.setPlate, arguments);
            }
        }
    }

    public void input(){
        inputThread.start();
    }
}
