/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tpst;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author kostis
 */
public class Tree {
     public Node root;
     public HashMap<String,ArrayList<String[]>> constraints = new HashMap<>();
     public HashMap<String,String> resources = new HashMap<>();
        
        String getName(Node n) {
        return n.Name;
    }
        
        String getType(Node n){
         return n.Type;
        }
        
        public Tree(String root_type){
           this.root = new Node(root_type);
        }
        
        public Tree(Node rootSet)
        {
            this.root = rootSet;
        }
        
        public void print()
        {
            if(!this.root.equals(null))
            {
                HashMap<Integer,Node> neighbors = root.getNeighbors();
                if(neighbors.size()>0)
                {
                    Iterator it = neighbors.entrySet().iterator();
                    while(it.hasNext())
                    {
                        Map.Entry<Integer,Node> temp = (Map.Entry<Integer,Node>) it.next();
                        Node temp_node = temp.getValue();
                        temp_node.print();
                    }
                }
            }
        }
        
        public double costFunction()
        {
            if(!this.root.equals(null))
            {
                HashMap<Integer,Node> neighbors = root.getNeighbors();
                if(neighbors.size()>0)
                {
                 if(root.Type.equals("Sequence"));
                 {
                     double cost =0;
                     Iterator it = neighbors.entrySet().iterator();
                     while(it.hasNext())
                    {
                        Map.Entry<Integer,Node> temp = (Map.Entry<Integer,Node>) it.next();
                        Node temp_node = temp.getValue();
                        cost += temp_node.calculateCost();
                    }
                     
                     return cost;
                 }
                }
                else
                {
                    
                    return 0.0;
                }
                
            }
            else
            {
                
                return 0.0;
            }
            
            
        }
        
        public void examineParallelism(Node N)
        {
            if(N.Type.equals("Sequence"))
            {
                HashMap<Integer,Node> neighbors = (HashMap<Integer,Node>) N.neighbors.clone();
               
                int pairings = neighbors.size()-1;
                for(int i=0;i<pairings;i++)
                {
                    Node one = neighbors.get(i);
                    Node two = neighbors.get(i+1);
                    if(!pairValidity(one,two))
                    {
                        continue;
                    }
                    
                    HashMap<Integer,Node> result = replace(i,i+1,N);
                   
                    HashMap<Integer,Node> old = (HashMap<Integer,Node>) N.neighbors.clone();
                    double cost_old = this.costFunction();
                    N.neighbors = result;
                    double cost_new = this.costFunction();
                    System.out.println("Cycle time: old : " + cost_old + ",  new: " + cost_new);
                    N.neighbors = neighbors;
                    
                }
                
            }
        }
        
        public void Knockout()
        {
            Node root = this.root;
            HashMap<Integer,Node> neighbors = (HashMap<Integer,Node>) root.neighbors.clone();
            Iterator it = neighbors.entrySet().iterator();
            while(it.hasNext())
            {
                Map.Entry<Integer,Node> entry = (Map.Entry<Integer, Node>) it.next();
                Node candidate = entry.getValue();
                if(candidate.Name.contains("Start") || candidate.Name.contains("End"))
                {
                    
                }
                else
                {
                    if(candidate.Type.equals("Sequence"))
                    {
                        this.examineReOrdering(candidate);
                    }
                    if(candidate.Type.equals("XOR"))
                    {
                        HashMap<Integer,Node>temp_neighbors = (HashMap<Integer,Node>) candidate.neighbors.clone();
                        Iterator it2 = temp_neighbors.entrySet().iterator();
                        while(it2.hasNext())
                        {
                            Map.Entry<Integer,Node> entry2 = (Map.Entry<Integer, Node>) it2.next();
                            Node path = entry2.getValue();
                            this.examineReOrdering(path);
                            
                        }
                    }
                }
            }
            
            
        }
        
