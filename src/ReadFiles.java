import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.print.Doc;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.*;
import org.xml.sax.SAXException;


public class ReadFiles {

    public static inputQueries readTXT(String filename)
    {
        ArrayList<String> queries = new ArrayList<String>();
        String xmlFile = "";
        try
        {
            boolean isNameOfFile = true;
            File file = new File(filename);
            FileReader reader = new FileReader(file);
            BufferedReader bReader = new BufferedReader(reader);
            String line;
            while((line = bReader.readLine())!=null)
            {
                if(isNameOfFile)
                {
                    xmlFile = line;
                    isNameOfFile = false;
                }
                else
                {
                    queries.add(line);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        inputQueries input = new inputQueries(xmlFile,queries);
        return input;

    }

    public static ArrayList<MyPair> readXML(String filename)
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        ArrayList<MyPair> nodes_preview = new ArrayList<MyPair>();
        try {
            factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document doc = builder.parse(filename);
            doc.getDocumentElement().normalize();

            System.out.println("Net name:" + doc.getDocumentElement().getNodeName());
            System.out.println("------");
            NodeList variable_list = doc.getElementsByTagName("VARIABLE");
            NodeList definition_list = doc.getElementsByTagName("DEFINITION");

            for (int i = 0; i < variable_list.getLength(); i++) {
                Node variable_node = variable_list.item(i);
                Node definition_node = definition_list.item(i);
                if (variable_node.getNodeType() == Node.ELEMENT_NODE && definition_node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element_variable = (Element) variable_node;
                    Element element_definition = (Element) definition_node;
                    String eventName = element_variable.getElementsByTagName("NAME").item(0).getTextContent();
                    String[] parents = new String[element_definition.getElementsByTagName("GIVEN").getLength()];
                    for (int j = 0; j < parents.length; j++)
                    {
                        String parent = element_definition.getElementsByTagName("GIVEN").item(j).getTextContent();
                        parents[j] = parent;
                    }
                    MyPair pair = new MyPair(eventName, parents);
                    //System.out.println("name: " + eventName);
                    //System.out.println("Parents: " + Arrays.toString(parents));
                    nodes_preview.add(pair);

                }
            }

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return nodes_preview;
    }
}
