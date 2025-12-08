package scene;

import engine.GameObject;
import engine.ResourceManager;
import engine.Timer;
import engine.components.DialogueBox;

import engine.components.Character;
import engine.components.SpriteRenderer;
import engine.components.Text;
import item.FireballItem;
import item.HealthItem;
import item.Item;
import item.ManaItem;

import java.util.Random;

import static com.raylib.Jaylib.BLACK;
import static com.raylib.Jaylib.WHITE;
import static com.raylib.Raylib.GetFontDefault;

public class WinScene extends Scene {

    private Timer timer;
    private DialogueBox dialogueBox;

    private Character[] characters;

    private GameObject mohammed, darwin;

    private GameObject mohammedText, darwinText;
    private Text mohammedReward, darwinReward;

    private Item reward;
    private Random random;

    private byte rewardCount = 0;

    public WinScene(Character[] characters) {
        this.characters = characters;

        timer = new Timer(1.5f);
        dialogueBox = new DialogueBox(0.03);

        objects.add(dialogueBox);

        mohammed = new GameObject();
        mohammed.AddComponent(new SpriteRenderer(
                ResourceManager.GetTexture("resources/characters/Mohammed.png"), WHITE
        ));

        mohammed.transform.localScale = 0.25f;

        mohammed.transform.localPosition.x(10).y(100);

        darwin = new GameObject();
        darwin.AddComponent(new SpriteRenderer(
                ResourceManager.GetTexture("resources/characters/Darwin_The_Wizard.png"), WHITE
        ));

        darwin.transform.localScale = 0.25f;

        darwin.transform.localPosition.x(10).y(400);

        objects.add(mohammed);
        objects.add(darwin);

        darwin.active = false;
        mohammed.active = false;

        mohammedText = new GameObject();
        mohammedReward = new Text("+100 XP!", GetFontDefault(), 50, 3.0f, BLACK);

        mohammedText.transform.localPosition.x(230).y(200);

        mohammedText.AddComponent(mohammedReward);

        objects.add(mohammedText);

        mohammedText.active = false;

        darwinText = new GameObject();
        darwinReward = new Text("+100 XP!", GetFontDefault(), 50, 3.0f, BLACK);

        darwinText.transform.localPosition.x(230).y(500);

        darwinText.AddComponent(darwinReward);

        objects.add(darwinText);

        darwinText.active = false;

        random = new Random();
        byte rewardItem = (byte) random.nextInt(0, 3);

        switch (rewardItem) {
            case 0:
                reward = new HealthItem();
                break;
            case 1:
                reward = new ManaItem();
                break;
            case 2:
                reward = new FireballItem();
                break;
        }

        // Add rewards
        characters[0].increaseXP(100);
        characters[1].increaseXP(100);

        characters[0].getInventory().addItem(reward);
        characters[1].getInventory().addItem(reward);
    }

    @Override
    public void Init() {
        if (characters[0].isAlive()) {
            dialogueBox.promptText("Here are your rewards for defeating the Big Bad Billy...");
        } else {
            dialogueBox.promptText("Luckily, Mohammed recovered from his injuries.\nHere are your rewards for defeating Billy...");
        }
    }

    @Override
    public void Update() {
        dialogueBox.Update();

        if (!dialogueBox.isVisible()) {
            if (!timer.isPlaying()) {
                timer.start();
            }

            switch (rewardCount) {
                case 0:
                    mohammed.active = true;
                    break;
                case 1:
                    mohammedText.active = true;
                    break;
                case 2:
                    mohammedReward.text = "+100 XP! New item: " + reward.getName();
                    break;
                case 3:
                    darwin.active = true;
                    break;
                case 4:
                    darwinText.active = true;
                    break;
                case 5:
                    darwinReward.text = "+100 XP! New item: " + reward.getName();
                    break;
                case 6:
                    dialogueBox.promptText("And of course... Mohammed gets all his keyboards back!");
                    break;
                case 7:
                    dialogueBox.promptText("Thanks for playing!");
                    break;
                case 8:
                    System.exit(0);
                    // TODO: Change scene to TitleScreen if it is added :)
                    // Otherwise, do something else other than exit
            }
        }

        if (timer.timerDone()) {
            rewardCount++;
        }
    }

}
