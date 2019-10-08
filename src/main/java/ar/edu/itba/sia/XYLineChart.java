package ar.edu.itba.sia;

import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;

import static java.awt.Toolkit.getDefaultToolkit;

public class XYLineChart extends ApplicationFrame {

    private final XYPlot plot;

    public XYLineChart(String applicationTitle, String chartTitle, String xAxisLabel, String yAxisLabel,
                       XYSeries xySeries) {
        super(applicationTitle);
        JFreeChart xylineChart = ChartFactory.createXYLineChart(
                chartTitle,
                xAxisLabel,
                yAxisLabel,
                createDataset(xySeries),
                PlotOrientation.VERTICAL,
                true , true , false);

        ChartPanel chartPanel = new ChartPanel(xylineChart);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        chartPanel.setPreferredSize( new java.awt.Dimension(screenSize.width, screenSize.height));
        plot = xylineChart.getXYPlot();

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesStroke( 0, new BasicStroke(1.0f));
        renderer.setSeriesShape(0, new Ellipse2D.Double());
        plot.setRenderer(renderer);
        setContentPane(chartPanel);

        pack();
        setVisible(true);
    }

    public XYLineChart(String applicationTitle, String chartTitle, String xAxisLabel, String yAxisLabel,
                       ArrayList<XYSeries> seriesList) {
        super(applicationTitle);
        JFreeChart xylineChart = ChartFactory.createXYLineChart(
                chartTitle,
                xAxisLabel,
                yAxisLabel,
                createDataset(seriesList),
                PlotOrientation.VERTICAL,
                true , true , false);

        ChartPanel chartPanel = new ChartPanel(xylineChart);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        chartPanel.setPreferredSize( new java.awt.Dimension(screenSize.width, screenSize.height));
        plot = xylineChart.getXYPlot();

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        List<Color> colors = new ArrayList<>();
        colors.add(Color.RED);
        colors.add(Color.DARK_GRAY);
        colors.add(Color.GREEN);
        colors.add(Color.YELLOW);
        colors.add(Color.MAGENTA);
        for(int i=0; i<seriesList.size(); i++){
            renderer.setSeriesPaint(i, colors.get(i%colors.size()));
            renderer.setSeriesStroke( i, new BasicStroke(1.0f));
            renderer.setSeriesShape(i, new Ellipse2D.Double());
        }
        plot.setRenderer(renderer);
        setContentPane(chartPanel);
        pack();
        setVisible(true);
    }

    private XYDataset createDataset(XYSeries xySeries) {
        final XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(xySeries);
        return dataset;
    }

    private XYDataset createDataset(ArrayList<XYSeries> xySeries) {
        final XYSeriesCollection dataset = new XYSeriesCollection();
        xySeries.forEach(dataset::addSeries);
        return dataset;
    }

    public XYPlot getPlot(){
        return plot;
    }
}
