import javafx.scene.control.Label;

public class ErrorThread extends Thread {
    public void run(){
        while(true){
            continue;
        }
    }

    public void pause(Label message) throws InterruptedException {
        message.setText("Invalid number");
        Thread.sleep(2000);
        message.setText("");
    }
}
