package engine;

import static com.raylib.Raylib.*;

public class Transform extends Component {
    public Transform() {
        type = "engine.Transform";
    }
    public Transform(Vector2 position, float rotation, float scale) {
        type = "engine.Transform";
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
    }

    public void Update() {}
    public void Render() {}

    public Vector2 position = new Vector2().x(0.0f).y(0.0f);
    public float rotation = 0.0f;
    public float scale = 1.0f;
}
