package jeawoon.blogproject.controller;


import jeawoon.blogproject.config.auth.PrincipalDetail;
import jeawoon.blogproject.dto.board.PostWriteDto;
import jeawoon.blogproject.dto.board.PostWriteForm;
import jeawoon.blogproject.entity.file.AttachType;
import jeawoon.blogproject.file.FileStore;
import jeawoon.blogproject.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final FileStore fileStore;

    @GetMapping("/board/main")
    public String main(Model model, @PageableDefault(size=3, sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
        model.addAttribute("boards", boardService.post_list(pageable));
        return "board/main";
    }

    @GetMapping("/board/write")
    public String post_write(@ModelAttribute PostWriteForm form, @AuthenticationPrincipal PrincipalDetail principal, Model model){
        model.addAttribute("principal", principal.getUser());
        System.out.println("principal = " + principal.getUser().getId());
        return "board/writeForm";
    }
    @PostMapping("/board/write")
    public String post_save(@ModelAttribute PostWriteForm form, BindingResult bindingResult, RedirectAttributes redirectAttributes) throws IOException {
        if(bindingResult.hasErrors()) {
            log.info("bindingResult : {}", bindingResult.getFieldError());
            return "/board/main";
        }
        PostWriteDto dto = form.createPostWriteDto();
        Long boardId = boardService.post_write(dto);
        return "redirect:/board/"+boardId;
    }

    @GetMapping("/board/{id}")
    public String post_detail(@PathVariable("id") Long id, Model model){
        model.addAttribute("board", boardService.post_detail(id));
        return "board/detail";
    }

    @GetMapping("/board/{id}/update")
    public String post_update(@PathVariable("id") Long id, Model model){
        model.addAttribute("board", boardService.post_detail(id));
        return "board/updateForm";
    }

    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource processImg(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:" + fileStore.getFullPath(filename, AttachType.IMAGE));
    } //    fileStore.getFullPath=>  C:/Users/nolan/springfile/이미지폴더/파일명.확장자

    @GetMapping("/attach/{filename}")

    public ResponseEntity<Resource> processAttaches(@PathVariable String filename, @RequestParam String originName) throws MalformedURLException {
        UrlResource resource = new UrlResource("file:" + fileStore.getFullPath(filename, AttachType.FILE));
        //    fileStore.getFullPath=>  C:/Users/nolan/springfile/업로드폴더/파일명.확장자

        String encodedUploadFileName = UriUtils.encode(originName, StandardCharsets.UTF_8);
        String contentDisposition = "attachment; filename=\"" + encodedUploadFileName + "\"";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(resource);
    }
}
