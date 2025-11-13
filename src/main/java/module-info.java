module org.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;  // For MarkdownViewer (Swing)

    opens org.example.demo to javafx.fxml;
    exports org.example.demo;
    exports org.example.demo.entities;
    exports org.example.demo.game;
    exports org.example.demo.systems;
    exports org.example.demo.ui;
    exports org.example.demo.markdown;
}