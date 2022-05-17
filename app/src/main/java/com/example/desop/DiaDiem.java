package com.example.desop;

import java.io.Serializable;

public class DiaDiem implements Serializable {


    private String moTa, tenDiaDiem, diaChi;
    private String url;

    public DiaDiem() {
    }

    public DiaDiem(String moTa, String tenDiaDiem, String diaChi, String url) {
        this.moTa = moTa;
        this.tenDiaDiem = tenDiaDiem;
        this.diaChi = diaChi;
        this.url = url;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getTenDiaDiem() {
        return tenDiaDiem;
    }

    public void setTenDiaDiem(String tenDiaDiem) {
        this.tenDiaDiem = tenDiaDiem;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
