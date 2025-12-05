package entity;

import engine.GameObject;
import engine.RectangleRenderer;

import static com.raylib.Jaylib.BLACK;
import static com.raylib.Jaylib.GetRenderWidth;
import static com.raylib.Jaylib.GetRenderHeight;
import static com.raylib.Raylib.*;

public class BattleMenu extends GameObject {
    private final int WIDTH = 700;
    private final int HEIGHT = 256;
    private final int OFFSET_Y = (HEIGHT / 2) + 32;

    public BattleMenu() {
        super();

        GameObject background = new GameObject();
        AddChild(background);

        background.AddComponent(new RectangleRenderer(WIDTH, HEIGHT, BLACK, true));
        background.transform.SetGlobalPosition(new Vector2().x(GetRenderWidth() * 0.5f).y(GetRenderHeight() - OFFSET_Y));
    }

    @Override
    public void Update() {
        if (IsKeyDown(KEY_LEFT))
            transform.localPosition.x(transform.localPosition.x() - 5);
        if (IsKeyDown(KEY_RIGHT))
            transform.localPosition.x(transform.localPosition.x() + 5);
        if (IsKeyDown(KEY_UP))
            transform.localPosition.y(transform.localPosition.y() - 5);
        if (IsKeyDown(KEY_DOWN))
            transform.localPosition.y(transform.localPosition.y() + 5);
    }
}
