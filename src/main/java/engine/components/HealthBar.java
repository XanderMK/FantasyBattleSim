package engine.components;

import static com.raylib.Jaylib.GRAY;
import static com.raylib.Jaylib.GREEN;
import static com.raylib.Jaylib.YELLOW;
import static com.raylib.Jaylib.RED;
import static com.raylib.Raylib.DrawRectangle;

import com.raylib.Raylib.Vector2;
import com.raylib.Raylib.Color;

public class HealthBar extends Component {
    private Entity target = null;
    private int width = 350, height = 75, overlayPadding = 4;
    private Color backgroundColor = GRAY, overlayFullColor = GREEN, overlayMediumColor = YELLOW, overlayEmptyColor = RED;

    public HealthBar() { type = "engine.components.HealthBar"; }
    public HealthBar(Entity target, int width, int height, int overlayPadding, Color backgroundColor, Color overlayFullColor, Color overlayMediumColor, Color overlayEmptyColor) {
        type = "engine.components.HealthBar";
        this.target = target;
        this.width = width;
        this.height = height;
        this.overlayPadding = overlayPadding;
        this.backgroundColor = backgroundColor;
        this.overlayFullColor = overlayFullColor;
        this.overlayMediumColor = overlayMediumColor;
        this.overlayEmptyColor = overlayEmptyColor;
    }


    @Override
    public void Update() { }

    @Override
    public void Render() {
        double healthRatio = target.getHealth() / target.getMaxHealth();
        Vector2 position = parentObject.transform.GetGlobalPosition();
        float scale = parentObject.transform.GetGlobalScale();

        DrawRectangle((int)position.x(), (int)position.y(), (int)(width*scale), (int)(height*scale), backgroundColor);
        DrawRectangle(
                (int)position.x()+overlayPadding,
                (int)position.y()-overlayPadding,
                (int)(width*healthRatio*scale),
                (int)(height*scale),
                (healthRatio < (1./3) ? overlayEmptyColor : (healthRatio < (2./3) ? overlayMediumColor : overlayFullColor))
        );
    }

    public void SetTarget(Entity target) {
        this.target = target;
    }
}
