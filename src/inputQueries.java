import java.util.ArrayList;
import java.util.Arrays;

public class inputQueries {

    String filename;
    ArrayList<String> BayesBallQueries = new ArrayList<String>();
    ArrayList<String> probabilityQueries = new ArrayList<String>();

    public inputQueries(String filename,ArrayList<String> Queries)
    {
        this.filename = filename;
        for(int i =0; i < Queries.size();i++)
        {
            if(Queries.get(i).charAt(0) == 'P')
            {
                probabilityQueries.add(Queries.get(i));
            }
            else
            {
                BayesBallQueries.add(Queries.get(i));
            }

        }
    }
}
