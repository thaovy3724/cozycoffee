package GUI.ThongKe;

import java.awt.BorderLayout;
import java.awt.Color;
import java.time.LocalDate;
import java.util.ArrayList;
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
    private ThongKeBUS thongKeBUS = new ThongKeBUS();
    private Chart chart;


    public ThongKeLoiNhuanTheoThang(int month, int year) {
        initComponent();
        updateChart(month, year);
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

    public void updateChart(int month, int year) {
        // Lấy dữ liệu hàng ngày từ ThongKeBUS
        List<List<Long>> chartResult = thongKeBUS.getProfitStatisticByMonth(month, year);

        // Xóa chart hiện tại
        this.remove(chart);

        // Tạo chart mới
        chart = new Chart();
        chart.addLegend("Chi", new Color(245, 189, 135));
        chart.addLegend("Doanh thu", new Color(135, 189, 245));
        chart.addLegend("Lợi nhuận", new Color(189, 135, 245));

        // Gộp dữ liệu thành nhóm 7 ngày
        // int daysInWeek = 7;

        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());

        List<LocalDate[]> weekRanges = getWeekRanges(start, end);

        for (int i = 0; i < weekRanges.size(); i++) {
            String label = "Từ Ngày " + weekRanges.get(i)[0].getDayOfMonth() + "->" + weekRanges.get(i)[1].getDayOfMonth();
            chart.addData(new ModelChart(
                    label,
                    new Long[]{
                            chartResult.get(0).get(i),
                            chartResult.get(1).get(i),
                            chartResult.get(2).get(i)
                    }
            ));
        }
//        for (int i = 0; i < weekRanges.size(); i++) {
//            int index = i + 1; // Ngày (1-based)
//
//            // Nếu là ngày cuối của nhóm 7 ngày hoặc ngày cuối của tháng
//            if (index % daysInWeek == 0 || i == end - 1) {
//                int startDay = index - (index % daysInWeek == 0 ? daysInWeek - 1 : (index % daysInWeek));
//                int endDay = index;
//                String label = "Từ Ngày " + startDay + "->" + endDay;
//                chart.addData(new ModelChart(
//                        label,
//                        new Long[]{
//                                chartResult.get(0).get(i),
//                                chartResult.get(1).get(i),
//                                chartResult.get(2).get(i)}
//                ));
//            }
//        }

        // Làm mới giao diện
        chart.repaint();
        chart.validate();
        this.add(chart, BorderLayout.CENTER);
        this.repaint();
        this.validate();
    }

    private List<LocalDate[]> getWeekRanges(LocalDate startOfMonth, LocalDate endOfMonth) {
        List<LocalDate[]> weekRanges = new ArrayList<>();
        LocalDate[][] tempWeekRanges = new LocalDate[5][2];
        tempWeekRanges[0] = new LocalDate[]{startOfMonth, startOfMonth.plusDays(6)};
        tempWeekRanges[1] = new LocalDate[]{startOfMonth.plusDays(7), startOfMonth.plusDays(13)};
        tempWeekRanges[2] = new LocalDate[]{startOfMonth.plusDays(14), startOfMonth.plusDays(20)};
        tempWeekRanges[3] = new LocalDate[]{startOfMonth.plusDays(21), startOfMonth.plusDays(27)};
        tempWeekRanges[4] = new LocalDate[]{startOfMonth.plusDays(28), endOfMonth};

        for (LocalDate[] week : tempWeekRanges) {
            LocalDate from = week[0];
            LocalDate to = week[1].isAfter(endOfMonth) ? endOfMonth : week[1];
            if (!from.isAfter(endOfMonth)) {
                weekRanges.add(new LocalDate[]{from, to});
            }
        }
        return weekRanges;
    }
}
