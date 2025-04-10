package DTO;

import java.util.Objects;

public class SanPhamDTO {
    private int idSP;
    private String tenSP;
    private int giaban;
    private String hinhanh;
    private boolean trangthai;
    private int idDM;

    public SanPhamDTO() {
        this.idSP = 0;
        this.tenSP = "";
        this.giaban = 0;
        this.hinhanh = "";
        this.trangthai = false;
        this.idDM = 0;
    }

    public SanPhamDTO(int idSP, String tenSP, int giaban, String hinhanh, boolean trangthai, int idDM) {
        this.idSP = idSP;
        this.tenSP = tenSP;
        this.giaban = giaban;
        this.hinhanh = hinhanh;
        this.trangthai = trangthai;
        this.idDM = idDM;
    }

    public int getIdSP() {
        return idSP;
    }

    public void setIdSP(int idSP) {
        this.idSP = idSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public int getGiaban() {
        return giaban;
    }

    public void setGiaban(int giaban) {
        this.giaban = giaban;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public boolean getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(boolean trangthai) {
        this.trangthai = trangthai;
    }

    public int getIdDM() {
        return idDM;
    }

    public void setIdDM(int idDM) {
        this.idDM = idDM;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SanPhamDTO)) return false;
        SanPhamDTO that = (SanPhamDTO) o;
        return idSP == that.idSP && giaban == that.giaban && trangthai == that.trangthai && idDM == that.idDM && Objects.equals(tenSP, that.tenSP) && Objects.equals(hinhanh, that.hinhanh);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idSP, tenSP, giaban, hinhanh, trangthai, idDM);
    }

    @Override
    public String toString() {
        return "SanPhamDTO{" +
                "idSP=" + idSP +
                ", tenSP='" + tenSP + '\'' +
                ", giaban=" + giaban +
                ", hinhanh='" + hinhanh + '\'' +
                ", trangthai=" + trangthai +
                ", idDM=" + idDM +
                '}';
    }
}
