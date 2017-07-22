<template> 
    <div>
        <h3>Recent Videos</h3>

        <div class="row">
            <!-- TODO: make a horizontal scroll -->
            <div v-for="(video, index) in recentVideos">
                <video class="col-md-2" @click="selectVideo(index)">
                    <source :src="video" type="video/mp4"></source>
                </video>
            </div>
        </div>

    </div>
</template>


<script>
    import { eventBus } from './main.js'

    export default {
        data() {
            return {
                recentVideos: [],
                selectedVideo: ''
            }
        },
        methods: {
            getRecentVideos() {
                this.recentVideos = this.$cookie.get("videos").split(','); 
                console.log(this.recentVideos);                
            },
            initVideos() {
                var lstVideos = [
                    'https://pixabay.com/en/videos/download/video-6815_large.mp4',
                    'https://pixabay.com/en/videos/download/video-8451_large.mp4'
                ];
                this.$cookie.set('videos', lstVideos, 1);
            },
            selectVideo(idx) {
                console.log(idx);
                this.selectedVideo = this.recentVideos[idx];
                eventBus.$emit('selectedVideo', this.selectedVideo);
            }
        },
        created() {
            eventBus.$on("recentVideos", (videos) => {
                console.log("new video added");
                // console.log(videos);
                // this.recentVideos = videos.length > 3 ? videos.slice(videos.length-3, videos.length) : videos;
                this.recentVideos = videos
                console.log(this.recentVideos);
            });
        },
        mounted: function() {
            // this.initVideos();
            // this.getRecentVideos();
        }
    }
</script>