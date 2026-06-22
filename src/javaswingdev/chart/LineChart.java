/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaswingdev.chart;

/**
 *
 * @author ArtoCO
 */

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.MouseEvent;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javaswingdev.chart.blankchart.BlankPlotChart;
import javaswingdev.chart.blankchart.BlankPlotChatRender;
import javaswingdev.chart.blankchart.SeriesSize;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;
import org.jdesktop.animation.timing.interpolation.PropertySetter;



public class LineChart extends javax.swing.JPanel {

    DecimalFormat df = new DecimalFormat("#,##0.##");
    private List<ModelLegend> legends = new ArrayList<>();
    private List<ModelChart> model = new ArrayList<>();
    private final int seriesSize = 18;
    private final int seriesSpace = 0;
    private Animator animator;
    private float animate;
    private String chartTitle = "SALES STATUS";  // Default title
    private javax.swing.JLabel labelTitle;
    private Animator animatorLabel;
    private TimingTarget targetLabel;
    private String showLabel;
    private Point labelLocation = new Point();

    private int overIndex = -1;


    public LineChart() {
        initComponents();
        
        setOpaque(false);
    
       createAnimatorChart();   // ✅ add this
       createAnimatorLabel();   // ✅ add this
  
        setLayout(new BorderLayout());

JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
topPanel.setOpaque(false);

// Create and style the title label
JLabel labelTitle = new JLabel("SALES STATUS");
labelTitle.setFont(getFont().deriveFont(Font.BOLD, 14f));
labelTitle.setForeground(new Color(255,255,255));

// Add title and legends to top panel
topPanel.add(labelTitle);
topPanel.add(panelLegend);  // Already populated with LegendItems

add(topPanel, BorderLayout.NORTH);         // Top panel with title + legend
add(blankPlotChart, BorderLayout.CENTER);  // Chart itself

        
        // Enable zoom in/out using mouse wheel
    blankPlotChart.addMouseWheelListener(evt -> {
    int notches = evt.getWheelRotation();
    double currentMax = blankPlotChart.getMaxValues();
    double newMax;

    if (notches < 0) {
        // Scroll up - zoom in (limit min)
        newMax = Math.max(10, currentMax * 0.9);
    } else {
        // Scroll down - zoom out
        newMax = currentMax * 1.1;
    }

    blankPlotChart.setMaxValues(newMax);
});


addMouseWheelListener(e -> {
    if (e.getPreciseWheelRotation() < 0) {
        zoomIn();
    } else {
        zoomOut();
    }
});




        setOpaque(false);
        TimingTarget target = new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                animate = fraction;
                repaint();
            }
        };
        animator = new Animator(800, target);
        animator.setResolution(0);
        animator.setAcceleration(0.5f);
        animator.setDeceleration(0.5f);
        blankPlotChart.getNiceScale().setMaxTicks(5);
        blankPlotChart.setBlankPlotChatRender(new BlankPlotChatRender() {
            @Override
            public int getMaxLegend() {
                return legends.size();
            }

            @Override
            public String getLabelText(int index) {
                return model.get(index).getLabel();
            }

            @Override
            public void renderSeries(BlankPlotChart chart, Graphics2D g2, SeriesSize size, int index) {
            }

            @Override
            public void renderSeries(BlankPlotChart chart, Graphics2D g2, SeriesSize size, int index, List<Path2D.Double> gra) {
                double totalSeriesWidth = (seriesSize * legends.size()) + (seriesSpace * (legends.size() - 1));
                double x = (size.getWidth() - totalSeriesWidth) / 2;
                
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
                int ss = seriesSize / 2;
                for (int i = 0; i < legends.size(); i++) {
                    double seriesValues = chart.getSeriesValuesOf(model.get(index).getValues()[i], size.getHeight()) * animate;
                    if (index == 0) {
                        gra.get(i).moveTo(size.getX() + x + ss, size.getY() + size.getHeight() - seriesValues);
                    } else {
                        gra.get(i).lineTo(size.getX() + x + ss, size.getY() + size.getHeight() - seriesValues);
                    }
                    x += seriesSpace + seriesSize;
                }
                if (showLabel != null) {
                   g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
                   Dimension s = getLabelWidth(showLabel, g2);
                   int space = 3;
                   int spaceTop = 5;

                   g2.setColor(new Color(70, 70, 70)); // same as BarChart
                   g2.fillRoundRect(labelLocation.x - s.width / 2 - 3,
                     labelLocation.y - s.height - space * 2 - spaceTop,
                     s.width + space * 2,
                     s.height + space * 2,
                     10, 10);

                   g2.setColor(new Color(200, 200, 200));
                   g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
                   g2.drawString(showLabel, labelLocation.x - s.width / 2, labelLocation.y - spaceTop - space * 2);
                }

                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
            }

            @Override
            public void renderGraphics(Graphics2D g2, List<Path2D.Double> gra) {
                g2.setStroke(new BasicStroke(3f));
                for (int i = 0; i < gra.size(); i++) {
                    g2.setPaint(new GradientPaint(0, 0, legends.get(i).getColor1(), getWidth(), 0, legends.get(i).getColor2()));
                    g2.draw(gra.get(i));
                }
            }

            @Override
public boolean mouseMoving(BlankPlotChart chart, MouseEvent evt, Graphics2D g2, SeriesSize size, int index) {
    double totalSeriesWidth = (seriesSize * legends.size()) + (seriesSpace * (legends.size() - 1));
    double x = (size.getWidth() - totalSeriesWidth) / 2;

    for (int i = 0; i < legends.size(); i++) {
        double data = model.get(index).getValues()[i];
        double seriesValue = chart.getSeriesValuesOf(data, size.getHeight()) * animate;

        int s = seriesSize / 2;
        int sy = seriesSize / 3;

        Polygon hoverZone = new Polygon(
            new int[] {
                (int) (size.getX() + x),
                (int) (size.getX() + x + s),
                (int) (size.getX() + x + seriesSize),
                (int) (size.getX() + x + seriesSize),
                (int) (size.getX() + x + s),
                (int) (size.getX() + x)
            },
            new int[] {
                (int) (size.getY() + size.getHeight() - seriesValue),
                (int) (size.getY() + size.getHeight() - seriesValue - sy),
                (int) (size.getY() + size.getHeight() - seriesValue),
                (int) (size.getY() + size.getHeight()),
                (int) (size.getY() + size.getHeight() + sy),
                (int) (size.getY() + size.getHeight())
            },
            6
        );

        if (hoverZone.contains(evt.getPoint())) {
            showLabel = df.format(data);
            Point newPoint = new Point((int) (size.getX() + x + s), (int) (size.getY() + size.getHeight() - seriesValue - sy));

            int currentIndex = index * legends.size() + i;
            if (currentIndex != overIndex) {
                Point oldPoint = overIndex == -1 ? new Point(newPoint.x, newPoint.y + 10) : labelLocation;
                startAnimateLabel(oldPoint, newPoint);
                overIndex = currentIndex;
            }

            chart.repaint();
            return true;
        }
        x += seriesSpace + seriesSize;
    }

    overIndex = -1;
    return false;
}


        });
    }
    
    @Override
