//Mert Can Gonen
//181101039

public abstract class AbstractBoard implements BoardInterface {

	Item[] items;

	abstract void placeItem(Item item);

	public Item[] getItems() {
		return items;
	}

	public void setItems(Item[] items) {
		this.items = items;
	}

}
