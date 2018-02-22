# **Recipe library**

This library contains all classes related to a recipe, including:
* **[Presentation](Presentation.java)**
* **[Recipe](Recipe.java)**
* **[Slide](Slide.java)**
* **[Ingredient](Ingredient.java)**

==============================================
## [Presentation](presentation.java)

The presentation class is an abstract class that outlines the absolute minimum a [Recipe](Recipe.java) must have in order to comply with the PWS.

####Attributes:
* ArrayList<Slide> slides

## [Recipe](Recipe.java)
The  Recipe class contains all data relevant to the individual recipe. 
#### Attributes:
* String title
* String author
* String description
* String id
* ArrayList<Slide> slides
* ArrayList<Ingredient> ingredients

All variables that aren't an ArrayList are private, and can only be edited via setters. However, "slides" and "ingredients" are left public to take full advantage of the built-in functionality of ArrayLists 

*See [ArrayList](https://docs.oracle.com/javase/8/docs/api/java/util/ArrayList.html)*

As an example, to get the first item contained in the first slide of a Recipe, you would do:

'''javascript
Object item = recipe.slides.get(0).items.get(0);
'''

## [Slide](Slide.java)

Slides contain a single ArrayList that stores any object associated with it. This is to allow complete freedom when it comes to determining the contents of the slide.

#### Attributes:
* ArrayList<Object> items

*See [ArrayList](https://docs.oracle.com/javase/8/docs/api/java/util/ArrayList.html)*

## [Ingredient](Ingredient.java)

Ingredients simply store information about a single ingredient of a recipe, and has the functionality to extract a variety of information from them.

#### Attributes:
* String name
* String quantity

Information can be extracted using the following methods:
* String getName()
* String getQuantity()
* int getQuantityValue()
* String getQuantityUnits()




![alt text](http://i0.kym-cdn.com/photos/images/original/000/139/998/1308977754001.jpg)
	

