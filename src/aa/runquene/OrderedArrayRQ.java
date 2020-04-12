package aa.runquene;


import java.io.PrintWriter;
import aa.runquene.bean.OrderedArrayProc;
import aa.runquene.impl.Runqueue;

/**
 * Implementation of the Runqueue interface using an Ordered Array.
 *
 * Your task is to complete the implementation of this class.
 * You may add methods and attributes, but ensure your modified class compiles and runs.
 *
 * @author Sajal Halder, Minyi Li, Jeffrey Chan, Chih-Hsuan Lee<s3714761>, Yiheng Liu<s3673551>
 */
public class OrderedArrayRQ implements Runqueue {

	private final int DEFAULT_LENGTH = 10;
	private OrderedArrayProc[] array;
	private int size = 0;
	
	
    /**
     * Constructs empty queue
     */
    public OrderedArrayRQ() {
        // Implement Me
        array = new OrderedArrayProc[DEFAULT_LENGTH];
    }  // end of OrderedArrayRQ()


    @Override
    public void enqueue(String procLabel, int vt) {
        // Implement me
    	// find the proper position for the input
    	if(array[0] == null){
			array[0] = new OrderedArrayProc(procLabel, vt);
		}
    	else if(array[array.length-1] != null) {
    		OrderedArrayProc[] tempQueneArrays = new OrderedArrayProc[array.length + DEFAULT_LENGTH];
    		System.arraycopy(array, 0, tempQueneArrays, 0, array.length);
    		array = tempQueneArrays;
    		enqueue(procLabel, vt);
    	} else {
        		for(int j = 0; j < array.length-1; j++) {
        			if(array[j] == null) {
        				array[j] = new OrderedArrayProc(procLabel, vt);
        				break;
					}
					else if(procLabel.equals(array[j].getProcLabel())){
						break;
					}
        		if(vt < array[j].getVt()) {
        			System.arraycopy(array, j, array, j+1, array.length-j-1);
        			array[j] = new OrderedArrayProc(procLabel, vt);
        			break;
        		}
        	}
    	}
    	size++;
    	
    } // end of enqueue()


    @Override
    public String dequeue() {
		// Implement me
    	OrderedArrayProc[] tempQueneArray = new OrderedArrayProc[array.length];
		
		for (int j = 1 ; j < array.length ; j++) {
			
			if (array[0] == null){
				return"";
			}
			tempQueneArray[j-1] = array[j];
		}
		
		String del = array[0].getProcLabel();
		array = tempQueneArray;
		
		size--;
        return del; // placeholder,modify this
    } // end of dequeue()


    @Override
    public boolean findProcess(String procLabel){
        // Implement me
    	for(int j = 0; j < array.length-1; j++) {
			if(array[j] == null){
				return false;
			}
			if(procLabel.equals(array[j].getProcLabel())){
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
    		if(procLabel.equals(array[j].getProcLabel())) {
				System.arraycopy(array, j+1, array, j, array.length-j-1);
				remove = true;
			}
    	}
    	size--;
    	return remove;
    } // end of removeProcess()


    @Override
    public int precedingProcessTime(String procLabel) {
        // Implement me
		int precedingTime = 0;
		Boolean findProcess1 = false;
		
    	for(int j = 0; j < array.length-1; j++) {
			if(array[j] == null){
				continue;
			}
    		if(procLabel.equals(array[j].getProcLabel())) {
				findProcess1 = true;
				break;
			}else{ 
				precedingTime += array[j].getVt();
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
		int Time = 0;
		int beforeTime = 0;
		Boolean findProcess2 = false;
		for(int j = 0; j < array.length; j++) {
			if(array[j] == null){
				break;
			}if(procLabel.equals(array[j].getProcLabel())) {
				findProcess2 = true;
				beforeTime =j+1;	
				break;
			}
		}
		if (findProcess2){
			for(int a=beforeTime; a <array.length; a++){	
				if(array[a]==null){
					break;
				}else{
					Time += array[a].getVt();
				}
			}
		}
		if(!findProcess2){
			return -1;
		}
    	return Time;
    } // end of SucceedingProcessTime()


    @Override
    public void printAllProcesses(PrintWriter os) {
        //Implement me
		
		os.println(this.toString());
    } // end of printAllProcesses()
    
    public String toString() {
    	StringBuffer sb = new StringBuffer();
		for(int i = 0; i < array.length-1; i++) {
    		if(array[i] == null){
				break;
			}else{
			sb.append(array[i].getProcLabel()).append(" ");
			}
    	}
		return sb.toString();
    }
    
    public int getSize() {
    	return size;
    }

} // end of class OrderedArrayRQ

