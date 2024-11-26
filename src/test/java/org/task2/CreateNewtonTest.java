package org.task2;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CreateNewtonTest {

    @Test
    void testSingleNode() {
        ArrayList<Node> nodes = new ArrayList<>(List.of(
                new Node(2.0, 5.0)
        ));

        CreatePolynomialCoefficients newton = new CreatePolynomialCoefficients.Newton();
        ArrayList<Double> coefficients = newton.create(nodes);

        // Ожидается полином: P(x) = 5
        assertEquals(1, coefficients.size());
        assertEquals(5.0, coefficients.getFirst(), 1e-9);
    }

    @Test
    void testTwoNodes() {
        ArrayList<Node> nodes = new ArrayList<>(Arrays.asList(
                new Node(1.0, 2.0),
                new Node(3.0, 4.0)
        ));

        CreatePolynomialCoefficients newton = new CreatePolynomialCoefficients.Newton();
        ArrayList<Double> coefficients = newton.create(nodes);

        // Ожидается полином: P(x) = x + 1
        assertEquals(2, coefficients.size());
        assertEquals(1.0, coefficients.get(1), 1e-9); // Коэффициент при x
        assertEquals(1.0, coefficients.get(0), 1e-9); // Свободный член
    }

    @Test
    void testThreeNodes() {
        ArrayList<Node> nodes = new ArrayList<>(Arrays.asList(
                new Node(0.0, 1.0),
                new Node(1.0, 0.0),
                new Node(2.0, 1.0)
        ));

        CreatePolynomialCoefficients newton = new CreatePolynomialCoefficients.Newton();
        ArrayList<Double> coefficients = newton.create(nodes);

        // Ожидается полином: P(x) = x^2 - 2x + 1
        assertEquals(3, coefficients.size());
        assertEquals(1.0, coefficients.get(2), 1e-9); // Коэффициент при x^2
        assertEquals(-2.0, coefficients.get(1), 1e-9); // Коэффициент при x
        assertEquals(1.0, coefficients.get(0), 1e-9); // Свободный член
    }

    @Test
    void testCustomNodes() {
        ArrayList<Node> nodes = new ArrayList<>(Arrays.asList(
                new Node(-1.0, -2.0),
                new Node(0.0, 0.0),
                new Node(1.0, 2.0)
        ));

        CreatePolynomialCoefficients newton = new CreatePolynomialCoefficients.Newton();
        ArrayList<Double> coefficients = newton.create(nodes);

        // Ожидается полином: P(x) = 2x
        assertEquals(2, coefficients.size());
        assertEquals(2.0, coefficients.get(1), 1e-9); // Коэффициент при x
        assertEquals(0.0, coefficients.get(0), 1e-9); // Свободный член
    }

    @Test
    void testComplexCase() {
        ArrayList<Node> nodes = new ArrayList<>(Arrays.asList(
                new Node(1.0, 1.0),
                new Node(2.0, 4.0),
                new Node(3.0, 9.0)
        ));

        CreatePolynomialCoefficients newton = new CreatePolynomialCoefficients.Newton();
        ArrayList<Double> coefficients = newton.create(nodes);

        // Ожидается полином: P(x) = x^2
        assertEquals(3, coefficients.size());
        assertEquals(1.0, coefficients.get(2), 1e-9); // Коэффициент при x^2
        assertEquals(0.0, coefficients.get(1), 1e-9); // Коэффициент при x
        assertEquals(0.0, coefficients.get(0), 1e-9); // Свободный член
    }
}
