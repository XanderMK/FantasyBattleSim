package scene;

import engine.GameObject;
import engine.components.RectangleRenderer;
import engine.components.Text;

import static com.raylib.Jaylib.BLACK;
import static com.raylib.Jaylib.WHITE;
import static com.raylib.Raylib.*;
import static main.Main.VIRTUAL_HEIGHT;
import static main.Main.VIRTUAL_WIDTH;

public class LoseScene extends Scene {

    private GameObject backgroundObj;
    private RectangleRenderer background;

    private GameObject gameOverObj;
    private Text gameOverText;

    @Override
    public void Init() {
        backgroundObj = new GameObject();
        background = new RectangleRenderer(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, BLACK, false);

        backgroundObj.AddComponent(background);

        objects.add(backgroundObj);

        gameOverObj = new GameObject();
        gameOverText = new Text("Game Over!\nBilly got away with all your keyboards...", GetFontDefault(), 50, 3.0f, WHITE);

        gameOverObj.transform.localPosition.x(10).y((VIRTUAL_HEIGHT / 2.0f) - 75);
        gameOverObj.AddComponent(gameOverText);

        objects.add(gameOverObj);
    }

    @Override
    public void Update() {
        for (GameObject gameObject : objects)
            gameObject.Update();

        if (IsKeyPressed(KEY_ESCAPE)) {
            SceneManager.setScene(true, new TitleScene());
        }
    }

}
