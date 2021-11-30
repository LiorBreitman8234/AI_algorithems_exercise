import java.util.ArrayList;


/**
 * In this class I run the BaseBall algorithm
 * To construct the class I need the query(source, destination and evidence nodes) and the network of nodes
 * If the source and destination of the query are dependent,I will return true
 * If the source and destination of the query are independent,I will return false
 */
public class BayesBall {
    EventNode source;
    EventNode dest;
    ArrayList<String> given;
    BayesianNetwork network;
    ArrayList<String> pastRoutes;

    public BayesBall(String source, String dest,ArrayList<String> given, BayesianNetwork network)
    {
        this.pastRoutes = new ArrayList<String>();
        this.network = new BayesianNetwork();
        this.given = new ArrayList<String>();
        this.network.nodesInNetwork.addAll(network.nodesInNetwork);
        this.source = this.network.nodesInNetwork.get(this.network.containsAndIndex(source));
        this.dest = this.network.nodesInNetwork.get(this.network.containsAndIndex(dest));
        if(given!= null)
        {
            for (String s : given) {
                EventNode curr = this.network.nodesInNetwork.get(this.network.containsAndIndex(s));
                this.given.add(curr.getName());
            }
        }

    }

    /**
     * In this function I go over the network of nodes recursively according to the conditions of bayes ball.
     * Conditions:
     *  1. If I came from parent to a given Node, I can only go to other parents.
     *  2. If I came from a parent to a not given node, I can only go to the children.
     *  3. If I came from a child to a given node, I can't go anywhere
     *  4. If I came from a child to a not given node, I can go anywhere
     * If the source and destination of the query are dependent, return true
     * If the source and destination of the query are independent, return false
     * @param Current The node that I am currently at
     * @param from The node that I came from
     * @return If I made it to the node, return true, else return false
     */
    public boolean bayesBallTraversal(EventNode Current, EventNode from)
    {
        //if I have a route, I want to add it ,so I won't go over it again
        if(from !=  null)
        {
            String path = Current.getName() +","+ from.getName();
            if(Current.childrenContain(from.getName()))
            {
                path+=",c";
            }
            else
            {
                path+=",p";
            }
            pastRoutes.add(path);
        }
        //if I got to the destination, return true;
        if(Current.getName().equals(dest.getName()))
        {
            return true;
        }
        //if I have givens and I am currently in a given node
        if(given != null && given.contains(Current.getName()))
        {
            //only check if I came from parents,if I came from child I can't continue
            if (!Current.childrenContain(from.getName())) {
                boolean flag = false;
                for (int i = 0; i < Current.getParents().size(); i++) {
                    String next = Current.getParents().get(i).getName();
                    EventNode goTo = network.nodesInNetwork.get(network.containsAndIndex(next));
                    String check = goTo.getName() + "," + Current.getName() + ",c";
                    if(!pastRoutes.contains(check))//If I already did this route, don't do it again
                    {
                        flag = bayesBallTraversal(goTo, Current);
                    }
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
                boolean flag = false;
                for (int i = 0; i < Current.getParents().size(); i++) {
                    String next = Current.getParents().get(i).getName();
                    EventNode goTo = this.network.nodesInNetwork.get(this.network.containsAndIndex(next));
                    String check = goTo.getName() + "," + Current.getName() + ",c";
                    if(!pastRoutes.contains(check))//If I already did this route, don't do it again
                    {
                        flag = bayesBallTraversal(goTo, Current);
                    }
                    if (flag) {
                        return true;
                    }
                }

                for(int i =0; i < Current.getChildren().size();i++)
                {
                    String next = Current.getChildren().get(i).getName();
                    EventNode goTo = network.nodesInNetwork.get(network.containsAndIndex(next));
                    String check = goTo.getName() + "," + Current.getName() + ",p";
                    if(!pastRoutes.contains(check))//If I already did this route, don't do it again
                    {
                        flag = bayesBallTraversal(goTo, Current);
                    }
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
                    String check = goTo.getName() + "," + Current.getName() + ",c";
                    if(!pastRoutes.contains(check))//If I already did this route, don't do it again
                    {
                        flag = bayesBallTraversal(goTo, Current);
                    }
                    if (flag) {
                        return true;
                    }
                }
                for(int i =0; i < Current.getChildren().size();i++)
                {
                    String next = Current.getChildren().get(i).getName();
                    EventNode goTo = network.nodesInNetwork.get(network.containsAndIndex(next));
                    String check = goTo.getName() + "," + Current.getName() + ",p";
                    if(!pastRoutes.contains(check))//If I already did this route, don't do it again
                    {
                        flag = bayesBallTraversal(goTo, Current);
                    }
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
                    String check = goTo.getName() + "," + Current.getName() + ",p";
                    if(!pastRoutes.contains(check))//If I already did this route, don't do it again
                    {
                        flag = bayesBallTraversal(goTo, Current);
                    }
                    if(flag)
                    {
                        return true;
                    }
                }
                return false;
            }
        }
    }
}
