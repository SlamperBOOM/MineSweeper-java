package model;

import java.io.*;
import java.util.List;

public class GameSaving {
    public static void saveGame(Field field, long seconds){
        try(Writer writer = new OutputStreamWriter(new FileOutputStream("src/model/savedgame.txt"))){
            writer.write(field.getWidth() + " " + field.getHeight() + " " + field.getBombCount() + "\n");
            writer.write(field.getEstimatedBombs() + " " + seconds + "\n");
            for(Plate plate: field.getPlates()){
                writer.write(plate.getState().toString() + " " + plate.getBombsAround() + " " + plate.isBomb() + "\n");
            }
        }catch(IOException e){

        }
    }

    public static Field loadGame(Model model){
        Field field = new Field();
        try(BufferedReader reader = new BufferedReader(new FileReader("src/model/savedgame.txt"))){
            String[] splitedLine = reader.readLine().split(" ");
            field = new Field(Integer.parseInt(splitedLine[0]), Integer.parseInt(splitedLine[1]), Integer.parseInt(splitedLine[2]));
            splitedLine = reader.readLine().split(" ");
            List<Plate> plates = field.getPlates();
            field.setEstimatedBombs(Integer.parseInt(splitedLine[0]));
            model.setSeconds(Long.parseLong(splitedLine[1]));
            for(Plate plate : plates){
                splitedLine = reader.readLine().split(" ");
                switch (splitedLine[0]){
                    case "CLOSED" -> plate.setState(PlateState.CLOSED);
                    case "FLAGGED" -> plate.setState(PlateState.FLAGGED);
                    case "OPENED" -> plate.setState(PlateState.OPENED);
                }
                plate.setBombsAround(Integer.parseInt(splitedLine[1]));
                plate.setBomb(splitedLine[2].equals("true"));
            }
        }catch (IOException e){

        }
        return field;
    }
}
