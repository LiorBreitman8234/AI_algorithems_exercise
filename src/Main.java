import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        BayesianNetwork BN = new BayesianNetwork();
        String filename = "src/alarm_net.xml";
        BN.buildNetwork(filename);
        for(int i = 0; i < BN.nodesInNetwork.size();i++)
        {
            System.out.println(BN.nodesInNetwork.get(i).toString());
        }
        ArrayList<String> given = new ArrayList<String>();
        given.add("J");
        BayesBall BB = new BayesBall("B","E",given,BN);
        boolean flag = BB.bayesBallTraversal(BB.source,null);
        System.out.println(BB.bayesBallTraversal(BB.source,null));
    }
}
