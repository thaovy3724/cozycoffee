package DTO;

public class NhaCungCapDTO {
	private int idNCC;
    private String tenNCC;
    private String diachi;
    private String sdt;
    private String email;
    private int trangthai;

    public NhaCungCapDTO() {
    }

    public NhaCungCapDTO(int idNCC, String tenNCC, String diachi, String sdt, String email, int trangthai) {
        this.idNCC = idNCC;
        this.tenNCC = tenNCC;
        this.diachi = diachi;
        this.sdt = sdt;
        this.email = email;
        this.trangthai = trangthai;
    }

    public NhaCungCapDTO(String tenNCC, String diachi, String sdt, String email, int trangthai) {
        this.tenNCC = tenNCC;
        this.diachi = diachi;
        this.sdt = sdt;
        this.email = email;
        this.trangthai = trangthai;
    }

    public int getIdNCC() {
        return idNCC;
    }

    public void setIdNCC(int idNCC) {
        this.idNCC = idNCC;
    }

    public String getTenNCC() {
        return tenNCC;
    }

    public void setTenNCC(String tenNCC) {
        this.tenNCC = tenNCC;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
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
}
