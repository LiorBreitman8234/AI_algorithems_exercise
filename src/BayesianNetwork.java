import java.util.ArrayList;

public class BayesianNetwork {
    ArrayList<EventNode> nodesInNetwork;

    public BayesianNetwork()
    {
        this.nodesInNetwork = new ArrayList<EventNode>();
    }

    public void buildNetwork(String fileName)
    {
        ArrayList<nodeBuilderHelper> helpers = ReadFiles.readXML(fileName);
        for(int i =0; i < helpers.size(); i++)
        {
            String name = helpers.get(i).getName();
            EventNode event =new EventNode(name);
            nodesInNetwork.add(event);
        }
        for(int i =0; i < helpers.size();i++)
        {
            String name = helpers.get(i).getName();
            int indexCurr = containsAndIndex(name);
            EventNode event = nodesInNetwork.get(indexCurr);
            String[] parents = helpers.get(i).getGiven();
            for(int j = 0; j < parents.length;j++)
            {
                int index = containsAndIndex(parents[j]);
                if(index != -1)
                {
                    event.addParent(nodesInNetwork.get(index));
                    nodesInNetwork.get(index).addChild(event);
                }
            }
        }
    }

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
