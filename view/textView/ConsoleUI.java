package view.textView;

import model.Commands;
import model.Field;
import model.Plate;
import model.Timer;
import view.MessageType;
import view.UserInterface;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class ConsoleUI extends Thread implements UserInterface, DocumentListener {

    private JTextArea fieldArea;
    private JTextField timeArea;
    private JTextArea userCommandArea;
    private JPanel panel;
    private JFrame console;

    private TextView view;

    public ConsoleUI(TextView view){
        this.view = view;
        console = new JFrame("MineSweeper");

        timeArea = new JTextField(50);
        timeArea.setBackground(Color.LIGHT_GRAY);
        timeArea.setFont(new Font("TimesRoman", Font.BOLD, 12));
        timeArea.setEditable(false);
        timeArea.setVisible(true);

        fieldArea = new JTextArea( 11, 50);
        //fieldArea.setBackground(Color.BLACK);
        fieldArea.setFont(new Font("TimesRoman", Font.BOLD, 18));
        fieldArea.setEditable(false);
        fieldArea.setVisible(true);

        userCommandArea = new JTextArea(5,50);
        userCommandArea.setBackground(Color.LIGHT_GRAY);
        userCommandArea.setEditable(true);
        userCommandArea.setVisible(true);
        userCommandArea.getDocument().addDocumentListener(this);

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(timeArea);
        panel.add(fieldArea);
        panel.add(userCommandArea);

        console.setContentPane(panel);
        console.setBounds(10, 10, 600, 400);
        console.setResizable(false);
        console.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        console.setVisible(true);
    }

    @Override
    public void drawField(Field field){
        fieldArea.setRows(field.getHeight() + 1);
        int width = field.getWidth();
        int height = field.getHeight();
        fieldArea.setCaretPosition(0);
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
                        }else if(bombsAround == 0){
                            fieldLine.append("\u2B1C");
                        } else {
                            switch (bombsAround){
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
        fieldArea.setText(fieldLine.toString());
    }

    @Override
    public void drawTime(Timer timer) {
        if(timer == null) {
            timeArea.setText("0 sec.");
        } else {
            timeArea.setText(timer.getSeconds() + " sec.");
        }
    }

    @Override
    public int showMessage(String message, MessageType type){
        if(type == MessageType.info) {
            JOptionPane.showMessageDialog(console, message, "Info", JOptionPane.INFORMATION_MESSAGE);
            return 0;
        }else{
            return JOptionPane.showConfirmDialog(console, message, "Confirm", JOptionPane.YES_NO_CANCEL_OPTION);
        }
    }

    @Override
    public void sendCommand(String command){
        List<Integer> arguments;
        arguments = new ArrayList<>();
        command = command.substring(0, command.length() - 1);
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
            case "pause" -> view.sendCommand(Commands.pause, arguments);
            case "unpause" -> view.sendCommand(Commands.unPause, arguments);
            case "about" -> view.sendCommand(Commands.about, arguments);
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
                        arguments.add(2);
                        break;
                    case "o":
                    case "open":
                        arguments.add(1);
                        break;
                    case "uf":
                    case "unflag":
                        arguments.add(3);
                        break;
                    default:
                        showMessage("Wrong command", MessageType.info);
                        break switch1;
                }
                view.sendCommand(Commands.setPlate, arguments);
            }
        }
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        String command = userCommandArea.getText();
        char[] charCommand = command.toCharArray();
        if(charCommand[command.length() - 1] == '\n') {
            sendCommand(command);
            SwingUtilities.invokeLater(() -> userCommandArea.setText(""));
        }

    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        //nothing
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        //nothing
    }
}
