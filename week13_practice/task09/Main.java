package week13_practice.task09;

interface BitrateStrategy {
    String selectResolution(double bandwidth);
}

class DataSaverStrategy implements BitrateStrategy {
    public String selectResolution(double bandwidth) {
        return bandwidth >= 2 ? "480p" : "240p";
    }
}

class BalancedAutoStrategy implements BitrateStrategy {
    public String selectResolution(double bandwidth) {
        if (bandwidth >= 5) {
            return "1080p";
        } else if (bandwidth >= 2) {
            return "720p";
        }

        return "360p";
    }
}

class UltraHdStrategy implements BitrateStrategy {
    public String selectResolution(double bandwidth) {
        if (bandwidth >= 20) {
            return "2160p";
        } else if (bandwidth >= 5) {
            return "1080p";
        }

        return "480p";
    }
}

class BufferMonitor {
    public double getBandwidth() {
        return 25.0;
    }
}

class VideoDecoder {
    public void decode(String streamUrl) {
        System.out.println("Loading stream: " + streamUrl);
    }

    public void setResolution(String resolution) {
        System.out.println("Video quality: " + resolution);
    }
}

class AudioSyncEngine {
    public void synchronize() {
        System.out.println("Audio synchronized");
    }

    public void maintainSync() {
        System.out.println("Audio synchronization maintained");
    }
}

class SubtitleRenderer {
    public void render() {
        System.out.println("Subtitles enabled");
    }
}

// Facade
class MediaPlayerFacade {
    private BitrateStrategy strategy;
    private boolean playing = false;

    private final BufferMonitor buffer = new BufferMonitor();
    private final VideoDecoder decoder = new VideoDecoder();
    private final AudioSyncEngine audio = new AudioSyncEngine();
    private final SubtitleRenderer subtitles = new SubtitleRenderer();

    public MediaPlayerFacade(BitrateStrategy strategy) {
        this.strategy = strategy;
    }

    public void playStream(String streamUrl) {
        double bandwidth = buffer.getBandwidth();

        System.out.println("Bandwidth: " + bandwidth + " Mbps");

        String resolution = strategy.selectResolution(bandwidth);

        decoder.decode(streamUrl);
        decoder.setResolution(resolution);

        audio.synchronize();
        subtitles.render();

        playing = true;

        System.out.println("Video is playing");
    }

    public void changeQualityMode(BitrateStrategy strategy) {
        this.strategy = strategy;

        if (playing) {
            double bandwidth = buffer.getBandwidth();

            String resolution =
                    strategy.selectResolution(bandwidth);

            decoder.setResolution(resolution);
            audio.maintainSync();

            System.out.println("Playback continues");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        MediaPlayerFacade player =
                new MediaPlayerFacade(new DataSaverStrategy());

        System.out.println("Data Saver Mode:");
        player.playStream("https://example.com/video");

        System.out.println("\nBalanced Auto Mode:");
        player.changeQualityMode(new BalancedAutoStrategy());

        System.out.println("\nUltra HD Mode:");
        player.changeQualityMode(new UltraHdStrategy());

    }
}


