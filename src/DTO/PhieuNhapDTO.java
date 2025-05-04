package DTO;

import java.time.LocalDate;
import java.time.ZoneId;

public class PhieuNhapDTO {
	private int idPN;
    private LocalDate ngaytao;
    private LocalDate ngaycapnhat;
    private int idTK;
    private int idNCC;
    private int idTT;

    public PhieuNhapDTO() {
    }

    //HUONGNGUYEN 1/5
    // Đổi String thành LocalDate
    public PhieuNhapDTO(int idPN, Object ngaytao, Object ngaycapnhat, int idTK,
                        int idNCC, int idTT) {
        this.idPN = idPN;
        setNgaytao(ngaytao);
        setNgaycapnhat(ngaycapnhat);
        this.idTK = idTK;
        this.idNCC = idNCC;
        this.idTT = idTT;
    }

    public PhieuNhapDTO(Object ngaytao, Object ngaycapnhat, int idTK,
                        int idNCC,
                        int idTT) {
        setNgaytao(ngaytao);
        setNgaycapnhat(ngaycapnhat);
        this.idTK = idTK;
        this.idNCC = idNCC;
        this.idTT = idTT;
    }

    public int getIdPN() {
        return idPN;
    }

    public void setIdPN(int idPN) {
        this.idPN = idPN;
    }

    public LocalDate getNgaytao() {
        return ngaytao;
    }

    public void setNgaytao(Object ngaytao) {
        if (ngaytao instanceof LocalDate) {
            this.ngaytao = (LocalDate) ngaytao;
        } else if (ngaytao instanceof java.sql.Date) {
            this.ngaytao = ((java.sql.Date) ngaytao).toLocalDate();
        } else if (ngaytao instanceof java.util.Date) {
            this.ngaytao = ((java.sql.Date) ngaytao).toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
        } else {
            this.ngaytao = null;
        }
    }

    public LocalDate getNgaycapnhat() {
        return ngaycapnhat;
    }

    public void setNgaycapnhat(Object ngaycapnhat) {
        if (ngaycapnhat instanceof LocalDate) {
            this.ngaycapnhat = (LocalDate) ngaycapnhat;
        } else if (ngaycapnhat instanceof java.sql.Date) {
            this.ngaycapnhat = ((java.sql.Date) ngaycapnhat).toLocalDate();
        } else if (ngaycapnhat instanceof java.util.Date) {
            this.ngaycapnhat = ((java.sql.Date) ngaycapnhat).toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
        } else {
            this.ngaycapnhat = null;
        }
    }

    public int getIdTK() {
        return idTK;
    }

    public void setIdTK(int idTK) {
        this.idTK = idTK;
    }

    public int getIdNCC() {
        return idNCC;
    }

    public void setIdNCC(int idNCC) {
        this.idNCC = idNCC;
    }

    public int getIdTT() {
        return idTT;
    }

    public void setIdTT(int idTT) {
        this.idTT = idTT;
    }


}