        public void examineParallelism2(Node N)
        {
            if(N.Type.equals("Sequence"))
            {
                HashMap<Integer,Node> neighbors = (HashMap<Integer,Node>) N.neighbors.clone();
                HashMap<Integer,Node> og_neighbors = (HashMap<Integer,Node>) N.neighbors.clone();
                int pairings = neighbors.size()-1;
                for(int i=0;i<pairings;i++)
                {
                    Node one = neighbors.get(i);
                    
                    for(int j=i+1;j<neighbors.size();j++)
                    {
                        Node two = neighbors.get(j);
                    if(!pairValidity(one,two))
                    {
                        continue;
                    }
                    
                    HashMap<Integer,Node> result = replace(i,j,N);
                    
                    HashMap<Integer,Node> old = (HashMap<Integer,Node>) N.neighbors.clone();
                    
                    double cost_old = this.costFunction();
                    N.neighbors = result;
                    double cost_new = this.costFunction();
                    System.out.println("Cycle time: old: " + cost_old + ",  new: " + cost_new);
                    
                    
                    if(cost_new<cost_old)
                    {
                        
                        HashMap<Integer,Node> resulting_neighbors = orderValidity(N.neighbors,constraints,two);
                        N.neighbors = resulting_neighbors;
                        System.out.println("New Plan: ");
                        System.out.println();
                        System.out.println();
                        System.out.println();
                        this.print();
                        
                        
                    }
                    
                    }
                    
                }
                
            }
           
            
        }
        
        public void examineReOrdering(Node N)
        {
            HashMap<String,String> changes = new HashMap<>();
            if(N.Type.equals("Sequence"))
            {
                HashMap<Integer,Node> neighbors = (HashMap<Integer,Node>) N.neighbors;
               
                int pairings = neighbors.size()-1;
                for(int i=0;i<pairings;i++)
                {
                    Node one = neighbors.get(i);
                   
                    Double rank_one = (1-one.selectivity);
                    for(int j=i+1;j<neighbors.size();j++){
                    
                        Node two = neighbors.get(j);
                        
                   
                    boolean two_parent = false;    
                    if(two.Type.equals("Leaf"))
                    {
                     if(!pairValidity(one,two))
                    {
                        continue;
                    }
                    
                    
                    Double rank_two = (1-two.selectivity);
                    if(rank_two == Double.POSITIVE_INFINITY)
                    {
                        rank_two = 0.0;
                    }
                    if(rank_one == Double.POSITIVE_INFINITY)
                    {
                        rank_one = 0.0;
                    }
                    if(rank_two>rank_one)
                    {
                                            }
                    }
                    if(two.Type.equals("XOR"))
                    {
                        two_parent = true;
                        HashMap<Integer,Node> two_neighbors = (HashMap<Integer,Node>) two.neighbors.clone();
                        Iterator it3 = two_neighbors.entrySet().iterator();
                        while(it3.hasNext())
                        {
                            Map.Entry<Integer,Node> two_entry = (Map.Entry<Integer,Node>) it3.next();
                            Node two_neighbor = (Node) two_entry.getValue();
                            if(!pairValidity(one,two_neighbor))
                            {
                             continue;
                            }
                            Double rank_two = (1-two_neighbor.selectivity)/(two_neighbor.cost);
                            if(rank_two == Double.POSITIVE_INFINITY)
                            {
                             rank_two = 0.0;
                            }
                            if(rank_one == Double.POSITIVE_INFINITY)
                            {
                             rank_one = 0.0;
                            }
                            if(rank_two>rank_one)
                            {
                             System.out.println("Reorder");
                             System.out.println("one: " + one.Name + " , " + "two: " + two_neighbor.Name);
                             //two.replaceNeighbor(two_neighbor, new Node("Blank path","Leaf",0,1));
                             //System.out.println(rank_two);
                             if(!changes.containsKey(two.Name))
                             {
                                 changes.put(two.Name, one.Name);
                             }
                             
                             //N.neighbors =N.reorderNeighbor(i, two,j);
                            }
                            
                        }
                    }
                    HashMap<Integer,Node> result = replace(i,i+1,N);
                    
                    HashMap<Integer,Node> old = (HashMap<Integer,Node>) N.neighbors.clone();
                    double cost_old = this.costFunction();
                    N.neighbors = result;
                    double cost_new = this.costFunction();
                    System.out.println("old: " + cost_old + ",  new: " + cost_new);
                    
                    
                }
               }
            }
            Iterator changes_it = changes.entrySet().iterator();
            while(changes_it.hasNext())
            {
               Map.Entry<String,String> entry = (Map.Entry<String,String>) changes_it.next();
               String first = entry.getKey();
               String second = entry.getValue();
               N.swap(first,second);
            }
                
            
            
                        
        }
        
