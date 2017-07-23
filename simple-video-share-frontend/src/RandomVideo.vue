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
    import { VideoService } from './service/VideoService.js'

    export default {
        mixins: [VideoService],
        
        data () {
            return {
                randVideoUrl: '',
                videoElement: null,
                videoEvents: { onplaying: true, onpause: false},
                recentVideos: []
            }
        },
        mounted() {
            // this function is from VideoService
            this.setVideoElement(this.$el.querySelector("#randVideoElm"));
            this.loadRandVideo();
        },

        methods: {
            loadRandVideo() {
                // send played video to recent videos
                if (this.randVideoUrl != ''){
                    // notify with new url
                    eventBus.$emit("newVideoUrl", this.randVideoUrl);
                }
                
                this.$http.get(config.BACK_END_URL + "/randomvideo")
                    .then(response => { return response.json()})
                    .then(data => {
                        // console.log(data);
                        this.randVideoUrl = config.BACK_END_URL + data.url;
                        this.loadAndPlayVideo();
                        
                    });
            },

            loadAndPlayVideo() {    
                // fixed but still persists in Chrome, works on Firefox
                this.pauseVideo();
                this.playVideo();
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