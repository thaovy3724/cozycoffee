package DTO;

public class CT_CongThucDTO {
	private int idCT;
    private int idNL;
    private float soluong;

    public CT_CongThucDTO() {
    }

    public CT_CongThucDTO(int idCT, int idNL, float soluong) {
        this.idCT = idCT;
        this.idNL = idNL;
        this.soluong = soluong;
    }

    public CT_CongThucDTO(int idNL, float soluong) {
        this.idNL = idNL;
        this.soluong = soluong;
    }

    public int getIdCT() {
        return idCT;
    }

    public void setIdCT(int idCT) {
        this.idCT = idCT;
    }

    public int getIdNL() {
        return idNL;
    }

    public void setIdNL(int idNL) {
        this.idNL = idNL;
    }

    public float getSoluong() {
        return soluong;
    }

    public void setSoluong(float soluong) {
        this.soluong = soluong;
    }
}