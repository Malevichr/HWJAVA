package org.main;

import kotlin.Pair;

import java.util.ArrayList;
import java.util.List;

public class LagrangePolynomial {

    public static ArrayList<Double> getLagrangeCoefficients(ArrayList<Pair<Double, Double>> points) {
        int n = points.size();
        ArrayList<Double> coefficients = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            coefficients.add(0.0); // Инициализация нулевыми коэффициентами
        }

        for (int i = 0; i < n; i++) {
            ArrayList<Double> li = computeBasisPolynomial(points, i);
            for (int j = 0; j < li.size(); j++) {
                coefficients.set(j, coefficients.get(j) + points.get(i).getSecond() * li.get(j));
            }
        }

        return coefficients;
    }

    private static ArrayList<Double> computeBasisPolynomial(ArrayList<Pair<Double, Double>> points, int i) {
        int n = points.size();
        ArrayList<Double> basis = new ArrayList<>();
        basis.add(1.0); // Начинаем с константы L_i(x) = 1

        double xi = points.get(i).getFirst();

        for (int j = 0; j < n; j++) {
            if (j == i) continue;
            double xj = points.get(j).getFirst();
            basis = multiplyPolynomials(basis, new ArrayList<>(List.of(-xj / (xi - xj), 1 / (xi - xj))));
        }

        return basis;
    }

    private static ArrayList<Double> multiplyPolynomials(ArrayList<Double> poly1, ArrayList<Double> poly2) {
        ArrayList<Double> result = new ArrayList<>();
        for (int i = 0; i < poly1.size() + poly2.size() - 1; i++) {
            result.add(0.0); // Инициализация коэффициентов нулями
        }

        for (int i = 0; i < poly1.size(); i++) {
            for (int j = 0; j < poly2.size(); j++) {
                result.set(i + j, result.get(i + j) + poly1.get(i) * poly2.get(j));
            }
        }

        return result;
    }

    public static void main(String[] args) {
        ArrayList<Pair<Double, Double>> points = new ArrayList<>();
        points.add(new Pair<>(1.0, 2.0));
        points.add(new Pair<>(2.0, 3.0));
        points.add(new Pair<>(3.0, 5.0));

        ArrayList<Double> coefficients = getLagrangeCoefficients(points);

        System.out.println("Koefs");
        for (double coef : coefficients) {
            System.out.print(coef + " ");
        }
    }
}