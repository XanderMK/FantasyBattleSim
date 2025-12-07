package engine.components;

import static com.raylib.Raylib.*;

public class Transform extends Component {
    public Transform() {
        type = "engine.components.Transform";
    }
    public Transform(Vector2 localPosition, float localRotation, float localScale) {
        type = "engine.components.Transform";
        this.localPosition = localPosition;
        this.localRotation = localRotation;
        this.localScale = localScale;
    }

    public void Update() {}
    public void Render() {}

    public Vector2 localPosition = new Vector2().x(0.0f).y(0.0f);
    public float localRotation = 0.0f;
    public float localScale = 1.0f;

    public Vector2 GetGlobalPosition() {
        if (parentObject.parent != null) {
            Vector2 parentPos = parentObject.parent.transform.GetGlobalPosition();
            return Vector2Add(parentPos, localPosition);
        }
        return localPosition;
    }
    public void SetGlobalPosition(Vector2 pos) {
        if (parentObject.parent != null) {
            Vector2 parentPos = parentObject.parent.transform.GetGlobalPosition();
            localPosition = Vector2Subtract(pos, parentPos);
        }
        else
            localPosition = pos;
    }

    public float GetGlobalRotation() {
        if (parentObject.parent != null) {
            float parentRot = parentObject.parent.transform.GetGlobalRotation();
            return parentRot + localRotation;
        }
        return localRotation;
    }
    public void SetGlobalRotation(float rot) {
        if (parentObject.parent != null) {
            float parentRot = parentObject.parent.transform.GetGlobalRotation();
            localRotation = rot - parentRot;
        }
        else
            localRotation = rot;
    }

    public float GetGlobalScale() {
        if (parentObject.parent != null) {
            float parentScale = parentObject.parent.transform.GetGlobalScale();
            return parentScale * localScale;
        }
        return localScale;
    }
    public void SetGlobalScale(float scale) {
        if (parentObject.parent != null) {
            float parentScale = parentObject.parent.transform.GetGlobalScale();
            localScale = scale / parentScale;
        }
        else
            localScale = scale;
    }
}
