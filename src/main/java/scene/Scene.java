package scene;

import engine.GameObject;

import java.util.ArrayList;

public abstract class Scene {

    protected ArrayList<GameObject> objects;

    public Scene() {
        objects = new ArrayList<>();
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

    public ArrayList<GameObject> getObjects() {
        return objects;
    }

}
