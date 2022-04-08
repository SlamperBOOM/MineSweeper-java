package view.graphicView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewGameDialog implements ActionListener {
    JDialog dialog;
    private JTextField width;
    private JTextField height;
    private JTextField bombs;
    private WindowUI ui;

    public NewGameDialog(JFrame window, WindowUI ui){
        this.ui = ui;
        dialog = new JDialog(window, true);
        JPanel parameters = new JPanel(new GridLayout(3, 2));
        width = new JTextField(10);
        width.setVisible(true);
        height = new JTextField(10);
        height.setVisible(true);
        bombs = new JTextField(10);
        bombs.setVisible(true);
        parameters.add(new JLabel("Width"));
        parameters.add(width);
        parameters.add(new JLabel("Height"));
        parameters.add(height);
        parameters.add(new JLabel("Bombs"));
        parameters.add(bombs);
        parameters.setVisible(true);

        JButton startButton = new JButton("Start");
        startButton.addActionListener(this);
        startButton.setActionCommand("newgame");
        startButton.setVisible(true);

        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));

        main.add(parameters);
        main.add(startButton);
        dialog.setContentPane(main);
        dialog.setTitle("New game");
        dialog.setBounds(window.getX() + 20, window.getY() + 20, 200, 200);
        dialog.setResizable(false);
        dialog.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
    }

    public void showDialog(){
        dialog.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(width.getText().equals("")){
            width.setText("9");
        }
        if(height.getText().equals("")){
            height.setText("9");
        }
        if(bombs.getText().equals("")){
            bombs.setText("10");
        }
        ActionEvent e1 = new ActionEvent(e, 0, "newgame " + width.getText() + " "
                + height.getText() + " " + bombs.getText());
        SwingUtilities.invokeLater(() -> ui.actionPerformed(e1));
        dialog.setVisible(false);
    }
}
