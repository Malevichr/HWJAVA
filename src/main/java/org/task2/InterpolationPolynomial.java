package org.task2;

import java.util.ArrayList;
import java.util.Collections;

public interface InterpolationPolynomial extends Polynomial {
    InterpolationPolynomial addNodes(ArrayList<Node> nodes);

    InterpolationPolynomial addNode(Node node);

    abstract class Abstract extends Polynomial.Base implements InterpolationPolynomial {
        protected final ArrayList<Node> nodes;

        public Abstract(
                ArrayList<Node> nodes,
                CreatePolynomialCoefficients createPolynomialCoefficients
        ) {
            super(createPolynomialCoefficients.create(nodes));
            this.nodes = nodes;
        }
        protected Abstract(
                ArrayList<Node> nodes,
                Polynomial basePolynomial
        ) {
            super(basePolynomial.getCoefficients());
            this.nodes = nodes;
        }
    }

    class Lagrange extends Abstract {
        public Lagrange(ArrayList<Node> nodes) {
            super(nodes, new CreatePolynomialCoefficients.Lagrange());
        }

        @Override
        public InterpolationPolynomial addNodes(ArrayList<Node> nodes) {
            ArrayList<Node> newNodes = new ArrayList<>(this.nodes);
            newNodes.addAll(nodes);
            return new Lagrange(newNodes);
        }

        @Override
        public InterpolationPolynomial addNode(Node node) {
            return addNodes((ArrayList<Node>) Collections.singletonList(node));
        }
//
//        @Override
//        public double calculate(double x) {
//            ArrayList<Double> xVal = new ArrayList<Double>();
//            ArrayList<Double> yVal = new ArrayList<Double>();
//
//            for (Node node : nodes) {
//                xVal.add(node.x());
//                yVal.add(node.y());
//            }
//            double result = 0.0;
//            int n = nodes.size();
//
//            for (int i = 0; i < n; i++) {
//                double term = yVal.get(i);
//                for (int j = 0; j < n; j++) {
//                    if (j != i) {
//                        term *= (x - xVal.get(j)) / (xVal.get(i) - xVal.get(j));
//                    }
//                }
//                result += term;
//            }
//
//            return result;
//        }
    }

    class Newton extends Abstract {
        public Newton(ArrayList<Node> nodes) {
            super(nodes, new CreatePolynomialCoefficients.Newton());
        }
        private Newton(ArrayList<Node> nodes, Polynomial basePolynomial){
            super(nodes, basePolynomial);
        }
        @Override
        public InterpolationPolynomial addNodes(ArrayList<Node> nodes) {
            InterpolationPolynomial newPolynomial = this;
            for (Node node : nodes){
                newPolynomial = newPolynomial.addNode(node);
            }
            return newPolynomial;
        }

        @Override
        public InterpolationPolynomial addNode(Node node) {
            Polynomial result = new Polynomial.Base(1.);
            for (Node value : nodes) {
                Polynomial term = new Base(-value.x(), 1.);
                result = result.times(term);
            }
            ArrayList<Node> newNodes = new ArrayList<>(nodes);
            newNodes.add(node);

            double devidedDifference = 0.0;
            for(int j = 0; j < newNodes.size(); j++){
                double denominator = 1.0;
                for (int i = 0; i < newNodes.size(); i++) {
                    if (i != j) {
                        denominator *= newNodes.get(j).x() - newNodes.get(i).x();
                    }
                }
                devidedDifference += newNodes.get(j).y()/denominator;
            }

            result = result.times(devidedDifference).plus(this);
            return new Newton(newNodes, result);
        }
    }
}
