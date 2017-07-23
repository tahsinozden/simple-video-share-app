package ozden.app.video;

public enum SupportedVideoFormat {
    MP4("mp4");

    private String name;

    SupportedVideoFormat(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
