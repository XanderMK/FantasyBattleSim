package engine.components;

import engine.GameObject;

public abstract class Component {
    public String type;
    public GameObject parentObject;
    public abstract void Update();
    public abstract void Render();
}
