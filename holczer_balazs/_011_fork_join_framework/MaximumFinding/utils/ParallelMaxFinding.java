package utils;

import java.util.concurrent.RecursiveTask;

public class ParallelMaxFinding extends RecursiveTask<Long> {

	private long[] nums;
	private int lowIndex;
	private int highIndex;
	
	public ParallelMaxFinding(long[] nums, int lowIndex, int highIndex) {
		this.highIndex = highIndex;
		this.lowIndex = lowIndex;
		this.nums = nums;
	}

	@Override
	protected Long compute() {
		
		if( highIndex - lowIndex < 5000 ) {
			return sequentialMaxFinding();
		} else {
			
			int middleIndex = (lowIndex+highIndex) / 2;
			
			ParallelMaxFinding leftSubtask = new ParallelMaxFinding(nums, lowIndex, middleIndex);
			ParallelMaxFinding rightSubtask = new ParallelMaxFinding(nums, middleIndex, highIndex);
			
			invokeAll(leftSubtask, rightSubtask);
			
			return Math.max(leftSubtask.join(), rightSubtask.join());
		}
	}
	
	private long sequentialMaxFinding() {
		
		long max = nums[0];
		
		for(int i=lowIndex; i<highIndex;++i)
			if( nums[i] > max)
				max = nums[i];
		
		return max;
	}
}
