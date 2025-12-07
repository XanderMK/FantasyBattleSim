package scene;

import engine.*;

import static main.Main.*;

import engine.components.*;
import engine.components.Character;
import gameplay.BattleEngine;
import item.FireballItem;
import item.HealthItem;
import item.Item;
import item.ManaItem;
import menus.BattleMenu;
import menus.ItemMenu;

import static com.raylib.Jaylib.WHITE;
import static com.raylib.Raylib.*;

public class BattleScene extends Scene {

    private final float DIALOGUE_SPEED = 0.03f;

    private Character[] characters;
    private Monster[] monsters;
    private BattleMenu battleMenu;

    private DialogueBox dialogue;

    private ItemMenu itemMenu;

    private BattleEngine battleEngine;

    private Sound fxDead;

    // TODO: The game is too hard rn, maybe even impossible without extreme luck. Must balance.

    @Override
    public void Init() {
        // Add the background
        GameObject background = new GameObject();
        background.AddComponent(new SpriteRenderer(
                ResourceManager.GetTexture("resources/bg.png"), WHITE
        ));
        objects.add(background);

        // Create objects for each character and enemy
        GameObject mohammed = new GameObject(), darwin = new GameObject();
        GameObject billy = new GameObject(), goblin1 = new GameObject(), goblin2 = new GameObject();

        // Create character components
        characters = new Character[]{
                new Character("Mohammed", 35.0, 10.0, 6.0, 10.0),
                new Character("Darwin the Wizard", 25.0, 5.0, 4.0, 7.5)
        };

        // Add character components to characters
        mohammed.AddComponent(characters[0]);
        darwin.AddComponent(characters[1]);

        mohammed.AddComponent(new SpriteRenderer(
                ResourceManager.GetTexture("resources/characters/Mohammed.png"), WHITE
        ));
        mohammed.transform.localPosition.x(220).y(240);

        darwin.AddComponent(new SpriteRenderer(
                ResourceManager.GetTexture("resources/characters/Darwin_The_Wizard.png"), WHITE
        ));
        darwin.transform.localPosition.x(360).y(360);

        for (Character character : characters) {
            character.parentObject.transform.localScale = 0.2f;

            // Give each character a health bar
            GameObject healthBar = new GameObject();
            HealthBar healthBarComp = new HealthBar();
            healthBarComp.SetTarget(character);
            healthBar.AddComponent(healthBarComp);
            character.parentObject.AddChild(healthBar);
        }

        // Add characters to object list
        objects.add(mohammed);
        objects.add(darwin);

        // Create monster components
        monsters = new Monster[]{
                new Monster("Billy", 50.0, 8.0),
                new Monster("Goblin 1", 30.0, 4.0),
                new Monster("Goblin 2", 30.0, 4.0)
        };

        // Add monster components to monsters
        billy.AddComponent(monsters[0]);
        goblin1.AddComponent(monsters[1]);
        goblin2.AddComponent(monsters[2]);

        for (byte i = 1; i < monsters.length; i++) {
            monsters[i].parentObject.AddComponent(new SpriteRenderer(
                    ResourceManager.GetTexture("resources/characters/Goblin.png"), WHITE
            ));
            monsters[i].parentObject.transform.localScale = 0.15f;
        }

        billy.AddComponent(new SpriteRenderer(
                ResourceManager.GetTexture("resources/characters/Billy.png"), WHITE
        ));
        billy.transform.localScale = 0.2f;
        billy.transform.localPosition.x(740).y(75);

        goblin1.transform.localPosition.x(590).y(-5);
        goblin2.transform.localPosition.x(435).y(60);

        // Give each monster a health bar
        for (Monster monster : monsters) {
            GameObject healthBar = new GameObject();
            HealthBar healthBarComp = new HealthBar();
            healthBarComp.SetTarget(monster);
            healthBar.AddComponent(healthBarComp);
            monster.parentObject.AddChild(healthBar);
        }

        // Add monsters to object list
        objects.add(billy);
        objects.add(goblin1);
        objects.add(goblin2);

        battleMenu = new BattleMenu();
        objects.add(battleMenu);

        dialogue = new DialogueBox(DIALOGUE_SPEED);
        dialogue.transform.SetGlobalPosition(new Vector2().x(30).y(VIRTUAL_HEIGHT - 120));
        objects.add(dialogue);

        // This is a fix to a bug I do not understand. Somehow when I instantiate the inventory object
        // in the Character class some of the assets do not appear. Why? I don't know. This fixes it
        // by instantiating the object later in the method. I seriously cannot comprehend why this
        // happens. But whatever, it works now. :)
        // - Skyler
        characters[0].createInventory(new Item[]{
                new ManaItem(),
                new ManaItem(),
                new HealthItem()
        });
        characters[1].createInventory(new Item[]{
                new HealthItem(),
                new ManaItem(),
                new ManaItem(),
                new FireballItem()
        });

        itemMenu = new ItemMenu();
        itemMenu.transform.localPosition.y(VIRTUAL_HEIGHT - itemMenu.getHeight());
        itemMenu.active = false;
        objects.add(itemMenu);

        battleEngine = new BattleEngine(characters, monsters, battleMenu, dialogue, itemMenu);
        battleEngine.startBattle();

        fxDead = ResourceManager.GetSound("resources/sfx/dead.wav");
    }

    @Override
    public void Update() {
        for (GameObject gameObject : objects)
            gameObject.Update();

        battleEngine.Update();
        GuiSetStyle(DEFAULT, TEXT_SIZE, 20);

        for (Character character : characters) {
            if (!character.isAlive() && character.parentObject.active && objects.contains(character.parentObject)) {
                character.parentObject.active = false;
                PlaySound(fxDead);
            }
        }

        for (Monster monster : monsters) {
            if (!monster.isAlive() && monster.parentObject.active && objects.contains(monster.parentObject)) {
                monster.parentObject.active = false;
                PlaySound(fxDead);
            }
        }
    }

}
