package com.group3.swengandroidapp.ShoppingList;

public class listItem
{
    private String itemName;
    private String itemUnits;
    private String itemQuantity;
    private boolean itemChecked;

    listItem(String name, String quantity, String units)
    {
        itemName = name;
        itemUnits = units;
        itemQuantity = quantity;
        //itemChecked = checked;
    }

    //Getters for the object.
    public String getName() {
        return itemName;
    }

    public String getQuantity() {
        return itemQuantity;
    }
    public String getUnits() {
        return itemUnits;
    }

    public boolean getCheckedState() {
        return itemChecked;
    }

    //Setters for the object.
    public void setName(String newName){
        itemName = newName;
    }

    public void setQuantity(String newQuantity){
        itemQuantity = newQuantity;
    }

    public void setUnits(String newUnits){
        itemUnits = newUnits;
    }

    public void setCheckedState(boolean newState){
        itemChecked = newState;
    }
}
