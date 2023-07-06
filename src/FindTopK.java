import util.Pair;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FindTopK {
    // testing: PQ is 5x faster than QS
    public static int findKthLargestPQ(int[] nums, int k) {
	PriorityQueue<Integer> minHeap = new PriorityQueue<> ();
	k = Math.min(k, nums.length);
	for(int i = 0; i < k; i++) {
	    minHeap.add(nums[i]);
	}
	for (int i = k; i < nums.length; i++) {
	    int a = nums[i];

	    if (a > minHeap.peek()) {
		minHeap.add(a);
		minHeap.poll();
	    }
	}
	return minHeap.peek();
    }

    public static List<Integer> findKLargestPQ(int[] nums, int k) {
	PriorityQueue<Integer> minHeap = new PriorityQueue<> ();
	k = Math.min(k, nums.length);
	for(int i = 0; i < k; i++) {
	    minHeap.add(nums[i]);
	}
	for (int i = k; i < nums.length; i++) {
	    int a = nums[i];

	    if (a > minHeap.peek()) {
		minHeap.add(a);
		minHeap.poll();
	    }
	}
	//int arr = new int[k];
	return minHeap.stream().toList ();
    }

    public static List<Integer> findKLargestPQParallel(int[] nums, int k) {
	List<int[]> blocks = getTopKBlocks(nums, k);
	Stream<List<Integer>> results = blocks.parallelStream ().map(arr -> findKLargestPQ (arr, k));
	List<Integer> resultList = new ArrayList<> ();
	results.sequential ().forEach (resultList::addAll);
	//System.out.println("ResultList size = " + resultList.size());
	/*if (resultList.size() > k) {
	    int diff = resultList.size() - k;
	    resultList.sort(Integer::compare);
	    resultList = resultList.subList (diff, resultList.size ());
	}*/
	return resultList;
    }

    public static int findKthLargestPQParallel(int[] nums, int k) {
	List<Integer> results = findKLargestPQParallel(nums, k);
	//System.out.println("result size = " + results.size() + " expected " + (k));
	int[] res = results.stream().mapToInt (x -> x).toArray ();
	return findKthLargestPQ (res, k);
    }

    private static List<int[]> getTopKBlocks(int[] nums, int k) {
	ArrayList<int[]> workBlocks = new ArrayList<> ();
	// stream uses this internally as well
	int numProc = Runtime.getRuntime().availableProcessors();
	int blockSize = (int)Math.ceil((double)nums.length / numProc);
	int numBlocks = (int)Math.ceil((double)nums.length / blockSize);
	int[][] subs = new int[numBlocks][blockSize];


 	for(int i = 0; i < numBlocks; i++) {
	     //System.out.println("Spawning block: [" + (i*blockSize) + ", " + ((i+1)*blockSize) + ")");
	    for(int j = 0; j < blockSize; j++) {
		subs[i][j] = nums[i * blockSize + j];
	    }
	}

	if (numProc * blockSize >= nums.length) {
	    //System.out.println("this happened");
	    for(int i = 0; i < numBlocks; i++) {
		workBlocks.add(subs[i]);
	    }
	}

	return workBlocks;
    }

    public static int findKthLargestQS(int[] nums, int k) {
	return quickSelect(nums, 0, nums.length-1, nums.length - k);

    }

    private static int quickSelect(int[] A, int left, int right, int k) {
	if (left == right) return A[left];

	int pIndex = new Random ().nextInt(right - left + 1) + left;
	pIndex = partition(A, left, right, pIndex);

	if (pIndex == k) return A[k];
	else if (pIndex < k) return quickSelect(A, pIndex+1, right, k);
	return quickSelect(A, left, pIndex-1, k);
    }

    private static int partition(int[] A, int left, int right, int pIndex) {
	int pivot = A[pIndex];
	swap(A, pIndex, right);
	pIndex = left;

	for (int i=left; i<=right; i++) {
	    int test = A[i];
	    if (test <= pivot) {
		swap(A, i, pIndex++);
	    }
	}

	return pIndex - 1;
    }

    private static void swap(int[] A, int x, int y) {
	int temp = A[x];
	A[x] = A[y];
	A[y] = temp;
    }
}

