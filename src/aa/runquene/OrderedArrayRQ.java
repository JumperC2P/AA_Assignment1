package aa.runquene;
import java.io.PrintWriter;


/**
 * Implementation of the Runqueue interface using an Ordered Array.
 *
 * Your task is to complete the implementation of this class.
 * You may add methods and attributes, but ensure your modified class compiles and runs.
 *
 * @author Sajal Halder, Minyi Li, Jeffrey Chan
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
    		OrderedArray[] temparray = new OrderedArray[array.length + SIZE];
    		System.arraycopy(array, 0, temparray, array.length-1, array.length-1);
    		array = temparray;
    		enqueue(procLabel, vt);
    	} else {
        		for(int i = 0; i < array.length-1; i++) {
        			if(array[i] == null) {
        				array[i] = new OrderedArray(procLabel, vt);
        				return;
        		}
        		if(vt < array[i].vt) {
        			System.arraycopy(array, i, array, i+1, array.length-i-1);
        			array[i] = new OrderedArray(procLabel, vt);
        			return;
        		}
        	}
    	}
    	
    } // end of enqueue()


    @Override
    public String dequeue() {
        // Implement me
    	if(array[0] == null){
			 return "";
		}
    	else {
    		String del = array[0].procLabel;
    		System.arraycopy(array, 1, array, 0, array.length-1);
        	return del;
    	}
    } // end of dequeue()


    @Override
    public boolean findProcess(String procLabel){
        // Implement me
    	for(int i = 0; i < array.length-1; i++) {
			if(array[i] == null){
				return false;
			}
    		if(array[i].procLabel.equals(procLabel)){
				return true;
			}
    	}
    	return false;
    } // end of findProcess()


    @Override
    public boolean removeProcess(String procLabel) {
        // Implement me
    	for(int i = 0; i < array.length-1; i++) {
    		if(array[i] == null){
				return false;
			}
    		if(array[i].procLabel.equals(procLabel)) {
    			System.arraycopy(array, i+1, array, i, array.length-i-1);
    			return true;
    		}
    	}
    	return false;
    } // end of removeProcess()


    @Override
    public int precedingProcessTime(String procLabel) {
        // Implement me
		if(!findProcess(procLabel)){
			return -1;
		}
		int preceding = 0;
    	for(int i = 0; i < array.length-1; i++) {
    		if(!array[i].procLabel.equals(procLabel)) {
    			preceding += array[i].vt;
			}else{ 
				break;
			}	
    	}
    	return preceding;
    }// end of precedingProcessTime()


    @Override
    public int succeedingProcessTime(String procLabel) {
        // Implement me
    	if(!findProcess(procLabel)){
			return -1;
		}
    	int preceding = 0;
    	int pos = 0;
    	for(int i = 0; i < array.length-1; i++) {
    		if(array[i].procLabel.equals(procLabel)) {
    			pos = i+1;
    			break;
    		}
    	}
    	for(int i = pos; i < array.length-1; i++) {
    		if(array[i] == null){
				break;
			}
    		preceding += array[i].vt;
    	}
    	return preceding;
    } // end of precedingProcessTime()


    @Override
    public void printAllProcesses(PrintWriter os) {
        //Implement me
    	for(int i = 0; i < array.length-1; i++) {
    		if(array[i] == null) {
				break;
			}
    		os.print(array[i].procLabel + " ");
    	}
    	os.println();
    } // end of printAllProcesses()

} // end of class OrderedArrayRQ
