//package GUI.Component.Chart.BarChart;
//
//import java.awt.Color;
//import java.awt.GradientPaint;
//import java.awt.Graphics2D;
//import java.util.ArrayList;
//import java.util.List;
//
//import GUI.Component.Chart.BarChart.BlankChart.BlankPlotChart;
//import GUI.Component.Chart.BarChart.BlankChart.BlankPlotChatRender;
//import GUI.Component.Chart.BarChart.BlankChart.SeriesSize;
//
//public class Chart extends javax.swing.JPanel {
//
//    private final List<ModelLegend> legends = new ArrayList<>();
//    private final List<ModelChart> model = new ArrayList<>();
//    private final int seriesSize = 12;
//    private final int seriesSpace = 6;
//
//    public Chart() {
//        initComponents();
//        blankPlotChart.setBlankPlotChatRender(new BlankPlotChatRender() {
//            @Override
//            public String getLabelText(int index) {
//                return model.get(index).getLabel();
//            }
//
//            @Override
//            public void renderSeries(BlankPlotChart chart, Graphics2D g2, SeriesSize size, int index) {
//                double totalSeriesWidth = (seriesSize * legends.size()) + (seriesSpace * (legends.size() - 1));
//                double x = (size.getWidth() - totalSeriesWidth) / 2;
//                for (int i = 0; i < legends.size(); i++) {
//                    ModelLegend legend = legends.get(i);
//                    GradientPaint gra = new GradientPaint(0, 0, new Color(86, 195, 250), 0, (int) (size.getY() + size.getHeight()), legend.getColor());
//                    g2.setPaint(gra);
//                    double seriesValues = chart.getSeriesValuesOf(model.get(index).getValues()[i], size.getHeight());
//                    g2.fillRect((int) (size.getX() + x), (int) (size.getY() + size.getHeight() - seriesValues), seriesSize, (int) seriesValues);
//                    x += seriesSpace + seriesSize;
//                }
//            }
//        });
//    }
//
//    public void addLegend(String name, Color color) {
//        ModelLegend data = new ModelLegend(name, color);
//        legends.add(data);
//        panelLegend.add(new LegendItem(data));
//        panelLegend.repaint();
//        panelLegend.revalidate();
//    }
//
//    public void addData(ModelChart data) {
//        model.add(data);
//        blankPlotChart.setLabelCount(model.size());
//        Long max = data.getMaxValues();
//        if (max > blankPlotChart.getMaxValues()) {
//            blankPlotChart.setMaxValues(max);
//        }
//    }
//
//    @SuppressWarnings("unchecked")
//    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
//    private void initComponents() {
//
//        blankPlotChart = new GUI.Component.Chart.BarChart.BlankChart.BlankPlotChart();
//        panelLegend = new javax.swing.JPanel();
//
//        setBackground(new java.awt.Color(255, 255, 255));
//
//        panelLegend.setOpaque(false);
//        panelLegend.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 0));
//
//        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
//        this.setLayout(layout);
//        layout.setHorizontalGroup(
//                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                        .addGroup(layout.createSequentialGroup()
//                                .addContainerGap()
//                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                                        .addComponent(panelLegend, javax.swing.GroupLayout.DEFAULT_SIZE, 573, Short.MAX_VALUE)
//                                        .addComponent(blankPlotChart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
//                                .addContainerGap())
//        );
//        layout.setVerticalGroup(
//                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                        .addGroup(layout.createSequentialGroup()
//                                .addContainerGap()
//                                .addComponent(blankPlotChart, javax.swing.GroupLayout.DEFAULT_SIZE, 342, Short.MAX_VALUE)
//                                .addGap(0, 0, 0)
//                                .addComponent(panelLegend, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
//                                .addContainerGap())
//        );
//    }// </editor-fold>//GEN-END:initComponents
//
//    // Variables declaration - do not modify//GEN-BEGIN:variables
//    private GUI.Component.Chart.BarChart.BlankChart.BlankPlotChart blankPlotChart;
//    private javax.swing.JPanel panelLegend;
//    // End of variables declaration//GEN-END:variables
//}
//

//HUONGNGUYEN 4/5 (ĐỪNG XÓA CODE CMT Ở TRÊN NHO PLZ)
package GUI.Component.Chart.BarChart;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import GUI.Component.Chart.BarChart.BlankChart.BlankPlotChart;
import GUI.Component.Chart.BarChart.BlankChart.BlankPlotChatRender;
import GUI.Component.Chart.BarChart.BlankChart.SeriesSize;

public class Chart extends javax.swing.JPanel {

    private final List<ModelLegend> legends = new ArrayList<>();
    private final List<ModelChart> model = new ArrayList<>();
    private final int seriesSize = 12;
    private final int seriesSpace = 6;

    public Chart() {
        initComponents();
        blankPlotChart.setBlankPlotChatRender(new BlankPlotChatRender() {
            @Override
            public String getLabelText(int index) {
                return model.get(index).getLabel();
            }

            @Override
            public void renderSeries(BlankPlotChart chart, Graphics2D g2, SeriesSize size, int index) {
                double totalSeriesWidth = (seriesSize * legends.size()) + (seriesSpace * (legends.size() - 1));
                double startX = (size.getWidth() - totalSeriesWidth) / 2;
                Long[] values = model.get(index).getValues();
                for (int i = 0; i < legends.size(); i++) {
                    ModelLegend legend = legends.get(i);
                    GradientPaint gra = new GradientPaint(0, 0, new Color(86, 195, 250), 0, (int) (size.getY() + size.getHeight()), legend.getColor());
                    g2.setPaint(gra);
                    double seriesValue = 0.0;
                    if (values != null && i < values.length && values[i] != null) {
                        seriesValue = chart.getSeriesValuesOf(values[i].doubleValue(), size.getHeight());
                        // Xử lý giá trị âm (vẽ dưới trục x nếu cần)
                        int yPosition = (int) (size.getY() + size.getHeight() - (seriesValue > 0 ? seriesValue : 0));
                        int height = (int) (seriesValue > 0 ? seriesValue : -seriesValue); // Chiều cao tuyệt đối
                        g2.fillRect((int) (size.getX() + startX + (i * (seriesSize + seriesSpace))), yPosition, seriesSize, height);
                    }
                }
            }
        });
    }

    public void addLegend(String name, Color color) {
        ModelLegend data = new ModelLegend(name, color);
        legends.add(data);
        panelLegend.add(new LegendItem(data));
        panelLegend.repaint();
        panelLegend.revalidate();
    }

    public void addData(ModelChart data) {
        model.add(data);
        blankPlotChart.setLabelCount(model.size());
        double max = data.getMaxValues();
        if (max > blankPlotChart.getMaxValues()) {
            blankPlotChart.setMaxValues(max);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold collapsed desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        blankPlotChart = new GUI.Component.Chart.BarChart.BlankChart.BlankPlotChart();
        panelLegend = new javax.swing.JPanel();

        setBackground(new java.awt.Color(255, 255, 255));

        panelLegend.setOpaque(false);
        panelLegend.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 0));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(panelLegend, javax.swing.GroupLayout.DEFAULT_SIZE, 573, Short.MAX_VALUE)
                                        .addComponent(blankPlotChart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(blankPlotChart, javax.swing.GroupLayout.DEFAULT_SIZE, 342, Short.MAX_VALUE)
                                .addGap(0, 0, 0)
                                .addComponent(panelLegend, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private GUI.Component.Chart.BarChart.BlankChart.BlankPlotChart blankPlotChart;
    private javax.swing.JPanel panelLegend;
    // End of variables declaration//GEN-END:variables
}