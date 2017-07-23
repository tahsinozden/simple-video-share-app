// set video element and go!
export const VideoService = {
    data() {
        return {
            videoElement: null,
            onplaying: false,
            onpause: true
        }
    },

    methods: {
        setVideoElement(videoElm) {
            this.videoElement = videoElm;
            // On video playing toggle values
            this.videoElement.onplaying = function() {
                this.onplaying = true;
                this.onpause = false;
            };

            // On video pause toggle values
            this.videoElement.onpause = function() {
                this.onplaying = false;
                this.onpause = true;
            };
        },

        videoElementExists() {
            return this.videoElement != null;
        },

        playVideo() {
            console.log("video start!");
            console.log(this.videoElement);
            console.log(this.videoElement.paused);
            console.log(this.onplaying);
            if (!this.videoElementExists()) {
                console.log("video not exist!");
                return;
            }

            if (!this.onplaying) {
                this.videoElement.load();
                this.videoElement.play();
            }
        },

        pauseVideo() {
            if (!this.videoElementExists()) {
                return;
            }

            if (this.onplaying) {
                this.videoElement.pause();
            }
        }

    }
}