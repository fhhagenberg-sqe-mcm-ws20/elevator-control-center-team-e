package at.fhhagenberg.sqe.util;

import java.util.*;

public class CollectionUtils {

    public static <TKey, TValue> Map<TKey, TValue> convertCollectionToMap(Collection<TValue> items, KeySelector<TKey, TValue> keySelector) {
        HashMap<TKey, TValue> result = new HashMap<>(items.size());
        for (TValue item : items) {
            result.put(keySelector.getKey(item), item);
        }
        return result;
    }

    public static <TKey, TValue> List<TValue> convertMapToList(Map<TKey, TValue> items) {
        ArrayList<TValue> result = new ArrayList<>(items.size());
        result.addAll(items.values());
        return result;
    }

    public static <TKey, TValue> TValue getItemWhere(Collection<TValue> items, ItemSelector<TValue> itemSelector) {
        for (TValue item : items) {
            if (itemSelector.matches(item)) {
                return item;
            }
        }
        return null;
    }

    public static interface KeySelector<TKey, TValue> {
        public TKey getKey(TValue value);
    }

    public static interface ItemSelector<TValue> {
        public boolean matches(TValue value);
    }
}