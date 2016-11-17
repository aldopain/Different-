import java.util.ArrayList;
import java.util.Random;

public class Chess {
	int[] knights = {2, 7, 26, 31};
	int[] bishops ={3, 6, 27, 30};
	int[] queens = {4, 28};
	int[] pawns = {9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24};
	int[] rooks = {1, 8, 25, 32};
	int[] kings = {5, 29};
	int createdFigures = 0;
	String alphabet = "abcdefgh";
	ArrayList<String> logs = new ArrayList<String>();
	int errorCount = 0;
	ArrayList<Integer> errors = new ArrayList<Integer>();
	ArrayList<Figure> fBlack = new ArrayList<Figure>();
	ArrayList<Figure> fWhite = new ArrayList<Figure>();
	/*int[][] desk = {{1, 2, 3, 4, 5, 6, 7, 8},
			{9, 10, 11, 12, 13, 14, 15, 16},
			{0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0},
			{17, 18, 19, 20, 21, 22, 23, 24},
			{25, 26, 27, 28, 29, 30, 31, 32}};*/
	
	int[][] desk = 
			{{0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,28},
			{27,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0},
			{0,0,0,5,0,0,0,0}};
			
	
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
		
		ArrayList<Integer[]> isFigureUnderAttack(){
			ArrayList<Integer[]> result = new ArrayList<Integer[]>();
			
			ArrayList<Integer[]> toCheck = new ArrayList<Integer[]>();
			toCheck.addAll(this.getKnightPossiblePositions());
			for(int i = 0; i < toCheck.size(); i++){
				int h = toCheck.get(i)[0];
				int w = toCheck.get(i)[1];
				for(int j = 0; j < knights.length; j++)
					if(this.checkPosition(h, w) == knights[j])
						result.add(toCheck.get(i));
			}
			
			toCheck = new ArrayList<Integer[]>();
			toCheck.addAll(this.getRookPossiblePositions());
			for(int i = 0; i < toCheck.size(); i++){
				int h = toCheck.get(i)[0];
				int w = toCheck.get(i)[1];
				for(int j = 0; j < rooks.length; j++)
					if(this.checkPosition(h, w) == rooks[j])
						result.add(toCheck.get(i));
				for(int j = 0; j < queens.length; j++)
					if(this.checkPosition(h, w) == queens[j])
						result.add(toCheck.get(i));
			}
			
			toCheck = new ArrayList<Integer[]>();
			toCheck.addAll(this.getBishopPossiblePositions());
			for(int i = 0; i < toCheck.size(); i++){
				int h = toCheck.get(i)[0];
				int w = toCheck.get(i)[1];
				for(int j = 0; j < bishops.length; j++)
					if(this.checkPosition(h, w) == bishops[j])
						result.add(toCheck.get(i));
				for(int j = 0; j < queens.length; j++)
					if(this.checkPosition(h, w) == queens[j])
						result.add(toCheck.get(i));
			}
			System.out.println("Всего: " + result.size());
			for(int i = 0; i < result.size(); i++)
				System.out.println(result.get(i)[0] + 1 + ";" + (result.get(i)[1] + 1));
			System.out.println("Всего: " + toCheck.size());
			return result;
		}
		
		//-2 - позиция за полем, 0 - пусто, -1 - своя фигура, если фигура противника, то возвращает ее индекс
		int checkPosition(int h, int w){
			if(h > 7 || h < 0 || w > 7 || w < 0)
				return -2;
			int c = desk[h][w];
			if(isPositionEmpty(h,w) == 1)
				return 0;
			if(index > 0 && index < 17){
				if(c > 0 && c < 17){
					return -1;
				}else{
					return c;
				}
			}else{
				if(c > 0 && c < 17)
					return c;
				else
					return -1;
			}
		}
		
