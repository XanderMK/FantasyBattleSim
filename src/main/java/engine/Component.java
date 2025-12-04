package engine;

public abstract class Component {
    public String type;
    public GameObject parent;
    public abstract void Update();
    public abstract void Render();
}
