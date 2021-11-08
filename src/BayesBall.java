import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class BayesBall {
    String source;
    String dest;
    ArrayList<String> given;
    BayesianNetwork network;

    public BayesBall(String source, String dest,ArrayList<String> given, BayesianNetwork network)
    {
        int index = network.containsAndIndex(source);
        EventNode eventSource = network.nodesInNetwork.get(index);
        this.source = eventSource.getName();
        index = network.containsAndIndex(dest);
        EventNode eventDest = network.nodesInNetwork.get(index);
        this.dest = eventDest.getName();
        this.network = network;
        if(given != null)
        {
            for(int i =0; i < given.size();i++)
            {
                index = network.containsAndIndex(given.get(i));
                this.given.add(network.nodesInNetwork.get(index).getName());
            }
        }


    }


    public void bfs(EventNode source, EventNode dest)
    {
        HashMap<String, Integer> explored = new HashMap<String,Integer>();
        Queue<EventNode> queue = new LinkedList<>();
        queue.add(source);

    }
}