protected void paintComponent(java.awt.Graphics g) {
    super.paintComponent(g);
    if (chartTitle != null && !chartTitle.isEmpty()) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(80, 80, 80));
        g2.setFont(getFont().deriveFont(java.awt.Font.BOLD, 18f));
        FontMetrics fm = g2.getFontMetrics();
        int x = (getWidth() - fm.stringWidth(chartTitle)) / 2;
        int y = fm.getHeight();
        g2.drawString(chartTitle, x, y);
    }
}


    public void addLegend(String name, Color color, Color color1) {
        ModelLegend data = new ModelLegend(name, color, color1);
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
    
    public void setChartTitle(String title) {
    this.chartTitle = title;
    repaint();  // Redraw to reflect title change
}


    public void clear() {
        animate = 0;
        showLabel = null;
        blankPlotChart.setLabelCount(0);
        model.clear();
        repaint();
    }

    public void start() {
        showLabel = null;
        if (!animator.isRunning()) {
            animator.start();
        }
    }

    private Dimension getLabelWidth(String text, Graphics2D g2) {
        FontMetrics ft = g2.getFontMetrics();
        Rectangle2D r2 = ft.getStringBounds(text, g2);
        return new Dimension((int) r2.getWidth(), (int) r2.getHeight());
    }
    
    public BlankPlotChart getBlankPlotChart() {
    return blankPlotChart;
}
    
    
    private double zoomFactor = 1.0;

public void zoomIn() {
    zoomFactor *= 1.2;
    updateZoom();
}

public void zoomOut() {
    zoomFactor /= 1.2;
    updateZoom();
}

private void updateZoom() {
    double baseMax = model.stream().mapToDouble(ModelChart::getMaxValues).max().orElse(100);
    blankPlotChart.setMaxValues(baseMax * zoomFactor);
    repaint();
}


private void createAnimatorLabel() {
    animatorLabel = new Animator(300);
    animatorLabel.setResolution(0);
    animatorLabel.setAcceleration(0.5f);
    animatorLabel.setDeceleration(0.5f);
}



private void createAnimatorChart() {
    TimingTarget target = new TimingTargetAdapter() {
        @Override
        public void timingEvent(float fraction) {
            animate = fraction;
            repaint();
        }
    };
    animator = new Animator(5000, target);  // ✅ FIXED: target is defined above
    animator.setResolution(0);
    animator.setAcceleration(0.5f);
    animator.setDeceleration(0.5f);
}

private void startAnimateLabel(Point oldLocation, Point newLocation) {
    if (animatorLabel != null && animatorLabel.isRunning()) {
        animatorLabel.stop();
    }
    targetLabel = new PropertySetter(this, "changeLocation", oldLocation, newLocation);
    animatorLabel.removeTarget(targetLabel);
    animatorLabel.addTarget(targetLabel);
    animatorLabel.start();
}

public void setChangeLocation(Point p) {
    this.labelLocation = p;
    repaint();
}

public void resetHover() {
    showLabel = null;
    overIndex = -1;
    repaint();
}



    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        blankPlotChart = new javaswingdev.chart.blankchart.BlankPlotChart();
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
    private javaswingdev.chart.blankchart.BlankPlotChart blankPlotChart;
    private javax.swing.JPanel panelLegend;
    // End of variables declaration//GEN-END:variables
}
