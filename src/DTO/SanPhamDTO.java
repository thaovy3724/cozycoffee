package DTO;

public class SanPhamDTO {
	private int idSP;
    private String tenSP;
    private int giaban;
    private String hinhanh;
    private int trangthai;
    private int idDM;

    public SanPhamDTO() {
    }

    public SanPhamDTO(int idSP, String tenSP, int giaban, String hinhanh, int trangthai, int idDM) {
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

    public int getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(int trangthai) {
        this.trangthai = trangthai;
    }

    public int getIdDM() {
        return idDM;
    }

    public void setIdDM(int idDM) {
        this.idDM = idDM;
    }
}
