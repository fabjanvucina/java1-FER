package hr.fer.oprpp1.math;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Vector2DTest {
	
    @Test
    public void testAdd() {
    	Vector2D vector = new Vector2D(1, 0);
        vector.add(new Vector2D(0, 1));

        assertEquals(new Vector2D(1, 1), vector);
    }
    
    @Test
    public void testAdded() {
    	Vector2D vector = new Vector2D(1, 0);
    	Vector2D newVector = vector.added(new Vector2D(0, 1));

        assertEquals(new Vector2D(1, 1), newVector);
    }
    
    @Test
    public void testRotate() {
    	Vector2D vector = new Vector2D(1, 0);
        vector.rotate(Math.PI / 2);
        
        assertTrue(vector.getX() < 1E-8);
        assertTrue(vector.getY() ==  1.0);
    }
    
    @Test
    public void testRotated() {
    	Vector2D vector = new Vector2D(1, 0);
    	Vector2D newVector = vector.rotated(Math.PI / 2);
        
        assertTrue(newVector.getX() < 1E-8);
        assertTrue(newVector.getY() ==  1.0);
    }
    
    @Test
    public void testScale() {
    	Vector2D vector = new Vector2D(1, 0);
        vector.scale(4);

        assertEquals(new Vector2D(4, 0), vector);
    }
    
    @Test
    public void testScaled() {
    	Vector2D vector = new Vector2D(1, 0);
    	Vector2D newVector = vector.scaled(4);

        assertEquals(new Vector2D(4, 0), newVector);
    }
    
    @Test
    public void testCopy() {
    	Vector2D vector = new Vector2D(1, 0);

        assertEquals(vector.copy(), vector);
    }
    
}
