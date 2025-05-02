package DTO;
import java.util.Objects;
public class NguyenLieuDTO {
    private int idNL;
    private String donvi;
    private String tenNL;
    
    public NguyenLieuDTO(int idNL, String tenNL) {
        this.idNL = idNL;
        this.tenNL = tenNL;
        this.donvi = "";
    }

    public NguyenLieuDTO(int idNL, String tenNL, String donvi) {
        this.idNL = idNL;
        this.donvi = donvi;
        this.tenNL = tenNL;
    }

    public NguyenLieuDTO(String tenNL, String donvi) {
        this.donvi = donvi;
        this.tenNL = tenNL;
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