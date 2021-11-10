import java.util.ArrayList;

public class queriesHandler {

    String filename;
    ArrayList<String> queries = new ArrayList<String>();

    public queriesHandler(String filename, ArrayList<String> Queries)
    {
        this.filename = filename;
        for(int i =0; i < Queries.size();i++)
        {
            this.queries.add(Queries.get(i));

        }
    }

    public ArrayList<String> handle(BayesianNetwork bn)
    {
        ArrayList<String> responses = new ArrayList<String>();
        String currentQuery = "";
        for(int i =0; i< queries.size();i++)
        {
            currentQuery = queries.get(i);
            if(currentQuery.charAt(0)!= 'P')//BayesBall query
            {
                String[] toCheck = currentQuery.split("\\|");// splits source and dest to one size, and evidence to the other
                String[] sourceAndDest = toCheck[0].split("-");
                String source = sourceAndDest[0];
                String dest = sourceAndDest[1];
                ArrayList<String> given = new ArrayList<String>();
                if(toCheck.length ==2)
                {
                    String[] evidence = toCheck[1].split(",");
                    for(int j = 0;j < evidence.length;j++)
                    {
                        evidence[j] = evidence[j].split("=")[0];
                        given.add(evidence[j]);
                    }
                }
                BayesBall b = new BayesBall(source,dest,given,bn);
                if(b.bayesBallTraversal(b.source,null))
                {
                    responses.add("no");
                }
                else
                {
                    responses.add("yes");
                }
            }
            else//probability query
            {
                //do nothing for now
            }
        }

        return responses;

    }
}
