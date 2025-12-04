package engine;

import java.util.HashSet;

public class GameObject {
    public HashSet<Component> components = new HashSet<>();
    public Transform transform;

    public GameObject() {
        // Make sure all game objects have a transform component
        transform = new Transform();
        AddComponent(transform);
    }

    public void Update() {
        for (Component c : components)
            c.Update();
    }

    public void Render() {
        for (Component c : components)
            c.Render();
    }

    public void AddComponent(Component component) {
        components.add(component);
        component.parent = this;
    }

    public Component GetComponent(String type) {
        for (Component c : components) {
            if (c.type.equals(type))
                return c;
        }
        return null;
    }
}
