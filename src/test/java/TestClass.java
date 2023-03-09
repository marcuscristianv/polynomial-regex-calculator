import org.junit.Assert;
import org.junit.jupiter.api.*;

public class TestClass {
    Polinom p1;
    Polinom p2;

    @BeforeEach
    void setUp() {
        p1 = new Polinom();
        p2 = new Polinom();
    }

    @Test
    public void testAdunare() {
        p1.parseString("x^5-3x+2");
        p2.parseString("-x^2-4x+10");

        Assert.assertTrue(p1.addition(p2).toString().equals("x^5-x^2-7x+12"));
    }

    @Test
    public void testScadere() {
        p1.parseString("-x^10+5x^2-7x+9");
        p2.parseString("x^8+5x^2+2x");

        Assert.assertTrue(p1.subtraction(p2).toString().equals("-x^10-x^8-9x+9"));
    }

    @Test
    public void testInmultire() {
        p1.parseString("x^3+6x-2");
        p2.parseString("x^8+2x^2+8");

        Assert.assertTrue(p1.multiplication(p2).toString().equals("x^11+6x^9-2x^8+2x^5+20x^3-4x^2+48x-16"));
    }

    @Test
    public void testImpartire() {
        p1.parseString("x^3-2x+1");
        p2.parseString("-5x^2-10x-6");

        Assert.assertTrue(p1.division(p2).get(0).toString().equals("-0.2x+0.4")); // catul impartirii
        Assert.assertTrue(p1.division(p2).get(1).toString().equals("0.8x+3.4")); // restul impartirii
    }

    @Test
    public void testDerivare() {
        p1.parseString("-3x^4+x^3+3");

        Assert.assertTrue(p1.derivative().toString().equals("-12x^3+3x^2"));
    }

    @Test
    public void testIntegrare() {
        p1.parseString("6x^3-x+1");

        Assert.assertTrue(p1.integration().toString().equals("1.5x^4-0.5x^2+x"));
    }
}
