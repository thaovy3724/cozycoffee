package GUI.ThongKe;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.List;

import javax.swing.JPanel;

import BUS.ThongKeBUS;
import GUI.Component.Chart.BarChart.Chart;
import GUI.Component.Chart.BarChart.ModelChart;

/**
 * @author: huonglamcoder
 */

// HUONGNGUYEN 3/5
public class ThongKeLoiNhuanTheoThang extends JPanel {
    ThongKeBUS thongKeBUS;
    Chart chart;


    public ThongKeLoiNhuanTheoThang(ThongKeBUS thongKeBUS) {
        this.thongKeBUS = thongKeBUS;
        initComponent();
    }

    public void initComponent() {
        setLayout(new BorderLayout());
        setBackground(new Color(255, 240, 220));
        chart = new Chart();
        chart.addLegend("Chi", new Color(245, 189, 135));
        chart.addLegend("Doanh thu", new Color(135, 189, 245));
        chart.addLegend("Lợi nhuận", new Color(189, 135, 245));
        this.add(chart, BorderLayout.CENTER);
    }

    public void updateChart(int year, int month) {
        // Lấy dữ liệu hàng ngày từ ThongKeBUS
        List<DTO.ThongKe.ThongKeTheoThangDTO> chartResult = thongKeBUS.getThongKeTheoThang(year, month);

        // Xóa chart hiện tại
        this.remove(chart);

        // Tạo chart mới
        chart = new Chart();
        chart.addLegend("Vốn", new Color(245, 189, 135));
        chart.addLegend("Doanh thu", new Color(135, 189, 245));
        chart.addLegend("Lợi nhuận", new Color(189, 135, 245));

        // Gộp dữ liệu thành nhóm 7 ngày
        Long sumChiphi = 0L;
        Long sumDoanhthu = 0L;
        Long sumLoinhuan = 0L;
        int daysInWeek = 7;

        for (int i = 0; i < chartResult.size(); i++) {
            int index = i + 1; // Ngày (1-based)
            sumChiphi += chartResult.get(i).getChiPhi();
            sumDoanhthu += chartResult.get(i).getDoanhThu();
            sumLoinhuan += chartResult.get(i).getLoiNhuan();

            // Nếu là ngày cuối của nhóm 7 ngày hoặc ngày cuối của tháng
            if (index % daysInWeek == 0 || i == chartResult.size() - 1) {
                int startDay = index - (index % daysInWeek == 0 ? daysInWeek - 1 : (index % daysInWeek));
                int endDay = index;
                String label = "Từ Ngày " + startDay + "->" + endDay;
                chart.addData(new ModelChart(
                        label,
                        new Long[]{sumChiphi, sumDoanhthu, sumLoinhuan}
                ));
                sumChiphi = 0L;
                sumDoanhthu = 0L;
                sumLoinhuan = 0L;
            }
        }

        // Làm mới giao diện
        chart.repaint();
        chart.validate();
        this.add(chart, BorderLayout.CENTER);
        this.repaint();
        this.validate();
    }
}
