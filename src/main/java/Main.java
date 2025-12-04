import engine.GameObject;
import engine.SpriteRenderer;
import engine.Text;

import static com.raylib.Jaylib.*;

import entity.Character;
import entity.Monster;

import java.util.HashSet;

public class Main {
    public static HashSet<GameObject> objects = new HashSet<>();

    public static void main(String[] args) {
        Init();

        while (!WindowShouldClose()) {
            Update();

            BeginDrawing();
            ClearBackground(BLACK);
                Render();
            EndDrawing();
        }

        Exit();
    }

    private static void Init() {
        InitWindow(1280, 720, "Fantasy Battle Sim");
        SetTargetFPS(60);
        {
            Character character = new Character("This!", 10.0, 5.0, 2.0);
            character.transform.position.x(10).y(10);
            character.transform.scale = 0.2f;
            character.AddComponent(new SpriteRenderer(
                    ResourceManager.GetTexture("resources/pingas.png"), WHITE
            ));

            objects.add(character);

            Monster monster = new Monster("Billy", 100.0, 1000.0);
            monster.transform.position.x(10).y(250);
            monster.transform.scale = 0.2f;
            monster.AddComponent(new SpriteRenderer(
                    ResourceManager.GetTexture("resources/pingas.png"), RED
            ));

            objects.add(monster);

            GameObject characterTextObj = new GameObject();
            characterTextObj.transform.position.x(10).y(150);

            Text characterText = new Text();
            characterText.text = String.format("Character [Name: %s, Health: %.2f, Defense: %.2f, Attack Damage: %.2f]",
                    character.getName(), character.getHealth(), character.getDefense(), character.getAttackDamage());
            characterText.font = ResourceManager.GetFont("resources/Comic Sans MS.ttf");
            characterText.tint = WHITE;
            characterText.baseFontSize = 25;
            characterText.spacing = 3.5f;
            characterTextObj.AddComponent(characterText);

            objects.add(characterTextObj);

            GameObject monsterTextObj = new GameObject();
            monsterTextObj.transform.position.x(10).y(400);

            Text monsterText = new Text();
            monsterText.text = String.format("Monster [Name: %s, Health: %.2f, Attack Damage: %.2f]",
                    monster.getName(), monster.getHealth(), monster.getAttackDamage());
            monsterText.font = ResourceManager.GetFont("resources/Comic Sans MS.ttf");
            monsterText.tint = WHITE;
            monsterText.baseFontSize = 25;
            monsterText.spacing = 3.5f;
            monsterTextObj.AddComponent(monsterText);

            objects.add(monsterTextObj);
        }
    }

    private static void Update() {
        for (GameObject gameObject : objects)
            gameObject.Update();
    }

    private static void Render() {
        for (GameObject gameObject : objects)
            gameObject.Render();
        // Remove FPS counter at some point
        DrawFPS(20, 20);
    }

    private static void Exit() {
        ResourceManager.UnloadAllAssets();
        CloseWindow();
    }
}