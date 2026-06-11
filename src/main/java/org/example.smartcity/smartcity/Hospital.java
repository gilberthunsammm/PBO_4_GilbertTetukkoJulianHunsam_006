package org.example.smartcity.smartcity;

import javafx.beans.property.SimpleIntegerProperty;

public class Hospital extends Building {

    private final SimpleIntegerProperty numberOfBeds;

    // Berfungsi sebagai "Constructor" untuk memberikan nilai awal saat data rumah sakit baru ditambahkan.
    // Fungsi ini akan mengirimkan data dasar (nama, alamat, lantai, status) ke kelas induknya (Building),
    // lalu menyimpan data khususnya sendiri, yaitu jumlah tempat tidur (numberOfBeds).
    public Hospital(String name, String address, int floors,
                    BuildingStatus status, int numberOfBeds) {
        super(name, address, floors, status);
        this.numberOfBeds = new SimpleIntegerProperty(numberOfBeds);
    }

    // Berfungsi untuk mengambil nilai angka (integer) dari jumlah tempat tidur
    public int  getNumberOfBeds()        { return numberOfBeds.get(); }

    // Berfungsi untuk mengubah atau menyimpan jumlah tempat tidur yang baru
    public void setNumberOfBeds(int v)   { numberOfBeds.set(v); }

    // Berfungsi untuk mengambil objek property dari jumlah tempat tidur
    // (wajib ada agar datanya bisa muncul dan reaktif jika dimasukkan ke dalam TableView JavaFX)
    public SimpleIntegerProperty numberOfBedsProperty() { return numberOfBeds; }

    // Berfungsi untuk memenuhi aturan (wajib) dari kelas abstrak 'Building'.
    // Karena ini adalah kelas Hospital, maka fungsi ini akan mengembalikan teks "Hospital" sebagai tipe bangunannya.
    @Override
    public String getBuildingType() { return "Hospital"; }
}