import java.util.LinkedList;
import java.util.Random;
public class DijkstrasBackendFD implements ICMDijkstrasBackend{
	LinkedList<String> current = new LinkedList<String>();
	int point = 0;
	int count = 0;
	@Override
	public boolean checkSubmission(String start, String end) {
		String[] correct;
		if(start.equals("D") && end.equals("L")) {
			correct = new String[] {"D","H","L"};
			if(current.size() != correct.length) {
				point--;
				return false;
			}
			for(int i = 0; i < correct.length; i++) {
				if(!current.removeFirst().equals(correct[i])) {
					point--;
					return false;
				}
			}
			point++;
			return true;
		}
		if(start.equals("A") && end.equals("B")) {
			correct = new String[] {"A","B"};
			if(current.size() != correct.length) {
				point--;
				return false;
			}
			for(int i = 0; i < correct.length; i++) {
				if(!current.removeFirst().equals(correct[i])) {
					point--;
					return false;
				}
			}
			point++;
			return true;
		}
		if(start.equals("A") && end.equals("H")) {
			correct = new String[] {"A","B","C","G","H"};
			if(current.size() != correct.length) {
				point--;
				return false;
			}
			for(int i = 0; i < correct.length; i++) {
				if(!current.removeFirst().equals(correct[i])) {
					point--;
					return false;
				}
			}
			point++;
			return true;
		}
		if(start.equals("E") && end.equals("L")) {
			correct = new String[] {"E","F","G","K","L"};
			if(current.size() != correct.length) {
				point--;
				return false;
			}
			for(int i = 0; i < correct.length; i++) {
				if(!current.removeFirst().equals(correct[i])) {
					point--;
					return false;
				}
			}
			point++;
			return true;
		}
		if(start.equals("I") && end.equals("C")) {
			correct = new String[] {"I","E","A","B","C"};
			if(current.size() != correct.length) {
				point--;
				return false;
			}
			for(int i = 0; i < correct.length; i++) {
				if(!current.removeFirst().equals(correct[i])) {
					point--;
					return false;
				}
			}
			point++;
			return true;
		}
		return true;
	}

	@Override
	public int getPoints() {
		// TODO Auto-generated method stub
		return this.point;
	}

	@Override
	public String[] newPuzzle() {
		String[] puzzle = null;
		count++;
		switch(count % 5) {
			case 0:
				puzzle = new String[] {"D", "L"};
				break;
			case 1:
				puzzle = new String[] {"A", "B"};
				break;
			case 2:
				puzzle = new String[] {"A", "H"};
				break;
			case 3:
				puzzle = new String[] {"E", "L"};
				break;
			case 4:
				puzzle = new String[] {"I", "C"};
				break;
		}
		return puzzle;
	}

	@Override
	public boolean addCurrent(String next) {
		if(current.isEmpty()) {
			this.current.add(next);
			return true;
		}
		String[][] mapping = new String[][] {
			new String[] {"A","BE"},
			new String[] {"B","ACF"},
			new String[] {"C","BGD"},
			new String[] {"D","CH"},
			new String[] {"E","AFI"},
			new String[] {"F","EBGJ"},
			new String[] {"G","FCHK"},
			new String[] {"H","DGL"},
			new String[] {"I","EJ"},
			new String[] {"J","IFK"},
			new String[] {"K","JGL"},
			new String[] {"L","HK"},
		};
		for(int i = 0; i < mapping.length; i++) {
			if(mapping[i][0].equals(current.peekLast())) {
				if(mapping[i][1].contains(next)) {
					current.add(next);
					return true;
				}else {
					return false;
				}
			}
		}
		return false;
		
	}

	@Override
	public void reset() {
		current = new LinkedList<String>();
		// TODO Auto-generated method stub
		
	}

	@Override
	public LinkedList<String> giveUp(String start, String end) {
		String[] correct;
		LinkedList<String> returnCorrect = new LinkedList<String>();
		current = new LinkedList<String>();
		if(start.equals("D") && end.equals("L")) {
			correct = new String[] {"D","H","L"};
			for(int i = 0; i < correct.length; i++) {
				returnCorrect.add(correct[i]);
			}
			return returnCorrect;
		}
		if(start.equals("A") && end.equals("B")) {
			correct = new String[] {"A","B"};
			for(int i = 0; i < correct.length; i++) {
				returnCorrect.add(correct[i]);
			}
			return returnCorrect;
		}
		if(start.equals("A") && end.equals("H")) {
			correct = new String[] {"A","B","C","G","H"};
			for(int i = 0; i < correct.length; i++) {
				returnCorrect.add(correct[i]);
			}
			return returnCorrect;
		}
		if(start.equals("E") && end.equals("L")) {
			correct = new String[] {"E","F","G","K","L"};
			for(int i = 0; i < correct.length; i++) {
				returnCorrect.add(correct[i]);
			}
			return returnCorrect;
		}
		if(start.equals("I") && end.equals("C")) {
			correct = new String[] {"I","E","A","B","C"};
			for(int i = 0; i < correct.length; i++) {
				returnCorrect.add(correct[i]);
			}
			return returnCorrect;
		}
		return null;
	}

	@Override
    public String[][] gridMatrixForFD(){
    	String[][] display = new String[][] {
			new String[] {"A","1","B","9","C","12","D"},
			new String[] {"3","8","11","20"},
			new String[] {"E","6","F","16","G","7","H"},
			new String[] {"15","17","2","5"},
			new String[] {"I","22","J","18","K","4","L"}
		};
		return display;
    }

	@Override
	public boolean unclick(String vertex) {
		if(current.peekLast().equals(vertex)) {
			current.removeLast();
			return true;
		}
		return false;
	}

	@Override
	public String hint(String start, String end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[][] cheatSheet(String start) {
		// TODO Auto-generated method stub
		return null;
	}

}
