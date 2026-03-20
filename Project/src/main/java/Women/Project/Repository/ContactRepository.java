package Women.Project.Repository;

import Women.Project.Models.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    List<Contact> findByUser_Id(Long userId);
}