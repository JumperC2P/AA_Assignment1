package aa.runquene.bean;

/**
 * @author Chih-Hsuan Lee <s3714761>
 *
 */
public class LinkedListProc extends Proc {
	
	private LinkedListProc nextNode;
	private LinkedListProc prevNode;
	
	public LinkedListProc(String procLabel, int vt) {
		this(procLabel, vt, null, null);
	}
	
	public LinkedListProc(String procLabel, int vt, LinkedListProc prevNode, LinkedListProc nextNode) {
		super.setProcLabel(procLabel);
		super.setVt(vt);
		this.setPrevNode(prevNode);
		this.setNextNode(nextNode);
	}

	public LinkedListProc getNextNode() {
		return nextNode;
	}

	public void setNextNode(LinkedListProc nextNode) {
		this.nextNode = nextNode;
	}

	public LinkedListProc getPrevNode() {
		return prevNode;
	}

	public void setPrevNode(LinkedListProc prevNode) {
		this.prevNode = prevNode;
	}

}