		ArrayList<Integer[]> getBishopPossiblePositions(){
			ArrayList<Integer[]> result = new ArrayList<Integer[]>();
			int f = 0;
			for(int i = 1; f == 0 && heigth + i < 8 && weigth + i < 8; i++)
				if((f = this.checkPosition(heigth + i , weigth + i)) != -1){
					Integer[] toAdd = {heigth + i , weigth + i};
					result.add(toAdd);
				}
			for(int i = 1; f == 0 && heigth + i < 8 && weigth - i >= 0; i++)
				if((f = this.checkPosition(heigth + i , weigth - i)) != -1){
					Integer[] toAdd = {heigth + i , weigth - i};
					result.add(toAdd);
				}
			for(int i = 1; f == 0 && heigth - i >= 0 && weigth + i < 8; i++)
				if((f = this.checkPosition(heigth - i , weigth + i)) != -1){
					Integer[] toAdd = {heigth - i , weigth + i};
					result.add(toAdd);
				}
			for(int i = 1; f == 0 && heigth - i >= 0 && weigth - i >= 0; i++)
				if((f = this.checkPosition(heigth - i , weigth - i)) != -1){
					Integer[] toAdd = {heigth - i , weigth - i};
					result.add(toAdd);
				}
			return result;
		}
		
		ArrayList<Integer[]> getKnightPossiblePositions(){
			ArrayList<Integer[]> result = new ArrayList<Integer[]>();
			if(heigth + 2 < 8){
				if(weigth + 1 < 8)
					if(this.checkPosition(heigth + 2, weigth + 1) != -1){
					Integer[] toAdd = {heigth + 2, weigth + 1};
					result.add(toAdd);
				}
				if(weigth - 1 >= 0)
					if(this.checkPosition(heigth + 2, weigth - 1) != -1){
					Integer[] toAdd = {heigth + 2, weigth - 1};
					result.add(toAdd);
				}
			}
			if(heigth + 1 < 8){
				if(weigth + 2 < 8)
					if(this.checkPosition(heigth + 1, weigth + 2) != -1){
					Integer[] toAdd = {heigth + 1, weigth + 2};
					result.add(toAdd);
				}
				if(weigth - 2 >= 0)
					if(this.checkPosition(heigth + 1, weigth - 2) != -1){
					Integer[] toAdd = {heigth + 1, weigth - 2};
					result.add(toAdd);
				}
			}
			if(heigth - 2 >= 0){
				if(weigth + 1 < 8)
					if(this.checkPosition(heigth - 2, weigth + 1) != -1){
					Integer[] toAdd = {heigth - 2, weigth + 1};
					result.add(toAdd);
				}
				if(weigth - 1 >= 0)
					if(this.checkPosition(heigth - 2, weigth - 1) != -1){
					Integer[] toAdd = {heigth - 2, weigth - 1};
					result.add(toAdd);
				}
			}
			if(heigth - 1 >= 0){
				if(weigth + 2 < 8)
					if(this.checkPosition(heigth - 1, weigth + 2) != -1){
					Integer[] toAdd = {heigth - 1, weigth + 2};
					result.add(toAdd);
				}
				if(weigth - 2 >= 0)
					if(this.checkPosition(heigth - 1, weigth - 2) != -1){
					Integer[] toAdd = {heigth - 1, weigth - 2};
					result.add(toAdd);
				}
			}
			return result;
		}
		
		ArrayList<Integer[]> getKingPossiblePositions(){
			ArrayList<Integer[]> result = new ArrayList<Integer[]>();
			int[] a = {0, 1, 0, -1, 1, 0, -1, 0, 1, 1, 1, -1, -1, 1, -1, -1};
			for(int i = 0; i < 8; i++)
				if(this.checkPosition(heigth +  a[i*2], weigth + a[i*2+1]) > -1){
					Integer[] toAdd = {heigth +  a[i*2], weigth + a[i*2+1]};
					result.add(toAdd);
				}
			return result;
		}
		
		ArrayList<Integer[]> getRookPossiblePositions(){
			ArrayList<Integer[]> result = new ArrayList<Integer[]>();
			int f = 0;
			for(int i = heigth + 1; i < 8 && f == 0; i++){
				if((f = this.checkPosition(i , weigth)) != -1){
					Integer[] toAdd = {i , weigth};
					result.add(toAdd);
				}
			}f = 0;
			for(int i = heigth -1; i > -1 && f == 0; i--){
				if((f = this.checkPosition(i , weigth)) != -1){
					Integer[] toAdd = {i , weigth};
					result.add(toAdd);
				}
			}f = 0;
			for(int i = weigth + 1; i < 8 && f == 0; i++){
				if((f = checkPosition(heigth , i)) != -1){
					Integer[] toAdd = {heigth , i};
					result.add(toAdd);
				}
			}f = 0;
			for(int i = weigth - 1; i > -1 && f == 0; i--){
				if((f = this.checkPosition(heigth , i)) != -1){
					Integer[] toAdd = {heigth , i};
					result.add(toAdd);
				}
			}
			return result;
		}
		
