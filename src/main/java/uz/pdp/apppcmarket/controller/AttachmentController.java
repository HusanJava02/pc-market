package uz.pdp.apppcmarket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.pdp.apppcmarket.dto.Response;
import uz.pdp.apppcmarket.entity.Attachment;
import uz.pdp.apppcmarket.entity.AttachmentContent;
import uz.pdp.apppcmarket.repository.AttachmentContentRepository;
import uz.pdp.apppcmarket.repository.AttachmentRepository;
import uz.pdp.apppcmarket.service.AttachmentDownloadService;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/attachment")
public class AttachmentController {

    @Autowired
    AttachmentDownloadService attachmentDownloadService;

    @GetMapping(value = "/download/{id}")
    public void downloadAttachment(@PathVariable Integer id, HttpServletResponse httpServletResponse)  {
      attachmentDownloadService.download(id,httpServletResponse);
    }

    @PostMapping("/upload")
    public HttpEntity<?> uploadAttachment(MultipartHttpServletRequest request) throws IOException {
        Response upload = attachmentDownloadService.upload(request);
        return ResponseEntity.ok(upload);
    }
    @GetMapping(value = "/info/{id}")
    public HttpEntity<?> getInfoFile(@PathVariable Integer id){
        Attachment infoById = attachmentDownloadService.getInfoById(id);
        return ResponseEntity.status(infoById != null ? HttpStatus.OK:HttpStatus.NOT_FOUND).body(infoById);
    }


}
