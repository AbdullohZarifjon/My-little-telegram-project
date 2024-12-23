package uz.pdp.telegram.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Messages extends BaseEntity {
    private String text;
    @ManyToOne
    private Users fromUser;
    @ManyToOne
    private Users toUser;
    @ManyToOne
    private Attachment attachment;

    public Messages(Users fromUser, Users toUser, Attachment attachment) {
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.attachment = attachment;
    }

    public Messages(String text, Users fromUser, Users toUser) {
        this.text = text;
        this.fromUser = fromUser;
        this.toUser = toUser;
    }
}
