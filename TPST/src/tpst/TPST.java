/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tpst;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author kostis
 */
public class TPST {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
       
        
        
            
        double[] path = {0.8,0.2};
        
        Node root = new Node("Sequence");
        Tree tree = new Tree(root);
        
        double[] path2={0.05,0.95};
        Node start = new Node("Start Event - Reception of a Reimbursement Request","Leaf",0,1);
        Node event = new Node("AND/XOR","AND/XOR",path2);
        Node end = new Node("End Event","Leaf",0,1);
        Node sequence2 = new Node("Sequence");
        Node days = new Node("7 Days","Leaf",56,1);
        Node email = new Node("Send Email to Employee - Treatment In Progress","Leaf", 0.01666666666 ,1);
        Node days2 = new Node("23 Days", "Leaf",184,1);
        Node notice = new Node("Send Email to Employee - Notice of Re-Submission","Leaf", 0.01666666666 ,1);
        Node sequence3 = new Node("Sequence");
        Node validate = new Node("Validate if Employee Account Exists","Leaf",8,1);
        Node Xor1 = new Node("XOR_Block1","XOR",path);
        Node create = new Node("Create Employee Account","Leaf",8,1);
        Node blank = new Node("Blank path","Leaf",0,1);
        Node blank2 = new Node("Blank path","Leaf",0,1);
        Node analysis = new Node("Analyze the Request for Automatic Authorization","Leaf",1,1);
        Node xor2 = new Node("XOR_Block2","XOR",path);
        Node transfer = new Node("Transfer the Money to the Employee Account","Leaf",16,1);
        Node sequence4 = new Node("Sequence");
        Node supervisor = new Node("Review and Approve Request (Supervisor)", "Leaf",8,0.4);
        double[] path3 = {0.6,0.4};
        Node xor3 = new Node("XOR_Block3","XOR",path3);
        Node transfer2 = new Node("Transfer Money to the Employee Account", "Leaf",2,1);
        Node advise = new Node("Advise the Employee of the Rejection of the Request","Leaf",1,1);
        
        
        root.insertNeighbor(start);
        root.insertNeighbor(event);
        root.insertNeighbor(end);
        event.insertNeighbor(sequence2);
        sequence2.insertNeighbor(days);
        sequence2.insertNeighbor(email);
        sequence2.insertNeighbor(days2);
        sequence2.insertNeighbor(notice);
        
        event.insertNeighbor(sequence3);
        sequence3.insertNeighbor(validate);
        sequence3.insertNeighbor(Xor1);
        Xor1.insertNeighbor(blank);
        Xor1.insertNeighbor(create);
        sequence3.insertNeighbor(analysis);
        sequence3.insertNeighbor(xor2);
        sequence3.insertNeighbor(xor3);
        xor2.insertNeighbor(blank2);
        xor2.insertNeighbor(supervisor);
        
        xor3.insertNeighbor(transfer);
        xor3.insertNeighbor(advise);
        
        
        
        
        String[] constraint1 = new String[2];
        constraint1[0] = "Analyze the Request for Automatic Authorization";
        constraint1[1] = "Transfer the Money to the Employee Account";
        HashMap<String,ArrayList<String[]>> constraints = new HashMap<>();
        ArrayList<String[]> temp = new ArrayList<>();
        temp.add(constraint1);
        String[] constraint2 = {"Review and Approve Request (Supervisor)","Transfer the Money to the Employee Account"};
        String[] constraint3 = {"Review and Approve Request (Supervisor)","Advise the Employee of the Rejection of the Request"};
        String[] constraint4 = {"Validate if Employee Account Exists","Create Employee Account"};
        String[] constraint5 = {"Create Employee Account","Analyze the Request for Automatic Authorization"};
        String[] constraint6 = {"Create Employee Account","Transfer the Money to the Employee Account"};
        String[] constraint7 = {"Create Employee Account","Advise the Employee of the Rejection of the Request"};
        String[] constraint8 = {"Validate if Employee Account Exists","Transfer the Money to the Employee Account"};
        String[] constraint9 = {"Validate if Employee Account Exists","Advise the Employee of the Rejection of the Request"};
        String[] constraint10 = {"Analyze the Request for Automatic Authorization","Review and Approve Request (Supervisor)"};
        String[] constraint11 = {"7 Days","Send Email to Employee - Treatment In Progress"};
        String[] constraint12 = {"7 Days", "23 Days"};
        String[] constraint13 = {"7 Days","Send Email to Employee - Notice of Re-Submission"};
        String[] constraint14 = {"Send Email to Employee - Treatment In Progress", "23 Days"};
        String[] constraint15 = {"Send Email to Employee - Treatment In Progress", "Send Email to Employee - Notice of Resubmission"};
        String[] constraint16 = {"Send Email to Employee - Notice of Resubmission", "23 Days"};
        String[] constraint18= {"Validate if Employee Account Exists","Analyze the Request for Automatic Authorization"};
        temp.add(constraint2);
        temp.add(constraint3);
        temp.add(constraint4);
        temp.add(constraint5);
        temp.add(constraint6);
        temp.add(constraint7);
        temp.add(constraint8);
        temp.add(constraint9);
        temp.add(constraint10);
        temp.add(constraint11);
        temp.add(constraint12);
        temp.add(constraint13);
        temp.add(constraint14);
        temp.add(constraint15);
        temp.add(constraint16);
        //temp.add(constraint18);
        String resourceA = "Clerk";
        String resourceB = "Supervisor";
        String resourceC = "Automated";
        HashMap<String,String> resources = new HashMap<>();
        resources.put("Review and Approve Request (Supervisor)",resourceB);
        resources.put("Validate if Employee Account Exists",resourceA);
        resources.put("Create Employee Account",resourceA);
        resources.put("Transfer the Money to the Employee Account",resourceA);
        resources.put("Advise the Employee of the Rejection of the Request", resourceA);
        resources.put("Analyze the Request for Automatic Authorization",resourceA);
        resources.put("Blank path", resourceA);
        resources.put("7 Days", resourceC);
        resources.put("Send Email to Employee - Treatment In Progress", resourceC);
        resources.put("Send Email to Employee - Notice of Re-Submission", resourceC);
        resources.put("23 Days", resourceC);
        
        tree.resources = resources;
        constraints.put("Precedence", temp);
        tree.print();
        System.out.println();
        System.out.println();
        System.out.println();
        tree.constraints=constraints;
        
        tree.findSequences();
        
        
        
    }
    
}
    
  
  
