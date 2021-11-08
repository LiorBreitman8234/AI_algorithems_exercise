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
        index = network.containsAndIndex(dest);
        EventNode eventDest = network.nodesInNetwork.get(index);

    }


    public void bfs(EventNode source, EventNode dest)
    {
        HashMap<String, Integer> explored = new HashMap<String,Integer>();
        Queue<EventNode> queue = new LinkedList<>();
        queue.add(source);

    }
}
