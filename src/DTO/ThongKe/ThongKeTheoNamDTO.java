package DTO.ThongKe;

import java.util.Objects;

/**
 * @author: huonglamcoder
 */

// HUONGNGUYEN 3/5
public class ThongKeTheoNamDTO {
    private int thang;
    private Long chiPhi;
    private Long doanhThu;
    private Long loiNhuan;

    public ThongKeTheoNamDTO() {
    }

    public ThongKeTheoNamDTO(int thang, Long chiPhi, Long doanhThu, Long loiNhuan) {
        this.thang = thang;
        this.chiPhi = chiPhi;
        this.doanhThu = doanhThu;
        this.loiNhuan = loiNhuan;
    }

    public int getThang() {
        return thang;
    }

    public void setThang(int thang) {
        this.thang = thang;
    }

    public Long getChiPhi() {
        return chiPhi;
    }

    public void setChiPhi(Long chiPhi) {
        this.chiPhi = chiPhi;
    }

    public Long getDoanhThu() {
        return doanhThu;
    }

    public void setDoanhThu(Long doanhThu) {
        this.doanhThu = doanhThu;
    }

    public Long getLoiNhuan() {
        return loiNhuan;
    }

    public void setLoiNhuan(Long loiNhuan) {
        this.loiNhuan = loiNhuan;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
			return false;
		}
        ThongKeTheoNamDTO that = (ThongKeTheoNamDTO) o;
        return thang == that.thang && Objects.equals(chiPhi, that.chiPhi) && Objects.equals(doanhThu, that.doanhThu) && Objects.equals(loiNhuan, that.loiNhuan);
    }

    @Override
    public int hashCode() {
        return Objects.hash(thang, chiPhi, doanhThu, loiNhuan);
    }
}
