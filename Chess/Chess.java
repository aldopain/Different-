import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Chess {
	ArrayList<Figure> fBlack = new ArrayList<Figure>();
	ArrayList<Figure> fWhite = new ArrayList<Figure>();
	ArrayList<Integer> black = new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16));
	ArrayList<Integer> white = new ArrayList<Integer>(Arrays.asList(17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32));
	int[][] desk = {{1, 2, 3, 4, 5, 6, 7, 8},
			{9, 10, 11, 12, 13, 14, 15, 16},
			{0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0},
			{17, 18, 19, 20, 21, 22, 23, 24},
			{25, 26, 27, 28, 29, 30, 31, 32}};
	
////////////////////////////////////
	class Figure{
		int heigth;
		int weigth;
		String name;
		int index;
		
		void out(){
			System.out.println("Name is " + name);
			System.out.println("Index = " + index);
			System.out.println("Heigth = " + heigth);
			System.out.println("Weigth = " + weigth);
		}
		
		void getPositionByStartIndex(){
			int f = 1;
			int i, j = -1;
			for(i = 0; i < 8 && f != 0; i++)
				for(j = 0; j < 8 && f != 0; j++){
					if(desk[i][j] == index)
						f = 0;
				}
			heigth = --i;
			weigth = --j;
		}
		
		//0 - пусто, 1 - фигура противника, 2 - своя фигура
		//я не ебу, работает это так, но с пешками прокнуло
		int checkPosition(int h, int w){
			int c = desk[h][w];
			if(isPositionEmpty(h,w) == 1)
				return 0;
			if(index > 0 && index < 17){
				if(c > 0 && c < 17){
					return 2;
				}else{
					return 1;
				}
			}else{
				if(c > 0 && c < 17)
					return 1;
				else
					return 2;
			}
		}
		
		ArrayList<Integer[]> getRookPossiblePositions(){
			ArrayList<Integer[]> result = new ArrayList<Integer[]>();
			int f = 0;
			for(int i = heigth + 1; i < 8 && f == 0; i++){
				if((f = this.checkPosition(i , weigth)) != 2){
					Integer[] toAdd = {i , weigth};
					result.add(toAdd);
				}
				System.out.println(i);
			}f = 0;
			for(int i = heigth -1; i > -1 && f == 0; i--){
				if((f = this.checkPosition(i , weigth)) != 2){
					Integer[] toAdd = {i , weigth};
					result.add(toAdd);
				}
				System.out.println(i);
			}f = 0;
			for(int i = weigth + 1; i < 8 && f == 0; i++){
				if((f = checkPosition(heigth , i)) != 2){
					Integer[] toAdd = {heigth , i};
					result.add(toAdd);
				}
				System.out.println(i);
			}f = 0;
			for(int i = weigth - 1; i > -1 && f == 0; i--){
				if((f = this.checkPosition(heigth , i)) != 2){
					Integer[] toAdd = {heigth , i};
					result.add(toAdd);
				}
				System.out.println(i);
			}
			return result;
		}
		
		ArrayList<Integer[]> getPossiblePositions(){
			int heigthIterator;
			ArrayList<Integer[]> result = new ArrayList<Integer[]>();
			switch(name){
			case "pawn":
				if(index < 17 && index > 0)
					heigthIterator = 1;
				else
					heigthIterator = -1;
				if(isPositionEmpty(heigth + heigthIterator, weigth) == 1){
					Integer[] toAdd = {heigth + heigthIterator, weigth};
					result.add(toAdd);
				}
				if(weigth < 7)
					if(this.checkPosition(heigth + heigthIterator, weigth + 1) == 1){
						Integer[] toAdd = {heigth + heigthIterator, weigth + 1};
						result.add(toAdd);
				}
				if(weigth > 0)
					if(this.checkPosition(heigth + heigthIterator, weigth - 1) == 1){
						Integer[] toAdd = {heigth + heigthIterator, weigth - 1};
						result.add(toAdd);
				}
				break;
			case "bishop":	//слон
				
				break;
			case "queen":	//ферзь
				
				break;
			case "king":	//король
				
				break;
			case "rook":	//ладья
				/*int f = 0;
				for(int i = heigth + 1; i < 8 && f == 0; i++){
					f = this.checkPosition(i, weigth);
					if(f != 2){
						Integer[] toAdd = {i, weigth};
						result.add(toAdd);
					}
					System.out.println("1");
				}
				f = 0;
				for(int i = heigth - 1; i >= 0 && f == 0; i--){
					f = this.checkPosition(i, weigth);
					if(f != 2){
						Integer[] toAdd = {i, weigth};
						result.add(toAdd);
					}
					System.out.println(i);
				}
				f = 0;
				for(int i = weigth + 1; i < 8 && f == 0; i++){
					f = this.checkPosition(heigth, i);
					if(f != 2){
						Integer[] toAdd = {heigth, i};
						result.add(toAdd);
					}
					System.out.println("3");
				}
				f = 0;
				for(int i = weigth - 1; i > -1 && f == 0; i--){
					f = this.checkPosition(heigth, i);
					if(f != 2){
						Integer[] toAdd = {heigth, i};
						result.add(toAdd);
					}
					System.out.println("4");
				}*/
				result = this.getRookPossiblePositions();
				break;
			case "knight":	//конь
				//System.out.println(heigth + ";" + weigth);
				if(heigth + 2 < 8){
					if(weigth + 1 < 8)
						if(this.checkPosition(heigth + 2, weigth + 1) != 2){
						Integer[] toAdd = {heigth + 2, weigth + 1};
						result.add(toAdd);
					}
					if(weigth - 1 >= 0)
						if(this.checkPosition(heigth + 2, weigth - 1) != 2){
						Integer[] toAdd = {heigth + 2, weigth - 1};
						result.add(toAdd);
					}
				}
				if(heigth + 1 < 8){
					if(weigth + 2 < 8)
						if(this.checkPosition(heigth + 1, weigth + 1) != 2){
						Integer[] toAdd = {heigth + 1, weigth + 1};
						result.add(toAdd);
					}
					if(weigth - 2 >= 0)
						if(this.checkPosition(heigth + 1, weigth - 1) != 2){
						Integer[] toAdd = {heigth + 1, weigth - 1};
						result.add(toAdd);
					}
				}
				if(heigth - 2 >= 0){
					if(weigth + 1 < 8)
						if(this.checkPosition(heigth - 2, weigth + 1) != 2){
						//System.out.println(heigth - 2 + ";" + (weigth + 1));
						Integer[] toAdd = {heigth - 2, weigth + 1};
						result.add(toAdd);
					}
					if(weigth - 1 >= 0)
						if(this.checkPosition(heigth - 2, weigth - 1) != 2){
						//System.out.println(heigth - 2 + ";" + (weigth - 1));
						Integer[] toAdd = {heigth - 2, weigth - 1};
						result.add(toAdd);
					}
				}
				if(heigth - 1 >= 0){
					if(weigth + 2 < 8)
						if(this.checkPosition(heigth - 1, weigth + 2) != 2){
						//System.out.println(heigth - 1 + ";" + weigth + 2);
						Integer[] toAdd = {heigth - 1, weigth + 2};
						result.add(toAdd);
					}
					if(weigth - 2 >= 0)
						if(this.checkPosition(heigth - 1, weigth - 2) != 2){
						//System.out.println("!!!");
						Integer[] toAdd = {heigth - 1, weigth - 2};
						result.add(toAdd);
					}
				}
			}
			return result;
		}
		
		//этот метод возвращает 1, если не существует возможных ходов у данной фигуры
		int tryToMove(){
			if(name.equals("pawn"))
				if(heigth == 0 || heigth == 7)
					return 1;
			ArrayList<Integer[]> positions = this.getPossiblePositions();
			if(positions.size() == 0)
				return 1;
			Integer[] newPos = positions.get(rng(positions.size()));
			desk[heigth][weigth] = 0;
			if(desk[newPos[0].intValue()][newPos[1].intValue()] != 0)
				if(index > 0 && index < 17)
					white.remove(new Integer(desk[newPos[0].intValue()][newPos[1].intValue()]));
				else
					black.remove(new Integer(desk[newPos[0].intValue()][newPos[1].intValue()]));
			desk[newPos[0].intValue()][newPos[1].intValue()] = index;
			heigth = newPos[0].intValue();
			weigth = newPos[1].intValue();
			return 0;
		}
	}
