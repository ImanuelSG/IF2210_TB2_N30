
package libs.GameWorld;

public interface SpecialObserverable {
    void registerObserver(SpecialObserver observer);

    void removeObserver(SpecialObserver observer);

    void notifySpecial();
}