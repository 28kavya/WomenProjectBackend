package Women.Project.Controller;

import Women.Project.Models.Contact;
import Women.Project.Models.ContactDTO;
import Women.Project.Models.User;
import Women.Project.Repository.AuthRepository;
import Women.Project.Repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/contacts")
@CrossOrigin(origins = "*")
public class ContactController {

    @Autowired
    private ContactRepository repository;

    @Autowired
    private AuthRepository userRepository;

    @PostMapping
    public Contact saveContact(@RequestBody ContactDTO request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Contact contact = new Contact();
        contact.setName(request.getName());
        contact.setPhone(request.getPhone());
        contact.setRelation(request.getRelation());
        contact.setPrimaryContact(request.getPrimaryContact());
        contact.setUser(user);

        return repository.save(contact);
    }

    @GetMapping("/{userId}")
    public List<Contact> getContactsByUser(@PathVariable Long userId) {
        return repository.findByUser_Id(userId);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }
}