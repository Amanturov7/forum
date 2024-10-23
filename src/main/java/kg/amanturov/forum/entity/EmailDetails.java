package kg.amanturov.forum.entity;

import lombok.Data;

@Data
public class EmailDetails {
    private String subject;
    private String recipient;
    private String msgBody;
    private String attachment;
}
