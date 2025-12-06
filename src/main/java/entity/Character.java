package entity;

public class Character extends Entity {

    private final double ATTACK_DAMAGE_MULTIPLIER = 1.25;

    private double defense;
    private final double MAX_DEFENSE;

    private boolean defending = false;

    private double mana;
    private final double MAX_MANA;

    public Character(String name, double health, double defense, double mana, double attackDamage) {
        super(name, health, attackDamage);
        this.defense = defense;
        this.mana = mana;

        MAX_DEFENSE = defense;
        MAX_MANA = mana;
    }

    public void modifyDefense(double amount) {
        defense += amount;

        if (defense <= 0) {
            defense = 0;
        }

        if (defense >= MAX_DEFENSE) {
            defense = MAX_DEFENSE;
        }
    }

    public void modifyMana(double amount) {
        mana += amount;

        if (mana <= 0) {
            mana = 0;
        }

        if (mana >= MAX_MANA) {
            mana = MAX_MANA;
        }
    }

    @Override
    public void modifyAttackDamage() {
        attackDamage *= ATTACK_DAMAGE_MULTIPLIER;
    }

    public double getAttackDamageMultiplier() {
        return ATTACK_DAMAGE_MULTIPLIER;
    }

    public double getDefense() {
        return defense;
    }

    public double getMaxDefense() {
        return MAX_DEFENSE;
    }

    public double getMana() {
        return mana;
    }

    public double getMaxMana() {
        return MAX_MANA;
    }

    public boolean isDefending() {
        return defending;
    }

    public void setDefending(boolean defending) {
        this.defending = defending;
    }

}
