//package GUI.Component.Chart.BarChart;
//
//public class ModelChart {
//
//    public String getLabel() {
//        return label;
//    }
//
//    public void setLabel(String label) {
//        this.label = label;
//    }
//
//    public double[] getValues() {
//        return values;
//    }
//
//    public void setValues(double[] values) {
//        this.values = values;
//    }
//
//    public ModelChart(String label, double[] values) {
//        this.label = label;
//        this.values = values;
//    }
//
//    public ModelChart() {
//    }
//
//    private String label;
//    private double values[];
//
//    public double getMaxValues() {
//        double max = 0;
//        for (double v : values) {
//            if (v > max) {
//                max = v;
//            }
//        }
//        return max;
//    }
//}

//HUONGNGUYEN 4/5
package GUI.Component.Chart.BarChart;

public class ModelChart {

    private String label;
    private Long[] values;

    public ModelChart() {
    }

    public ModelChart(String label, Long[] values) {
        this.label = label;
        this.values = values;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Long[] getValues() {
        return values;
    }

    public void setValues(Long[] values) {
        this.values = values;
    }

    public long getMaxValues() {
        long max = 0L;
        for (Long v : values) {
            if (v != null && v > max) {
                max = v;
            }
        }
        return max;
    }
}