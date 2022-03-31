package model;

public class Plate {
    private PlateState state;
    private boolean bomb = false;
    private boolean updated = false;
    private int bombsAround = 0;

    public Plate(){}

    public Plate(PlateState state, Boolean bomb){
        setState(state);
        this.bomb = bomb;
    }

    public boolean isUpdated(){
        if(updated) {
            updated = false;
            return true;
        } else{
            return false;
        }
    }

    public void setBomb(boolean bomb){
        this.bomb = bomb;
    }

    public PlateState getState(){
        return state;
    }

    public int getBombsAround(){
        return bombsAround;
    }

    public void setBombsAround(int count){
        bombsAround = count;
    }

    public void setState(PlateState state){
        this.state = state;
        updated = true;
    }

    public boolean isBomb(){
        return bomb;
    }
}
