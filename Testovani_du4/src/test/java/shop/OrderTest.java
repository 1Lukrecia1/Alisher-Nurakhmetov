package shop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class OrderTest {
    @Test
    public void testConstructor(){
        ShoppingCart mockedShoppingCart = mock(ShoppingCart.class);

        Order order = new Order(mockedShoppingCart, "testCust1","address 1");
        verify(mockedShoppingCart,times(1)).getCartItems();
        Assertions.assertEquals("testCust1", order.getCustomerName());
        Assertions.assertEquals("address 1", order.getCustomerAddress());
        Assertions.assertEquals(0, order.getState());
    }

    @Test
    public void testConstructor_nullValues(){
        ShoppingCart mockedShoppingCart = mock(ShoppingCart.class);

        Order order2 = new Order(mockedShoppingCart, null, null);

        verify(mockedShoppingCart,times(1)).getCartItems();
        Assertions.assertNull(order2.getCustomerName());
        Assertions.assertNull(order2.getCustomerAddress());
        Assertions.assertEquals(0, order2.getState());
    }

    @Test
    public void testConstructorWithState(){
        
        ShoppingCart mockedShoppingCart = mock(ShoppingCart.class);

        
        Order order1 = new Order(mockedShoppingCart, "testCust1", "address 1", 1);

        
        verify(mockedShoppingCart,times(1)).getCartItems();

        Assertions.assertEquals(1, order1.getState());
        Assertions.assertEquals("testCust1", order1.getCustomerName());
        Assertions.assertEquals("address 1", order1.getCustomerAddress());

        Order order2 = new Order(mockedShoppingCart, null, null, 12);
        verify(mockedShoppingCart,times(2)).getCartItems();
        Assertions.assertNull(order2.getCustomerName());
        Assertions.assertNull(order2.getCustomerAddress());
        Assertions.assertEquals(12, order2.getState());
    }

    @Test
    public void testConstructorWithState_nullValues(){
        ShoppingCart mockedShoppingCart = mock(ShoppingCart.class);

        Order order2 = new Order(mockedShoppingCart, null, null, 12);

        verify(mockedShoppingCart,times(1)).getCartItems();
        Assertions.assertNull(order2.getCustomerName());
        Assertions.assertNull(order2.getCustomerAddress());
        Assertions.assertEquals(12, order2.getState());
    }


}