package jeawoon.blogproject.file;


import jeawoon.blogproject.entity.file.AttachFile;
import jeawoon.blogproject.entity.file.AttachType;
import jeawoon.blogproject.entity.file.UploadFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileStore {


    @Value("${file.dir}")
    private String fileDir;
    //=>  C:/Users/nolan/springfile/

    public String getFullPath(String filename, AttachType attachType){
        String type = (attachType == AttachType.FILE) ? "uploadfiles/" : "images/";
        return fileDir+type+filename; // C:/Users/nolan/springfile/타입/파일명.??;
    }
    public String getWebPath(String filename, AttachType attachType){
        String type = (attachType == AttachType.FILE) ? "uploadfiles/" : "images/";
        //        String reFilename = filename.substring(filename.lastIndexOf("/")+1);
        //        // C:/Users/nolan/springfile/images/c3a2eedd-4364-4f3f-a275-a800512e4949.png
        //localhost:8080/images/C:/Users/nolan/springfile/images/d1bda89a-5e02-4632-b9f6-a1ff57c210ae.png
        //localhost:8080/images/b6b97a89-3c8e-4dba-aa08-ee1cc1249cff.png  ==> 열었을때 이게 정상
        return filename; // 타입/파일명.???
    }

    public List<AttachFile> storeFiles(List<MultipartFile> multipartFiles, AttachType attachType) throws IOException {
        List<AttachFile> storeFileResult = new ArrayList<>();

        for(MultipartFile multipartFile : multipartFiles){
            if(!multipartFile.isEmpty()){
                storeFileResult.add(storeFile(multipartFile, attachType));
            }
        }
        return storeFileResult;
    }

    public AttachFile storeFile(MultipartFile multipartFile, AttachType attachType) throws IOException {
        if(multipartFile.isEmpty()) return null;

        String originalFilename = multipartFile.getOriginalFilename(); //db에 저장할 이름
        String uuid = UUID.randomUUID().toString();
        String ext = originalFilename.substring(originalFilename.lastIndexOf("."));
        String storeFileName = uuid+ext;
        multipartFile.transferTo(new File(getFullPath(storeFileName, attachType))); //서버에 저장할 이름

        return AttachFile.builder().uploadFileName(originalFilename).storeFileName(storeFileName).attachType(attachType).build();
    }
}
