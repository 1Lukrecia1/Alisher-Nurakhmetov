package archive;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import shop.Order;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PurchasesArchiveTest {

    @Test
    void printItemPurchaseStatisticsTest() {
        ByteArrayOutputStream myTestOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(myTestOut));
        PurchasesArchive purchasesArchive = new PurchasesArchive();

        //ACT
        purchasesArchive.printItemPurchaseStatistics();
        String result = myTestOut.toString().trim();

        //ASSERT

        Assertions.assertEquals("ITEM PURCHASE STATISTICS:", result);

        //CLEANUP
        System.setOut(System.out);}

    @Test
    void getHowManyTimesHasBeenItemSoldTest() {
    }
    @Test
    public void printItemPurchaseStatistics_itemPurchaseArchiveIsFilled_returnsStream(){
        ByteArrayOutputStream myTestOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(myTestOut));

        //MOCK AND CONSTRUCT
        ItemPurchaseArchiveEntry mockedEntry = mock(ItemPurchaseArchiveEntry.class);
        when(mockedEntry.toString()).thenReturn("a");
        HashMap<Integer, ItemPurchaseArchiveEntry> hashMap = new HashMap<Integer, ItemPurchaseArchiveEntry>();
        hashMap.put(1, mockedEntry);
        ArrayList<Order> mockedOrders = mock(ArrayList.class);
        PurchasesArchive archive = new PurchasesArchive(hashMap, mockedOrders);

        //ACT
        archive.printItemPurchaseStatistics();
        String expectedResult = "ITEM PURCHASE STATISTICS:\na";
        String result = myTestOut.toString().trim();

        //ASSERT
        Assertions.assertEquals(expectedResult, result);

        //CLEANUP
        System.setOut(System.out);
    }

    @Test
    void putOrderToPurchasesArchiveTest() {
    }
}