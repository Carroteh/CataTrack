module com.carrot.catatrack {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.carrot.catatrack to javafx.fxml;
    exports com.carrot.catatrack;
}