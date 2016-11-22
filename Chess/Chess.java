import java.util.ArrayList;
import java.util.Random;

public class Chess {
	int mate = 0;
	Figure whiteKing;
	Figure blackKing;
	int[] knights = {2, 7, 26, 31};
	int[] bishops = {3, 6, 27, 30};
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
	
	/*int[][] board = 
			{{0,0,0,0,0,0,0,0},
			{17, 18, 19, 20, 21, 22, 23, 24},
			{0,0,0,0,0,0,0,0},
			{0,5,0,0,29,0,0,0},
			{0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0},
			{9, 10, 11, 12, 13, 14, 15, 16},
			{0,0,0,0,0,0,0,0}};*/
	
	int[][] board = {{1, 2, 3, 4, 5, 6, 7, 8},
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
		int cost;
		
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
					if(board[i][j] == index)
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
			//this.out();
			Integer[] pawnsPositions = {-1, 1, -1, -1, 1, -1, 1, 1};
			if(index/17 == 1){
				for(int i = 0; i < 2; i++){
					int h = pawnsPositions[i*2] + heigth;
					int w = pawnsPositions[i*2 + 1] + weigth;
					for(int j = 0; j < pawns.length; j++)
						if(this.checkPosition(h, w) == pawns[j]){
							Integer[] toAdd = {h, w};
							result.add(toAdd);
						}
				}
			}else{
				for(int i = 2; i < 4; i++){
					int h = pawnsPositions[i*2] + heigth;
					int w = pawnsPositions[i*2 + 1] + weigth;
					for(int j = 0; j < pawns.length; j++)
						if(this.checkPosition(h, w) == pawns[j]){
							Integer[] toAdd = {h, w};
							result.add(toAdd);
						}
				}
			}
			
			toCheck = new ArrayList<Integer[]>();
			toCheck.addAll(this.getKingPossiblePositions());
			for(int i = 0; i < toCheck.size(); i++){
				int h = toCheck.get(i)[0];
				int w = toCheck.get(i)[1];
				for(int j = 0; j < kings.length; j++)
					if(this.checkPosition(h, w) == kings[j])
						result.add(toCheck.get(i));
			}
			return result;
		}
		
