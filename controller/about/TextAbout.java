package controller.about;

import controller.about.About;

public class TextAbout implements About {
    String newGameDescription;
    String highScoresDescription;
    String pauseDescription;
    String setPlateDescription;

    public TextAbout(){
        newGameDescription = "-newgame *width* *height* *bombcount*: use this command to start new game. " +
                "Field size could be between 5x5 and 30x30 and bomb count couldn't be over 25% of field size.\n   " +
                "You also can use newgame without arguments to choose default field (9x9 with 10 bombs);";
        highScoresDescription = "-highscores: shows available highscores;";
        pauseDescription = "-pause/unpause: pausing/unpausing the game;";
        setPlateDescription = "-*x* *y* *open/flag/unflag*: sets a plate on field in specified status(opened, flagged, closed). " +
                "You can also use o/f/uf instead.";
    }

    @Override
    public String getAbout() {
        String message = "";
        message += "In game commands:\n  ";
        message += newGameDescription + "\n  " +
                pauseDescription + "\n  " +
                setPlateDescription + "\n";
        message += "Not in game commands:\n  ";
        message += highScoresDescription;
        return message;
    }
}
