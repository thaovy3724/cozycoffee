package DTO;

public class CongThucDTO {
	private int idCT;
    private String mota;
    private int trangthai;
    private int idSP;

    public CongThucDTO() {
    }
    
    public CongThucDTO(int idCT, String mota, int trangthai, int idSP) {
        this.idCT = idCT;
        this.mota = mota;
        this.trangthai = trangthai;
        this.idSP = idSP;
    }

    // Getter và Setter cho idCT
    public int getIdCT() {
        return idCT;
    }

    public void setIdCT(int idCT) {
        this.idCT = idCT;
    }

    // Getter và Setter cho mota
    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    // Getter và Setter cho trangthai
    public int getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(int trangthai) {
        this.trangthai = trangthai;
    }
    
    // Getter và Setter cho idSP
    public int getIdSP() {
        return idSP;
    }

    public void setIdSP(int idSP) {
        this.idSP = idSP;
    }
}
