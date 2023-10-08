module com.se233.chapter1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires org.apache.logging.log4j;


    opens com.se233.chapter1 to javafx.fxml;
    exports com.se233.chapter1;
}