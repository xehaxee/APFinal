module com.example.elahefinal {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.example.elahefinal to javafx.fxml;


    exports com.example.elahefinal;

}