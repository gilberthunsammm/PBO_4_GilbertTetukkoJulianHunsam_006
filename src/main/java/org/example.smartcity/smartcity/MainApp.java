package org.example.smartcity.smartcity;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.net.URL;

public class MainApp extends Application {

    // Menyimpan daftar bangunan. ObservableList digunakan agar jika ada data yang ditambah/dihapus,
    // tabel di UI akan otomatis langsung memperbarui tampilannya.
    private final ObservableList<Building> buildingData = FXCollections.observableArrayList();

    // Deklarasi variabel-variabel komponen form antarmuka (UI)
    private TextField        tfName;
    private TextField        tfAddress;
    private TextField        tfFloors;
    private ComboBox<BuildingStatus> cbStatus;
    private ComboBox<String> cbType;
    private Label    lblExtra;
    private TextField tfExtra;
    private TableView<Building> table;
    private Label lblInfo;

    // Fungsi START: Ini adalah "Jantung" dari aplikasi JavaFX.
    // Fungsi ini dijalankan pertama kali untuk merakit semua bagian UI (Header, Tabel, Form, Background)
    // lalu menampilkannya ke layar (Stage).
    @Override
    public void start(Stage primaryStage) {

        // Membuat layout utama transparan agar background GIF bisa terlihat
        BorderPane uiRoot = new BorderPane();
        uiRoot.setStyle("-fx-background-color: transparent;");

        // Menyusun Header di atas, Tabel dan Form di tengah, serta Status Bar di bawah
        uiRoot.setTop(buildHeader());

        HBox center = new HBox(16);
        center.setPadding(new Insets(16));
        center.setStyle("-fx-background-color: transparent;");

        VBox tableSection = buildTableSection();
        VBox formSection  = buildFormSection();
        HBox.setHgrow(tableSection, Priority.ALWAYS); // Membuat tabel melebar otomatis

        center.getChildren().setAll(tableSection, formSection);
        uiRoot.setCenter(center);
        uiRoot.setBottom(buildStatusBar());

        // Logika untuk memuat file GIF latar belakang
        ImageView animatedBackground = new ImageView();
        try {
            URL gifUrl = getClass().getResource("/media/BG.gif");
            if (gifUrl != null) {
                Image bgImage = new Image(gifUrl.toExternalForm());
                animatedBackground.setImage(bgImage);
                animatedBackground.fitWidthProperty().bind(primaryStage.widthProperty());
                animatedBackground.fitHeightProperty().bind(primaryStage.heightProperty());
                animatedBackground.setPreserveRatio(false);
            } else {
                System.out.println("❌ Fail BG.gif tidak dijumpai di /resources/media/");
            }
        } catch (Exception e) {
            System.out.println("❌ Gagal memuat GIF: " + e.getMessage());
        }

        // Menumpuk background GIF di posisi paling belakang, dan uiRoot (Tabel & Form) di posisi depan
        StackPane mainRoot = new StackPane();
        mainRoot.setStyle("-fx-background-color: #111122;");
        mainRoot.getChildren().addAll(animatedBackground, uiRoot);

        // Membungkus semuanya ke dalam Scene dan menampilkannya
        Scene scene = new Scene(mainRoot, 1000, 620);
        primaryStage.setTitle("Smart City Management System - Gilbert PBO");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Mengisi tabel dengan beberapa data contoh saat aplikasi pertama kali dibuka
        buildingData.add(new Hospital("RS Sehat", "Jl. Kesehatan No.1", 5, BuildingStatus.ACTIVE, 120));
        buildingData.add(new Market("Pasar Induk", "Jl. Pasar Raya No.3", 2, BuildingStatus.ACTIVE, 50_000_000));
        buildingData.add(new Office("Gedung Pemkot", "Jl. Balai Kota No.1", 8, BuildingStatus.ACTIVE, 40));
    }

