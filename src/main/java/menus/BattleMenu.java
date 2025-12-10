package menus;

import engine.*;
import engine.components.Button;
import engine.components.ImageButton;
import engine.components.RectangleRenderer;

import static com.raylib.Jaylib.BLACK;
import static com.raylib.Jaylib.DARKGRAY;
import static com.raylib.Raylib.*;
import static main.Main.*;

public class BattleMenu extends GameObject {

    private final int WIDTH = 300;
    private final int HEIGHT = 300;

    private final int BORDER_SIZE = 12;

    private final int BUTTON_WIDTH = WIDTH / 2;
    private final int BUTTON_HEIGHT = HEIGHT / 2;

    private GameObject faceButtonObj;
    private ImageButton[] faceButtons;

    private GameObject attackButtonObj;
    private Button[] attackButtons;

    private GameObject backgroundObj;
    private RectangleRenderer backgroundRectangle;

    public GameObject cover;
    private RectangleRenderer coverRectangle;
    private Color coverColor;

    public BattleMenu() {
        super();

        GameObject buttonBackground = new GameObject();
        buttonBackground.AddComponent(new RectangleRenderer(WIDTH + BORDER_SIZE, HEIGHT + BORDER_SIZE, BLACK, false));
        buttonBackground.transform.SetGlobalPosition(new Vector2().x(VIRTUAL_WIDTH - WIDTH - BORDER_SIZE).y(VIRTUAL_HEIGHT - HEIGHT - BORDER_SIZE));
        AddChild(buttonBackground);

        faceButtonObj = new GameObject();
        AddChild(faceButtonObj);

        faceButtonObj.transform.localScale = 0.15f;

        faceButtons = new ImageButton[]{
                new ImageButton("resources/buttons/Attack.png"),
                new ImageButton("resources/buttons/Item.png", BUTTON_WIDTH, 0),
                new ImageButton("resources/buttons/Defend.png", 0, BUTTON_HEIGHT),
                new ImageButton("resources/buttons/Flee.png", BUTTON_WIDTH, BUTTON_HEIGHT)
        };
        for (ImageButton face : faceButtons) {
            faceButtonObj.AddComponent(face);
        }

        backgroundObj = new GameObject();
        backgroundRectangle = new RectangleRenderer(670, 50, DARKGRAY, false);

        backgroundObj.AddComponent(backgroundRectangle);
        backgroundObj.transform.SetGlobalPosition(new Vector2().x(140).y(VIRTUAL_HEIGHT - 100));

        AddChild(backgroundObj);

        faceButtonObj.transform.SetGlobalPosition(new Vector2().x(VIRTUAL_WIDTH - WIDTH - ((float) BORDER_SIZE / 2))
                .y(VIRTUAL_HEIGHT - HEIGHT - ((float) BORDER_SIZE / 2)));

        attackButtonObj = new GameObject();
        AddChild(attackButtonObj);
        attackButtonObj.transform.SetGlobalPosition(new Vector2().x(150).y(VIRTUAL_HEIGHT - 90));

        attackButtons = new Button[]{
                new Button("Normal Attack", 300, 30),
                new Button("Mana Attack", 300, 30, 350, 0)
        };
        for (Button button : attackButtons) {
            attackButtonObj.AddComponent(button);
        }

        disableAttackButtons();

        // For some reason colors with some amount of transparency
        // are tinted red for some reason. I don't know why, but I wanted
        // this tint to be light gray to denote the buttons can't be pressed,
        // but I guess a light red works too
        coverColor = new Color()
                .r((byte) -1)
                .g((byte) 0)
                .b((byte) -1)
                .a((byte) 30);

        cover = new GameObject();
        coverRectangle = new RectangleRenderer(WIDTH, HEIGHT, coverColor, false);
        cover.AddComponent(coverRectangle);
        cover.transform.SetGlobalPosition(new Vector2().x(VIRTUAL_WIDTH - WIDTH - BORDER_SIZE / 2.0f).y(VIRTUAL_HEIGHT - HEIGHT - BORDER_SIZE / 2.0f));
        AddChild(cover);
        cover.active = false;
    }

    @Override
    public void Update() {
        faceButtonObj.Update();
        attackButtonObj.Update();
    }

    public ImageButton[] getFaceButtons() {
        return faceButtons;
    }

    public ImageButton getFaceButtonAt(int i) {
        return faceButtons[i];
    }

    public Button[] getAttackButtons() {
        return attackButtons;
    }

    public Button getAttackButtonAt(int i) {
        return attackButtons[i];
    }

    public void enableAttackButtons() {
        backgroundObj.active = true;
        for (Button button : attackButtons) {
            button.visible = true;
        }
    }

    public void disableAttackButtons() {
        backgroundObj.active = false;
        for (Button button : attackButtons) {
            button.visible = false;
        }
    }

}