		ArrayList<Integer[]> getPawnPossiblePositions(){
			int heigthIterator;
			ArrayList<Integer[]> result = new ArrayList<Integer[]>();
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
			return result;
		}
		
		ArrayList<Integer[]> getPossiblePositions(){
			ArrayList<Integer[]> result = new ArrayList<Integer[]>();
			switch(name){
			case "pawn":	//пешка
				result.addAll(this.getPawnPossiblePositions());
				break;
			case "bishop":	//слон
				result.addAll(this.getBishopPossiblePositions());
				break;
			case "queen":	//ферзь
				result.addAll(this.getRookPossiblePositions());
				result.addAll(this.getBishopPossiblePositions());
				break;
			case "king":	//король
				result.addAll(this.getKingPossiblePositions());
				break;
			case "rook":	//ладья
				result.addAll(this.getRookPossiblePositions());
				break;
			case "knight":	//конь
				result.addAll(this.getKnightPossiblePositions());
			}
			return result;
		}
		
		int findByIndex(int index){
			int f = 0, i = 0;
			if(index > 0 && index < 17)
				for(; i < fBlack.size() && f != 1; i++){
					if(fBlack.get(i).index == index){
						f = 1;
						fBlack.get(i).out();
					}
				}
			if(index > 16 && index < 33)
				for(; i < fWhite.size() && f != 1; i++){
					if(fWhite.get(i).index == index){
						f = 1;
						fWhite.get(i).out();
					}
				}
			//System.out.println("f = " + f);
			i--;
			//System.out.println("i = " + i);
			if(f == 1)
				return i;
			else
				return 100;
		}
		
		int tryToMove(){
			if(name.equals("pawn"))
				if(heigth%7 == 0)
					return 1;
			
			int a, toDelete, checkToKing, buf;
			int oldHeigth = heigth, oldWeigth = weigth;
			
			Figure king;
			if(index/16 == 0)
				king = fBlack.get(0);
			else
				king = fWhite.get(0);
			
			ArrayList<Integer[]> positions = this.getPossiblePositions();
			
			do{
				//System.out.println(name + " positions = " + positions.size());
				//showDesk();
				if(positions.size() == 0)
					return 1;
				Integer[] newPos = positions.remove(rng(positions.size()));
				int newHeigth = newPos[0];
				int newWeigth = newPos[1];
				desk[heigth][weigth] = 0;
				if((a = desk[newHeigth][newWeigth]) != 0)
					toDelete = findByIndex(a);
				else
					toDelete = 100;
				buf = desk[newHeigth][newWeigth];
				desk[newHeigth][newWeigth] = index;
				heigth = newHeigth;
				weigth = newWeigth;
				if((checkToKing = king.isFigureUnderAttack().size()) > 0){
					//System.out.println("checkToKing.size > 0");
					desk[newHeigth][newWeigth] = buf;
					heigth = oldHeigth;
					weigth = oldWeigth;
					desk[heigth][weigth] = index;
				}
				//System.out.println("checkToKing = " + checkToKing);
			}while(checkToKing > 0);
			
			if(toDelete != 100)
				if(index/16 == 0)
					fWhite.remove(toDelete);
				else
					fBlack.remove(toDelete);
			if(name.equals("pawn") && (heigth%7 == 0))
				makeQueenFromPawn();
			return 0;
		}

		void makeQueenFromPawn(){
			name = "queen";
			int[] newQueens = new int[queens.length + 1];
			for(int i = 0; i < queens.length; i++)
				newQueens[i] = queens[i];
			newQueens[queens.length] = index;
			queens = newQueens;
		}
	}
