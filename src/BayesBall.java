import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class BayesBall {
    EventNode source;
    EventNode dest;
    ArrayList<String> given;
    BayesianNetwork network;

    public BayesBall(String source, String dest,ArrayList<String> given, BayesianNetwork network)
    {
        this.network = new BayesianNetwork();
        this.given = new ArrayList<String>();
        for(int i =0; i < network.nodesInNetwork.size();i++)
        {
            this.network.nodesInNetwork.add(network.nodesInNetwork.get(i));
        }
        this.source = this.network.nodesInNetwork.get(this.network.containsAndIndex(source));
        this.dest = this.network.nodesInNetwork.get(this.network.containsAndIndex(dest));
        if(given!= null)
        {
            for(int i =0; i < given.size();i++)
            {
                EventNode curr = this.network.nodesInNetwork.get(this.network.containsAndIndex(given.get(i)));
                this.given.add(curr.getName());
            }
        }

    }


    public boolean bayesBallTraversal(EventNode Current, EventNode from)
    {
        if(Current.getName().equals(dest.getName()))
        {
            return true;
        }
        if(given != null && given.contains(Current.getName()))
        {
            if (!Current.childrenContain(from.getName())) {
                boolean flag = false;
                for (int i = 0; i < Current.getParents().size(); i++) {
                    String next = Current.getParents().get(i).getName();
                    EventNode goTo = network.nodesInNetwork.get(network.containsAndIndex(next));
                    flag = bayesBallTraversal(goTo, Current);
                    if (flag) {
                        return true;
                    }
                }
            }
            return false;
        }
        else
        {
            if(from == null)
            {
                boolean flag;
                for (int i = 0; i < Current.getParents().size(); i++) {
                    String next = Current.getParents().get(i).getName();
                    EventNode goTo = this.network.nodesInNetwork.get(this.network.containsAndIndex(next));
                    flag = bayesBallTraversal(goTo, Current);
                    if (flag) {
                        return true;
                    }
                }

                for(int i =0; i < Current.getChildren().size();i++)
                {
                    String next = Current.getChildren().get(i).getName();
                    EventNode goTo = network.nodesInNetwork.get(network.containsAndIndex(next));
                    flag = bayesBallTraversal(goTo,Current);
                    if(flag)
                    {
                        return true;
                    }
                }
                return false;
            }
            if(Current.childrenContain(from.getName()))
            {
                boolean flag = false;
                for (int i = 0; i < Current.getParents().size(); i++) {
                    String next = Current.getParents().get(i).getName();
                    EventNode goTo = network.nodesInNetwork.get(network.containsAndIndex(next));
                    flag = bayesBallTraversal(goTo, Current);
                    if (flag) {
                        return true;
                    }
                }
                for(int i =0; i < Current.getChildren().size();i++)
                {
                    String next = Current.getChildren().get(i).getName();
                    EventNode goTo = network.nodesInNetwork.get(network.containsAndIndex(next));
                    flag = bayesBallTraversal(goTo,Current);
                    if(flag)
                    {
                        return true;
                    }
                }
                return false;
            }
            else
            {
                boolean flag = false;
                for(int i =0; i < Current.getChildren().size();i++)
                {
                    String next = Current.getChildren().get(i).getName();
                    EventNode goTo = network.nodesInNetwork.get(network.containsAndIndex(next));
                    flag = bayesBallTraversal(goTo,Current);
                    if(flag)
                    {
                        return true;
                    }
                }
                return false;
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
