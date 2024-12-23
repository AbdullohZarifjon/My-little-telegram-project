package uz.pdp.telegram.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import uz.pdp.telegram.entity.Attachment;
import uz.pdp.telegram.entity.Messages;
import uz.pdp.telegram.entity.Users;
import uz.pdp.telegram.repo.AttachmentRepo;
import uz.pdp.telegram.repo.MessageRepo;
import uz.pdp.telegram.repo.UsersRepo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MyController {
    private final AttachmentRepo attachmentRepo;
    private final MessageRepo messageRepo;
    private final UsersRepo usersRepo;


    @PostMapping("/inputMessage")
    public String inputMessage(HttpServletRequest request, HttpSession session, Model model) throws ServletException, IOException {
        String text = request.getParameter("text");
        String toId = request.getParameter("toUserId");
        Object object = session.getAttribute("user");
        if (object == null || toId == null) {
            return "/login";
        }
        int toUserId = Integer.parseInt(toId);
        Users fromUser = (Users) object;

        Optional<Users> toUsersOptional2 = usersRepo.findById(toUserId);

        if (toUsersOptional2.isEmpty()) {
            return "/login";
        }
        Users toUser = toUsersOptional2.get();

        Attachment attachment = null;
        if (request.getPart("file") != null) {
            Part file = request.getPart("file");
            String type = getFileType(file);

            attachment = new Attachment(file.getSubmittedFileName(), type, file.getInputStream().readAllBytes());
            attachmentRepo.save(attachment);

        }
        saveMessage(fromUser, toUser, text, attachment);
        return getString(model, toUserId, fromUser, toUser, messageRepo);
    }

    private void saveMessage(Users fromUser, Users toUser, String text, Attachment attachment) {
        Messages message = new Messages();
        if (text != null && !text.isEmpty()) {
            message.setText(text);
        }
        if (attachment != null) {
            message.setAttachment(attachment);
        }
        message.setFromUser(fromUser);
        message.setToUser(toUser);
        messageRepo.save(message);
    }

    private String getFileType(Part file) throws IOException {
//            fileni type ni tekshirishni shu yerga
        String contentType = file.getContentType();
        String type = "";
        if (contentType.startsWith("image/")) {
            type = "image";
//                System.out.println("Image type");
        } else if (contentType.equals("audio/mpeg")) {
            type = "audio";
//                System.out.println("Audio type");
        }
        return type;
    }

    public static String getString(Model model, int toUserId, Users fromUser, Users toUser, MessageRepo messageRepo) {
        List<Users> usersForSidebarButton = messageRepo.findUniqueToUsersByFromUserId(fromUser.getId());
        List<Messages> messagesForContend = messageRepo.findMessagesByFromUser_IdAndToUser_IdOrFromUser_IdAndToUser_Id(fromUser.getId(), toUserId, toUserId, fromUser.getId());
        model.addAttribute("usersListForSidebar", usersForSidebarButton);
        model.addAttribute("messagesListForContend", messagesForContend);
        model.addAttribute("fromUserId", fromUser.getId());
        model.addAttribute("toUser", toUser);
        return "/main2";
    }

    @GetMapping("/file/get")
    public ResponseEntity<byte[]> getContentBytes(HttpServletRequest request) {
        String audioId = request.getParameter("audioId");
        String jpgId = request.getParameter("jpgId");

        Attachment attachment = getAttachment(audioId, jpgId);

        try {

            // Agar fayl topilmasa, 404 qaytaring
            if (attachment == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            // Faylni MIME turini aniqlash
            String mimeType = Files.probeContentType(Paths.get(attachment.getName()));
            mimeType = (mimeType != null) ? mimeType : "application/octet-stream";

            // Faylni qaytarish
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + attachment.getName() + "\"")
                    .contentType(MediaType.parseMediaType(mimeType))
                    .body(attachment.getContent());
        } catch (Exception e) {
            // Xato yuz berganda 500 qaytarish
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    private Attachment getAttachment(String audioId, String jpgId) {
        Attachment attachment = null;
        if (audioId != null) {
            int id = Integer.parseInt(audioId);
            Optional<Attachment> optionalAttachment = attachmentRepo.findById((long) id);
            if (optionalAttachment.isPresent()) {
                attachment = optionalAttachment.get();
            }
        } else if (jpgId != null) {
            int id = Integer.parseInt(jpgId);
            Optional<Attachment> optionalAttachment = attachmentRepo.findById((long) id);
            if (optionalAttachment.isPresent()) {
                attachment = optionalAttachment.get();
            }
        }
        return attachment;
    }

}
