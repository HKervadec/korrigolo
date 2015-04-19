package lu.uni.adtool.copypaste;

import lu.uni.adtool.adtree.ADTNode;
import lu.uni.adtool.adtree.ADTreeNode;
import lu.uni.adtool.ui.ADTreeCanvas;

public class ADTreeTransferHandler
{
	private ADTreeNode savedNode;
	

	public void cut(ADTreeNode adt)
	{
		this.savedNode = new ADTreeNode();
		this.savedNode.setTerm(adt.getTerm());
		System.out.println("cut : "+this.savedNode.getTerm().getName());
	}
	
	public void copy(ADTreeNode adt)
	{	
		System.out.println("copied");
	}
	

	public void paste(ADTreeCanvas canvas) 
	{
		if (this.savedNode == null)
		{
			System.out.println("Nothing to paste !");
			return;
		}
		ADTreeNode focusedNode = canvas.getFocused();
		if (focusedNode == null)
		{
			return;
		}
		// not working : canvas.addChild(focusedNode,this.savedNode);
		System.out.println("pasted");
	}

}



