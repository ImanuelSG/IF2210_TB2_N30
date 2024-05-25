package libs.GameWorld;

public interface BearAttackListener {
    void setupBearAttack(int rowstart, int rowend, int colstart, int colend, int duration);

    void endBearAttack();
}
