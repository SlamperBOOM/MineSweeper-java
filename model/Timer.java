package model;

public class Timer extends Thread{
    private long seconds;
    private Model model;
    private boolean stopFlag = true;

    public Timer(Model model){
        seconds = 0;
        this.model = model;
    }

    public Timer(Model model, long seconds){
        this.seconds = seconds;
        this.model = model;
    }

    @Override
    public void run(){
        while(stopFlag) {
            try {
                sleep(200);
                seconds += 200;
                if (seconds % 1000 == 0) {
                    model.notifyView(false);
                }
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void setStopped(){
        stopFlag = false;
    }

    public long getAlternateSeconds(){
        return seconds;
    }

    public long getSeconds(){
        return seconds / 1000;
    }
}
