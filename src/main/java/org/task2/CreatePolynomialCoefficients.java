package org.task2;

import java.util.ArrayList;

public interface CreatePolynomialCoefficients {
    ArrayList<Double> create(ArrayList<Node> nodes);

    final class Lagrange implements CreatePolynomialCoefficients {

        @Override
        public ArrayList<Double> create(ArrayList<Node> nodes) {
            Polynomial polynomial = new Polynomial.Base(); // Итоговый полином
            int n = nodes.size();

            for (int i = 0; i < n; i++) {
                double denominator = 1.0;
                Polynomial term = new Polynomial.Base(1.0); // Стартуем с константы 1

                for (int j = 0; j < n; j++) {
                    if (i != j) {
                        // Создаем полином (x - xj)
                        Polynomial factor = new Polynomial.Base(-nodes.get(j).x(), 1.0);
                        term = term.times(factor); // Умножаем текущий член на (x - xj)
                        denominator *= (nodes.get(i).x() - nodes.get(j).x());
                    }
                }

                // Умножаем текущий базисный полином на yi/denominator
                term = term.times(nodes.get(i).y() / denominator);
                polynomial = polynomial.plus(term); // Складываем с итоговым полиномом
            }

            return polynomial.getCoefficients(); // Возвращаем коэффициенты полинома
        }
    }

    final class Newton implements CreatePolynomialCoefficients {
        @Override
        public ArrayList<Double> create(ArrayList<Node> nodes) {
            int n = nodes.size();
            // Вычисляем разделённые разности
            double[][] dividedDifferences = new double[n][n];

            // Заполняем первую колонку значениями y
            for (int i = 0; i < n; i++) {
                dividedDifferences[i][0] = nodes.get(i).y();
            }

            // Заполняем таблицу разделённых разностей
            for (int j = 1; j < n; j++) {
                for (int i = 0; i < n - j; i++) {
                    dividedDifferences[i][j] = (dividedDifferences[i + 1][j - 1] - dividedDifferences[i][j - 1]) /
                            (nodes.get(i + j).x() - nodes.get(i).x());
                }
            }

            // Коэффициенты итогового полинома
            ArrayList<Double> coefficients = new ArrayList<>();
            coefficients.add(dividedDifferences[0][0]); // Свободный член

            // Создаем итоговый полином
            Polynomial result = new Polynomial.Base(coefficients);

            for (int i = 1; i < n; i++) {
                Polynomial term = new Polynomial.Base(1.0);
                for (int j = 0; j < i; j++) {
                    // Умножаем текущий член на (x - xj)
                    term = term.times(new Polynomial.Base(-nodes.get(j).x(), 1.0));
                }
                // Умножаем базисный полином на коэффициент
                term = term.times(dividedDifferences[0][i]);
                result = result.plus(term);
            }

            return result.getCoefficients();
        }
    }
}