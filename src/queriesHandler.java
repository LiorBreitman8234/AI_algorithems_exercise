import java.util.ArrayList;
import java.util.Arrays;

public class queriesHandler {

    String filename;
    ArrayList<String> queries = new ArrayList<String>();

    public queriesHandler(String filename, ArrayList<String> Queries) {
        this.filename = filename;
        for (int i = 0; i < Queries.size(); i++) {
            this.queries.add(Queries.get(i));

        }
    }

    public ArrayList<String> handle(BayesianNetwork bn) {
        ArrayList<String> responses = new ArrayList<String>();
        String currentQuery = "";
        for (int i = 0; i < queries.size(); i++) {
            currentQuery = queries.get(i);
            if (currentQuery.charAt(0) != 'P')//BayesBall query
            {
                String[] toCheck = currentQuery.split("\\|");// splits source and dest to one size, and evidence to the other
                String[] sourceAndDest = toCheck[0].split("-");
                String source = sourceAndDest[0];
                String dest = sourceAndDest[1];
                ArrayList<String> given = new ArrayList<String>();
                if (toCheck.length == 2) {
                    String[] evidence = toCheck[1].split(",");
                    for (int j = 0; j < evidence.length; j++) {
                        evidence[j] = evidence[j].split("=")[0];
                        given.add(evidence[j]);
                    }
                }
                BayesBall b = new BayesBall(source, dest, given, bn);
                if (b.bayesBallTraversal(b.source, null)) {
                    responses.add("no");
                } else {
                    responses.add("yes");
                }
            } else {
                //P(B=T|J=T,M=T) A-E
                String[] queryHiddenSplit = currentQuery.split(" ");
                String[] hidden = new String[1];
                if(queryHiddenSplit.length >1)
                {
                    hidden = queryHiddenSplit[1].split("-");
                }
                String[] splitQuery = queryHiddenSplit[0].split("\\(");
                String[] splitQueryEvidence = splitQuery[1].split("\\|");
                String Query = splitQueryEvidence[0];
                String[] evidenceFirst = splitQueryEvidence[1].split("\\)");
                String[] evidence = new String[1];
                if(evidenceFirst.length > 0 )
                {
                    evidence = evidenceFirst[0].split(",");

                }
                VariableElimination VE = new VariableElimination(bn, Query, evidence, hidden);
                ArrayList<Double> response = VE.VariableEliminationAlgo();
                String res = response.get(0) + "," + response.get(1).intValue() + "," + response.get(2).intValue();
                responses.add(res);
            }
        }
        return responses;
    }
}
