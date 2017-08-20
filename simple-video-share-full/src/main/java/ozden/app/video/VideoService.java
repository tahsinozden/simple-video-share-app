package ozden.app.video;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ozden.app.common.FileService;
import ozden.app.video.persistance.Video;
import ozden.app.video.persistance.VideoRepository;
import ozden.app.video.persistance.VideoTag;
import ozden.app.video.persistance.VideoTagRepository;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

import static org.springframework.util.CollectionUtils.isEmpty;

@Service
public class VideoService {

    private final String DEFAULT_VIDEO_TAG_NAME = "general";
    private final String TAG_ID_SEPERATOR = ",";

    @Autowired
    private FileService fileService;

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private VideoTagRepository videoTagRepository;

    public Optional<String> getRandomVideoName(String videoFilesPath) {
        Optional<File> file = fileService.getRandomFileWithExtension(videoFilesPath, SupportedVideoFormat.MP4.getName());
        return file.isPresent() ? Optional.of(file.get().getName()) : Optional.empty();
    }

    public InputStream getVideoStream(String videoPath) {
        InputStream is = null;
        try {
            is = fileService.getFileStream(videoPath);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return is;
    }

    public Optional<Video> saveVideo(MultipartFile file, List<String> videoTagNames) {
        if (file == null) {
            throw new IllegalArgumentException("file is null!");
        }

        String videoName = file.getName();
        String savedFilePath;
        try {
            savedFilePath = fileService.saveFile(file);
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }


        String videoTagIds = createVideoTags(videoTagNames);
        Video newVideo = new Video(videoName, "", videoTagIds, savedFilePath, LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
        newVideo = videoRepository.save(newVideo);

        return Optional.of(newVideo);
    }

    public List<VideoTag> getAllVideoTags() {
        return videoTagRepository.findAll();
    }

    private String createVideoTags(List<String> videoTagNames) {
        if (isEmpty(videoTagNames)) {
            VideoTag tag = new VideoTag(DEFAULT_VIDEO_TAG_NAME);
            tag = videoTagRepository.save(tag);
            return tag.getTagName();
        }

        StringBuilder tagString = new StringBuilder();

        videoTagNames.stream()
                .forEach(name -> {
                    VideoTag videoTag;
                    List<VideoTag> tmpTags = videoTagRepository.findByTagName(name);
                    if (!isEmpty(tmpTags)) {
                        videoTag = tmpTags.get(0);
                    } else {
                        videoTag = videoTagRepository.save(new VideoTag(name));
                    }

                    tagString.append(videoTag.getTagId()).append(TAG_ID_SEPERATOR);
                });

        return tagString.toString();
    }

}

