package view;

import view.graphicView.WindowUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SetModeDialog implements ActionListener {

    private JLabel messageBox;
    private JButton graphicMode;
    private JButton textMode;
    private JDialog dialog;

    WindowUI ui;

    public SetModeDialog(JFrame window, WindowUI ui){
        this.ui = ui;

        dialog = new JDialog(window, true);
        dialog.setTitle("Mode choosing");
        JPanel main = new JPanel(new GridLayout(2, 1));
        JPanel buttons = new JPanel(new GridLayout(1, 2));

        graphicMode = new JButton("Graphic mode");
        graphicMode.addActionListener(this);
        graphicMode.setActionCommand("1");
        graphicMode.setVisible(true);

        textMode = new JButton("Text mode");
        textMode.addActionListener(this);
        textMode.setActionCommand("0");
        textMode.setVisible(true);

        buttons.add(graphicMode);
        buttons.add(textMode);

        messageBox = new JLabel("What mode do you want to play?");
        messageBox.setVisible(true);

        main.add(messageBox);
        main.add(buttons);

        dialog.add(main);
        dialog.setBounds(window.getX() + 20, window.getY() + 20, 250, 150);
    }

    public void showDialog(){
        dialog.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ActionEvent e1 = new ActionEvent(e, 0, "initialize " + e.getActionCommand());
        //SwingUtilities.invokeLater(()-> );
        ui.actionPerformed(e1);
        dialog.setVisible(false);
    }
}
