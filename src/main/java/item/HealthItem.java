package item;

import engine.ResourceManager;
import engine.SpriteRenderer;
import entity.Monster;
import entity.Character;

import static com.raylib.Jaylib.WHITE;

public class HealthItem extends Item {

    public HealthItem() {
        name = "Health Potion";
        description = "A potion that heals your character by 10 HP";
        type = "Character";
        effectValue = 10.0;
        sprite = new SpriteRenderer(
                ResourceManager.GetTexture("resources/items/Red_Potion.png"), WHITE);

        AddComponent(sprite);
    }

    @Override
    public void effect(Character character) {
        character.modifyHealth(effectValue);
    }

    @Override
    public void effect(Monster monster) {}

}
