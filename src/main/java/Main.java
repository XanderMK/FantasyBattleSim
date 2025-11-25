import static com.raylib.Jaylib.WHITE;
import static com.raylib.Raylib.*;

public class Main {
    public static void main(String[] args) {
        InitWindow(1280, 720, "Fantasy Battle Sim");
        SetTargetFPS(60);
        Camera2D camera = new Camera2D()
                .zoom(5.0f);

        Texture tex = ResourceManager.GetTexture("resources/pingas.png");

        Vector2 position = new Vector2();
        position.x(camera.zoom() / 2.0f).y(camera.zoom() / 2.0f);

        while (!WindowShouldClose()) {
            if (IsKeyDown(KEY_LEFT)) position.x(position.x() - 1);
            if (IsKeyDown(KEY_RIGHT)) position.x(position.x() + 1);
            if (IsKeyDown(KEY_UP)) position.y(position.y() - 1);
            if (IsKeyDown(KEY_DOWN)) position.y(position.y() + 1);

            ClearBackground(WHITE);
            BeginMode2D(camera);

            DrawTextureEx(tex, position, 0.0f, 0.1f, WHITE);

            EndMode2D();
            DrawFPS(20, 20);
            EndDrawing();
        }

        ResourceManager.UnloadAllAssets();

        CloseWindow();
    }
}