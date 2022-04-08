package model.about;

public class GraphicAbout implements About{
    String newGameDescription;
    String highScoresDescription;
    String pauseDescription;

    public GraphicAbout(){
        newGameDescription = "New Game: opens dialog window where you can set parameters for new game. " +
        "You can also leave fields empty to start default game.";
        highScoresDescription = "High Scores: shows available highscores";
        pauseDescription = "Pause/Continue: pausing/continuing the game";
    }
    @Override
    public String getAbout() {
        String message = "";
        message += "In game commands:\n  ";
        message += newGameDescription + "\n  " +
                pauseDescription + "\n";
        message += "Not in game commands:\n  ";
        message += highScoresDescription;
        return message;
    }
}
