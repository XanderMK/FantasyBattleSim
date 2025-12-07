package item;

import engine.ResourceManager;
import engine.SpriteRenderer;
import entity.Character;
import entity.Monster;

import static com.raylib.Jaylib.WHITE;

public class ManaItem extends Item {

    public ManaItem() {
        name = "Mana Potion";
        description = "A potion that fully restores your mana";
        type = "Character";
        sprite = new SpriteRenderer(
                ResourceManager.GetTexture("resources/items/Blue_Potion.png"), WHITE);

        AddComponent(sprite);
    }

    @Override
    public void effect(Character character) {
        character.modifyMana(character.getMaxMana());
    }

    @Override
    public void effect(Monster monster) {}

}
