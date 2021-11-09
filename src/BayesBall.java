import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class BayesBall {
    EventNode source;
    EventNode dest;
    ArrayList<EventNode> given;
    BayesianNetwork network;

    public BayesBall(String source, String dest,ArrayList<String> given, BayesianNetwork network)
    {
        this.network = new BayesianNetwork();
        for(int i =0; i < network.nodesInNetwork.size();i++)
        {
            this.network.nodesInNetwork.add(network.nodesInNetwork.get(i));
        }
        this.source = this.network.nodesInNetwork.get(this.network.containsAndIndex(source));
        this.dest = this.network.nodesInNetwork.get(this.network.containsAndIndex(dest));
        for(int i =0; i < given.size();i++)
        {
            EventNode curr = this.network.nodesInNetwork.get(this.network.containsAndIndex(given.get(i)));
            this.given.add(new EventNode(curr));
        }
    }


    public boolean bayesBallTraversal(String Current, String from)
    {
        if(Current.equals(source))
        {
            return true;
        }
        if(given.contains(Current))
        {

        }

        return false;
    }
    public void bfs(EventNode source, EventNode dest)
    {
        HashMap<String, Integer> explored = new HashMap<String,Integer>();
        Queue<EventNode> queue = new LinkedList<>();
        queue.add(source);

    }
}
