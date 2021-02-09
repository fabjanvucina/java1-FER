package hr.fer.oprpp1.custom.scripting.nodes;

/**
 * Node subclass which represents a document model.
 * 
 * @author fabjanvucina
 */
public class DocumentNode extends Node {

	@Override
	public String toString() {		
		StringBuilder sb = new StringBuilder();
		visitChildren(this, sb);
		
		return sb.toString();
	}
	
	
	/**
	 * Private method which visits the parent's children and appends their values to the passed <code>StringBuilder</code> object.
	 * @param parent
	 * @param sb <code>StringBuilder</code> object
	 */
	private void visitChildren(Node parent, StringBuilder sb) {
		//default condition of recursive method
		if(parent.numberOfChildren() == 0) return;
		
		//iterate over child nodes
		for(int i=0, n=parent.numberOfChildren(); i<n; i++) {
			
			//special case for TextNodes, we need to append \ before {
			if(parent.getChild(i) instanceof TextNode) {
				String value = parent.getChild(i).getText();
				char[] valueArray = value.toCharArray();
				
				for(int j=0, m=valueArray.length; j<m; j++) {
					if(valueArray[j] == '{') {
						sb.append('\\');
					}
					sb.append(valueArray[j]);
				}
			}
			
			else {
				sb.append(parent.getChild(i).getText());
				
				//don't add " " at the end, it would create an extra TextNode
				if(parent.getChild(i).numberOfChildren() != 0) {
					sb.append(" ");
				}
				
				visitChildren(parent.getChild(i), sb);
				if(parent.getChild(i) instanceof ForLoopNode) {
					sb.append("{$ END $}");
				}
			}
		}
	}


	@Override
	public String getText() {
		return "";
	}
}
