package model.highscores;

import java.util.Comparator;

public class TableRowComparator implements Comparator<TableRow> {

    @Override
    public int compare(TableRow o1, TableRow o2) {
        if(o1.getBombs() == o2.getBombs() &&
        o1.getHeight() == o2.getHeight() &&
        o1.getBombs() == o2.getBombs() &&
        o1.getTime() == o2.getTime()){
            return 0;
        }
        if(o1.getWidth()*o1.getHeight() > o2.getHeight()* o2.getWidth()){
            if(o1.getBombs() > o2.getBombs()){
                if(o1.getTime() < o2.getTime()){
                    return 1;
                }else{
                    return -1;
                }
            }else{
                return - 1;
            }
        }else{
            return -1;
        }
    }
}
