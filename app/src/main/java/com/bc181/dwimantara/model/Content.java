package com.bc181.dwimantara.model;

import com.google.gson.annotations.SerializedName;

public class Content {

    @SerializedName("id")
    private int id;
    @SerializedName("nama")
    private String nama;
    @SerializedName("tahun")
    private String tahun;
    @SerializedName("stock")
    private String stock;
    @SerializedName("brand")
    private String brand;
    @SerializedName("photo")
    private String photo;
    @SerializedName("jenis")
    private String jenis;

    public Content(int id, String nama, String tahun, String stock, String brand, String photo, String jenis) {
        this.id = id;
        this.nama = nama;
        this.tahun = tahun;
        this.stock = stock;
        this.brand = brand;
        this.photo = photo;
        this.jenis = jenis;
    }

    public int getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getTahun() {
        return tahun;
    }

    public String getStock() {
        return stock;
    }

    public String getBrand() {
        return brand;
    }

    public String getPhoto() {
        return photo;
    }

    public String getJenis() {
        return jenis;
    }
}
