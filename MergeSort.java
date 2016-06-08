import java.util.Scanner;

public class MergeSort {
	int[] arr;
	int[] buf;
	int N;
	
	void mergeSort(int l, int h)
	{
		if (l < h){
			int m = l + (h - l)/2;
			mergeSort(l, m);
			mergeSort(m+1, h);
			merge(l, m, h);
		}
	}
	
	void merge(int l, int m, int h)
	{
		for(int i = l; i <= h; i++)
			buf[i] = arr[i];
		int i = l;
		int j = m + 1;
		int k = l;
		while(i <= m && j <= h)
		{
			if (buf[i] <= buf[j])
			{
				arr[k] = buf[i];
				i++;
			}
			else
			{
				arr[k] = buf[j];
				j++;
			}
		k++;
		}
		for(; i <= m ; i++, k++)
			arr[k] = buf[i];
	}
	
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
		buf = new int[N];
	}
	
	public static void main (String args []) 
	{
		MergeSort obj = new MergeSort();
		obj.set();
		obj.mergeSort(0, obj.N-1);
		obj.get();
	}
}
