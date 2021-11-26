
import java.util.ArrayList;

/**
 * We use this class to represent each node in our notebook
 */
public class EventNode {
    private String name;
    private ArrayList<String> outcomes;
    private ArrayList<EventNode> parents;
    private ArrayList<EventNode> children;
    private String values;//one step before building cpt
    private CPT cpt;

    /**
     * This constructor creates an empty node
     * @param name The name of the event
     */
    public EventNode(String name) {
        this.name = name;
        this.parents = new ArrayList<EventNode>();
        this.children = new ArrayList<EventNode>();
        this.outcomes = new ArrayList<String>();
    }

    /**
     * copy constructor for the event node
     * @param other the other node
     */
    public EventNode(EventNode other) {
        this.name = other.getName();
        this.parents = new ArrayList<EventNode>();
        this.children = new ArrayList<EventNode>();
        this.outcomes = new ArrayList<String>();
        for (int i = 0; i < other.parents.size(); i++) {
            this.parents.add(new EventNode(other.parents.get(i)));
        }
        for (int i = 0; i < other.children.size(); i++) {
            this.children.add(new EventNode(other.children.get(i)));
        }
        this.outcomes.addAll(other.outcomes);

    }

    /**
     * in this function we build the initial cpt of the node
     */
    public void BuildCPT() {
        this.cpt = new CPT(this);
    }

    /**
     * @param parent the parent to add to the list of this node's parents
     */
    public void addParent(EventNode parent) {
        this.parents.add(new EventNode(parent));
    }

    /**
     * @param child the child to add to this node's children
     */
    public void addChild(EventNode child) {
        this.children.add(new EventNode(child));
    }

    /**
     * @param outcome outcome to add
     */
    public void addOutcome(String outcome) {
        this.outcomes.add(outcome);
    }

    // the next functions are getters and setters
    public String getValues() {
        return values;
    }

    public void setValues(String values) {
        this.values = values;
    }

    public CPT getCPT() {
        return this.cpt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<EventNode> getParents() {
        ArrayList<EventNode> parents = new ArrayList<EventNode>();
        for (int i = 0; i < this.parents.size(); i++) {
            parents.add(new EventNode(this.parents.get(i)));
        }
        return parents;
    }

    public ArrayList<EventNode> getChildren() {
        ArrayList<EventNode> childs = new ArrayList<EventNode>();
        for (EventNode child : this.children) {
            childs.add(new EventNode(child));
        }
        return childs;
    }


    public ArrayList<String> getOutcomes() {
        return outcomes;
    }


    /**
     * @param event the name of the event we want to check
     * @return true if the event is in the parents list, false if it isn't
     */
    public boolean parentContain(String event) {
        for (EventNode parent : this.parents) {
            if (parent.getName().equals(event)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param event the name of the event we want to check
     * @return true if the event is in the children list, false if it isn't
     */
    public boolean childrenContain(String event) {
        for (EventNode child : this.children) {
            if (child.getName().equals(event)) {
                return true;
            }
        }
        return false;
    }



    @Override
    public String toString() {

        String toRet = "name: ";
        toRet += this.name;
        toRet += "\n children: ";
        for (int i = 0; i < this.children.size(); i++) {
            toRet += this.children.get(i).getName() + ",";
        }
        toRet += "\n Parents: ";
        for (int i = 0; i < this.parents.size(); i++) {
            toRet += this.parents.get(i).getName() + ",";
        }
        return toRet;

    }
}
