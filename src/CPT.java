import java.util.ArrayList;


/**
 * In this class I build the first version of each node's cpt(the full one from the xml file)
 */
public class CPT {

    public String name;
    public ArrayList<String> columns;
    public ArrayList<rowInCPT> rows;
    public ArrayList<Double> values;

    public CPT(EventNode mainNode)
    {
        ArrayList<ArrayList<String>> outcomes;
        this.name = mainNode.getName();
        String[] valuesString = mainNode.getValues().split(" ");
        this.values = new ArrayList<Double>();
        this.columns = new ArrayList<String>();
        //get all the values from the list
        for(int i =0; i < valuesString.length;i++)
        {
            values.add(Double.parseDouble(valuesString[i]));
        }
        outcomes = new ArrayList<ArrayList<String>>();
        ArrayList<EventNode> parents = mainNode.getParents();
        for(int i =0; i < parents.size();i++)
        {
            outcomes.add(new ArrayList<String>());
            this.columns.add(parents.get(i).getName());
        }
        outcomes.add(new ArrayList<String>());// for the current node
        this.columns.add(mainNode.getName());
        int countOutcomes =0;
        //go over the lines to insert the right values in the list
        for(int i =outcomes.size()-1; i >=0;i--)
        {
            if(countOutcomes ==0)
            {
                //the node that we are building the cpt for
                int counter = 0;
                while(counter < this.values.size())
                {
                    int amountOfOutcomes = mainNode.getOutcomes().size();
                    outcomes.get(i).add(mainNode.getOutcomes().get(counter%amountOfOutcomes));//here we switch outcome every row
                    counter++;
                }
                countOutcomes += mainNode.getOutcomes().size();//first addition to know when to switch outcomes
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
                countOutcomes *= amountOfOutcomes;//multiply to know when to switch between outcomes
            }
        }
        //convert the list to rowInCPT class
        this.rows = new ArrayList<rowInCPT>();
        for(int i =0; i < this.values.size();i++)
        {
            ArrayList<String> combination = new ArrayList<String>();
            for(int j =0; j < outcomes.size();j++)
            {
                combination.add(outcomes.get(j).get(i));
            }
            this.rows.add(new rowInCPT(combination,this.values.get(i),this.columns));
        }
    }

    /**
     * @param name The name of the column
     * @return The index of the column we got
     */

    public int getIndexColumn(String name)
    {
        for(int i =0; i < this.columns.size() ;i++)
        {
            if(columns.get(i).equals(name))
            {
                return i;
            }
        }
        return -1;
    }

    /**
     * Prints the cpt with all the names of the variables
     */
    public void printCPT()
    {
        System.out.println("\nCPT of: "+ this.name);
        System.out.println("---------------------------------------");
        for(int i =0; i < columns.size();i++)
        {
            System.out.print(this.columns.get(i) + "   ");
        }
        System.out.println("value");
        System.out.println("---------------------------------------");

       for(int i =0; i < this.rows.size();i++)
       {
           System.out.println(this.rows.get(i).toString());
       }
    }
}
