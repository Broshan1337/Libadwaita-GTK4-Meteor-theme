package me.pindour.adwaita.api.animation;

import java.util.function.Function;

public enum Easing {
    LINEAR(t -> t),
    QUAD_OUT(t -> 1.0 - (1.0 - t) * (1.0 - t)),
    CUBIC_OUT(t -> 1.0 - Math.pow(1.0 - t, 3.0)),
    QUART_OUT(t -> 1.0 - Math.pow(1.0 - t, 4.0)),
    // libadwaita / GTK default: spring-like ease-out, cubic-bezier(0, 0, 0.2, 1)
    EASE_OUT(t -> cubicBezier(t, 0.0, 0.2, 1.0));

    private final Function<Double, Double> function;

    Easing(Function<Double, Double> function) {
        this.function = function;
    }

    public double apply(double t) {
        return function.apply(t);
    }

    /**
     * Evaluates a CSS-style cubic-bezier timing function with control points
     * P0 = (0, 0), P1 = (x1, y1), P2 = (x2, y2), P3 = (1, 1).
     *
     * <p>Given the elapsed time fraction {@code t} (the x-axis), solves for the
     * curve parameter and returns the corresponding progress value (the y-axis).
     */
    private static double cubicBezier(double t, double x1, double x2, double y2) {
        return cubicBezier(t, x1, 0.0, x2, y2);
    }

    private static double cubicBezier(double t, double x1, double y1, double x2, double y2) {
        if (t <= 0.0) return 0.0;
        if (t >= 1.0) return 1.0;

        // Solve for the parameter s such that bezierAxis(s, x1, x2) == t (Newton-Raphson, then bisection fallback).
        double s = t;
        for (int i = 0; i < 8; i++) {
            double x = bezierAxis(s, x1, x2) - t;
            double dx = bezierAxisDerivative(s, x1, x2);
            if (Math.abs(dx) < 1e-6) break;
            s -= x / dx;
        }
        s = Math.max(0.0, Math.min(1.0, s));

        return bezierAxis(s, y1, y2);
    }

    private static double bezierAxis(double s, double p1, double p2) {
        double u = 1.0 - s;
        // P0 = 0, P3 = 1
        return 3.0 * u * u * s * p1 + 3.0 * u * s * s * p2 + s * s * s;
    }

    private static double bezierAxisDerivative(double s, double p1, double p2) {
        double u = 1.0 - s;
        return 3.0 * u * u * p1 + 6.0 * u * s * (p2 - p1) + 3.0 * s * s * (1.0 - p2);
    }
}
