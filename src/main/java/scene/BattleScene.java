package scene;

import engine.GameObject;
import engine.SpriteRenderer;
import engine.ResourceManager;
import engine.Text;
import entity.Character;
import entity.Monster;

import static com.raylib.Jaylib.RED;
import static com.raylib.Jaylib.WHITE;
import static com.raylib.Raylib.*;

public class BattleScene extends Scene {

    private Character[] characters;
    private Monster[] monsters;

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
        characters[1].AddComponent(new SpriteRenderer(
                ResourceManager.GetTexture("resources/Darwin_The_Wizard.png"), WHITE
        ));

        for (byte i = 0; i < characters.length; i++) {
            // Temporary position
            characters[i].transform.position.x(10).y(150 + (175 * i));
            characters[i].transform.scale = 0.2f;

            objects.add(characters[i]);
        }

        // Create monster objects
        monsters = new Monster[]{
                new Monster("Billy", 50.0, 10.0),
                new Monster("Goblin 1", 30.0, 5.0),
                new Monster("Goblin 1", 30.0, 5.0)
        };

        for (byte i = 1; i < monsters.length; i++) {
            monsters[i].AddComponent(new SpriteRenderer(
                    ResourceManager.GetTexture("resources/Goblin.png"), WHITE
            ));
        }

        monsters[0].AddComponent(new SpriteRenderer(
                ResourceManager.GetTexture("resources/Billy.png"), WHITE
        ));

        for (byte i = 0; i < monsters.length; i++) {
            monsters[i].transform.position.x(1090).y(75 + (180 * i));
            monsters[i].transform.scale = 0.2f;

            objects.add(monsters[i]);
        }
    }

    @Override
    public void Update() {
        for (GameObject gameObject : objects)
            gameObject.Update();

        // Temporary :)
        x += 10.0f + (5.0f * GetFrameTime());
        characters[0].transform.position.x(x);
    }

}
