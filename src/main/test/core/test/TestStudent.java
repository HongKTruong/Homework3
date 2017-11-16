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

public class TestStudent {
	private IAdmin admin;
	private IInstructor instructor;
	private IStudent student;
	
	// Create an instance of Admin before every @Test
    @Before
    public void setup() {
        this.admin = new Admin();
        this.instructor = new Instructor();
        this.student = new Student();
        
        this.admin.createClass("Test", 2017, "Teach", 10);
        this.admin.createClass("Small", 2017, "Teach", 1);
        this.admin.createClass("Later", 2018, "Teach", 10);
        
        this.instructor.addHomework("Teach", "Test", 2017, "HW");
        this.instructor.addHomework("Teach", "Test", 2018, "HW");
    }
    
    /****************** registerForClass() *****************/
	
    // Student can register for a class that has not met its enrollment capacity
    @Test
	public void testRegister() {
		this.student.registerForClass("Bob", "Test", 2017);
		assertTrue(this.student.isRegisteredFor("Bob", "Test", 2017));
	}
    
    // Student cannot register for a class that has met its enrollment capacity
    @Test
	public void testRegisterSmall() {
		this.student.registerForClass("Bob", "Small", 2017);
		this.student.registerForClass("Sam", "Small", 2017);
		assertFalse(this.student.isRegisteredFor("Sam", "Small", 2017));
	}
    
    /****************** dropClass() *****************/
    
    // Student can drop a class
    @Test
	public void testDrop() {
		this.student.registerForClass("Bob", "Test", 2017);
		this.student.dropClass("Bob", "Test", 2017);
		assertFalse(this.student.isRegisteredFor("Bob", "Test", 2017));
	}
    
    /****************** submitHomework() *****************/
    
    // Student can submit homework
    @Test
	public void testSubmit() {
		this.student.registerForClass("Bob", "Test", 2017);
		this.student.submitHomework("Bob", "HW", "sol", "Test", 2017);
		assertTrue(this.student.hasSubmitted("Bob", "HW", "Test", 2017));
	}
    
    // Student cannot submit homework if it has not been assigned
    @Test
	public void testSubmitNoHW() {
		this.student.registerForClass("Bob", "Test", 2017);
		this.student.submitHomework("Bob", "Fake", "sol", "Test", 2017);
		assertFalse(this.student.hasSubmitted("Bob", "Fake", "Test", 2017));
	}
    
    // Student cannot submit homework if it's not the current year
    @Test
	public void testSubmitCurrentYear() {
    		this.student.registerForClass("Bob", "Test", 2018);
		this.student.submitHomework("Bob", "HW", "sol", "Test", 2018);
		assertFalse(this.student.hasSubmitted("Bob", "HW", "Test", 2018));
	}
}
