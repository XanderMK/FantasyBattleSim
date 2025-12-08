package gameplay;

import engine.components.*;
import engine.ResourceManager;
import engine.Timer;
import engine.components.Character;
import menus.BattleMenu;
import menus.BattleMenuButtons;
import menus.ItemMenu;
import scene.LoseScene;
import scene.SceneManager;
import scene.WinScene;

import java.util.Random;

import static com.raylib.Raylib.*;

public class BattleEngine {

    private final double MANA_DRAIN = 2.0;
    private final double DEFENSE_DRAIN = 5.0;

    private Character[] characters;
    private Monster[] monsters;
    private BattleMenu battleMenu;
    private DialogueBox dialogueBox;
    private ItemMenu itemMenu;

    private boolean waiting = false;
    private boolean methodChosen = false;
    private boolean monsterAttacked = false;
    private boolean usedItem = false;
    private byte itemIndex = -1;
    private byte currentCharacter = 0;
    private byte currentMonster = -1;

    private Timer timer;
    private Random random;

    private BattleState battleState;

    private Sound fxItem;

    public BattleEngine(Character[] characters, Monster[] monsters, BattleMenu battleMenu, DialogueBox dialogueBox, ItemMenu itemMenu) {
        this.characters = characters;
        this.monsters = monsters;
        this.battleMenu = battleMenu;
        this.dialogueBox = dialogueBox;
        this.itemMenu = itemMenu;

        timer = new Timer(2.0f);
        random = new Random();

        battleState = BattleState.NOT_STARTED;

        fxItem = ResourceManager.GetSound("resources/sfx/item.wav");
    }

    public void startBattle() {
        battleState = BattleState.ONGOING;
        waiting = false;
        methodChosen = false;
        monsterAttacked = false;
        usedItem = false;
        itemIndex = -1;
        currentCharacter = 0;
        currentMonster = -1;
    }

    // This logic feels like its being held up by duct tape and my hopes and dreams, but
    // somehow it works. I think. There's probably some soul-destroying bug hiding
    // in the code somewhere. Somebody needs to test this more other than me.
    // - Skyler

    // P.S. It may seem like im complaining, but I really enjoyed working on this project :)

    // TODO: Monsters and characters need balancing
    // TODO: Truncate visual numerical decimals to 2 max

