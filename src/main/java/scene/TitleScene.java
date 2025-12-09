package scene;

import engine.GameObject;
import engine.ResourceManager;
import engine.components.ImageButton;
import engine.components.SpriteRenderer;
import engine.components.Text;
import main.Main;

import static com.raylib.Jaylib.WHITE;
import static com.raylib.Raylib.*;
import static main.Main.VIRTUAL_HEIGHT;
import static main.Main.VIRTUAL_WIDTH;

public class TitleScene extends Scene {

    public GameObject background;

    private Font titleFont;

    private GameObject title;
    private Text titleText;

    private GameObject buttonObj;
    private ImageButton[] buttons;

    private GameObject arrow;

    private GameObject info;
    private Text infoText;

    private Sound fxHover;

    @Override
    public void Init() {
        background = new GameObject();
        background.AddComponent(new SpriteRenderer(
                ResourceManager.GetTexture("resources/title/bgTitle.png"), WHITE
        ));

        objects.add(background);

        titleFont = ResourceManager.GetFont("resources/font/Sketch_Gothic_School.ttf");

        title = new GameObject();
        titleText = new Text("Fantasy Battle Simulator", titleFont, 130, 2.5f, WHITE);

        title.AddComponent(titleText);
        title.transform.localRotation = -5.0f;
        title.transform.localPosition.x(10).y(80);

        objects.add(title);

        buttonObj = new GameObject();
        buttons = new ImageButton[3];

        // I do not know why these images have random brown pixels on them
        // Why am I using images instead of normal buttons? I thought
        // the normal ones looked stupid. Yeah.
        // - Skyler
        // TODO: Figure that out maybe? Already wasted an hour on them lol
        buttons[0] = new ImageButton("resources/title/play.png");
        buttons[1] = new ImageButton("resources/title/about.png", 0, 125);
        buttons[2] = new ImageButton("resources/title/exit.png", 0, 250);

        for (ImageButton button : buttons) {
            buttonObj.AddComponent(button);
            buttonObj.transform.localScale = 0.6f;
        }

        buttonObj.transform.localPosition.x((VIRTUAL_WIDTH / 2.0f) - (buttons[0].texture.width() * buttonObj.transform.localScale / 2.0f)).y(240);

        objects.add(buttonObj);

        arrow = new GameObject();
        arrow.AddComponent(new SpriteRenderer(
                ResourceManager.GetTexture("resources/misc/Select.png"), WHITE
        ));

        arrow.transform.localScale = 4.0f;
        arrow.transform.localPosition.x(buttonObj.transform.localPosition.x() - 50);

        arrow.active = false;

        objects.add(arrow);

        info = new GameObject();
        infoText = new Text("CSCI 125", GetFontDefault(), 10, 2.0f, WHITE);

        info.AddComponent(infoText);
        info.transform.localPosition.y(VIRTUAL_HEIGHT - 12).x(4);

        objects.add(info);

        fxHover = ResourceManager.GetSound("resources/sfx/hover.wav");
    }

    @Override
    public void Update() {
        for (GameObject gameObject : objects)
            gameObject.Update();

        GuiSetStyle(DEFAULT, TEXT_SIZE, 40);

        for (ImageButton button : buttons) {
            if (button.isHovered() && !arrow.active) {
                PlaySound(fxHover);
                arrow.active = true;
                arrow.transform.localPosition.y(button.parentObject.transform.localPosition.y() + button.offY + 15);
            }
        }
        if (noButtonHovered()) arrow.active = false;

        if (buttons[0].isPressed()) {
            SceneManager.setScene(true, new BattleScene());
        }
        if (buttons[1].isPressed()) {
            SceneManager.setScene(true, new AboutScene());
        }
        if (buttons[2].isPressed() || IsKeyPressed(KEY_ESCAPE)) {
            Main.Exit();
            System.exit(0);
        }
    }

    private boolean noButtonHovered() {
        for (ImageButton button : buttons) {
            if (button.isHovered()) {
                return false;
            }
        }

        return true;
    }

}
