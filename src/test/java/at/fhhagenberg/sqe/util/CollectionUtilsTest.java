package at.fhhagenberg.sqe.util;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class CollectionUtilsTest {

    @Nested
    class ConvertCollectionToMapTest {
        @Test
        public void testConvertCollectionToMapRegular() {
            int itemsCount = 10;
            List<Dummy> items = createDummyList(itemsCount);
            Map<Integer, Dummy> map = CollectionUtils.convertCollectionToMap(items, Dummy::getDummy);

            assertEquals(itemsCount, map.size());
            for (int i = 0; i < itemsCount; i++) {
                assertEquals(items.get(i), map.get(i));
            }
        }

        @Test
        public void testConvertCollectionToMapEmptyCollection() {
            List<Dummy> items = new ArrayList<>();
            Map<Integer, Dummy> map = CollectionUtils.convertCollectionToMap(items, Dummy::getDummy);

            assertEquals(0, map.size());
        }

        @Test
        public void testConvertCollectionToMapNullCollection() {
            List<Dummy> items = null;
            Map<Integer, Dummy> map = CollectionUtils.convertCollectionToMap(items, Dummy::getDummy);

            assertNull(map);
        }

        @Test
        public void testConvertCollectionToMapNullSelector() {
            int itemsCount = 10;
            List<Dummy> items = createDummyList(itemsCount);

            assertThrows(NullPointerException.class, () -> {
                CollectionUtils.convertCollectionToMap(items, null);
            });
        }

        @Test
        public void testConvertCollectionToMapNullCollectionNullSelector() {
            List<Dummy> items = null;
            Map<Integer, Dummy> map = CollectionUtils.convertCollectionToMap(items, null);

            assertNull(map);
        }

        @Test
        public void testConvertCollectionToMapDuplicateKeys() {
            int itemsCount = 10;
            List<Dummy> items = createDummyList(itemsCount);
            items.get(3).setDummy(2);
            Map<Integer, Dummy> map = CollectionUtils.convertCollectionToMap(items, Dummy::getDummy);

            assertEquals(itemsCount - 1, map.size());
            assertEquals(items.get(3), map.get(2));
        }

        @Test
        public void testConvertCollectionToMapInterchangedItems() {
            int itemsCount = 10;
            List<Dummy> items = createDummyList(itemsCount);
            items.get(3).setDummy(2);
            items.get(2).setDummy(3);
            Map<Integer, Dummy> map = CollectionUtils.convertCollectionToMap(items, Dummy::getDummy);

            assertEquals(itemsCount, map.size());
            assertEquals(items.get(2), map.get(3));
            assertEquals(items.get(3), map.get(2));
        }
    }

    @Nested
    class ConvertMapToListTest {
        @Test
        public void testConvertMapToListRegular() {
            int itemsCount = 10;
            Map<Integer, Dummy> map = createDummyMap(itemsCount);
            List<Dummy> items = CollectionUtils.convertMapToList(map);

            assertEquals(itemsCount, items.size());
            for (int i = 0; i < itemsCount; i++) {
                assertEquals(map.get(i), items.get(i));
            }
        }

        @Test
        public void testConvertMapToListNullMap() {
            Map<Integer, Dummy> map = null;
            List<Dummy> items = CollectionUtils.convertMapToList(map);

            assertNull(items);
        }

        @Test
        public void testConvertMapToListEmptyMap() {
            Map<Integer, Dummy> map = new HashMap<>();
            List<Dummy> items = CollectionUtils.convertMapToList(map);

            assertEquals(0, items.size());
        }
    }

    @Nested
    class GetItemWhereTest {
        @Test
        public void testGetItemWhereRegular() {
            int itemsCount = 10;
            List<Dummy> items = createDummyList(itemsCount);
            Dummy dummyResult = CollectionUtils.getItemWhere(items, dummy -> dummy.getDummy() == 3);
            assertNotNull(dummyResult);
            assertEquals(items.get(3), dummyResult);
        }

        @Test
        public void testGetItemWhereInvalidItem() {
            int itemsCount = 10;
            List<Dummy> items = createDummyList(itemsCount);
            Dummy dummyResult = CollectionUtils.getItemWhere(items, dummy -> dummy.getDummy() == 12341);
            assertNull(dummyResult);
        }

        @Test
        public void testGetItemWhereNullCollection() {
            List<Dummy> items = null;
            Dummy dummyResult = CollectionUtils.getItemWhere(items, dummy -> dummy.getDummy() == 12341);
            assertNull(dummyResult);
        }

        @Test
        public void testGetItemWhereNullSelector() {
            int itemsCount = 10;
            List<Dummy> items = createDummyList(itemsCount);
            assertThrows(NullPointerException.class, () -> {
                CollectionUtils.getItemWhere(items, null);
            });
        }

        @Test
        public void testGetItemWhereNullCollectionNullSelector() {
            List<Dummy> items = null;
            Dummy dummyResult = CollectionUtils.getItemWhere(items, null);
            assertNull(dummyResult);
        }
    }

    private static List<Dummy> createDummyList(int itemsCount) {
        List<Dummy> items = new ArrayList<>(itemsCount);
        for (int i = 0; i < itemsCount; i++) {
            items.add(new Dummy(i));
        }
        return items;
    }

    private static Map<Integer, Dummy> createDummyMap(int itemsCount) {
        Map<Integer, Dummy> items = new HashMap<>(itemsCount);
        for (int i = 0; i < itemsCount; i++) {
            items.put(i, new Dummy(i));
        }
        return items;
    }

    private static class Dummy {
        private int dummy;

        public Dummy(int dummy) {
            setDummy(dummy);
        }

        public int getDummy() {
            return dummy;
        }

        public void setDummy(int dummy) {
            this.dummy = dummy;
        }
    }
}