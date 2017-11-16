package core.test;

import core.api.IAdmin;
import core.api.IInstructor;
import core.api.IStudent;
import core.api.impl.Admin;
import core.api.impl.Instructor;
import core.api.impl.Student;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestInstructor {
	private IAdmin admin;
	private IInstructor instructor;
	private IStudent student;
	
	// Done before each @Test
    @Before
    public void setup() {
        this.admin = new Admin();
        this.instructor = new Instructor();
        this.student = new Student();
        
        this.admin.createClass("Test", 2017, "Teach", 20);
    }
    
    /****************** addHomework() *****************/
    
    // Instructor can add homework to a class he teaches
    @Test
    public void testAddHW() {
    		this.instructor.addHomework("Teach", "Test", 2017, "HW");
    		assertTrue(this.instructor.homeworkExists("Test", 2017, "HW"));
    }
    
    // Instructor cannot add homework to a class he does not teach
    @Test
    public void testAddHWNotTeaching() {
    		this.instructor.addHomework("Instructor", "Test", 2017, "HW");
    		assertFalse(this.instructor.homeworkExists("Test", 2017, "HW"));
    }
    
    /****************** assignGrade() *****************/
    
    // Instructor can assign a grade
    @Test
    public void testAssignHW() {
		this.instructor.addHomework("Teach", "Test", 2017, "HW");
		this.student.registerForClass("Bob", "Test", 2017);
		this.student.submitHomework("Bob", "HW", "sol", "Test", 2017);
		this.instructor.assignGrade("Teach", "Test", 2017, "HW", "Bob", 100);
		assertTrue(this.instructor.getGrade("Test", 2017, "HW", "Bob") == 100);
    }
    
    // Instructor cannot assign a grade if they are not teaching the class
    @Test
    public void testAssignHWFakeTeacher() {
		this.instructor.addHomework("Teach", "Test", 2017, "HW");
		this.student.registerForClass("Bob", "Test", 2017);
		this.student.submitHomework("Bob", "HW", "sol", "Test", 2017);
		this.instructor.assignGrade("FakeTeach", "Test", 2017, "HW", "Bob", 100);
		assertFalse(this.instructor.getGrade("Test", 2017, "HW", "Bob") == 100);
    }
    
    // Instructor cannot assign a grade if the homework has not been assigned
    @Test
    public void testAssignNoHW() {
		this.instructor.addHomework("Teach", "Test", 2017, "HW");
		this.student.registerForClass("Bob", "Test", 2017);
		this.student.submitHomework("Bob", "HW", "sol", "Test", 2017);
		
		this.instructor.assignGrade("Teach", "Test", 2017, "NoHW", "Bob", 100);
		assertFalse(this.instructor.getGrade("Test", 2017, "NoHW", "Bob") == 100);
    }
    
    // Instructor cannot assign a grade if the student has not submitted their homework
    @Test
    public void testAssignStudentFail() {
		this.instructor.addHomework("Teach", "Test", 2017, "HW");
		this.student.registerForClass("Bob", "Test", 2017);
		
		this.instructor.assignGrade("Teach", "Test", 2017, "HW", "Bob", 100);
		assertFalse(this.instructor.getGrade("Test", 2017, "HW", "Bob") == 100);
    }
    
}
