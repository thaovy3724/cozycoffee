package DTO;

public class PhieuNhapDTO {
	private int idPN;
    private String ngaytao;
    private String ngaycapnhat;
    private int idTK;
    private int idNCC;
    private int idTT;

    public PhieuNhapDTO() {
    }

    public PhieuNhapDTO(int idPN, String ngaytao, String ngaycapnhat, int idTK, int idNCC, int idTT) {
        this.idPN = idPN;
        this.ngaytao = ngaytao;
        this.ngaycapnhat = ngaycapnhat;
        this.idTK = idTK;
        this.idNCC = idNCC;
        this.idTT = idTT;
    }
    
    // HUONGNGUYEN 30/4
    public PhieuNhapDTO(String ngaytao, String ngaycapnhat, int idTK, int idNCC, int idTT) {
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

    public String getNgaytao() {
        return ngaytao;
    }

    public void setNgaytao(String ngaytao) {
        this.ngaytao = ngaytao;
    }

    public String getNgaycapnhat() {
        return ngaycapnhat;
    }

    public void setNgaycapnhat(String ngaycapnhat) {
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