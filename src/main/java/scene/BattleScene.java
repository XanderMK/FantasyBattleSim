package scene;

import engine.GameObject;
import engine.SpriteRenderer;
import engine.ResourceManager;
import menus.BattleMenu;
import entity.Character;
import entity.Monster;
import menus.BattleMenuButtons;

import java.util.Collections;

import static com.raylib.Jaylib.WHITE;

public class BattleScene extends Scene {

    private Character[] characters;
    private Monster[] monsters;
    private BattleMenu battleMenu;

    private float x = 0.0f;

    @Override
    public void Init() {
        // Create character objects
        characters = new Character[]{
                new Character("Mohammed", 20.0, 10.0, 7.5),
                new Character("Darwin the Wizard", 15.0, 5.0, 5.0)
        };

        characters[0].AddComponent(new SpriteRenderer(
                ResourceManager.GetTexture("resources/Mohammed.png"), WHITE
        ));
        characters[0].transform.localPosition.x(220).y(290);

        characters[1].AddComponent(new SpriteRenderer(
                ResourceManager.GetTexture("resources/Darwin_The_Wizard.png"), WHITE
        ));
        characters[1].transform.localPosition.x(360).y(410);

        for (byte i = 0; i < characters.length; i++) {
            characters[i].transform.localScale = 0.2f;

            objects.add(characters[i]);
        }

        // Create monster objects
        monsters = new Monster[]{
                new Monster("Billy", 50.0, 10.0),
                new Monster("Goblin 1", 30.0, 5.0),
                new Monster("Goblin 2", 30.0, 5.0)
        };

        for (byte i = 1; i < monsters.length; i++) {
            monsters[i].AddComponent(new SpriteRenderer(
                    ResourceManager.GetTexture("resources/Goblin.png"), WHITE
            ));
            monsters[i].transform.localScale = 0.15f;
        }

        monsters[0].AddComponent(new SpriteRenderer(
                ResourceManager.GetTexture("resources/Billy.png"), WHITE
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
    }

    @Override
    public void Update() {
        for (GameObject gameObject : objects)
            gameObject.Update();

        // Temporary :)
        if (battleMenu.getFaceButtonAt(BattleMenuButtons.FACE_DEFEND.index).isPressed()) {
            battleMenu.getFaceButtonAt(BattleMenuButtons.FACE_DEFEND.index).visible = false;
        }
    }

}
