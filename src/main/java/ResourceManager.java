import java.util.HashMap;

import com.raylib.Raylib.Texture;
import static com.raylib.Raylib.LoadTexture;
import static com.raylib.Raylib.UnloadTexture;

import com.raylib.Raylib.Font;
import static com.raylib.Raylib.LoadFont;
import static com.raylib.Raylib.UnloadFont;

import com.raylib.Raylib.Sound;
import static com.raylib.Raylib.LoadSound;
import static com.raylib.Raylib.UnloadSound;

import com.raylib.Raylib.Music;
import static com.raylib.Raylib.LoadMusicStream;
import static com.raylib.Raylib.UnloadMusicStream;

import com.raylib.Raylib.Shader;
import static com.raylib.Raylib.LoadShader;
import static com.raylib.Raylib.UnloadShader;


public class ResourceManager {
    // TEXTURES
    private static final HashMap<String, Texture> textures = new HashMap<>();
    public static Texture GetTexture(String filepath) {
        Texture result = textures.get(filepath);
        if (result == null) {
            result = LoadTexture(filepath);
            textures.put(filepath, result);
        }
        return result;
    }
    public static void UnloadTextures() {
        for (Texture texture : textures.values())
            UnloadTexture(texture);
        textures.clear();
    }

    // FONTS
    private static final HashMap<String, Font> fonts = new HashMap<>();
    public static Font GetFont(String filepath) {
        Font result = fonts.get(filepath);
        if (result == null) {
            result = LoadFont(filepath);
            fonts.put(filepath, result);
        }
        return result;
    }
    public static void UnloadFonts() {
        for (Font font : fonts.values())
            UnloadFont(font);
        fonts.clear();
    }

    // SOUNDS
    private static final HashMap<String, Sound> sounds = new HashMap<>();
    public static Sound GetSound(String filepath) {
        Sound result = sounds.get(filepath);
        if (result == null) {
            result = LoadSound(filepath);
            sounds.put(filepath, result);
        }
        return result;
    }
    public static void UnloadSounds() {
        for (Sound sound : sounds.values())
            UnloadSound(sound);
        sounds.clear();
    }

    // MUSIC
    private static final HashMap<String, Music> music = new HashMap<>();
    public static Music GetMusic(String filepath) {
        Music result = music.get(filepath);
        if (result == null) {
            result = LoadMusicStream(filepath);
            music.put(filepath, result);
        }
        return result;
    }
    public static void UnloadMusic() {
        for (Music music : music.values())
            UnloadMusicStream(music);
        music.clear();
    }

    // SHADERS
    private static final HashMap<String[], Shader> shaders = new HashMap<>();
    public static Shader GetShader(String vsFilepath, String fsFilepath) {
        Shader result = shaders.get(new String[] {vsFilepath, fsFilepath});
        if (result == null) {
            result = LoadShader(vsFilepath, fsFilepath);
            shaders.put(new String[] {vsFilepath, fsFilepath}, result);
        }
        return result;
    }
    public static void UnloadShaders() {
        for (Shader shader : shaders.values())
            UnloadShader(shader);
        shaders.clear();
    }

    // OTHER METHODS
    public static void UnloadAllAssets() {
        UnloadTextures();
        UnloadFonts();
        UnloadSounds();
        UnloadMusic();
        UnloadShaders();
        // Add more datatypes as needed...
    }
}
