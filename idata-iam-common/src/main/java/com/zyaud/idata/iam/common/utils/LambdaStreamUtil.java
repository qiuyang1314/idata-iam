package com.zyaud.idata.iam.common.utils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class LambdaStreamUtil {
    public static <E, R> List<R> toList(Collection<E> collection, Function<? super E, ? extends R> mapper) {
        return collection.stream().map(mapper).collect(Collectors.toList());
    }

    public static <E, R> Set<R> toSet(Collection<E> collection, Function<? super E, ? extends R> mapper) {
        return collection.stream().map(mapper).collect(Collectors.toSet());
    }

    public static <E> List<E> toFilterList(Collection<E> collection, Function<? super E, Boolean> filter) {
        return collection.stream().filter(t -> filter.apply(t)).collect(Collectors.toList());
    }

    public static <E> Set<E> toFilterSet(Collection<E> collection, Function<? super E, Boolean> filter) {
        return collection.stream().filter(t -> filter.apply(t)).collect(Collectors.toSet());
    }

    public static <E,R> Set<R> toFilterAndMapper(Collection<E> collection, Function<? super E, Boolean> filter,
                                               Function<? super E, ? extends R> mapper) {
        return collection.stream().filter(t -> filter.apply(t)).map(mapper).collect(Collectors.toSet());
    }

    public static <E> Optional<E> findFirst(Collection<E> collection, Function<? super E, Boolean> filter) {
        return collection.stream().filter(t -> filter.apply(t)).findFirst();
    }

    public static <E> E getFirst(Collection<E> collection, Function<? super E, Boolean> filter) {
        Optional<E> option = collection.stream().filter(t -> filter.apply(t)).findFirst();
        return option.orElse(null);
    }

    public static <E, K, V> Map<K, V> toMap(Collection<E> collection, Function<? super E, ? extends K> keyMapper,
                                            Function<? super E, ? extends V> valueMapper) {
        return collection.stream().collect(Collectors.toMap(keyMapper, valueMapper));
    }

    public static <E, K> Map<K, List<E>> groupingBy(Collection<E> collection,
                                                    Function<? super E, ? extends K> classifier) {
        return collection.stream().collect(Collectors.groupingBy(classifier));
    }
}
