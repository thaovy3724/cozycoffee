package DTO;

public class TrangThai_PnDTO {
	private int idTT;
    private String tenTT;

    public TrangThaiPNDTO() {
    }

    public TrangThaiPNDTO(int idTT, String tenTT) {
        this.idTT = idTT;
        this.tenTT = tenTT;
    }

    public int getIdTT() {
        return idTT;
    }

    public void setIdTT(int idTT) {
        this.idTT = idTT;
    }

    public String getTenTT() {
        return tenTT;
    }

    public void setTenTT(String tenTT) {
        this.tenTT = tenTT;
    }
}
