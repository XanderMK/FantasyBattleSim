package engine;

import static com.raylib.Jaylib.RED;
import static com.raylib.Jaylib.WHITE;
import static com.raylib.Raylib.*;
import static main.Main.VIRTUAL_RATIO;

public class ImageButton extends Component {

    public String imagePath;
    public float offX, offY;
    public Texture texture;

    private boolean pressed;
    public boolean visible = true;

    public ImageButton() {
        type = "engine.ImageButton";
    }

    public ImageButton(String imagePath) {
        type = "engine.ImageButton";

        this.imagePath = imagePath;

        texture = ResourceManager.GetTexture(imagePath);
    }

    public ImageButton(String imagePath, float offX, float offY) {
        type = "engine.ImageButton";

        this.imagePath = imagePath;
        this.offX = offX;
        this.offY = offY;

        texture = ResourceManager.GetTexture(imagePath);
    }

    public ImageButton(Texture texture) {
        type = "engine.ImageButton";

        this.texture = texture;
    }

    public ImageButton(Texture texture, float offX, float offY) {
        type = "engine.ImageButton";

        this.texture = texture;
        this.offX = offX;
        this.offY = offY;
    }

    @Override
    public void Update() {
        pressed = false;

        Vector2 modifiedMousePosition = GetMousePosition();
        modifiedMousePosition.x(modifiedMousePosition.x() / VIRTUAL_RATIO);
        modifiedMousePosition.y(modifiedMousePosition.y() / VIRTUAL_RATIO);

        if (visible) {
            if (CheckCollisionPointRec(modifiedMousePosition, new Rectangle()
                    .x(parentObject.transform.localPosition.x() + offX)
                    .y(parentObject.transform.localPosition.y() + offY)
                    .width(texture.width() * parentObject.transform.localScale)
                    .height(texture.height() * parentObject.transform.localScale))) {
                if (IsMouseButtonPressed(MOUSE_BUTTON_LEFT)) {
                    pressed = true;
                }
            }
        }
    }

    @Override
    public void Render() {
        if (visible) {
            DrawTextureEx(texture, new Vector2()
                    .x(parentObject.transform.localPosition.x() + offX)
                    .y(parentObject.transform.localPosition.y() + offY), parentObject.transform.localRotation, parentObject.transform.localScale, WHITE);
        }
        /*
        DrawRectangleRec(new Rectangle()
                .x(parentObject.transform.localPosition.x() + offX)
                .y(parentObject.transform.localPosition.y() + offY)
                .width(texture.width() * parentObject.transform.localScale)
                .height(texture.height() * parentObject.transform.localScale), new Color().r(RED.r()).g(RED.g()).b(RED.b()).a((byte) 20));*/
    }

    public boolean isPressed() {
        return pressed;
    }
}
