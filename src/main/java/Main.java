import static com.raylib.Jaylib.*;
import static com.raylib.Raylib.*;
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
            GameObject newObj = new GameObject();
            newObj.transform.position.x(100);
            newObj.transform.scale = 0.4f;
            newObj.AddComponent(new SpriteRenderer(
                    ResourceManager.GetTexture("resources/pingas.png"),
                    RED
            ));

            objects.add(newObj);
        }
        {
            GameObject newObj = new GameObject();
            newObj.transform.position.x(225).y(300);

            Text text = new Text();
            text.text = "pingas";
            text.font = ResourceManager.GetFont("resources/Comic Sans MS.ttf");
            text.tint = WHITE;
            text.baseFontSize = 48;
            newObj.AddComponent(text);

            objects.add(newObj);
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