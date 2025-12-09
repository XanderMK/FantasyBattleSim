package scene;

import com.raylib.Raylib;
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
import static com.raylib.Raylib.PlaySound;

public class WinScene extends Scene {

    private Random random;

    private Timer timer;
    private DialogueBox dialogueBox;

    private Character[] characters;

    private GameObject mohammed, darwin;

    private GameObject mohammedText, darwinText;
    private Text mohammedReward, darwinReward;

    private long mohammedXP, darwinXP;
    private Item mohammedItem, darwinItem;

    private Raylib.Sound fxItemGain, fxXpGain;

    private byte rewardCount = 0, rewardStore;

    public WinScene(Character[] characters) {
        this.characters = characters;

        random = new Random();

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
        mohammedReward = new Text("", GetFontDefault(), 50, 3.0f, BLACK);

        mohammedText.transform.localPosition.x(280).y(200);

        mohammedText.AddComponent(mohammedReward);

        objects.add(mohammedText);

        mohammedText.active = false;

        darwinText = new GameObject();
        darwinReward = new Text("", GetFontDefault(), 50, 3.0f, BLACK);

        darwinText.transform.localPosition.x(280).y(525);

        darwinText.AddComponent(darwinReward);

        objects.add(darwinText);

        darwinText.active = false;

        fxItemGain = ResourceManager.GetSound("resources/sfx/itemgain.wav");
        fxXpGain = ResourceManager.GetSound("resources/sfx/xpgain.wav");
    }

    @Override
    public void Init() {
        mohammedXP = random.nextLong(80, 120);
        characters[0].increaseXP(mohammedXP);

        mohammedItem = generateItem();
        characters[0].getInventory().addItem(mohammedItem);

        darwinXP = random.nextLong(80, 120);
        characters[1].increaseXP(darwinXP);

        darwinItem = generateItem();
        characters[1].getInventory().addItem(darwinItem);

        if (!characters[0].isAlive()) {
            dialogueBox.promptText("Luckily, Mohammed recovered from his injuries.\nHere are your rewards for defeating Billy...");
        } else if (!characters[1].isAlive()) {
            dialogueBox.promptText("Luckily, Darwin recovered from his injuries.\nHere are your rewards for defeating Billy...");
        } else {
            dialogueBox.promptText("Here are your rewards for defeating the Big Bad Billy...");
        }
    }

    @Override
    public void Update() {
        dialogueBox.Update();

        if (!dialogueBox.isVisible()) {
            if (!timer.isPlaying()) {
                timer.start();
            }

            switch (rewardStore) {
                case 0:
                    mohammed.active = true;
                    break;
                case 1:
                    mohammedText.active = true;

                    mohammedReward.text = String.format("+%d XP!", mohammedXP);

                    PlaySound(fxXpGain);
                    break;
                case 2:
                    mohammedReward.text = String.format("+%d XP!   New item: %s!", mohammedXP, mohammedItem.getName());

                    PlaySound(fxItemGain);
                    break;
                case 3:
                    darwin.active = true;
                    break;
                case 4:
                    darwinText.active = true;

                    darwinReward.text = String.format("+%d XP!", darwinXP);

                    PlaySound(fxXpGain);
                    break;
                case 5:
                    darwinReward.text = String.format("+%d XP!   New item: %s!", darwinXP, darwinItem.getName());

                    PlaySound(fxItemGain);
                    break;
                case 6:
                    dialogueBox.promptText("And of course... Mohammed gets all his keyboards back!");
                    break;
                case 7:
                    dialogueBox.promptText("Thanks for playing!");
                    break;
                case 8:
                    SceneManager.setScene(true, new TitleScene());
            }

            rewardStore = 0;
        }

        if (timer.timerDone()) {
            rewardCount++;
            rewardStore = rewardCount;
        }
    }

    public Item generateItem() {
        byte itemNumber = (byte) random.nextInt(0, 3);

        return switch (itemNumber) {
            case 1 -> new ManaItem();
            case 2 -> new FireballItem();
            default -> new HealthItem();
        };
    }

}