        public HashMap<Integer,Node> replace(int one, int two, Node father)
        {
            HashMap<Integer,Node> replace = (HashMap<Integer,Node>) father.neighbors.clone();
            Node one_node = replace.get(one);
            Node two_node = replace.get(two);
            
            replace.remove(one);
            replace.remove(two);
           
            Node and = new Node("AND","AND");
            and.insertNeighbor(one_node);
            and.insertNeighbor(two_node);
            replace.put(one, and);
            
            for(int i=0;i<father.neighbors.size();i++)
            {
                if(i>two)
                {
                    Node change = (Node) replace.get(i);
                    replace.remove(i);
                    replace.put(i-1, change);
                }
            }
            
            return replace;
        }
        
        public HashMap<Integer,Node> reOrder(String move, String constant, HashMap<Integer,Node> neighbors,HashMap<String, ArrayList<String[]>> constraints)
        {
           HashMap<Integer,Node> order = (HashMap<Integer,Node>) neighbors.clone();
           
           int index_move=0;
           int index_constant=0;
           for(int i=0;i<order.size();i++)
           {
               Node temp = order.get(i);
               if(temp.Name.equals(move))
               {
                   index_move = i;
               }
               if(temp.Name.equals(constant))
               {
                   index_constant =i;
               }
           }
           HashMap<Integer,Node> new_order = new HashMap<Integer,Node>();
           
           for(int i=0;i<order.size();i++)
           {
               if(i<=index_constant)
               {
                   if(index_constant==i)
                   {
                       
                       new_order.put(index_constant, order.get(index_move));
                       new_order.put(index_constant+1,order.get(index_constant));
                   }
                   else
                   {
                   new_order.put(i, order.get(i));
                   }
               }
               else if(i>index_constant && i< index_move)
               {
                   new_order.put(i+1,order.get(i));
               }
               else if(i== index_move)
               {
                   continue;
               }
               else
               {
                   new_order.put(i,order.get(i));
                   
               }
           }
           if(this.orderValidity2(new_order,constraints,order.get(index_move)))
           {
               
               System.out.println("activity can be moved, valid plan");
               
               return new_order;
           }
           else
           {
              System.out.println("activity cannot be moved, invalid plan");
              return neighbors;
           }
        }
        
