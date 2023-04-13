//Mert Can Gonen
//181101039

import java.util.ArrayList;
import java.util.Arrays;

public class Board extends AbstractBoard {

	private String[][] itemPositions; // 10x9 array to store items according to their positions on board
	private String boardSituation; // string variable that holds actual situation of items on board before print
	private ArrayList<String> boardLines; // we used it to save board situation lines when we read from any file

	// initialization of items
	public Board() {
		this.setItems(items);
		this.itemPositions = new String[10][9];
		boardSituation = "";
		boardLines = new ArrayList<>();
		this.initialItemsCreate();
		this.setItemPositionsArrayPerItem();
	}

	public String[][] getItemPositions() {
		return this.itemPositions;
	}

	public void setItemPositions(String[][] positions) {
		this.itemPositions = positions;
	}

	public String getBoardSituation() {
		return this.boardSituation;
	}

	public void setBoardSituation(String situation) {
		this.boardSituation = situation;
	}

	public ArrayList<String> getBoardLines() {
		return this.boardLines;
	}

	public void setBoardLines(ArrayList<String> lines) {
		this.boardLines = lines;
	}

	// Done
	public void initialItemsCreate() {
		Item[] items = new Item[32];
		// red items
		items[0] = new Item("K", "j1");
		items[1] = new Item("A", "j2");
		items[2] = new Item("F", "j3");
		items[3] = new Item("V", "j4");
		items[4] = new Item("Ş", "j5");
		items[5] = new Item("V", "j6");
		items[6] = new Item("F", "j7");
		items[7] = new Item("A", "j8");
		items[8] = new Item("K", "j9");
		items[9] = new Item("T", "h2");
		items[10] = new Item("T", "h8");
		items[11] = new Item("P", "g1");
		items[12] = new Item("P", "g3");
		items[13] = new Item("P", "g5");
		items[14] = new Item("P", "g7");
		items[15] = new Item("P", "g9");
		// white items
		items[16] = new Item("k", "a1");
		items[17] = new Item("a", "a2");
		items[18] = new Item("f", "a3");
		items[19] = new Item("v", "a4");
		items[20] = new Item("ş", "a5");
		items[21] = new Item("v", "a6");
		items[22] = new Item("f", "a7");
		items[23] = new Item("a", "a8");
		items[24] = new Item("k", "a9");
		items[25] = new Item("t", "c2");
		items[26] = new Item("t", "c8");
		items[27] = new Item("p", "d1");
		items[28] = new Item("p", "d3");
		items[29] = new Item("p", "d5");
		items[30] = new Item("p", "d7");
		items[31] = new Item("p", "d9");
		this.setItems(items);
	}

	/*
	 * We use it to create and fill item positions array.
	 */
	public void setItemPositionsArrayPerItem() {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 9; j++) {
				this.itemPositions[i][j] = "-";
			}
		}
		for (int i = 0; i < this.getItems().length; i++) {
			if (!this.getItems()[i].getPosition().equals("XX")) {
				this.placeItem(this.getItems()[i]);
			}
		}
		this.createBoardSituation();
	}

	/*
	 * We use it to load items array and board situation from text/binary file.
	 */
	public void setItemPositionsFromBoardSituation(ArrayList<String> lines) {
		for (int i = 0; i < this.items.length; i++) { // reset items positions in items array
			items[i].setPosition("XX");
		}
		for (int i = 0; i < this.itemPositions.length; i++) { // reset item positions on string array
			for (int j = 0; j < this.itemPositions[0].length; j++) {
				this.itemPositions[i][j] = "-";
			}
		}
		ArrayList<Character> itemChars = new ArrayList<>(
				Arrays.asList('K', 'A', 'F', 'V', 'Ş', 'T', 'P', 'k', 'a', 'f', 'v', 'ş', 't', 'p'));
		char vertical = 'j';
		for (int i = 0; i < lines.size(); i += 2) {
			String tmp = lines.get(i);
			tmp = tmp.replace("\t", "");
			for (int j = 1; j < tmp.length(); j += 3) {
				if (itemChars.contains(tmp.charAt(j))) {
					int v = i / 2;
					int h = (j + 2) / 3;
					String item = "" + tmp.charAt(j);
					this.itemPositions[v][h - 1] = item; // itemPositions string is updated
					String position = vertical + "" + h;
					for (int k = 0; k < this.items.length; k++) {
						if (this.items[k].getItem().equals(tmp.charAt(j) + "")
								&& this.items[k].getPosition().equals("XX")) {
							items[k] = new Item(item, position);
							break;
						}
					}
				}
			}
			vertical--;
		}
	}

	/*
	 * We use it to place item on item positions array.
	 */
	public void placeItem(Item item) {
		String position = item.getPosition();
		int horizontal = Integer.valueOf(position.charAt(1) - '0') - 1;
		char verticalChar = position.charAt(0);
		int vertical = 0;
		switch (verticalChar) {
			case 'j':
				vertical = 0;
				break;
			case 'i':
				vertical = 1;
				break;
			case 'h':
				vertical = 2;
				break;
			case 'g':
				vertical = 3;
				break;
			case 'f':
				vertical = 4;
				break;
			case 'e':
				vertical = 5;
				break;
			case 'd':
				vertical = 6;
				break;
			case 'c':
				vertical = 7;
				break;
			case 'b':
				vertical = 8;
				break;
			case 'a':
				vertical = 9;
				break;
			default:
				break;
		}
		this.itemPositions[vertical][horizontal] = item.getItem();
	}

	/*
	 * We use it to create board's actual situatiın to print.
	 */
	public void createBoardSituation() {
		this.boardSituation = "";
		char row = 'j';
		for (int i = 0; i < 10; i++) {
			boardSituation += row + "\t";
			row--;
			for (int j = 0; j < 9; j++) {
				if (j != 8) {
					boardSituation += this.itemPositions[i][j] + "--";
				} else {
					boardSituation += this.itemPositions[i][j] + "\n";
				}
			}
			if (i == 0) {
				boardSituation += " \t|  |  |  |\\ | /|  |  |  |\n";
			} else if (i == 1) {
				boardSituation += " \t|  |  |  |/ | \\|  |  |  |\n";
			} else if (i == 4) {
				boardSituation += " \t|                       |\n";
			} else if (i == 7) {
				boardSituation += " \t|  |  |  |/ | \\|  |  |  |\n";
			} else if (i == 8) {
				boardSituation += " \t|  |  |  |\\ | /|  |  |  |\n";
			} else if (i == 2 || i == 3 || i == 5 || i == 6) {
				boardSituation += " \t|  |  |  |  |  |  |  |  |\n";
			}
		}
		boardSituation += "\n \t1--2--3--4--5--6--7--8--9";
	}

	// Done
	public void print() {
		System.out.println(this.boardSituation);
	}

	public void printItemPositions() {
		for (int i = 0; i < 32; i++) {
			System.out.println(this.items[i].getItem() + " " + this.items[i].getPosition());
		}
	}

}