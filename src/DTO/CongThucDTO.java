package DTO;

public class CongThucDTO {
    private int idCT;
    private String mota;
    private int idSP;

    public CongThucDTO() {
        idCT = 0;
        mota = "";
        idSP = 0;
    }

    public CongThucDTO(int idCT, int idSP,String mota) {
        this.idCT = idCT; 
        this.idSP = idSP;
        this.mota = mota;
    }
    public CongThucDTO( int idSP, String mota) {
 
        this.idSP = idSP;
        this.mota = mota;
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

   
    // Getter và Setter cho idSP
    public int getIdSP() {
        return idSP;
    }

    public void setIdSP(int idSP) {
        this.idSP = idSP;
    }
}