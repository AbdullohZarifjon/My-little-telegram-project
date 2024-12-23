package uz.pdp.telegram.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.telegram.entity.Attachment;

public interface AttachmentRepo extends JpaRepository<Attachment, Long> {
}
