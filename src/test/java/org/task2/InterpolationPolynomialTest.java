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
        new InterpolationPolynomial.Lagrange(nodesGreat);
        System.out.println(System.currentTimeMillis() - startTime);

        startTime = System.currentTimeMillis();
        new InterpolationPolynomial.Newton(nodesGreat);
        System.out.println(System.currentTimeMillis() - startTime);
    }
}
