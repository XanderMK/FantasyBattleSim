import engine.ResourceManager;
import scene.BattleScene;
import scene.Scene;

import static com.raylib.Jaylib.*;

public class Main {

    private static Scene currentScene;

    public static void main(String[] args) {
        Init();

        while (!WindowShouldClose()) {
            Update();

            BeginDrawing();
            ClearBackground(DARKGRAY);
                Render();
            EndDrawing();
        }

        Exit();
    }

    private static void Init() {
        InitWindow(1280, 720, "Fantasy Battle Sim");
        SetTargetFPS(60);

        // Init scenes
        currentScene = new BattleScene();
        currentScene.Init();
    }

    private static void Update() {
        // TODO: If we want multiple scenes move this to an ArrayList or something of scenes instead
        currentScene.Update();
    }

    private static void Render() {
        currentScene.Render();
        // Remove FPS counter at some point
        DrawFPS(20, 20);
    }

    private static void Exit() {
        ResourceManager.UnloadAllAssets();
        CloseWindow();
    }
}