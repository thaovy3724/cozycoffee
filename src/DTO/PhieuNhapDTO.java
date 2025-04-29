package DTO;

import java.sql.Date;

public class PhieuNhapDTO {
	private int idPN;
    private Date ngaytao;
    private Date ngaycapnhat;
    private int idTK;
    private int idNCC;
    private int idTT;

    public PhieuNhapDTO() {
    }

    public PhieuNhapDTO(int idPN, Date ngaytao, Date ngaycapnhat, int idTK, int idNCC, int idTT) {
        this.idPN = idPN;
        this.ngaytao = ngaytao;
        this.ngaycapnhat = ngaycapnhat;
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

    public Date getNgaytao() {
        return ngaytao;
    }

    public void setNgaytao(Date ngaytao) {
        this.ngaytao = ngaytao;
    }

    public Date getNgaycapnhat() {
        return ngaycapnhat;
    }

    public void setNgaycapnhat(Date ngaycapnhat) {
        this.ngaycapnhat = ngaycapnhat;
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
