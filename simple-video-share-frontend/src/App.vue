<template>
  <div id="app">
		
    <random-video></random-video>
    <video-uploader></video-uploader>
    <recent-videos-bar></recent-videos-bar>
  </div>
</template>

<script>

import RandomVideo from './RandomVideo.vue'
import VideoUploader from './VideoUploader.vue'
import RecentVideosBar from './RecentVideosBar.vue'

export default {
  data () {
    return {
      randVideoUrl: ''
    }
  },
  methods: {
    loadRandVideo() {
        this.$http.get('http://localhost:8080/randomvideo')
            .then(response => { return response.json()})
            .then(data => {
                console.log(data);
                this.randVideoUrl = "http://localhost:8080" + data.url;
                console.log(this.$refs.videoElm);
                this.$refs.videoElm.load();
                this.$refs.videoElm.play();
            });
    }
  },
  components: {
    randomVideo: RandomVideo,
    videoUploader: VideoUploader,
    recentVideosBar: RecentVideosBar
  }
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}

h1, h2 {
  font-weight: normal;
}

ul {
  list-style-type: none;
  padding: 0;
}

li {
  display: inline-block;
  margin: 0 10px;
}

a {
  color: #42b983;
}
</style>
