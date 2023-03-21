import org.example.Calculator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class CalculatorTest {
    private Calculator calcul;
    public CalculatorTest() {
        calcul = new Calculator();
    }

    @Test
    void addTest(){
        int addingExpect = 5;
        Assertions.assertEquals(addingExpect, calcul.add(3,2));
    }

    @Test
    void multiTest(){
        int multExpect = 6;
        Assertions.assertEquals(multExpect, calcul.multiply(2,2));
    }

    @Test
    void subtractionTest(){
        int sebstrExpect = 6 - 2;
        Assertions.assertEquals(sebstrExpect,calcul.subtraction(6,2));
    }
    @Test
    void divisionTest(){
        Assertions.assertThrows(ArithmeticException.class,
                ()-> {
            calcul.division(8,0);
        });
    }


}
