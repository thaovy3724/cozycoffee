package DTO.ThongKe;

import java.sql.Date;
import java.util.Objects;

/**
 * @author: huonglamcoder
 */

// HUONGNGUYEN 3/5
public class ThongKeTheoThangDTO {
    private Date ngay;
    private Long chiPhi;
    private Long doanhThu;
    private Long loiNhuan;

    public ThongKeTheoThangDTO() {
    }

    public ThongKeTheoThangDTO(Date ngay, Long chiPhi, Long doanhThu, Long loiNhuan) {
        this.ngay = ngay;
        this.chiPhi = chiPhi;
        this.doanhThu = doanhThu;
        this.loiNhuan = loiNhuan;
    }

    public Date getNgay() {
        return ngay;
    }

    public void setNgay(Date ngay) {
        this.ngay = ngay;
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
        ThongKeTheoThangDTO that = (ThongKeTheoThangDTO) o;
        return ngay == that.ngay && Objects.equals(chiPhi, that.chiPhi) && Objects.equals(doanhThu, that.doanhThu) && Objects.equals(loiNhuan, that.loiNhuan);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ngay, chiPhi, doanhThu, loiNhuan);
    }
}
