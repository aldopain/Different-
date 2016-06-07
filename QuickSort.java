//простенькая программка, демонстрирующая работу быстрой сортировки

import java.util.Random;
import java.util.Scanner;

class QuickSort{
	static Random random = new Random(); 	//рандомайзер
	int arr[];							            	//массив чисел
	void quickSort(int beg, int end)		  //подаем начало и конец выделяемой области массива
	{
		int p = arr[random.nextInt(end+1)];	//случайно выбираем p
		int i = beg, j = end, temp;			
		do{
			while(arr[i]<p) i++;		         	//ищем номер элемента справа от p, который больше p
			while(arr[j]>p) j--;		        	//ищем номер элемента слева от p, который меньше p
			if(i<=j){
				temp = arr[i]; 
				arr[i] = arr[j]; 
				arr[j] = temp;			          	//меняем их местами
				i++;j--;					
			}
		}while (i<=j);
		if ( beg < j ) quickSort(beg, j);
		if ( end > i ) quickSort(i, end); 	//применяем метод рекурсивно к подмножествам
	}
	
	void get()
	{
		for(int x: arr)
			System.out.println(x);
	}
	
	void set()
	{
		Scanner in = new Scanner(System.in);
		int N = in.nextInt();
		arr = new int[N];
		for(int i = 0; i < N; i++)
			arr[i] = in.nextInt();
		in.close();
	}
	
public static void main (String args []) 
{
	QuickSort obj = new QuickSort();
	obj.set();
	obj.quickSort(0, obj.arr.length - 1);
	obj.get();
}

}
