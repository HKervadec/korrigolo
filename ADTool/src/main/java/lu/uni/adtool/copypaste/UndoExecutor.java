package lu.uni.adtool.copypaste;

import java.util.Stack;

import lu.uni.adtool.adtree.ADTNode;
import lu.uni.adtool.adtree.ADTreeNode;
import lu.uni.adtool.ui.ADTreeCanvas;

public class UndoExecutor 
{
	private Stack<ADTNode> savedRootStack = new Stack<ADTNode>();
	private final int STACKLIMIT = 5;
	
	/**
	 * restore the tree in its previous state
	 * @param canvas
	 */
	public void undo(ADTreeCanvas canvas) 
	{
		if (this.savedRootStack.empty())
		{
			System.out.println("Nothing to undo!");
			return;
		}
		canvas.getTree().createFromTerms(this.savedRootStack.pop());
		System.out.println("Last action undone.");		
	}
	
	/**
	 * save the current state of the tree
	 * @param root
	 */
	public void saveTreeState(ADTreeNode root)
	{
		if (this.savedRootStack.size() > this.STACKLIMIT)
		{
			ADTNode lastNode = this.savedRootStack.pop();
			this.savedRootStack.clear();
			this.savedRootStack.push(lastNode);
		}
		this.savedRootStack.push(root.getTerm());
		System.out.println("Tree was saved.");
	}
}
