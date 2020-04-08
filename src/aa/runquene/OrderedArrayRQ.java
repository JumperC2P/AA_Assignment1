package aa.runquene;

import java.io.PrintWriter;


/**
 * Implementation of the Runqueue interface using an Ordered Array.
 *
 * Your task is to complete the implementation of this class.
 * You may add methods and attributes, but ensure your modified class compiles and runs.
 *
 * @author Sajal Halder, Minyi Li, Jeffrey Chan, Chih-Hsuan Lee<s3714761>, Yiheng Liu<s3673551>
 */
public class OrderedArrayRQ implements Runqueue {

	private final int SIZE = 10;
	private OrderedArray[] array;
	
	public class OrderedArray {
		String procLabel;
		int vt;
		
		public OrderedArray(String procLabel, int vt) {
			this.procLabel = procLabel;
			this.vt = vt;
		}
	}
	
	
    /**
     * Constructs empty queue
     */
    public OrderedArrayRQ() {
        // Implement Me
        array = new OrderedArray[SIZE];
    }  // end of OrderedArrayRQ()


    @Override
    public void enqueue(String procLabel, int vt) {
        // Implement me
    	// find the proper position for the input
    	if(array[0] == null){
			array[0] = new OrderedArray(procLabel, vt);
			return;
		}
    	if(this.array[array.length-1] != null) {
    		OrderedArray[] tempQueneArrays = new OrderedArray[array.length + SIZE];
    		System.arraycopy(array, 0, tempQueneArrays, array.length-1, array.length-1);
    		array = tempQueneArrays;
    		enqueue(procLabel, vt);
    	} else {
        		for(int j = 0; j < array.length-1; j++) {
        			if(array[j] == null) {
        				array[j] = new OrderedArray(procLabel, vt);
        				return;
        		}
        		if(vt < array[j].vt) {
        			System.arraycopy(array, j, array, j+1, array.length-j-1);
        			array[j] = new OrderedArray(procLabel, vt);
        			return;
        		}
        	}
    	}
    	
    } // end of enqueue()


    @Override
    public String dequeue() {
		// Implement me
		OrderedArray[] tempQueneArrays = new OrderedArray[array.length];
    	if(this.array[0] == null){
			 return "";
		}
    	else {
			String del = array[0].procLabel;
			this.array = tempQueneArrays;
        	return del;
    	}
    } // end of dequeue()


    @Override
    public boolean findProcess(String procLabel){
        // Implement me
    	for(int i = 0; i < array.length-1; i++) {
			if(array[i].procLabel.equals(procLabel)){
				return true;			
			}
    		if(array[i] == null){
				break;
			}
    	}
    	return false;
    } // end of findProcess()


    @Override
    public boolean removeProcess(String procLabel) {
		// Implement me
		OrderedArray[] currArray = new OrderedArray[array.length];
    	for(int j = 0; j < array.length-1; j++) {
    		if(array[j].procLabel.equals(procLabel)) {
				this.array = currArray;
    			return true;
			}
			if(array[j] == null){
				return false;
			}
    	}
    	return false;
    } // end of removeProcess()


    @Override
    public int precedingProcessTime(String procLabel) {
        // Implement me
		int precedingTime = 0;
    	for(int j = 0; j < array.length-1; j++) {
    		if(!array[j].procLabel.equals(procLabel)) {
    			precedingTime += array[j].vt;
			}else{ 
				break;
			}	
		}
		if(!findProcess(procLabel)){
			return -1;
		}
    	return precedingTime;
    }// end of precedingProcessTime()


    @Override
    public int succeedingProcessTime(String procLabel) {
        // Implement me
    	int preceding = 0;
		int pos = 0;
		Boolean findProcess = false;
		for(int j = pos; j < array.length-1; j++) {
			if(array[j] == null){
				break;
			}
    		preceding += array[j].vt;
		}
    	for(int j = 0; j < array.length-1; j++) {
    		if(array[j].procLabel.equals(procLabel)) {
				findProcess = true;
    			pos = j+1;
    			break;
			}
			preceding += array[j].vt;
    	}
		if(!findProcess){
			return -1;
		}
    	return preceding;
    } // end of precedingProcessTime()


    @Override
    public void printAllProcesses(PrintWriter os) {
        //Implement me
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < array.length-1; i++) {
    		if(array[i] == null) {
				break;
			}else{
			sb.append(array[i].procLabel + " ");
			}
    	}
    	os.println(sb.toString());
    } // end of printAllProcesses()

} // end of class OrderedArrayRQ
