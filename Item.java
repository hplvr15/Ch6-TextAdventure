import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Write a description of class Item here.
 * 
 * @author Hannah Dela Cruz
 * @version April 20, 2016
 */
public class Item
{
    // instance variables - replace the example below with your own
    private String itemDescription;
    private HashMap<String, Item> items;

    /**
     * Constructor for objects of class Item
     */
    
    public Item(String itemDescription)
    {
        this.itemDescription = itemDescription;
        items = new HashMap<String, Item>();
    }
    
    /**
    
    */
    
    public void setItem(String itemDescription, Item name)
    {
        items.put(itemDescription, name);
    }
    
    /**
     * @return The short description of the item
     * (the one that was defined in the constructor).
     */
    
    public String getShortItemDescription()
    {
        return itemDescription;
    }
    
     /**
     * Return a description of the item in the form:
     *    Item: 
     * @return A long description of the item
     */
    
    public String getLongItemDescription()
    {
        return "Item: " + itemDescription;
    }
   
    private String getItemString()
    {
        String returnString = "Items";
        Set<String> keys = items.keySet();
        for(String item : keys) 
        {
            returnString += " " + item;
        }
        return returnString;
    }
    
    public Item getItem(String itemDescription) 
    {
        return items.get(itemDescription);
    }
    
}
