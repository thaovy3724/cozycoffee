package DTO;

public class NhomQuyenDTO {
	private int idNQ;
    private String tenNQ;

    public NhomQuyenDTO() {
    }

    public NhomQuyenDTO(int idNQ, String tenNQ) {
        this.idNQ = idNQ;
        this.tenNQ = tenNQ;
    }

    public int getIdNQ() {
        return idNQ;
    }

    public void setIdNQ(int idNQ) {
        this.idNQ = idNQ;
    }

    public String getTenNQ() {
        return tenNQ;
    }

    public void setTenNQ(String tenNQ) {
        this.tenNQ = tenNQ;
    }
    
    @Override
    public String toString(){
        return tenNQ;
    }
}
