package week12_practice.FacadeCodingQuestions.task05;

class VideoFile {
    private String filename;

    public VideoFile(String filename) {
        this.filename = filename;
        System.out.println("Loading video: " + filename);
    }

    public String getFilename() {
        return filename;
    }
}

class CodecFactory {
    public String extractCodec(VideoFile file) {
        System.out.println("Detecting video codec...");
        return "Original Codec";
    }
}

class BitrateReader {
    public String convert(VideoFile file, String codec) {
        System.out.println("Converting video to " + codec + "...");
        return "Converted Video Data";
    }
}

class AudioMixer {
    public String fixAudio(String videoData) {
        System.out.println("Processing audio...");
        return videoData;
    }
}

class VideoConverterFacade {
    private CodecFactory codecFactory = new CodecFactory();
    private BitrateReader bitrateReader = new BitrateReader();
    private AudioMixer audioMixer = new AudioMixer();

    public void convert(String filename, String targetFormat) {
        System.out.println("Starting video conversion...");

        VideoFile file = new VideoFile(filename);

        String codec = codecFactory.extractCodec(file);
        System.out.println("Detected codec: " + codec);

        String convertedData = bitrateReader.convert(file, targetFormat);

        audioMixer.fixAudio(convertedData);

        System.out.println("Video converted successfully to "
                + targetFormat);
    }
}

public class Main {
    public static void main(String[] args) {
        VideoConverterFacade converter = new VideoConverterFacade();

        converter.convert("movie.avi", "mp4");
    }
}