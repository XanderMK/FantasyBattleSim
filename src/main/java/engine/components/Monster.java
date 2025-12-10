package engine.components;

public class Monster extends Entity {

    private final double ATTACK_DAMAGE_MULTIPLIER = 1.1;

    public Monster(String name, double health, double attackDamage) {
        super(name, health, attackDamage);
        type = "engine.components.Monster";
    }

    public double getAttackDamageMultiplier() {
        return ATTACK_DAMAGE_MULTIPLIER;
    }
}
