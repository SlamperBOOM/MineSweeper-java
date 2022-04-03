package model.highscores;

import java.util.*;
import java.io.*;

public class HighScores {
    List<TableRow> scores;

    public HighScores(){
        loadHighScores();
    }

    public void loadHighScores(){
        scores = new ArrayList<TableRow>();
        try (BufferedReader reader = new BufferedReader(new FileReader("src/model/highscores/highscores.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] splitedLine = line.split(" ");
                scores.add(new TableRow(Integer.parseInt(splitedLine[0]), Integer.parseInt(splitedLine[1]),
                        Integer.parseInt(splitedLine[2]), Long.parseLong(splitedLine[3])));
            }
        } catch (IOException e) {
            //just leave scores empty
        }
    }

    public void writeHighScores(){
        scores.sort(new TableRowComparator());
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/model/highscores/highscores.txt"))) {
            for (TableRow row : scores) {
                writer.write(row.toString() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            //do nothing
        }
    }

    public void addHighScore(int width, int height, int bombs, long time){
        scores.add(new TableRow(width, height, bombs, time));
        if(scores.size() > 15){
            scores.sort(new TableRowComparator());
            scores.remove(scores.size() - 1);
        }
    }

    public List<TableRow> getHighScores(){
         return scores;
    }
}
