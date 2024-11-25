package org.task2;


import java.util.ArrayList;
import java.util.List;

public interface Polynomial {
    int getPower();

    Polynomial times(double k);

    double calculate(double x);

    Polynomial times(Polynomial other);

    Polynomial minus(Polynomial other);

    Polynomial div(double k);

    Polynomial plus(Polynomial other);

    ArrayList<Double> getCoefficients();

    class Base implements Polynomial {
        private final ArrayList<Double> coefficients = new ArrayList<>();

        public ArrayList<Double> getCoefficients() {
            return (ArrayList<Double>) coefficients.clone();
        }

        public Base() {
            coefficients.add(0.0);
        }

        public Base(ArrayList<Double> coefficients) {
            this.coefficients.addAll(coefficients);
            correctCoefficients();
        }

        public Base(Double... coefficients) {
            this.coefficients.addAll(List.of(coefficients));
            correctCoefficients();
        }

        @Override
        public int getPower() {
            return coefficients.size() - 1;
        }

        private void correctCoefficients() {
            while (coefficients.size() > 1) {
                if (coefficients.get(getPower()) == 0) {
                    coefficients.remove(getPower());
                } else {
                    break;
                }
            }
        }

        @Override
        public String toString() {
            var sb = new StringBuilder();
            for (int i = getPower(); i >= 0; i--) {
                var k = coefficients.get(i);
                if (k != 0.0) {
                    if (k < 0)
                        sb.append("-");
                    else if (i != getPower())
                        sb.append("+");
                    if (Math.abs(k) != 1.0 || i == 0)
                        sb.append(Math.abs(k));
                    if (i > 0) sb.append('x');
                    if (i > 1) {
                        sb.append('^');
                        sb.append(i);
                    }
                }
            }
            if (sb.isEmpty()) sb.append('0');
            return sb.toString();
        }

        @Override
        public Polynomial.Base times(double k) {
            var l = new ArrayList<Double>();
            for (var c : coefficients) {
                l.add(c * k);
            }
            return new Polynomial.Base(l);
        }

        @Override
        public double calculate(double x) {
            double p = 1;
            double s = coefficients.getFirst();
            for (int i = 1; i < coefficients.size(); i++) {
                p *= x;
                s += coefficients.get(i) * p;
            }
            return s;
        }

        @Override
        public Polynomial times(Polynomial other) {
            // (ax^2 + bx + c)(dx + e) = adx^3+bdx^2+cdx+aex^2+bex+ce=adx^3+(bd+ae)x^2+(cd+be)x+ce
            var c = new ArrayList<Double>(coefficients.size() + other.getCoefficients().size());
            for (int i = 0; i < coefficients.size() + other.getCoefficients().size(); i++) {
                c.add(0.);
            }
            for (int i = 0; i < coefficients.size(); i++) {
                for (int j = 0; j < other.getCoefficients().size(); j++) {
                    c.set(i + j, c.get(i + j) + coefficients.get(i) * other.getCoefficients().get(j));
                }
            }
            return new Polynomial.Base(c);
        }

        @Override
        public Polynomial minus(Polynomial other) {
            return plus(other.times(-1.));
        }

        @Override
        public Polynomial div(double k) {
            return times(1. / k);
        }

        @Override
        public Polynomial plus(Polynomial other) {
            var c = new ArrayList<>(
                    getPower() >= other.getPower() ? coefficients : other.getCoefficients()
            );
            if (getPower() >= other.getPower()) {
                for (int i = 0; i < other.getCoefficients().size(); i++) {
                    c.set(i, other.getCoefficients().get(i) + c.get(i)); // c[i] = other.coeffs[i] + c[i]
                }
            } else {
                for (int i = 0; i < coefficients.size(); i++) {
                    c.set(i, coefficients.get(i) + c.get(i));
                }
            }
            return new Polynomial.Base(c);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj != null && obj.getClass() == this.getClass()) {
                return obj.hashCode() == hashCode();
            }
            return false;
        }

        @Override
        public int hashCode() {
            return 31 * coefficients.hashCode();
        }
    }
}