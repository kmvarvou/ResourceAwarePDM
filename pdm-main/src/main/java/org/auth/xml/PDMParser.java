package org.auth.xml;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.auth.model.DataElement;
import org.auth.model.Operation;
import org.auth.model.PDM;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.auth.model.Resource;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PDMParser {
    public PDM parse(String filePath) throws PDMParserException, ParserConfigurationException, IOException, SAXException {
        // create a file instance from given path
        File file = new File(filePath);

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(file);  
        doc.getDocumentElement().normalize();


        // if the PDM has no name, then assume that input file is malformed
        if (doc.getElementsByTagName("Name").getLength() != 1) {
            throw new PDMParserException("Input PDM must have a name.");
        }
        
        

        // get PDM name
        String name = doc.getElementsByTagName("Name").item(0).getTextContent();

        // first get the list of all data elements
        NodeList nodeList = doc.getElementsByTagName("DataElement");

        List<DataElement> dataElements = new ArrayList<>();
        HashMap<String, Integer> elementMap = new HashMap<>(); // construct a map for easier access
        for (int i = 0; i < nodeList.getLength(); i++) {
            String key = nodeList.item(i).getAttributes().getNamedItem("DataElementID").getTextContent();
            dataElements.add(new DataElement(i + 1, key));
            elementMap.put(key, i + 1);
        }

        // get the list of all resources
        List<Resource> resources = new ArrayList<>();
        HashMap<Integer,Resource> resources_id = new HashMap<>();
        nodeList = doc.getElementsByTagName("Resource");
        
        for(int i=0; i < nodeList.getLength(); i++) // for each resource
        {
            Node node = nodeList.item(i);
            String resourceName = node.getAttributes().getNamedItem("ResourceID").getTextContent();
            List<Operation> responsible = new ArrayList<>();
            
            int capacity =0;
            NodeList childNodes = node.getChildNodes();
            for (int j = 0; j < childNodes.getLength(); j++) {
                Node child = childNodes.item(j);
                switch (child.getNodeName()) {
                 case "Capacity":
                        capacity = Integer.parseInt(child.getTextContent());
                        break;
                }
            }
            Resource resource = new Resource(i+1,resourceName,capacity);
            resources.add(resource);
            resources_id.put(i+1, resource);
            
            
            
        }
         // get the list of all operations
        nodeList = doc.getElementsByTagName("Operation");
        
        List<Operation> operations = new ArrayList<>();

        for (int i = 0; i < nodeList.getLength(); i++) { // for each operation
            // node operation
            Node node = nodeList.item(i);

            String operationName = node.getAttributes().getNamedItem("OperationID").getTextContent();

            List<DataElement> input = new ArrayList<>();
            DataElement output = null; // output must not be null by the end of parsing

            double time = 0.0;
            double cost = 0.0;
            double probability = 0.0;
            Resource resource = null;
            // iterate child nodes
            NodeList childNodes = node.getChildNodes();
            List<Resource> resources_op = new ArrayList<>();
            HashMap<Resource,Double> cost_resource = new HashMap<>();
            HashMap<Resource,Double> time_resource = new HashMap<>();
            for (int j = 0; j < childNodes.getLength(); j++) {
                Node child = childNodes.item(j);
                // Since each child node corresponds to a specific operation attribute,
                // it is easier to use a switch statement to extract values
                switch (child.getNodeName()) {
                    case "Input":
                        input = this.getDataElements(child, elementMap);
                        break;
                    case "Output":
                        List<DataElement> tmp = this.getDataElements(child, elementMap);
                        if (tmp.size() > 0) {
                            output = tmp.get(0);
                        }
                        break;
                    case "Assignment":
                        resources_op = getResource(child, resources);
                        resource = resources.get(0);
                        break;
                    case "Cost":
                        cost_resource = this.getCost(child, resources_op);
                        break;
                    case "Time":
                        time_resource = this.getTime(child, resources_op);
                        break;
                    case "Probability":
                        probability = Double.parseDouble(child.getTextContent());
                        break;
                    default:
                        break;
                }
            }

            // if an operation has no output, then assume that input file is malformed
            if (output == null) {
                throw new PDMParserException("Operation " + operationName + " has no output.");
            }
            //System.out.println(resources.size());
            Operation op = new Operation(i + 1, operationName, input, output, time, cost, probability, resource, resources_op, cost_resource, time_resource);
            operations.add(op);
            for(Resource r : resources_op)
            {
                //System.out.println(operationName);
                //System.out.println(i+1);
                //r.addResponsibility(op);
            }
            
        }

        DataElement objective = null;

        // if the PDM has no objective, then assume that input file is malformed
        if (doc.getElementsByTagName("RootElementRef").getLength() != 1) {
            throw new PDMParserException("PDM " + name + " must have an objective.");
        }

        String key = doc.getElementsByTagName("RootElementRef").item(0).getTextContent();

        objective = new DataElement(elementMap.get(key), key);

        return new PDM(name, dataElements, operations, objective,resources);
    }

    private List<DataElement> getDataElements(Node node, HashMap<String, Integer> elementMap) {
        List<DataElement> dataElements = new ArrayList<>();

        for (int i = 0; i < node.getChildNodes().getLength(); i++) {
            Node tmp = node.getChildNodes().item(i);
            if (tmp.getNodeName().equals("DataElementRef")) {
                
                String key = node.getChildNodes().item(i).getTextContent();
                dataElements.add(new DataElement(elementMap.get(key), key));
            }
        }

        return dataElements;
    }
    
    
    
    
     private List<Resource> getResource(Node node, List<Resource> elementMap) {
        
         List<Resource> backup_resources = new ArrayList<>();
        
        for(int i = 0 ; i < node.getChildNodes().getLength(); i ++) 
        {
            Node tmp = node.getChildNodes().item(i);
            Resource resource = null;
            if (tmp.getNodeName().equals("ResourceRef")) {
               
                String resource_name = node.getChildNodes().item(i).getTextContent();
                for(Resource r : elementMap)
                {
                  
                   if(r.getName().equals(resource_name))
                   {
                       
                       resource = r;
                   }
                }
                backup_resources.add(resource);
                //resource.addResponsibility(op);
            }
            
           
            
        }

        return backup_resources;
    }
     
     
   
    
    
    private HashMap<String,Double> getCoefficient(Node node, List<Resource> elementMap) {
        
         HashMap<String,Double> coefficient = new HashMap<>();
        int j=0;
        for(int i = 0 ; i < node.getChildNodes().getLength(); i ++) 
        {
            Node tmp = node.getChildNodes().item(i);
            Resource resource = null;
               
            if (tmp.getNodeName().equals("Overhead")) {
               
                //String resource_name = node.getChildNodes().item(i).getTextContent();
                
                Double number = Double.parseDouble(node.getChildNodes().item(i).getTextContent());
                coefficient.put(elementMap.get(j).getName(), number);
                j++;
                //coefficient.put(resource, Double.NaN)
            }
            
           
            
        }

        return coefficient;
    }
    
    private HashMap<Resource,Double> getCost(Node node, List<Resource> elementMap) {
        
         HashMap<Resource,Double> coefficient = new HashMap<>();
        int j=0;
        for(int i = 0 ; i < node.getChildNodes().getLength(); i ++) 
        {
            Node tmp = node.getChildNodes().item(i);
            Resource resource = null;
               
            if (tmp.getNodeName().equals("Number")) {
               
                //String resource_name = node.getChildNodes().item(i).getTextContent();
                
                Double number = Double.parseDouble(node.getChildNodes().item(i).getTextContent());
                coefficient.put(elementMap.get(j), number);
                j++;
                //coefficient.put(resource, Double.NaN)
            }
            
           
            
        }

        return coefficient;
    }
    
    
    private HashMap<Resource,Double> getTime(Node node, List<Resource> elementMap) {
        
         HashMap<Resource,Double> coefficient = new HashMap<>();
        int j=0;
        for(int i = 0 ; i < node.getChildNodes().getLength(); i ++) 
        {
            Node tmp = node.getChildNodes().item(i);
            Resource resource = null;
               
            if (tmp.getNodeName().equals("Number")) {
               
                //String resource_name = node.getChildNodes().item(i).getTextContent();
                
                Double number = Double.parseDouble(node.getChildNodes().item(i).getTextContent());
                coefficient.put(elementMap.get(j), number);
                j++;
                //coefficient.put(resource, Double.NaN)
            }
            
           
            
        }

        return coefficient;
    }
    
    
    
    

}