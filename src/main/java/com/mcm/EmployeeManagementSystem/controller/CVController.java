package com.mcm.EmployeeManagementSystem.controller;

import com.mcm.EmployeeManagementSystem.model.Skill;
import com.mcm.EmployeeManagementSystem.model.User;
import com.mcm.EmployeeManagementSystem.store.SkillStore;
import com.mcm.EmployeeManagementSystem.store.UserStore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.*;

import org.apache.poi.xwpf.usermodel.*;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/cv")
public class CVController {
    private final UserStore userStore;
    private final SkillStore skillStore;

    @GetMapping("/user/{id}")
    public ResponseEntity<InputStreamResource> downloadCV(@PathVariable Long id) throws IOException {
        User user = userStore.find(id);
        List<Skill> skills = skillStore.findByUser(user);
        System.out.println(user.getName());
        System.out.println(skills);
        // Create a new Word document
        XWPFDocument document = new XWPFDocument();

        XWPFParagraph nameParagraph = document.createParagraph();
        XWPFRun nameRun = nameParagraph.createRun();
        nameRun.setText(user.getName() + " " + user.getSurname());

        // Create a paragraph for the contact details
        XWPFParagraph emailParagraph = document.createParagraph();
        XWPFRun emailRun = emailParagraph.createRun();
        emailRun.setText("Email: " + user.getEmail());

        XWPFParagraph contactParagraph = document.createParagraph();
        XWPFRun contactRun = contactParagraph.createRun();
        contactRun.setText("Phone: " + user.getPhoneNum());

        XWPFParagraph addressParagraph = document.createParagraph();
        XWPFRun addressRun = addressParagraph.createRun();
        addressRun.setText("Address: " + user.getAddress().getStreet() + " " + user.getAddress().getNumber() + ", " + user.getAddress().getCity() + ", " + user.getAddress().getCountry());

        XWPFParagraph skillsParagraph = document.createParagraph();
        XWPFRun skillsRun = skillsParagraph.createRun();
        skillsRun.setText("Skills:");

        for (Skill skill: skills) {
            XWPFParagraph skillParagraph = document.createParagraph();
            XWPFRun skillRun = skillParagraph.createRun();
            skillRun.setText(skill.getName() + ": " + skill.getLevel());
        }

        // Generate the CV document
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        document.write(baos);
        baos.close();

        // Set the response headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "cv.docx");

        // Create an InputStreamResource from the generated CV document
        InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(baos.toByteArray()));

        // Return the ResponseEntity with the CV document as the response body
        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }
}
