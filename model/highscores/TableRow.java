package model.highscores;

public class TableRow {
    private int width;
    private int height;
    private int bombs;
    private long time;

    public TableRow(int width, int height, int bombs, long time){
        this.bombs = bombs;
        this.time = time;
        this.height = height;
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public int getBombs() {
        return bombs;
    }

    public long getTime() {
        return time;
    }

    public int getWidth() {
        return width;
    }

    @Override
    public String toString() {
        return  width + " " + height +
                " " + bombs +
                " " + time;
    }
}
