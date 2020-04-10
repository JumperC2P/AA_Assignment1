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
		}
    	else if(array[array.length-1] != null) {
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
		OrderedArray[] tempQueneArray = new OrderedArray[array.length];
		
		for (int j = 1 ; j < array.length ; j++) {
			
			if (array[0] == null){
				return"";
			}
			tempQueneArray[j-1] = array[j];
		}
		
		String del = array[0].procLabel;
		array = tempQueneArray;
		
        return del; // placeholder,modify this
    } // end of dequeue()


    @Override
    public boolean findProcess(String procLabel){
        // Implement me
    	for(int j = 0; j < array.length-1; j++) {
			if(array[j] == null){
				return false;
			}
			if(procLabel.equals(array[j].procLabel)){
				return true;			
			}
    	}
    	return false;
    } // end of findProcess()


    @Override
    public boolean removeProcess(String procLabel) {
		// Implement me
		Boolean remove = false;
    	for(int j = 0; j < array.length-1; j++) {
			if(array[j] == null){
				return remove;
			}
    		if(procLabel.equals(array[j].procLabel)) {
				System.arraycopy(array, j+1, array, j, array.length-j-1);
				remove = true;
			}
    	}
    	return remove;
    } // end of removeProcess()


    @Override
    public int precedingProcessTime(String procLabel) {
        // Implement me
		int precedingTime =0;
		Boolean findProcess1 = false;
		
    	for(int j = 0; j < array.length-1; j++) {
			if(array[j] == null){
				break;
			}
    		if(!procLabel.equals(array[j].procLabel)) {
				precedingTime += array[j].vt;
				findProcess1 = true;		
			}else{ 
				break;
			}				
		}
		if(!findProcess1){
			return -1;
		}
		return precedingTime;
    }// end of precedingProcessTime()


    @Override
    public int succeedingProcessTime(String procLabel) {
        // Implement me
		int precedingTime = 0;
		int beforTime = 0;
		Boolean findProcess2 = false;
		for(int j = 0; j < array.length-1; j++) {
			if(array[j] == null){
				break;
			}if(procLabel.equals(array[j].procLabel)) {
				findProcess2 = true;
				beforTime =j+1;	
				continue;
			}
		}
		for(int a=beforTime; a <array.length-1; a++){
			if (findProcess2){
				if(array[a]==null){
					break;
				}else{
					precedingTime += array[a].vt;
				}
			}else{
				break;
			}
		}
		if(!findProcess2){
			return -1;
		}
    	return precedingTime;
    } // end of SucceedingProcessTime()


    @Override
    public void printAllProcesses(PrintWriter os) {
        //Implement me
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < array.length-1; i++) {
    		if(array[i] == null){
				break;
			}else{
			sb.append(array[i].procLabel).append(" ");
			}
    	}
    	os.println(sb.toString());
    } // end of printAllProcesses()

} // end of class OrderedArrayRQ
