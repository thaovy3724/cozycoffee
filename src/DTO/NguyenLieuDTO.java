package DTO;

public class NguyenLieuDTO {
	private int idNL;
    private String donvi;
    private String tenNL;

    public NguyenLieuDTO() {
    }

    public NguyenLieuDTO(int idNL, String donvi, String tenNL) {
        this.idNL = idNL;
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
}
