package com.example.contactmanagement.controller;

import com.example.contactmanagement.model.Contact;
import com.example.contactmanagement.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/contacts")
public class ContactController {

    @Autowired
    private ContactRepository contactRepository;

    @GetMapping
    public String listContacts(Model model) {
        List<Contact> contacts = contactRepository.findAll();
        model.addAttribute("contacts", contacts);
        return "contacts";
    }

    @GetMapping("/new")
    public String newContactForm(Model model) {
        model.addAttribute("contact", new Contact());
        return "new_contact";
    }

    @PostMapping
    public String createContact(@ModelAttribute Contact contact) {
        contactRepository.save(contact);
        return "redirect:/contacts";
    }

    @GetMapping("/edit/{id}")
    public String editContactForm(@PathVariable Long id, Model model) {
        Contact contact = contactRepository.findById(id)
                                           .orElseThrow(() -> new IllegalArgumentException("Contato inválido: " + id));
        model.addAttribute("contact", contact);
        return "edit_contact";
    }

    @PostMapping("/update/{id}")
    public String updateContact(@PathVariable Long id, @ModelAttribute Contact contact) {
        Contact existingContact = contactRepository.findById(id)
                                                  .orElseThrow(() -> new IllegalArgumentException("Contato inválido: " + id));
        existingContact.setName(contact.getName());
        existingContact.setEmail(contact.getEmail());
        existingContact.setPhone(contact.getPhone());
        contactRepository.save(existingContact);
        return "redirect:/contacts";
    }

    @GetMapping("/delete/{id}")
    public String deleteContact(@PathVariable Long id) {
        Contact contact = contactRepository.findById(id)
                                           .orElseThrow(() -> new IllegalArgumentException("Contato inválido: " + id));
        contactRepository.delete(contact);
        return "redirect:/contacts";
    }
}