        public boolean pairValidity(Node one_node, Node two_node)
        {
            HashMap<String,String> resources = this.resources;
            if(one_node.Type.equals("Leaf") && two_node.Type.equals("Leaf"))
            {
                ArrayList<String[]> precedence = constraints.get("Precedence");
                if(precedence==null)
                {
                    return true;
                }
                for(int i=0;i<precedence.size();i++)
                {
                    String[] check = precedence.get(i);
                    String resource_a = resources.get(one_node.Name);
                    String resource_b = resources.get(two_node.Name);
                    
                    if((check[0].equals(one_node.Name) && check[1].equals(two_node.Name)) || resource_a.equals(resource_b) )
                    {
                        //System.out.println("pair not eligible");
                        return false;
                        
                    }
                    
                    if(check[1].equals(one_node.Name) && check[0].equals(two_node.Name) || resource_a.equals(resource_b))
                    {
                        //System.out.println("pair not eligible");
                        return false;
                    }
                }
            }
            
            if(one_node.Type.equals("Leaf") && !two_node.Type.equals("Leaf"))
            {
                //System.out.println(one_node.Name);
                ArrayList<String[]> precedence = constraints.get("Precedence");
                for(int i=0;i<precedence.size();i++)
                {
                    String resource_a = resources.get(one_node.Name);
                    String resource_b = resources.get(two_node.Name);
                    String[] check = precedence.get(i);
                    HashMap<Integer,Node> neighbors = (HashMap<Integer,Node>) two_node.neighbors.clone();
                    Iterator it = neighbors.entrySet().iterator();
                    while(it.hasNext())
                    {
                        Map.Entry<Integer,Node> entry = (Map.Entry<Integer,Node>) it.next();
                        Node next_check =  entry.getValue();
                    if((check[0].equals(one_node.Name) && check[1].equals(next_check.Name)) || resource_a.equals(resource_b))
                    {
                        //System.out.println("pair not eligible");
                        return false;
                        
                    }
                    
                    
                    }
                }
            }
            
            if(!one_node.Type.equals("Leaf") && !two_node.Type.equals("Leaf"))
            {
                ArrayList<String[]> precedence = constraints.get("Precedence");
                for(int i=0;i<precedence.size();i++)
                {
                    
                    
                    String[] check = precedence.get(i);
                    HashMap<Integer,Node> neighbors_one = (HashMap<Integer,Node>) one_node.neighbors.clone();
                    HashMap<Integer,Node> neighbors_two = (HashMap<Integer,Node>) two_node.neighbors.clone();
                    Iterator it = neighbors_one.entrySet().iterator();
                    while(it.hasNext())
                    {
                        Map.Entry<Integer,Node> entry = (Map.Entry<Integer,Node>) it.next();
                        Node next_check_first =  entry.getValue();
                        Iterator it2 = neighbors_two.entrySet().iterator();
                        while(it2.hasNext())
                        {
                            Map.Entry<Integer,Node> entry2 = (Map.Entry<Integer,Node>) it2.next();
                            Node next_check_second =  entry2.getValue();
                            String resource_a = resources.get(next_check_first.Name);
                            String resource_b = resources.get(next_check_second.Name);
                            
                            if((check[0].equals(next_check_first.Name) && check[1].equals(next_check_second.Name))  || resource_a.equals(resource_b))
                            {
                               //System.out.println("pair not eligible");
                               //System.out.println(one_node.Name + " , " + two_node.Name);
                               return false;
                        
                             }
                            else
                            {
                                //System.out.println(next_check_first.Name + "   , " + next_check_second.Name);
                            }
                            
                        }
                    }

                }
            }
            
            if(!one_node.Type.equals("Leaf") && two_node.Type.equals("Leaf"))
            {
                ArrayList<String[]> precedence = constraints.get("Precedence");
                for(int i=0;i<precedence.size();i++)
                {
                    String[] check = precedence.get(i);
                    HashMap<Integer,Node> neighbors_one = (HashMap<Integer,Node>) one_node.neighbors.clone();
                    Iterator it = neighbors_one.entrySet().iterator();
                    while(it.hasNext())
                    {
                      Map.Entry<Integer,Node> entry = (Map.Entry<Integer,Node>) it.next();
                      Node next_check_first =  entry.getValue();
                      String resource_a = resources.get(next_check_first.Name);
                      String resource_b = resources.get(two_node.Name);
                      
                      if((check[0].equals(next_check_first.Name) && check[1].equals(two_node.Name))  || resource_a.equals(resource_b))
                            {
                               //System.out.println("pair not eligible");
                               //System.out.println(one_node.Name + " , " + two_node.Name);
                               return false;
                        
                             }
                            else
                            {
                                //System.out.println(next_check_first.Name + "   , " + next_check_second.Name);
                            }
                    }
                    
                }    
            }
            
            System.out.println("pair eligible for parallel execution: " + one_node.Name + " , " + two_node.Name);
            
            return true;
        }
        
