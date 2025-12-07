package engine.components;

import static com.raylib.Raylib.*;

public class SpriteRenderer extends Component {
    public Texture texture = null;
    public Color tint = new Color().r((byte)255).g((byte)255).b((byte)255).a((byte)255);

    public SpriteRenderer() {
        type = "engine.components.SpriteRenderer";
    }
    public SpriteRenderer(Texture texture, Color tint) {
        type = "engine.components.SpriteRenderer";
        this.texture = texture;
        this.tint = tint;
    }

    public void Update() {}
    public void Render() {
        DrawTextureEx(texture, parentObject.transform.GetGlobalPosition(), parentObject.transform.GetGlobalRotation(), parentObject.transform.GetGlobalScale(), tint);
    }
}
