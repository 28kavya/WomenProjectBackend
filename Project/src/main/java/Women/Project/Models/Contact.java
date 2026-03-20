package Women.Project.Models;

import jakarta.persistence.*;
import lombok.Data;
@Data
@Entity
@Table(name = "contacts")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String phone;
    private String relation;
    private String primaryContact;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}