        public boolean constraintValidity(Node one_node, Node two_node)
        {
            
            if(one_node.Type.equals("Leaf") && two_node.Type.equals("Leaf"))
            {
                ArrayList<String[]> precedence = constraints.get("Precedence");
                if(precedence==null)
                {
                    return true;
                }
                for(int i=0;i<precedence.size();i++)
                {
                    String[] check = precedence.get(i);
                    
                    
                    if((check[0].equals(one_node.Name) && check[1].equals(two_node.Name))  )
                    {
                        //System.out.println("pair not eligible");
                        return false;
                        
                    }
                    
                    if(check[1].equals(one_node.Name) && check[0].equals(two_node.Name) )
                    {
                        //System.out.println("pair not eligible");
                        return false;
                    }
                }
            }
            
            if(one_node.Type.equals("Leaf") && !two_node.Type.equals("Leaf"))
            {
                //System.out.println(one_node.Name);
                ArrayList<String[]> precedence = constraints.get("Precedence");
                for(int i=0;i<precedence.size();i++)
                {
                   
                    String[] check = precedence.get(i);
                    HashMap<Integer,Node> neighbors = (HashMap<Integer,Node>) two_node.neighbors.clone();
                    Iterator it = neighbors.entrySet().iterator();
                    while(it.hasNext())
                    {
                        Map.Entry<Integer,Node> entry = (Map.Entry<Integer,Node>) it.next();
                        Node next_check =  entry.getValue();
                    if((check[0].equals(one_node.Name) && check[1].equals(next_check.Name)))
                    {
                        //System.out.println("pair not eligible");
                        return false;
                        
                    }
                    
                    
                    }
                }
            }
            
            if(!one_node.Type.equals("Leaf") && !two_node.Type.equals("Leaf"))
            {
                ArrayList<String[]> precedence = constraints.get("Precedence");
                for(int i=0;i<precedence.size();i++)
                {
                    
                    
                    String[] check = precedence.get(i);
                    HashMap<Integer,Node> neighbors_one = (HashMap<Integer,Node>) one_node.neighbors.clone();
                    HashMap<Integer,Node> neighbors_two = (HashMap<Integer,Node>) two_node.neighbors.clone();
                    Iterator it = neighbors_one.entrySet().iterator();
                    while(it.hasNext())
                    {
                        Map.Entry<Integer,Node> entry = (Map.Entry<Integer,Node>) it.next();
                        Node next_check_first =  entry.getValue();
                        Iterator it2 = neighbors_two.entrySet().iterator();
                        while(it2.hasNext())
                        {
                            Map.Entry<Integer,Node> entry2 = (Map.Entry<Integer,Node>) it2.next();
                            Node next_check_second =  entry2.getValue();
                            
                            
                            if((check[0].equals(next_check_first.Name) && check[1].equals(next_check_second.Name)))
                            {
                               //System.out.println("pair not eligible");
                               //System.out.println(one_node.Name + " , " + two_node.Name);
                               return false;
                        
                             }
                            else
                            {
                                //System.out.println(next_check_first.Name + "   , " + next_check_second.Name);
                            }
                            
                        }
                    }

                }
            }
            
            if(!one_node.Type.equals("Leaf") && two_node.Type.equals("Leaf"))
            {
                ArrayList<String[]> precedence = constraints.get("Precedence");
                for(int i=0;i<precedence.size();i++)
                {
                    String[] check = precedence.get(i);
                    HashMap<Integer,Node> neighbors_one = (HashMap<Integer,Node>) one_node.neighbors.clone();
                    Iterator it = neighbors_one.entrySet().iterator();
                    while(it.hasNext())
                    {
                      Map.Entry<Integer,Node> entry = (Map.Entry<Integer,Node>) it.next();
                      Node next_check_first =  entry.getValue();
                      
                      
                      if((check[0].equals(next_check_first.Name) && check[1].equals(two_node.Name)) )
                            {
                               //System.out.println("pair not eligible");
                               //System.out.println(one_node.Name + " , " + two_node.Name);
                               return false;
                        
                             }
                            else
                            {
                                //System.out.println(next_check_first.Name + "   , " + next_check_second.Name);
                            }
                    }
                    
                }    
            }
            
            
            
            return true;
        }
        
        
        public HashMap<Integer,Node> orderValidity(HashMap<Integer,Node> plan, HashMap<String,ArrayList<String[]>> constraints, Node reordered)
        {
            ArrayList<String[]> precedence = constraints.get("Precedence");
            Iterator it = plan.entrySet().iterator();
            String name = reordered.Name;
            
            int i=0;
            int index=0;
            while(it.hasNext())
            {
                Map.Entry<Integer,Node> entry = (Map.Entry<Integer,Node>) it.next();
                if(entry.getValue().Name.equals(name))
                {
                   index = i;
                }
                i++;
            }
            
            
            if(!reordered.Type.equals("Leaf"))
            {
                Iterator it2 = reordered.neighbors.entrySet().iterator();
                while(it2.hasNext())
                {
                  Map.Entry<Integer,Node> entry2 = (Map.Entry<Integer,Node>) it2.next();
                  Node first_check = entry2.getValue();
                  String first_check_name = first_check.Name;
                  for(int j=0;j<plan.size();j++)
                  {
                      if(j>index)
                      {
                          String second_check_name = plan.get(j).Name;
                          String temp[] = new String[2];
                          temp[0]=second_check_name;
                          temp[1]=first_check_name;
                          for(int k=0;k<precedence.size();k++)
                          {
                              String temp2[] = precedence.get(k);
                              if(temp2[0].equals(temp[0]) && temp2[1].equals(temp[1]))
                              {
                                                                   
                                HashMap<Integer,Node> new_plan = this.reOrder(temp2[0], temp2[1], plan, constraints);
                                plan = new_plan;
                                 
                               
                              }
                          }
                      }
                  }
                }
               
            }
             return plan;
        }
        
