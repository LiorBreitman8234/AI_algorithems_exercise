import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.*;
import org.xml.sax.SAXException;


public class ReadFiles {

    public static queriesHandler readTXT(String filename)
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
        queriesHandler input = new queriesHandler(xmlFile,queries);
        return input;

    }

    public static ArrayList<nodeBuilderHelper> readXML(String filename)
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        ArrayList<nodeBuilderHelper> nodes_preview = new ArrayList<nodeBuilderHelper>();
        try {
            factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document doc = builder.parse(filename);
            doc.getDocumentElement().normalize();

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
                    String[] outcomes = new String[element_variable.getElementsByTagName("OUTCOME").getLength()];
                    String values = element_definition.getElementsByTagName("TABLE").item(0).getTextContent();
                    for (int j = 0; j < parents.length; j++)
                    {
                        String parent = element_definition.getElementsByTagName("GIVEN").item(j).getTextContent();
                        parents[j] = parent;
                    }
                    for(int j =0; j < outcomes.length;j++)
                    {
                        String outcome = element_variable.getElementsByTagName("OUTCOME").item(j).getTextContent();
                        outcomes[j] = outcome;
                    }
                    nodeBuilderHelper helper = new nodeBuilderHelper(eventName, parents, outcomes, values);
                    nodes_preview.add(helper);

                }
            }

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return nodes_preview;
    }
}
