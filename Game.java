import controller.Controller;

public class Game {
    private Controller controller;

    public Game(){
        controller = new Controller();
    }

    public void startGame(){
        controller.init();
    }
}
