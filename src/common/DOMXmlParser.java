package common;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.gargoylesoftware.htmlunit.javascript.host.dom.DOMParser;

import static org.w3c.dom.Node.ATTRIBUTE_NODE;
import static org.w3c.dom.Node.CDATA_SECTION_NODE;
import static org.w3c.dom.Node.COMMENT_NODE;
import static org.w3c.dom.Node.DOCUMENT_TYPE_NODE;
import static org.w3c.dom.Node.ELEMENT_NODE;
import static org.w3c.dom.Node.ENTITY_NODE;
import static org.w3c.dom.Node.ENTITY_REFERENCE_NODE;
import static org.w3c.dom.Node.NOTATION_NODE;
import static org.w3c.dom.Node.PROCESSING_INSTRUCTION_NODE;
import static org.w3c.dom.Node.TEXT_NODE;

public class DOMXmlParser {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	public Document getDocument(String xmlDoc){
		
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = null;
		Document doc = null;
		
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			
			doc = dBuilder.parse(new ByteArrayInputStream(xmlDoc.getBytes()));


		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return doc;
		
	}

	public void testMethod(String xmlDoc) {
		Document doc = getDocument(xmlDoc);
	}
	
	
	public NodeList getNodeListFromDocument(String nodeName,Document doc){
		return doc.getElementsByTagName(nodeName);
	}
	
	public Node getSubNode(String nodeName,Node parentNode){
		
		NodeList subNodes = parentNode.getChildNodes();
		Node nodeRequired = null;
		for(int i =0 ;i<subNodes.getLength();i++){
			Node subNode = subNodes.item(i);
			if(getNodeName(subNode).equalsIgnoreCase(nodeName)){
				nodeRequired = subNode;
				break;
			}
		}
		return nodeRequired;
	}
	
	public String getNodeValue(Node node){
		return node.getTextContent();
	}
	
	public String getNodeName(Node node){
		return node.getNodeName();
	}
	
	public Node getSubNode(String nodeName,String nodeValue,Node parentNode){
			
			NodeList subNodes = parentNode.getChildNodes();
			Node nodeRequired = null;
			for(int i =0 ;i<subNodes.getLength();i++){
				Node subNode = subNodes.item(i);
				if(getNodeName(subNode).equalsIgnoreCase(nodeName) && getNodeValue(subNode).equalsIgnoreCase(nodeValue)){
					nodeRequired = subNode;
					break;
				}
			}
			return nodeRequired;
		}
	
	public Node getParentNodeFromSetOfNodeList(String nodeName,NodeList parentNodeList){
		Node nodeRequired = null;
		for(int j =0;j<=parentNodeList.getLength();j++){
			Node parentNode = parentNodeList.item(j);
			NodeList subNodes = parentNode.getChildNodes();
			
			for(int i =0 ;i<subNodes.getLength();i++){
				Node subNode = subNodes.item(i);
				if(getNodeName(subNode).equalsIgnoreCase(nodeName)){
					nodeRequired = subNode;
					break;
				}
			}
			
			if(nodeRequired != null){
				break;
			}
		}
		
		return nodeRequired.getParentNode();
	}
	
	
	
	public Node getParentNodeFromSetOfNodeList(String nodeName,String nodeValue,NodeList parentNodeList){
		Node nodeRequired = null;
		for(int j =0;j<=parentNodeList.getLength();j++){
			Node parentNode = parentNodeList.item(j);
			System.out.println("Parent Nodes: "+parentNode);
			NodeList subNodes = parentNode.getChildNodes();
			System.out.println("Sub Nodes: "+subNodes);
			
			for(int i =0 ;i<subNodes.getLength();i++){
				Node subNode = subNodes.item(i);
				if(getNodeName(subNode).equalsIgnoreCase(nodeName) && getNodeValue(subNode).equalsIgnoreCase(nodeValue)){
					nodeRequired = subNode;
					break;
				}
			}
			if(nodeRequired != null){
				break;
			}
		}
		
		return nodeRequired.getParentNode();
	}
	
	public String nodeToString (Node node)
	{
		StringWriter writer = new StringWriter();
		try 
		{
			Transformer tf = TransformerFactory.newInstance().newTransformer();
			tf.transform(new DOMSource(node), new StreamResult(writer));
		} 
		catch (Exception e) { e.printStackTrace(); }
		
		return writer.toString();
	}
	
}