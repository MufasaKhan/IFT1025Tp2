package server;

import javafx.scene.control.Button;



@FunctionalInterface
public interface EventHandler {

    void handle(String cmd, String arg);



}
