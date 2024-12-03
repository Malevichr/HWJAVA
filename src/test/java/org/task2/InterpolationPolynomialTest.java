package org.task2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InterpolationPolynomialTest {
    private final ArrayList<Node> nodesFirstSet = new ArrayList<>(
            List.of(
                    new Node(1.0, 1.0),
                    new Node(2.0, 3.0),
                    new Node(3.0, 4.0)
            )
    );
    private final ArrayList<Node> nodesSecondSet = new ArrayList<>(
            List.of(
                    new Node(4.0, 10.0),
                    new Node(5.0, 20.0),
                    new Node(6.0, 30.0)
            )
    );
    private final ArrayList<Node> nodesAll = new ArrayList<Node>(
            List.of(
                    new Node(1.0, 1.0),
                    new Node(2.0, 4.0),
                    new Node(3.0, 5.0),
                    new Node(4.0, 10.0),
                    new Node(5.0, 20.0),
                    new Node(6.0, 30.0)
            )
    );
    private final ArrayList<Node> nodesGreat = new ArrayList<>();

    @BeforeEach
    public void setup(){
        for (int i = 0; i <= 500; i++){
            nodesGreat.add(new Node((double) i, (double)i /2 ));
        }
    }

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
    @Test
    void timeTest(){
        long startTime = System.currentTimeMillis();
        InterpolationPolynomial lagrange = new InterpolationPolynomial.Lagrange(nodesGreat);
        System.out.println("Lagrange init time " + (System.currentTimeMillis() - startTime));
        startTime = System.currentTimeMillis();
        lagrange.addNode(new Node(1000., 2000.));
        System.out.println("Lagrange add time " + (System.currentTimeMillis() - startTime));

        startTime = System.currentTimeMillis();
        InterpolationPolynomial newton = new InterpolationPolynomial.Newton(nodesGreat);
        System.out.println("Newton init time " + (System.currentTimeMillis() - startTime));
        startTime = System.currentTimeMillis();
        newton.addNode(new Node(1000., 2000.));
        System.out.println("Newton add time " + (System.currentTimeMillis() - startTime));

    }
}
