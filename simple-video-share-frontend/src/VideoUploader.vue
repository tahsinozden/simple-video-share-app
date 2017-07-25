<template>
    <div class="container">
        <form action="/uploader"
			enctype="multipart/form-data" method="post">
            <div class="col-xs-12 col-sm-8 col-sm-offset-2 col-md-6 col-md-offset-3">
                <div class="row">
                        <h2>Please choose a file to upload!</h2>
                        <input 
                                type="file" 
                                name="file" 
                                size="40" 
                                accept="video/mp4" 
                                v-on:change="setVideoFile($event)" 
                                ref="inputFile" 
                                class="form-control">
                        <input 
                                type="submit" 
                                @click.prevent="uploadVideo()" 
                                value="Send" 
                                class="form-control" 
                                :disabled="disableSubmit">     
                        <div>
                            <p v-if="uploadStatus.success == 1" class="alert alert-success">Video Uploaded!</p>    
                            <p v-if="uploadStatus.fail == 1" class="alert alert-danger">Video Upload Failed!</p>
                            <p v-if="this.uploadStatus.uploading == 1" class="alert alert-info">Uploading...</p> 
                        </div> 
        
                </div>
            </div>
        </form>
    </div>
</template>

<script>
    import { config } from './config.js'

    export default {
        data() {
            return {
                chosenFile: '',
                uploadStatus: {success: 0, fail: 0, uploading: 0},
                disableSubmit: false
            }
        },
        methods: {
            uploadVideo() {

                var formData = new FormData();
                // for single file
                // document.getElementById("file").files[0]
                var selectedFile = this.$refs.inputFile.files[0];
                if (selectedFile == undefined || selectedFile == null ){
                    alert("select a file!");
                    return;
                }

                this.disableSubmit = true;
                this.uploadStatus.uploading = 1;
                this.uploadStatus.fail = 0;
                this.uploadStatus.success = 0;

                formData.append('file', selectedFile);
                this.$http.post(config.BACK_END_URL + "/uploader", formData, {headers: {'Content-Type': 'amultipart/form-data'}} )
                    .then(
                        response => { 
                            console.log(response);
                            this.uploadStatus.success = 1;
                            this.uploadStatus.uploading = 0;
                            this.uploadStatus.fail = 0;
                            this.disableSubmit = false;
                            this.$refs.inputFile.value = null;
                        },
                        error => {
                            console.log(error);
                            this.disableSubmit = false;
                            this.uploadStatus.fail = 1
                            this.uploadStatus.uploading = 0;
                            this.uploadStatus.success = 0;
                            alert(error.data.message);
                        }
                    );
            },
            
            setVideoFile(event) {
                console.log(event.target);
                this.chosenFile = event.target.value;
            }
        }
    }
</script>