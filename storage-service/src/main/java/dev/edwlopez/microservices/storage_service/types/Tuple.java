package dev.edwlopez.microservices.storage_service.types;

public record Tuple<T1, T2>(T1 val1, T2 val2) {
    public static <T1, T2> Tuple<T1, T2> of(T1 val1, T2 val2) {
        return new Tuple<>(val1, val2);
    }
}
