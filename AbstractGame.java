//Mert Can Gonen
//181101039

public abstract class AbstractGame {

	Board board;
	Player white;
	Player red;

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public Player getWhite() {
		return white;
	}

	public void setWhite(Player white) {
		this.white = white;
	}

	public Player getRed() {
		return red;
	}

	public void setRed(Player red) {
		this.red = red;
	}

	abstract void play(String from, String to);

	abstract void save_binary(String address);

	abstract void save_text(String address);

	abstract void load_text(String address);

	abstract void load_binary(String address);

}
