import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class BayesBall {
    EventNode source;
    EventNode dest;
    ArrayList<EventNode> Given;


    public void bfs(EventNode source, EventNode dest)
    {
        HashMap<String, Integer> explored = new HashMap<String,Integer>();
        Queue<EventNode> queue = new LinkedList<>();
        queue.add(source);

    }
}
