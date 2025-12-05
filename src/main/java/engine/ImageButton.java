package engine;

import static com.raylib.Jaylib.BLACK;
import static com.raylib.Jaylib.WHITE;
import static com.raylib.Raylib.*;

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

    @Override
    public void Update() {
        pressed = false;

        if (visible) {
            if (CheckCollisionPointRec(GetMousePosition(), new Rectangle()
                    .x(parentObject.transform.localPosition.x() + offX)
                    .y(parentObject.transform.localPosition.y() + offY)
                    .width(texture.width())
                    .height(texture.height()))) {
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
    }

    public boolean isPressed() {
        return pressed;
    }
}
