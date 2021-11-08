public class Main {

    public static void main(String[] args) {
        BayesianNetwork BN = new BayesianNetwork();
        String filename = "src/alarm_net.xml";
        BN.buildNetwork(filename);
        for(int i = 0; i < BN.nodesInNetwork.size();i++)
        {
            System.out.println(BN.nodesInNetwork.get(i).toString());
        }
        BayesBall BB = new BayesBall("B","E",null,BN);
    }
}
