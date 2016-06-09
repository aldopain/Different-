import java.util.Random;
import java.util.Scanner;

public class InsertSort {
	int[] arr;
	int N;
	Random random;
	
	void get()
	{
		for(int x: arr)
			System.out.println(x);
	}
	
	void set()
	{
		Scanner in = new Scanner(System.in);
		N = in.nextInt();
		arr = new int[N];
		for(int i = 0; i < N; i++)
			arr[i] = in.nextInt();
		in.close();
	}
	
	void check()
	{
		int flag = 1;
		for(int i = 0; i < N-1; i++)
			if(arr[i] > arr[++i])
			{
				System.out.println("Ошибка!");
				flag = 0;
			}
		if (flag == 1)
			System.out.println("Алгоритм работает.");
	}
	
	void lazyset()
	{
		random = new Random();
		N = 100;
		arr = new int[N];
		for(int i = 0; i < N; i++)
			arr[i] = random.nextInt(100);
	}
	
	void insertSort(int l, int h)
	{
		int j, i;
		for(j = l ;j < h; j++)
		{
			    int key = arr[j];
			    i = j - 1;
			    while (i >= l && arr[i] > key)
			    {
			    	arr[i+1] = arr[i];
			        i--;
			    }
			    arr[i+1] = key;
	}
	}
	
	public static void main (String args []) 
	{
		InsertSort obj = new InsertSort();
		obj.lazyset();
		obj.insertSort(0, obj.N);
		obj.get();
		obj.check();
	}
}
