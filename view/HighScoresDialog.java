package view;

import model.highscores.TableRow;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class HighScoresDialog {

    public static void showDialog(List<TableRow> scores, JFrame console){
        JDialog dialog = new JDialog(console);
        JPanel table = new JPanel(new GridLayout(16, 4));
        table.setVisible(true);
        Font head = new Font("TimesRoman", Font.BOLD, 15);
        Font body = new Font("TimesRoman", Font.BOLD, 12);
        JLabel label;
        label = new JLabel();
        table.add(label);
        label = new JLabel("Size");
        label.setFont(head);
        label.setVisible(true);
        table.add(label);
        label = new JLabel("Bombs");
        label.setFont(head);
        label.setVisible(true);
        table.add(label);
        label = new JLabel("Time");
        label.setFont(head);
        label.setVisible(true);
        table.add(label);
        for (int i=0;i<15;++i) {
            if(scores.size() < i + 1){
                label = new JLabel(String.valueOf(i+1));
                label.setFont(body);
                label.setVisible(true);
                table.add(label);
                label = new JLabel();
                label.setVisible(true);
                table.add(label);
                label = new JLabel();
                label.setVisible(true);
                table.add(label);
                label = new JLabel();
                label.setVisible(true);
                table.add(label);
            }else {
                TableRow score = scores.get(i);
                label = new JLabel(String.valueOf(i+1));
                label.setFont(body);
                label.setVisible(true);
                table.add(label);
                label = new JLabel(score.getWidth() + "x" + score.getWidth());
                label.setFont(body);
                label.setVisible(true);
                table.add(label);
                label = new JLabel(String.valueOf(score.getBombs()));
                label.setFont(body);
                label.setVisible(true);
                table.add(label);
                label = new JLabel(String.valueOf(score.getTime()));
                label.setFont(body);
                label.setVisible(true);
                table.add(label);
            }
        }
        JButton okButton = new JButton("Ok");
        okButton.setVisible(true);
        okButton.addActionListener(e -> dialog.setVisible(false));
        okButton.setVisible(true);
        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.add(table);
        main.add(okButton);
        main.setVisible(true);
        dialog.setContentPane(main);
        dialog.setTitle("High Scores");
        dialog.setBounds(console.getX() + 20, console.getY() + 20, 300, 500);
        dialog.setResizable(false);
        dialog.setVisible(true);
    }
}