    public void Update() {
        if (battleState.equals(BattleState.ONGOING) && currentCharacter != -1 && currentMonster == -1) {
            if (!characters[currentCharacter].isAlive()) {
                currentCharacter++;

                if (currentCharacter >= characters.length) {
                    currentCharacter = -1;
                    currentMonster = 0;
                }
                return;
            }

            if (!waiting) {
                dialogueBox.promptText(characters[currentCharacter].getName() + ", you're up!");
                waiting = true;
            }
            if (!dialogueBox.isVisible()) {
                byte attackingMonster;
                do {
                    attackingMonster = (byte) random.nextInt(0, monsters.length);
                } while (!monsters[attackingMonster].isAlive());

                double criticalChance = random.nextDouble(0.0, 1.0);
                double missChance = random.nextDouble(0.0, 1.0);

                if (battleMenu.getFaceButtonAt(BattleMenuButtons.FACE_ATTACK.index).isPressed()) {
                    if (itemMenu.active) return;

                    if (battleMenu.getAttackButtonAt(0).visible) {
                        battleMenu.disableAttackButtons();
                    } else {
                        battleMenu.enableAttackButtons();
                    }
                }

                if (battleMenu.getAttackButtonAt(BattleMenuButtons.ATTACK_NORMAL.index).isPressed()) {
                    characters[currentCharacter].attackAnimation(true);

                    battleMenu.disableAttackButtons();

                    if (missChance < 0.10) { // 10% chance for miss
                        dialogueBox.promptText(characters[currentCharacter].getName() + " missed!");
                    } else if (criticalChance < 0.35) { // 35% chance for critical attacks
                        double modifiedDamage = characters[currentCharacter].getAttackDamage() * 1.2;

                        dialogueBox.promptText("CRITICAL! " + characters[currentCharacter].getName() + " used a normal attack on " +
                                monsters[attackingMonster].getName() + ". It did " + modifiedDamage + " damage!");
                        monsters[attackingMonster].modifyHealth(-modifiedDamage);
                    } else {
                        dialogueBox.promptText(characters[currentCharacter].getName() + " used a normal attack on " + monsters[attackingMonster].getName() + ". It did " +
                                characters[currentCharacter].getAttackDamage() + " damage!");
                        monsters[attackingMonster].modifyHealth(-characters[currentCharacter].getAttackDamage());
                    }

                    methodChosen = true;
                }
                if (battleMenu.getAttackButtonAt(BattleMenuButtons.ATTACK_MAGIC.index).isPressed()) {
                    battleMenu.disableAttackButtons();

                    if (characters[currentCharacter].getMana() <= 0) {
                        dialogueBox.promptText("You are out of mana! You can not perform magic attacks anymore.");

                        return;
                    }

                    characters[currentCharacter].attackAnimation(true);

                    double modifiedDamage = characters[currentCharacter].getAttackDamage() * characters[currentCharacter].getAttackDamageMultiplier();

                    dialogueBox.promptText(characters[currentCharacter].getName() + " used a magic attack on " + monsters[attackingMonster].getName() + ". It did " +
                            modifiedDamage + " damage! -" + MANA_DRAIN + " mana.");
                    monsters[attackingMonster].modifyHealth(-modifiedDamage);
                    characters[currentCharacter].modifyMana(-MANA_DRAIN);

                    methodChosen = true;
                }

                if (battleMenu.getAttackButtonAt(0).visible) return;

                if (battleMenu.getFaceButtonAt(BattleMenuButtons.FACE_ITEM.index).isPressed()) {
                    if (itemMenu.active) {
                        itemMenu.closeInventory();
                    } else {
                        itemMenu.displayInventory(characters[currentCharacter].getInventory());
                    }
                }

                if (itemMenu.active) {
                    byte i = 0;
                    for (ImageButton button : itemMenu.getImageButton()) {
                        if (button == null) {
                            i++;
                            continue;
                        }

                        if (button.isPressed()) {
                            itemMenu.closeInventory();

                            dialogueBox.promptText(characters[currentCharacter].getName() + " used " + characters[currentCharacter].getInventory().getItem(i).getName() +
                                    "! " + characters[currentCharacter].getInventory().getItem(i).getDescription());

                            if (characters[currentCharacter].getInventory().getItem(i).getType().equals("Character")) {
                                characters[currentCharacter].getInventory().getItem(i).effect(characters[currentCharacter]);
                            } else {
                                characters[currentCharacter].getInventory().getItem(i).effect(monsters[attackingMonster]);
                            }

                            PlaySound(fxItem);
                            itemIndex = i;
                            usedItem = true;
                        }

                        i++;
                    }
                }

                if (!dialogueBox.isVisible() && usedItem) {
                    characters[currentCharacter].getInventory().removeItem(characters[currentCharacter].getInventory().getItem(itemIndex));
                    usedItem = false;

                    methodChosen = true;
                }

                if (battleMenu.getFaceButtonAt(BattleMenuButtons.FACE_DEFEND.index).isPressed()) {
                    dialogueBox.promptText(characters[currentCharacter].getName() + " has extra defense next enemy attack!");
                    characters[currentCharacter].setDefending(true);

                    methodChosen = true;
                }

                if (battleMenu.getFaceButtonAt(BattleMenuButtons.FACE_FLEE.index).isPressed()) {
                    dialogueBox.promptText("You cannot flee from this fight!");
                }

                if (methodChosen && !dialogueBox.isPlaying()) {
                    waiting = false;
                    methodChosen = false;
                    currentCharacter++;

                    if (currentCharacter >= characters.length) {
                        currentCharacter = -1;
                        currentMonster = 0;
                    }
                }
            }
        }

        if (battleState.equals(BattleState.ONGOING) && currentMonster != -1 && currentCharacter == -1) {
            if (!monsters[currentMonster].isAlive()) {
                currentMonster++;

                if (currentMonster >= monsters.length) {
                    currentMonster = -1;
                    currentCharacter = 0;
                }
                return;
            }

            double missChance = random.nextDouble(0.0, 1.0);

            if (!waiting) {
                dialogueBox.promptText(monsters[currentMonster].getName() + " is attacking..!");
                waiting = true;
                timer.start();
            }
            if (waiting && timer.timerDone() && !monsterAttacked) {
                byte attackingCharacter = (byte) random.nextInt(0, characters.length);

                if (missChance < 0.75) { // 75% chance to hit
                    if (characters[attackingCharacter].isDefending()) {
                        double modifiedDamage = monsters[currentMonster].getAttackDamage() / monsters[currentMonster].getAttackDamageMultiplier();

                        monsters[currentMonster].attackAnimation(false);

                        dialogueBox.promptText(monsters[currentMonster].getName() + " attacked " + characters[attackingCharacter].getName() + "!" + " Extra defense is active. -" +
                                modifiedDamage + " HP.");
                        characters[attackingCharacter].setDefending(false);

                        characters[attackingCharacter].modifyHealth(-modifiedDamage);
                        characters[attackingCharacter].modifyDefense(-DEFENSE_DRAIN);
                    } else {
                        monsters[currentMonster].attackAnimation(false);

                        dialogueBox.promptText(monsters[currentMonster].getName() + " attacked " + characters[attackingCharacter].getName() + "! -" +
                                monsters[currentMonster].getAttackDamage() + " HP");

                        characters[attackingCharacter].modifyHealth(-monsters[currentMonster].getAttackDamage());
                    }
                } else {
                    monsters[currentMonster].attackAnimation(false);

                    dialogueBox.promptText(monsters[currentMonster].getName() + " missed!");
                }

                monsterAttacked = true;
            }
            if (monsterAttacked && !dialogueBox.isVisible()) {
                monsterAttacked = false;
                waiting = false;
                currentMonster++;

                if (currentMonster >= monsters.length) {
                    currentMonster = -1;
                    currentCharacter = 0;
                }
            }
        }

        if (allCharactersDead() && !dialogueBox.isVisible()) {
            battleState = BattleState.LOSS;
            SceneManager.setScene(true, new LoseScene());
        }

        if (allMonstersDead() && !battleState.equals(BattleState.WIN) && !dialogueBox.isPlaying()) {
            battleState = BattleState.WIN;
            waiting = false;
        }

        if (battleState.equals(BattleState.WIN)) {
            if (!waiting) {
                dialogueBox.promptText("You win! You have defeated Billy and his evil Goblins.");
                waiting = true;
            }
            if (!dialogueBox.isVisible()) {
                SceneManager.setScene(true, new WinScene(characters));
            }
        }
    }

    private boolean allCharactersDead() {
        for (Character character : characters) {
            if (character.isAlive()) {
                return false;
            }
        }

        return true;
    }

    private boolean allMonstersDead() {
        for (Monster monster : monsters) {
            if (monster.isAlive()) {
                return false;
            }
        }

        return true;
    }

    // Hi! You scrolled all the way to the bottom. And what did you find? Nothing.

}
