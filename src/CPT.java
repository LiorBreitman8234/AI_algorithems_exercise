import javax.swing.plaf.IconUIResource;
import java.util.ArrayList;

public class CPT {

    public String name;
    public ArrayList<String> given;
    public ArrayList<rowInCPT> rows;
    public ArrayList<Double> values;

    public CPT(EventNode mainNode)
    {
        ArrayList<ArrayList<String>> outcomes;
        this.name = mainNode.getName();
        String[] valuesString = mainNode.getValues().split(" ");
        this.values = new ArrayList<Double>();
        this.given = new ArrayList<String>();
        for(int i =0; i < valuesString.length;i++)
        {
            values.add(Double.parseDouble(valuesString[i]));
        }
        outcomes = new ArrayList<ArrayList<String>>();
        ArrayList<EventNode> parents = mainNode.getParents();
        for(int i =0; i < parents.size();i++)
        {
            outcomes.add(new ArrayList<String>());
            this.given.add(parents.get(i).getName());
        }
        outcomes.add(new ArrayList<String>());// for the current node
        int countOutcomes =0;
        for(int i =outcomes.size()-1; i >=0;i--)
        {
            if(countOutcomes ==0)
            {
                //the node that we are building the cpt for
                int counter = 0;
                while(counter < this.values.size())
                {
                    int amountOfOutcomes = mainNode.getOutcomes().size();
                    int currentOutcomeIndex = counter%amountOfOutcomes;
                    outcomes.get(i).add(mainNode.getOutcomes().get(counter%amountOfOutcomes));
                    counter++;
                }
                countOutcomes += mainNode.getOutcomes().size();
            }
            else
            {
                EventNode currParent = parents.get(i);
                int amountOfOutcomes = currParent.getOutcomes().size();
                int counter = 0;
                while(outcomes.get(i).size() < this.values.size())
                {
                    for(int j =0; j < countOutcomes;j++)
                    {
                        outcomes.get(i).add(currParent.getOutcomes().get(counter%amountOfOutcomes));
                    }
                    counter++;
                }
                countOutcomes *= amountOfOutcomes;
            }
        }
        this.rows = new ArrayList<rowInCPT>();
        for(int i =0; i < this.values.size();i++)
        {
            ArrayList<String> combination = new ArrayList<String>();
            for(int j =0; j < outcomes.size();j++)
            {
                combination.add(outcomes.get(j).get(i));
            }
            this.rows.add(new rowInCPT(combination,this.values.get(i)));
        }
    }

    public int getIndexParent(String name)
    {
        for(int i =0; i < this.given.size() ;i++)
        {
            if(given.get(i).equals(name))
            {
                return i;
            }
        }
        return -1;
    }

    public void printCPT()
    {
        System.out.println("\nCPT of: "+ this.name);
        System.out.println("---------------------------------------");
        for(int i =0; i < given.size();i++)
        {
            System.out.print(this.given.get(i) + "   ");
        }
        System.out.print(this.name+ "   ");
        System.out.println("value");
        System.out.println("---------------------------------------");

       for(int i =0; i < this.rows.size();i++)
       {
           System.out.println(this.rows.get(i).toString());
       }
    }
}
