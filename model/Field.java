package model;

import java.util.*;

public class Field {
    private List<Plate> field;
    private int height;
    private int width;
    private int bombCount;
    private int setBombs;
    private boolean updated = false;

    private final int[] yShift = {-1, 0, 1, 1, 1, 0, -1, -1};
    private final int[] xShift = {-1, -1, -1, 0, 1, 1, 1, 0};

    public Field(){
        field = new ArrayList<Plate>(9*9);
        height = 9;
        width = 9;
        bombCount = 10;
        setBombs = 0;
        for(int i=0; i<height*width; ++i){
            field.add(new Plate(PlateState.CLOSED, false));
        }
        updated = true;
    }

    public Field(int width, int height, int bombCount){
        field = new ArrayList<Plate>(width * height);
        this.height = height;
        this.width = width;
        this.bombCount = bombCount;
        for(int i=0; i<height*width; ++i){
            field.add(new Plate());
        }
        updated = true;
    }

    public int getBombCount(){
        return bombCount;
    }

    public int getEstimatedBombs() {
        return bombCount - setBombs;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public boolean isUpdated(){
        if(updated) {
            updated = false;
            return true;
        } else{
            return false;
        }
    }

    public void fillField(int setX, int setY){
        double bombProbability = (double)bombCount/(height*width);
        int bombs = 0;
        Random random = new Random();
        while(bombs < bombCount) {
            for (int y = 0; y < height; ++y) {
                for(int x=0; x<width; ++x) {
                    if (setX == x && setY == y) {
                        continue;
                    }
                    if (random.nextDouble() % 1 <= bombProbability && bombs < bombCount) {
                        field.get(y * width + x).setBomb(true);
                        bombs++;
                    }
                }
            }
        }

        for(int y=0; y<height; ++y){
            for(int x=0; x<width; ++x){

                int bombsAround = 0;
                for(int index=0; index<8; ++index){
                    if(y + yShift[index] >= 0 && y + yShift[index] < height && x + xShift[index] >= 0 && x + xShift[index] < width &&
                            field.get((y + yShift[index]) * height + x + xShift[index]).isBomb()){
                        bombsAround++;
                    }

                }
                field.get(y*width + x).setBombsAround(bombsAround);
            }
        }
        updated = true;
    }

    public void openField(){
        for(int i=0; i<field.size(); ++i){
            if(field.get(i).getState() != PlateState.FLAGGED){
                field.get(i).setState(PlateState.OPENED);
            }
        }
        updated = true;
    }

    public List<Plate> getPlates(){
        return field;
    }

    public int setPlate(int x, int y, PlateState state, boolean recursive){
        switch (state){
            case OPENED -> {
                if(recursive && field.get(y * width + x).isBomb()){
                    return 0;
                } else if(!recursive && field.get(y * width + x).isBomb()) {
                    return 1;
                }if(field.get(y * width + x).getState() != PlateState.FLAGGED) {
                    field.get(y * width + x).setState(state);
                }
                int flagsAround = 0;
                for(int index=0; index<8; ++index) {
                    if(y + yShift[index] >= 0 && y + yShift[index] < height && x + xShift[index] >= 0 && x + xShift[index] < width &&
                            field.get((y + yShift[index]) * height + x + xShift[index]).getState() == PlateState.FLAGGED){
                        flagsAround++;
                    }
                }
                if(flagsAround == field.get(y * width + x).getBombsAround()){
                    for(int index=0; index<8; ++index) {
                        if(y + yShift[index] >= 0 && y + yShift[index] < height && x + xShift[index] >= 0 && x + xShift[index] < width &&
                                field.get((y + yShift[index]) * height + x + xShift[index]).getState() != PlateState.FLAGGED &&
                                field.get((y + yShift[index]) * height + x + xShift[index]).getState() != PlateState.OPENED){
                            if(setPlate(x + xShift[index], y + yShift[index], PlateState.OPENED, false) == 1){
                                return 1;
                            }
                        }
                    }
                } else if(field.get(y * width + x).getBombsAround() == 0) {
                    for(int index=0; index<8; ++index) {
                        if(y + yShift[index] >= 0 && y + yShift[index] < height && x + xShift[index] >= 0 && x + xShift[index] < width &&
                                field.get((y + yShift[index]) * height + x + xShift[index]).getState() != PlateState.FLAGGED &&
                                field.get((y + yShift[index]) * height + x + xShift[index]).getState() != PlateState.OPENED){
                            setPlate(x + xShift[index], y + yShift[index], PlateState.OPENED, true);
                        }
                    }
                }
            }
            case FLAGGED -> {
                if( field.get(y * width + x).getState() != PlateState.OPENED) {
                    setBombs++;
                    field.get(y * width + x).setState(state);
                }
            }
            case CLOSED -> {
                if( field.get(y * width + x).getState() != PlateState.OPENED) {
                    setBombs--;
                    field.get(y * width + x).setState(state);
                }
            }
        }
        updated = true;
        return 0;
    }
}
