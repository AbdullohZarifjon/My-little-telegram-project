package uz.pdp.telegram.entity;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Attachment extends BaseEntity {
    private String name;
    private String type;
    private byte[] content;

    public Attachment(String name, String type, byte[] content) {
        this.name = name;
        this.type = type;
        this.content = content;
    }

}
