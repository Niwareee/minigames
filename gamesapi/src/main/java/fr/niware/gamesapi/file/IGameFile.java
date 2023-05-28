package fr.niware.gamesapi.file;

public interface IGameFile<T> {

    String getName();

    T getConfig();

    void save(T tyoe);
}
