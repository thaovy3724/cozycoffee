package DTO;

import java.sql.Date;
import java.time.LocalDate;

public class Lo_NguyenLieuDTO {
	private int idNL;
    private int idPN;
    private float soluongnhap;
    private float tonkho;
    private int dongia;
    private LocalDate hsd;

    public Lo_NguyenLieuDTO() {
    }

    public Lo_NguyenLieuDTO(int idNL, int idPN, float soluongnhap, float tonkho, int dongia, LocalDate hsd) {
        this.idNL = idNL;
        this.idPN = idPN;
        this.soluongnhap = soluongnhap;
        this.tonkho = tonkho;
        this.dongia = dongia;
        this.hsd = hsd;
    }

    public int getIdNL() {
        return idNL;
    }

    public void setIdNL(int idNL) {
        this.idNL = idNL;
    }

    public int getIdPN() {
        return idPN;
    }

    public void setIdPN(int idPN) {
        this.idPN = idPN;
    }

    public float getSoluongnhap() {
        return soluongnhap;
    }

    public void setSoluongnhap(float soluongnhap) {
        this.soluongnhap = soluongnhap;
    }

    public float getTonkho() {
        return tonkho;
    }

    public void setTonkho(float tonkho) {
        this.tonkho = tonkho;
    }

    public int getDongia() {
        return dongia;
    }

    public void setDongia(int dongia) {
        this.dongia = dongia;
    }

    public LocalDate getHsd() {
        return this.hsd;
    }

    public void setHsd(LocalDate hsd) {
        this.hsd = hsd;
    }
}
