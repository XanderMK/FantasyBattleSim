package engine.components;

import static com.raylib.Jaylib.BLACK;
import static com.raylib.Raylib.Color;
import static com.raylib.Raylib.Vector2;
import static com.raylib.Raylib.DrawRectangle;

public class RectangleRenderer extends Component {
    public int width = 100, height = 100;
    public Color color = BLACK;
    public boolean drawFromCenter = true;

    public RectangleRenderer() { type = "engine.components.RectangleRenderer"; }
    public RectangleRenderer(int width, int height, Color color, boolean drawFromCenter) {
        type = "engine.components.RectangleRenderer";
        this.width = width;
        this.height = height;
        this.color = color;
        this.drawFromCenter = drawFromCenter;
    }

    @Override
    public void Update() { }

    @Override
    public void Render() {
        Vector2 position = parentObject.transform.GetGlobalPosition();
        int posX = (int)position.x();
        int posY = (int)position.y();

        if (drawFromCenter)
        {
            posX -= width / 2;
            posY -= height / 2;
        }

        DrawRectangle(posX, posY, (int)(width * parentObject.transform.GetGlobalScale()), (int)(height * parentObject.transform.GetGlobalScale()), color);
    }
}
