package DTO;

import java.time.LocalDate;
import java.time.ZoneId;

public class HoaDonDTO {
	private int idHD;
    private LocalDate ngaytao;
    private int idTK;

    public HoaDonDTO(int idHD, Object ngaytao, int idTK) {
        this.idHD = idHD;
        setNgaytao(ngaytao);
        this.idTK = idTK;
    }

    public HoaDonDTO(Object ngaytao, int idTK) {
        setNgaytao(ngaytao);
        this.idTK = idTK;
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

    public void setNgaytao(Object ngaytao) {
        if (ngaytao instanceof LocalDate) {
            this.ngaytao = (LocalDate) ngaytao;
        }else if (ngaytao instanceof java.sql.Date) {
            this.ngaytao = ((java.sql.Date) ngaytao).toLocalDate();
        } else if (ngaytao instanceof java.util.Date) {
            this.ngaytao = ((java.util.Date) ngaytao).toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();
        } else this.ngaytao = null; 
    }

    public int getIdTK() {
        return idTK;
    }

    public void setIdTK(int idTK) {
        this.idTK = idTK;
    }
}