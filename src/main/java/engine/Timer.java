package engine;

import static com.raylib.Raylib.*;

public class Timer {

    public double waitTime;

    private double startTime;

    private boolean playing = false;

    public Timer(double waitTime) {
        this.waitTime = waitTime;
    }

    public void start() {
        startTime = GetTime();
        playing = true;
    }

    public boolean timerDone() {
        if (GetTime() - startTime >= waitTime && playing) {
            playing = false;
            return true;
        }

        return false;
    }

    public double getElapsed() {
        return GetTime() - startTime;
    }

    public boolean isPlaying() {
        return playing;
    }
}