		//-2 - позиция за полем, 0 - пусто, -1 - своя фигура, если фигура противника, то возвращает ее индекс
		int checkPosition(int h, int w){
			if(h > 7 || h < 0 || w > 7 || w < 0)
				return -2;
			int c = board[h][w];
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
			f = 0;
			for(int i = 1; f == 0 && heigth + i < 8 && weigth - i >= 0; i++)
				if((f = this.checkPosition(heigth + i , weigth - i)) != -1){
					Integer[] toAdd = {heigth + i , weigth - i};
					result.add(toAdd);
				}
			f = 0;
			for(int i = 1; f == 0 && heigth - i >= 0 && weigth + i < 8; i++)
				if((f = this.checkPosition(heigth - i , weigth + i)) != -1){
					Integer[] toAdd = {heigth - i , weigth + i};
					result.add(toAdd);
				}
			f = 0;
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
				if(this.checkPosition(heigth + heigthIterator, weigth + 1) > 0){
					Integer[] toAdd = {heigth + heigthIterator, weigth + 1};
					result.add(toAdd);
			}
			if(weigth > 0)
				if(this.checkPosition(heigth + heigthIterator, weigth - 1) > 0){
					Integer[] toAdd = {heigth + heigthIterator, weigth - 1};
					result.add(toAdd);
			}
			if((index/17 == 0 && heigth == 1) || (index/17 == 1 && heigth == 6))
				if(isPositionEmpty(heigth + 2*heigthIterator, weigth) == 1){
					Integer[] toAdd = {heigth + 2*heigthIterator, weigth};
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

		int tryToMove(){
			if(name.equals("pawn"))
				if(heigth%7 == 0)
					return 1;
			
			int a, toDelete, checkToKing;
			
			Figure king;
			if(index/17 == 0)
				king = blackKing;
			else
				king = whiteKing;
			
			ArrayList<Integer[]> positions = this.getPossiblePositions();
			
			do{
				if(positions.size() == 0)
					return 1;
				Integer[] newPos = positions.remove(rng(positions.size()));
				int newHeigth = newPos[0];
				int newWeigth = newPos[1];
				if((a = board[newHeigth][newWeigth]) != 0)
					toDelete = findByIndex(a);
				else
					toDelete = 100;
				int[] tmp = moveFigure(newHeigth, newWeigth);
				
				if((checkToKing = king.isFigureUnderAttack().size()) > 0)
					cancelMove(tmp);
			}while(checkToKing > 0);
			
			if(toDelete != 100)
				if(index/17 == 0)
					fWhite.remove(toDelete);
				else
					fBlack.remove(toDelete);
			System.out.println(index + " " + name + " " + (heigth + 1) + ";" + (weigth + 1));
			/*if(name.equals("pawn") && (heigth%7 == 0))
				makeQueenFromPawn();*/
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
	
		int[] moveFigure(int newHeigth, int newWeigth){
			int ind = checkPosition(newHeigth, newWeigth);
			int[] retval = {ind, heigth, weigth};
			if(ind >= 0){
				board[heigth][weigth] = 0;
				heigth = newHeigth;
				weigth = newWeigth;
				board[newHeigth][newWeigth] = index;
				return retval;
			}else{
				return null;
			}
		}
		
		void cancelMove(int[] oldInfo){
			board[heigth][weigth] = oldInfo[0];
			heigth = oldInfo[1];
			weigth = oldInfo[2];
			board[heigth][weigth] = index;
		}
	}
	
	class Move{
		Figure f;
		Integer[] newPos;
		int rating;
		
		Move(Figure figure, Integer[] np){
			f = figure;
			newPos = np;
			setRating();
		}
		
		void out(){
			System.out.println(f.name + newPos[0] + ";" + newPos[1] + " = " + rating);
		}
		
		void deleteFigure(){
			int index;
			if((index = f.checkPosition(newPos[0], newPos[1])) > 0)
				if(index/17 == 0)
					fBlack.remove(findByIndex(index));
				else
					fWhite.remove(findByIndex(index));	
		}

		int checkMove(){
			int rating;
			Figure king;
			int[] oldInfo = f.moveFigure(newPos[0], newPos[1]);		
			if(oldInfo == null){
				rating = -1000;
			}else{
				if(f.index/17 == 0)
					king = blackKing;
				else
					king = whiteKing;
				if(king.isFigureUnderAttack().size() > 0){
					f.cancelMove(oldInfo);
					return -1000;
				}
				int ti = findByIndex(oldInfo[0]);
				if(ti != 100){
					if(oldInfo[0]/17 == 0)
						rating = fBlack.get(ti).cost;
					else
						rating = fWhite.get(ti).cost;
				}else{
					rating = 0;
				}
				if(f.isFigureUnderAttack().size() > 0){
					rating -= f.cost;
				}
				
			}
			f.cancelMove(oldInfo);
			return rating;
		}
			
		void setRating(){
			rating = checkMove();
		}
	}
/////////////////////////////
	
	int findByIndex(int index){
		int f = 0, i = 0;
		if(index/17 == 0)
			for(; i < fBlack.size() && f != 1; i++){
				if(fBlack.get(i).index == index){
					f = 1;
				}
			}
		if(index/17 == 1)
			for(; i < fWhite.size() && f != 1; i++){
				if(fWhite.get(i).index == index){
					f = 1;
				}
			}
		i--;
		if(f == 1)
			return i;
		else
			return 100;
	}
	
	ArrayList<Move> chooseBestMoves(ArrayList<Move> ma){
		ArrayList<Move> bestMoves = new ArrayList<Move>();
		int maxRating = 0;
		for(Move curMove: ma){
			if(curMove.rating == maxRating)
				bestMoves.add(curMove);
			if(curMove.rating > maxRating){
				maxRating = curMove.rating;
				bestMoves.removeAll(bestMoves);
				bestMoves.add(curMove);
			}
		}
		return bestMoves;
	}
	
	void doMove(ArrayList<Figure> side){
		ArrayList<Move> moves = chooseBestMoves(sortMoves(getAllMoves(side)));
		Move chosen = moves.get(rng(moves.size()));
		chosen.deleteFigure();
		chosen.f.moveFigure(chosen.newPos[0], chosen.newPos[1]);
		System.out.print("MOVE ");
		chosen.out();
		if(whiteKing.isFigureUnderAttack().size() > 0 || blackKing.isFigureUnderAttack().size() > 0)
			System.out.println("CHECK");
		if(chosen.f.name.equals("pawn") && (chosen.f.heigth%7 == 0))
			chosen.f.makeQueenFromPawn();
	}
	
	ArrayList<Move> sortMoves(ArrayList<Move> ma){
		ArrayList<Move> retVal = new ArrayList<Move>();
		for(Move curMove: ma)
			if(curMove.rating > -100){
				retVal.add(curMove);
			}
		return retVal;
	}
	
	ArrayList<Move> getAllMoves(ArrayList<Figure> side){
		ArrayList<Move> result = new ArrayList<Move>();
		for(Figure f: side){
			ArrayList<Integer[]> curFigureAllNewPos = f.getPossiblePositions();
			for(Integer[] curNewPos: curFigureAllNewPos){
				Move toAdd = new Move(f, curNewPos);
				result.add(toAdd);
			}
		}
		return result;
	}
	
	void setboard(){
		int[][] nboard = {{0,0,0,0,5,0,0,0},
				{17, 18, 19, 20, 21, 22, 23, 24},
				{0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0},
				{9, 10, 11, 12, 13, 14, 15, 16},
				{0,0,0,0,29,0,0,0},
				{0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0}};
		board = nboard;
	}
	
	void rngMove(ArrayList<Figure> f){
		ArrayList<Figure> buffer = new ArrayList<Figure>();
		buffer.addAll(f);
		int a = 1;
		while(a == 1 && buffer.size() > 0){
			Figure c = buffer.remove(rng(buffer.size()));
			a = c.tryToMove();
		}
		if(buffer.size() == 0 && a == 1){
			mate = 1;
			if(f.get(0).isFigureUnderAttack().size() > 0)
				System.out.println("MATE " + f.get(0).index);
			else
				System.out.println("PATE " + f.get(0).index);
		}
	}
	
	void createFigures(int cost, String name, int[] fArr){
		for(int i = 0; i < fArr.length; i++){
			Figure a = new Figure();
			a.name = name;
			a.index = fArr[i];
			a.getPositionByStartIndex();
			a.cost = cost;
			if(fArr[i]/17 == 0)
				fBlack.add(a);
			else
				fWhite.add(a);
		}
	}
	
	void createAllFigures(){
		createFigures(1000, "king", kings);
		createFigures(9, "queen", queens);
		createFigures(3, "bishop", bishops);
		createFigures(1, "pawn", pawns);
		createFigures(5, "rook", rooks);
		createFigures(3, "knight", knights);
		whiteKing = fWhite.get(0);
		blackKing = fBlack.get(0);
	}
	
	void showBoard(){
		String[][] board = new String[8][8];
		for(int i = 0; i < 8; i++)
			for(int j = 0; j < 8; j++)
				board[i][j] = "00 ";
		for(Figure f: fBlack)
			board[f.heigth][f.weigth] = "ч" + getAbbreviation(f.name) + " ";
		for(Figure f: fWhite)
			board[f.heigth][f.weigth] = "б" + getAbbreviation(f.name) + " ";
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++)
				System.out.print(board[i][j]);
			System.out.println();
		}
		System.out.println();
	}
	
