import static com.raylib.Raylib.*;

public class Text extends Component {
    public String text = "";
    public Font font = null;
    public float baseFontSize = 10.0f;
    public float spacing = 0.0f;
    public Color tint = new Color().r((byte)255).g((byte)255).b((byte)255).a((byte)255);

    public Text() {
        type = "Text";
    }
    public Text(String text, Font font, float baseFontSize, float spacing, Color tint) {
        type = "Text";
        this.text = text;
        this.font = font;
        this.baseFontSize = baseFontSize;
        this.spacing = spacing;
        this.tint = tint;
    }

    public void Update() {}
    public void Render() {
        DrawTextPro(font, text, parent.transform.position, new Vector2().x(0).y(0), parent.transform.rotation, baseFontSize * parent.transform.scale, spacing, tint);
    }
}
