package org.example.smartcity.smartcity;

import javafx.beans.property.SimpleIntegerProperty;

public class Office extends Building {

    private final SimpleIntegerProperty numberOfRooms;

    // Berfungsi sebagai "Constructor" untuk memberikan nilai awal saat data kantor (Office) baru ditambahkan.
    // Fungsi ini mengirimkan data dasar (nama, alamat, lantai, status) ke kelas induknya (Building),
    // lalu menyimpan data khususnya sendiri, yaitu jumlah ruangan (numberOfRooms).
    public Office(String name, String address, int floors,
                  BuildingStatus status, int numberOfRooms) {
        super(name, address, floors, status);
        this.numberOfRooms = new SimpleIntegerProperty(numberOfRooms);
    }

    // Berfungsi untuk mengambil nilai angka (integer) dari jumlah ruangan kantor
    public int  getNumberOfRooms()       { return numberOfRooms.get(); }

    // Berfungsi untuk mengubah atau menyimpan nilai jumlah ruangan yang baru
    public void setNumberOfRooms(int v)  { numberOfRooms.set(v); }

    // Berfungsi untuk mengambil objek property dari jumlah ruangan
    // (wajib ada agar datanya bisa muncul dan reaktif jika dimasukkan ke dalam TableView JavaFX)
    public SimpleIntegerProperty numberOfRoomsProperty() { return numberOfRooms; }

    // Berfungsi untuk memenuhi aturan (wajib) dari kelas abstrak 'Building'.
    // Karena ini adalah kelas Office, maka fungsi ini akan mengembalikan teks "Office" sebagai tipe bangunannya.
    @Override
    public String getBuildingType() { return "Office"; }
}