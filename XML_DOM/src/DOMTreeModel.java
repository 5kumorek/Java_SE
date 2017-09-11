import javax.swing.event.*; 
import javax.swing.tree.*; 
import org.w3c.dom.*; 

public class DOMTreeModel  implements TreeModel{
	private Document doc;
	
	public DOMTreeModel(Document doc)
	{
		this.doc=doc;
	}
	public Object getRoot()
	{
		return doc.getDocumentElement();
	}
	public int getChildCount(Object parent)
	{
		Node node = (Node) parent;
		NodeList list = node.getChildNodes();
		return list.getLength();
	}
	public Object getChild(Object parent, int index)
	{
		Node node = (Node) parent;
		NodeList list = node.getChildNodes();
		return list.item(index);
	}
	public int getIndexOfChild(Object parent, Object child)
	{
		Node node = (Node) parent;
		NodeList list = node.getChildNodes();
		for(int i=0;i<list.getLength();i++)
		{
			if(getChild(node,i)==child) return i;
		}
		return -1;
	}
	public boolean isLeaf(Object node)
	{
		return getChildCount(node)==0;
	}
	 public void valueForPathChanged(TreePath path, Object newValue) 
	 {
		 
	 }
	@Override
	public void addTreeModelListener(TreeModelListener l) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void removeTreeModelListener(TreeModelListener l) {
		// TODO Auto-generated method stub
		
	}
	 
	
}
