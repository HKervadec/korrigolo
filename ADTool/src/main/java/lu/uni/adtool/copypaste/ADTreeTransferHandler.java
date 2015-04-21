package lu.uni.adtool.copypaste;

import java.util.Vector;

import lu.uni.adtool.adtree.ADTreeNode;
import lu.uni.adtool.adtree.Node;
import lu.uni.adtool.ui.ADTreeCanvas;

public class ADTreeTransferHandler
{
	private int counter = 1;
	private ADTreeNode clonedNode;
	private Vector<Node> children;
	

	public void cut(ADTreeNode adt)
	{
		System.out.println("cut : "+adt.getLabel());
	}
	
	public void copy(ADTreeNode adt)
	{	
		this.clonedNode = new ADTreeNode(adt.getType(), adt.getRefinmentType(), adt.getLabel());
		System.out.println("copied");
	}
	

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
												this.clonedNode.getLabel()+counter++);
		canvas.addChild(focusedNode, nodeToPaste);
		System.out.println("pasted : "+clonedNode.getLabel());
	}

}



