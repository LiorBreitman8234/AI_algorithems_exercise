import javax.swing.plaf.IconUIResource;
import java.util.ArrayList;

public class CPT {

    private String name;
    private ArrayList<String> given;
    public ArrayList<ArrayList<String>> outcomes;
    public ArrayList<Double> values;

    public CPT(EventNode mainNode)
    {
        this.name =mainNode.getName();
        String[] valuesString = mainNode.getValues().split(" ");
        this.values = new ArrayList<Double>();
        this.given = new ArrayList<String>();
        for(int i =0; i < valuesString.length;i++)
        {
            values.add(Double.parseDouble(valuesString[i]));
        }
        this.outcomes = new ArrayList<ArrayList<String>>();
        ArrayList<EventNode> parents = mainNode.getParents();
        for(int i =0; i < parents.size();i++)
        {
            this.outcomes.add(new ArrayList<String>());
            this.given.add(parents.get(i).getName());
        }
        this.outcomes.add(new ArrayList<String>());// for the current node
        int countOutcomes =0;
        for(int i =this.outcomes.size()-1; i >=0;i--)
        {
            if(countOutcomes ==0)
            {
                //the node that we are building the cpt for
                int counter = 0;
                while(counter < this.values.size())
                {
                    int amountOfOutcomes = mainNode.getOutcomes().size();
                    int currentOutcomeIndex = counter%amountOfOutcomes;
                    this.outcomes.get(i).add(mainNode.getOutcomes().get(counter%amountOfOutcomes));
                    counter++;
                }
                countOutcomes += mainNode.getOutcomes().size();
            }
            else
            {
                EventNode currParent = parents.get(i);
                int amountOfOutcomes = currParent.getOutcomes().size();
                int counter = 0;
                while(this.outcomes.get(i).size() < this.values.size())
                {
                    for(int j =0; j < countOutcomes;j++)
                    {
                        this.outcomes.get(i).add(currParent.getOutcomes().get(counter%amountOfOutcomes));
                    }
                    counter++;
                }
                countOutcomes *= amountOfOutcomes;
            }
        }
    }

    public void printCPT()
    {

        System.out.println("CPT of: "+ this.name);
        System.out.println("---------------------------------------");
        for(int i =0; i < given.size();i++)
        {
            System.out.print(this.given.get(i) + "   ");
        }
        System.out.print(this.name+ "   ");
        System.out.println("value\n");
        for(int i =0; i < values.size();i++)
        {
            for(int j =0; j < this.outcomes.size();j++)
            {
                System.out.print(this.outcomes.get(j).get(i) + "   ");
            }
            System.out.println(this.values.get(i) + "\n");
        }
    }
}
