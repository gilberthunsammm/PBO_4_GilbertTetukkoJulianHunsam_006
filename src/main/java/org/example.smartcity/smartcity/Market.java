package org.example.smartcity.smartcity;

import javafx.beans.property.SimpleDoubleProperty;

public class Market extends Building {

    private final SimpleDoubleProperty monthlyRevenue;

    // Berfungsi sebagai "Constructor" untuk memberikan nilai awal saat data pasar (Market) baru ditambahkan.
    // Fungsi ini mengirimkan data dasar (nama, alamat, lantai, status) ke kelas induknya (Building),
    // lalu menyimpan data khususnya sendiri, yaitu pendapatan bulanan (monthlyRevenue).
    public Market(String name, String address, int floors,
                  BuildingStatus status, double monthlyRevenue) {
        super(name, address, floors, status);
        this.monthlyRevenue = new SimpleDoubleProperty(monthlyRevenue);
    }

    // Berfungsi untuk mengambil nilai angka desimal (double) dari pendapatan bulanan pasar
    public double getMonthlyRevenue()        { return monthlyRevenue.get(); }

    // Berfungsi untuk mengubah atau menyimpan nilai pendapatan bulanan yang baru
    public void   setMonthlyRevenue(double v){ monthlyRevenue.set(v); }

    // Berfungsi untuk mengambil objek property dari pendapatan bulanan
    // (wajib ada agar datanya bisa muncul dan reaktif jika dimasukkan ke dalam TableView JavaFX)
    public SimpleDoubleProperty monthlyRevenueProperty() { return monthlyRevenue; }

    // Berfungsi untuk memenuhi aturan (wajib) dari kelas abstrak 'Building'.
    // Karena ini adalah kelas Market, maka fungsi ini akan mengembalikan teks "Market" sebagai tipe bangunannya.
    @Override
    public String getBuildingType() { return "Market"; }
}