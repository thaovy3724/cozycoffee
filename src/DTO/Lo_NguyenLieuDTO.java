package DTO;

import java.time.LocalDate;
import java.time.ZoneId;

public class Lo_NguyenLieuDTO {
	private int idNL;
    private int idPN;
    private float soluongnhap;
    private float tonkho;
    private int dongia;
    private LocalDate hsd;

    public Lo_NguyenLieuDTO() {
    }

    //HUONGNGUYEN 1/5
    //Đổi String thành LocalDate
    public Lo_NguyenLieuDTO(int idNL, int idPN, float soluongnhap,
                            float tonkho, int dongia, Object hsd) {
        this.idNL = idNL;
        this.idPN = idPN;
        this.soluongnhap = soluongnhap;
        this.tonkho = tonkho;
        this.dongia = dongia;
        setHsd(hsd);
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
        return hsd;
    }

    public void setHsd(Object hsd) {
        if (hsd instanceof LocalDate) {
            this.hsd = (LocalDate) hsd;
        } else if (hsd instanceof java.sql.Date) {
            this.hsd = ((java.sql.Date) hsd).toLocalDate();
        } else if (hsd instanceof java.util.Date) {
            this.hsd = ((java.sql.Date) hsd).toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
        } else {
            this.hsd = null;
        }
    }
}
