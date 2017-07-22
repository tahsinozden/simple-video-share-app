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
                recentVideos: []
            }
        },
        mounted: function() {
            this.loadRandVideo();
        },
        methods: {
            loadRandVideo() {
                // push the recent video to the stack before loading a new one
                if (this.randVideoUrl != ''){
                    this.recentVideos.push(this.randVideoUrl);
                    // this.recentVideos.unshift(this.randVideoUrl);
                    // this.recentVideos.splice(0, 0, this.randVideoUrl);
                    // notify that the list is updated
                    eventBus.$emit("recentVideos", this.recentVideos);
                }
                
                this.$http.get(config.BACK_END_URL + "/randomvideo")
                    .then(response => { return response.json()})
                    .then(data => {
                        console.log(data);
                        this.randVideoUrl = config.BACK_END_URL + data.url;
                        let videoElm = this.$el.querySelector("#randVideoElm");
                        videoElm.load();
                        videoElm.play();
                        // console.log(this.$refs.videoElm);
                        // this.$refs.videoElm.load();
                        // this.$refs.videoElm.play();
                        // console.log(this.$el.querySelector("#randVideoElm"));
                    });
            },
            loadAndPlayVideo() {
                let videoElm = this.$el.querySelector("#randVideoElm");   
                // console.log(videoElm);            
                videoElm.load();
                videoElm.play();     
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