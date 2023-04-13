//Mert Can Gonen
//181101039

public abstract class AbstractItem implements ItemInterface {

	private String item; // itemi temsil eden string. K,v gibi
	private String position; // tahtadaki konumu gösterir. Örneğin, a1

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getPosition() {
		return this.position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

}
