package view.graphicView;

import model.highscores.TableRow;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class HighScoresDialog {
    JDialog dialog;
    JPanel table;

    public HighScoresDialog(JFrame parent){
        dialog = new JDialog(parent, true);
        dialog.setTitle("High Scores");
        dialog.setBounds(parent.getX() + 20, parent.getY() + 20, 300, 500);
        dialog.setResizable(false);

        table = new JPanel(new GridLayout(16, 4));
        table.setVisible(true);
    }

    public void createHighScoresView(List<TableRow> scores){
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

        fillTable(scores, body);

        JButton okButton = new JButton("Ok");
        okButton.addActionListener(e -> dialog.setVisible(false));
        okButton.setVisible(true);

        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.add(table);
        main.add(okButton);
        main.setVisible(true);
        dialog.setContentPane(main);
    }

    private void fillTable(List<TableRow> scores, Font bodyStyle){
        JLabel label;
        for (int i=0;i<15;++i) {
            if(scores.size() < i + 1){
                label = new JLabel(String.valueOf(i+1));
                label.setFont(bodyStyle);
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
                label.setFont(bodyStyle);
                label.setVisible(true);
                table.add(label);
                label = new JLabel(score.getWidth() + "x" + score.getHeight());
                label.setFont(bodyStyle);
                label.setVisible(true);
                table.add(label);
                label = new JLabel(String.valueOf(score.getBombs()));
                label.setFont(bodyStyle);
                label.setVisible(true);
                table.add(label);
                label = new JLabel(String.valueOf(score.getTime()));
                label.setFont(bodyStyle);
                label.setVisible(true);
                table.add(label);
            }
        }
    }

    public void showDialog(){
        dialog.setVisible(true);
    }
}
