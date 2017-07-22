<template> 
    <div>

        <h3>Recent Videos</h3>
        <div class="scrollmenu">
            <div v-for="(video, index) in recentVideos">
                <div>
                    <video @click="selectVideo(index)">
                        <source :src="video" type="video/mp4"></source>
                    </video>
                </div>
            </div>
        </div>

    </div>
</template>

<<style>

div.scrollmenu {
    background-color: #333;
    overflow: auto;
    white-space: nowrap;
}

div.scrollmenu div {
    display: inline-block;
    color: white;
    text-align: center;
    padding: 14px;
    text-decoration: none;
}

div.scrollmenu video {
    width: 200px;
    height: 100px;
}

div.scrollmenu video:hover {
    background-color: #777;
}

</style>

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
                // TODO: show videos in reverse order
                this.recentVideos = videos;
                console.log(this.recentVideos);
            });
        },
        mounted: function() {
            // this.initVideos();
            // this.getRecentVideos();
        }
    }
</script>