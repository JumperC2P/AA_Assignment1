package aa.runquene;


import java.io.PrintWriter;
import aa.runquene.bean.OrderArrayProc;
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

	private final int SIZE = 10;
	private OrderArrayProc[] array;
	
	
    /**
     * Constructs empty queue
     */
    public OrderedArrayRQ() {
        // Implement Me
        array = new OrderedArrayProc[SIZE];
    }  // end of OrderedArrayRQ()


    @Override
    public void enqueue(String procLabel, int vt) {
        // Implement me
    	// find the proper position for the input
    	if(array[0] == null){
			array[0] = new OrderedArrayProc(procLabel, vt);
		}
    	else if(array[array.length-1] != null) {
    		OrderedArrayProc[] tempQueneArrays = new OrderedArrayProc[array.length + SIZE];
    		System.arraycopy(array, 0, tempQueneArrays, array.length-1, array.length-1);
    		array = tempQueneArrays;
    		enqueue(procLabel, vt);
    	} else {
        		for(int j = 0; j < array.length-1; j++) {
        			if(array[j] == null) {
        				array[j] = new OrderedArrayProc(procLabel, vt);
        				return;
					}
					else if(procLabel.equals(array[j].getprocLabel())){
						break;
					}
        		if(vt < array[j].vt) {
        			System.arraycopy(array, j, array, j+1, array.length-j-1);
        			array[j] = new OrderedArrayProc(procLabel, vt);
        			return;
        		}
        	}
    	}
    	
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
		
		String del = array[0].getprocLabel();
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
			if(procLabel.equals(array[j].getprocLabel())){
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
    		if(procLabel.equals(array[j].getprocLabel())) {
				System.arraycopy(array, j+1, array, j, array.length-j-1);
				remove = true;
			}
    	}
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
    		if(procLabel.equals(array[j].getprocLabel())) {
				findProcess1 = true;
				break;
			}else{ 
				precedingTime += array[j].vt;
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
		int beforeTime = 0;
		Boolean findProcess2 = false;
		for(int j = 0; j < array.length-1; j++) {
			if(array[j] == null){
				break;
			}if(procLabel.equals(array[j].getprocLabel())) {
				findProcess2 = true;
				beforeTime =j+1;	
				break;
			}
		}
		if (findProcess2){
			for(int a=beforeTime; a <array.length-1; a++){	
				if(array[a]==null){
					break;
				}else{
					precedingTime += array[a].vt;
				}
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
			sb.append(array[i].getprocLabel()).append(" ");
			}
    	}
		os.println(sb.toString());
    } // end of printAllProcesses()

} // end of class OrderedArrayRQ

