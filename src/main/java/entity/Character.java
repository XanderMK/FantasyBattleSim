package entity;

public class Character extends Entity {

    private final double ATTACK_DAMAGE_MULTIPLIER = 1.25;

    private double defense;
    private final double MAX_DEFENSE;

    public Character(String name, double health, double defense, double attackDamage) {
        super(name, health, attackDamage);
        this.defense = defense;

        MAX_DEFENSE = defense;
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

}
