package scene;

import engine.GameObject;
import engine.ResourceManager;
import engine.components.ImageButton;
import engine.components.SpriteRenderer;
import engine.components.Text;

import java.util.Arrays;

import static com.raylib.Jaylib.BLACK;
import static com.raylib.Jaylib.WHITE;
import static com.raylib.Raylib.*;
import static main.Main.VIRTUAL_HEIGHT;
import static main.Main.VIRTUAL_WIDTH;

public class AboutScene extends Scene {

    public GameObject background;

    private GameObject back;
    private ImageButton backButton;

    private GameObject[] about;
    private Text[] aboutText;

    private GameObject mohammed;

    @Override
    public void Init() {
        background = new GameObject();
        background.AddComponent(new SpriteRenderer(
                ResourceManager.GetTexture("resources/title/bgTitle.png"), WHITE
        ));

        objects.add(background);

        back = new GameObject();
        backButton = new ImageButton("resources/title/back.png");
        back.AddComponent(backButton);
        back.transform.localScale = 0.5f;
        back.transform.localPosition.x(10).y(10);

        objects.add(back);

        about = new GameObject[7];
        aboutText = new Text[about.length];

        aboutText[0] = new Text("About", GetFontDefault(), 80, 3.0f, BLACK);
        aboutText[1] = new Text("A game made for our CSCI 125 Programming I final project", GetFontDefault(), 40, 3.0f, BLACK);
        aboutText[2] = new Text("Made by:", GetFontDefault(), 30, 3.0f, BLACK);
        aboutText[3] = new Text("Conner Bluj", GetFontDefault(), 20, 3.0f, BLACK);
        aboutText[4] = new Text("Skyler Dietz", GetFontDefault(), 20, 3.0f, BLACK);
        aboutText[5] = new Text("Xander Kleiber", GetFontDefault(), 20, 3.0f, BLACK);
        aboutText[6] = new Text("SFX from SFXR", GetFontDefault(), 30, 3.0f, BLACK);

        for (byte i = 0; i < about.length; i++) {
            about[i] = new GameObject();
            about[i].AddComponent(aboutText[i]);
            about[i].transform.localPosition.x((VIRTUAL_WIDTH / 2.0f) - (MeasureText(aboutText[i].text, (int) aboutText[i].baseFontSize) / 2.0f));
        }

        about[0].transform.localPosition.y(20);
        about[1].transform.localPosition.y(220);
        about[2].transform.localPosition.y(300);
        about[3].transform.localPosition.y(350);
        about[4].transform.localPosition.y(390);
        about[5].transform.localPosition.y(430);
        about[6].transform.localPosition.y(490);

        objects.addAll(Arrays.asList(about));

        mohammed = new GameObject();
        mohammed.AddComponent(new SpriteRenderer(
                ResourceManager.GetTexture("resources/characters/Mohammed.png"), WHITE
        ));

        mohammed.transform.localPosition.x(20).y(VIRTUAL_HEIGHT - 250);
        mohammed.transform.localScale = 0.3f;

        objects.add(mohammed);
    }

    @Override
    public void Update() {
        for (GameObject gameObject : objects)
            gameObject.Update();

        if (backButton.isPressed() || IsKeyPressed(KEY_ESCAPE)) {
            SceneManager.setScene(true, new TitleScene());
        }
    }

}