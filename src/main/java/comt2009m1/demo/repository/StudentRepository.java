package comt2009m1.demo.repository;

import comt2009m1.demo.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, String>  {
    Student findByRollNumber(String rollNumber);
    void deleteByRollNumber(String rollNumber);
}
