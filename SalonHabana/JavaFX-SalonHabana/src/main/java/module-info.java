module com.example.bk7 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.example.bk7 to javafx.fxml;
    exports com.example.bk7;
}