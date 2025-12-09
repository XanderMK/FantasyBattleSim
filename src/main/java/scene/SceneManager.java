package scene;

import engine.ResourceManager;

public class SceneManager {

    private static Scene currentScene;

    public SceneManager(Scene initialScene) {
        currentScene = initialScene;
    }

    public void Init() {
        currentScene.Init();
    }

    public void Update() {
        currentScene.Update();
    }

    public void Render() {
        currentScene.Render();
    }

    public static void setScene(boolean autoInit, Scene scene) {
        currentScene = scene;
        ResourceManager.UnloadAllAssets();
        if (autoInit) currentScene.Init();
    }

}
