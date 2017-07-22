<template>
    <div class="col-xs-12 col-sm-8 col-sm-offset-2 col-md-6 col-md-offset-3">
        <video id="randVideoElm" controls width="560" height="315" ref="videoElm">
            <source id="randVideoSrc" :src="randVideoUrl" type="video/mp4"></source>
        </video>
        <br><input type="button" @click="loadRandVideo()" value="Load Random Video" class="form-control" />

        <a :href="randVideoUrl" download="">Download</a>
    </div>
</template>

<script>

    import { eventBus } from './main.js'
    import { config } from './config.js'

    export default {
        data () {
            return {
                randVideoUrl: '',
                videoElement: null,
                videoEvents: { onplaying: true, onpause: false},
                recentVideos: []
            }
        },
        mounted: function() {
            this.videoElement = this.$el.querySelector("#randVideoElm");
            this.loadRandVideo();
        },
        methods: {
            loadRandVideo() {
                // push the recent video to the stack before loading a new one
                if (this.randVideoUrl != ''){
                    this.recentVideos.push(this.randVideoUrl);
                    // notify that the list is updated
                    eventBus.$emit("recentVideos", this.recentVideos);
                }
                
                this.$http.get(config.BACK_END_URL + "/randomvideo")
                    .then(response => { return response.json()})
                    .then(data => {
                        // console.log(data);
                        this.randVideoUrl = config.BACK_END_URL + data.url;
                        this.loadAndPlayVideo();
                        
                    });
            },
            isVideoPlaying(videoElement) {
                var isPlaying = videoElement.currentTime > 0 && !videoElement.paused && !videoElement.ended 
                    && videoElement.readyState > 2;
                return isPlaying;
            },
            loadAndPlayVideo() {    
                this.videoElement = this.$el.querySelector("#randVideoElm");
                this.videoElement.load();     
                // FIXME: fix DOMException: The play() request was interrupted by a new load request.           
                if (!this.isVideoPlaying(this.videoElement)) {
                   this.videoElement.play();
                }   
            }
        },
        created() {
            eventBus.$on('selectedVideo', (data) => {
                console.log("selected video : " + data);
                this.randVideoUrl = data;
                this.loadAndPlayVideo();
            })
        }
    }

</script>