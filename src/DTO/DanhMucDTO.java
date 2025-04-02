package DTO;

public class DanhMucDTO {
	private int idDM;
    private String tenDM;
    private int trangthai;
    private int idDMCha;

    public DanhMucDTO() {
    }

    public DanhMucDTO(int idDM, String tenDM, int trangthai, int idDMCha) {
        this.idDM = idDM;
        this.tenDM = tenDM;
        this.trangthai = trangthai;
        this.idDMCha = idDMCha;
    }

    public int getIdDM() {
        return idDM;
    }

    public void setIdDM(int idDM) {
        this.idDM = idDM;
    }

    public String getTenDM() {
        return tenDM;
    }

    public void setTenDM(String tenDM) {
        this.tenDM = tenDM;
    }

    public int getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(int trangthai) {
        this.trangthai = trangthai;
    }

    public int getIdDMCha() {
        return idDMCha;
    }

    public void setIdDMCha(int idDMCha) {
        this.idDMCha = idDMCha;
    }
}
