
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;


// remove PACKAGE
public class PascalTree extends RecursiveTask<Integer> {
    private static int threshold_n = 23;// 21 //25
    private static int threshold_k = 5; // 6 //5
    private int n;
    private int k;
    public PascalTree(int n, int k)
    {
        this.n = n;
        this.k = k;
    }
    private int sequential(int n, int k) {
        if (k > n)
            return 0;
        if (k == 0 || k == n)
            return 1;
        int left = sequential(n-1, k-1);
        int right = sequential(n-1 ,k);
        return left + right;
    }

    public static void setThreshold_n(int threshold_n) {
        PascalTree.threshold_n = threshold_n;
    }

    public static void setThreshold_k(int threshold_k) {
        PascalTree.threshold_k = threshold_k;
    }

    public Integer compute() {
        if(n < threshold_n || k < threshold_k) return sequential(n, k);
        PascalTree left = new PascalTree(this.n-1, this.k-1);
        PascalTree right = new PascalTree(this.n-1, this.k);
        left.fork();
        return right.compute() + left.join();
    }
    public static void main(String[] args) {
        int noOfThreads = Integer.parseInt(args[0]);
        ForkJoinPool pool = new ForkJoinPool(noOfThreads);
        PascalTree p = new PascalTree(30, 10);
        // p is a task
        long start = System.currentTimeMillis();
        int result = pool.invoke(p);
        System.out.println("result: " + result);
        long end = System.currentTimeMillis();
        System.out.println((end - start) + " ms");


    }
}
