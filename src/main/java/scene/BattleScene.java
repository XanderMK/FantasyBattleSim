package scene;

import engine.*;

import static main.Main.*;

import gameplay.BattleEngine;
import menus.BattleMenu;
import entity.Character;
import entity.Monster;

import java.util.Collections;

import static com.raylib.Jaylib.WHITE;
import static com.raylib.Raylib.*;

public class BattleScene extends Scene {

    private final float DIALOGUE_SPEED = 0.03f;

    private Character[] characters;
    private Monster[] monsters;
    private BattleMenu battleMenu;

    private DialogueBox dialogue;

    private BattleEngine battleEngine;

    private Sound fxDead;

    @Override
    public void Init() {
        // Create character objects
        characters = new Character[]{
                new Character("Mohammed", 30.0, 10.0, 6.0, 7.5),
                new Character("Darwin the Wizard", 20.0, 5.0, 4.0, 5.0)
        };

        characters[0].AddComponent(new SpriteRenderer(
                ResourceManager.GetTexture("resources/characters/Mohammed.png"), WHITE
        ));
        characters[0].transform.localPosition.x(220).y(240);

        characters[1].AddComponent(new SpriteRenderer(
                ResourceManager.GetTexture("resources/characters/Darwin_The_Wizard.png"), WHITE
        ));
        characters[1].transform.localPosition.x(360).y(360);

        for (byte i = 0; i < characters.length; i++) {
            characters[i].transform.localScale = 0.2f;

            objects.add(characters[i]);
        }

        // Create monster objects
        monsters = new Monster[]{
                new Monster("Billy", 50.0, 8.0),
                new Monster("Goblin 1", 30.0, 4.0),
                new Monster("Goblin 2", 30.0, 4.0)
        };

        for (byte i = 1; i < monsters.length; i++) {
            monsters[i].AddComponent(new SpriteRenderer(
                    ResourceManager.GetTexture("resources/characters/Goblin.png"), WHITE
            ));
            monsters[i].transform.localScale = 0.15f;
        }

        monsters[0].AddComponent(new SpriteRenderer(
                ResourceManager.GetTexture("resources/characters/Billy.png"), WHITE
        ));
        monsters[0].transform.localScale = 0.2f;
        monsters[0].transform.localPosition.x(740).y(75);

        monsters[1].transform.localPosition.x(590).y(-5);
        monsters[2].transform.localPosition.x(435).y(60);

        Collections.addAll(objects, monsters);

        GameObject background = new GameObject();
        background.AddComponent(new SpriteRenderer(
                ResourceManager.GetTexture("resources/bg.png"), WHITE
        ));
        objects.add(background);

        battleMenu = new BattleMenu();
        objects.add(battleMenu);

        dialogue = new DialogueBox(DIALOGUE_SPEED);
        dialogue.transform.SetGlobalPosition(new Vector2().x(30).y(VIRTUAL_HEIGHT - 120));
        objects.add(dialogue);

        // TODO: Make a system that controls dialogue boxes
        //dialogue.promptText("muhahauaaahahahaaahaaha!! i have stolen your keyboards, Mohammed. Come get them if you can!!");

        battleEngine = new BattleEngine(characters, monsters, battleMenu, dialogue);
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
            if (!character.isAlive() && objects.contains(character)) {
                objects.remove(character);
                PlaySound(fxDead);
            }
        }

        for (Monster monster : monsters) {
            if (!monster.isAlive() && objects.contains(monster)) {
                objects.remove(monster);
                PlaySound(fxDead);
            }
        }
    }

}
