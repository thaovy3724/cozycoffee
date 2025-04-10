package DTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HoaDonDTO {
    private int idHD;
    private LocalDate ngaytao;
    private int idTK;
    private List<DTO.CTHoaDonDTO> chiTietHoaDon;

    public HoaDonDTO() {
        this.idHD = 0;
        this.ngaytao = LocalDate.now();
        this.idTK = 0;
        this.chiTietHoaDon = new ArrayList<>();
    }

    public HoaDonDTO(int idHD, LocalDate ngaytao, int idTK){
        this.idHD = idHD;
        this.ngaytao = ngaytao;
        this.idTK = idTK;
        this.chiTietHoaDon = new ArrayList<>();
    }

    public HoaDonDTO(LocalDate ngaytao, int idTK){
        this.idHD = 0;
        this.ngaytao = ngaytao;
        this.idTK = idTK;
        this.chiTietHoaDon = new ArrayList<>();
    }


    public int getIdHD() {
        return idHD;
    }

    public void setIdHD(int idHD) {
        this.idHD = idHD;
    }

    public LocalDate getNgaytao() {
        return ngaytao;
    }

    public void setNgaytao(LocalDate ngaytao) {
        this.ngaytao = ngaytao;
    }

    public int getIdTK() {
        return idTK;
    }

    public void setIdTK(int idTK) {
        this.idTK = idTK;
    }

    public List<DTO.CTHoaDonDTO> getCTHoaDon() {
        return chiTietHoaDon;
    }

    public void setChiTietHoaDon(List<DTO.CTHoaDonDTO> chiTietHoaDon) {
        this.chiTietHoaDon = chiTietHoaDon;
    }

    @Override
    public String toString() {
        return "HoaDonDTO{" +
                "idHD=" + idHD +
                ", ngaytao=" + ngaytao +
                ", idTK=" + idTK +
                ", chiTietHoaDon=" + chiTietHoaDon +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HoaDonDTO that = (HoaDonDTO) o;
        return idHD == that.idHD; // So sánh dựa trên idHD
    }

    @Override
    public int hashCode() {
        return Objects.hash(idHD, ngaytao, idTK, chiTietHoaDon);
    }
}
