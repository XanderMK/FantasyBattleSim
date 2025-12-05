package menus;

public enum BattleMenuButtons {

    FACE_ATTACK(0),
    FACE_ITEM(1),
    FACE_DEFEND(2),
    FACE_FLEE(3);

    public final int index;

    BattleMenuButtons(int index) {
        this.index = index;
    }

}
