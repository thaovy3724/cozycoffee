package GUI.ThongKe;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.List;

import javax.swing.JPanel;

import BUS.ThongKeBUS;
import DTO.ThongKe.ThongKeTheoNamDTO;
import GUI.Component.Chart.BarChart.Chart;
import GUI.Component.Chart.BarChart.ModelChart;

/**
 * @author: huonglamcoder
 */

// HUONGNGUYEN 3/5
public class ThongKeLoiNhuanTheoNam extends JPanel {
    ThongKeBUS thongKeBUS;
    Chart chart;

    public ThongKeLoiNhuanTheoNam(ThongKeBUS thongKeBUS) {
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

    public void updateChart(int year) {
        List<ThongKeTheoNamDTO> chartData =
                thongKeBUS.getThongKeTheoNam(year);
        this.remove(chart);

        chart = new Chart();
        chart.addLegend("Chi", new Color(245, 189, 135));
        chart.addLegend("Doanh thu", new Color(135, 189, 245));
        chart.addLegend("Lợi nhuận", new Color(189, 135, 245));

        for (int i = 0; i < chartData.size(); i++) {

            chart.addData(new ModelChart(
                    "Tháng " + (i + 1),
                    new Long[] {
                            chartData.get(i).getChiPhi(),
                            chartData.get(i).getDoanhThu(),
                            chartData.get(i).getLoiNhuan()
                    }
            ));
        }
        chart.repaint();
        chart.validate();
        this.add(chart, BorderLayout.CENTER);
        this.repaint();
        this.validate();
    }
}
