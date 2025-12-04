import static com.raylib.Raylib.*;

public class SpriteRenderer extends Component {
    public Texture texture = null;
    public Color tint = new Color().r((byte)255).g((byte)255).b((byte)255).a((byte)255);

    public SpriteRenderer() {
        type = "SpriteRenderer";
    }
    public SpriteRenderer(Texture texture, Color tint) {
        type = "SpriteRenderer";
        this.texture = texture;
        this.tint = tint;
    }

    public void Update() {}
    public void Render() {
        DrawTextureEx(texture, parent.transform.position, parent.transform.rotation, parent.transform.scale, tint);
    }
}
