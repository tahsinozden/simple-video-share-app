package ozden.app.video;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ozden.app.common.FileService;

import java.io.File;
import java.io.InputStream;
import java.util.Optional;

@Service
public class VideoService {

	@Autowired
	private FileService fileService;

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
}

