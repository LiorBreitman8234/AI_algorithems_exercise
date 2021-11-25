import java.util.ArrayList;

/**
 * This class will hold all the nodes in my network
 * and from here I also build the initial cpt of every node
 */
public class BayesianNetwork {
    ArrayList<EventNode> nodesInNetwork;

    //Empty constructor
    public BayesianNetwork()
    {
        this.nodesInNetwork = new ArrayList<EventNode>();
    }

    /**
     * In this function I build all the nodes for the network and keep them
     * and also call the function to build the cps of all the nodes
     * @param fileName the XML file from which to get all the information I need to build the network
     */
    public void buildNetwork(String fileName)
    {
        ArrayList<nodeBuilderHelper> helpers = fileHandler.readXML(fileName);
        for (nodeBuilderHelper nodeBuilderHelper : helpers) {
            String name = nodeBuilderHelper.getName();
            EventNode event = new EventNode(name);
            for (int j = 0; j < nodeBuilderHelper.getOutcomes().length; j++) {
                event.addOutcome(nodeBuilderHelper.getOutcomes()[j]);
            }
            event.setValues(nodeBuilderHelper.values);
            nodesInNetwork.add(event);
        }
        for (nodeBuilderHelper helper : helpers) {
            String name = helper.getName();
            int indexCurr = containsAndIndex(name);
            EventNode event = nodesInNetwork.get(indexCurr);
            String[] parents = helper.getGiven();
            for (String parent : parents) {
                int index = containsAndIndex(parent);
                if (index != -1) {
                    event.addParent(nodesInNetwork.get(index));
                    nodesInNetwork.get(index).addChild(event);
                }
            }
        }
        BuildCPTForNetwork();
    }

    /**
     * This function goes on every node and builds its cpt
     */
    public void BuildCPTForNetwork()
    {
        for (EventNode eventNode : this.nodesInNetwork) {
            eventNode.BuildCPT();
        }
    }


    /**
     * This function recursively checks if the current node is a descendent of the toCheck node
     * @param current The node that I check if he is the descendant
     * @param toCheck The node that is the ancestor I am checking on
     * @return If the current node is a Descendant, return true, else return false
     */
    public boolean isDescendant(String current, String toCheck)
    {
        if(current.equals(toCheck))
        {
            return true;
        }
        EventNode thisNode = this.nodesInNetwork.get(this.containsAndIndex(current));
        if(thisNode.parentContain(toCheck))
        {
            return true;
        }
        if(thisNode.getParents().size() == 0)
        {
            return false;
        }
        boolean flag;
        for(int i =0; i < thisNode.getParents().size();i++)
        {
            String nextNode = thisNode.getParents().get(i).getName();
            flag = this.isDescendant(nextNode,toCheck);
            if(flag)
            {
                return  true;
            }
        }
        return false;

    }
    /**
     * @param eventName The name of the node I want to get back
     * @return  The index of the node I wanted
     */
    public int containsAndIndex(String eventName)
    {
        for(int i =0; i < nodesInNetwork.size();i++)
        {
            if(nodesInNetwork.get(i).getName().equals(eventName)) {
                return i;
            }
        }
        return -1;
    }

}
