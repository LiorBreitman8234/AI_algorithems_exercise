import java.util.ArrayList;
import java.util.Collections;

public class Factor {

    public String FactorOf;
    public EventNode nodeOfFactor;
    private int count;
    private ArrayList<String> parents;
    private ArrayList<String> given;
    private ArrayList<ArrayList<String>> rows;
    private ArrayList<Double> values;

    public Factor(EventNode node, ArrayList<String> given, int count)
    {
        this.count = count;
        this.FactorOf = node.getName();
        this.nodeOfFactor = node;
        this.parents = new ArrayList<String>();
        for(int i =0; i < nodeOfFactor.getParents().size();i++)
        {
            this.parents.add(this.nodeOfFactor.getParents().get(i).getName());
        }
        this.given = new ArrayList<String>();
        for(int i =0; i < given.size();i++)
        {
            this.given.add(given.get(i));
        }


    }
}
