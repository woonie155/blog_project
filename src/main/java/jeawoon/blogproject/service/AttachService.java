package jeawoon.blogproject.service;

import jeawoon.blogproject.entity.file.AttachFile;
import jeawoon.blogproject.entity.file.AttachType;
import jeawoon.blogproject.file.FileStore;
import jeawoon.blogproject.repository.AttachRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AttachService {

    private final AttachRepository attachRepository;
    private final FileStore fileStore;

    public List<AttachFile> saveAttachments(Map<AttachType, List<MultipartFile>> multipartFileListMap) throws IOException {
        List<AttachFile> imageFiles = fileStore.storeFiles(multipartFileListMap.get(AttachType.IMAGE), AttachType.IMAGE);
        List<AttachFile> uploadFiles = fileStore.storeFiles(multipartFileListMap.get(AttachType.FILE), AttachType.FILE);
        List<AttachFile> result = Stream.of(imageFiles, uploadFiles)
                .flatMap(f -> f.stream())
                .collect(Collectors.toList());

        return result;
    }


//    public Map<AttachType, List<AttachFile>> findAttachments() {
//        List<AttachFile> attachments = attachRepository.findAll();
//        Map<AttachType, List<AttachFile>> result = attachments.stream()
//                .collect(Collectors.groupingBy(AttachFile::getAttachType));
//
//        return result;
//    }

}
