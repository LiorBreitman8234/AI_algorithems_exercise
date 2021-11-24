import javafx.scene.Parent;

import java.util.ArrayList;
import java.util.Collections;

public class EventNode {
    private String name;
    private ArrayList<String> outcomes;
    private ArrayList<EventNode> parents;
    private ArrayList<EventNode> children;
    private String values;//one step before building cpt
    private CPT cpt;

    public EventNode(String name, ArrayList<EventNode> parents, ArrayList<EventNode> childs, ArrayList<String> outcomes) {
        this.name = name;
        if (parents != null) {
            for (int i = 0; i < parents.size(); i++) {
                this.parents.add(new EventNode(parents.get(i)));
            }
        }
        if (childs != null) {
            for (int i = 0; i < childs.size(); i++) {
                this.children.add(new EventNode(children.get(i)));
            }
        }
        if (outcomes != null) {
            for (int i = 0; i < outcomes.size(); i++) {
                this.outcomes.add(outcomes.get(i));
            }
        }

    }

    public EventNode(String name) {
        this.name = name;
        this.parents = new ArrayList<EventNode>();
        this.children = new ArrayList<EventNode>();
        this.outcomes = new ArrayList<String>();
    }

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
        for (int i = 0; i < other.outcomes.size(); i++) {
            this.outcomes.add(other.outcomes.get(i));
        }

    }

    public void BuildCPT() {
        this.cpt = new CPT(this);
    }

    public void PrintCPT() {
        this.cpt.printCPT();
    }

    public void addParent(EventNode parent) {
        this.parents.add(new EventNode(parent));
    }

    public void addChild(EventNode child) {
        this.children.add(new EventNode(child));
    }

    public void addOutcome(String outcome) {
        this.outcomes.add(outcome);
    }

    public String getValues() {
        return values;
    }

    public void setValues(String values) {
        this.values = values;
    }


    public boolean isDescendant(EventNode other) {
        if (this.name.equals(other.getName())) {
            return true;
        }
        if (this.parents.size() == 0) {
            return false;
        }
        if (this.parentContain(other.getName())) {
            return true;
        }
        boolean flag = false;
        for (int i = 0; i < this.parents.size(); i++) {
            flag = this.parents.get(i).isDescendant(other);
            if (flag) {
                return true;
            }
        }
        return false;
    }

    public CPT getCPT() {
        return this.cpt;
    }

    public boolean parentContain(String event) {
        for (int i = 0; i < this.parents.size(); i++) {
            if (this.parents.get(i).getName().equals(event)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<String> getOutcomes() {
        return outcomes;
    }

    public void setOutcomes(ArrayList<String> outcomes) {
        this.outcomes = new ArrayList<String>();
        for (int i = 0; i < outcomes.size(); i++) {
            this.outcomes.add(outcomes.get(i));
        }
    }

    public boolean childrenContain(String event) {
        for (int i = 0; i < this.children.size(); i++) {
            if (this.children.get(i).getName().equals(event)) {
                return true;
            }
        }
        return false;
    }

    public void removeKid(String name) {
        for (EventNode node : this.children) {
            if (node.getName().equals(name)) {
                this.children.remove(node);
            }
        }
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

    public void setParents(ArrayList<EventNode> parents) {
        this.parents = new ArrayList<EventNode>();
        for (int i = 0; i < parents.size(); i++) {
            this.parents.add(new EventNode(parents.get(i)));
        }
    }

    public ArrayList<EventNode> getChildren() {
        ArrayList<EventNode> childs = new ArrayList<EventNode>();
        for (int i = 0; i < this.children.size(); i++) {
            childs.add(new EventNode(this.children.get(i)));
        }
        return childs;
    }

    public void setChildren(ArrayList<EventNode> children) {
        this.children = new ArrayList<EventNode>();
        for (int i = 0; i < children.size(); i++) {
            this.children.add(new EventNode(children.get(i)));
        }
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
