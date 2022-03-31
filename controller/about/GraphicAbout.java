package controller.about;

public class GraphicAbout implements About{
    String newGameDescription;
    String highScoresDescription;
    String pauseDescription;
    String setPlateDescription;

    public GraphicAbout(){
        newGameDescription = "newgame *width* *height* *bombcount*: use this command to start new game. " +
                "You also can use newgame without arguments to choose default field";
        highScoresDescription = "highscores: shows available highscores";
        pauseDescription = "pause/unpause: pausing/unpausing the game";
        setPlateDescription = "*x* *y* *open/flag/unflag*: sets a plate on field in specified status(opened, flagged, closed)";
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
