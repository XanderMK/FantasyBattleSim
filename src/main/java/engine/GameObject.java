package engine;

import engine.components.Component;
import engine.components.Transform;

import java.util.ArrayList;

public class GameObject {
    public ArrayList<GameObject> children = new ArrayList<>();
    public ArrayList<Component> components = new ArrayList<>();

    public GameObject parent = null;
    public Transform transform;
    public boolean active = true;

    public GameObject() {
        // Make sure all game objects have a transform component
        transform = new Transform();
        AddComponent(transform);
    }

    public void Update() {
        if (!active) return;
        for (Component c : components)
            c.Update();
        for (GameObject g : children)
            g.Update();
    }

    public void Render() {
        if (!active) return;
        for (Component c : components)
            c.Render();
        for (GameObject g : children)
            g.Render();
    }

    public void AddComponent(Component component) {
        components.add(component);
        component.parentObject = this;
    }

    public void RemoveComponent(Component component) {
        components.remove(component);
        component.parentObject = null;
    }

    public Component GetComponent(String type) {
        for (Component c : components) {
            if (c.type.equals(type))
                return c;
        }
        return null;
    }

    public void AddChild(GameObject child) {
        children.add(child);
        child.parent = this;
    }
}
