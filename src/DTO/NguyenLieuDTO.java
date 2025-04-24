package DTO;
import java.util.Objects;
public class NguyenLieuDTO {
    private int idNL;
    private String donvi;
    private String tenNL;
    private int trangthai;
    
    public NguyenLieuDTO(int idNL, String tenNL) {
        this.idNL = idNL;
        this.tenNL = tenNL;
        this.donvi = "";
        this.trangthai = 1;
    }

    public NguyenLieuDTO(int idNL, String tenNL, String donvi, int trangthai) {
        this.idNL = idNL;
        this.donvi = donvi;
        this.tenNL = tenNL;
        this.trangthai = trangthai;
    }

    public NguyenLieuDTO(String tenNL, String donvi, int trangthai) {
        this.donvi = donvi;
        this.tenNL = tenNL;
        this.trangthai = trangthai;
    }

    public int getIdNL() {
        return idNL;
    }

    public void setIdNL(int idNL) {
        this.idNL = idNL;
    }

    public String getDonvi() {
        return donvi;
    }

    public void setDonvi(String donvi) {
        this.donvi = donvi;
    }

    public String getTenNL() {
        return tenNL;
    }

    public void setTenNL(String tenNL) {
        this.tenNL = tenNL;
    }
    public int getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(int trangthai) {
        this.trangthai = trangthai;
    }
    @Override
    public String toString() {
        
        return tenNL; // Hiển thị tên nguyên liệu trong JComboBox
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        NguyenLieuDTO that = (NguyenLieuDTO) obj;
        return idNL == that.idNL;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idNL);
    }

    

   

}
