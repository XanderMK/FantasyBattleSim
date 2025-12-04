package scene;

import engine.GameObject;

import java.util.HashSet;

public abstract class Scene {

    protected HashSet<GameObject> objects;

    public Scene() {
        objects = new HashSet<>();
    }

    public abstract void Init();

    public void Update() {
        for (GameObject gameObject : objects)
            gameObject.Update();
    }

    public void Render() {
        for (GameObject gameObject : objects)
            gameObject.Render();
    }

    public HashSet<GameObject> getObjects() {
        return objects;
    }

}
