package menus;

import engine.Component;
import engine.GameObject;
import engine.ImageButton;
import engine.RectangleRenderer;
import item.Inventory;
import item.Item;

import static com.raylib.Jaylib.DARKGRAY;
import static main.Main.VIRTUAL_HEIGHT;

public class ItemMenu extends GameObject {

    private final int WIDTH = 950;
    private final int HEIGHT = 150;

    private RectangleRenderer background;
    private GameObject items;

    private ImageButton[] imageButton;

    public ItemMenu() {
        background = new RectangleRenderer(WIDTH, HEIGHT, DARKGRAY, false);

        AddComponent(background);
        items = new GameObject();
    }

    public void displayInventory(Inventory inventory) {
        active = true;

        imageButton = new ImageButton[inventory.getMaxItems()];

        byte i = 0;
        byte j = 0;
        for (Item item : inventory.getItems()) {
            if (item == null) {
                i++;
                continue;
            }

            imageButton[i] = new ImageButton(item.getTexture(), 40.0f + (j * item.getTexture().width() * item.transform.localScale * 2), VIRTUAL_HEIGHT - ((float) HEIGHT / 1.4f));
            items.AddComponent(imageButton[i]);
            imageButton[i].parentObject.transform.localScale = item.transform.localScale;
            i++;
            j++;
        }

        AddChild(items);
    }

    public void closeInventory() {
        for (ImageButton img : imageButton) {
            if (img == null) continue;

            items.RemoveComponent(img);
        }

        active = false;
    }

    public int getHeight() {
        return HEIGHT;
    }

    public ImageButton[] getImageButton() {
        return imageButton;
    }

}
