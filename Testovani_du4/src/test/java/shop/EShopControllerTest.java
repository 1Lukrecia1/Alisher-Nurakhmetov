package shop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import storage.NoItemInStorage;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static shop.EShopController.newCart;
import static shop.EShopController.purchaseShoppingCart;

class EShopControllerTest {
    ShoppingCart shoppingTestCart;
    ByteArrayOutputStream TestOut;
    Item[] storageTestItems;

    @BeforeEach
    public void setupEshop(){
        //ARRANGE
        EShopController.storage = null;
        EShopController.startEShop();

        shoppingTestCart = newCart();

        storageTestItems = new Item[]{
                new StandardItem(1, "Dancing Panda v.2", 5000, "GADGETS", 5),
                new StandardItem(2, "Dancing Panda v.3 with USB port", 6000, "GADGETS", 10),
                new StandardItem(3, "Screwdriver", 200, "TOOLS", 5),
                new DiscountedItem(4, "Star Wars Jedi buzzer", 500, "GADGETS", 30, "1.8.2013", "1.12.2013"),
                new DiscountedItem(5, "Angry bird cup", 300, "GADGETS", 20, "1.9.2013", "1.12.2013"),
                new DiscountedItem(6, "Soft toy Angry bird (size 40cm)", 800, "GADGETS", 10, "1.8.2013", "1.12.2013")
        };

        int[] itemCount = {10,10,4,5,10,1};
        for (int i = 0; i < storageTestItems.length; i++) {
            EShopController.storage.insertItems(storageTestItems[i], itemCount[i]);
        }

        TestOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(TestOut));
    }

    @Test
    public void purchaseEmptyShoppingCart(){
        try {
            purchaseShoppingCart(shoppingTestCart, "Petr Pan", "Doma 123");
        } catch (NoItemInStorage e) {
            e.printStackTrace();
        }
        String result = TestOut.toString().trim();

        assertEquals("Error: shopping cart is empty", result);
    }

    @Test
    public void BuyingOutOfStockItem(){
        storageTestItems = new Item[]{
                new StandardItem(1, "Dancing Panda v.2", 5000, "GADGETS", 5),
                new StandardItem(2, "Dancing Panda v.3 with USB port", 6000, "GADGETS", 10),
                new StandardItem(3, "Screwdriver", 200, "TOOLS", 5),
                new DiscountedItem(4, "Star Wars Jedi buzzer", 500, "GADGETS", 30, "1.8.2013", "1.12.2013"),
                new DiscountedItem(5, "Angry bird cup", 300, "GADGETS", 20, "1.9.2013", "1.12.2013"),
                new DiscountedItem(6, "Soft toy Angry bird (size 40cm)", 800, "GADGETS", 10, "1.8.2013", "1.12.2013")
        };
        String expectedResult = "STORAGE IS CURRENTLY CONTAINING:\n" +
                "STOCK OF ITEM:  Item   ID 1   NAME Dancing Panda v.2   CATEGORY GADGETS   PRICE 5000.0   LOYALTY POINTS 5    PIECES IN STORAGE: 10\n" +
                "STOCK OF ITEM:  Item   ID 2   NAME Dancing Panda v.3 with USB port   CATEGORY GADGETS   PRICE 6000.0   LOYALTY POINTS 10    PIECES IN STORAGE: 10\n" +
                "STOCK OF ITEM:  Item   ID 3   NAME Screwdriver   CATEGORY TOOLS   PRICE 200.0   LOYALTY POINTS 5    PIECES IN STORAGE: 4\n" +
                "STOCK OF ITEM:  Item   ID 4   NAME Star Wars Jedi buzzer   CATEGORY GADGETS   ORIGINAL PRICE 500.0    DISCOUNTED PRICE 35000.0  DISCOUNT FROM Thu Aug 01 00:00:00 CEST 2013    DISCOUNT TO Sun Dec 01 00:00:00 CET 2013    PIECES IN STORAGE: 5\n" +
                "STOCK OF ITEM:  Item   ID 5   NAME Angry bird cup   CATEGORY GADGETS   ORIGINAL PRICE 300.0    DISCOUNTED PRICE 24000.0  DISCOUNT FROM Sun Sep 01 00:00:00 CEST 2013    DISCOUNT TO Sun Dec 01 00:00:00 CET 2013    PIECES IN STORAGE: 10\n" +
                "STOCK OF ITEM:  Item   ID 6   NAME Soft toy Angry bird (size 40cm)   CATEGORY GADGETS   ORIGINAL PRICE 800.0    DISCOUNTED PRICE 72000.0  DISCOUNT FROM Thu Aug 01 00:00:00 CEST 2013    DISCOUNT TO Sun Dec 01 00:00:00 CET 2013    PIECES IN STORAGE: 1" +
                "\nItem with ID 1 added to the shopping cart.\nItem with ID 1 removed from the shopping " +
                "cart.\nItem with ID 6 added to the shopping cart.\nItem with ID 6 added to the shopping cart.";
        Item item6 = storageTestItems[5];

        EShopController.storage.printListOfStoredItems();
        shoppingTestCart.addItem(storageTestItems[0]);
        shoppingTestCart.removeItem(1);
        shoppingTestCart.removeItem(5);

        shoppingTestCart.addItem(storageTestItems[5]);
        shoppingTestCart.addItem(storageTestItems[5]);

        String result = TestOut.toString().trim();
        assertEquals(expectedResult, result);

        Assertions.assertThrows(NoItemInStorage.class, () -> {purchaseShoppingCart(shoppingTestCart, "Bob Ross", "Doma 420");});

        int itemStock = EShopController.storage.getItemCount(6);

        assertEquals(0, itemStock);

        int itemSold6 = EShopController.archive.getHowManyTimesHasBeenItemSold(item6);
        assertEquals(0, itemSold6);
    }

    @Test
    public void standardExperience(){
        Item item4 = storageTestItems[3];
        Item item5 = storageTestItems[4];

        String printListOfStoreItems =
                "STORAGE IS CURRENTLY CONTAINING:\n" +
                        "STOCK OF ITEM:  Item   ID 1   NAME Dancing Panda v.2   CATEGORY GADGETS   PRICE 5000.0   LOYALTY POINTS 5    PIECES IN STORAGE: 10\n" +
                        "STOCK OF ITEM:  Item   ID 2   NAME Dancing Panda v.3 with USB port   CATEGORY GADGETS   PRICE 6000.0   LOYALTY POINTS 10    PIECES IN STORAGE: 10\n" +
                        "STOCK OF ITEM:  Item   ID 3   NAME Screwdriver   CATEGORY TOOLS   PRICE 200.0   LOYALTY POINTS 5    PIECES IN STORAGE: 4\n" +
                        "STOCK OF ITEM:  Item   ID 4   NAME Star Wars Jedi buzzer   CATEGORY GADGETS   ORIGINAL PRICE 500.0    DISCOUNTED PRICE 35000.0  DISCOUNT FROM Thu Aug 01 00:00:00 CEST 2013    DISCOUNT TO Sun Dec 01 00:00:00 CET 2013    PIECES IN STORAGE: 5\n" +
                        "STOCK OF ITEM:  Item   ID 5   NAME Angry bird cup   CATEGORY GADGETS   ORIGINAL PRICE 300.0    DISCOUNTED PRICE 24000.0  DISCOUNT FROM Sun Sep 01 00:00:00 CEST 2013    DISCOUNT TO Sun Dec 01 00:00:00 CET 2013    PIECES IN STORAGE: 10\n" +
                        "STOCK OF ITEM:  Item   ID 6   NAME Soft toy Angry bird (size 40cm)   CATEGORY GADGETS   ORIGINAL PRICE 800.0    DISCOUNTED PRICE 72000.0  DISCOUNT FROM Thu Aug 01 00:00:00 CEST 2013    DISCOUNT TO Sun Dec 01 00:00:00 CET 2013    PIECES IN STORAGE: 1";
        String addItem = "\nItem with ID 1 added to the shopping cart.\nItem with ID 1 removed from the shopping cart.";
        String addItem2 = "\nItem with ID 4 added to the shopping cart.\nItem with ID 5 added to the shopping cart.\n";
        String printItemPurchaseStatistics =
                "ITEM PURCHASE STATISTICS:\n"+
                        "ITEM  Item   ID 4   NAME Star Wars Jedi buzzer   CATEGORY GADGETS   ORIGINAL PRICE 500.0    DISCOUNTED PRICE 35000.0  DISCOUNT FROM Thu Aug 01 00:00:00 CEST 2013    DISCOUNT TO Sun Dec 01 00:00:00 CET 2013   HAS BEEN SOLD 1 TIMES\n" +
                        "ITEM  Item   ID 5   NAME Angry bird cup   CATEGORY GADGETS   ORIGINAL PRICE 300.0    DISCOUNTED PRICE 24000.0  DISCOUNT FROM Sun Sep 01 00:00:00 CEST 2013    DISCOUNT TO Sun Dec 01 00:00:00 CET 2013   HAS BEEN SOLD 1 TIMES\n";

        String printListOfStoreItems2 =
                "STORAGE IS CURRENTLY CONTAINING:\n"+
                        "STOCK OF ITEM:  Item   ID 1   NAME Dancing Panda v.2   CATEGORY GADGETS   PRICE 5000.0   LOYALTY POINTS 5    PIECES IN STORAGE: 10\n" +
                        "STOCK OF ITEM:  Item   ID 2   NAME Dancing Panda v.3 with USB port   CATEGORY GADGETS   PRICE 6000.0   LOYALTY POINTS 10    PIECES IN STORAGE: 10\n" +
                        "STOCK OF ITEM:  Item   ID 3   NAME Screwdriver   CATEGORY TOOLS   PRICE 200.0   LOYALTY POINTS 5    PIECES IN STORAGE: 4\n" +
                        "STOCK OF ITEM:  Item   ID 4   NAME Star Wars Jedi buzzer   CATEGORY GADGETS   ORIGINAL PRICE 500.0    DISCOUNTED PRICE 35000.0  DISCOUNT FROM Thu Aug 01 00:00:00 CEST 2013    DISCOUNT TO Sun Dec 01 00:00:00 CET 2013    PIECES IN STORAGE: 4\n" +
                        "STOCK OF ITEM:  Item   ID 5   NAME Angry bird cup   CATEGORY GADGETS   ORIGINAL PRICE 300.0    DISCOUNTED PRICE 24000.0  DISCOUNT FROM Sun Sep 01 00:00:00 CEST 2013    DISCOUNT TO Sun Dec 01 00:00:00 CET 2013    PIECES IN STORAGE: 9\n" +
                        "STOCK OF ITEM:  Item   ID 6   NAME Soft toy Angry bird (size 40cm)   CATEGORY GADGETS   ORIGINAL PRICE 800.0    DISCOUNTED PRICE 72000.0  DISCOUNT FROM Thu Aug 01 00:00:00 CEST 2013    DISCOUNT TO Sun Dec 01 00:00:00 CET 2013    PIECES IN STORAGE: 1";
        String expectedResult = printListOfStoreItems + addItem + addItem2 + printItemPurchaseStatistics + printListOfStoreItems2;

        //ACT
        EShopController.storage.printListOfStoredItems();
        shoppingTestCart.addItem(storageTestItems[0]);
        shoppingTestCart.removeItem(1);
        shoppingTestCart.removeItem(5);

        shoppingTestCart.addItem(item4);
        shoppingTestCart.addItem(item5);

        try {
            purchaseShoppingCart(shoppingTestCart, "Karel Schwarzenberg", "Hrad 1");
        } catch (NoItemInStorage e) {
            e.printStackTrace();
        }
        EShopController.archive.printItemPurchaseStatistics();
        EShopController.storage.printListOfStoredItems();

        String result = TestOut.toString().trim();
        assertEquals(expectedResult, result);

        int itemSt4 = EShopController.storage.getItemCount(4);
        int itemSt5 = EShopController.storage.getItemCount(5);
        assertEquals(4, itemSt4);
        assertEquals(9, itemSt5);

        int itemTestSld4 = EShopController.archive.getHowManyTimesHasBeenItemSold(item4);
        int itemTestSld5 = EShopController.archive.getHowManyTimesHasBeenItemSold(item5);
        assertEquals(1, itemTestSld4);
        assertEquals(1, itemTestSld5);
    }
}