/////////////////////////////
	
	void rngMove(ArrayList<Figure> f){
		int a = 1;
		while(a == 1)
			a = f.get(rng(2)).tryToMove();
	}
	
	void createKnights(){
		Figure a = new Figure();
		a.name = "knight";
		a.index = 2;
		a.getPositionByStartIndex();
		fBlack.add(a);
		
		Figure b = new Figure();
		b.name = "knight";
		b.index = 7;
		b.getPositionByStartIndex();
		fBlack.add(b);
		
		Figure c = new Figure();
		c.name = "knight";
		c.index = 26;
		c.getPositionByStartIndex();
		fWhite.add(c);
		
		Figure d = new Figure();
		d.name = "knight";
		d.index = 31;
		d.getPositionByStartIndex();
		fWhite.add(d);
	}
	
	void createRooks(){
		Figure a = new Figure();
		a.name = "rook";
		a.index = 1;
		a.getPositionByStartIndex();
		fBlack.add(a);
		
		Figure b = new Figure();
		b.name = "rook";
		b.index = 8;
		b.getPositionByStartIndex();
		fBlack.add(b);
		
		Figure c = new Figure();
		c.name = "rook";
		c.index = 25;
		c.getPositionByStartIndex();
		fWhite.add(c);
		
		Figure d = new Figure();
		d.name = "rook";
		d.index = 32;
		d.getPositionByStartIndex();
		fWhite.add(d);
	}
	
	void createPawns(){
		for(int i = 9; i < 17; i++){
			Figure a = new Figure();
			a.name = "pawn";
			a.index = i;
			a.getPositionByStartIndex();
			fBlack.add(a);
		}
		for(int i = 17; i < 25; i++){
			Figure a = new Figure();
			a.name = "pawn";
			a.index = i;
			a.getPositionByStartIndex();
			fWhite.add(a);
		}
	}
	
	void createFigures(){
		createRooks();
		createPawns();
		createKnights();
	}
	
	void showDesk(){
		for(int[] x: desk){
			System.out.println();
			for(int a: x)
				if(a > 8 && a < 17)
					System.out.print("чП" + " ");
				else if(a > 16 && a < 25)
					System.out.print("бП" + " ");
				else if(a == 26 || a == 31)
					System.out.print("бК" + " ");
				else if(a == 2 || a == 7)
					System.out.print("чК" + " ");
				else if(a == 0)
					System.out.print(a + "" + a + " ");
				else if(a == 25 || a == 32)
					System.out.print("бЛ" + " ");
				else if(a == 8 || a == 1)
					System.out.print("чЛ" + " ");
				else
					System.out.print(a + " ");
		}
	}
	
	static int rng(int max){
		return new Random().nextInt(max);
	}
	
	int isPositionEmpty(int h, int w){
		if(desk[h][w] == 0)
			return 1;
		return 0;
	}
	
	void test(){
		for(int i = 0; i < 10; i++){
			rngMove(fWhite);
			rngMove(fBlack);
			showDesk();
			System.out.println();
		}
	}
	
	void kTest(){
		createRooks();
		ArrayList<Integer[]> a = fWhite.get(0).getPossiblePositions();
		fWhite.get(0).out();
		for(int i = 0; i < a.size(); i++){
			System.out.println();
			for(int j = 0; j < a.get(i).length; j++)
				System.out.println(a.get(i)[j]);
		}
		fWhite.get(0).tryToMove();
		fWhite.get(0).out();
	}

	public static void main(String[] args) {
		Chess a = new Chess();
		a.createFigures();
		System.out.println("!");
		a.test();
		System.out.println("!");
		//a.kTest();
		a.showDesk();
	}
}
