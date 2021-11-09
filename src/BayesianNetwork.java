import java.util.ArrayList;

public class BayesianNetwork {
    ArrayList<EventNode> nodesInNetwork;

    public BayesianNetwork()
    {
        this.nodesInNetwork = new ArrayList<EventNode>();
    }

    public void buildNetwork(String fileName)
    {
        ArrayList<MyPair> pairs = ReadXML.readXML(fileName);
        for(int i =0; i < pairs.size();i++)
        {
            String name = pairs.get(i).getName();
            EventNode event = new EventNode(name);
            String[] parents = pairs.get(i).getGiven();
            for(int j = 0; j < parents.length;j++)
            {
                int index = containsAndIndex(parents[j]);
                if(index != -1)
                {
                    event.addParent(nodesInNetwork.get(index));
                    nodesInNetwork.get(index).addChild(event);
                }
            }
            nodesInNetwork.add(event);
        }
        fixNetwork();
    }

    private void fixNetwork()
    {
        for(int i =0; i < this.nodesInNetwork.size();i++)
        {
            EventNode Curr = this.nodesInNetwork.get(i);
            ArrayList<EventNode> children = new ArrayList<EventNode>();
            for(int j =0; j < Curr.getChildren().size();j++)
            {
                int indexChild = containsAndIndex(Curr.getChildren().get(j).getName());
                EventNode toAdd = this.nodesInNetwork.get(indexChild);
                children.add(toAdd);
            }
            Curr.setChildren(children);
            ArrayList<EventNode> parents = new ArrayList<EventNode>();
            for(int j =0; j < Curr.getParents().size();j++)
            {
                int indexParent = containsAndIndex(Curr.getParents().get(j).getName());
                EventNode toAdd = this.nodesInNetwork.get(indexParent);
                parents.add(toAdd);
            }
            Curr.setParents(parents);
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
