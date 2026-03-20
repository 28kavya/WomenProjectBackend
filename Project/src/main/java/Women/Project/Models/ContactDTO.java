package Women.Project.Models;

import lombok.Data;

@Data
public class ContactDTO {
    private String name;
    private String phone;
    private String relation;
    private String primaryContact;
    private Long userId;
}
