import java.util.Scanner;

public class ShellSort {
	int[] a;
	int N;
	
	int increment(int inc[])
	{
		int p1, p2, p3, s;
		p1 = p2 = p3 = 1;
		s = -1;
		do{
			if (++s%2 == 1) inc[s] = 8*p1 - 6*p2 + 1;
			else{
				inc[s] = 9*p1 - 9*p3 + 1;
				p2 *= 2;
				p3*= 2;
			}
			p1 *= 2;
		} while(3*inc[s] < N);
		return s>0?--s:0;
	}
	
	void shellSort()
	{
		int inc, i, j;
		int s;
		int[] seq = new int[N];
		s = increment(seq);
		while(s>=0){
			inc = seq[s--];
			for(i = inc; i < N; i++){
				int temp = a[i];
				for(j = i-inc; j>=0 && a[j] > temp; j -= inc)
					a[j+inc] = a[j];
				a[j+inc] = temp;
			}
		}
	}
	
	void get()
	{
		for(int x: a)
			System.out.println(x);
	}
	
	void set()
	{
		Scanner in = new Scanner(System.in);
		N = in.nextInt();
		a = new int[N];
		for(int i = 0; i < N; i++)
			a[i] = in.nextInt();
		in.close();
	}
	
	public static void main (String args []) 
	{
		ShellSort obj = new ShellSort();
		obj.set();
		obj.shellSort();
		obj.get();
	}
}
