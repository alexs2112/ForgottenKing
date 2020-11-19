package items;

import java.util.ArrayList;

public class Inventory {
	private Item[] items;
	public Item[] items() { return items; }
	private int[] quantity;
	public int[] quantity() { return quantity; }
	
	public ArrayList<Item> getItems() {
		ArrayList<Item> list = new ArrayList<Item>();
		for (int i = 0; i < items.length; i++) {
			if (items[i] != null)
				for (int x = 0; x < quantity[i]; x++)
					list.add(items[i]);
		}
		return list;
	}
	public ArrayList<Item> getUniqueItems() { 
		ArrayList<Item> list = new ArrayList<Item>();
		for (int i = 0; i < items.length; i++) {
			if (items[i] != null)
				list.add(items[i]);
		}
		return list;
	}
    public Item get(int i) { return items[i]; }
    
    public int indexOf(Item item) {
    	for (int i = 0; i < items.length; i++) {
			if (items[i] == item)
				return i;
		}
    	return -1;
    }

	public Inventory(int max) {
		items = new Item[max];
		quantity = new int[max];
	}
	public Inventory() {
		this(20);
	}
	
	public void add(Item item){
		addMany(item, 1);
	}
	public void addMany(Item item, int amount){
		for (int i = 0; i < items.length; i++){
	        if (items[i] != null && items[i].name().equals(item.name())){
	             quantity[i] += amount;
	             return;
	        }
	    }
	    for (int i = 0; i < items.length; i++){
	        if (items[i] == null){
	             items[i] = item;
	             quantity[i] += amount;
	             return;
	        }
	    }
	}
	
	public void remove(Item item){
	    for (int i = 0; i < items.length; i++){
	        if (items[i] == item){
	        	quantity[i] -= 1;
	        	if (quantity[i] <= 0) {
	        		items[i] = null;
	        	}
	            return;
	        }
	    }
	}
//	public void removeAll(Item item){
//	    for (int i = 0; i < items.length; i++){
//	        if (items[i] == item){
//	        	quantity[i] = 0;
//	        	items[i] = null;
//	            return;
//	        }
//	    }
//	}
	
	public boolean contains(Item item) {
		for (Item i : getUniqueItems())
			if (i == item)
				return true;
		return false;
	}
	public boolean containsName(String s) {
		for (Item i : getUniqueItems())
			if (i.name().equals(s))
				return true;
		return false;
	}
	
	public boolean isFull(){
	    int size = 0;
	    for (int i = 0; i < items.length; i++){
	        if (items[i] != null)
	             size++;
	    }
	    return size == items.length;
	}
	public boolean isEmpty(){
	    for (int i = 0; i < items.length; i++){
	        if (items[i] != null)
	             return false;
	    }
	    return true;
	}
	
	public Item getFirstItem() {
		for (int i = 0; i < items.length; i++){
	        if (items[i] != null){
	             return items[i];
	        }
	    }
		return null;
	}
	
	public String listOfItems() {
		String line = "";
		for (int i = 0; i < items.length; i++)
			if (items[i] != null) {
				if (quantity[i] > 1)
					line += quantity[i] + " ";
				line += items[i].name();
				if (quantity[i] > 1) {
					if (line.contains(" of "))
						line = line.replaceAll(" of ", "s of ");
					else
						line += "s";
				}
				line += ", ";
			}
		if (line.length() <= 3)
			return "";
		return line.substring(0,line.length()-2);
	}
	
	public int numberOfItems() {
		int x = 0;
		for (int i = 0; i < items.length; i++)
			if (items[i] != null)
				x += quantity[i];
		return x;
	}
	public int quantityOf(Item item) {
		for (int i = 0; i < items.length; i++)
			if (items[i] != null && items[i] == item) {
				return quantity[i];
			}
		return 0;
	}
	public double totalWeight() {
		double x = 0;
		for (int i = 0; i < items.length; i++) {
			if (items[i] != null)
				x += items[i].weight() * quantity[i];
		}
		int scale = (int) Math.pow(10, 1);
	    return (double) Math.round(x * scale) / scale;
	}

}
