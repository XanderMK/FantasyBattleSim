package main;

import engine.ResourceManager;
import scene.BattleScene;
import scene.Scene;
import scene.SceneManager;

import static com.raylib.Jaylib.*;
import static com.raylib.Raylib.Rectangle;
import static com.raylib.Raylib.Vector2;

public class Main {

    private static SceneManager sceneManager;

    // "Real" screen resolution (can be changed without issue)
    private final static int WIDTH = 1600;
    private final static int HEIGHT = 900;

    // "Virtual" screen resolution (changing this will cause problems)
    public final static int VIRTUAL_WIDTH = 1280;
    public final static int VIRTUAL_HEIGHT = 720;

    public final static float VIRTUAL_RATIO = (float) WIDTH / (float) VIRTUAL_WIDTH;

    private static Camera2D worldSpaceCamera, screenSpaceCamera;
    private static RenderTexture target;

    private static Rectangle sourceRec, destRec;

    public static void main(String[] args) {
        Init();

        while (!WindowShouldClose()) {
            Update();

            BeginTextureMode(target);
                ClearBackground(DARKGRAY);
                    BeginMode2D(worldSpaceCamera);
                        Render();
                    EndMode2D();
            EndTextureMode();

            BeginDrawing();
                ClearBackground(RED);

                BeginMode2D(screenSpaceCamera);
                    DrawTexturePro(target.texture(), sourceRec, destRec, new Vector2().x(0.0f).y(0.0f), 0.0f, WHITE);
                EndMode2D();
            EndDrawing();
        }

        Exit();
    }

    private static void Init() {
        InitWindow(WIDTH, HEIGHT, "Fantasy Battle Sim");
        SetTargetFPS(60);

        InitAudioDevice();
        SetMasterVolume(0.5f);

        // Stuff for virtual world
        worldSpaceCamera = new Camera2D()
                .zoom(1.0f);
        screenSpaceCamera = new Camera2D()
                .zoom(1.0f);

        target = LoadRenderTexture(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);

        sourceRec = new Rectangle()
                .x(0.0f)
                .y(0.0f)
                .width((float) target.texture().width())
                .height(-(float) target.texture().height());
        destRec = new Rectangle()
                .x(-VIRTUAL_RATIO)
                .y(-VIRTUAL_RATIO)
                .width(WIDTH + (VIRTUAL_RATIO * 2))
                .height(HEIGHT + (VIRTUAL_RATIO * 2));

        // Init sceneManager
        sceneManager = new SceneManager(new BattleScene());
        sceneManager.Init();
    }

    private static void Update() {
        // TODO: If we want multiple scenes move this to an ArrayList or something of scenes instead
        sceneManager.Update();
    }

    private static void Render() {
        sceneManager.Render();
        // Remove FPS counter at some point
        DrawFPS(20, 20);
    }

    private static void Exit() {
        ResourceManager.UnloadAllAssets();
        UnloadRenderTexture(target);
        CloseAudioDevice();
        CloseWindow();
    }
}