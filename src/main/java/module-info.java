module org.jumbo.simpletrace {
    requires javafx.controls;
    requires javafx.fxml;

    requires java.desktop;
    requires playwright;
    requires com.google.common;

    opens org.jumbo.simpletrace to javafx.fxml;
    exports org.jumbo.simpletrace;
}