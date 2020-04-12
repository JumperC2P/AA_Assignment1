package aa.runquene;

import java.io.PrintWriter;
import java.lang.String;

import aa.runquene.bean.LinkedListProc;
import aa.runquene.impl.Runqueue;

/**
 * Implementation of the run queue interface using an Ordered Link List.
 *
 * Your task is to complete the implementation of this class.
 * You may add methods and attributes, but ensure your modified class compiles and runs.
 *
 * @author Sajal Halder, Minyi Li, Jeffrey Chan, Chih-Hsuan Lee<s3714761>, Yiheng Liu<s3673551>
 */
public class OrderedLinkedListRQ implements Runqueue {
	
	private int size;
	
	LinkedListProc headNode;
	LinkedListProc tailNode;

    /**
     * Constructs empty linked list
     */
    public OrderedLinkedListRQ() {
    	headNode = null;
    	tailNode = null;
    	size = 0;
    }  // end of OrderedLinkedList()


    @Override
    public void enqueue(String procLabel, int vt) {
    	
    	LinkedListProc newNode = new LinkedListProc(procLabel, vt);
    
    	// If headNode is null, the new node is headnode
    	if (headNode == null) {
    		headNode = newNode;
    		tailNode = newNode;
    		size++;
    		return;
    	}
    	
    	if (findProcess(procLabel)) {
    		return;
    	}
    	
    	// Recursive function to insert node with ascending order
    	insert(headNode, newNode);
    	
    	// increase the size
    	size++;

    } // end of enqueue()

    /**
     * Recursive: check the relationship of current node and new node
     * @param currentNode
     * @param newNode
     */
    private void insert(LinkedListProc currentNode, LinkedListProc newNode) {
		
    	// If the vt of new node is greater than that of current node,
    	// 		if yes: Check if the next node of current node is exist, 
    	//					if no, it means current node is tail
    	// 					if yes, use the next node of current node to call insert function to check again
    	//		if no: it means the new node should be the previous one of current node
    	if (currentNode.getVt() <= newNode.getVt()) {
    		LinkedListProc nextNode =  currentNode.getNextNode();
    		if ( nextNode != null) {
    			insert(nextNode, newNode);
    		}else {
    			switchNodes(currentNode, newNode, true);
    		}
    	}else {
    		switchNodes(currentNode, newNode, false);
    	}
	}

    /**
     * Switch the setting of nodes (Rearrange the order of nodes)
     * @param currentNode
     * @param newNode
     * @param isLast
     */
    private void switchNodes(LinkedListProc currentNode, LinkedListProc newNode, Boolean isLast) {
		
    	// Check if it comes to the tail or not
		if (isLast) {
			newNode.setNextNode(null);
			newNode.setPrevNode(currentNode);
			currentNode.setNextNode(newNode);
			tailNode = currentNode;
		}else {
			newNode.setNextNode(currentNode);
			newNode.setPrevNode(currentNode.getPrevNode());
			currentNode.setPrevNode(newNode);
		}
		
		// if the previous node of new node is null, it means that new node is head node.
    	if (newNode.getPrevNode() == null) {
    		headNode = newNode;
    	}else {
    		newNode.getPrevNode().setNextNode(newNode);
    	}
    }

    @Override
    public String dequeue() {
    	
    	if (headNode == null)
    		return "";
    	
    	//record to label of nodes which will be deleted.
    	String deleteProcLabel = headNode.getProcLabel();
    	
    	// head node changes.
    	headNode = headNode.getNextNode();
    	if (headNode != null)
    		headNode.setPrevNode(null);
    	// reduce size
    	size--;
    	
        return deleteProcLabel; // placeholder, modify this
    } // end of dequeue()


    @Override
    public boolean findProcess(String procLabel) {
    	// Find the process with the procLabel 
        return find(headNode, procLabel) != null; // placeholder, modify this
    } // end of findProcess()

    
    /**
     * Recursive: check the procLabel of current node is the same with the procLabel from input
     * @param currentNode
     * @param procLabel
     * @return the process if it is found.
     */
    private LinkedListProc find(LinkedListProc currentNode, String procLabel) {
		if (procLabel.equals(currentNode.getProcLabel())) {
			return currentNode;
		}else {
			if (currentNode.getNextNode() != null) {
				return find(currentNode.getNextNode(), procLabel);
			}
		}
		return null;
	}


	@Override
    public boolean removeProcess(String procLabel) {
        
		LinkedListProc node = find(headNode, procLabel);
		
		if (node != null) {
			node.getPrevNode().setNextNode(node.getNextNode());
			if (node.getNextNode() != null) {
				node.getNextNode().setPrevNode(node.getPrevNode());
			}
			return true;
		}else {
			return false; 
		}

    } // End of removeProcess()


    @Override
    public int precedingProcessTime(String procLabel) {
    	
    	int sum = 0;
    	
    	String resultString = sumVruntime(sum, headNode, procLabel);
    	
    	String[] resultAray = resultString.split(",");
    	Boolean result = Boolean.valueOf(resultAray[0]);
    	
    	if (result) {
    		return Integer.valueOf(resultAray[1]);
    	}

        return -1; // placeholder, modify this
    } // end of precedingProcessTime()


    private String sumVruntime(int sum, LinkedListProc currentNode, String procLabel) {
		
    	if (procLabel.equals(currentNode.getProcLabel())) {
    		return true + "," + sum;
    	}else {
    		if (currentNode.getNextNode() != null) {
    			sum += currentNode.getVt();
    			return sumVruntime(sum, currentNode.getNextNode(), procLabel);
    		}
    	}
		return false + "," + sum;
	}


	@Override
    public int succeedingProcessTime(String procLabel) {
        
		LinkedListProc node = find(headNode, procLabel);
		
		if (node == null) {
			return -1;
		}
		
		return sumVruntime(0, node.getNextNode());

    } // end of precedingProcessTime()


    private int sumVruntime(int sum, LinkedListProc currentNode) {
    	if (currentNode != null) {
    		sum += currentNode.getVt();
    		if (currentNode.getNextNode() == null) {
    			return sum;
    		}
    		return sumVruntime(sum, currentNode.getNextNode());
    	}
    	return sum;
	}


	@Override
    public void printAllProcesses(PrintWriter os) {
    	
		os.println(this.toString());

    } // end of printAllProcess()
    
	
	public String toString() {
		return getLabel(new StringBuffer(), headNode).toString();
	}
	
    
    private StringBuffer getLabel(StringBuffer sb, LinkedListProc node) {
		sb.append(node.getProcLabel()).append(" ");
		if (node.getNextNode() == null) {
			return sb;
		}else {
			getLabel(sb, node.getNextNode());
		}
		return sb;
	}

	public int getSize() {
		return size;
	}

} // end of class OrderedLinkedListRQ
