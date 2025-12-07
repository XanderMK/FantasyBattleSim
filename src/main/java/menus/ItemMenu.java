package menus;

import engine.GameObject;
import engine.components.Component;
import engine.components.ImageButton;
import engine.components.RectangleRenderer;
import engine.components.Text;
import item.Inventory;
import item.Item;

import static com.raylib.Jaylib.WHITE;
import static com.raylib.Raylib.*;
import static com.raylib.Jaylib.DARKGRAY;
import static main.Main.VIRTUAL_HEIGHT;
import static main.Main.VIRTUAL_RATIO;

public class ItemMenu extends GameObject {

    private final int WIDTH = 950;
    private final int HEIGHT = 150;

    private RectangleRenderer background;
    private GameObject items;

    private ImageButton[] imageButton;

    private GameObject descriptionObj;
    private Text descriptionText;

    private Inventory inventory;

    public ItemMenu() {
        background = new RectangleRenderer(WIDTH, HEIGHT, DARKGRAY, false);

        AddComponent(background);
        items = new GameObject();

        descriptionObj = new GameObject();
        descriptionText = new Text("", GetFontDefault(), 20, 2.0f, WHITE);

        descriptionObj.AddComponent(descriptionText);
    }

    public void displayInventory(Inventory inventory) {
        this.inventory = inventory;
        active = true;

        imageButton = new ImageButton[inventory.getMaxItems()];

        byte i = 0;
        byte j = 0;
        for (Item item : inventory.getItems()) {
            if (item == null) {
                i++;
                continue;
            }

            imageButton[i] = new ImageButton(item.getTexture(), 40.0f + (j * 12 * item.transform.localScale * 2), VIRTUAL_HEIGHT - ((float) HEIGHT / 1.4f));
            items.AddComponent(imageButton[i]);
            imageButton[i].parentObject.transform.localScale = item.transform.localScale;
            i++;
            j++;
        }

        AddChild(items);
        AddChild(descriptionObj);
    }

    @Override
    public void Update() {
        if (!active) return;
        for (Component c : components)
            c.Update();
        for (GameObject g : children)
            g.Update();

        for (byte i = 0; i < imageButton.length; i++) {
            if (imageButton[i] == null) continue;

            if (imageButton[i].isHovered()) {
                descriptionText.text = inventory.getItem(i).getDescription();
                break;
            } else {
                descriptionText.text = "";
            }
        }

        descriptionObj.transform.SetGlobalPosition(new Vector2().x((GetMouseX() + 20) / VIRTUAL_RATIO).y((GetMouseY() + 20) / VIRTUAL_RATIO));
    }

    public void closeInventory() {
        for (ImageButton img : imageButton) {
            if (img == null) continue;

            items.RemoveComponent(img);
            descriptionText.text = "";
        }

        inventory = null;
        active = false;
    }

    public int getHeight() {
        return HEIGHT;
    }

    public ImageButton[] getImageButton() {
        return imageButton;
    }

}
