/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tpst;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author kostis
 */
public class Node {
    String Name;
    String Type;
    Node left;
    Node right;
    Node center;
    double cost;
    double selectivity;
    double[] path;
    boolean distribution = false;
    HashMap<Integer,Node> neighbors;
    
    public Node(String Name, String Type)
    {
        this.Name = Name;
        this.Type = Type;
    }
    
     public Node(String Name, String Type, double cost, double prob)
    {
        this.Name = Name;
        this.Type = Type;
        this.cost = cost;
        this.selectivity = prob;
    }
     
    public Node(String Name, String Type, double cost[], double prob)
    {
        this.Name = Name;
        this.Type = Type;
        int max = cost.length-1;
        int index = (int) ((Math.random() * (max - 0)) + 0);
        this.cost = cost[index];
        this.selectivity = prob;
        
    }
     
     public Node(String Name, String Type, double[] path)
    {
        this.Name = Name;
        this.Type = Type;
        this.path = path;
    }
     
      public Node(String Name, String Type,double cost, double[] path)
    {
        this.Name = Name;
        this.Type = Type;
        this.cost = cost;
        this.path = path;
    }
    
    public Node(String Type_Name)
    {
        this.Name = Type_Name;
        this.Type = Type_Name;
        //this.neighbors = new HashMap<>();
    }
    
    public void setNeighbor(String pos, Node n)
    {
        if(pos.equals("left"))
        {
            this.left=n;
        }
        else if(pos.equals("right"))
        {
            this.right=n;
        }
        else if(pos.equals("center"))
        {
            this.center=n;
        }
        else 
        {
          System.out.println("Invdalid position argument");
        }
     
      
       
    }
    
    public void insertMetadata(double[] prob)
    {
        this.path=prob;
    }
    
    public HashMap<Integer,Node> getNeighbors()
      {
          return this.neighbors;
      }  
    
    public void insertNeighbor(Node n)
    {
        if(this.neighbors!=null)
        {HashMap<Integer,Node> temp = this.neighbors;
        int id = temp.size();
        this.neighbors.put(id, n);
        }
        else
        {
            
            HashMap<Integer,Node> temp =new HashMap();
            temp.put(0, n);
            this.neighbors = temp;
        }
    }
    
    public void print()
    {
        //System.out.println(this.Type);
        if(this.Type.equals("Leaf"))
        {
            System.out.println(this.Name);
            return;
        }
        else
        {
            
            Iterator it = this.neighbors.entrySet().iterator();
            while(it.hasNext())
            {
                Map.Entry<Integer,Node> temp = (Map.Entry<Integer,Node>) it.next();
                        Node temp_node = temp.getValue();
                        temp_node.print();
            }
            return;
        }
    }
    
    public double calculateCost()
    {
        if(this.Type.equals("Leaf"))
        {
            //System.out.println(this.Name);
            return this.cost;
        }
        else
        {
            
            Iterator it = this.neighbors.entrySet().iterator();
            if(this.Type.equals("Sequence"))
            {
                double sum=0;
            while(it.hasNext())
            {
                Map.Entry<Integer,Node> temp = (Map.Entry<Integer,Node>) it.next();
                        Node temp_node = temp.getValue();
                        sum += temp_node.calculateCost();
                        
            }
            //System.out.println("parent:" + this.Name + "cost:" + sum);
            return sum;
            }
            else if(this.Type.equals("AND/XOR"))
            {
                double min=Double.MAX_VALUE;
                while(it.hasNext())
                {
                Map.Entry<Integer,Node> temp = (Map.Entry<Integer,Node>) it.next();
                        Node temp_node = temp.getValue();
                        if(temp_node.calculateCost()<min)
                        {
                            min=temp_node.calculateCost();
                        }
                }
                //System.out.println("parent:" + this.Name + "cost:" + max);
                return min;
                
            }
            else if(this.Type.equals("AND"))
            {
                double max=0;
                while(it.hasNext())
                {
                Map.Entry<Integer,Node> temp = (Map.Entry<Integer,Node>) it.next();
                        Node temp_node = temp.getValue();
                        if(temp_node.calculateCost()>max)
                        {
                            max=temp_node.calculateCost();
                        }
                }
                //System.out.println("parent:" + this.Name + "cost:" + max);
                return max;
                
            }
            else if(this.Type.equals("XOR"))
            {
                double sum=0;
                
               while(it.hasNext())
               {
                Map.Entry<Integer,Node> temp = (Map.Entry<Integer,Node>) it.next();
                        Node temp_node = temp.getValue();
                        
                         //System.out.println(this.Name);
                         //System.out.println(temp.getKey());
                        sum += this.path[temp.getKey()]*temp_node.calculateCost();
                        
                        
                        
                }
               //System.out.println("parent:" + this.Name + "cost:" + sum);
               return sum;
            }
            else{
                return 0;
            }
        }
        
    }
    