/////////////////////////////
	
	void rngMove(ArrayList<Figure> f){
		ArrayList<Figure> buffer = new ArrayList<Figure>();
		buffer.addAll(f);
		int a = 1;
		while(a == 1 && buffer.size() > 0){
			Figure c = buffer.get(rng(buffer.size()));
			a = c.tryToMove();
			buffer.remove(c);
		}
	}
	
	void createQueens(){
		/*Figure a = new Figure();
		a.name = "queen";
		a.index = 4;
		a.getPositionByStartIndex();
		fBlack.add(a);*/
		
		Figure b = new Figure();
		b.name = "queen";
		b.index = 28;
		b.getPositionByStartIndex();
		fWhite.add(b);
		
		createdFigures += 2;
	}
	
	void createBishops(){
		/*Figure a = new Figure();
		a.name = "bishop";
		a.index = 3;
		a.getPositionByStartIndex();
		fBlack.add(a);
		
		Figure b = new Figure();
		b.name = "bishop";
		b.index = 6;
		b.getPositionByStartIndex();
		fBlack.add(b);*/
		
		Figure c = new Figure();
		c.name = "bishop";
		c.index = 27;
		c.getPositionByStartIndex();
		fWhite.add(c);
		
		/*Figure d = new Figure();
		d.name = "bishop";
		d.index = 30;
		d.getPositionByStartIndex();
		fWhite.add(d);*/
		
		createdFigures += 4;
	}
	
	void createKings(){
		Figure a = new Figure();
		a.name = "king";
		a.index = 5;
		a.getPositionByStartIndex();
		fBlack.add(a);
		
		/*Figure b = new Figure();
		b.name = "king";
		b.index = 29;
		b.getPositionByStartIndex();
		fWhite.add(b);
		
		createdFigures += 2;*/
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
		
		createdFigures += 4;
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
		
		createdFigures += 4;
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
		
		createdFigures += 16;
	}
	
	void createFigures(){
		createBishops();
		createQueens();
		createRooks();
		createPawns();
		createKnights();
		createKings();
	}
	
	void showDesk(){
		for(int[] x: desk){
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
				else if(a == 3 || a == 6)
					System.out.print("чC" + " ");
				else if(a == 27 || a == 30)
					System.out.print("бС" + " ");
				else if(a == 4)
					System.out.print("чФ" + " ");
				else if(a == 28)
					System.out.print("бФ" + " ");
				else if(a == 5)
					System.out.print("чЦ" + " ");
				else if(a == 29)
					System.out.print("бЦ" + " ");
			System.out.println();
		}
		System.out.println();
	}
	
	void checkDesk(int i){
		int c = 0;
		for(int[] x: desk)
			for(int a: x)
				if(a != 0)
					c++;
		if(c != fBlack.size() + fWhite.size() + 32 - createdFigures){
			System.out.println("c = " + c);
			System.out.println("В массивах: " + (fBlack.size() + fWhite.size()));
			System.out.println("Не было создано: " + (32 - createdFigures));
			errorCount++;
			errors.add(i);
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
		createFigures();
		for(int i = 0; i < 100; i++){
			rngMove(fWhite);
			rngMove(fBlack);
			System.out.println(i + 1 + ".");
			//System.out.println(logs.get(i*2 + 1));
			showDesk();
			checkDesk(i + 1);
		}
		System.out.println("Errors found: " + errorCount);
		for(Integer x: errors)
			System.out.print(x + ", ");
		System.out.println();
		for(Figure c: fWhite)
			System.out.println(c.name + " " + c.index);
		for(Figure c: fBlack)
			System.out.println(c.name + " " + c.index);
	}

	void createFiguresToTest(){
		createKings();
		createQueens();
		createBishops();
	}
	
	void ktest(){
		createFiguresToTest();
		ArrayList<Integer[]> a = fWhite.get(1).getBishopPossiblePositions();
		for(Integer[] x: a)
			System.out.println(x[0] + ";" + x[1]);
		//rngMove(fBlack);
		showDesk();
		//fBlack.get(0).isFigureUnderAttack();
	}
	
	public static void main(String[] args) {
		Chess a = new Chess();
		//System.out.println(a.createdFigures);
		a.ktest();
	}
}
