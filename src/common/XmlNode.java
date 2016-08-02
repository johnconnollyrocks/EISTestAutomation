package common;

import java.io.ByteArrayInputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import common.exception.MetadataException;

public final class XmlNode implements Cloneable {
	
	private String nodeName;
	private String nodeValue;
	private boolean hasChildren;
	//private String nodeCode;
	private List<XmlNode> listOfChildren;
	private Node node;
	private String nodeXmlString;
	
	//================
	//  CONSTRUCTORS
	//================
	
	public XmlNode (Node node)
	{
		this.node = node;
		nodeName = node.getNodeName();
		listOfChildren = convertNodeList(node.getChildNodes());
		if(listOfChildren.isEmpty()) {
			nodeValue = node.getTextContent();
			hasChildren = false;
		} 
		else {
			nodeValue = null;
			hasChildren = true;
		}
		nodeXmlString = nodeToCode(node);
	}
	
	public XmlNode (String xmlNodeString) 
	{
		this (getDocument(xmlNodeString));
	}
	
	
	//===========================
	// PUBLIC RETURN COMMON TYPES
	//============================
	
	public String getNodeName() {
		return this.nodeName;
	}
	
	public String getNodeValue() {
		return this.nodeValue;
	}
	
	public boolean hasChildren() {
		return this.hasChildren;
	}
	
	public List<XmlNode> getChildren() {
		return this.listOfChildren;
	}
	
	public String getXmlString() {
		return nodeXmlString;
	}
	
	public String getNodeCode() {
		return this.getXmlString();
	}
	
	public List<String> getChildNames() {
		List<String> listOfChildNames = new ArrayList<>();
		for (XmlNode eachChild : listOfChildren) {
			listOfChildNames.add(eachChild.getNodeName());
		}
		return listOfChildNames;
	}
	
	public String getChildNode (String childNodeName) {
		return this.getChildXmlNode(childNodeName).getNodeCode();
	}
	
	public String getChildNodeValue (String childNodeName) {
		return this.getChildXmlNode(childNodeName).getNodeValue();
	}
	
	public List<String> getSubNodes (String subNodeName)
	{
		List<String> listOfSubNodeCodes = new ArrayList<>();
		List<XmlNode> listOfSubNodes = this.getSubXmlNodes(subNodeName);
		for (XmlNode eachChild : listOfSubNodes) {
			listOfSubNodeCodes.add(eachChild.getNodeCode());
		}
		return listOfSubNodeCodes;
	}

	
	
	//===========================
	//  RETURN XMLNODES
	//============================
		
	public XmlNode getChildXmlNode (String childNodeName) {
		for (int i = 0 ; i < listOfChildren.size() ; i++) {
			if(listOfChildren.get(i).getNodeName().equalsIgnoreCase(childNodeName)) {return listOfChildren.get(i);}
		}
		return null;
	}
	
	public List<XmlNode> getSubXmlNodes (String subNodeName) {	
		return getSubXmlNodes (this, subNodeName);
	}	
	private List<XmlNode> getSubXmlNodes (XmlNode nodeItem, String subNodeName)
	{
		List<XmlNode> listOfSubNodes = new ArrayList<>();
		for (XmlNode aChild : nodeItem.getChildren()) {
			if(aChild.getNodeName().equalsIgnoreCase(subNodeName)) {
				listOfSubNodes.add(aChild);}}	
		
		for (XmlNode aChild : nodeItem.getChildren()) {
			if(listOfSubNodes.isEmpty()) {
				listOfSubNodes = getSubXmlNodes (aChild, subNodeName);}}
		
		return listOfSubNodes;
	}

	//===========
	// PRIVATE
	//===========
	private static Document getDocument(String xmlDoc)
	{
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = null;
		Document d = null;
		
		try {
			dBuilder = dbFactory.newDocumentBuilder();		
			d = dBuilder.parse(new ByteArrayInputStream(xmlDoc.getBytes()));
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return d;
	}
	
	private List<XmlNode> convertNodeList (NodeList nodeList) {
		List<XmlNode> listOfNodes = new ArrayList<>();
		for (int i = 0 ; i < nodeList.getLength() ; i++) {
			if(nodeList.item(i).getNodeName().equalsIgnoreCase("#text")) {continue;}
			listOfNodes.add(new XmlNode(nodeList.item(i)));
		}
		return listOfNodes;
	}
	
	private String nodeToCode (Node node)
	{
		StringWriter writer = new StringWriter();
		try {
			Transformer tf = TransformerFactory.newInstance().newTransformer();
			tf.transform(new DOMSource(node), new StreamResult(writer));
		} catch (Exception e) { e.printStackTrace(); }
		
		return writer.toString();
	}
}