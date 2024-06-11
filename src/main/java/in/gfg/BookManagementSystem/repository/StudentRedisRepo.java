package in.gfg.BookManagementSystem.repository;

import in.gfg.BookManagementSystem.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class StudentRedisRepo {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    private final String BOOK_KEY= "student:";


    public  void setStudentToRedis(Student student){

        setStudentToRedisByStudentName(student);
        setStudentToRedisByEmail(student);
    }

    public void setStudentToRedisByStudentName(Student student){
        redisTemplate.opsForList().leftPush(BOOK_KEY+student.getName() , student);
    }
    public  void setStudentToRedisByEmail(Student student){
        redisTemplate.opsForList().leftPush(BOOK_KEY+student.getEmail() , student);
    }

    public List<Student> getStudentByStudentName(String value) {
        return (List<Student>) (Object) redisTemplate.opsForList().range(BOOK_KEY+value, 0, -1);
    }

}
