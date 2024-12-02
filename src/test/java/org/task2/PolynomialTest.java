package org.task2;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class PolynomialTest {

    @BeforeEach
    void setUp() {
        System.out.println("Starting test");
    }

    @AfterEach
    void tearDown() {
        System.out.println("Test Finished");
    }

    @Test
    void testToString() {
        Polynomial p1 = new Polynomial.Base.Base(0.);
        Assertions.assertEquals("0", p1.toString());
        Polynomial p2 = new Polynomial.Base(0., 1.);
        Assertions.assertEquals("x", p2.toString());

    }

    @Test
    void testTimes() {
        Polynomial p = new Polynomial.Base(1., 0.5, -2., 0.);
        Polynomial p2 = new Polynomial.Base(2., 1., -4., 0.);
        Polynomial pk = p.times(2.);
        Assertions.assertEquals(p2, pk);
    }

    @Test
    void testPlus() {
        Polynomial p = new Polynomial.Base(1., 0.5, -2., 0.);
        Polynomial p2 = new Polynomial.Base(2., 1., -4., 0.);
        Polynomial p3 = new Polynomial.Base(-2., -1., 4.);
        Polynomial p4 = new Polynomial.Base(2.);
        Polynomial pk = p.plus(p2);
        Polynomial pr = new Polynomial.Base(3., 1.5, -6., 0.);
        Polynomial pk2 = p2.plus(p3);
        Polynomial pk3 = p3.plus(p4);
        Polynomial pk4 = p4.plus(p3);
        Polynomial pr2 = new Polynomial.Base(0., -1., 4.);
        Assertions.assertEquals(pr, pk);
        Assertions.assertEquals(new Polynomial.Base(), pk2);
        Assertions.assertEquals(pr2, pk3);
        Assertions.assertEquals(pr2, pk4);
    }

    @Test
    void testGetPower() {
        Polynomial p = new Polynomial.Base();
        Assertions.assertEquals(0, p.getPower());
        Polynomial p1 = new Polynomial.Base(1., 1.);
        Assertions.assertEquals(1, p1.getPower());
        Polynomial p2 = new Polynomial.Base(2., 1., -4., 0.);
        Assertions.assertEquals(2, p2.getPower());
    }

    @Test
    void testTimesPolynomial() {
        Polynomial p = new Polynomial.Base(1., 2.);
        Polynomial p1 = new Polynomial.Base(1., 0., 2.);
        //(2x+1)(2x^2+1)= 4x^3+2x+2x^2+1
        Polynomial tp = new Polynomial.Base(1., 2., 2., 4.);
        Assertions.assertEquals(tp, p.times(p1));
        Polynomial p2 = new Polynomial.Base();
        Assertions.assertEquals(new Polynomial.Base(), p.times(p2));

        Polynomial pi1 = new Polynomial.Base(1.);
        Polynomial pi2 = new Polynomial.Base(-2., 1.);

        System.out.println(pi1);
        System.out.println(pi2);
        System.out.println(pi1.times(pi2));
    }

    @Test
    void myTest() {
        ArrayList<Node> nodes = new ArrayList<>(
                List.of(
                        new Node(0.0, 0.0),
                        new Node(1.0, 1.0)
                )
        );
        InterpolationPolynomial newton = new InterpolationPolynomial.Newton(nodes);
        System.out.println(newton);
        newton = newton.addNode(new Node(3., 4.));
        System.out.println(newton);
        ArrayList<Node> nodes2 = new ArrayList<>(
                List.of(
                        new Node(0.0, 0.0),
                        new Node(1.0, 1.0),
                        new Node(3.0, 4.0)
                )
        );
        InterpolationPolynomial.Newton newton2 = new InterpolationPolynomial.Newton(nodes2);
        System.out.println(newton2);
    }
}
