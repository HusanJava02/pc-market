package uz.pdp.apppcmarket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.pdp.apppcmarket.dto.Response;
import uz.pdp.apppcmarket.entity.Attachment;
import uz.pdp.apppcmarket.entity.AttachmentContent;
import uz.pdp.apppcmarket.repository.AttachmentContentRepository;
import uz.pdp.apppcmarket.repository.AttachmentRepository;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.Optional;

@Service
public class AttachmentDownloadService {

    @Autowired
    AttachmentRepository attachmentRepository;

    @Autowired
    AttachmentContentRepository attachmentContentRepository;


    public Response download(Integer id, HttpServletResponse httpServletResponse) {
        Optional<AttachmentContent> optionalAttachmentContent = attachmentContentRepository.findByAttachmentId(id);
        if (!optionalAttachmentContent.isPresent())
            return new Response(false,"not found file");
        AttachmentContent attachmentContent = optionalAttachmentContent.get();
        Attachment attachment = attachmentContent.getAttachment();
        httpServletResponse.setHeader("Content-Disposition","attachment; filename=\""+attachment.getName()+"\"");
        httpServletResponse.setContentType(attachment.getContentType());
        httpServletResponse.setStatus(200);
        try(ServletOutputStream outputStream = httpServletResponse.getOutputStream()){
            outputStream.write(attachmentContent.getBytes());
            return new Response(true,"succes");
        }catch (Exception e){
            e.printStackTrace();
            return new Response(false,"unsuccess");

        }

    }

    public Response upload(MultipartHttpServletRequest request) throws IOException {
        Iterator<String> fileNames = request.getFileNames();
        if (fileNames.hasNext()){
            String fileName = fileNames.next();
            MultipartFile file = request.getFile(fileName);
            if (file != null){
                Attachment attachment = new Attachment();
                attachment.setName(file.getName());
                attachment.setContentType(file.getContentType());
                attachment.setSize(file.getSize());
                Attachment savedAttachment = attachmentRepository.save(attachment);
                AttachmentContent attachmentContent = new AttachmentContent();
                attachmentContent.setAttachment(savedAttachment);
                attachmentContent.setBytes(file.getBytes());
                attachmentContentRepository.save(attachmentContent);
                return new Response(true,"succesfully saved");
            }else return new Response(false,"not found file is null");
        }else return new Response(false,"no any file sended");
    }

    public Attachment getInfoById(Integer id) {
        return attachmentRepository.findById(id).orElse(null);
    }
}
