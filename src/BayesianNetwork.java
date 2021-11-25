import java.util.ArrayList;

public class BayesianNetwork {
    ArrayList<EventNode> nodesInNetwork;

    public BayesianNetwork()
    {
        this.nodesInNetwork = new ArrayList<EventNode>();
    }

    public void buildNetwork(String fileName)
    {
        ArrayList<nodeBuilderHelper> helpers = fileHandler.readXML(fileName);
        for(int i =0; i < helpers.size(); i++)
        {
            String name = helpers.get(i).getName();
            EventNode event =new EventNode(name);
            for(int j =0; j < helpers.get(i).getOutcomes().length;j++)
            {
                event.addOutcome(helpers.get(i).getOutcomes()[j]);
            }
            event.setValues(helpers.get(i).values);
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
        BuildCPTForNetwork();
    }

    public void BuildCPTForNetwork()
    {
       for(int i =0; i < this.nodesInNetwork.size();i++)
       {
           this.nodesInNetwork.get(i).BuildCPT();
       }
    }


    public boolean isDescendant(String current, String toChcek)
    {
        if(current.equals(toChcek))
        {
            return true;
        }
        EventNode thisNode = this.nodesInNetwork.get(this.containsAndIndex(current));
        if(thisNode.parentContain(toChcek))
        {
            return true;
        }
        if(thisNode.getParents().size() == 0)
        {
            return false;
        }
        boolean flag = false;
        for(int i =0; i < thisNode.getParents().size();i++)
        {
            String nextNode = thisNode.getParents().get(i).getName();
            flag = this.isDescendant(nextNode,toChcek);
            if(flag)
            {
                return  true;
            }
        }
        return false;

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
