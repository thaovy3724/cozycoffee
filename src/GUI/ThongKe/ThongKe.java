package GUI.ThongKe;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import BUS.ThongKeBUS;

/**
 * @author: huonglamcoder
 */

// HUONGNGUYEN 3/5
public class ThongKe extends JScrollPane {
    private final ThongKeBUS thongKeBUS = new ThongKeBUS();
    private final Color backgroundColor = new Color(255, 240, 220);

    private ThongKeLoiNhuanTheoNam thongKeLoiNhuanTheoNam;
    private ThongKeLoiNhuanTheoThang thongKeLoiNhuanTheoThang;

    private JPanel currentChartPanel;

    public ThongKe() {
        initComponent();
    }

    public void initComponent() {
        this.setBackground(backgroundColor);

        thongKeLoiNhuanTheoNam = new ThongKeLoiNhuanTheoNam(thongKeBUS);
        thongKeLoiNhuanTheoThang = new ThongKeLoiNhuanTheoThang(thongKeBUS);

        currentChartPanel = thongKeLoiNhuanTheoNam;
        this.setViewportView(currentChartPanel);
    }

    public void updateChartByYear(int year) {
        thongKeLoiNhuanTheoNam.updateChart(year);
        if (currentChartPanel != thongKeLoiNhuanTheoNam) {
            currentChartPanel = thongKeLoiNhuanTheoNam;
            this.setViewportView(currentChartPanel);
        }
    }

    public void updateChartByMonth(int year, int month) {
        thongKeLoiNhuanTheoThang.updateChart(year, month);
        if (currentChartPanel != thongKeLoiNhuanTheoThang) {
            currentChartPanel = thongKeLoiNhuanTheoThang;
            this.setViewportView(currentChartPanel);
        }
    }
}
