package DTO;

public class HoaDonDTO {
	private int idHD;
    private String ngaytao;
    private int idTK;

    public HoaDonDTO() {
    }

    public HoaDonDTO(int idHD, String ngaytao, int idTK) {
        this.idHD = idHD;
        this.ngaytao = ngaytao;
        this.idTK = idTK;
    }

    public int getIdHD() {
        return idHD;
    }

    public void setIdHD(int idHD) {
        this.idHD = idHD;
    }

    public String getNgaytao() {
        return ngaytao;
    }

    public void setNgaytao(String ngaytao) {
        this.ngaytao = ngaytao;
    }

    public int getIdTK() {
        return idTK;
    }

    public void setIdTK(int idTK) {
        this.idTK = idTK;
    }
}
