import engine.GameObject;
import engine.ResourceManager;
import scene.BattleScene;

import static com.raylib.Jaylib.*;

public class Main {

    private static BattleScene battleScene;

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
        battleScene = new BattleScene();
        battleScene.Init();
    }

    private static void Update() {
        // TODO: If we want multiple scenes move this to an ArrayList or something of scenes instead
        battleScene.Update();
    }

    private static void Render() {
        battleScene.Render();
        // Remove FPS counter at some point
        DrawFPS(20, 20);
    }

    private static void Exit() {
        ResourceManager.UnloadAllAssets();
        CloseWindow();
    }
}