package guidemon.model.stats;

public enum DragCoefficientShape {
    SPHERE(0.47),
    HALF_SPHERE(0.42),
    CONE(0.50),
    CUBE(1.05),
    ANGLED_CUBE(0.80),
    LONG_CYLINDER(0.82),
    SHORT_CYLINDER(1.15), 
    STREAMLINED_BODY(0.04),
    STREAMLINED_HALF_BODY(0.09); 

    private double dragCoefficient; 

    DragCoefficientShape(double dragCoefficient) { 
        this.dragCoefficient = dragCoefficient; 
    }

    public double getDragCoefficienct() { 
        return this.dragCoefficient; 
    }
}