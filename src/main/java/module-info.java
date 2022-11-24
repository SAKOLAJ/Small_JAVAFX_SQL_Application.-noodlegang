module com.example.test2 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires org.jsoup;
    requires java.sql;

    opens com.example.test2 to javafx.fxml;
    exports com.example.test2;
}