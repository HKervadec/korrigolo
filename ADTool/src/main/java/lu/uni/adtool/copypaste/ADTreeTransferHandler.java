package lu.uni.adtool.copypaste;

import java.util.Vector;

import javax.swing.JOptionPane;

import lu.uni.adtool.adtree.ADTNode;
import lu.uni.adtool.adtree.ADTreeNode;
import lu.uni.adtool.adtree.Node;
import lu.uni.adtool.ui.ADTreeCanvas;

public class ADTreeTransferHandler
{
	private ADTreeNode clonedNode;
	private final int MAXSIZE = 1000;
	
	/**
	 * returns the attribute clonedNode
	 */
	public ADTreeNode getClonedNode() {
		return clonedNode;
	}

	/**
	 * returns true if a node has been cut or copied
	 */
	public boolean hasClonedNode() {
		return (clonedNode != null);
	}
	
	/**
	 * Copy
	 * @param adt
	 */
	public void copy(ADTreeNode adt)
	{
		if (adt.getTerm().getSize() > this.MAXSIZE)
		{
			System.out.println("Subtree is too big to be copied!");
			return;
		}
		this.clonedNode = new ADTreeNode(adt.getType(), adt.getRefinmentType(), adt.getLabel());
		this.clonedNode.setTerm(this.copyTerm(adt.getTerm()));
	}
	
	/**
	 * subfunction for copy
	 * @param term
	 * @return
	 */
	private ADTNode copyTerm(ADTNode term)
	{
		ADTNode clonedTerm = new ADTNode(term.getId(),term.getType(),term.getName());
		Vector<Node> clonedChildren = new Vector<Node>();
		for(Node child : term.getChildren())
		{
				ADTNode castedChild = ((ADTNode) child);
				clonedChildren.add(this.copyTerm(castedChild));
		}
		clonedTerm.setChildren(clonedChildren);
		
		return clonedTerm;
	}

	/**
	 * Paste
	 * @param canvas
	 */
	public void paste(ADTreeCanvas canvas) 
	{
		ADTreeNode focusedNode = canvas.getFocused();
		if (focusedNode == null || clonedNode == null)
		{
			return;
		}
		
		if (focusedNode.getType() != this.clonedNode.getType())
		{
			JOptionPane.showMessageDialog(null, "You can't paste "+focusedNode.getType().toString()+" node "
					+ "as child of "+this.clonedNode.getType().toString()+" node.");
			return;
		}
		
		ADTreeNode nodeToPaste = new ADTreeNode(this.clonedNode.getType(),
												this.clonedNode.getRefinmentType(),
												this.clonedNode.getLabel());
		nodeToPaste.setTerm(this.copyTerm(clonedNode.getTerm()));
		
		ADTreeNode child = canvas.addChild(focusedNode);
		canvas.getTree().cloneTreeFromTerms(nodeToPaste.getTerm(), child);
		System.out.println("Pasted: "+clonedNode.getLabel());
	}

}



