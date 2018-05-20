# SImpLeGraphicsModule
In order to add this module, place GraphicModule.java into your directory.

# What's Included.
The module includes the manipulation of graphics, including; lines, circles & rectangles. These shapes can have a gradient applied to them, solid colour, as well as their size and position being manipulated as wished. Another feature is the duration - any object can have a time element added to it, meaning that the object will disappear after the given time.

# How to Use.
We believe the module is mainly self explanitory, but with the following we hope to provide a brief insight into how to use the code.
1) Rectangle

```java
rectangle(double x, double y, double w, double h, double aw, double ah, Color outline, Color color0, Color color1, double time);
```
'x' and 'y' are used to position the rectangle, while 'w' and 'h', change the width and height respectively. However, 'aw' and 'ah can be either ignored, as the default is set to 0, or manipulated in order to give the shape curved edges. Otherwise, ignoring them will give sharp 90 degree edges as expected.
'color0' and 'color1' can be set such that they are different; this will create a rather picturesque gradient design, forming two colours changing from top to bottom. These can be set to the same colour otherwise, and the shape will be block coloured. The outline can be also changed here, meaning that it is possible to make shapes stand out better. Moreover, if only one colour is selected, the shape will be of solid colour, with a matching outline.
'time', in milliseconds determines how long the object will be present for, for example, setting the time to 3000 would produce a rectangle which would become invisible after 3 seconds.

2) Circle
```java
circle(double x, double y, double radius, Color outline, Color color0, Color color1, double time)
```
Not overly dissimilar to the rectangle, the 'x' and 'y' here define the center of the circle, and 'radius' of course defines the circle's radius. The other values can be seen as being identical to those above.

3) Line
```java
line(double x0, double y0, double x1, double y1, Color color, double time) 
```
'x0' and 'y0' in this case determine the starting points of the line, while 'x1' and 'y1' determine the finishing point. Once again the other variables are identical to those seen previously. There is only one colour in this case, as a line does not necessarily need anything but the one colour.

# Notes
- Not setting a duration will make the graphic stay indefinitely.
- Setting one colour will create a solid shape.
- While the module has been tried and tested- if any problems are found with the code module, please liase with the creator, Amy Jo Turner (ajt562@york.ac.uk), or speak with any of the team over at SImpLe.

Many thanks for your purchase, we hope it is satisfactory to your needs!


