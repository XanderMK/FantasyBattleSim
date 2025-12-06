package engine;

import org.w3c.dom.css.Rect;

import java.nio.file.attribute.FileAttribute;

import static com.raylib.Jaylib.BLACK;
import static com.raylib.Raylib.*;
import static com.raylib.Jaylib.WHITE;

public class DialogueBox extends GameObject {

    private final int WIDTH = 900;
    private final int HEIGHT = 100;

    private float textWidth;
    private int textLine = 0;

    private RectangleRenderer base;
    private RectangleRenderer outline;
    private String line = "";

    public double timeBetweenChar;

    private boolean visible = false;
    private boolean playing = false;

    private Timer timer;
    private Text text;

    private StringBuilder fullLine;
    private int nextChar = 0;

    private Sound fxTextMove, fxClick;

    public DialogueBox(double timeBetweenChar) {
        this.timeBetweenChar = timeBetweenChar;

        fxTextMove = ResourceManager.GetSound("resources/sfx/textmove.wav");
        fxClick = ResourceManager.GetSound("resources/sfx/click.wav");
    }

    public void promptText(String line) {
        if (playing) return;

        this.line = line;

        fullLine = new StringBuilder();
        timer = new Timer(timeBetweenChar);
        nextChar = 0;
        textLine = 0;

        outline = new RectangleRenderer(WIDTH, HEIGHT, BLACK, false);
        base = new RectangleRenderer(WIDTH - 5, HEIGHT - 5, WHITE, false);
        text = new Text("", GetFontDefault(), 30, 2.7f, BLACK);
        AddComponent(outline);
        AddComponent(text);
        AddComponent(base);
        timer.start();
        playing = true;
        visible = true;
    }

    public void endPrompt() {
        if (!visible) return;

        RemoveComponent(outline);
        RemoveComponent(base);
        RemoveComponent(text);
        PlaySound(fxClick);
        timer.waitTime = timeBetweenChar;
        visible = false;
    }

    @Override
    public void Update() {
        if (timer.timerDone() && playing) {
            if (nextChar >= line.length()) {
                playing = false;
                return;
            }

            // SetTextLineSpacing() is *NOT* in this version of Jaylib, so we are limited to 1.5 line spacing. So just...
            // don't write text longer than two lines :)
            textWidth = MeasureText(fullLine.substring(textLine), 30);
            if (textWidth > WIDTH) {
                fullLine.append("\n");
                textLine++;
            }

            fullLine.append(line.charAt(nextChar));
            nextChar++;
            text.text = fullLine.toString();
            timer.start();

            PlaySound(fxTextMove);
        }

        if (playing) {
            if (IsMouseButtonPressed(MOUSE_BUTTON_LEFT)) {
                timer.waitTime = 0.01;
            }
        } else {
            if (IsMouseButtonPressed(MOUSE_BUTTON_LEFT)) {
                endPrompt();
            }
        }
    }

    @Override
    public void Render() {
        // Annoying bug was annoying me. Stupid fix. Sorry.
        // - Skyler
        if (outline.parentObject != null && base.parentObject != null && text.parentObject != null) {
            outline.Render();
            base.Render();
            text.Render();
        }
    }

    public boolean isPlaying() {
        return playing;
    }

    public boolean isVisible() {
        return visible;
    }

}
