//import javafx.animation.*;
//import javafx.event.EventHandler;
//import javafx.scene.paint.Color;
//import javafx.scene.shape.*;
//import javafx.scene.paint.LinearGradient;
//import javafx.util.Duration;
//
///* -- The following code has been produced as intended in the contracts between SImpLe and the buyer.
//    If you have any trouble with the code, feel free to contact Amy Jo Turner (ajt562@york.ac.uk) for assistance. -- */
//public abstract class GraphicModule {
//
//
//    //The following are full graphics with full capabilities, if color0 and color1 are set to the same, the color will be solid, otherwise gradient.
//
//    public static Line line(double x0, double y0, double x1, double y1, Color color, double time) {
//        Line line = new Line(); //Creates a line segment in coordinate space.
//        line.setStartX(x0); //the xy coordinates of the starting point.
//        line.setStartY(y0);
//        line.setEndX(x1); //the xy coordinates of the ending point.
//        line.setEndY(y1);
//        line.setStroke(color);
//        Timeline timeline = new Timeline(); //makes line disappear after time seconds.
//        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(time),
//                (EventHandler) event -> line.setVisible(false)));
//        timeline.play();
//        return line;
//    }
//
//    public static Circle circle(double x, double y, double radius, Color outline, Color color0, Color color1, double time) {
//        Circle circle = new Circle(); //Creates a circle shape with specified radius and center location in pixels.
//        circle.setCenterX(x);
//        circle.setCenterY(y);
//        circle.setRadius(radius);
//        circle.setStroke(outline);
//        LinearGradient grad = LinearGradient.valueOf("linear-gradient(from 50% 0% to 50% 100%, " + color0.toString() + " 0%, " + color1.toString() + " 100%)");
//        circle.setFill(grad);
//        Timeline timeline = new Timeline(); //makes shape disappear after time seconds.
//        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(time),
//                (EventHandler) event -> circle.setVisible(false)));
//        timeline.play();
//        return circle;
//    }
//
//    public static Rectangle rectangle(double x, double y, double w, double h, double aw, double ah, Color outline, Color color0, Color color1, double time) {
//        Rectangle rectangle = new Rectangle(); //Creates a rectangle with specified location and size.
//        rectangle.setX1(x);
//        rectangle.setY1(y);
//        rectangle.setWidth(w);
//        rectangle.setHeight(h);
//        rectangle.setArcWidth(aw); // arc width and height are used to specify rounded corners.
//        rectangle.setArcHeight(ah);
//        rectangle.setStroke(outline);
//        LinearGradient grad = LinearGradient.valueOf("linear-gradient(from 50% 0% to 50% 100%, " + color0.toString() + " 0%, " + color1.toString() + " 100%)");
//        rectangle.setFill(grad);
//        Timeline timeline = new Timeline(); //makes shape disappear after time seconds.
//        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(time),
//                (EventHandler) event -> rectangle.setVisible(false)));
//        timeline.play();
//        return rectangle;
//    }
//
//    // The following are simplified methods assigned with defaults.
//
//    public static Circle circle(double x, double y, double radius, Color color) {
//        Circle circle = new Circle(); //Creates a circle shape with specified radius and center location in pixels.
//        circle.setCenterX(x);
//        circle.setCenterY(y);
//        circle.setRadius(radius);
//        circle.setFill(color);
//        circle.setStroke(color);
//        return circle;
//    }
//
//    public static Rectangle rectangle(double x, double y, double w, double h, Color color) {
//        Rectangle rectangle = new Rectangle(); //Creates a rectangle with specified location and size.
//        rectangle.setX1(x);
//        rectangle.setY1(y);
//        rectangle.setWidth(w);
//        rectangle.setHeight(h);
//        rectangle.setArcWidth(0); // arc width and height are used to specify rounded corners.
//        rectangle.setArcHeight(0);
//        rectangle.setFill(color);
//        rectangle.setStroke(color);
//        return rectangle;
//
//    }
//
//    public static Line line(double x0, double y0, double x1, double y1, Color color) {
//
//        Line line = new Line(); //Creates a line segment in coordinate space.
//        line.setStartX(x0); //the xy coordinates of the starting point.
//        line.setStartY(y0);
//        line.setEndX(x1); //the xy coordinates of the ending point.
//        line.setEndY(y1);
//        line.setStroke(color);
//        return line;
//
//    }
//}