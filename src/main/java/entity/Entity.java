package entity;

import engine.GameObject;

public abstract class Entity extends GameObject {

    protected String name;

    protected double health;
    protected final double MAX_HEALTH;

    protected double attackDamage;
    protected final double BASE_ATTACK_DAMAGE;

    protected boolean alive = true;

    public Entity(String name, double health, double attackDamage) {
        this.name = name;
        this.health = health;
        this.attackDamage = attackDamage;

        MAX_HEALTH = health;
        BASE_ATTACK_DAMAGE = attackDamage;
    }

    public void modifyHealth(double amount) {
        health += amount;

        if (health <= 0) {
            health = 0;
            alive = false;
        }

        if (health >= MAX_HEALTH) {
            health = MAX_HEALTH;
        }
    }

    public void resetAttackDamage() {
        attackDamage = BASE_ATTACK_DAMAGE;
    }

    public abstract void modifyAttackDamage();

    public String getName() {
        return name;
    }

    public double getHealth() {
        return health;
    }

    public double getMaxHealth() {
        return MAX_HEALTH;
    }

    public double getAttackDamage() {
        return attackDamage;
    }

    public double getBaseAttackDamage() {
        return BASE_ATTACK_DAMAGE;
    }

    public boolean isAlive() {
        return alive;
    }

}
