package fr.niware.gamesapi.database;

public interface ThrowingConsumer<T> {
    void accept(T t) throws Exception;
}
