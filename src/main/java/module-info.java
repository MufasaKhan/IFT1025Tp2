module Server {
    requires javafx.controls;
    requires javafx.fxml;


    opens server.models to javafx.base;
    exports server to javafx.graphics;


}