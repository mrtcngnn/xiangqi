//Mert Can Gonen
//181101039

public class Player {

	public String name; // oyuncu ismi
	public float puan; // her taş yedikçe oyuncunun puanı taşın puanına göre artar.

	public Player(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getPuan() {
		return this.puan;
	}

	public void setPuan(float puan) {
		this.puan = puan;
	}

}
