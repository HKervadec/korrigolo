package lu.uni.adtool.copypaste;

import java.util.LinkedList;

import javax.swing.JOptionPane;

import lu.uni.adtool.adtree.ADTNode;
import lu.uni.adtool.adtree.ADTreeNode;
import lu.uni.adtool.ui.ADTreeCanvas;

public class UndoExecutor 
{
	private LinkedList<ADTNode> savedTreesQueue = new LinkedList<ADTNode>();
	private final int QUEUEMEMORYLIMIT = 5000;
	private int currentQueueSize = 0;

	
	/**
	 * restores the tree in its previous state
	 * @param canvas
	 */
	public void undo(ADTreeCanvas canvas) 
	{
		if (canvas == null)
		{
			return;
		}
		if (this.savedTreesQueue.isEmpty())
		{
			JOptionPane.showMessageDialog(null, "Nothing to undo.");
			return;
		}
		canvas.getTree().createFromTerms(this.savedTreesQueue.pop());
		System.out.println("Last action undone.");		
	}
	
	/**
	 * save the current state of the tree
	 * @param root
	 */
	public void saveTreeState(ADTreeNode root)
	{
		int rootSize = root.getTerm().getSize();
		while ((this.currentQueueSize + rootSize > this.QUEUEMEMORYLIMIT) && !this.savedTreesQueue.isEmpty())
		{
			this.currentQueueSize -= this.savedTreesQueue.removeLast().getSize();
		}
		this.savedTreesQueue.push(root.getTerm());
		this.currentQueueSize += root.getTerm().getSize();
	}
}
