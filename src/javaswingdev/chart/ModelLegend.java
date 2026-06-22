package javaswingdev.chart;

import java.awt.Color;

public class ModelLegend {
    private final String name;
    private final Color color1;
    private final Color color2;

    public ModelLegend(String name, Color color1, Color color2) {
        this.name = name;
        this.color1 = color1;
        this.color2 = color2;
    }

    public String getName() {
        return name;
    }

    public Color getColor1() {
        return color1;
    }

    public Color getColor2() {
        return color2;
    }
}
