import java.io.*;
import java.util.ArrayList;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

/**
 * in This class I will handle whatever is related to files
 * read the net from the xml files
 * read the net and queries from text file
 * write the answers to a text files
 */
public class fileHandler {

    /**
     * @param filename the text file with the net name and queries
     * @return the handler that will handle all the queries
     */
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
        return new queriesHandler(xmlFile,queries);

    }

    /**
     * This function will read all the rows from the xml files and sort them correctly
     * @param filename the xml file name
     * @return a list of the information we need to build our network
     */
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

    /**
     * this function will write all the responses to the queries to a text file
     * @param response the list of responses
     */
    public static void writeToTxt(ArrayList<String> response)
    {
        try
        {
            FileWriter writer = new FileWriter("output.txt");
            for (String s : response) {
                writer.write(s + "\n");
            }
            writer.close();
        }
        catch (Exception e)
        {
            System.out.println("error");
            e.printStackTrace();
        }
    }
}
