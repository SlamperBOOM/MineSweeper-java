package view.graphicView;

import model.Commands;
import model.Field;
import model.Plate;
import model.Timer;
import model.highscores.TableRow;
import view.HighScoresDialog;
import view.MessageType;
import view.SetModeDialog;
import view.UserInterface;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WindowUI implements UserInterface, ActionListener {
    private GraphicView view;

    private JFrame window;
    private JPanel topPanel;
    private JPanel contentPane;
    private JTextField timeArea;
    private JTextField bombCountArea;
    private JButton flagSwitch;
    private JPanel fieldArea;
    private JButton[] fieldPlates;
    private GameMenu menu;

    private Image closedIcon;
    private Image openedIcon;
    private Image bombIcon;
    private Image flagIcon;
    private Image oneIcon;
    private Image twoIcon;
    private Image threeIcon;
    private Image fourIcon;
    private Image fiveIcon;
    private Image sixIcon;
    private Image sevenIcon;
    private Image eightIcon; //12
    private Image flagSwitchIcon;
    private Image flagSwitchTransparentIcon;

    private boolean flagState = false;

    public WindowUI(GraphicView view){
        this.view = view;
        window = new JFrame("MineSweeper");

        readImages();

        createEnvironment();

        createField();

        contentPane = new JPanel(new BorderLayout());
        contentPane.add(topPanel, BorderLayout.NORTH);
        contentPane.add(fieldArea, BorderLayout.CENTER);
        contentPane.setVisible(true);

        menu = new GameMenu(this);
        menu.createMenu();

        window.setJMenuBar(menu.getMenuBar());
        window.setContentPane(contentPane);
        window.setBounds(50, 50, 0, 0);
        window.pack();
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setVisible(true);

        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                view.sendCommand(Commands.exit, new ArrayList<>());
                super.windowClosing(e);
            }
        });
    }

    private void readImages(){
        BufferedImage image = null;
        try {
            image = ImageIO.read(this.getClass().getResourceAsStream("resources/facingDown.png"));
            closedIcon = image.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            image = ImageIO.read(this.getClass().getResourceAsStream("resources/bomb.png"));
            bombIcon = image.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            image = ImageIO.read(this.getClass().getResourceAsStream("resources/zero.png"));
            openedIcon = image.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            image = ImageIO.read(this.getClass().getResourceAsStream("resources/flagged.png"));
            flagIcon = image.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            flagSwitchIcon = image.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
            image = ImageIO.read(this.getClass().getResourceAsStream("resources/flaggedTransparent.png"));
            flagSwitchTransparentIcon = image.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
            image = ImageIO.read(this.getClass().getResourceAsStream("resources/1.png"));
            oneIcon = image.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            image = ImageIO.read(this.getClass().getResourceAsStream("resources/2.png"));
            twoIcon = image.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            image = ImageIO.read(this.getClass().getResourceAsStream("resources/3.png"));
            threeIcon = image.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            image = ImageIO.read(this.getClass().getResourceAsStream("resources/4.png"));
            fourIcon = image.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            image = ImageIO.read(this.getClass().getResourceAsStream("resources/5.png"));
            fiveIcon = image.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            image = ImageIO.read(this.getClass().getResourceAsStream("resources/6.png"));
            sixIcon = image.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            image = ImageIO.read(this.getClass().getResourceAsStream("resources/7.png"));
            sevenIcon = image.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            image = ImageIO.read(this.getClass().getResourceAsStream("resources/8.png"));
            eightIcon = image.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        }catch (IOException e){
            showMessage("Cannot find images, please try again", MessageType.info);
            window.dispose();
        }
    }

    private void createEnvironment(){
        timeArea = new JTextField(3);
        timeArea.setBackground(Color.LIGHT_GRAY);
        timeArea.setFont(new Font("TimesRoman", Font.BOLD, 12));
        timeArea.setEditable(false);
        timeArea.setVisible(true);

        flagSwitch = new JButton();
        flagSwitch.setIcon(new ImageIcon(flagSwitchTransparentIcon));
        flagSwitch.setActionCommand("switchflag");
        flagSwitch.addActionListener(this);
        flagSwitch.setPreferredSize(new Dimension(25, 25));
        flagSwitch.setVisible(true);

        bombCountArea = new JTextField(3);
        bombCountArea.setBackground(Color.LIGHT_GRAY);
        bombCountArea.setFont(new Font("TimesRoman", Font.BOLD, 12));
        bombCountArea.setEditable(false);
        bombCountArea.setVisible(true);

        topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        topPanel.add(bombCountArea);
        topPanel.add(flagSwitch);
        topPanel.add(timeArea);
    }

    private void createField(){
        fieldArea = new JPanel(new GridLayout(9, 9, 0, 0));
        fieldPlates = new JButton[9*9];
        for(int i = 0; i < 9; ++i){
            for(int j = 0; j < 9; ++j) {
                fieldPlates[i * 9 + j] = new JButton();
                fieldPlates[i * 9 + j].setIcon(new ImageIcon(closedIcon));
                fieldPlates[i * 9 + j].setPreferredSize(new Dimension(30, 30));
                fieldPlates[i * 9 + j].setVisible(true);
                fieldPlates[i * 9 + j].addActionListener(this);
                fieldPlates[i * 9 + j].setActionCommand("click " + (j+1) + " " + (i+1));
                fieldArea.add(fieldPlates[i * 9 + j]);
            }
        }
    }

    public void setMode(){
        SetModeDialog dialog = new SetModeDialog(window, this);
        dialog.showDialog();
    }

    @Override
    public void sendCommand(String command){
        String[] splitedLine = command.split(" ");
        List<Integer> arguments = new ArrayList<Integer>();
        switch (splitedLine[0]){
            case "newgame" -> {
                arguments.add(Integer.parseInt(splitedLine[1]));
                arguments.add(Integer.parseInt(splitedLine[2]));
                arguments.add(Integer.parseInt(splitedLine[3]));
                view.sendCommand(Commands.newGame, arguments);
            }
            case "pause" -> view.sendCommand(Commands.pause, arguments);
            case "unpause" -> view.sendCommand(Commands.unPause, arguments);
            case "highscores" -> view.sendCommand(Commands.highScores, arguments);
            case "about" -> view.sendCommand(Commands.about, arguments);
            case "exit" -> view.sendCommand(Commands.exit, arguments);
            case "switchmode" -> {
                arguments.add(0);
                view.sendCommand(Commands.switchMode, arguments);
                window.dispose();
            }
            case "initialize" ->{
                arguments.add(Integer.parseInt(splitedLine[1]));
                if(arguments.get(0) == 0) {
                    view.sendCommand(Commands.initialize, arguments);
                    window.dispose();
                }
            }
            case "click" -> {
                arguments.add(Integer.parseInt(splitedLine[1]));
                arguments.add(Integer.parseInt(splitedLine[2]));
                if(flagState){
                    arguments.add(2);
                }else{
                    arguments.add(1);
                }
                view.sendCommand(Commands.setPlate, arguments);
            }
        }
    }

    @Override
    public void drawField(Field field) {
        List<Plate> plates = field.getPlates();
        for(int i = 0; i < field.getHeight() * field.getWidth(); ++i){
            if(plates.get(i).isUpdated()) {
                switch (plates.get(i).getState()) {
                    case OPENED -> {
                        if (plates.get(i).isBomb()) {
                            fieldPlates[i].setIcon(new ImageIcon(bombIcon));
                        } else {
                            switch (plates.get(i).getBombsAround()) {
                                case 0 -> fieldPlates[i].setIcon(new ImageIcon(openedIcon));
                                case 1 -> fieldPlates[i].setIcon(new ImageIcon(oneIcon));
                                case 2 -> fieldPlates[i].setIcon(new ImageIcon(twoIcon));
                                case 3 -> fieldPlates[i].setIcon(new ImageIcon(threeIcon));
                                case 4 -> fieldPlates[i].setIcon(new ImageIcon(fourIcon));
                                case 5 -> fieldPlates[i].setIcon(new ImageIcon(fiveIcon));
                                case 6 -> fieldPlates[i].setIcon(new ImageIcon(sixIcon));
                                case 7 -> fieldPlates[i].setIcon(new ImageIcon(sevenIcon));
                                case 8 -> fieldPlates[i].setIcon(new ImageIcon(eightIcon));
                            }
                        }
                    }
                    case FLAGGED -> fieldPlates[i].setIcon(new ImageIcon(flagIcon));
                    case CLOSED -> fieldPlates[i].setIcon(new ImageIcon(closedIcon));
                }
            }
        }
        bombCountArea.setText(field.getEstimatedBombs().toString());
    }

    @Override
    public void drawTime(Timer timer) {
        if(timer == null) {
            timeArea.setText("0 sec.");
        } else {
            timeArea.setText(timer.getSeconds() + " sec.");
        }
    }

    public void setFieldSize(Field field) {
        int height = field.getHeight();
        int width = field.getWidth();
        contentPane.remove(fieldArea);
        fieldArea = new JPanel(new GridLayout(height, width, 0, 0));
        fieldPlates = new JButton[height * width];
        for(int i = 0; i < height; ++i){
            for(int j = 0; j < width; ++j) {
                int index = i * width + j;
                fieldPlates[index] = new JButton();
                fieldPlates[index].setIcon(new ImageIcon(closedIcon));
                fieldPlates[index].setPreferredSize(new Dimension(30, 30));
                fieldPlates[index].setVisible(true);
                fieldPlates[index].addActionListener(this);
                fieldPlates[index].setActionCommand("click " + (j+1) + " " + (i+1));
                fieldArea.add(fieldPlates[index]);
            }
        }
        contentPane.add(fieldArea);
        window.pack();
    }

    @Override
    public int showMessage(String message, MessageType type){
        if(type == MessageType.info) {
            JOptionPane.showMessageDialog(window, message, "Info", JOptionPane.INFORMATION_MESSAGE);
            return 0;
        }else{
            return JOptionPane.showConfirmDialog(window, message, "Confirm", JOptionPane.YES_NO_CANCEL_OPTION);
        }
    }

    @Override
    public int showMessage(MessageType type, List<TableRow> scores) {
        HighScoresDialog.showDialog(scores, window);
        return 0;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand().split(" ")[0]){
            case "createNewGame" -> {
                NewGameDialog newGame = new NewGameDialog(window, this);
                newGame.showDialog();
            }
            case "switchflag" -> {
                if(flagState){
                    flagSwitch.setIcon(new ImageIcon(flagSwitchTransparentIcon));
                    flagState = false;
                }else{
                    flagSwitch.setIcon(new ImageIcon((flagSwitchIcon)));
                    flagState = true;
                }
            }
            default -> sendCommand(e.getActionCommand());
        }
    }
}
