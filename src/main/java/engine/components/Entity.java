package engine.components;

import engine.ResourceManager;
import engine.Timer;

import static com.raylib.Raylib.*;

public abstract class Entity extends Component {

    protected String name;

    protected double health;
    protected final double MAX_HEALTH;

    protected double attackDamage;
    protected final double BASE_ATTACK_DAMAGE;

    protected boolean alive = true;

    private Timer timer;

    private final double MOVE_DISTANCE_CONST = 20.0;
    private double moveDistance = MOVE_DISTANCE_CONST;

    private Sound fxHit;

    public Entity(String name, double health, double attackDamage) {
        type = "engine.components.Entity";

        this.name = name;
        this.health = health;
        this.attackDamage = attackDamage;

        MAX_HEALTH = health;
        BASE_ATTACK_DAMAGE = attackDamage;

        fxHit = ResourceManager.GetSound("resources/sfx/hit.wav");
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

    public void attackAnimation(boolean forward) {
        moveDistance = MOVE_DISTANCE_CONST;
        if (!forward) moveDistance = -moveDistance;

        timer = new Timer(0.1f);

        parentObject.transform.localPosition.x((float) (parentObject.transform.localPosition.x() + moveDistance));
        timer.start();
        PlaySound(fxHit);
    }

    @Override
    public void Update() {
        if (timer == null) return;

        if (timer.timerDone()) {
            parentObject.transform.localPosition.x((float) (parentObject.transform.localPosition.x() - moveDistance));
        }
    }

    @Override
    public void Render() { }

    public void resetAttackDamage() {
        attackDamage = BASE_ATTACK_DAMAGE;
    }

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
