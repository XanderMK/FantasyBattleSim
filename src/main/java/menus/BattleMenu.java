package menus;

import engine.*;

import static com.raylib.Jaylib.BLACK;
import static com.raylib.Raylib.*;

public class BattleMenu extends GameObject {

    private final int WIDTH = 300;
    private final int HEIGHT = 300;

    private final int BORDER_SIZE = 12;

    private final int BUTTON_WIDTH = WIDTH / 2;
    private final int BUTTON_HEIGHT = HEIGHT / 2;

    private GameObject buttons;
    private ImageButton[] faceButtons;

    public BattleMenu() {
        super();

        buttons = new GameObject();
        AddChild(buttons);

        buttons.transform.localScale = 0.15f;

        faceButtons = new ImageButton[]{
                new ImageButton("resources/buttons/Attack.png"),
                new ImageButton("resources/buttons/Item.png", BUTTON_WIDTH, 0),
                new ImageButton("resources/buttons/Defend.png", 0, BUTTON_HEIGHT),
                new ImageButton("resources/buttons/Flee.png", BUTTON_WIDTH, BUTTON_HEIGHT)
        };
        for (ImageButton face : faceButtons) {
            buttons.AddComponent(face);
        }

        buttons.transform.SetGlobalPosition(new Vector2().x(GetRenderWidth() - WIDTH - ((float) BORDER_SIZE / 2))
                .y(GetRenderHeight() - HEIGHT - ((float) BORDER_SIZE / 2)));

        GameObject buttonBackground = new GameObject();
        buttonBackground.AddComponent(new RectangleRenderer(WIDTH + BORDER_SIZE, HEIGHT + BORDER_SIZE, BLACK, false));
        buttonBackground.transform.SetGlobalPosition(new Vector2().x(GetRenderWidth() - WIDTH - BORDER_SIZE).y(GetRenderHeight() - HEIGHT - BORDER_SIZE));
        AddChild(buttonBackground);
    }

    @Override
    public void Update() {
        buttons.Update();
    }

    public ImageButton[] getFaceButtons() {
        return faceButtons;
    }

    public ImageButton getFaceButtonAt(int i) {
        return faceButtons[i];
    }

}