        public boolean orderValidity2(HashMap<Integer,Node> plan, HashMap<String,ArrayList<String[]>> constraints, Node reordered)
        {
            ArrayList<String[]> precedence = constraints.get("Precedence");
            Iterator it = plan.entrySet().iterator();
            String name = reordered.Name;
            System.out.println("Based on pair for parallel exeuction activity:  " + name + "  needs to be moved upstream (if possible) for plan to be valid");
            int i=0;
            int index=0;
            while(it.hasNext())
            {
                Map.Entry<Integer,Node> entry = (Map.Entry<Integer,Node>) it.next();
                if(entry.getValue().Name.equals(name))
                {
                   index = i;
                }
                i++;
            }
            
            
            if(!reordered.Type.equals("Leaf"))
            {
                Iterator it2 = reordered.neighbors.entrySet().iterator();
                while(it2.hasNext())
                {
                  Map.Entry<Integer,Node> entry2 = (Map.Entry<Integer,Node>) it2.next();
                  Node first_check = entry2.getValue();
                  String first_check_name = first_check.Name;
                  for(int j=0;j<plan.size();j++)
                  {
                      if(j>index)
                      {
                          String second_check_name = plan.get(j).Name;
                          String temp[] = new String[2];
                          temp[0]=second_check_name;
                          temp[1]=first_check_name;
                          for(int k=0;k<precedence.size();k++)
                          {
                              String temp2[] = precedence.get(k);
                              if(temp2[0].equals(temp[0]) && temp2[1].equals(temp[1]))
                              {
                                  
                                  return false;
                                  //this.reOrder(temp2[0], temp2[1], plan, constraints);
                              }
                          }
                      }
                  }
                }
            }else
            {
                for(int j=0;j<plan.size();j++)
                {
                   if(j>index)
                      {
                          Node second_check_node = plan.get(j);
                          String temp[] = new String[2];
                          //temp[0]=second_check_name;
                          //temp[1]=name;
                          for(int k=0;k<precedence.size();k++)
                          {
                              
                            if(!this.constraintValidity(second_check_node,reordered))
                            {
                                return false;
                            }
                          }
                      } 
                }
            }
            
            
            return true;
            
        }
        
        public void findSequences()
        {
            Node og_root = this.root;
            HashMap<Integer,Node> root_neighbors = og_root.neighbors;
            Node middle_root = root_neighbors.get(1);
            if(middle_root.Type.equals("Sequence"))
            {
                
                this.examineParallelism2(middle_root);
            }
            else
            {
               HashMap<Integer,Node> middle_root_neighbors = middle_root.neighbors;
                for(int i=0;i<middle_root_neighbors.size();i++)
                {
                    Node sequence = middle_root_neighbors.get(i);
                    this.examineParallelism2(sequence);
                }
            }
            
        }
        
        
    }
    

