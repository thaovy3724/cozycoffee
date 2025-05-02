package DTO;

public class TaiKhoanDTO {
	private int idTK;
    private String tenTK;
    private String matkhau;
    private String email;
    private int trangthai;
    private int idNQ;

    public TaiKhoanDTO() {
    }

    public TaiKhoanDTO(int idTK, String tenTK, String matkhau, String email, int trangthai, int idNQ) {
        this.idTK = idTK;
        this.tenTK = tenTK;
        this.matkhau = matkhau;
        this.email = email;
        this.trangthai = trangthai;
        this.idNQ = idNQ;
    }

    public TaiKhoanDTO(String tenTK, String matkhau, String email, int trangthai, int idNQ){
        this.tenTK = tenTK;
        this.matkhau = matkhau;
        this.email = email;
        this.trangthai = trangthai;
        this.idNQ = idNQ;
    }

    public int getIdTK() {
        return idTK;
    }

    public void setIdTK(int idTK) {
        this.idTK = idTK;
    }

    public String getTenTK() {
        return tenTK;
    }

    public void setTenTK(String tenTK) {
        this.tenTK = tenTK;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(int trangthai) {
        this.trangthai = trangthai;
    }

    public int getIdNQ() {
        return idNQ;
    }

    public void setIdNQ(int idNQ) {
        this.idNQ = idNQ;
    }
}