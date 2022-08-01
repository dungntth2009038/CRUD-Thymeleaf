package comt2009m1.demo.service;

import com.sun.org.apache.bcel.internal.generic.RETURN;
import comt2009m1.demo.entity.Student;
import comt2009m1.demo.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentService {
    final StudentRepository studentRepository;

    public Student save(Student student) {
        return studentRepository.save(student);
    }

    public Page<Student> findAll(int page, int limit) {
        return studentRepository.findAll(PageRequest.of(page - 1, limit, Sort.by(Sort.Direction.ASC, "rollNumber")));
    }

    public Student get(String rollNumber) throws StudentNotFoundException {
        Optional<Student> result = studentRepository.findById(rollNumber);
        if (result.isPresent()) {
            return result.get();
        }
        throw new StudentNotFoundException("Could not find any user with ID" + rollNumber);
//        return result.get();
    }
//    public void delete(String rollNumber){
//        studentRepository.deleteByRollNumber(rollNumber);
////        studentRepository.deleteById("gbdghdg");
//    }
    public void deleteById(String id) {
        studentRepository.deleteById(id);
    }
}
