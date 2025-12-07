package item;

import engine.ResourceManager;
import engine.components.SpriteRenderer;
import engine.components.Character;
import engine.components.Monster;

import static com.raylib.Jaylib.WHITE;

public class FireballItem extends Item {

    public FireballItem() {
        name = "Fireball";
        description = "A sun-like object in your pocket. Does 15 damage to monsters.";
        type = "Monster";
        effectValue = 15.0;
        sprite = new SpriteRenderer(
                ResourceManager.GetTexture("resources/items/Fireball.png"), WHITE);

        AddComponent(sprite);
    }

    @Override
    public void effect(Character character) {}

    @Override
    public void effect(Monster monster) {
        monster.modifyHealth(-effectValue);
    }

}
