package org.example.smartcity.smartcity;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public abstract class Building {

    private final SimpleStringProperty name;
    private final SimpleStringProperty address;
    private final SimpleIntegerProperty floors;
    private final SimpleObjectProperty<BuildingStatus> status;

    // Berfungsi sebagai "Constructor" untuk memberikan nilai awal (nama, alamat, lantai, status)
    // saat sebuah data bangunan baru pertama kali diciptakan di dalam program.
    public Building(String name, String address, int floors, BuildingStatus status) {
        this.name    = new SimpleStringProperty(name);
        this.address = new SimpleStringProperty(address);
        this.floors  = new SimpleIntegerProperty(floors);
        this.status  = new SimpleObjectProperty<>(status);
    }

    // Berfungsi untuk mengambil nilai teks (String) dari nama bangunan
    public String getName()              { return name.get(); }
    // Berfungsi untuk mengubah atau menyimpan nama bangunan yang baru
    public void   setName(String v)      { name.set(v); }
    // Berfungsi untuk mengambil objek property dari nama (syarat wajib agar data bisa muncul dan reaktif di TableView)
    public SimpleStringProperty nameProperty() { return name; }

    // Berfungsi untuk mengambil nilai teks (String) dari alamat bangunan
    public String getAddress()           { return address.get(); }
    // Berfungsi untuk mengubah atau menyimpan alamat bangunan yang baru
    public void   setAddress(String v)   { address.set(v); }
    // Berfungsi untuk mengambil objek property dari alamat untuk TableView
    public SimpleStringProperty addressProperty() { return address; }

    // Berfungsi untuk mengambil nilai angka (integer) dari jumlah lantai
    public int  getFloors()              { return floors.get(); }
    // Berfungsi untuk mengubah atau menyimpan jumlah lantai yang baru
    public void setFloors(int v)         { floors.set(v); }
    // Berfungsi untuk mengambil objek property dari jumlah lantai untuk TableView
    public SimpleIntegerProperty floorsProperty() { return floors; }

    // Berfungsi untuk mengambil nilai status bangunan (contoh: ACTIVE, INACTIVE)
    public BuildingStatus getStatus()             { return status.get(); }
    // Berfungsi untuk mengubah atau menyimpan status bangunan yang baru
    public void           setStatus(BuildingStatus v) { status.set(v); }
    // Berfungsi untuk mengambil objek property dari status untuk TableView
    public SimpleObjectProperty<BuildingStatus> statusProperty() { return status; }

    // Berfungsi sebagai aturan (wajib) di mana setiap bangunan spesifik (seperti Hospital, Market, Office)
    // harus mengembalikan nama tipe bangunannya masing-masing.
    public abstract String getBuildingType();
}