	String getAbbreviation(String name){
		switch(name){
		case "pawn":
			return "П";
		case "knight":
			return "К";
		case "queen":
			return "Ф";
		case "king":
			return "Ц";
		case "bishop":
			return "С";
		case "rook":
			return "Л";
		}
		return null;
	}
	
	void checkBoard(int i){
		int c = 0;
		for(int[] x: board)
			for(int a: x)
				if(a != 0)
					c++;
		if(c != fBlack.size() + fWhite.size()){
			System.out.println("c = " + c);
			System.out.println("В массивах: " + (fBlack.size() + fWhite.size()));
			errorCount++;
			errors.add(i);
		}
	}
	
	static int rng(int max){
		return new Random().nextInt(max);
	}
	
	int isPositionEmpty(int h, int w){
		try {
			if(board[h][w] == 0)
				return 1;
		} catch (ArrayIndexOutOfBoundsException e) {
			return -1;
		}
		return 0;
	}
	
	void test(){
		createAllFigures();
		for(int i = 0; i < 200 && mate == 0; i++){
			rngMove(fWhite);
			if(whiteKing.isFigureUnderAttack().size() > 0){
				errorCount++;
				errors.add(i + 1);
			}
			if(blackKing.isFigureUnderAttack().size() > 0)
				System.out.println("CHECK!");
			rngMove(fBlack);
			if(blackKing.isFigureUnderAttack().size() > 0){
				errorCount++;
				errors.add(i + 1);
			}
			if(whiteKing.isFigureUnderAttack().size() > 0)
				System.out.println("CHECK!");
			System.out.println(i + 1 + ".");
			showBoard();
			checkBoard(i + 1);
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
	
	void ktest(){
		createAllFigures();
		int mate = 0;
		for(int i = 0; i < 200 && mate == 0; i++){
			System.out.print(i + ".");
			try {
				doMove(fWhite);
			} catch (IllegalArgumentException e) {
				System.out.println("MATE, BLACK WIN");
				mate = 1;
			}
			try {
				doMove(fBlack);
			} catch (IllegalArgumentException e) {
				System.out.println("MATE, WHITE WIN");
				mate = 1;
			}
			showBoard();
		}
	}
	
	public static void main(String[] args) {
		Chess a = new Chess();
		a.ktest();
	}
}