    public void replaceNeighbor(Node old, Node replacement)
    {
        Iterator it = this.neighbors.entrySet().iterator();
        while(it.hasNext())
        {
            Map.Entry<Integer,Node> entry = (Map.Entry<Integer,Node>) it.next();
            int key = entry.getKey();
            Node candidate = entry.getValue();
            if(old.Name.equals(candidate.Name))
            {
                this.neighbors.put(key, replacement);
            }
        }
                
    }
    
    public HashMap<Integer,Node> reorderNeighbor(int pos_a, Node b, int pos_b)
    {
        HashMap<Integer,Node> new_neighbors = new HashMap();
        
        
        for(int i=0;i<this.neighbors.size();i++)
        {
            if(i<pos_a)
            {
                Node temp_node = this.neighbors.get(i);
                new_neighbors.put(i, temp_node);
            }
            else if(i>pos_a && i <pos_b)
            {
                Node temp_node = this.neighbors.get(i);
                new_neighbors.put(i+1,temp_node);
            }
            else if(i==pos_a)
            {
                Node temp_node = this.neighbors.get(i);
                new_neighbors.put(i, b);
                new_neighbors.put(i+1,temp_node);
                
            }
            else if(i== pos_b)
            {
                //System.out.println(this.neighbors.get(i).Name);
                continue;
            }
            else if(i>pos_a && i >= pos_b)
            {
                Node temp_node = this.neighbors.get(i);
                new_neighbors.put(i, temp_node);
            }
        }
        
        return new_neighbors;
        
        
    }
    
    public void swap(String first, String second)
        {
            HashMap<Integer,Node> new_neighbors = new HashMap<>();
            HashMap<Integer,Node> old_neighbors = this.neighbors;
            Iterator it = old_neighbors.entrySet().iterator();
            int first_index=0;
            int second_index=0;
            int i=0;
            while(it.hasNext())
            {
                Map.Entry<Integer,Node> entry = (Map.Entry<Integer,Node>) it.next();
                Node next_check =  entry.getValue();
                if(next_check.Name.equals(first))
                {
                    first_index =i;
                }
                if(next_check.Name.equals(second))
                {
                    second_index =i;
                }
                i++;
            }
            
            Iterator it2 = old_neighbors.entrySet().iterator();
            i=0;
            for( i=0;i<old_neighbors.size();i++)
            {
                if(i<second_index)
                {
                    new_neighbors.put(i, old_neighbors.get(i));
                }
                else if(i>second_index && i<first_index)
                {
                    new_neighbors.put(i+1, old_neighbors.get(i));
                }
                
                if(i==second_index)
                {
                    new_neighbors.put(i, old_neighbors.get(first_index));
                    new_neighbors.put(i+1, old_neighbors.get(i));
                }
                
                if(i>second_index && i>first_index)
                {
                    new_neighbors.put(i, old_neighbors.get(i));
                }
            }
            
            this.neighbors = new_neighbors;
        }
        

    
}
