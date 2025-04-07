package DTO;

import java.util.Objects;

public class TaiKhoanDTO {
    private int idTK;
    private String tenTK;
    private String matkhau;
    private String hoten;
    private String email;
    private String dienthoai;
    private boolean trangthai;
    private int idNQ;

    public TaiKhoanDTO() {
        this.idTK = 0;
        this.tenTK = "";
        this.matkhau = "";
        this.hoten = "";
        this.email = "";
        this.dienthoai = "";
        this.trangthai = false;
        this.idNQ = 0;
    }

    public TaiKhoanDTO(int idTK, String tenTK, String matkhau, String hoten, String email, String dienthoai, boolean trangthai, int idNQ) {
        this.idTK = idTK;
        this.tenTK = tenTK;
        this.matkhau = matkhau;
        this.hoten = hoten;
        this.email = email;
        this.dienthoai = dienthoai;
        this.trangthai = trangthai;
        this.idNQ = idNQ;
    }

    public int getIdTK() {
        return idTK;
    }

    public void setIdTK(int idTK) {
        this.idTK = idTK;
    }

    public String getTenTK() {
        return tenTK;
    }

    public void setTenTK(String tenTK) {
        this.tenTK = tenTK;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDienthoai() {
        return dienthoai;
    }

    public void setDienthoai(String dienthoai) {
        this.dienthoai = dienthoai;
    }

    public boolean getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(boolean trangthai) {
        this.trangthai = trangthai;
    }

    public int getIdNQ() {
        return idNQ;
    }

    public void setIdNQ(int idNQ) {
        this.idNQ = idNQ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TaiKhoanDTO)) return false;
        TaiKhoanDTO that = (TaiKhoanDTO) o;
        return idTK == that.idTK && trangthai == that.trangthai && Objects.equals(tenTK, that.tenTK) && Objects.equals(matkhau, that.matkhau) && Objects.equals(hoten, that.hoten) && Objects.equals(email, that.email) && Objects.equals(dienthoai, that.dienthoai) && Objects.equals(idNQ, that.idNQ);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTK, tenTK, matkhau, hoten, email, dienthoai, trangthai, idNQ);
    }

    @Override
    public String toString() {
        return "TaiKhoanDTO{" +
                "idTK=" + idTK +
                ", tenTK='" + tenTK + '\'' +
                ", matkhau='" + matkhau + '\'' +
                ", hoTen='" + hoten + '\'' +
                ", email='" + email + '\'' +
                ", dienThoai='" + dienthoai + '\'' +
                ", trangThai=" + trangthai +
                ", idNQ='" + idNQ + '\'' +
                '}';
    }
}
