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

public class TestAdmin {
	private IAdmin admin;
	private IInstructor instructor;
	private IStudent student;
	
	// Create an instance of Admin before every @Test
    @Before
    public void setup() {
        this.admin = new Admin();
        this.instructor = new Instructor();
        this.student = new Student();
    }
    
    /****************** createClass() *****************/
       
    // Class with current year (2017) and positive size should exist
    @Test
    public void testCreateClassNormal() {
    		this.admin.createClass("Test", 2017, "Instructor", 10);
    		assertTrue(this.admin.classExists("Test", 2017));
    }
    
    // Class of size 0 should not exist
    @Test
    public void testCreateEmptyClass() {
    		this.admin.createClass("Test", 2017, "Instructor", 0);
    		assertFalse(this.admin.classExists("Test", 2017));
    }
    
    // Class with negative size should not exist
    @Test
    public void testCreateNegativeClass() {
    		this.admin.createClass("Test", 2017, "Instructor", -10);
    		assertFalse(this.admin.classExists("Test", 2017));
    }
    
    /*** Year parameter ***/
    // Class with pre-2017 years should not exist
    @Test
    public void testCreateClassPreviousYears() {
    		this.admin.createClass("Test", 2000, "Instructor", 10);
    		assertFalse(this.admin.classExists("Test", 2000));
    }
    
    // Class with post-2017 year should exist
    @Test
    public void testCreateClassFutureYears() {
    		this.admin.createClass("Test", 2030, "Instructor", 10);
    		assertTrue(this.admin.classExists("Test", 2030));
    }
    
    /*** Unique className/year pair ***/
    // Classes with the same year but different names should exist
    @Test
    public void testCreateClassUniqueName() {
    		this.admin.createClass("Test1", 2017, "Instructor1", 10);
    		this.admin.createClass("Test2", 2017, "Instructor2", 10);
    		assertTrue(this.admin.classExists("Test1", 2017));
    		assertTrue(this.admin.classExists("Test2", 2017));
    }
    
    // Classes with the same name but different years should exist
    @Test
    public void testCreateClassUniqueYear() {
    		this.admin.createClass("Test", 2018, "Instructor1", 10);
    		this.admin.createClass("Test", 2017, "Instructor2", 10);
    		assertTrue(this.admin.classExists("Test", 2018));
    		assertTrue(this.admin.classExists("Test", 2017));
    }
    
    // New class cannot have same name AND year as an existing class
    @Test
    public void testCreateClassUniqueBoth() {
    		this.admin.createClass("Test", 2017, "Instructor1", 10);
    		this.admin.createClass("Test", 2017, "Instructor2", 10);
    		assertTrue(this.admin.getClassInstructor("Test", 2017) == "Instructor1");
    }
    
    /*** Instructor Assignment ***/
    // In one year, two classes can have the same instructor
    @Test
    public void testCreateClassInstructorTwice() {
    		this.admin.createClass("Test1", 2017, "Instructor", 10);
    		this.admin.createClass("Test2", 2017, "Instructor", 10);
    		assertTrue(this.admin.classExists("Test1", 2017));
    		assertTrue(this.admin.classExists("Test2", 2017));
    }
    
    // In one year, more than two classes cannot have the same instructor
    @Test
    public void testCreateClassInstructorThrice() {
    		this.admin.createClass("Test1", 2017, "Instructor", 10);
    		this.admin.createClass("Test2", 2017, "Instructor", 10);
    		this.admin.createClass("Test3", 2017, "Instructor", 10);
    		assertFalse(this.admin.classExists("Test3", 2017));
    }
    
    // Instructor can teacher more than two classes of different years
    @Test
    public void testCreateClassInstructorYears() {
    		this.admin.createClass("Test1", 2017, "Instructor", 10);
    		this.admin.createClass("Test2", 2018, "Instructor", 10);
    		this.admin.createClass("Test3", 2019, "Instructor", 10);
    		assertTrue(this.admin.classExists("Test3", 2017));
    }
    
    /****************** changeCapacity() *****************/

    // Can change capacity of class to greater than the current capacity
    public void testCreateClassCapacityIncrease() {
		this.admin.createClass("Test", 2017, "Instructor", 10);
		this.admin.changeCapacity("Test", 2017, 20);
		assertEquals(this.admin.getClassCapacity("Test", 2017), 20);
    }
    
    // Cannot change capacity of class to less than number of registered students
    public void testCreateClassCapacityDecrease() {
		this.admin.createClass("Test", 2017, "Instructor", 2);
		this.student.registerForClass("Bob", "Test", 2017);
		this.student.registerForClass("Sam", "Test", 2017);
		
		this.admin.changeCapacity("Test", 2017, 1);
		assertFalse(this.admin.getClassCapacity("Test", 2017) == 1);
    }
    
    
}