module org.example.smartcity {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    // Perhatikan tambahan .smartcity di dua baris bawah ini
    opens org.example.smartcity.smartcity to javafx.fxml;
    exports org.example.smartcity.smartcity;
}