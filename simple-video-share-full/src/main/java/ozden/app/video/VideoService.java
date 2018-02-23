package ozden.app.video;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
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
import java.util.*;

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

    public Optional<Video> getRandomVideoByTagIds(List<String> tagIds) {
        if (CollectionUtils.isEmpty(tagIds)) {
            return getRandomVideo();
        }

        List<Video> videos = videoRepository.findByVideoFilePathLike("%." + SupportedVideoFormat.MP4.getName());
        Map<Video, HashSet<String>> pairs = getVideoAndTagIdsPair(videos);
        Set<String> ids = new HashSet<>(tagIds);

        for (Map.Entry<Video, HashSet<String>> pair : pairs.entrySet()) {
            if (pair.getValue().containsAll(ids)) {
                return Optional.of(pair.getKey());
            }
        }

        return Optional.empty();
    }

    public Optional<Video> getRandomVideo() {
        List<Video> videos = videoRepository.findByVideoFilePathLike("%." + SupportedVideoFormat.MP4.getName());
        return generateRandomVideoFromVideoList(videos);
    }

    private Map<Video, HashSet<String>> getVideoAndTagIdsPair(List<Video> videos) {
        Map<Video, HashSet<String>> pairs = new HashMap<>();
        for (Video video : videos) {
            pairs.put(video, getVideoTagIdsAsList(video.getVideoTagIds()));
        }

        return pairs;
    }

    private HashSet<String> getVideoTagIdsAsList(String ids) {
        return new HashSet<>(Arrays.asList(ids.split(TAG_ID_SEPERATOR)));
    }

    private Optional<Video> generateRandomVideoFromVideoList(List<Video> videos) {
        return isEmpty(videos) ? Optional.empty() :
                new Random()
                        .ints(100, 0, videos.size())
                        .mapToObj(i -> videos.get(i))
                        .findFirst();
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
        
        File savedFile;
        try {
            savedFile = fileService.saveFile(file);
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }

        String videoTagIds;
        if (!isEmpty(videoTagNames)) {
            videoTagIds = createVideoTags(videoTagNames);
        } else {
            videoTagIds = getDefaultTagIdForVideo();
        }

        Video newVideo = new Video(savedFile.getName(), "", videoTagIds, savedFile.getAbsolutePath(), LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
        newVideo = videoRepository.save(newVideo);

        return Optional.of(newVideo);
    }

    public List<VideoTag> getAllVideoTags() {
        return videoTagRepository.findAll();
    }

    private String createVideoTags(List<String> videoTagNames) {
        if (isEmpty(videoTagNames)) {
            return "";
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

    public List<VideoTag> getVideoTagsById(List<Integer> ids) {
        return videoTagRepository.findByTagIdIn(ids);
    }

    private String getDefaultTagIdForVideo() {
        String tagId = "";
        List<VideoTag> tags = videoTagRepository.findByTagName(DEFAULT_VIDEO_TAG_NAME);
        if (isEmpty(tags)) {
            tagId = createDefaultVideoTag().getTagId() + TAG_ID_SEPERATOR;
        } else {
            tagId = tags.get(0).getTagId() + TAG_ID_SEPERATOR;
        }

        return tagId;
    }

    private VideoTag createDefaultVideoTag() {
        VideoTag tag = new VideoTag(DEFAULT_VIDEO_TAG_NAME);
        tag = videoTagRepository.save(tag);
        return tag;
    }

    public List<Video> getVideoByIds(List<Integer> videoIds) {
        return videoRepository.findByVideoIdIn(videoIds);
    }
}

