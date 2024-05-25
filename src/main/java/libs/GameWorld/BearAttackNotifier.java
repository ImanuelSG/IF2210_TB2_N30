package libs.GameWorld;

public interface BearAttackNotifier {
    void addListener(BearAttackListener listener);

    void notifyListener(int startrow, int endrow, int startcol, int endcol, int duration);
}
