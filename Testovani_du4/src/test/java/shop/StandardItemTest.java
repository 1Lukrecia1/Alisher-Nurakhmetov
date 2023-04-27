package shop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class StandardItemTest {

    @Test
    public void constructorTest(){
        StandardItem stanItem = new StandardItem(23, "TestName", 2.2314f, "Some Category", 10);
        Assertions.assertEquals(23, stanItem.getID());
        Assertions.assertEquals("TestName", stanItem.getName());
        Assertions.assertEquals(2.2314f, stanItem.getPrice());
        Assertions.assertEquals("Some Category", stanItem.getCategory());
        Assertions.assertEquals(10, stanItem.getLoyaltyPoints());
    }
    @Test
    void testToString() {
    }

    @Test
    void getLoyaltyPoints() {
    }

    @Test
    void setLoyaltyPoints() {
    }
    @ParameterizedTest(name = "Item {0} = Item {5} is {10}")
    @CsvSource({"1, a, 1F, a, 1, 1, a, 1F, a, 1, true",
            "1, a, 1F, a, 1, 2, b, 2F, b, 2, false",
            "1, a, 1F, a, 1, 1, a, 1F, a, 2, false"})
    void testEquals( int id1, String name1, float price1, String category1, int loyaltyPoints1,
                     int id2, String name2, float price2, String category2, int loyaltyPoints2,
                     boolean equality) {
        StandardItem standardItem1 = new StandardItem(id1, name1, price1, category1, loyaltyPoints1);
        StandardItem standardItem2 = new StandardItem(id2, name2, price2, category2, loyaltyPoints2);

        //ACT
        boolean item1isEqualItem2 = standardItem1.equals(standardItem2);

        //ASSERT
        Assertions.assertEquals(equality, item1isEqualItem2);
    }

    @Test
    void copyTest() {
        StandardItem stItem1 = new StandardItem(23, "TestName", 2.2314f, "Some Category", 10);
        StandardItem stItem2 = stItem1.copy();
        Assertions.assertEquals(stItem1,stItem2);
    }
}