    // Fungsi untuk merakit dan mendesain bagian HEADER (Judul aplikasi di bagian atas)
    private HBox buildHeader() {
        Label title = new Label("🏙  Smart City Building Management");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        title.setTextFill(Color.WHITE);

        HBox header = new HBox(title);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(18));
        header.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");
        return header;
    }

    // Fungsi untuk merakit dan mendesain bagian TABEL (Menampilkan daftar bangunan)
    @SuppressWarnings("unchecked")
    private VBox buildTableSection() {
        table = new TableView<>(buildingData);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        table.setStyle("-fx-background-color: transparent; "
                + "-fx-control-inner-background: transparent; "
                + "-fx-table-cell-border-color: rgba(255, 255, 255, 0.1);");

        // Menghubungkan setiap kolom di tabel dengan variabel di kelas Building (name, address, dll)
        TableColumn<Building, String> colName = new TableColumn<>("Name");
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colName.setStyle("-fx-text-fill: white;");

        TableColumn<Building, String> colAddress = new TableColumn<>("Address");
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colAddress.setStyle("-fx-text-fill: white;");

        TableColumn<Building, Integer> colFloors = new TableColumn<>("Floors");
        colFloors.setCellValueFactory(new PropertyValueFactory<>("floors"));
        colFloors.setStyle("-fx-text-fill: white;");

        TableColumn<Building, BuildingStatus> colStatus = new TableColumn<>("Status");
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colStatus.setStyle("-fx-text-fill: white;");

        TableColumn<Building, String> colType = new TableColumn<>("Type");
        colType.setCellValueFactory(cd -> new javafx.beans.property.SimpleStringProperty(cd.getValue().getBuildingType()));
        colType.setStyle("-fx-text-fill: white;");

        table.getColumns().addAll(colName, colAddress, colFloors, colStatus, colType);

        Label sectionTitle = new Label("📋 Building List");
        sectionTitle.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        sectionTitle.setTextFill(Color.WHITE);

        VBox box = new VBox(8, sectionTitle, table);
        box.setPadding(new Insets(12));
        box.setStyle("-fx-background-color: rgba(0, 0, 0, 0.6); "
                + "-fx-border-color: rgba(255, 255, 255, 0.2); -fx-border-radius: 8; "
                + "-fx-background-radius: 8;");
        VBox.setVgrow(table, Priority.ALWAYS);
        return box;
    }

    // Fungsi untuk merakit dan mendesain bagian FORM (Input data di sebelah kanan layar)
    private VBox buildFormSection() {
        Label sectionTitle = new Label("➕ Add / Remove Building");
        sectionTitle.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        sectionTitle.setTextFill(Color.WHITE);

        cbType = new ComboBox<>(FXCollections.observableArrayList("Hospital", "Market", "Office"));
        cbType.setValue("Hospital");
        cbType.setMaxWidth(Double.MAX_VALUE);

        tfName    = new TextField();  tfName.setPromptText("Building name");
        tfAddress = new TextField();  tfAddress.setPromptText("Address");
        tfFloors  = new TextField();  tfFloors.setPromptText("Number of floors");

        cbStatus  = new ComboBox<>(FXCollections.observableArrayList(BuildingStatus.values()));
        cbStatus.setValue(BuildingStatus.ACTIVE);
        cbStatus.setMaxWidth(Double.MAX_VALUE);

        lblExtra = new Label("Number of Beds:");
        lblExtra.setTextFill(Color.WHITE);
        tfExtra  = new TextField(); tfExtra.setPromptText("e.g. 100");

        // EVENT HANDLING: Saat tipe bangunan (Hospital/Market/Office) diganti,
        // panggil fungsi updateExtraField() agar label di bawahnya ikut berubah
        cbType.setOnAction(e -> updateExtraField());

        Button btnAdd    = new Button("✅  Add Building");
        Button btnDelete = new Button("🗑  Demolish (Delete)");

        btnAdd.setMaxWidth(Double.MAX_VALUE);
        btnDelete.setMaxWidth(Double.MAX_VALUE);
        btnAdd.setStyle("-fx-background-color: #388e3c; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 6;");
        btnDelete.setStyle("-fx-background-color: #c62828; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 6;");

        // EVENT HANDLING: Menyambungkan tombol Add & Delete dengan fungsinya masing-masing
        btnAdd.setOnAction(e    -> handleAdd());
        btnDelete.setOnAction(e -> handleDelete());

        GridPane grid = new GridPane();
        grid.setHgap(8); grid.setVgap(10);
        grid.setPadding(new Insets(8, 0, 8, 0));

        Label lblType = new Label("Type:");     lblType.setTextFill(Color.WHITE);
        Label lblName = new Label("Name:");     lblName.setTextFill(Color.WHITE);
        Label lblAddr = new Label("Address:");  lblAddr.setTextFill(Color.WHITE);
        Label lblFlr  = new Label("Floors:");   lblFlr.setTextFill(Color.WHITE);
        Label lblStat = new Label("Status:");   lblStat.setTextFill(Color.WHITE);

        grid.add(lblType, 0, 0); grid.add(cbType, 1, 0);
        grid.add(lblName, 0, 1); grid.add(tfName, 1, 1);
        grid.add(lblAddr, 0, 2); grid.add(tfAddress, 1, 2);
        grid.add(lblFlr, 0, 3); grid.add(tfFloors, 1, 3);
        grid.add(lblStat, 0, 4); grid.add(cbStatus, 1, 4);
        grid.add(lblExtra, 0, 5); grid.add(tfExtra, 1, 5);

        ColumnConstraints c0 = new ColumnConstraints(90);
        ColumnConstraints c1 = new ColumnConstraints(170);
        grid.getColumnConstraints().addAll(c0, c1);

        VBox box = new VBox(10, sectionTitle, new Separator(), grid, btnAdd, btnDelete);
        box.setPadding(new Insets(14));
        box.setPrefWidth(300);
        box.setStyle("-fx-background-color: rgba(0, 0, 0, 0.6); "
                + "-fx-border-color: rgba(255, 255, 255, 0.2); -fx-border-radius: 8; "
                + "-fx-background-radius: 8;");
        return box;
    }

    // Fungsi untuk merakit bagian STATUS BAR (Informasi jumlah bangunan di bagian paling bawah)
    private HBox buildStatusBar() {
        lblInfo = new Label("Ready. Total buildings: 3");
        lblInfo.setTextFill(Color.WHITE);

        HBox bar = new HBox(lblInfo);
        bar.setPadding(new Insets(6, 14, 6, 14));
        bar.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");
        return bar;
    }

    // Fungsi LOGIKA: Mengubah teks pertanyaan tambahan di form sesuai tipe bangunan yang dipilih di ComboBox
    private void updateExtraField() {
        switch (cbType.getValue()) {
            case "Hospital":
                lblExtra.setText("Number of Beds:");
                tfExtra.setPromptText("e.g. 100");
                break;
            case "Market":
                lblExtra.setText("Monthly Revenue:");
                tfExtra.setPromptText("e.g. 5000000");
                break;
            case "Office":
                lblExtra.setText("Number of Rooms:");
                tfExtra.setPromptText("e.g. 20");
                break;
        }
    }

    // Fungsi LOGIKA: Dijalankan saat tombol "Add" diklik.
    // Bertugas mengambil data ketikan user, mengecek error (validasi),
    // membuat objek bangunan baru, dan menyimpannya ke dalam tabel.
    private void handleAdd() {
        String name    = tfName.getText().trim();
        String address = tfAddress.getText().trim();
        String floorsStr = tfFloors.getText().trim();
        String extraStr  = tfExtra.getText().trim();

        // Cek apakah ada kolom yang masih kosong
        if (name.isEmpty() || address.isEmpty() || floorsStr.isEmpty() || extraStr.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Incomplete Form", "Please fill in all fields before adding.");
            return;
        }

        // Cek apakah input lantai adalah angka
        int floors;
        try { floors = Integer.parseInt(floorsStr); }
        catch (NumberFormatException ex) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", "Floors must be a number.");
            return;
        }

        BuildingStatus status = cbStatus.getValue();
        Building building;

        // Cek dan buat objek berdasarkan tipe bangunan (Polymorphism)
        switch (cbType.getValue()) {
            case "Hospital": {
                try { building = new Hospital(name, address, floors, status, Integer.parseInt(extraStr)); }
                catch (Exception ex) { showAlert(Alert.AlertType.ERROR, "Error", "Invalid number of beds"); return; }
                break;
            }
            case "Market": {
                try { building = new Market(name, address, floors, status, Double.parseDouble(extraStr)); }
                catch (Exception ex) { showAlert(Alert.AlertType.ERROR, "Error", "Invalid monthly revenue"); return; }
                break;
            }
            default: { // Office
                try { building = new Office(name, address, floors, status, Integer.parseInt(extraStr)); }
                catch (Exception ex) { showAlert(Alert.AlertType.ERROR, "Error", "Invalid number of rooms"); return; }
                break;
            }
        }

        // Memasukkan data bangunan baru ke dalam daftar, otomatis akan muncul di tabel UI
        buildingData.add(building);
        updateStatusBar();
        clearForm();
        showAlert(Alert.AlertType.INFORMATION, "Success", "Building added successfully!");
    }

    // Fungsi LOGIKA: Dijalankan saat tombol "Delete" diklik.
    // Bertugas mengecek baris mana yang dipilih user di tabel, memunculkan dialog konfirmasi, lalu menghapusnya.
    private void handleDelete() {
        Building selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a building from the table to delete.");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete \"" + selected.getName() + "\"?", ButtonType.YES, ButtonType.NO);
        confirm.setTitle("Confirm Delete"); confirm.setHeaderText(null);
        confirm.showAndWait().ifPresent(btn -> {
            if (btn == ButtonType.YES) {
                buildingData.remove(selected); // Menghapus data
                updateStatusBar();
            }
        });
    }

    // Fungsi HELPER: Mengosongkan kembali form inputan setelah data berhasil ditambahkan
    private void clearForm() {
        tfName.clear(); tfAddress.clear(); tfFloors.clear(); tfExtra.clear();
        cbStatus.setValue(BuildingStatus.ACTIVE); cbType.setValue("Hospital"); updateExtraField();
    }

    // Fungsi HELPER: Memperbarui teks jumlah total bangunan di status bar bawah
    private void updateStatusBar() { lblInfo.setText("Total buildings: " + buildingData.size()); }

    // Fungsi HELPER: Jalan pintas untuk memunculkan pesan Pop-Up (Alert) di layar
    private void showAlert(Alert.AlertType type, String title, String msg) {
        new Alert(type, msg, ButtonType.OK).showAndWait();
    }

    // Fungsi MAIN standar Java untuk memicu aplikasi JavaFX berjalan
    public static void main(String[] args) { launch(args); }
}