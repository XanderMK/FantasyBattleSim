package item;

import engine.GameObject;
import engine.SpriteRenderer;
import entity.Monster;
import entity.Character;

import static com.raylib.Raylib.*;

public abstract class Item extends GameObject {

    protected String name, description, type;
    protected double effectValue;

    protected SpriteRenderer sprite;

    public Item() {
        transform.localScale = 4.0f;
    }

    public abstract void effect(Character character);

    public abstract void effect(Monster monster);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getEffectValue() {
        return effectValue;
    }

    public void setEffectValue(double effectValue) {
        this.effectValue = effectValue;
    }

    public Texture getTexture() {
        return sprite.texture;
    }

}
