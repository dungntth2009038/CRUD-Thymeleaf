package comt2009m1.demo.controller;

import comt2009m1.demo.entity.Student;
import comt2009m1.demo.repository.StudentRepository;
import comt2009m1.demo.service.StudentNotFoundException;
import comt2009m1.demo.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = "admin/students")
@RequiredArgsConstructor
@Slf4j
public class StudentController {
    @Autowired
    private StudentRepository studentRepository;

    final StudentService studentService;

    @RequestMapping(path = "/create", method = RequestMethod.GET)
    public String createStudent(Model model) {
//        Student student = new Student("A001","Tien Dung", "dung@gmail.com", "0365053725" , 1);
        List<String> strings = new ArrayList<>();
        strings.add("Student");
        strings.add("Teacher");
        strings.add("Doctor");
        model.addAttribute("professional", strings);
        model.addAttribute("student", new Student());
        return "views/students/create";
    }

    //    @RequestMapping(path = "/create", method = RequestMethod.POST)
//    public String processSaveStudent(@ModelAttribute("student") Student student){
//        studentService.save(student);
//        return "views/students/create";
//    }
    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public String processSaveStudent(@Valid @ModelAttribute("student") Student student,
                                     BindingResult bindingResult,
                                     RedirectAttributes redirectAttributes,
                                     Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("student", student);
            return "views/students/create";
        }
        studentService.save(student);
        redirectAttributes.addFlashAttribute("message", "The user has been saved successfully.");
        return "redirect:/admin/students/list";
    }

    @RequestMapping(path = "list", method = RequestMethod.GET)
    public String findAll(@RequestParam(value = "page", defaultValue = "1") int page,
                          @RequestParam(value = "limit", defaultValue = "10") int limit,
                          Model model) {
        model.addAttribute("studentPageable", studentService.findAll(page, limit));
        return "views/students/list";
    }

    @GetMapping("edit/{rollNumber}")
    public String showEdit(@PathVariable("rollNumber") String rollNumber,
                           RedirectAttributes redirectAttributes,
                           Model model) {
        try {
            Student student = studentService.get(rollNumber);
            model.addAttribute("student", student);
            model.addAttribute("pageTitle", "Edit User (ID: " + rollNumber + ")");
            return "views/students/edit";
        } catch (StudentNotFoundException e) {

//            redirectAttributes.addFlashAttribute("message",e.getMessage());
////            return "redirect:/admin/students/list";
            return null;
        }
    }

    //    @RequestMapping(path = "delete/{rollNumber}", method = RequestMethod.DELETE)
//    public String deleteUser(@PathVariable("rollNumber") String rollNumber,
//                             RedirectAttributes redirectAttributes) {
//        Optional<Student> student = Optional.ofNullable(studentRepository.findByRollNumber(rollNumber));
//        if (!student.isPresent()) {
//            return "admin/students/list";
//        }
//        studentService.delete(student.get().getRollNumber());
//        return "redirect:/admin/students/list";
//    }
    @RequestMapping(method = RequestMethod.DELETE, path = "delete/{rollNumber}")
    public String delete(@PathVariable String rollNumber) {
        Optional<Student> student = studentRepository.findById(rollNumber);
        if (!student.isPresent()) {
            ResponseEntity.badRequest().build();
        }
        studentService.deleteById(rollNumber);
        return "redirect:/admin/students/list";
    }
}

