package gameplay;

import engine.components.*;
import engine.ResourceManager;
import engine.Timer;
import engine.components.Character;
import menus.BattleMenu;
import menus.BattleMenuButtons;
import menus.ItemMenu;

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

    // TODO: Implement critical attacks and miss attacks for monsters (this is *super* important with the current balance... maybe not the criticals though)
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
                    dialogueBox.promptText(characters[currentCharacter].getName() + " used a normal attack on " + monsters[attackingMonster].getName() + ". It did " +
                            characters[currentCharacter].getAttackDamage() + " damage!");
                    monsters[attackingMonster].modifyHealth(-characters[currentCharacter].getAttackDamage());

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

            if (!waiting) {
                dialogueBox.promptText(monsters[currentMonster].getName() + " is attacking..!");
                waiting = true;
                timer.start();
            }
            if (waiting && timer.timerDone() && !monsterAttacked) {
                byte attackingCharacter = (byte) random.nextInt(0, characters.length);

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

        // TODO: Action for battle end
        if (allCharactersDead()) {
            battleState = BattleState.LOSS;
        }

        if (allMonstersDead()) {
            battleState = BattleState.WIN;
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

}
