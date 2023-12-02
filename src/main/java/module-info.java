module com.carrot.catatrack {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.xerial.sqlitejdbc;
    requires org.apache.logging.log4j;


    opens com.carrot.catatrack to javafx.fxml;
    exports com.carrot.catatrack;
    exports com.carrot.catatrack.controller;
    opens com.carrot.catatrack.controller to javafx.fxml;
}