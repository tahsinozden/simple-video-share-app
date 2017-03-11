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
    export default {
        data () {
            return {
                randVideoUrl: ''
            }
        },
        mounted: function() {
            this.loadRandVideo();
        },
        methods: {
            loadRandVideo() {
                this.$http.get('http://localhost:8080/randomvideo')
                    .then(response => { return response.json()})
                    .then(data => {
                        console.log(data);
                        this.randVideoUrl = "http://localhost:8080" + data.url;
                        let videoElm = this.$el.querySelector("#randVideoElm");
                        videoElm.load();
                        videoElm.play();
                        // console.log(this.$refs.videoElm);
                        // this.$refs.videoElm.load();
                        // this.$refs.videoElm.play();
                        // console.log(this.$el.querySelector("#randVideoElm"));
                    });
            }
        }
    }

</script>