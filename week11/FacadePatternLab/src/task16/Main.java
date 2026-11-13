
package task16;

// I used ChatGPT to help with this task.

class AssetLoader {
    void load(int level) {
        System.out.println("Loading level " + level);
    }
}

class TextureCache {
    void loadTextures() {
        System.out.println("Loading textures");
    }

    void clear() {
        System.out.println("Textures cleared");
    }
}

class PhysicsEngine {
    void start() {
        System.out.println("Physics started");
    }

    void reset() {
        System.out.println("Physics reset");
    }
}

class AudioMixer {
    void playMusic() {
        System.out.println("Music started");
    }
}

class SaveStateReader {
    void readSave(String slot) {
        System.out.println("Reading save: " + slot);
    }
}

class GameLevelFacade {
    private AssetLoader assets = new AssetLoader();
    private TextureCache textures = new TextureCache();
    private PhysicsEngine physics = new PhysicsEngine();
    private AudioMixer audio = new AudioMixer();
    private SaveStateReader saves = new SaveStateReader();

    void loadLevel(int levelNumber, String saveSlot) {
        if (levelNumber <= 0) {
            System.out.println("Invalid level");
            return;
        }

        assets.load(levelNumber);
        textures.loadTextures();
        physics.start();

        // TODO: start background music
        // TODO: restore player position from save slot

        System.out.println("Level loaded");
    }

    void unloadCurrentLevel() {
        textures.clear();
        physics.reset();

        // TODO: stop music when leaving the level

        System.out.println("Level unloaded");
    }
}

public class Main {
    public static void main(String[] args) {
        GameLevelFacade game = new GameLevelFacade();

        game.loadLevel(1, "save1");

        System.out.println();

        game.unloadCurrentLevel();
    }
}
