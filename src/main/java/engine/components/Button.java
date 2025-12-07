package engine.components;

import static com.raylib.Raylib.*;
import static main.Main.VIRTUAL_RATIO;

public class Button extends Component {

    public String text;
    public int width, height;

    public float offX, offY;

    public boolean visible = true;
    private boolean pressed = false;

    public Button() {
        type = "engine.components.Button";
    }

    public Button(String text, int width, int height) {
        type = "engine.components.Button";

        this.text = text;
        this.width = width;
        this.height = height;
    }

    public Button(String text, int width, int height, float offX, float offY) {
        type = "engine.components.Button";

        this.text = text;
        this.width = width;
        this.height = height;
        this.offX = offX;
        this.offY = offY;
    }

    @Override
    public void Update() {
        pressed = false;

        Vector2 modifiedMousePosition = GetMousePosition();
        modifiedMousePosition.x(modifiedMousePosition.x() / VIRTUAL_RATIO);
        modifiedMousePosition.y(modifiedMousePosition.y() / VIRTUAL_RATIO);

        // Annoying ass bug with scaling :(
        // Can't rely on built-in GUI button clicking
        if (visible) {
            if (CheckCollisionPointRec(modifiedMousePosition, new Rectangle()
                    .x(parentObject.transform.localPosition.x() + offX)
                    .y(parentObject.transform.localPosition.y() + offY)
                    .width(width * parentObject.transform.localScale)
                    .height(height * parentObject.transform.localScale))) {
                if (IsMouseButtonPressed(MOUSE_BUTTON_LEFT)) {
                    pressed = true;
                }
            }
        }
    }

    @Override
    public void Render() {
        if (visible) {
            GuiButton(new Rectangle()
                    .x(parentObject.transform.localPosition.x() + offX)
                    .y(parentObject.transform.localPosition.y() + offY)
                    .width(width)
                    .height(height), text);
        }
    }

    public boolean isPressed() {
        return pressed;
    }

}
