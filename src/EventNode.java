import javafx.scene.Parent;

import java.util.ArrayList;
import java.util.Collections;

public class EventNode {
    private String name;
    private ArrayList<EventNode> parents;
    private ArrayList<EventNode> children;
    //public cpt table

    public EventNode(String name, ArrayList<EventNode> parents, ArrayList<EventNode> childs)
    {
        this.name = name;
        if(parents!=null)
        {
            for(int i =0 ;i < parents.size();i++)
            {
                this.parents.add(new EventNode(parents.get(i)));
            }
        }
        if(childs!= null)
        {
            for(int i =0 ;i <childs.size();i++)
            {
                this.children.add(new EventNode(children.get(i)));
            }
        }

    }

    public EventNode(String name)
    {
        this.name = name;
        this.parents  = new ArrayList<EventNode>();
        this.children = new ArrayList<EventNode>();
    }

    public EventNode(EventNode other)
    {
        this.name = other.getName();
        this.parents = new ArrayList<EventNode>();
        this.children = new ArrayList<EventNode>();
        for(int i =0; i < other.parents.size(); i++)
        {
          this.parents.add(new EventNode(other.parents.get(i)));
        }
        for(int i =0; i < other.children.size();i++)
        {
            this.children.add(new EventNode(other.children.get(i)));
        }
    }

    public void addParent(EventNode parent)
    {
        this.parents.add(new EventNode(parent));
    }

    public void addChild(EventNode child)
    {
        this.children.add(new EventNode(child));
    }


    public boolean parentContain(String event)
    {
        for(int i =0; i < this.parents.size();i++)
        {
            if(this.parents.get(i).getName().equals(event))
            {
                return true;
            }
        }
        return false;
    }

    public boolean childrenContain(String event)
    {
        for(int i =0; i < this.children.size();i++)
        {
            if(this.children.get(i).getName().equals(event))
            {
                return true;
            }
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<EventNode> getParents() {
        ArrayList<EventNode> parents = new ArrayList<EventNode>();
        for(int i =0; i < this.parents.size();i++)
        {
            parents.add(new EventNode(this.parents.get(i)));
        }
        return parents;
    }

    public void setParents(ArrayList<EventNode> parents) {
        this.parents = new ArrayList<EventNode>();
        for(int i =0; i < parents.size();i++)
        {
            this.parents.add(new EventNode(parents.get(i)));
        }
    }

    public ArrayList<EventNode> getChildren() {
        ArrayList<EventNode> childs = new ArrayList<EventNode>();
        for(int i =0; i < this.children.size();i++)
        {
            childs.add(new EventNode(this.children.get(i)));
        }
        return childs;
    }

    public void setChildren(ArrayList<EventNode> children) {
        this.children = new ArrayList<EventNode>();
        for(int i =0; i < children.size();i++)
        {
            this.children.add(new EventNode(children.get(i)));
        }
    }

    public String toString()
    {

        String toRet ="name: ";
        toRet += this.name;
        toRet +="\n children: ";
        for(int i =0; i < this.children.size();i++)
        {
            toRet += this.children.get(i).getName() + ",";
        }
        toRet += "\n Parents: ";
        for(int i =0; i < this.parents.size();i++)
        {
            toRet += this.parents.get(i).getName() + ",";
        }
        return toRet;

    }


}
