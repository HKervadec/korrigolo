package lu.uni.adtool.copypaste;

import java.util.Vector;

import lu.uni.adtool.adtree.ADTNode;
import lu.uni.adtool.adtree.ADTreeNode;
import lu.uni.adtool.adtree.Node;
import lu.uni.adtool.ui.ADTreeCanvas;

public class ADTreeTransferHandler
{
	private ADTreeNode clonedNode;
	
	/**
	 * Cut
	 * @param adt
	 */
	public void cut(ADTreeNode adt)
	{
		System.out.println("cut : "+adt.getLabel());
	}
	
	/**
	 * Copy
	 * @param adt
	 */
	public void copy(ADTreeNode adt)
	{	
		this.clonedNode = new ADTreeNode(adt.getType(), adt.getRefinmentType(), adt.getLabel());
		this.clonedNode.setTerm(this.copyTerm(adt.getTerm()));
		
		System.out.println("copied : "+adt.getLabel());
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
		if (this.clonedNode == null)
		{
			System.out.println("Nothing to paste !");
			return;
		}
		ADTreeNode focusedNode = canvas.getFocused();
		if (focusedNode == null)
		{
			return;
		}
		
		if (focusedNode.getType() != this.clonedNode.getType())
		{
			System.out.println("You can't paste "+focusedNode.getType().toString()+" node "
					+ "as child of "+this.clonedNode.getType().toString()+" node.");
			return;
		}
		
		ADTreeNode nodeToPaste = new ADTreeNode(this.clonedNode.getType(),
												this.clonedNode.getRefinmentType(),
												this.clonedNode.getLabel());
		nodeToPaste.setTerm(this.copyTerm(clonedNode.getTerm()));
		System.out.println(nodeToPaste.getTerm().toString());
		
		ADTreeNode child = canvas.addChild(focusedNode);
		canvas.getTree().cloneTreeFromTerms(nodeToPaste.getTerm(), child);
		System.out.println("pasted : "+clonedNode.getLabel());
	}

}



