package DTO;

public class CT_HoaDonDTO {
	private int idSP;
    private int idHD;
    private int soluong;
    private int gialucdat;

    public CT_HoaDonDTO(int idSP, int idHD, int soluong, int gialucdat) {
        this.idSP = idSP;
        this.idHD = idHD;
        this.soluong = soluong;
        this.gialucdat = gialucdat;
    }

    public CT_HoaDonDTO(int idSP, int soluong, int gialucdat) {
        this.idSP = idSP;
        this.soluong = soluong;
        this.gialucdat = gialucdat;
    }

    public int getIdSP() {
        return idSP;
    }

    public void setIdSP(int idSP) {
        this.idSP = idSP;
    }

    public int getIdHD() {
        return idHD;
    }

    public void setIdHD(int idHD) {
        this.idHD = idHD;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public int getGialucdat() {
        return gialucdat;
    }

    public void setGialucdat(int gialucdat) {
        this.gialucdat = gialucdat;
    }
}