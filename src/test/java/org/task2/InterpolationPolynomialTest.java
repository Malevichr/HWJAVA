package org.task2;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InterpolationPolynomialTest {
    private final ArrayList<Node> nodesFirstSet = new ArrayList<>(
            List.of(
                    new Node(1.0, 1.0),
                    new Node(2.0, 3.0),
                    new Node(3.0, 4.0)
            )
    );
    private final ArrayList<Node> nodesSecondSet = new ArrayList<>(
            List.of(
                    new Node(4.0, 1.0),
                    new Node(3.0, 2.0),
                    new Node(2.0, 1.0)
            )
    );
    private final ArrayList<Node> nodesAll = new ArrayList<>(
            List.of(
                    new Node(4.0, 1.0),
                    new Node(3.0, 2.0),
                    new Node(2.0, 1.0),
                    new Node(4.0, 1.0),
                    new Node(3.0, 2.0),
                    new Node(2.0, 1.0)
            )
    );
    @Test
    void lagrangeTest(){
        InterpolationPolynomial lagrangePolynomial = new InterpolationPolynomial.Lagrange(nodesFirstSet);
        InterpolationPolynomial actual = lagrangePolynomial.addNodes(nodesSecondSet);
        InterpolationPolynomial expected = new InterpolationPolynomial.Lagrange(nodesAll);
        assertEquals(expected, actual);
    }
    @Test
    void newtonTest(){
        InterpolationPolynomial newtonPolynomial = new InterpolationPolynomial.Newton(nodesFirstSet);
        InterpolationPolynomial actual = newtonPolynomial.addNodes(nodesSecondSet);
        InterpolationPolynomial expected = new InterpolationPolynomial.Newton(nodesAll);
        assertEquals(expected, actual);
    }
}
