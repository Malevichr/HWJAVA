package org.task2;

import java.util.ArrayList;

public interface InterpolationPolynomial extends Polynomial {
    InterpolationPolynomial addNodes(ArrayList<Node> nodes);

    abstract class Abstract extends Polynomial.Base implements InterpolationPolynomial {
        protected final ArrayList<Node> nodes;

        public Abstract(
                ArrayList<Node> nodes,
                CreatePolynomialCoefficients createPolynomialCoefficients
        ) {
            super(createPolynomialCoefficients.create(nodes));
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
    }
    class Newton extends Abstract{
        public Newton(ArrayList<Node> nodes){
            super(nodes, new CreatePolynomialCoefficients.Newton());
        }
        @Override
        public InterpolationPolynomial addNodes(ArrayList<Node> nodes) {
            ArrayList<Node> newNodes = new ArrayList<>(this.nodes);
            newNodes.addAll(nodes);
            return new Newton(newNodes);
        }
    }
}
