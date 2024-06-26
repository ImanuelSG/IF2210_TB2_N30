package controller;

public interface Observerable {
    void addObserver(Observer observer);

    void removeObserver(Observer observer);

    void notifyObserver();
}