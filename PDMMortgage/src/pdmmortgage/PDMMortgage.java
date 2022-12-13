/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdmmortgage;

/**
 *
 * @author kostis
 */
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Scanner; 
import java.util.concurrent.atomic.AtomicBoolean;


/**
 *
 * @author Κωστής
 */
public class PDMMortgage{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException{
        int y=0;
        
        
        int b=10;
         while(b !=0 && b != 1)
        {
        Scanner scan = new Scanner(System.in);
        System.out.println("Please choose type of PDM implementation.");
        System.out.println("Enter 0 for baseline (original) implementation or 1 for parallelized (extended). ");
        try{
        b = scan.nextInt();
        } catch(InputMismatchException e){
            System.out.println("Unsupported input, please enter either 0 or 1.");
        }
        }
         
        if(b==1)
        {
        int s=1000;
        int resource_mapping = 0;
        int execution_mode=100;
        while(s !=0 && s != 1)
        {
        Scanner scan = new Scanner(System.in);
        System.out.println("Please choose distribution for generation of the operations' attributes.");
        System.out.println("Enter 0 for uniform distribution or 1 for gaussian distribution. ");
        try{
        s = scan.nextInt();
        } catch(InputMismatchException e){
            System.out.println("Unsupported input, please enter either 0 or 1.");
        }
        }
        
        while(execution_mode !=0 && execution_mode != 1)
        {
        Scanner scan = new Scanner(System.in);
        System.out.println("Please choose execution mode.");
        System.out.println("Enter 0 for execution mode \"executable\" or 1 for \"resource-depletion\". ");
        try{
        
        execution_mode = scan.nextInt();
        } catch(InputMismatchException e){
            System.out.println("Unsupported input, please enter either 0 or 1.");
        }
        }
        int choice_number = 13;
        int instances =1;
        int[][] sum = new int [instances][choice_number];
        int[][] sum2= new int [instances][choice_number];
        double[][] deviation = new double [instances][choice_number]; 
        int[][] optimal = new int [instances][choice_number];
        int[][] success = new int [instances][choice_number];
        int[][] failure = new int [instances][choice_number];
        int[][] early = new int[instances][choice_number];
        int[][] cost_failure = new int[instances][choice_number]; 
        
        int debug=0;
        
        PrintWriter[][] pw = new PrintWriter[instances][choice_number];
        String[] heuristics = {"Random","Lowest-Cost","Shortest-Time","Lowest-Fail-Probability","Root-Distance","Cost-Distance","Time-Distance","Rank-based","Rank-based-Time","Rank-based-Combination","Rank-based-extended","Rank-Based-extended-Time","Rank-Based-extended-Combo"};
        HashMap<String,Integer> resources = new HashMap<String,Integer>();
        resources.put("resourceA",2);
        resources.put("resourceB",2);
        //resources.put("resourceC",1);
        int p=0;
        while(p<instances)
        {
        while(y<choice_number)
        {
           String filename = heuristics[y] + (p+1)+  ".txt";
           pw[p][y] = new PrintWriter(new FileWriter(filename));
           
           y++;
        }
        p++;
        y=0;
        }
        y=0;
        while(y<1000)
        {
        
        
        RandomGaussian gaussian = new RandomGaussian();
        double min =0.001, max =1.0;
        
        Integer[] instances_matrix = new Integer[instances];
        for(int insta=0;insta<instances;insta++)
        {
            instances_matrix[insta] = (insta);
        }
        Double[] current_time = new Double[instances];
        Double[] starting_time = new Double[instances];
        HashMap<Integer,Integer[]> min_index = new HashMap<>();
        for(int i=0;i<instances;i++)
        {
            starting_time[i] = 0.0;
            current_time[i] = 0.0;
        }
        Double[] cost = new Double[instances];
        Double[] time = new Double[instances];
        double[][] indexes = new double[instances][3];
        int minInt=1, maxInt=10;
        int minInt2=1, maxInt2=10;
        Random r = new Random();
        
        HashMap<Integer, String[]> operations = new HashMap();
        
        double random;
        int result;
        //operation format {"resource","input1",....,"inputN","output","time","cost","probability"}
        if(s==1)
        {
            String[] temp = {"resourceA","B","C","D","A","1","5","0.95"};
            random = gaussian.getRandProb(1-(Double.parseDouble(temp[temp.length-1])));
            result = gaussian.getRandInt(Double.parseDouble(temp[temp.length-2]));
            temp[temp.length-1] = String.valueOf(random);
            temp[temp.length-2] = String.valueOf(result);
            result = gaussian.getRandInt2(Double.parseDouble(temp[temp.length-3]));
            temp[temp.length-3] = String.valueOf(result);
            operations.put(1,temp);
            String[] temp2 = {"resourceA","F","G","H","C","4","5","0.95"};
            random = gaussian.getRandProb(1-(Double.parseDouble(temp2[temp2.length-1])));
            temp2[temp2.length-1] = String.valueOf(random);
            result = gaussian.getRandInt(Double.parseDouble(temp2[temp2.length-2]));
            temp2[temp2.length-2] = String.valueOf(result);
            result = gaussian.getRandInt2((Double.parseDouble(temp2[temp2.length-3])));
            temp2[temp2.length-3] = String.valueOf(result);
            operations.put(2,temp2);
            String[] temp3 = {"resourceA","H","A","3","9","0.95"};
            random = gaussian.getRandProb(1-(Double.parseDouble(temp3[temp3.length-1])));
            temp3[temp3.length-1] = String.valueOf(random);
            result = gaussian.getRandInt(Double.parseDouble(temp3[temp3.length-2]));
            temp3[temp3.length-2] = String.valueOf(result);
            result = gaussian.getRandInt2((Double.parseDouble(temp3[temp3.length-3])));
            temp3[temp3.length-3] = String.valueOf(result);
            operations.put(3,temp3);
            String[] temp4 = {"resourceA","E","A","2","2","1"};
            random = gaussian.getRandProb(1-(Double.parseDouble(temp4[temp4.length-1])));
            temp4[temp4.length-1] = String.valueOf(random);
            result = gaussian.getRandInt(Double.parseDouble(temp4[temp4.length-2]));
            temp4[temp4.length-2] = String.valueOf(result);
            result = gaussian.getRandInt2((Double.parseDouble(temp4[temp4.length-3])));
            temp4[temp4.length-3] = String.valueOf(result);
            operations.put(4,temp4);
            String[] temp5 = {"resourceA","B","1","0","1"};
            random = gaussian.getRandProb(1-(Double.parseDouble(temp5[temp5.length-1])));
            temp5[temp5.length-1] = String.valueOf(random);
            result = gaussian.getRandInt(Double.parseDouble(temp5[temp5.length-2]));
            temp5[temp5.length-2] = String.valueOf(result);
            result = gaussian.getRandInt2(Double.parseDouble(temp5[temp5.length-3]));
            temp5[temp5.length-3] = String.valueOf(result);
            operations.put(5,temp5);
            String[] temp6 = {"resourceA","D","1","0","1"};
            random = gaussian.getRandProb(1-(Double.parseDouble(temp6[temp6.length-1])));
            temp6[temp6.length-1] = String.valueOf(random);
            result = gaussian.getRandInt(Double.parseDouble(temp6[temp6.length-2]));
            temp6[temp6.length-2] = String.valueOf(result);
            result = gaussian.getRandInt2(Double.parseDouble(temp6[temp6.length-3]));
            temp6[temp6.length-3] = String.valueOf(result);
            operations.put(6,temp6);
            String[] temp7 = {"resourceA","E","1","1","0.5"};
            random = gaussian.getRandProb(1-(Double.parseDouble(temp7[temp7.length-1])));
            temp7[temp7.length-1] = String.valueOf(random);
            result = gaussian.getRandInt(Double.parseDouble(temp7[temp7.length-2]));
            temp7[temp7.length-2] = String.valueOf(result);
            result = gaussian.getRandInt2(Double.parseDouble(temp7[temp7.length-3]));
            temp7[temp7.length-3] = String.valueOf(result);
            operations.put(7,temp7);
            String[] temp8 = {"resourceA","F","1","0","1"};
            random = gaussian.getRandProb(1-(Double.parseDouble(temp8[temp8.length-1])));
            temp8[temp8.length-1] = String.valueOf(random);
            result = gaussian.getRandInt(Double.parseDouble(temp8[temp8.length-2]));
            temp8[temp8.length-2] = String.valueOf(result);
            result = gaussian.getRandInt2(Double.parseDouble(temp8[temp8.length-3]));
            temp8[temp8.length-3] = String.valueOf(result);
            operations.put(8,temp8);
            String[] temp9 = {"resourceA","G","2","0","1"};
            random = gaussian.getRandProb(1-(Double.parseDouble(temp9[temp9.length-1])));
            temp9[temp9.length-1] = String.valueOf(random);
            result = gaussian.getRandInt(Double.parseDouble(temp9[temp9.length-2]));
            temp9[temp9.length-2] = String.valueOf(result);
            result = gaussian.getRandInt2(Double.parseDouble(temp9[temp9.length-3]));
            temp9[temp9.length-3] = String.valueOf(result);
            operations.put(9,temp9);
            String[] temp10 = {"resourceA","H","10","3","0.85"};
            random = gaussian.getRandProb(1-(Double.parseDouble(temp10[temp10.length-1])));
            temp10[temp10.length-1] = String.valueOf(random);
            result = gaussian.getRandInt(Double.parseDouble(temp10[temp10.length-2]));
            temp10[temp10.length-2] = String.valueOf(result);
            result = gaussian.getRandInt2(Double.parseDouble(temp10[temp10.length-3]));
            temp10[temp10.length-3] = String.valueOf(result);
            operations.put(10,temp10);
        }
        else
        {
            random = ThreadLocalRandom.current().nextDouble(min, max);
            result = r.nextInt((maxInt - minInt) + 1) + minInt;
            String[] temp = {"resourceA","B","C","D","A","1","5","0.95"};
            temp[temp.length-1] = String.valueOf(random);
            temp[temp.length-2] = String.valueOf(result);
            result = r.nextInt((maxInt2 - minInt2) + 1) + minInt;
            temp[temp.length-3] = String.valueOf(result);
            operations.put(1,temp);
            String[] temp2 = {"resourceA","F","G","H","C","4","5","0.95"};
            random = ThreadLocalRandom.current().nextDouble(min, max);
            temp2[temp2.length-1] = String.valueOf(random);
            result = r.nextInt((maxInt - minInt) + 1) + minInt;
            temp2[temp2.length-2] = String.valueOf(result);
            result = r.nextInt((maxInt2 - minInt2) + 1) + minInt;
            temp2[temp2.length-3] = String.valueOf(result);
            operations.put(2,temp2);
            String[] temp3 = {"resourceB","H","A","3","9","0.95"};
            random = ThreadLocalRandom.current().nextDouble(min, max);
            temp3[temp3.length-1] = String.valueOf(random);
            result = r.nextInt((maxInt - minInt) + 1) + minInt;
            temp3[temp3.length-2] = String.valueOf(result);
            result = r.nextInt((maxInt2 - minInt2) + 1) + minInt;
            temp3[temp3.length-3] = String.valueOf(result);
            operations.put(3,temp3);
            String[] temp4 = {"resourceB","E","A","2","2","1"};
            random = ThreadLocalRandom.current().nextDouble(min, max);
            temp4[temp4.length-1] = String.valueOf(random);
            result = r.nextInt((maxInt - minInt) + 1) + minInt;
            temp4[temp4.length-2] = String.valueOf(result);
            result = r.nextInt((maxInt2 - minInt2) + 1) + minInt;
            temp4[temp4.length-3] = String.valueOf(result);
            operations.put(4,temp4);
            String[] temp5 = {"resourceA","B","1","0","1"};
            random = ThreadLocalRandom.current().nextDouble(min, max);
            temp5[temp5.length-1] = String.valueOf(random);
            result = r.nextInt((maxInt - minInt) + 1) + minInt;
            temp5[temp5.length-2] = String.valueOf(result);
            result = r.nextInt((maxInt2 - minInt2) + 1) + minInt;
            temp5[temp5.length-3] = String.valueOf(result);
            operations.put(5,temp5);
            String[] temp6 = {"resourceA","D","1","0","1"};
            random = ThreadLocalRandom.current().nextDouble(min, max);
            temp6[temp6.length-1] = String.valueOf(random);
            result = r.nextInt((maxInt - minInt) + 1) + minInt;
            temp6[temp6.length-2] = String.valueOf(result);
            result = r.nextInt((maxInt2 - minInt2) + 1) + minInt;
            temp6[temp6.length-3] = String.valueOf(result);
            operations.put(6,temp6);
            String[] temp7 = {"resourceB","E","1","1","0.5"};
            random = ThreadLocalRandom.current().nextDouble(min, max);
            temp7[temp7.length-1] = String.valueOf(random);
            result = r.nextInt((maxInt - minInt) + 1) + minInt;
            temp7[temp7.length-2] = String.valueOf(result);
            result = r.nextInt((maxInt2 - minInt2) + 1) + minInt;
            temp7[temp7.length-3] = String.valueOf(result);
            operations.put(7,temp7);
            String[] temp8 = {"resourceA","F","1","0","1"};
            random = ThreadLocalRandom.current().nextDouble(min, max);
            temp8[temp8.length-1] = String.valueOf(random);
            result = r.nextInt((maxInt - minInt) + 1) + minInt;
            temp8[temp8.length-2] = String.valueOf(result);
            result = r.nextInt((maxInt2 - minInt2) + 1) + minInt;
            temp8[temp8.length-3] = String.valueOf(result);
            operations.put(8,temp8);
            String[] temp9 = {"resourceA","G","2","0","1"};
            random = ThreadLocalRandom.current().nextDouble(min, max);
            temp9[temp9.length-1] = String.valueOf(random);
            result = r.nextInt((maxInt - minInt) + 1) + minInt;
            temp9[temp9.length-2] = String.valueOf(result);
            result = r.nextInt((maxInt2 - minInt2) + 1) + minInt;
            temp9[temp9.length-3] = String.valueOf(result);
            operations.put(9,temp9);
            String[] temp10 = {"resourceB","H","10","3","0.85"};
            random = ThreadLocalRandom.current().nextDouble(min, max);
            temp10[temp10.length-1] = String.valueOf(random);
            result = r.nextInt((maxInt - minInt) + 1) + minInt;
            temp10[temp10.length-2] = String.valueOf(result);
            result = r.nextInt((maxInt2 - minInt2) + 1) + minInt;
            temp10[temp10.length-3] = String.valueOf(result);
            operations.put(10,temp10);
        }
        HashMap<Integer,String[]> operations_graph = (HashMap) operations.clone();
        HashMap<Integer,String[]> operations_opt = (HashMap) operations.clone();
        Iterator print = operations.entrySet().iterator();
        
        String[] complete= {"7 4","10 3","10 9 8 2 5 6 1"};
        int[] path_costs = new int[3];
        int[] check;
        
        for(int i=0;i<complete.length;i++)
        {
            
            String[] temp_op = complete[i].split(" ");
            for(int j=0;j<temp_op.length;j++)
            {
                String[] temp_op2 = operations.get(Integer.parseInt(temp_op[j]));
                path_costs[i] += Integer.parseInt(temp_op2[temp_op2.length-2]);
            }
        }
        
        
        int temp_cost;
        String temp_path;
        for (int i = 0; i <path_costs.length; i++) {     
          for (int j = i+1; j <path_costs.length; j++) {     
              if(path_costs[i] >path_costs[j]) {      //swap elements if not in order
                 temp_cost = path_costs[i];
                 temp_path = complete[i];
                 complete[i] = complete[j];
                 path_costs[i] = path_costs[j];    
                 path_costs[j] = temp_cost;
                 complete[j] = temp_path;
               }     
            }     
        }
        int count = 10;
        int choice =0;
        HashMap<Integer,Map<Integer, String[]>> listOfMaps = new HashMap<>();
        int u=0;
         
        
        while(u<(choice_number*instances))
        {
           listOfMaps.put(u,(HashMap) operations.clone());
           u++;
          
           
        }
        
        
        
        HashMap<Integer,Integer> productions = new HashMap<Integer,Integer>();
        determineExecutionInstance(operations,productions);
        while(choice<choice_number)
        {
        current_time[0]=0.0;
        //current_time[1]=0;
        HashMap<Integer,ArrayList<Double>> listOfEnd = new HashMap<>();
        HashMap<Integer,ArrayList<String>> listOfAllocations = new HashMap<>();
        HashMap<Integer,HashMap<Integer, String[]>> listOfExecutables = new HashMap<>();
        HashMap<Integer,HashMap<Integer, String[]>> listOfExecutables2 = new HashMap<>();
        HashMap<Integer,HashSet<String>> listOfAvailables = new HashMap<>();
        HashMap<Integer,HashMap<String,Double>> listOfTimes = new HashMap<>();
        HashMap<Integer,Map<Integer, Integer>> listOfPaths = new HashMap<>();
        HashMap<Integer,HashSet<Integer>> listOfPrunes = new HashMap<>();
        HashMap<Integer,ArrayList<Double>> listOfStarts = new HashMap<>();
        HashMap<Integer,HashMap<Integer,Double>> listOfStarting = new HashMap<>();
        int inst=0;
        boolean next = false;
        while(inst<instances)
        {
         
         starting_time[inst] =0.0;
         current_time[inst] =0.0;
         Integer[] temp_inst =  new Integer[2];
         temp_inst[0] = temp_inst[1] = 0;
    
         
         min_index.put(inst,temp_inst);
         ArrayList<Double> temp_end = new ArrayList();
         ArrayList<String> temp_allocation = new ArrayList();
         ArrayList<Double> temp_start = new ArrayList();
         HashMap<Integer,String[]> temp_executable = new HashMap();
         HashMap<Integer, String[]> temp_executable2 = new HashMap();
         HashSet<String> temp_available = new HashSet();
         HashMap<Integer,Integer> temp_path2 = new HashMap<Integer,Integer>();
         HashSet<Integer> temp_prune = new HashSet<Integer>();
         HashMap<String,Double> times = new HashMap<>();
         HashMap<Integer,Double> temp_starting = new HashMap<>();
         
         listOfStarts.put(inst,temp_start);
         listOfTimes.put(inst,times);
         listOfEnd.put(inst,temp_end);
         listOfAllocations.put(inst,temp_allocation);
         listOfExecutables.put(inst,temp_executable);
         listOfAvailables.put(inst,temp_available);
         listOfPaths.put(inst,temp_path2);
         listOfPrunes.put(inst,temp_prune);
         listOfExecutables2.put(inst, temp_executable2);
         listOfStarting.put(inst, temp_starting);
         cost[inst]=0.0;
         time[inst]=0.0;
         inst++;
        }    
        inst=0;
        count =10;
        
        
        boolean[] complete_instance = new boolean[instances];
        boolean resourceAvailability=false;
        HashMap<Integer,String[]> removed_operations = new HashMap<>();
        while(count !=0)
        {
           inst=0;
           boolean resource_flag2 = false;
          while(inst<instances){
               
              
              int complete_count=0;
              for(int insta=0;insta<complete_instance.length;insta++)
              {
                  if(complete_instance[insta]==true)
                  {
                      complete_count++;
                  }
              }
              if(complete_count==complete_instance.length)
              {
                  count=0;
                  break;
              }
              
              if(complete_instance[inst]==true)
              {
               inst++;
              
               continue;
              }   
              
              operations= (HashMap)listOfMaps.get(choice + (inst * choice_number));
              
              
              HashMap<Integer,String[]> executable = new HashMap<Integer,String[]>();//new HashMap();
              HashMap<Integer,String[]> executable_without = new HashMap<Integer,String[]>();
        HashSet<String> available = listOfAvailables.get(inst);
        HashMap<Integer,Integer> path = (HashMap) listOfPaths.get(inst);
        HashSet<Integer> prune = listOfPrunes.get(inst);
        ArrayList<Double> end = listOfEnd.get(inst);
        Integer[] temp_inst = min_index.get(inst);
        ArrayList<String> allocation = listOfAllocations.get(inst);
        HashMap<String,Double> times = (HashMap) listOfTimes.get(inst);
        HashMap<Integer,Double> starting = (HashMap) listOfStarting.get(inst);
           Iterator it = operations.entrySet().iterator();
           ArrayList<Double> start = listOfStarts.get(inst);
           boolean available_input = false;
           boolean available_resources = false;
           while (it.hasNext()) //calculate executable operations
           {
            
            HashMap.Entry pair = (HashMap.Entry)it.next();
            String[] temp11 = (String[])pair.getValue();
            if(temp11.length==5) //cases where operation has no input elements
            {
                available_input = true;
                

                executable.put((Integer)pair.getKey(),(String[]) pair.getValue());
                executable_without.put((Integer)pair.getKey(),(String[]) pair.getValue());
                
                
            }
            else //cases where operation has input elements. so each input element is checked for availability
            {
              int temp12 = 0; // checks number of input elements produced
              int temp13 =0;  // checks number of input elements produced (until now)
              for(int i=1;i<temp11.length-4;i++)
              {
                if(available.contains(temp11[i]))//each input is checked
                {
                    
                    temp12++;
                    
                }
              }
              for(int i=1;i<temp11.length-4;i++)
              {
                if(!times.containsKey(temp11[i]))
                {
                    
                }
                else
                {
                    Double next_time = times.get(temp11[i]);
                    if(next_time<= current_time[inst])
                    {
                        temp13++;
                    }
                }
                  
              }
              
              if((temp12 == temp11.length -5) &&(temp13 == temp11.length -5))//if every input is available then the operation is available for execution
              {
                available_input = true;
               
               executable.put((Integer)pair.getKey(),(String[]) pair.getValue());
               
               
              }
              else if((temp12 == temp11.length -5) &&(temp13 != temp11.length -5))
              {
                  executable_without.put((Integer)pair.getKey(),(String[]) pair.getValue());
              }
            }
            
            }
           if(choice==10 || choice==11 || choice == 12)
           {
               if(prune.size()>0)
               {
                   for(Integer op : prune)
                   {
                       executable.remove(op);
                       executable_without.remove(op);
                   }
               }
           }
           
           if(execution_mode==1)
           {
           HashMap<String,Integer> resource_availability = findNumberAvailableResources(executable,resources,listOfAllocations,listOfEnd,starting_time[inst]);
           int entire_availability =0 ;
           Iterator resource_availability_it = resource_availability.entrySet().iterator();
           while(resource_availability_it.hasNext())
           {
               Map.Entry<String,Integer> resource_entry = (Map.Entry<String,Integer>) resource_availability_it.next();
               entire_availability += resource_entry.getValue();
             
               if(resource_entry.getValue()==0)
               {
                   Iterator executable_it = executable.entrySet().iterator();
                   while(executable_it.hasNext())
                   {
                        Map.Entry<Integer,String[]> operation_entry = (Map.Entry<Integer,String[]>) executable_it.next();
                        String[] operation_cand = operation_entry.getValue();
                        if(operation_cand[0].equals(resource_entry.getKey()))
                        {
                            executable_it.remove();
                        }
                   }
               }
           } // edo mexri oti na nai
           
           if(executable.isEmpty() && entire_availability==0)
           {
               starting_time[inst] = findEarliestStartingTime(resources,listOfAllocations,listOfEnd,starting_time[inst]);
                current_time[inst] = starting_time[inst];
                if(current_time[inst]==Double.MAX_VALUE)
                {
                    time[inst] = getDuration(end);
               System.out.println("Heuristic: " + heuristics[choice] + " instance: " + inst + "cost: " + cost[inst] + "     time: " + time[inst]);
               System.out.println("");
               String filename = heuristics[choice] + ".txt";
               
               //pw[choice].println((y+1)+","+cost+","+time);
               cost_failure[inst][choice] +=cost[inst];
               failure[inst][choice]+=1;
               
               complete_instance[inst] = true;    
               System.out.println("prin");
               break;
                }
                
                
                
                
                continue;
           }
           
           if(executable.isEmpty() && entire_availability!=0)
           {
               starting_time[inst] = findEarliestStartingTime(resources,listOfAllocations,listOfEnd,starting_time[inst]);
                current_time[inst] = starting_time[inst];
               continue;
           }
           }
           
           
           if(executable.isEmpty() && executable_without.isEmpty())//execution has completed since the production of A is no longer possible
           {
               
               if(choice==0)
               {
                   check = new int[complete.length];
                   for(int i=0;i<check.length;i++)
                   {
                       String optemp = complete[i];
                       String[] optemp2 = optemp.split(" ");
                       for(int j=0;j<optemp2.length;j++)
                       {
                           if(path.containsKey(Integer.parseInt(optemp2[j])))
                           {
                            Integer op = path.get(Integer.parseInt(optemp2[j]));
                            if(op.equals(1))
                            {
                               check[i]+=1;
                            }
                           }
                       }
                   }
                   
                   for(int i=0;i<check.length;i++)
                   {
                       if(check[i]==0)
                       {
                           
                           
                           
                       }
                   }
                   
               }
               
               
               time[inst] = getDuration(end);
               System.out.println("Heuristic: " + heuristics[choice] + " instance: " + inst + "cost: " + cost[inst] + "     time: " + time[inst]);
               System.out.println("");
               String filename = heuristics[choice] + ".txt";
               
               
               cost_failure[inst][choice] +=cost[inst];
               failure[inst][choice]+=1;
               
               complete_instance[inst] = true;              
               break;
           }
           AtomicBoolean resource_flag = new AtomicBoolean(false); 
          

           
           StringBuilder resource_choice= new StringBuilder("");
           if(executable.isEmpty() && !executable_without.isEmpty())//execution has completed since the production of A is no longer possible
           {
           
            System.out.println(executable_without.keySet());
            int key;
            if(choice==0)
             {
              key=executeRandom(executable_without,available,operations,path,productions);
              }
           else if(choice==1)
              {
               key=executeLowestCost(executable_without,available,operations,path,productions);
              }
           else if(choice==2)
              {
               key=executeShortestTime(executable_without,available,operations,path,productions); 
              }
           else if(choice==3)
           {
              key=executeLowestFail(executable_without,available,operations,path,productions);
           }
           else if(choice==4)
           {
             
              key=rootDistance(executable_without,available,operations_graph,path,productions);
            
           }
           else if(choice==5)
           {
             
               key=costDistance(executable_without,available,operations_graph,path,productions);
                
               
           }
           else if(choice==6)
           {
              
               key=timeDistance(executable_without,available,operations_graph,path,productions);
               
           }
           else if(choice==7)
           {
              
               key=knockoutPath(executable_without,available,operations_graph);
               
              
              
              
           }
           else if(choice==8)
           {   
               
               key=knockoutPathTime(executable_without,available,operations_graph);
               
               
              
           }
           else if (choice==9)
           {
              
               key=knockoutPathComb(executable_without,available,operations_graph);
               
               
           }
           else if (choice==10)
           {
              
              key=knockoutPathPruneBoth(executable_without,available,operations_graph);
              
              
           }
           else if(choice==11)
           {
             
              key=knockoutPathPruneBothTime(executable_without,available,operations_graph);
            
              
           }
           else
           {
               
               key=knockoutPathPruneBothCombo(executable_without,available,operations_graph);
               
           }
           
           
            
            
            
            current_time[inst] = findStartingTime(operations,key,listOfEnd,listOfAllocations,instances_matrix,current_time[inst],resources,times,starting_time[inst],indexes,inst);
            
            
            
            continue;           
           }
           
           
           int key;
           if(choice==0)
           {
               
            key=executeRandom(executable,available,operations,path,productions);
               
           }
           else if(choice==1)
           {
             
            key=executeLowestCost(executable,available,operations,path,productions);
           
           }
           else if(choice==2)
           {
               
            key=executeShortestTime(executable,available,operations,path,productions); 
               
           }
           else if(choice==3)
           {
             
            key=executeLowestFail(executable,available,operations,path,productions);
             
           }
           else if(choice==4)
           {
              
            key=rootDistance(executable,available,operations_graph,path,productions);
           
           }
           else if(choice==5)
           {
              
            key=costDistance(executable,available,operations_graph,path,productions);
                
           }
           else if(choice==6)
           {

            key=timeDistance(executable,available,operations_graph,path,productions);
           
           }
           else if(choice==7)
           {
              
            key=knockoutPath(executable,available,operations_graph);
               
           }
           else if(choice==8)
           {   
               
            key=knockoutPathTime(executable,available,operations_graph);
               
           }
           else if (choice==9)
           {
              
               key=knockoutPathComb(executable,available,operations_graph);
               
               
               
           }
           else if (choice==10)
           {
             
              key=knockoutPathPruneBoth(executable,available,operations_graph);
              
              
               
           }
           else if(choice==11)
           {
            
            key=knockoutPathPruneBothTime(executable,available,operations_graph);
             
           }
           else
           {
               
            key=knockoutPathPruneBothCombo(executable,available,operations_graph);
               
           }
           
           
           
           
           
           String[] temp16 = executable.get(key);
           int result_execution = productions.get(key);
          
           
           
           
           if(execution_mode==0)
           {
               
            current_time[inst] = findStartingTime(operations,key,listOfEnd,listOfAllocations,instances_matrix,current_time[inst],resources,times,starting_time[inst],indexes,inst);
               
               
            }
           if(result_execution ==1) // the execution was successful and therefore the element is produced and added to the set of available elements
           {
            
            available.add(temp16[temp16.length-4]);
            starting.put(key, current_time[inst]);
            starting_time[inst] = current_time[inst];
            if(resource_flag.get()==false)
            {
            Double end_time = current_time[inst] + Double.parseDouble(temp16[temp16.length-3]);
            
            System.out.println("Heuristic: " + heuristics[choice] + " Op"  + key+ "," + temp16[temp16.length-4] + " , instance: " + inst + " start_time: " + current_time[inst] + " , end time:" + end_time + "duration: " + Double.parseDouble(temp16[temp16.length-3]) + "resource: " + temp16[0] + "cost: " + Double.parseDouble(temp16[temp16.length-2]));
            
            if(execution_mode==0)
            {
               current_time[inst] = end_time; 
            }
            path.put(key,0);
            allocation.add(temp16[0]);
            end.add(end_time);
            times.put((temp16[temp16.length-4]),end_time);
            start.add(starting_time[inst]);
           
            }
            else
            {
             
             
            }
           }
           else //execution was unsuccessful and therefore the element was not produced
           {
            if(resource_flag.get()==false)
            {
            Double end_time = current_time[inst] + Double.parseDouble(temp16[temp16.length-3]);
            starting.put(key, current_time[inst]);
            System.out.println("Heuristic: " + heuristics[choice] + " Op"  + key+ "," + temp16[temp16.length-4] + "   fail, instance: " + inst + " start_time: " + current_time[inst] + " , end time:" + end_time + " duration: " + Double.parseDouble(temp16[temp16.length-3]) + "resource: " + temp16[0] + " cost: " + Double.parseDouble(temp16[temp16.length-2]));
                     
             if(execution_mode==0)
            {
               current_time[inst] = end_time; 
            }
            path.put(key,1);
            allocation.add(temp16[0]);
            end.add(end_time);
            
            times.put((temp16[temp16.length-4]),end_time);
            start.add(starting_time[inst]);
            
            
            }
            else
            {
                
                continue;
            }
           }
           
           
           String[] temp14 = operations.get(key);
           if(resource_mapping==0)
           {
           if(resource_flag.get()==false)
           {
            double cost2 = Double.valueOf(temp14[temp14.length-2]);
           
           double time2 = Double.valueOf(temp14[temp14.length-3]);
           cost[inst] = cost[inst] + cost2; //cost of the entire path is calculated in a step by step manner
           
           }
           }
           else
           {
            if(resource_flag.get()==false)
            {
             double cost2 = Double.valueOf(temp14[temp14.length-2]);
           
             double time2 = Double.valueOf(temp14[temp14.length-3]);
             cost[inst] = cost[inst] + cost2; //cost of the entire path is calculated in a step by step manner
             System.out.println("cost2");
            }
            else
            {
              double cost2 = Double.valueOf(temp14[temp14.length-2]);
              System.out.println("tywin");
              double time2 = Double.valueOf(temp14[temp14.length-3]);
              cost[inst] = cost[inst] + cost2;
              System.out.println("cost3");
            }
           }
           
           time[inst] = getDuration(end);
           operations.remove(key);//operation is removed from the list of operations as it is no longer available
           executable.remove(key);
           if(available.contains("A"))//if A has been produced then the execution is completed
           {
               success[inst][choice]+=1;
              
                   check = new int[complete.length];
                   for(int i=0;i<check.length;i++)
                   {
                       String optemp = complete[i];
                       String[] optemp2 = optemp.split(" ");
                       for(int j=0;j<optemp2.length;j++)
                       {
                           if(productions.containsKey(Integer.parseInt(optemp2[j])))
                           {
                            Integer op = productions.get(Integer.parseInt(optemp2[j]));
                            if(op.equals(1))
                            {
                               check[i]+=1;
                            }
                           }
                           
                       }
                   }
                   
                   int cp =0;
                   int cost_f =0;
                   int cost_opt =0;
                   for(int i=0;i<check.length;i++)
                   {
                       if(check[i]==0)
                       {
                           
                           
                           cp=i;
                           
                           break;
                       }
                   }
                    cost_opt = path_costs[cp];
                    Iterator it_productions = productions.entrySet().iterator();
                    while(it_productions.hasNext())
                    {
                       HashMap.Entry entry = (HashMap.Entry) it_productions.next();
                       if(entry.getValue().equals(1))
                       {
                           
                           String[] operation_temp = (String[]) operations_opt.get((Integer)entry.getKey());
                           
                           
                       }
                    }
                    int temp_m = cost_f + cost_opt;
                    optimal[inst][choice] += cost_opt;
                    deviation[inst][choice] += (cost[inst] - temp_m);
                   
               
               
               System.out.println("Heuristic: " + heuristics[choice] + " instance: " + inst + "cost: " + cost[inst] + "     time: " + time[inst]);
               System.out.println("");
               pw[inst][choice].println(cost[inst]+","+time[inst]);
               
                   debug++;
               
               sum[inst][choice] += cost[inst];
              sum2[inst][choice] += time[inst];
              complete_instance[inst] = true; 
               break;
           }
         listOfStarting.put(inst, starting);
         listOfEnd.put(inst,end);
         listOfStarts.put(inst,start);
         listOfAllocations.put(inst,allocation);
         
         listOfAvailables.put(inst,available);
         listOfPaths.put(inst,path);
         listOfPrunes.put(inst,prune);
         listOfTimes.put(inst,times);
         min_index.put(inst,temp_inst);
         listOfMaps.put(choice+(inst*choice_number),operations);
         
         if(execution_mode==0)
         {
           if(executable.size()==0)
           {
             next = true;
           }  
         
           if(next)
           {
            inst++;
           }
         }
            
          
           
          } 
        }//
        
        
        choice++;
        }//
        System.out.println();
        System.out.println();
        y++;
    
    }
        double result = 0.0;
        double result2 =0.0;
        double result3= 0.0;
       System.out.println("Sum of average cost and time for each heuristic for each instance:");
      
       for(int i=0;i<choice_number;i++)
      {
       for(int t=0;t<instances;t++)
      {
       result += (double) sum[t][i]/ (double)success[t][i]; //average execution cost
       
       result2 += (double) sum2[t][i]/ (double)success[t][i];
       
       result3 += (double) deviation[t][i]/(double)success[t][i];
       
       
      
          
      }
       System.out.println(heuristics[i] +" cost: " + result +  "  time: " +result2);
       result = result2 = result3 = 0.0;
    }
      
      
       
      for(int i=0;i<choice_number;i++)
      {
       //System.out.println(success[0][i]);   
      }
      
      
      
      
      
        }
        else
        {
          
         y=0;
        int s=10;
        while(s !=0 && s != 1)
        {
        Scanner scan = new Scanner(System.in);
        System.out.println("Please choose distribution for generation of the operations' attributes.");
        System.out.println("Enter 0 for uniform distribution or 1 for gaussian distribution. ");
        try{
        s = scan.nextInt();
        } catch(InputMismatchException e){
            System.out.println("Unsupported input, please enter either 0 or 1.");
        }
        }
        int[] sum = new int [13];
        int[] sum2= new int [13];
        double[] deviation = new double [13]; 
        int[] optimal = new int [13];
        int[] success = new int [13];
        int[] failure = new int [13];
        int[] early = new int[13];
        int[] cost_failure = new int[13]; 
        int debug=0;
        
        int choice_number = 13;
        PrintWriter[] pw = new PrintWriter[13];
       String[] heuristics = {"Random","Lowest-Cost","Shortest-Time","Lowest-Fail-Probability","Root-Distance","Cost-Distance","Time-Distance","Rank-based","Rank-based-Time","Rank-based-Combination","Rank-based-extended","Rank-Based-extended-Time","Rank-Based-extended-Combo","Minimize-Waiting-Time","Minimize Waiting Time Conditional"};
        while(y<choice_number)
        {
           String filename = heuristics[y] + ".txt";
           pw[y] = new PrintWriter(new FileWriter(filename));
           y++;
        }
        y=0;
        while(y<1000)
        {
        Integer cost =0,time=0;
        RandomGaussian gaussian = new RandomGaussian();
        double min =0.001, max =1.0;
        
        int minInt=0, maxInt=10;
        int minInt2=0, maxInt2=10;
        Random r = new Random();
        
        HashMap<Integer, String[]> operations = new HashMap();
        
        double random;
        int result;
        
        if(s==1)
        {
            String[] temp = {"B","C","D","A","1","5","0.95"};
            random = gaussian.getRandProb(1-(Double.parseDouble(temp[temp.length-1])));
            result = gaussian.getRandInt(Double.parseDouble(temp[temp.length-2]));
            temp[temp.length-1] = String.valueOf(random);
            temp[temp.length-2] = String.valueOf(result);
            result = gaussian.getRandInt2(Double.parseDouble(temp[temp.length-3]));
            temp[temp.length-3] = String.valueOf(result);
            operations.put(1,temp);
            String[] temp2 = {"F","G","H","C","4","5","0.95"};
            random = gaussian.getRandProb(1-(Double.parseDouble(temp2[temp2.length-1])));
            temp2[temp2.length-1] = String.valueOf(random);
            result = gaussian.getRandInt(Double.parseDouble(temp2[temp2.length-2]));
            temp2[temp2.length-2] = String.valueOf(result);
            result = gaussian.getRandInt2((Double.parseDouble(temp2[temp2.length-3])));
            temp2[temp2.length-3] = String.valueOf(result);
            operations.put(2,temp2);
            String[] temp3 = {"H","A","3","9","0.95"};
            random = gaussian.getRandProb(1-(Double.parseDouble(temp3[temp3.length-1])));
            temp3[temp3.length-1] = String.valueOf(random);
            result = gaussian.getRandInt(Double.parseDouble(temp3[temp3.length-2]));
            temp3[temp3.length-2] = String.valueOf(result);
            result = gaussian.getRandInt2((Double.parseDouble(temp3[temp3.length-3])));
            temp3[temp3.length-3] = String.valueOf(result);
            operations.put(3,temp3);
            String[] temp4 = {"E","A","2","2","1"};
            random = gaussian.getRandProb(1-(Double.parseDouble(temp4[temp4.length-1])));
            temp4[temp4.length-1] = String.valueOf(random);
            result = gaussian.getRandInt(Double.parseDouble(temp4[temp4.length-2]));
            temp4[temp4.length-2] = String.valueOf(result);
            result = gaussian.getRandInt2((Double.parseDouble(temp4[temp4.length-3])));
            temp4[temp4.length-3] = String.valueOf(result);
            operations.put(4,temp4);
            String[] temp5 = {"B","0","0","1"};
            random = gaussian.getRandProb(1-(Double.parseDouble(temp5[temp5.length-1])));
            temp5[temp5.length-1] = String.valueOf(random);
            result = gaussian.getRandInt(Double.parseDouble(temp5[temp5.length-2]));
            temp5[temp5.length-2] = String.valueOf(result);
            result = gaussian.getRandInt2(Double.parseDouble(temp5[temp5.length-3]));
            temp5[temp5.length-3] = String.valueOf(result);
            operations.put(5,temp5);
            String[] temp6 = {"D","0","0","1"};
            random = gaussian.getRandProb(1-(Double.parseDouble(temp6[temp6.length-1])));
            temp6[temp6.length-1] = String.valueOf(random);
            result = gaussian.getRandInt(Double.parseDouble(temp6[temp6.length-2]));
            temp6[temp6.length-2] = String.valueOf(result);
            result = gaussian.getRandInt2(Double.parseDouble(temp6[temp6.length-3]));
            temp6[temp6.length-3] = String.valueOf(result);
            operations.put(6,temp6);
            String[] temp7 = {"E","1","1","0.5"};
            random = gaussian.getRandProb(1-(Double.parseDouble(temp7[temp7.length-1])));
            temp7[temp7.length-1] = String.valueOf(random);
            result = gaussian.getRandInt(Double.parseDouble(temp7[temp7.length-2]));
            temp7[temp7.length-2] = String.valueOf(result);
            result = gaussian.getRandInt2(Double.parseDouble(temp7[temp7.length-3]));
            temp7[temp7.length-3] = String.valueOf(result);
            operations.put(7,temp7);
            String[] temp8 = {"F","0","0","1"};
            random = gaussian.getRandProb(1-(Double.parseDouble(temp8[temp8.length-1])));
            temp8[temp8.length-1] = String.valueOf(random);
            result = gaussian.getRandInt(Double.parseDouble(temp8[temp8.length-2]));
            temp8[temp8.length-2] = String.valueOf(result);
            result = gaussian.getRandInt2(Double.parseDouble(temp8[temp8.length-3]));
            temp8[temp8.length-3] = String.valueOf(result);
            operations.put(8,temp8);
            String[] temp9 = {"G","2","0","1"};
            random = gaussian.getRandProb(1-(Double.parseDouble(temp9[temp9.length-1])));
            temp9[temp9.length-1] = String.valueOf(random);
            result = gaussian.getRandInt(Double.parseDouble(temp9[temp9.length-2]));
            temp9[temp9.length-2] = String.valueOf(result);
            result = gaussian.getRandInt2(Double.parseDouble(temp9[temp9.length-3]));
            temp9[temp9.length-3] = String.valueOf(result);
            operations.put(9,temp9);
            String[] temp10 = {"H","10","3","0.85"};
            random = gaussian.getRandProb(1-(Double.parseDouble(temp10[temp10.length-1])));
            temp10[temp10.length-1] = String.valueOf(random);
            result = gaussian.getRandInt(Double.parseDouble(temp10[temp10.length-2]));
            temp10[temp10.length-2] = String.valueOf(result);
            result = gaussian.getRandInt2(Double.parseDouble(temp10[temp10.length-3]));
            temp10[temp10.length-3] = String.valueOf(result);
            operations.put(10,temp10);
        }
        else
        {
            random = ThreadLocalRandom.current().nextDouble(min, max);
            result = r.nextInt((maxInt - minInt) + 1) + minInt;
            String[] temp = {"B","C","D","A","1","5","0.95"};
            temp[temp.length-1] = String.valueOf(random);
            temp[temp.length-2] = String.valueOf(result);
            result = r.nextInt((maxInt2 - minInt2) + 1) + minInt;
            temp[temp.length-3] = String.valueOf(result);
            operations.put(1,temp);
            String[] temp2 = {"F","G","H","C","4","5","0.95"};
            random = ThreadLocalRandom.current().nextDouble(min, max);
            temp2[temp2.length-1] = String.valueOf(random);
            result = r.nextInt((maxInt - minInt) + 1) + minInt;
            temp2[temp2.length-2] = String.valueOf(result);
            result = r.nextInt((maxInt2 - minInt2) + 1) + minInt;
            temp2[temp2.length-3] = String.valueOf(result);
            operations.put(2,temp2);
            String[] temp3 = {"H","A","3","9","0.95"};
            random = ThreadLocalRandom.current().nextDouble(min, max);
            temp3[temp3.length-1] = String.valueOf(random);
            result = r.nextInt((maxInt - minInt) + 1) + minInt;
            temp3[temp3.length-2] = String.valueOf(result);
            result = r.nextInt((maxInt2 - minInt2) + 1) + minInt;
            temp3[temp3.length-3] = String.valueOf(result);
            operations.put(3,temp3);
            String[] temp4 = {"E","A","2","2","1"};
            random = ThreadLocalRandom.current().nextDouble(min, max);
            temp4[temp4.length-1] = String.valueOf(random);
            result = r.nextInt((maxInt - minInt) + 1) + minInt;
            temp4[temp4.length-2] = String.valueOf(result);
            result = r.nextInt((maxInt2 - minInt2) + 1) + minInt;
            temp4[temp4.length-3] = String.valueOf(result);
            operations.put(4,temp4);
            String[] temp5 = {"B","0","0","1"};
            random = ThreadLocalRandom.current().nextDouble(min, max);
            temp5[temp5.length-1] = String.valueOf(random);
            result = r.nextInt((maxInt - minInt) + 1) + minInt;
            temp5[temp5.length-2] = String.valueOf(result);
            result = r.nextInt((maxInt2 - minInt2) + 1) + minInt;
            temp5[temp5.length-3] = String.valueOf(result);
            operations.put(5,temp5);
            String[] temp6 = {"D","0","0","1"};
            random = ThreadLocalRandom.current().nextDouble(min, max);
            temp6[temp6.length-1] = String.valueOf(random);
            result = r.nextInt((maxInt - minInt) + 1) + minInt;
            temp6[temp6.length-2] = String.valueOf(result);
            result = r.nextInt((maxInt2 - minInt) + 1) + minInt;
            temp6[temp6.length-3] = String.valueOf(result);
            operations.put(6,temp6);
            String[] temp7 = {"E","1","1","0.5"};
            random = ThreadLocalRandom.current().nextDouble(min, max);
            temp7[temp7.length-1] = String.valueOf(random);
            result = r.nextInt((maxInt - minInt) + 1) + minInt;
            temp7[temp7.length-2] = String.valueOf(result);
            result = r.nextInt((maxInt2 - minInt) + 1) + minInt;
            temp7[temp7.length-3] = String.valueOf(result);
            operations.put(7,temp7);
            String[] temp8 = {"F","0","0","1"};
            random = ThreadLocalRandom.current().nextDouble(min, max);
            temp8[temp8.length-1] = String.valueOf(random);
            result = r.nextInt((maxInt - minInt) + 1) + minInt;
            temp8[temp8.length-2] = String.valueOf(result);
            result = r.nextInt((maxInt2 - minInt) + 1) + minInt;
            temp8[temp8.length-3] = String.valueOf(result);
            operations.put(8,temp8);
            String[] temp9 = {"G","2","0","1"};
            random = ThreadLocalRandom.current().nextDouble(min, max);
            temp9[temp9.length-1] = String.valueOf(random);
            result = r.nextInt((maxInt - minInt) + 1) + minInt;
            temp9[temp9.length-2] = String.valueOf(result);
            result = r.nextInt((maxInt2 - minInt) + 1) + minInt;
            temp9[temp9.length-3] = String.valueOf(result);
            operations.put(9,temp9);
            String[] temp10 = {"H","10","3","0.85"};
            random = ThreadLocalRandom.current().nextDouble(min, max);
            temp10[temp10.length-1] = String.valueOf(random);
            result = r.nextInt((maxInt - minInt) + 1) + minInt;
            temp10[temp10.length-2] = String.valueOf(result);
            result = r.nextInt((maxInt2 - minInt) + 1) + minInt;
            temp10[temp10.length-3] = String.valueOf(result);
            operations.put(10,temp10);
        }
        HashMap<Integer,String[]> operations_graph = (HashMap) operations.clone();
        HashMap<Integer,String[]> operations_opt = (HashMap) operations.clone();
        Iterator print = operations.entrySet().iterator();
        
        String[] complete= {"7 4","10 3","10 9 8 2 5 6 1"};
        int[] path_costs = new int[3];
        int[] check;
        
        for(int i=0;i<complete.length;i++)
        {
            
            String[] temp_op = complete[i].split(" ");
            for(int j=0;j<temp_op.length;j++)
            {
                String[] temp_op2 = operations.get(Integer.parseInt(temp_op[j]));
                path_costs[i] += Integer.parseInt(temp_op2[temp_op2.length-2]);
            }
        }
        
        
        int temp_cost;
        String temp_path;
        for (int i = 0; i <path_costs.length; i++) {     
          for (int j = i+1; j <path_costs.length; j++) {     
              if(path_costs[i] >path_costs[j]) {      //swap elements if not in order
                 temp_cost = path_costs[i];
                 temp_path = complete[i];
                 complete[i] = complete[j];
                 path_costs[i] = path_costs[j];    
                 path_costs[j] = temp_cost;
                 complete[j] = temp_path;
               }     
            }     
        }
        int count = 10;
        int choice =0;
        List<Map<Integer, String[]>> listOfMaps = new ArrayList<Map<Integer, String[]>>();
        int u=0;
         
        
        while(u<choice_number)
        {
           listOfMaps.add((HashMap) operations.clone());
           u++;
          
           
        }
        HashMap<Integer,Integer> productions = new HashMap<Integer,Integer>();
        determineExecutionInstance(operations,productions);
        while(choice<choice_number)
        {
        cost=0;
        time=0;
        operations= (HashMap)listOfMaps.get(choice);
        HashMap<Integer,String[]> executable = new HashMap();
        
        HashSet<String> available = new HashSet();
        HashMap<Integer,Integer> path = new HashMap<Integer,Integer>();
        HashSet<Integer> prune = new HashSet<Integer>();
        
        
        
        while(count !=0)
        {
           
           Iterator it = operations.entrySet().iterator();
           while (it.hasNext()) //calculate executable operations
           {
            
            HashMap.Entry pair = (HashMap.Entry)it.next();
            String[] temp11 = (String[])pair.getValue();
            if(temp11.length==4) //cases where operation has no input elements
            {
                executable.put((Integer)pair.getKey(),(String[]) pair.getValue());
            }
            else //cases where operation has input elements. so each input element is checked for availability
            {
              int temp12 = 0;
              for(int i=0;i<temp11.length-4;i++)
              {
                if(available.contains(temp11[i]))//each input is checked
                {
                    temp12++;
                    
                }
              }
              if(temp12 == temp11.length -4)//if every input is available then the operation is available for execution
              {
                  executable.put((Integer)pair.getKey(),(String[]) pair.getValue());
              }
            }
            //it.remove(); // avoids a ConcurrentModificationException
            
            }
           if(choice==10 || choice==11 || choice==12)
           {
               
               if(prune.size()>0)
               {
                   
                   for(Integer op : prune)
                   {
                       executable.remove(op);
                   }
               }
           }
          
           if(executable.isEmpty())//execution has completed since the production of A is no longer possible
           {
               if(choice==0)
               {
                   check = new int[complete.length];
                   for(int i=0;i<check.length;i++)
                   {
                       String optemp = complete[i];
                       String[] optemp2 = optemp.split(" ");
                       for(int j=0;j<optemp2.length;j++)
                       {
                           if(path.containsKey(Integer.parseInt(optemp2[j])))
                           {
                            Integer op = path.get(Integer.parseInt(optemp2[j]));
                            if(op.equals(1))
                            {
                               check[i]+=1;
                            }
                           }
                       }
                   }
                   
                   for(int i=0;i<check.length;i++)
                   {
                       if(check[i]==0)
                       {
                           
                           
                           //break;
                       }
                   }
                   
               }
               
               System.out.println("Heuristic: " + heuristics[choice] + " cost: " + cost + "     time: " + time);
               System.out.println("");
               String filename = heuristics[choice] + ".txt";
               
               //pw[choice].println((y+1)+","+cost+","+time);
               cost_failure[choice] +=cost;
               failure[choice]+=1;
               
              
               break;
           }
           
           int key;
           if(choice==0)
           {
               
            key=executeRandom(executable,available,operations_graph,path,productions);
               
           }
           else if(choice==1)
           {
             
            key=executeLowestCost(executable,available,operations_graph,path,productions);
           
           }
           else if(choice==2)
           {
               
            key=executeShortestTime(executable,available,operations_graph,path,productions); 
               
           }
           else if(choice==3)
           {
             
            key=executeLowestFail(executable,available,operations_graph,path,productions);
             
           }
           else if(choice==4)
           {
              
            key=rootDistanceBaseline(executable,available,operations_graph,path,productions);
           
           }
           else if(choice==5)
           {
              
            key=costDistanceBaseline(executable,available,operations_graph,path,productions);
                
           }
           else if(choice==6)
           {

            key=timeDistanceBaseline(executable,available,operations_graph,path,productions);
           
           }
           else if(choice==7)
           {
              
            key=knockoutPathBaseline(executable,available,operations_graph);
               
           }
           else if(choice==8)
           {   
               
            key=knockoutPathTimeBaseline(executable,available,operations_graph);
               
           }
           else if (choice==9)
           {
              
               key=knockoutPathCombBaseline(executable,available,operations_graph);
               
               
               
           }
           else if (choice==10)
           {
             
              key=knockoutPathPruneBothBaseline(executable,available,operations_graph);
              
              
               
           }
           else if(choice==11)
           {
            
            key=knockoutPathPruneBothTimeBaseline(executable,available,operations_graph);
             
           }
           else
           {
               
            key=knockoutPathPruneBothComboBaseline(executable,available,operations_graph);
               
           }
           
           
           String[] temp16 = operations.get(key);
           int result_execution = productions.get(key);
           int cost2 = Integer.valueOf(temp16[temp16.length-2]);
           int time2 = Integer.valueOf(temp16[temp16.length-3]);
           if(result_execution ==1) // the execution was successful and therefore the element is produced and added to the set of available elements
           {
            
            available.add(temp16[temp16.length-4]);
            System.out.println("Heuristic: " + heuristics[choice] + " Op"  + key+ "," + temp16[temp16.length-4]  + " time: " + Double.parseDouble(temp16[temp16.length-3])  + " cost: " + Double.parseDouble(temp16[temp16.length-2]));
            path.put(key,0);
           }
           else //execution was unsuccessful and therefore the element was not produced
           {
            System.out.println("Heuristic: " + heuristics[choice] + " Op"  + key+ "," + temp16[temp16.length-4] + " fail, " + " time: " + Double.parseDouble(temp16[temp16.length-3])  + " cost: " + Double.parseDouble(temp16[temp16.length-2]));
            path.put(key,1);
           }
           
           
           cost = cost + cost2; //cost of the entire path is calculated in a step by step manner
           
           
           time = time + time2;
           operations.remove(key);//operation is removed from the list of operations as it is no longer available
           executable.remove(key);
           if(available.contains("A"))//if A has been produced then the execution is completed
           {
               success[choice]+=1;
              
                   check = new int[complete.length];
                   for(int i=0;i<check.length;i++)
                   {
                       String optemp = complete[i];
                       String[] optemp2 = optemp.split(" ");
                       for(int j=0;j<optemp2.length;j++)
                       {
                           if(productions.containsKey(Integer.parseInt(optemp2[j])))
                           {
                            Integer op = productions.get(Integer.parseInt(optemp2[j]));
                            if(op.equals(1))
                            {
                               check[i]+=1;
                            }
                           }
                           
                       }
                   }
                   
                   int cp =0;
                   int cost_f =0;
                   int cost_opt =0;
                   for(int i=0;i<check.length;i++)
                   {
                       if(check[i]==0)
                       {
                           
                           
                           cp=i;
                           
                           break;
                       }
                   }
                    cost_opt = path_costs[cp];
                    Iterator it_productions = productions.entrySet().iterator();
                    while(it_productions.hasNext())
                    {
                       HashMap.Entry entry = (HashMap.Entry) it_productions.next();
                       if(entry.getValue().equals(1))
                       {
                           
                           String[] operation_temp = (String[]) operations_opt.get((Integer)entry.getKey());
                           
                           //cost_f += Integer.parseInt(operation_temp[operation_temp.length-2]);
                       }
                    }
                    int temp_m = cost_f + cost_opt;
                    optimal[choice] += cost_opt;
                    deviation[choice] += (cost - temp_m);
                   
               
               System.out.println("Heuristic: " + heuristics[choice] + "cost: " + cost + "     time: " + time);
               System.out.println("");
               pw[choice].println(cost+","+time);
               
                   debug++;
               
               sum[choice] += cost;
              sum2[choice] += time;
               break;
           }
           
           
           
           
            
           
            
            
        }
        
        choice++;
        }//neo while
        System.out.println();
        System.out.println();
        y++;
    
    }
       
      for(int i=0;i<choice_number;i++)
      {
       double result = (double) sum[i]/ (double)success[i]; //average execution cost
       pw[i].close();
       double result2 = (double) sum2[i]/ (double)success[i];
       double result3 = (double) deviation[i]/(double)success[i];
       
       System.out.println(heuristics[i] +" cost: " + result +  "  time: " +result2 + ",  number of successful executions: " + success[i] );
          
      }
      
      
      
      
      
      
      
     
     }
     }//edo
    
    private static int getRandomNumberInRange(int min, int max) 
    {
        if (min > max)
        {
          throw new IllegalArgumentException("max must be greater than min");
        }
        if(min==max)
        {
          return 1;
        }
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
    
    private static int executeRandom(HashMap<Integer,String[]> input, HashSet<String> available, HashMap<Integer,String[]> operations, HashMap<Integer,Integer> path, HashMap<Integer,Integer> productions)//Random selection strategy
    {
        
        int next = getRandomNumberInRange(1,input.size()) -1;
        int count =0;
        int key=0;
        Iterator it2 = input.entrySet().iterator();
        
        while(it2.hasNext())
        {
           HashMap.Entry pair2 = (HashMap.Entry)it2.next();
           if(count==next)
           {
            key =  (Integer) pair2.getKey();
            /*
            String[] temp13 = (String []) pair2.getValue();
            
            //operations.remove(key);
            input.remove(key);
            double prob = Double.parseDouble(temp13[temp13.length-1]);
            int result = productions.get(key);
            if(result ==1) // the execution was successful and therefore the element is produced and added to the set of available elements
            {
                path.put(key,0);
                available.add(temp13[temp13.length-4]);
                allocation.add(temp13[0]);
                end.add(current_time + Integer.parseInt(temp13[temp13.length-3]));
                System.out.println("Op" + key+ "," + temp13[temp13.length-4] + " , instance: " + inst + " start_time: " + current_time + " , end time:" + (current_time + Integer.parseInt(temp13[temp13.length-3])));
            }
            else //execution was unsuccessful and therefore the element was not produced
            {
                path.put(key,1);
                allocation.add(temp13[0]);
                end.add(current_time + Integer.parseInt(temp13[temp13.length-3]));
                System.out.println("Op" + key+ "," + temp13[temp13.length-4] + " fail , instance: " + " , instance: " + inst + " start_time: " + current_time + " , end time:" + (current_time + Integer.parseInt(temp13[temp13.length-3])));
            }
            
            
            */
            break;
           }
           
           count++;
                   
        }
        
        return key;
    }
    
    private static int executeLowestCost(HashMap<Integer,String[]> input, HashSet<String> available, HashMap<Integer,String[]> operations,HashMap<Integer,Integer> path, HashMap<Integer,Integer> productions)//Lowest Cost selection strategy
    {
        int n = input.size();
        int[][] cost = new int [n][3];
        Iterator t = input.entrySet().iterator();
        int i=0;
        while(t.hasNext())
        {
          HashMap.Entry pair = (HashMap.Entry) t.next();
          cost[i][0]= (Integer) pair.getKey();
          String[] temp15= (String[]) pair.getValue();
          cost[i][1]= Integer.parseInt(temp15[temp15.length-2]);
          
          i++;
        }
        int key =getMinValue(cost);
        
        /*String[] temp16 = input.get(key);
        double prob = Double.parseDouble(temp16[temp16.length-1]);
        input.remove(key);
        int result = productions.get(key);
        if(result ==1) // the execution was successful and therefore the element is produced and added to the set of available elements
        {
            available.add(temp16[temp16.length-4]);
            System.out.println("Op" + key+ "," + temp16[temp16.length-4] + " , instance: " + inst + " start_time: " + current_time + " , end time:" + (current_time + Integer.parseInt(temp16[temp16.length-3])));
            path.put(key,0);
        }
        else //execution was unsuccessful and therefore the element was not produced
        {
            System.out.println("Op" + key+ "," + temp16[temp16.length-4] + " fail , instance: "  + inst + " start_time: " + current_time + " , end time:" + (current_time + Integer.parseInt(temp16[temp16.length-3])));
            path.put(key,1);
        }*/
        
        return key;
    }
    
    private static int executeShortestTime(HashMap<Integer,String[]> input, HashSet<String> available, HashMap<Integer,String[]> operations,HashMap<Integer,Integer> path, HashMap<Integer,Integer> productions)
    {
        int n = input.size();
        
        int[][] cost = new int [n][3];
        Iterator t = input.entrySet().iterator();
        int i=0;
        while(t.hasNext())
        {
          HashMap.Entry pair = (HashMap.Entry) t.next();
          cost[i][0]= (Integer) pair.getKey();
          String[] temp15= (String[]) pair.getValue();
          cost[i][1]= Integer.parseInt(temp15[temp15.length-3]);
          
          i++;
        }
        int key =getMinValue(cost);
        /*
        String[] temp16 = input.get(key);
        double prob = Double.parseDouble(temp16[temp16.length-1]);
        input.remove(key);
        int result = productions.get(key);
        if(result ==1) // the execution was successful and therefore the element is produced and added to the set of available elements
        {
            available.add(temp16[temp16.length-4]);
            System.out.println("Op" + key+ "," + temp16[temp16.length-4] + " , instance: " + inst);
            allocation.add(temp16[0]);
            end.add(current_time + Integer.parseInt(temp16[temp16.length-3]));
            path.put(key, 0);
            
        }
        else //execution was unsuccessful and therefore the element was not produced
        {
            System.out.println("Op" + key+ "," + temp16[temp16.length-4] + " fail , instance: "  + " , instance: " + inst);
            allocation.add(temp16[0]);
            end.add(current_time + Integer.parseInt(temp16[temp16.length-3]));
            path.put(key, 1);
        }
        */
        return key;
    }
    
    private static int executeLowestFail(HashMap<Integer,String[]> input, HashSet<String> available, HashMap<Integer,String[]> operations, HashMap<Integer,Integer> path, HashMap<Integer,Integer> productions)
    {
        int n = input.size();
        double[][] cost = new double [n][3];
        Iterator t = input.entrySet().iterator();
        int i=0;
        while(t.hasNext())
        {
          HashMap.Entry pair = (HashMap.Entry) t.next();
          cost[i][0]= (Integer) pair.getKey();
          String[] temp15= (String[]) pair.getValue();
          cost[i][1]= Double.parseDouble(temp15[temp15.length-1]);
          
          i++;
        }
        int key = (int)getMaxValue(cost);
        /*
        String[] temp16 = input.get(key);
        double prob = Double.parseDouble(temp16[temp16.length-1]);
        input.remove(key);
        int result = productions.get(key);
        if(result ==1) // the execution was successful and therefore the element is produced and added to the set of available elements
        {
            available.add(temp16[temp16.length-4]);
            System.out.println("Op" + key+ "," + temp16[temp16.length-4] + inst + " start_time: " + current_time + " , end time:" + (current_time + Integer.parseInt(temp16[temp16.length-3])));
            allocation.add(temp16[0]);
            end.add(current_time + Integer.parseInt(temp16[temp16.length-3]));
            path.put(key, 0);
            
        }
        else //execution was unsuccessful and therefore the element was not produced
        {
            System.out.println("Op" + key+ "," + temp16[temp16.length-4] + " fail , instance: "  + inst + " start_time: " + current_time + " , end time:" + (current_time + Integer.parseInt(temp16[temp16.length-3])));
            allocation.add(temp16[0]);
            end.add(current_time + Integer.parseInt(temp16[temp16.length-3]));
            path.put(key, 1);
        }
        */
        return key;
    }
    
    public static int getMinValue(int[][] numbers)
    {
                
        int minValue = numbers[0][1];
        int index=0;
        for(int i=1;i<numbers.length;i++)
        {
         if(numbers[i][1] < minValue)
           {
                minValue = numbers[i][1];
                index =i;
           }
		}
                
		return numbers[index][0];
	}
    
    public static int getMinValueDouble(double[][] numbers)
    {
                
        double minValue = numbers[0][1];
        int index=0;
        for(int i=1;i<numbers.length;i++)
        {
         if(numbers[i][1] < minValue)
           {
                minValue = numbers[i][1];
                index =i;
           }
		}
                
		return (int) numbers[index][0];
	}
    
    public static double getMaxValue(double[][] numbers)
    {
                
        double maxValue = numbers[0][1];
        int index=0;
        for(int i=1;i<numbers.length;i++)
        {
         if(numbers[i][1] > maxValue)
           {
                maxValue = numbers[i][1];
                index =i;
           }
		}
                
		return numbers[index][0];
	}
    
    public static int getMinValue2(int[][] numbers)
    {
                
        int minValue = numbers[0][1];
        int index=0;
        for(int i=1;i<numbers.length;i++)
        {
         if(numbers[i][1] < minValue)
           {
                minValue = numbers[i][1];
                index =i;
           }
		}
                
		return minValue;
	}
    
    private static int execution(double prob)// function that takes the input the success probability of a rule and executes it
    {
        Random r = new Random();
        double result = r.nextDouble();
        if(result>prob)
        {
          return 0; //failure
        }
        else
        {
          return 1;//success
        }
    }
    
    
    
    private static int rootDistance(HashMap<Integer,String[]> input, HashSet<String> available, HashMap<Integer,String[]> operations, HashMap<Integer,Integer> path, HashMap<Integer,Integer> productions)
    {
        int graph[][] = new int[9][9];
        for(int i=0;i<9;i++)
        {
            for(int j=0;j<9;j++)
            {
                graph[i][j]=-1;
            }
        }
        
        Iterator it = operations.entrySet().iterator();
        while(it.hasNext())
        {
            HashMap.Entry pair = (HashMap.Entry) it.next(); 
            String[] dokimi = (String[]) pair.getValue();
            int limit = dokimi.length-3;
            String[] te = new String[limit];
            
            if(limit==1)
            {
                int one = 0;
                int two = dokimi[1].charAt(0) - 'A' + 1;
                graph[one][two] = 1;
               
                
            }
            else
            {
             for(int y=1;y<limit-1;y++)
             {
                int one = dokimi[y].charAt(0) - 'A' + 1;
                int two = dokimi[limit-1].charAt(0) - 'A' +1;
                
                graph[two][one]=1;
               
                
                
             }
            }
            
        }
        
        
        
        ShortestPath s = new ShortestPath(); 
        int[] dist = s.dijkstra(graph, 1);
        
        
        int n = input.size();
        int[][] cost = new int [n][2];
        Iterator t = input.entrySet().iterator();
        int i=0;
        while(t.hasNext())
        {
          HashMap.Entry pair = (HashMap.Entry) t.next();  // takes from the dist array, which contains the distance from root for all operations, the distances
          String[] temp15= (String[]) pair.getValue(); // of the available for execution operations and saves them in the cost array
          int tempkey = (Integer) pair.getKey();
          cost[i][0] = tempkey;
          char tempstring = temp15[temp15.length-4].charAt(0);
          int tempint = tempstring - 'A' +1;
          
          cost[i][1] = dist[tempint];  //in the case wherethere are >=1 input elements the one that has the biggest distance represents the distance
          
          
          i++;
        }
        int key = (int) getMinValue(cost); // the rule with the lowest value(distance from root) is selected for execution
        /*
        //System.out.println(key);
        String[] temp16 = input.get(key);
        double prob = Double.parseDouble(temp16[temp16.length-1]);
        input.remove(key);
        int result = productions.get(key);
        if(result ==1) // the execution was successful and therefore the element is produced and added to the set of available elements
        {
            available.add(temp16[temp16.length-4]);
            System.out.println("Op" + key+ "," + temp16[temp16.length-4] + " , instance: " + inst + " start_time: " + current_time + " , end time:" + (current_time + Integer.parseInt(temp16[temp16.length-3])));
            path.put(key, 0);
            allocation.add(temp16[0]);
            end.add(current_time + Integer.parseInt(temp16[temp16.length-3]));
            
        }
        else //execution was unsuccessful and therefore the element was not produced
        {
            System.out.println("Op" + key+ "," + temp16[temp16.length-4] + " fail , instance: " + " , instance: " + inst + " start_time: " + current_time + " , end time:" + (current_time + Integer.parseInt(temp16[temp16.length-3])));
            path.put(key, 1);
            allocation.add(temp16[0]);
            end.add(current_time + Integer.parseInt(temp16[temp16.length-3]));
        }
        */
        return key;
    }
    
    private static int rootDistanceBaseline(HashMap<Integer,String[]> input, HashSet<String> available, HashMap<Integer,String[]> operations, HashMap<Integer,Integer> path, HashMap<Integer,Integer> productions)
    {
        int graph[][] = new int[9][9];
        for(int i=0;i<9;i++)
        {
            for(int j=0;j<9;j++)
            {
                graph[i][j]=-1;
            }
        }
        
        Iterator it = operations.entrySet().iterator();
        while(it.hasNext())
        {
            HashMap.Entry pair = (HashMap.Entry) it.next(); 
            String[] dokimi = (String[]) pair.getValue();
            int limit = dokimi.length-3;
            String[] te = new String[limit];
            
            if(limit==1)
            {
                int one = 0;
                int two = dokimi[0].charAt(0) - 'A' + 1;
                graph[one][two] = 1;
               
                
            }
            else
            {
             for(int y=0;y<limit-1;y++)
             {
                int one = dokimi[y].charAt(0) - 'A' + 1;
                int two = dokimi[limit-1].charAt(0) - 'A' +1;
                
                graph[two][one]=1;
               
                
                
             }
            }
            
        }
        
        
        
        ShortestPath s = new ShortestPath(); 
        int[] dist = s.dijkstra(graph, 1);
        
        
        int n = input.size();
        int[][] cost = new int [n][2];
        Iterator t = input.entrySet().iterator();
        int i=0;
        while(t.hasNext())
        {
          HashMap.Entry pair = (HashMap.Entry) t.next();  // takes from the dist array, which contains the distance from root for all operations, the distances
          String[] temp15= (String[]) pair.getValue(); // of the available for execution operations and saves them in the cost array
          int tempkey = (Integer) pair.getKey();
          cost[i][0] = tempkey;
          char tempstring = temp15[temp15.length-4].charAt(0);
          int tempint = tempstring - 'A' +1;
          
          cost[i][1] = dist[tempint];  //in the case wherethere are >=1 input elements the one that has the biggest distance represents the distance
          
          
          i++;
        }
        int key = (int) getMinValue(cost); // the rule with the lowest value(distance from root) is selected for execution
        /*
        //System.out.println(key);
        String[] temp16 = input.get(key);
        double prob = Double.parseDouble(temp16[temp16.length-1]);
        input.remove(key);
        int result = productions.get(key);
        if(result ==1) // the execution was successful and therefore the element is produced and added to the set of available elements
        {
            available.add(temp16[temp16.length-4]);
            System.out.println("Op" + key+ "," + temp16[temp16.length-4] + " , instance: " + inst + " start_time: " + current_time + " , end time:" + (current_time + Integer.parseInt(temp16[temp16.length-3])));
            path.put(key, 0);
            allocation.add(temp16[0]);
            end.add(current_time + Integer.parseInt(temp16[temp16.length-3]));
            
        }
        else //execution was unsuccessful and therefore the element was not produced
        {
            System.out.println("Op" + key+ "," + temp16[temp16.length-4] + " fail , instance: " + " , instance: " + inst + " start_time: " + current_time + " , end time:" + (current_time + Integer.parseInt(temp16[temp16.length-3])));
            path.put(key, 1);
            allocation.add(temp16[0]);
            end.add(current_time + Integer.parseInt(temp16[temp16.length-3]));
        }
        */
        return key;
    }
    
    private static int costDistance(HashMap<Integer,String[]> input, HashSet<String> available, HashMap<Integer,String[]> operations, HashMap<Integer,Integer> path, HashMap<Integer,Integer> productions)
    {
        int graph[][] = new int[9][9];
        for(int i=0;i<9;i++)
        {
            for(int j=0;j<9;j++)
            {
                graph[i][j]=-1;
            }
        }
        if(operations.size()<10)
        {
            
        }
        Iterator it = operations.entrySet().iterator();
        while(it.hasNext())
        {
            HashMap.Entry pair = (HashMap.Entry) it.next(); 
            String[] dokimi = (String[]) pair.getValue();
            int limit = dokimi.length-3;
            String[] te = new String[limit];
            
            if(limit==1)
            {
                int one = 0;
                int two = dokimi[1].charAt(0) - 'A' + 1;
                graph[one][two] = Integer.parseInt(dokimi[dokimi.length-2]);
               
                
            }
            else
            {
             for(int y=1;y<limit-1;y++)//ITAN =0, <limit-1
             {
                int one = dokimi[y].charAt(0) - 'A' + 1;
                int two = dokimi[limit-1].charAt(0) - 'A' +1;
                
                graph[two][one]=Integer.parseInt(dokimi[dokimi.length-2]);
               
                
                
             }
            }
            
        }
        
        
        
        ShortestPath s = new ShortestPath(); 
        int[] dist = s.dijkstra(graph, 1);
        
        for(int i=0;i<dist.length;i++)
        {
            //System.out.println("dist: " + dist[i]);
        }
        
        int n = input.size();
        int[][] cost = new int [n][2];
        Iterator t = input.entrySet().iterator();
        int i=0;
        while(t.hasNext())
        {
          HashMap.Entry pair = (HashMap.Entry) t.next();  // takes from the dist array, which contains the distance from root for all operations, the distances
          String[] temp15= (String[]) pair.getValue(); // of the available for execution operations and saves them in the cost array
          int tempkey = (Integer) pair.getKey();
          cost[i][0] = tempkey;
          char tempstring = temp15[temp15.length-4].charAt(0);
          int tempint = tempstring - 'A' +1;
          
            cost[i][1] = dist[tempint];  //in the case wherethere are >=1 input elements the one that has the biggest distance represents the distance 
            //System.out.println("Op:" + tempkey + "  cost: " + cost[i][1]);
          
          i++;
          
        }
        for(int j=0;j<n;j++)
        {
          //System.out.println(cost[j][0] + "cost: " + cost[j][1]);  
        }
        //System.out.println();
        int key = (int) getMinValue(cost); // the rule with the lowest value(distance from root) is selected for execution
        //System.out.println(key);
        String[] temp16 = input.get(key);
        /*
        double prob = Double.parseDouble(temp16[temp16.length-1]);
        input.remove(key);
        int result = productions.get(key);
        if(result ==1) // the execution was successful and therefore the element is produced and added to the set of available elements
        {
            available.add(temp16[temp16.length-4]);
            System.out.println("Op" + key+ "," + temp16[temp16.length-4] + " , instance: " + inst + " start_time: " + current_time + " , end time:" + (current_time + Integer.parseInt(temp16[temp16.length-3])));
            path.put(key, 0);
            allocation.add(temp16[0]);
            end.add(current_time + Integer.parseInt(temp16[temp16.length-3]));
            
        }
        else //execution was unsuccessful and therefore the element was not produced
        {
            System.out.println("Op" + key+ "," + temp16[temp16.length-4] + " fail , instance: " + " , instance: " + inst + " start_time: " + current_time + " , end time:" + (current_time + Integer.parseInt(temp16[temp16.length-3])));
            path.put(key, 1);
            allocation.add(temp16[0]);
            end.add(current_time + Integer.parseInt(temp16[temp16.length-3]));
        }*/
        return key;
    }
    
    private static int costDistanceBaseline(HashMap<Integer,String[]> input, HashSet<String> available, HashMap<Integer,String[]> operations, HashMap<Integer,Integer> path, HashMap<Integer,Integer> productions)
    {
        int graph[][] = new int[9][9];
        for(int i=0;i<9;i++)
        {
            for(int j=0;j<9;j++)
            {
                graph[i][j]=-1;
            }
        }
        if(operations.size()<10)
        {
            
        }
        Iterator it = operations.entrySet().iterator();
        while(it.hasNext())
        {
            HashMap.Entry pair = (HashMap.Entry) it.next(); 
            String[] dokimi = (String[]) pair.getValue();
            int limit = dokimi.length-3;
            String[] te = new String[limit];
            
            if(limit==1)
            {
                int one = 0;
                int two = dokimi[0].charAt(0) - 'A' + 1;
                graph[one][two] = Integer.parseInt(dokimi[dokimi.length-2]);
               
                
            }
            else
            {
             for(int y=0;y<limit-1;y++)//ITAN =0, <limit-1
             {
                int one = dokimi[y].charAt(0) - 'A' + 1;
                int two = dokimi[limit-1].charAt(0) - 'A' +1;
                
                graph[two][one]=Integer.parseInt(dokimi[dokimi.length-2]);
               
                
                
             }
            }
            
        }
        
        
        
        ShortestPath s = new ShortestPath(); 
        int[] dist = s.dijkstra(graph, 1);
        
        for(int i=0;i<dist.length;i++)
        {
            //System.out.println("dist: " + dist[i]);
        }
        
        int n = input.size();
        int[][] cost = new int [n][2];
        Iterator t = input.entrySet().iterator();
        int i=0;
        while(t.hasNext())
        {
          HashMap.Entry pair = (HashMap.Entry) t.next();  // takes from the dist array, which contains the distance from root for all operations, the distances
          String[] temp15= (String[]) pair.getValue(); // of the available for execution operations and saves them in the cost array
          int tempkey = (Integer) pair.getKey();
          cost[i][0] = tempkey;
          char tempstring = temp15[temp15.length-4].charAt(0);
          int tempint = tempstring - 'A' +1;
          
            cost[i][1] = dist[tempint];  //in the case wherethere are >=1 input elements the one that has the biggest distance represents the distance 
            //System.out.println("Op:" + tempkey + "  cost: " + cost[i][1]);
          
          i++;
          
        }
        for(int j=0;j<n;j++)
        {
          //System.out.println(cost[j][0] + "cost: " + cost[j][1]);  
        }
        //System.out.println();
        int key = (int) getMinValue(cost); // the rule with the lowest value(distance from root) is selected for execution
        //System.out.println(key);
        String[] temp16 = input.get(key);
        /*
        double prob = Double.parseDouble(temp16[temp16.length-1]);
        input.remove(key);
        int result = productions.get(key);
        if(result ==1) // the execution was successful and therefore the element is produced and added to the set of available elements
        {
            available.add(temp16[temp16.length-4]);
            System.out.println("Op" + key+ "," + temp16[temp16.length-4] + " , instance: " + inst + " start_time: " + current_time + " , end time:" + (current_time + Integer.parseInt(temp16[temp16.length-3])));
            path.put(key, 0);
            allocation.add(temp16[0]);
            end.add(current_time + Integer.parseInt(temp16[temp16.length-3]));
            
        }
        else //execution was unsuccessful and therefore the element was not produced
        {
            System.out.println("Op" + key+ "," + temp16[temp16.length-4] + " fail , instance: " + " , instance: " + inst + " start_time: " + current_time + " , end time:" + (current_time + Integer.parseInt(temp16[temp16.length-3])));
            path.put(key, 1);
            allocation.add(temp16[0]);
            end.add(current_time + Integer.parseInt(temp16[temp16.length-3]));
        }*/
        return key;
    }
    
    private static int costDistanceNew(HashMap<Integer,String[]> input, HashSet<String> available, HashMap<Integer,String[]> operations, HashMap<Integer,Integer> path, HashMap<Integer,Integer> productions)
    {
        int graph[][] = new int[9][9];
        double graph2[][] = new double[9][9];
        for(int i=0;i<9;i++)
        {
            for(int j=0;j<9;j++)
            {
                graph[i][j]=-1;
                graph[i][j]=-1;
            }
        }
        if(operations.size()<10)
        {
            
        }
        Iterator it = operations.entrySet().iterator();
        while(it.hasNext())
        {
            HashMap.Entry pair = (HashMap.Entry) it.next(); 
            String[] dokimi = (String[]) pair.getValue();
            int limit = dokimi.length-3;
            String[] te = new String[limit];
            
            if(limit==1)
            {
                int one = 0;
                int two = dokimi[1].charAt(0) - 'A' + 1;
                graph[one][two] = Integer.parseInt(dokimi[dokimi.length-2]);
               
                
            }
            else
            {
             for(int y=1;y<limit-1;y++)
             {
                int one = dokimi[y].charAt(0) - 'A' + 1;
                int two = dokimi[limit-1].charAt(0) - 'A' +1;
                
                graph[two][one]=Integer.parseInt(dokimi[dokimi.length-2]);
               
                
                
             }
            }
            
        }
        
        
        
        ShortestPath s = new ShortestPath();
        ShortestPathProb sp = new ShortestPathProb();
        int[] dist = s.dijkstra(graph, 1);
        double[] distProb = sp.dijkstra(graph2, 1);
        int[] dist2 = new int[dist.length];
        for(int f=1;f<dist.length;f++)
        {
            prune2(operations,f,graph,dist2);
        }
        for(int f=1;f<dist.length;f++)
        {
           dist[f] += dist2[f];
        }        
        int n = input.size();
        double[][] cost = new double [n][2];
        Iterator t = input.entrySet().iterator();
        int i=0;
        while(t.hasNext())
        {
          HashMap.Entry pair = (HashMap.Entry) t.next();  // takes from the dist array, which contains the distance from root for all operations, the distances
          String[] temp15= (String[]) pair.getValue(); // of the available for execution operations and saves them in the cost array
          int tempkey = (Integer) pair.getKey();
          cost[i][0] = tempkey;
          int limit =  temp15.length-3; //in the case wherethere are >=1 input elements the one that has the biggest distance represents the distance
            
          if(limit==1)
          {
           char index = temp15[0].charAt(0);
           int ind = index - 'A' +1;
           int denominator = dist[ind];
           double numerator = distProb[ind];
           cost[i][1] = numerator/denominator;
              
          }
          else
          {
           char index = temp15[limit-1].charAt(0);
           int ind = index - 'A' +1;
           int denominator = dist[ind];
           double numerator = distProb[ind];
           cost[i][1] = numerator/denominator;   
          }
            //System.out.println("Op:" + tempkey + "  cost: " + cost[i][1]);
          
          i++;
          
        }
        for(int j=0;j<n;j++)
        {
          //System.out.println(cost[j][0] + "cost: " + cost[j][1]);  
        }
        //System.out.println();
         int key = (int) getMaxValue(cost); // the rule with the lowest value(distance from root) is selected for execution
        //System.out.println(key);
        String[] temp16 = input.get(key);
        double prob = Double.parseDouble(temp16[temp16.length-1]);
        /*
        input.remove(key);
        int result = productions.get(key);
        if(result ==1) // the execution was successful and therefore the element is produced and added to the set of available elements
        {
            available.add(temp16[temp16.length-4]);
            System.out.println("Op" + key+ "," + temp16[temp16.length-4]);
            path.put(key, 0);
            
        }
        else //execution was unsuccessful and therefore the element was not produced
        {
            System.out.println("Op" + key+ "," + temp16[temp16.length-4] + " fail , instance: ");
            path.put(key, 1);
        }
         */
        return key;
    }
    
    private static int timeDistance(HashMap<Integer,String[]> input, HashSet<String> available, HashMap<Integer,String[]> operations, HashMap<Integer,Integer> path, HashMap<Integer,Integer> productions)
    {
        int graph[][] = new int[9][9];
        for(int i=0;i<9;i++)
        {
            for(int j=0;j<9;j++)
            {
                graph[i][j]=-1;
            }
        }
        
        Iterator it = operations.entrySet().iterator();
        while(it.hasNext())
        {
            HashMap.Entry pair = (HashMap.Entry) it.next(); 
            String[] dokimi = (String[]) pair.getValue();
            int limit = dokimi.length-3;
            String[] te = new String[limit];
            
            if(limit==1)
            {
                int one = 0;
                int two = dokimi[1].charAt(0) - 'A' + 1;
                graph[one][two] = Integer.parseInt(dokimi[dokimi.length-3]);
               
                
            }
            else
            {
             for(int y=1;y<limit-1;y++)
             {
                int one = dokimi[y].charAt(0) - 'A' + 1;
                int two = dokimi[limit-1].charAt(0) - 'A' +1;
                
                graph[two][one]=Integer.parseInt(dokimi[dokimi.length-3]);
               
                
                
             }
            }
            
        }
        
        
        
        ShortestPath s = new ShortestPath(); 
        int[] dist = s.dijkstra(graph, 1);
       
        
        int n = input.size();
        int[][] cost = new int [n][2];
        Iterator t = input.entrySet().iterator();
        int i=0;
        while(t.hasNext())
        {
          HashMap.Entry pair = (HashMap.Entry) t.next();  // takes from the dist array, which contains the distance from root for all operations, the distances
          String[] temp15= (String[]) pair.getValue(); // of the available for execution operations and saves them in the cost array
          int tempkey = (Integer) pair.getKey();
          cost[i][0] = tempkey;
          char tempstring = temp15[temp15.length-4].charAt(0);
          int tempint = tempstring - 'A' +1;
          
             cost[i][1] = dist[tempint];  //in the case wherethere are >=1 input elements the one that has the biggest distance represents the distance 
           
          
          
          
          
          i++;
        }
        int key = (int) getMinValue(cost); // the rule with the lowest value(distance from root) is selected for execution
        //System.out.println(key);
        /*
        String[] temp16 = input.get(key);
        double prob = Double.parseDouble(temp16[temp16.length-1]);
        input.remove(key);
        int result = productions.get(key);
        if(result ==1) // the execution was successful and therefore the element is produced and added to the set of available elements
        {
            available.add(temp16[temp16.length-4]);
            System.out.println("Op" + key+ "," + temp16[temp16.length-4] + " , instance: " + inst + " start_time: " + current_time + " , end time:" + (current_time + Integer.parseInt(temp16[temp16.length-3])));
            path.put(key, 0);
            allocation.add(temp16[0]);
            end.add(current_time + Integer.parseInt(temp16[temp16.length-3]));
            
        }
        else //execution was unsuccessful and therefore the element was not produced
        {
            System.out.println("Op" + key+ "," + temp16[temp16.length-4] + " fail , instance: " + " , instance: " + inst + " start_time: " + current_time + " , end time:" + (current_time + Integer.parseInt(temp16[temp16.length-3])));
            path.put(key, 1);
            allocation.add(temp16[0]);
            end.add(current_time + Integer.parseInt(temp16[temp16.length-3]));
        }
        */
        return key;
    }
    
    private static int timeDistanceBaseline(HashMap<Integer,String[]> input, HashSet<String> available, HashMap<Integer,String[]> operations, HashMap<Integer,Integer> path, HashMap<Integer,Integer> productions)
    {
        int graph[][] = new int[9][9];
        for(int i=0;i<9;i++)
        {
            for(int j=0;j<9;j++)
            {
                graph[i][j]=-1;
            }
        }
        
        Iterator it = operations.entrySet().iterator();
        while(it.hasNext())
        {
            HashMap.Entry pair = (HashMap.Entry) it.next(); 
            String[] dokimi = (String[]) pair.getValue();
            int limit = dokimi.length-3;
            String[] te = new String[limit];
            
            if(limit==1)
            {
                int one = 0;
                int two = dokimi[0].charAt(0) - 'A' + 1;
                graph[one][two] = Integer.parseInt(dokimi[dokimi.length-3]);
               
                
            }
            else
            {
             for(int y=0;y<limit-1;y++)
             {
                int one = dokimi[y].charAt(0) - 'A' + 1;
                int two = dokimi[limit-1].charAt(0) - 'A' +1;
                
                graph[two][one]=Integer.parseInt(dokimi[dokimi.length-3]);
               
                
                
             }
            }
            
        }
        
        
        
        ShortestPath s = new ShortestPath(); 
        int[] dist = s.dijkstra(graph, 1);
       
        
        int n = input.size();
        int[][] cost = new int [n][2];
        Iterator t = input.entrySet().iterator();
        int i=0;
        while(t.hasNext())
        {
          HashMap.Entry pair = (HashMap.Entry) t.next();  // takes from the dist array, which contains the distance from root for all operations, the distances
          String[] temp15= (String[]) pair.getValue(); // of the available for execution operations and saves them in the cost array
          int tempkey = (Integer) pair.getKey();
          cost[i][0] = tempkey;
          char tempstring = temp15[temp15.length-4].charAt(0);
          int tempint = tempstring - 'A' +1;
          
             cost[i][1] = dist[tempint];  //in the case wherethere are >=1 input elements the one that has the biggest distance represents the distance 
           
          
          
          
          
          i++;
        }
        int key = (int) getMinValue(cost); // the rule with the lowest value(distance from root) is selected for execution
        //System.out.println(key);
        /*
        String[] temp16 = input.get(key);
        double prob = Double.parseDouble(temp16[temp16.length-1]);
        input.remove(key);
        int result = productions.get(key);
        if(result ==1) // the execution was successful and therefore the element is produced and added to the set of available elements
        {
            available.add(temp16[temp16.length-4]);
            System.out.println("Op" + key+ "," + temp16[temp16.length-4] + " , instance: " + inst + " start_time: " + current_time + " , end time:" + (current_time + Integer.parseInt(temp16[temp16.length-3])));
            path.put(key, 0);
            allocation.add(temp16[0]);
            end.add(current_time + Integer.parseInt(temp16[temp16.length-3]));
            
        }
        else //execution was unsuccessful and therefore the element was not produced
        {
            System.out.println("Op" + key+ "," + temp16[temp16.length-4] + " fail , instance: " + " , instance: " + inst + " start_time: " + current_time + " , end time:" + (current_time + Integer.parseInt(temp16[temp16.length-3])));
            path.put(key, 1);
            allocation.add(temp16[0]);
            end.add(current_time + Integer.parseInt(temp16[temp16.length-3]));
        }
        */
        return key;
    }
    
    
    
    
    
    
     
     
   
   
private static int knockoutPath(HashMap<Integer,String[]> input, HashSet<String> available, HashMap<Integer,String[]> operations)
   {
        int graph[][] = new int[9][9];
        double graph2[][] = new double [9][9];
        for(int l=0;l<9;l++)
        {
            for(int p=0;p<9;p++)
            {
                graph2[l][p]=-1;
                graph[l][p]=-1;
            }
        }
        Iterator it = operations.entrySet().iterator();
        while(it.hasNext())
        {
            HashMap.Entry pair = (HashMap.Entry) it.next(); 
            String[] dokimi = (String[]) pair.getValue();
            int limit = dokimi.length-3;
            String[] te = new String[limit];
            
            if(limit==1)
            {
                int one = 0;
                int two = dokimi[1].charAt(0) - 'A' + 1;
                graph[one][two] = Integer.parseInt(dokimi[limit+1]);
                double prob = Double.parseDouble(dokimi[limit+2]);
                graph2[one][two] = 1 - prob;
            }
            else
            {
             for(int y=1;y<limit-1;y++)
             {
                int one = dokimi[y].charAt(0) - 'A' + 1;
                int two = dokimi[limit-1].charAt(0) - 'A' +1;
                //graph[one][two]=Integer.parseInt(dokimi[limit]);
                graph[two][one]=Integer.parseInt(dokimi[limit+1]);
                double prob = Double.parseDouble(dokimi[limit+2]);
                //graph2[one][two] = 1 - prob;
                graph2[two][one] = 1 - prob;
                
             }
            }
            
        }
        
        int dist[] = new int[operations.size()+1];
        double distProb[] = new double[operations.size()+1];
        ShortestPath tp = new ShortestPath();
        ShortestPathProb tpp = new ShortestPathProb();
        dist = tp.dijkstra(graph, 1);
        distProb = tpp.dijkstra(graph2, 1);
        
        int n = input.size();
        double[][] cost = new double[n][2];
        
        Iterator t = input.entrySet().iterator();
        int i=0;
        while(t.hasNext())
        {
          HashMap.Entry pair = (HashMap.Entry) t.next();  // takes from the dist array, which contains the distance from root for all operations, the distances
          String[] temp15= (String[]) pair.getValue(); // of the available for execution operations and saves them in the cost array
          int tempkey = (Integer) pair.getKey();
          cost[i][0] = tempkey;
          int limit = temp15.length-3;
          if(limit==1)
          {
           char index = temp15[1].charAt(0);
           int ind = index - 'A' +1;
           int denominator = dist[ind];
           double numerator = distProb[ind];
           cost[i][1] = numerator/denominator;
              
          }
          else
          {
           char index = temp15[limit-1].charAt(0);
           int ind = index - 'A' +1;
           int denominator = dist[ind];
           double numerator = distProb[ind];
           cost[i][1] = numerator/denominator;   
          }
          
          
          i++;
        }
        int key = (int) getMaxValue(cost); // the rule with the lowest value(distance from root) is selected for execution
        //System.out.println(key);
        /*
        String[] temp16 = input.get(key);
        double prob = Double.parseDouble(temp16[temp16.length-1]);
        input.remove(key);
        int result = productions.get(key);
        if(result ==1) // the execution was successful and therefore the element is produced and added to the set of available elements
        {
            available.add(temp16[temp16.length-4]);
            System.out.println("Op" + key+ "," + temp16[temp16.length-4] + " , instance: " + inst + " start_time: " + current_time + " , end time:" + (current_time + Integer.parseInt(temp16[temp16.length-3])));
            path.put(key, 0);
            allocation.add(temp16[0]);
            end.add(current_time + Integer.parseInt(temp16[temp16.length-3]));
        }
        else //execution was unsuccessful and therefore the element was not produced
        {
            System.out.println("Op" + key+ "," + temp16[temp16.length-4] + " fail , instance: " + " , instance: " + inst + " start_time: " + current_time + " , end time:" + (current_time + Integer.parseInt(temp16[temp16.length-3])));
            path.put(key, 1);
            allocation.add(temp16[0]);
            end.add(current_time + Integer.parseInt(temp16[temp16.length-3]));
            //pruneOperations(operations,key,complete);
        }
        */
        return key;
        
       
   }

private static int knockoutPathBaseline(HashMap<Integer,String[]> input, HashSet<String> available, HashMap<Integer,String[]> operations)
   {
        int graph[][] = new int[9][9];
        double graph2[][] = new double [9][9];
        for(int l=0;l<9;l++)
        {
            for(int p=0;p<9;p++)
            {
                graph2[l][p]=-1;
                graph[l][p]=-1;
            }
        }
        Iterator it = operations.entrySet().iterator();
        while(it.hasNext())
        {
            HashMap.Entry pair = (HashMap.Entry) it.next(); 
            String[] dokimi = (String[]) pair.getValue();
            int limit = dokimi.length-3;
            String[] te = new String[limit];
            
            if(limit==1)
            {
                int one = 0;
                int two = dokimi[0].charAt(0) - 'A' + 1;
                graph[one][two] = Integer.parseInt(dokimi[limit+1]);
                double prob = Double.parseDouble(dokimi[limit+2]);
                graph2[one][two] = 1 - prob;
            }
            else
            {
             for(int y=0;y<limit-1;y++)
             {
                int one = dokimi[y].charAt(0) - 'A' + 1;
                int two = dokimi[limit-1].charAt(0) - 'A' +1;
                //graph[one][two]=Integer.parseInt(dokimi[limit]);
                graph[two][one]=Integer.parseInt(dokimi[limit+1]);
                double prob = Double.parseDouble(dokimi[limit+2]);
                //graph2[one][two] = 1 - prob;
                graph2[two][one] = 1 - prob;
                
             }
            }
            
        }
        
        int dist[] = new int[operations.size()+1];
        double distProb[] = new double[operations.size()+1];
        ShortestPath tp = new ShortestPath();
        ShortestPathProb tpp = new ShortestPathProb();
        dist = tp.dijkstra(graph, 1);
        distProb = tpp.dijkstra(graph2, 1);
        
        int n = input.size();
        double[][] cost = new double[n][2];
        
        Iterator t = input.entrySet().iterator();
        int i=0;
        while(t.hasNext())
        {
          HashMap.Entry pair = (HashMap.Entry) t.next();  // takes from the dist array, which contains the distance from root for all operations, the distances
          String[] temp15= (String[]) pair.getValue(); // of the available for execution operations and saves them in the cost array
          int tempkey = (Integer) pair.getKey();
          cost[i][0] = tempkey;
          int limit = temp15.length-3;
          if(limit==1)
          {
           char index = temp15[0].charAt(0);
           int ind = index - 'A' +1;
           int denominator = dist[ind];
           double numerator = distProb[ind];
           cost[i][1] = numerator/denominator;
              
          }
          else
          {
           char index = temp15[limit-1].charAt(0);
           int ind = index - 'A' +1;
           int denominator = dist[ind];
           double numerator = distProb[ind];
           cost[i][1] = numerator/denominator;   
          }
          
          
          i++;
        }
        int key = (int) getMaxValue(cost); // the rule with the lowest value(distance from root) is selected for execution
        //System.out.println(key);
        /*
        String[] temp16 = input.get(key);
        double prob = Double.parseDouble(temp16[temp16.length-1]);
        input.remove(key);
        int result = productions.get(key);
        if(result ==1) // the execution was successful and therefore the element is produced and added to the set of available elements
        {
            available.add(temp16[temp16.length-4]);
            System.out.println("Op" + key+ "," + temp16[temp16.length-4] + " , instance: " + inst + " start_time: " + current_time + " , end time:" + (current_time + Integer.parseInt(temp16[temp16.length-3])));
            path.put(key, 0);
            allocation.add(temp16[0]);
            end.add(current_time + Integer.parseInt(temp16[temp16.length-3]));
        }
        else //execution was unsuccessful and therefore the element was not produced
        {
            System.out.println("Op" + key+ "," + temp16[temp16.length-4] + " fail , instance: " + " , instance: " + inst + " start_time: " + current_time + " , end time:" + (current_time + Integer.parseInt(temp16[temp16.length-3])));
            path.put(key, 1);
            allocation.add(temp16[0]);
            end.add(current_time + Integer.parseInt(temp16[temp16.length-3]));
            //pruneOperations(operations,key,complete);
        }
        */
        return key;
        
       
   }

private static int knockoutPathPrune(HashMap<Integer,String[]> input, HashSet<String> available, HashMap<Integer,String[]> operations)
   {
        int graph[][] = new int[9][9];
        double graph2[][] = new double [9][9];
        for(int l=0;l<9;l++)
        {
            for(int p=0;p<9;p++)
            {
                graph2[l][p]=-1;
                graph[l][p]=-1;
            }
        }
        Iterator it = operations.entrySet().iterator();
        while(it.hasNext())
        {
            HashMap.Entry pair = (HashMap.Entry) it.next(); 
            String[] dokimi = (String[]) pair.getValue();
            int limit = dokimi.length-3;
            String[] te = new String[limit];
            
            
            if(limit==1)
            {
                int one = 0;
                int two = dokimi[1].charAt(0) - 'A' + 1;
                graph[one][two] = Integer.parseInt(dokimi[limit+1]);
                double prob = Double.parseDouble(dokimi[limit+2]);
                graph2[one][two] = 1 - prob;
            }
            else
            {
             for(int y=1;y<limit-1;y++)
             {
                int one = dokimi[y].charAt(0) - 'A' + 1;
                int two = dokimi[limit-1].charAt(0) - 'A' +1;
                //graph[one][two]=Integer.parseInt(dokimi[limit]);
                graph[two][one]=Integer.parseInt(dokimi[limit+1]);
                double prob = Double.parseDouble(dokimi[limit+2]);
                //graph2[one][two] = 1 - prob;
                graph2[two][one] = 1 - prob;
                
             }
            }
            
        }
        
        int dist[] = new int[operations.size()+1];
        double distProb[] = new double[operations.size()+1];
        ShortestPath tp = new ShortestPath();
        ShortestPathProb tpp = new ShortestPathProb();
        dist = tp.dijkstra(graph, 1);
        distProb = tpp.dijkstra(graph2, 1);
        
        int n = input.size();
        double[][] cost = new double[n][2];
        
        Iterator t = input.entrySet().iterator();
        int i=0;
        while(t.hasNext())
        {
          HashMap.Entry pair = (HashMap.Entry) t.next();  // takes from the dist array, which contains the distance from root for all operations, the distances
          String[] temp15= (String[]) pair.getValue(); // of the available for execution operations and saves them in the cost array
          int tempkey = (Integer) pair.getKey();
          cost[i][0] = tempkey;
          int limit = temp15.length-3;
          if(limit==1)
          {
           char index = temp15[0].charAt(0);
           int ind = index - 'A' +1;
           int denominator = dist[ind];
           double numerator = distProb[ind];
           cost[i][1] = numerator/denominator;
              
          }
          else
          {
           char index = temp15[limit-1].charAt(0);
           int ind = index - 'A' +1;
           int denominator = dist[ind];
           double numerator = distProb[ind];
           cost[i][1] = numerator/denominator;   
          }
          
          
          i++;
        }
        int key = (int) getMaxValue(cost); // the rule with the lowest value(distance from root) is selected for execution
        //System.out.println(key);
        /*
        String[] temp16 = input.get(key);
        double prob = Double.parseDouble(temp16[temp16.length-1]);
        input.remove(key);
        int result = productions.get(key);
        if(result ==1) // the execution was successful and therefore the element is produced and added to the set of available elements
        {
            available.add(temp16[temp16.length-4]);
            System.out.println("Op" + key+ "," + temp16[temp16.length-4] + " , instance: " + inst + " start_time: " + current_time + " , end time:" + (current_time + Integer.parseInt(temp16[temp16.length-3])));
            path.put(key, 0);
            allocation.add(temp16[0]);
            end.add(current_time + Integer.parseInt(temp16[temp16.length-3]));
        }
        else //execution was unsuccessful and therefore the element was not produced
        {
            System.out.println("Op" + key+ "," + temp16[temp16.length-4] + " fail , instance: " + " , instance: " + inst + " start_time: " + current_time + " , end time:" + (current_time + Integer.parseInt(temp16[temp16.length-3])));
            path.put(key, 1);
            prune(operations,key,prune);
            allocation.add(temp16[0]);
            end.add(current_time + Integer.parseInt(temp16[temp16.length-3]));
        }
        */
        return key;
        
       
   }

private static int knockoutPathPruneBaseline(HashMap<Integer,String[]> input, HashSet<String> available, HashMap<Integer,String[]> operations)
   {
        int graph[][] = new int[9][9];
        double graph2[][] = new double [9][9];
        for(int l=0;l<9;l++)
        {
            for(int p=0;p<9;p++)
            {
                graph2[l][p]=-1;
                graph[l][p]=-1;
            }
        }
        Iterator it = operations.entrySet().iterator();
        while(it.hasNext())
        {
            HashMap.Entry pair = (HashMap.Entry) it.next(); 
            String[] dokimi = (String[]) pair.getValue();
            int limit = dokimi.length-3;
            String[] te = new String[limit];
            
            
            if(limit==1)
            {
                int one = 0;
                int two = dokimi[0].charAt(0) - 'A' + 1;
                graph[one][two] = Integer.parseInt(dokimi[limit+1]);
                double prob = Double.parseDouble(dokimi[limit+2]);
                graph2[one][two] = 1 - prob;
            }
            else
            {
             for(int y=0;y<limit-1;y++)
             {
                int one = dokimi[y].charAt(0) - 'A' + 1;
                int two = dokimi[limit-1].charAt(0) - 'A' +1;
                //graph[one][two]=Integer.parseInt(dokimi[limit]);
                graph[two][one]=Integer.parseInt(dokimi[limit+1]);
                double prob = Double.parseDouble(dokimi[limit+2]);
                //graph2[one][two] = 1 - prob;
                graph2[two][one] = 1 - prob;
                
             }
            }
            
        }
        
        int dist[] = new int[operations.size()+1];
        double distProb[] = new double[operations.size()+1];
        ShortestPath tp = new ShortestPath();
        ShortestPathProb tpp = new ShortestPathProb();
        dist = tp.dijkstra(graph, 1);
        distProb = tpp.dijkstra(graph2, 1);
        
        int n = input.size();
        double[][] cost = new double[n][2];
        
        Iterator t = input.entrySet().iterator();
        int i=0;
        while(t.hasNext())
        {
          HashMap.Entry pair = (HashMap.Entry) t.next();  // takes from the dist array, which contains the distance from root for all operations, the distances
          String[] temp15= (String[]) pair.getValue(); // of the available for execution operations and saves them in the cost array
          int tempkey = (Integer) pair.getKey();
          cost[i][0] = tempkey;
          int limit = temp15.length-3;
          if(limit==1)
          {
           char index = temp15[0].charAt(0);
           int ind = index - 'A' +1;
           int denominator = dist[ind];
           double numerator = distProb[ind];
           cost[i][1] = numerator/denominator;
              
          }
          else
          {
           char index = temp15[limit-1].charAt(0);
           int ind = index - 'A' +1;
           int denominator = dist[ind];
           double numerator = distProb[ind];
           cost[i][1] = numerator/denominator;   
          }
          
          
          i++;
        }
        int key = (int) getMaxValue(cost); // the rule with the lowest value(distance from root) is selected for execution
        //System.out.println(key);
        /*
        String[] temp16 = input.get(key);
        double prob = Double.parseDouble(temp16[temp16.length-1]);
        input.remove(key);
        int result = productions.get(key);
        if(result ==1) // the execution was successful and therefore the element is produced and added to the set of available elements
        {
            available.add(temp16[temp16.length-4]);
            System.out.println("Op" + key+ "," + temp16[temp16.length-4] + " , instance: " + inst + " start_time: " + current_time + " , end time:" + (current_time + Integer.parseInt(temp16[temp16.length-3])));
            path.put(key, 0);
            allocation.add(temp16[0]);
            end.add(current_time + Integer.parseInt(temp16[temp16.length-3]));
        }
        else //execution was unsuccessful and therefore the element was not produced
        {
            System.out.println("Op" + key+ "," + temp16[temp16.length-4] + " fail , instance: " + " , instance: " + inst + " start_time: " + current_time + " , end time:" + (current_time + Integer.parseInt(temp16[temp16.length-3])));
            path.put(key, 1);
            prune(operations,key,prune);
            allocation.add(temp16[0]);
            end.add(current_time + Integer.parseInt(temp16[temp16.length-3]));
        }
        */
        return key;
        
       
   }

  private static int knockoutPathPruneBoth(HashMap<Integer,String[]> input, HashSet<String> available, HashMap<Integer,String[]> operations)
   {
        int graph[][] = new int[9][9];
        double graph2[][] = new double [9][9];
        for(int l=0;l<9;l++)
        {
            for(int p=0;p<9;p++)
            {
                graph2[l][p]=-1;
                graph[l][p]=-1;
            }
        }
        Iterator it = operations.entrySet().iterator();
        while(it.hasNext())
        {
            HashMap.Entry pair = (HashMap.Entry) it.next(); 
            String[] dokimi = (String[]) pair.getValue();
            int limit = dokimi.length-3;
            String[] te = new String[limit];
            
            
            if(limit==1)
            {
                int one = 0;
                int two = dokimi[1].charAt(0) - 'A' + 1;
                graph[one][two] = Integer.parseInt(dokimi[limit+1]);
                double prob = Double.parseDouble(dokimi[limit+2]);
                graph2[one][two] = 1 - prob;
            }
            else
            {
             for(int y=1;y<limit-1;y++)
             {
                int one = dokimi[y].charAt(0) - 'A' + 1;
                int two = dokimi[limit-1].charAt(0) - 'A' +1;
                //graph[one][two]=Integer.parseInt(dokimi[limit]);
                graph[two][one]=Integer.parseInt(dokimi[limit+1]);
                double prob = Double.parseDouble(dokimi[limit+2]);
                //graph2[one][two] = 1 - prob;
                graph2[two][one] = 1 - prob;
                
             }
            }
            
        }
        
        int dist[] = new int[operations.size()+1];
        double distProb[] = new double[operations.size()+1];
        ShortestPath tp = new ShortestPath();
        ShortestPathProb tpp = new ShortestPathProb();
        dist = tp.dijkstra(graph, 1);
        distProb = tpp.dijkstra(graph2, 1);
        int[] dist2 = new int[dist.length];
        for(int f=1;f<dist.length;f++)
        {
            prune2(operations,f,graph,dist2);
        }
        for(int f=1;f<dist.length;f++)
        {
           dist[f] += dist2[f];
        }        
        int n = input.size();
        double[][] cost = new double[n][2];
        
        Iterator t = input.entrySet().iterator();
        int i=0;
        while(t.hasNext())
        {
          HashMap.Entry pair = (HashMap.Entry) t.next();  // takes from the dist array, which contains the distance from root for all operations, the distances
          String[] temp15= (String[]) pair.getValue(); // of the available for execution operations and saves them in the cost array
          int tempkey = (Integer) pair.getKey();
          cost[i][0] = tempkey;
          int limit = temp15.length-3;
          if(limit==1)
          {
           char index = temp15[0].charAt(0);
           int ind = index - 'A' +1;
           int denominator = dist[ind];
           double numerator = distProb[ind];
           cost[i][1] = numerator/denominator;
              
          }
          else
          {
           char index = temp15[limit-1].charAt(0);
           int ind = index - 'A' +1;
           int denominator = dist[ind];
           double numerator = distProb[ind];
           cost[i][1] = numerator/denominator;   
          }
          
          
          i++;
        }
        int key = (int) getMaxValue(cost); // the rule with the lowest value(distance from root) is selected for execution
        //System.out.println(key);
        /*
        String[] temp16 = input.get(key);
        double prob = Double.parseDouble(temp16[temp16.length-1]);
        input.remove(key);
        int result = productions.get(key);
        if(result ==1) // the execution was successful and therefore the element is produced and added to the set of available elements
        {
            available.add(temp16[temp16.length-4]);
            System.out.println("Op" + key+ "," + temp16[temp16.length-4] + " , instance: " + inst + " start_time: " + current_time + " , end time:" + (current_time + Integer.parseInt(temp16[temp16.length-3])));
            path.put(key, 0);
            allocation.add(temp16[0]);
            end.add(current_time + Integer.parseInt(temp16[temp16.length-3]));
        }
        else //execution was unsuccessful and therefore the element was not produced
        {
            System.out.println("Op" + key+ "," + temp16[temp16.length-4] + " fail , instance: " + " , instance: " + inst + " start_time: " + current_time + " , end time:" + (current_time + Integer.parseInt(temp16[temp16.length-3])));
            path.put(key, 1);
            prune(operations,key,prune);
            allocation.add(temp16[0]);
            end.add(current_time + Integer.parseInt(temp16[temp16.length-3]));
        }
        */
        return key;
        
       
   }
  
  private static int knockoutPathPruneBothBaseline(HashMap<Integer,String[]> input, HashSet<String> available, HashMap<Integer,String[]> operations)
   {
        int graph[][] = new int[9][9];
        double graph2[][] = new double [9][9];
        for(int l=0;l<9;l++)
        {
            for(int p=0;p<9;p++)
            {
                graph2[l][p]=-1;
                graph[l][p]=-1;
            }
        }
        Iterator it = operations.entrySet().iterator();
        while(it.hasNext())
        {
            HashMap.Entry pair = (HashMap.Entry) it.next(); 
            String[] dokimi = (String[]) pair.getValue();
            int limit = dokimi.length-3;
            String[] te = new String[limit];
            
            
            if(limit==1)
            {
                int one = 0;
                int two = dokimi[0].charAt(0) - 'A' + 1;
                graph[one][two] = Integer.parseInt(dokimi[limit+1]);
                double prob = Double.parseDouble(dokimi[limit+2]);
                graph2[one][two] = 1 - prob;
            }
            else
            {
             for(int y=0;y<limit-1;y++)
             {
                int one = dokimi[y].charAt(0) - 'A' + 1;
                int two = dokimi[limit-1].charAt(0) - 'A' +1;
                //graph[one][two]=Integer.parseInt(dokimi[limit]);
                graph[two][one]=Integer.parseInt(dokimi[limit+1]);
                double prob = Double.parseDouble(dokimi[limit+2]);
                //graph2[one][two] = 1 - prob;
                graph2[two][one] = 1 - prob;
                
             }
            }
            
        }
        
        int dist[] = new int[operations.size()+1];
        double distProb[] = new double[operations.size()+1];
        ShortestPath tp = new ShortestPath();
        ShortestPathProb tpp = new ShortestPathProb();
        dist = tp.dijkstra(graph, 1);
        distProb = tpp.dijkstra(graph2, 1);
        int[] dist2 = new int[dist.length];
        for(int f=1;f<dist.length;f++)
        {
            prune2(operations,f,graph,dist2);
        }
        for(int f=1;f<dist.length;f++)
        {
           dist[f] += dist2[f];
        }        
        int n = input.size();
        double[][] cost = new double[n][2];
        
        Iterator t = input.entrySet().iterator();
        int i=0;
        while(t.hasNext())
        {
          HashMap.Entry pair = (HashMap.Entry) t.next();  // takes from the dist array, which contains the distance from root for all operations, the distances
          String[] temp15= (String[]) pair.getValue(); // of the available for execution operations and saves them in the cost array
          int tempkey = (Integer) pair.getKey();
          cost[i][0] = tempkey;
          int limit = temp15.length-3;
          if(limit==1)
          {
           char index = temp15[0].charAt(0);
           int ind = index - 'A' +1;
           int denominator = dist[ind];
           double numerator = distProb[ind];
           cost[i][1] = numerator/denominator;
              
          }
          else
          {
           char index = temp15[limit-1].charAt(0);
           int ind = index - 'A' +1;
           int denominator = dist[ind];
           double numerator = distProb[ind];
           cost[i][1] = numerator/denominator;   
          }
          
          
          i++;
        }
        int key = (int) getMaxValue(cost); // the rule with the lowest value(distance from root) is selected for execution
        //System.out.println(key);
        /*
        String[] temp16 = input.get(key);
        double prob = Double.parseDouble(temp16[temp16.length-1]);
        input.remove(key);
        int result = productions.get(key);
        if(result ==1) // the execution was successful and therefore the element is produced and added to the set of available elements
        {
            available.add(temp16[temp16.length-4]);
            System.out.println("Op" + key+ "," + temp16[temp16.length-4] + " , instance: " + inst + " start_time: " + current_time + " , end time:" + (current_time + Integer.parseInt(temp16[temp16.length-3])));
            path.put(key, 0);
            allocation.add(temp16[0]);
            end.add(current_time + Integer.parseInt(temp16[temp16.length-3]));
        }
        else //execution was unsuccessful and therefore the element was not produced
        {
            System.out.println("Op" + key+ "," + temp16[temp16.length-4] + " fail , instance: " + " , instance: " + inst + " start_time: " + current_time + " , end time:" + (current_time + Integer.parseInt(temp16[temp16.length-3])));
            path.put(key, 1);
            prune(operations,key,prune);
            allocation.add(temp16[0]);
            end.add(current_time + Integer.parseInt(temp16[temp16.length-3]));
        }
        */
        return key;
        
       
   }
  
  private static int knockoutPathPruneBothTime(HashMap<Integer,String[]> input, HashSet<String> available, HashMap<Integer,String[]> operations)
   {
        int graph[][] = new int[9][9];
        double graph2[][] = new double [9][9];
        for(int l=0;l<9;l++)
        {
            for(int p=0;p<9;p++)
            {
                graph2[l][p]=-1;
                graph[l][p]=-1;
            }
        }
        Iterator it = operations.entrySet().iterator();
        while(it.hasNext())
        {
            HashMap.Entry pair = (HashMap.Entry) it.next(); 
            String[] dokimi = (String[]) pair.getValue();
            int limit = dokimi.length-3;
            String[] te = new String[limit];
            
            
            if(limit==1)
            {
                int one = 0;
                int two = dokimi[1].charAt(0) - 'A' + 1;
                graph[one][two] = Integer.parseInt(dokimi[limit]);
                double prob = Double.parseDouble(dokimi[limit+2]);
                graph2[one][two] = 1 - prob;
            }
            else
            {
             for(int y=1;y<limit-1;y++)
             {
                int one = dokimi[y].charAt(0) - 'A' + 1;
                int two = dokimi[limit-1].charAt(0) - 'A' +1;
                //graph[one][two]=Integer.parseInt(dokimi[limit]);
                graph[two][one]=Integer.parseInt(dokimi[limit]);
                double prob = Double.parseDouble(dokimi[limit+2]);
                //graph2[one][two] = 1 - prob;
                graph2[two][one] = 1 - prob;
                
             }
            }
            
        }
        
        int dist[] = new int[operations.size()+1];
        double distProb[] = new double[operations.size()+1];
        ShortestPath tp = new ShortestPath();
        ShortestPathProb tpp = new ShortestPathProb();
        dist = tp.dijkstra(graph, 1);
        distProb = tpp.dijkstra(graph2, 1);
        int[] dist2 = new int[dist.length];
        for(int f=1;f<dist.length;f++)
        {
            prune2(operations,f,graph,dist2);
        }
        for(int f=1;f<dist.length;f++)
        {
           dist[f] += dist2[f];
        }        
        int n = input.size();
        double[][] cost = new double[n][2];
        
        Iterator t = input.entrySet().iterator();
        int i=0;
        while(t.hasNext())
        {
          HashMap.Entry pair = (HashMap.Entry) t.next();  // takes from the dist array, which contains the distance from root for all operations, the distances
          String[] temp15= (String[]) pair.getValue(); // of the available for execution operations and saves them in the cost array
          int tempkey = (Integer) pair.getKey();
          cost[i][0] = tempkey;
          int limit = temp15.length-3;
          if(limit==1)
          {
           char index = temp15[0].charAt(0);
           int ind = index - 'A' +1;
           int denominator = dist[ind];
           double numerator = distProb[ind];
           cost[i][1] = numerator/denominator;
              
          }
          else
          {
           char index = temp15[limit-1].charAt(0);
           int ind = index - 'A' +1;
           int denominator = dist[ind];
           double numerator = distProb[ind];
           cost[i][1] = numerator/denominator;   
          }
          
          
          i++;
        }
        int key = (int) getMaxValue(cost); // the rule with the lowest value(distance from root) is selected for execution
        //System.out.println(key);
        /*
        String[] temp16 = input.get(key);
        double prob = Double.parseDouble(temp16[temp16.length-1]);
        input.remove(key);
        int result = productions.get(key);
        if(result ==1) // the execution was successful and therefore the element is produced and added to the set of available elements
        {
            available.add(temp16[temp16.length-4]);
            System.out.println("Op" + key+ "," + temp16[temp16.length-4] + " , instance: " + inst + " start_time: " + current_time + " , end time:" + (current_time + Integer.parseInt(temp16[temp16.length-3])));
            path.put(key, 0);
            allocation.add(temp16[0]);
            end.add(current_time + Integer.parseInt(temp16[temp16.length-3]));
        }
        else //execution was unsuccessful and therefore the element was not produced
        {
            System.out.println("Op" + key+ "," + temp16[temp16.length-4] + " fail , instance: " + " , instance: " + inst + " start_time: " + current_time + " , end time:" + (current_time + Integer.parseInt(temp16[temp16.length-3])));
            path.put(key, 1);
            prune(operations,key,prune);
            allocation.add(temp16[0]);
            end.add(current_time + Integer.parseInt(temp16[temp16.length-3]));
        }
        */
        return key;
        
       
   }
  
  private static int knockoutPathPruneBothTimeBaseline(HashMap<Integer,String[]> input, HashSet<String> available, HashMap<Integer,String[]> operations)
   {
        int graph[][] = new int[9][9];
        double graph2[][] = new double [9][9];
        for(int l=0;l<9;l++)
        {
            for(int p=0;p<9;p++)
            {
                graph2[l][p]=-1;
                graph[l][p]=-1;
            }
        }
        Iterator it = operations.entrySet().iterator();
        while(it.hasNext())
        {
            HashMap.Entry pair = (HashMap.Entry) it.next(); 
            String[] dokimi = (String[]) pair.getValue();
            int limit = dokimi.length-3;
            String[] te = new String[limit];
            
            
            if(limit==1)
            {
                int one = 0;
                int two = dokimi[0].charAt(0) - 'A' + 1;
                graph[one][two] = Integer.parseInt(dokimi[limit]);
                double prob = Double.parseDouble(dokimi[limit+2]);
                graph2[one][two] = 1 - prob;
            }
            else
            {
             for(int y=0;y<limit-1;y++)
             {
                int one = dokimi[y].charAt(0) - 'A' + 1;
                int two = dokimi[limit-1].charAt(0) - 'A' +1;
                //graph[one][two]=Integer.parseInt(dokimi[limit]);
                graph[two][one]=Integer.parseInt(dokimi[limit]);
                double prob = Double.parseDouble(dokimi[limit+2]);
                //graph2[one][two] = 1 - prob;
                graph2[two][one] = 1 - prob;
                
             }
            }
            
        }
        
        int dist[] = new int[operations.size()+1];
        double distProb[] = new double[operations.size()+1];
        ShortestPath tp = new ShortestPath();
        ShortestPathProb tpp = new ShortestPathProb();
        dist = tp.dijkstra(graph, 1);
        distProb = tpp.dijkstra(graph2, 1);
        int[] dist2 = new int[dist.length];
        for(int f=1;f<dist.length;f++)
        {
            prune2(operations,f,graph,dist2);
        }
        for(int f=1;f<dist.length;f++)
        {
           dist[f] += dist2[f];
        }        
        int n = input.size();
        double[][] cost = new double[n][2];
        
        Iterator t = input.entrySet().iterator();
        int i=0;
        while(t.hasNext())
        {
          HashMap.Entry pair = (HashMap.Entry) t.next();  // takes from the dist array, which contains the distance from root for all operations, the distances
          String[] temp15= (String[]) pair.getValue(); // of the available for execution operations and saves them in the cost array
          int tempkey = (Integer) pair.getKey();
          cost[i][0] = tempkey;
          int limit = temp15.length-3;
          if(limit==1)
          {
           char index = temp15[0].charAt(0);
           int ind = index - 'A' +1;
           int denominator = dist[ind];
           double numerator = distProb[ind];
           cost[i][1] = numerator/denominator;
              
          }
          else
          {
           char index = temp15[limit-1].charAt(0);
           int ind = index - 'A' +1;
           int denominator = dist[ind];
           double numerator = distProb[ind];
           cost[i][1] = numerator/denominator;   
          }
          
          
          i++;
        }
        int key = (int) getMaxValue(cost); // the rule with the lowest value(distance from root) is selected for execution
        //System.out.println(key);
        /*
        String[] temp16 = input.get(key);
        double prob = Double.parseDouble(temp16[temp16.length-1]);
        input.remove(key);
        int result = productions.get(key);
        if(result ==1) // the execution was successful and therefore the element is produced and added to the set of available elements
        {
            available.add(temp16[temp16.length-4]);
            System.out.println("Op" + key+ "," + temp16[temp16.length-4] + " , instance: " + inst + " start_time: " + current_time + " , end time:" + (current_time + Integer.parseInt(temp16[temp16.length-3])));
            path.put(key, 0);
            allocation.add(temp16[0]);
            end.add(current_time + Integer.parseInt(temp16[temp16.length-3]));
        }
        else //execution was unsuccessful and therefore the element was not produced
        {
            System.out.println("Op" + key+ "," + temp16[temp16.length-4] + " fail , instance: " + " , instance: " + inst + " start_time: " + current_time + " , end time:" + (current_time + Integer.parseInt(temp16[temp16.length-3])));
            path.put(key, 1);
            prune(operations,key,prune);
            allocation.add(temp16[0]);
            end.add(current_time + Integer.parseInt(temp16[temp16.length-3]));
        }
        */
        return key;
        
       
   }
  
  private static int knockoutPathPruneBothCombo(HashMap<Integer,String[]> input, HashSet<String> available, HashMap<Integer,String[]> operations)
   {
        int graph[][] = new int[9][9];
        double graph2[][] = new double [9][9];
        for(int l=0;l<9;l++)
        {
            for(int p=0;p<9;p++)
            {
                graph2[l][p]=-1;
                graph[l][p]=-1;
            }
        }
        Iterator it = operations.entrySet().iterator();
        while(it.hasNext())
        {
            HashMap.Entry pair = (HashMap.Entry) it.next(); 
            String[] dokimi = (String[]) pair.getValue();
            int limit = dokimi.length-3;
            String[] te = new String[limit];
            
            
            if(limit==1)
            {
                int one = 0;
                int two = dokimi[1].charAt(0) - 'A' + 1;
                graph[one][two] = Integer.parseInt(dokimi[limit+1]) + Integer.parseInt(dokimi[limit]);
                double prob = Double.parseDouble(dokimi[limit+2]);
                graph2[one][two] = 1 - prob;
            }
            else
            {
             for(int y=1;y<limit-1;y++)
             {
                int one = dokimi[y].charAt(0) - 'A' + 1;
                int two = dokimi[limit-1].charAt(0) - 'A' +1;
                //graph[one][two]=Integer.parseInt(dokimi[limit]);
                graph[two][one]=Integer.parseInt(dokimi[limit+1]) + Integer.parseInt(dokimi[limit]);
                double prob = Double.parseDouble(dokimi[limit+2]);
                //graph2[one][two] = 1 - prob;
                graph2[two][one] = 1 - prob;
                
             }
            }
            
        }
        
        int dist[] = new int[operations.size()+1];
        double distProb[] = new double[operations.size()+1];
        ShortestPath tp = new ShortestPath();
        ShortestPathProb tpp = new ShortestPathProb();
        dist = tp.dijkstra(graph, 1);
        distProb = tpp.dijkstra(graph2, 1);
        int[] dist2 = new int[dist.length];
        for(int f=1;f<dist.length;f++)
        {
            prune2(operations,f,graph,dist2);
        }
        for(int f=1;f<dist.length;f++)
        {
           dist[f] += dist2[f];
        }        
        int n = input.size();
        double[][] cost = new double[n][2];
        
        Iterator t = input.entrySet().iterator();
        int i=0;
        while(t.hasNext())
        {
          HashMap.Entry pair = (HashMap.Entry) t.next();  // takes from the dist array, which contains the distance from root for all operations, the distances
          String[] temp15= (String[]) pair.getValue(); // of the available for execution operations and saves them in the cost array
          int tempkey = (Integer) pair.getKey();
          cost[i][0] = tempkey;
          int limit = temp15.length-3;
          if(limit==1)
          {
           char index = temp15[0].charAt(0);
           int ind = index - 'A' +1;
           int denominator = dist[ind];
           double numerator = distProb[ind];
           cost[i][1] = numerator/denominator;
              
          }
          else
          {
           char index = temp15[limit-1].charAt(0);
           int ind = index - 'A' +1;
           int denominator = dist[ind];
           double numerator = distProb[ind];
           cost[i][1] = numerator/denominator;   
          }
          
          
          i++;
        }
        int key = (int) getMaxValue(cost); // the rule with the lowest value(distance from root) is selected for execution
        //System.out.println(key);
        /*
        String[] temp16 = input.get(key);
        double prob = Double.parseDouble(temp16[temp16.length-1]);
        input.remove(key);
        int result = productions.get(key);
        if(result ==1) // the execution was successful and therefore the element is produced and added to the set of available elements
        {
            available.add(temp16[temp16.length-4]);
            System.out.println("Op" + key+ "," + temp16[temp16.length-4] + " , instance: " + inst + " start_time: " + current_time + " , end time:" + (current_time + Integer.parseInt(temp16[temp16.length-3])));
            path.put(key, 0);
            allocation.add(temp16[0]);
            end.add(current_time + Integer.parseInt(temp16[temp16.length-3]));
        }
        else //execution was unsuccessful and therefore the element was not produced
        {
            System.out.println("Op" + key+ "," + temp16[temp16.length-4] + " fail , instance: " + " , instance: " + inst + " start_time: " + current_time + " , end time:" + (current_time + Integer.parseInt(temp16[temp16.length-3])));
            path.put(key, 1);
            prune(operations,key,prune);
            allocation.add(temp16[0]);
            end.add(current_time + Integer.parseInt(temp16[temp16.length-3]));
        }
        */
        return key;
        
       
   }
  
  private static int knockoutPathPruneBothComboBaseline(HashMap<Integer,String[]> input, HashSet<String> available, HashMap<Integer,String[]> operations)
   {
        int graph[][] = new int[9][9];
        double graph2[][] = new double [9][9];
        for(int l=0;l<9;l++)
        {
            for(int p=0;p<9;p++)
            {
                graph2[l][p]=-1;
                graph[l][p]=-1;
            }
        }
        Iterator it = operations.entrySet().iterator();
        while(it.hasNext())
        {
            HashMap.Entry pair = (HashMap.Entry) it.next(); 
            String[] dokimi = (String[]) pair.getValue();
            int limit = dokimi.length-3;
            String[] te = new String[limit];
            
            
            if(limit==1)
            {
                int one = 0;
                int two = dokimi[0].charAt(0) - 'A' + 1;
                graph[one][two] = Integer.parseInt(dokimi[limit+1]) + Integer.parseInt(dokimi[limit]);
                double prob = Double.parseDouble(dokimi[limit+2]);
                graph2[one][two] = 1 - prob;
            }
            else
            {
             for(int y=0;y<limit-1;y++)
             {
                int one = dokimi[y].charAt(0) - 'A' + 1;
                int two = dokimi[limit-1].charAt(0) - 'A' +1;
                //graph[one][two]=Integer.parseInt(dokimi[limit]);
                graph[two][one]=Integer.parseInt(dokimi[limit+1]) + Integer.parseInt(dokimi[limit]);
                double prob = Double.parseDouble(dokimi[limit+2]);
                //graph2[one][two] = 1 - prob;
                graph2[two][one] = 1 - prob;
                
             }
            }
            
        }
        
        int dist[] = new int[operations.size()+1];
        double distProb[] = new double[operations.size()+1];
        ShortestPath tp = new ShortestPath();
        ShortestPathProb tpp = new ShortestPathProb();
        dist = tp.dijkstra(graph, 1);
        distProb = tpp.dijkstra(graph2, 1);
        int[] dist2 = new int[dist.length];
        for(int f=1;f<dist.length;f++)
        {
            prune2(operations,f,graph,dist2);
        }
        for(int f=1;f<dist.length;f++)
        {
           dist[f] += dist2[f];
        }        
        int n = input.size();
        double[][] cost = new double[n][2];
        
        Iterator t = input.entrySet().iterator();
        int i=0;
        while(t.hasNext())
        {
          HashMap.Entry pair = (HashMap.Entry) t.next();  // takes from the dist array, which contains the distance from root for all operations, the distances
          String[] temp15= (String[]) pair.getValue(); // of the available for execution operations and saves them in the cost array
          int tempkey = (Integer) pair.getKey();
          cost[i][0] = tempkey;
          int limit = temp15.length-3;
          if(limit==1)
          {
           char index = temp15[0].charAt(0);
           int ind = index - 'A' +1;
           int denominator = dist[ind];
           double numerator = distProb[ind];
           cost[i][1] = numerator/denominator;
              
          }
          else
          {
           char index = temp15[limit-1].charAt(0);
           int ind = index - 'A' +1;
           int denominator = dist[ind];
           double numerator = distProb[ind];
           cost[i][1] = numerator/denominator;   
          }
          
          
          i++;
        }
        int key = (int) getMaxValue(cost); // the rule with the lowest value(distance from root) is selected for execution
        //System.out.println(key);
        /*
        String[] temp16 = input.get(key);
        double prob = Double.parseDouble(temp16[temp16.length-1]);
        input.remove(key);
        int result = productions.get(key);
        if(result ==1) // the execution was successful and therefore the element is produced and added to the set of available elements
        {
            available.add(temp16[temp16.length-4]);
            System.out.println("Op" + key+ "," + temp16[temp16.length-4] + " , instance: " + inst + " start_time: " + current_time + " , end time:" + (current_time + Integer.parseInt(temp16[temp16.length-3])));
            path.put(key, 0);
            allocation.add(temp16[0]);
            end.add(current_time + Integer.parseInt(temp16[temp16.length-3]));
        }
        else //execution was unsuccessful and therefore the element was not produced
        {
            System.out.println("Op" + key+ "," + temp16[temp16.length-4] + " fail , instance: " + " , instance: " + inst + " start_time: " + current_time + " , end time:" + (current_time + Integer.parseInt(temp16[temp16.length-3])));
            path.put(key, 1);
            prune(operations,key,prune);
            allocation.add(temp16[0]);
            end.add(current_time + Integer.parseInt(temp16[temp16.length-3]));
        }
        */
        return key;
        
       
   }
   
   
   private static int knockoutPathTime(HashMap<Integer,String[]> input, HashSet<String> available, HashMap<Integer,String[]> operations)
   {
        int graph[][] = new int[9][9];
        double graph2[][] = new double [9][9];
        for(int l=0;l<9;l++)
        {
            for(int p=0;p<9;p++)
            {
                graph2[l][p]=-1;
                graph[l][p]=-1;
            }
        }
        Iterator it = operations.entrySet().iterator();
        while(it.hasNext())
        {
            HashMap.Entry pair = (HashMap.Entry) it.next(); 
            String[] dokimi = (String[]) pair.getValue();
            int limit = dokimi.length-3;
            String[] te = new String[limit];
            
            if(limit==1)
            {
                int one = 0;
                int two = dokimi[1].charAt(0) - 'A' + 1;
                graph[one][two] = Integer.parseInt(dokimi[limit]);
                double prob = Double.parseDouble(dokimi[limit+2]);
                graph2[one][two] = 1 - prob;
            }
            else
            {
             for(int y=1;y<limit-1;y++)
             {
                int one = dokimi[y].charAt(0) - 'A' + 1;
                int two = dokimi[limit-1].charAt(0) - 'A' +1;
                //graph[one][two]=Integer.parseInt(dokimi[limit]);
                graph[two][one]=Integer.parseInt(dokimi[limit]);
                double prob = Double.parseDouble(dokimi[limit+2]);
                //graph2[one][two] = 1 - prob;
                graph2[two][one] = 1 - prob;
                
             }
            }
            
        }
        
        int dist[] = new int[operations.size()+1];
        double distProb[] = new double[operations.size()+1];
        ShortestPath tp = new ShortestPath();
        ShortestPathProb tpp = new ShortestPathProb();
        dist = tp.dijkstra(graph, 1);
        distProb = tpp.dijkstra(graph2, 1);
        
        int n = input.size();
        double[][] cost = new double[n][2];
        
        Iterator t = input.entrySet().iterator();
        int i=0;
        while(t.hasNext())
        {
          HashMap.Entry pair = (HashMap.Entry) t.next();  // takes from the dist array, which contains the distance from root for all operations, the distances
          String[] temp15= (String[]) pair.getValue(); // of the available for execution operations and saves them in the cost array
          int tempkey = (Integer) pair.getKey();
          cost[i][0] = tempkey;
          int limit = temp15.length-3;
          if(limit==1)
          {
           char index = temp15[0].charAt(0);
           int ind = index - 'A' +1;
           int denominator = dist[ind];
           double numerator = distProb[ind];
           cost[i][1] = numerator/denominator;
              
          }
          else
          {
           char index = temp15[limit-1].charAt(0);
           int ind = index - 'A' +1;
           int denominator = dist[ind];
           double numerator = distProb[ind];
           cost[i][1] = numerator/denominator;   
          }
          
          
          i++;
        }
        int key = (int) getMaxValue(cost); // the rule with the lowest value(distance from root) is selected for execution
        //System.out.println(key);
        /*
        String[] temp16 = input.get(key);
        double prob = Double.parseDouble(temp16[temp16.length-1]);
        input.remove(key);
        int result = productions.get(key);
        if(result ==1) // the execution was successful and therefore the element is produced and added to the set of available elements
        {
            available.add(temp16[temp16.length-4]);
            System.out.println("Op" + key+ "," + temp16[temp16.length-4] + " , instance: " + inst + " start_time: " + current_time + " , end time:" + (current_time + Integer.parseInt(temp16[temp16.length-3])));
            path.put(key, 0);
            allocation.add(temp16[0]);
            end.add(current_time + Integer.parseInt(temp16[temp16.length-3]));
        }
        else //execution was unsuccessful and therefore the element was not produced
        {
            System.out.println("Op" + key+ "," + temp16[temp16.length-4] + " fail , instance: " + " , instance: " + inst + " start_time: " + current_time + " , end time:" + (current_time + Integer.parseInt(temp16[temp16.length-3])));
            path.put(key, 1);
            allocation.add(temp16[0]);
            end.add(current_time + Integer.parseInt(temp16[temp16.length-3]));
            //pruneOperations(operations,key,prune);
        } 
        */
        return key;
        
       
   }
   
   private static int knockoutPathTimeBaseline(HashMap<Integer,String[]> input, HashSet<String> available, HashMap<Integer,String[]> operations)
   {
        int graph[][] = new int[9][9];
        double graph2[][] = new double [9][9];
        for(int l=0;l<9;l++)
        {
            for(int p=0;p<9;p++)
            {
                graph2[l][p]=-1;
                graph[l][p]=-1;
            }
        }
        Iterator it = operations.entrySet().iterator();
        while(it.hasNext())
        {
            HashMap.Entry pair = (HashMap.Entry) it.next(); 
            String[] dokimi = (String[]) pair.getValue();
            int limit = dokimi.length-3;
            String[] te = new String[limit];
            
            if(limit==1)
            {
                int one = 0;
                int two = dokimi[0].charAt(0) - 'A' + 1;
                graph[one][two] = Integer.parseInt(dokimi[limit]);
                double prob = Double.parseDouble(dokimi[limit+2]);
                graph2[one][two] = 1 - prob;
            }
            else
            {
             for(int y=0;y<limit-1;y++)
             {
                int one = dokimi[y].charAt(0) - 'A' + 1;
                int two = dokimi[limit-1].charAt(0) - 'A' +1;
                //graph[one][two]=Integer.parseInt(dokimi[limit]);
                graph[two][one]=Integer.parseInt(dokimi[limit]);
                double prob = Double.parseDouble(dokimi[limit+2]);
                //graph2[one][two] = 1 - prob;
                graph2[two][one] = 1 - prob;
                
             }
            }
            
        }
        
        int dist[] = new int[operations.size()+1];
        double distProb[] = new double[operations.size()+1];
        ShortestPath tp = new ShortestPath();
        ShortestPathProb tpp = new ShortestPathProb();
        dist = tp.dijkstra(graph, 1);
        distProb = tpp.dijkstra(graph2, 1);
        
        int n = input.size();
        double[][] cost = new double[n][2];
        
        Iterator t = input.entrySet().iterator();
        int i=0;
        while(t.hasNext())
        {
          HashMap.Entry pair = (HashMap.Entry) t.next();  // takes from the dist array, which contains the distance from root for all operations, the distances
          String[] temp15= (String[]) pair.getValue(); // of the available for execution operations and saves them in the cost array
          int tempkey = (Integer) pair.getKey();
          cost[i][0] = tempkey;
          int limit = temp15.length-3;
          if(limit==1)
          {
           char index = temp15[0].charAt(0);
           int ind = index - 'A' +1;
           int denominator = dist[ind];
           double numerator = distProb[ind];
           cost[i][1] = numerator/denominator;
              
          }
          else
          {
           char index = temp15[limit-1].charAt(0);
           int ind = index - 'A' +1;
           int denominator = dist[ind];
           double numerator = distProb[ind];
           cost[i][1] = numerator/denominator;   
          }
          
          
          i++;
        }
        int key = (int) getMaxValue(cost); // the rule with the lowest value(distance from root) is selected for execution
        //System.out.println(key);
        /*
        String[] temp16 = input.get(key);
        double prob = Double.parseDouble(temp16[temp16.length-1]);
        input.remove(key);
        int result = productions.get(key);
        if(result ==1) // the execution was successful and therefore the element is produced and added to the set of available elements
        {
            available.add(temp16[temp16.length-4]);
            System.out.println("Op" + key+ "," + temp16[temp16.length-4] + " , instance: " + inst + " start_time: " + current_time + " , end time:" + (current_time + Integer.parseInt(temp16[temp16.length-3])));
            path.put(key, 0);
            allocation.add(temp16[0]);
            end.add(current_time + Integer.parseInt(temp16[temp16.length-3]));
        }
        else //execution was unsuccessful and therefore the element was not produced
        {
            System.out.println("Op" + key+ "," + temp16[temp16.length-4] + " fail , instance: " + " , instance: " + inst + " start_time: " + current_time + " , end time:" + (current_time + Integer.parseInt(temp16[temp16.length-3])));
            path.put(key, 1);
            allocation.add(temp16[0]);
            end.add(current_time + Integer.parseInt(temp16[temp16.length-3]));
            //pruneOperations(operations,key,prune);
        } 
        */
        return key;
        
       
   }
   
   private static int knockoutPathComb(HashMap<Integer,String[]> input, HashSet<String> available, HashMap<Integer,String[]> operations)
   {
        int graph[][] = new int[9][9];
        double graph2[][] = new double [9][9];
        for(int l=0;l<9;l++)
        {
            for(int p=0;p<9;p++)
            {
                graph2[l][p]=-1;
                graph[l][p]=-1;
            }
        }
        Iterator it = operations.entrySet().iterator();
        while(it.hasNext())
        {
            HashMap.Entry pair = (HashMap.Entry) it.next(); 
            String[] dokimi = (String[]) pair.getValue();
            int limit = dokimi.length-3;
            String[] te = new String[limit];
            
            if(limit==1)
            {
                int one = 0;
                int two = dokimi[1].charAt(0) - 'A' + 1;
                graph[one][two] = Integer.parseInt(dokimi[limit+1]) + Integer.parseInt(dokimi[limit]);
                double prob = Double.parseDouble(dokimi[limit+2]);
                graph2[one][two] = 1 - prob;
            }
            else
            {
             for(int y=1;y<limit-1;y++)
             {
                int one = dokimi[y].charAt(0) - 'A' + 1;
                int two = dokimi[limit-1].charAt(0) - 'A' +1;
                //graph[one][two]=Integer.parseInt(dokimi[limit]);
                graph[two][one]=Integer.parseInt(dokimi[limit+1]) + Integer.parseInt(dokimi[limit]);
                double prob = Double.parseDouble(dokimi[limit+2]);
                //graph2[one][two] = 1 - prob;
                graph2[two][one] = 1 - prob;
                
             }
            }
            
        }
        
        int dist[] = new int[operations.size()+1];
        double distProb[] = new double[operations.size()+1];
        ShortestPath tp = new ShortestPath();
        ShortestPathProb tpp = new ShortestPathProb();
        dist = tp.dijkstra(graph, 1);
        distProb = tpp.dijkstra(graph2, 1);
        
        int n = input.size();
        double[][] cost = new double[n][2];
        
        Iterator t = input.entrySet().iterator();
        int i=0;
        while(t.hasNext())
        {
          HashMap.Entry pair = (HashMap.Entry) t.next();  // takes from the dist array, which contains the distance from root for all operations, the distances
          String[] temp15= (String[]) pair.getValue(); // of the available for execution operations and saves them in the cost array
          int tempkey = (Integer) pair.getKey();
          cost[i][0] = tempkey;
          int limit = temp15.length-3;
          if(limit==1)
          {
           char index = temp15[0].charAt(0);//ITAN 0
           int ind = index - 'A' +1;
           int denominator = dist[ind];
           double numerator = distProb[ind];
           cost[i][1] = numerator/denominator;
              
          }
          else
          {
           char index = temp15[limit-1].charAt(0);
           int ind = index - 'A' +1;
           int denominator = dist[ind];
           double numerator = distProb[ind];
           cost[i][1] = numerator/denominator;   
          }
          
          
          i++;
        }
        int key = (int) getMaxValue(cost); // the rule with the lowest value(distance from root) is selected for execution
        //System.out.println(key);
        /*
        String[] temp16 = input.get(key);
        double prob = Double.parseDouble(temp16[temp16.length-1]);
        input.remove(key);
        int result = productions.get(key);
        if(result ==1) // the execution was successful and therefore the element is produced and added to the set of available elements
        {
            available.add(temp16[temp16.length-4]);
            System.out.println("Op" + key+ "," + temp16[temp16.length-4] + " , instance: " + inst + " start_time: " + current_time + " , end time:" + (current_time + Integer.parseInt(temp16[temp16.length-3])));
            path.put(key, 0);
            allocation.add(temp16[0]);
            end.add(current_time + Integer.parseInt(temp16[temp16.length-3]));
        }
        else //execution was unsuccessful and therefore the element was not produced
        {
            System.out.println("Op" + key+ "," + temp16[temp16.length-4] + " fail , instance: " + " , instance: " + inst + " start_time: " + current_time + " , end time:" + (current_time + Integer.parseInt(temp16[temp16.length-3])));
            path.put(key, 1);
            allocation.add(temp16[0]);
            end.add(current_time + Integer.parseInt(temp16[temp16.length-3]));
        }
        */
        return key;
        
       
   }
   
   private static int knockoutPathCombBaseline(HashMap<Integer,String[]> input, HashSet<String> available, HashMap<Integer,String[]> operations)
   {
        int graph[][] = new int[9][9];
        double graph2[][] = new double [9][9];
        for(int l=0;l<9;l++)
        {
            for(int p=0;p<9;p++)
            {
                graph2[l][p]=-1;
                graph[l][p]=-1;
            }
        }
        Iterator it = operations.entrySet().iterator();
        while(it.hasNext())
        {
            HashMap.Entry pair = (HashMap.Entry) it.next(); 
            String[] dokimi = (String[]) pair.getValue();
            int limit = dokimi.length-3;
            String[] te = new String[limit];
            
            if(limit==1)
            {
                int one = 0;
                int two = dokimi[0].charAt(0) - 'A' + 1;
                graph[one][two] = Integer.parseInt(dokimi[limit+1]) + Integer.parseInt(dokimi[limit]);
                double prob = Double.parseDouble(dokimi[limit+2]);
                graph2[one][two] = 1 - prob;
            }
            else
            {
             for(int y=0;y<limit-1;y++)
             {
                int one = dokimi[y].charAt(0) - 'A' + 1;
                int two = dokimi[limit-1].charAt(0) - 'A' +1;
                //graph[one][two]=Integer.parseInt(dokimi[limit]);
                graph[two][one]=Integer.parseInt(dokimi[limit+1]) + Integer.parseInt(dokimi[limit]);
                double prob = Double.parseDouble(dokimi[limit+2]);
                //graph2[one][two] = 1 - prob;
                graph2[two][one] = 1 - prob;
                
             }
            }
            
        }
        
        int dist[] = new int[operations.size()+1];
        double distProb[] = new double[operations.size()+1];
        ShortestPath tp = new ShortestPath();
        ShortestPathProb tpp = new ShortestPathProb();
        dist = tp.dijkstra(graph, 1);
        distProb = tpp.dijkstra(graph2, 1);
        
        int n = input.size();
        double[][] cost = new double[n][2];
        
        Iterator t = input.entrySet().iterator();
        int i=0;
        while(t.hasNext())
        {
          HashMap.Entry pair = (HashMap.Entry) t.next();  // takes from the dist array, which contains the distance from root for all operations, the distances
          String[] temp15= (String[]) pair.getValue(); // of the available for execution operations and saves them in the cost array
          int tempkey = (Integer) pair.getKey();
          cost[i][0] = tempkey;
          int limit = temp15.length-3;
          if(limit==1)
          {
           char index = temp15[0].charAt(0);//ITAN 0
           int ind = index - 'A' +1;
           int denominator = dist[ind];
           double numerator = distProb[ind];
           cost[i][1] = numerator/denominator;
              
          }
          else
          {
           char index = temp15[limit-1].charAt(0);
           int ind = index - 'A' +1;
           int denominator = dist[ind];
           double numerator = distProb[ind];
           cost[i][1] = numerator/denominator;   
          }
          
          
          i++;
        }
        int key = (int) getMaxValue(cost); // the rule with the lowest value(distance from root) is selected for execution
        //System.out.println(key);
        /*
        String[] temp16 = input.get(key);
        double prob = Double.parseDouble(temp16[temp16.length-1]);
        input.remove(key);
        int result = productions.get(key);
        if(result ==1) // the execution was successful and therefore the element is produced and added to the set of available elements
        {
            available.add(temp16[temp16.length-4]);
            System.out.println("Op" + key+ "," + temp16[temp16.length-4] + " , instance: " + inst + " start_time: " + current_time + " , end time:" + (current_time + Integer.parseInt(temp16[temp16.length-3])));
            path.put(key, 0);
            allocation.add(temp16[0]);
            end.add(current_time + Integer.parseInt(temp16[temp16.length-3]));
        }
        else //execution was unsuccessful and therefore the element was not produced
        {
            System.out.println("Op" + key+ "," + temp16[temp16.length-4] + " fail , instance: " + " , instance: " + inst + " start_time: " + current_time + " , end time:" + (current_time + Integer.parseInt(temp16[temp16.length-3])));
            path.put(key, 1);
            allocation.add(temp16[0]);
            end.add(current_time + Integer.parseInt(temp16[temp16.length-3]));
        }
        */
        return key;
        
       
   }
   
   public static void printD(double mat[]) 
    { 
        // Loop through all rows 
        for (int i = 0; i < mat.length; i++) 
        {
            // Loop through all elements of current row 
            System.out.println(mat[i]);
        }
        System.exit(0);
    }
   
   private static int determineExecutionInstance(HashMap<Integer,String[]> operations, HashMap<Integer,Integer> productions)
   {
       Iterator it = operations.entrySet().iterator();
       while(it.hasNext())
       {
           HashMap.Entry temp = (HashMap.Entry) it.next();
           int key = (Integer) temp.getKey();
           String[] value = (String[]) temp.getValue();
           double prob = Double.parseDouble(value[value.length-1]);
           int result = execution(prob);
           productions.put(key, result);
       }
             
       return 1;
   }
   
   private static void pruneOperations(HashMap<Integer,String[]> operations, int key, HashSet<Integer> prune)
   {
        String[] entry = (String []) operations.get(key);
        String output = entry[entry.length-4];
        
        
        Iterator it = operations.entrySet().iterator();
        while(it.hasNext())
        {
            HashMap.Entry entry2 = (HashMap.Entry) it.next();
            String[] compare = (String[]) entry2.getValue();
            for(int i=0;i<compare.length-4;i++)
            {
                if(compare[i].equals(output))
                {
                    prune.add((Integer)entry2.getKey());
                }
            }
        }
        
        
       
   }
   
   private static void prune(HashMap<Integer,String[]> operations, int key, HashSet<Integer> prune)
    {
        Iterator it = operations.entrySet().iterator();
        String[] operation = (String[])operations.get(key);
        String output = operation[operation.length-4];
        HashSet<String> candidates = new HashSet();
        HashSet<String> prune2 = new HashSet();
        while(it.hasNext())
        {
            HashMap.Entry entry = (HashMap.Entry) it.next();
            String[] compare = (String[]) entry.getValue();
            for(int i=0;i<compare.length-4;i++)
            {
                if(compare[i].equals(output))
                {
                 for(int j=0;j<compare.length-4;j++)
                 {
                     
                     if(j!=i)
                     {
                         
                         candidates.add(compare[j]);
                     }
                 }
                }
            }
        }
        
        int sum_cand=0;
        int sum_out=0;
        for(String cand : candidates)
        {
          
          sum_cand=0;
          sum_out=0;
          it = operations.entrySet().iterator();
          while(it.hasNext())
          {
           HashMap.Entry entry = (HashMap.Entry) it.next();
           
           String[] compare = (String[]) entry.getValue();
           for(int i=0;i<compare.length-4;i++)
           {
            if(compare[i].equals(cand))
            {
                sum_cand+=1;
                
             for(int j=0;j<compare.length-4;j++)
             {
                 
              if(compare[j].equals(output))
              {
                  
                  sum_out+=1;
              }   
             }   
            }   
           }
          }
          
          if(sum_cand==sum_out)
          {
              prune2.add(cand);
          }
        }
        
        for(String c : prune2)
        {
             it=operations.entrySet().iterator();
             while(it.hasNext())
             {
               HashMap.Entry entry = (HashMap.Entry) it.next();
               String compare[] = (String[]) entry.getValue();
               if(compare[compare.length-4].equals(c))
               {
                   
                   prune.add((Integer)entry.getKey());
               }
             }
            
        }
        
       
    }
   
   private static void prune2(HashMap<Integer,String[]> operations, int key, int[][] dist, int[] dist2)
    {
        Iterator it = operations.entrySet().iterator();
        String[] operation = (String[])operations.get(key);
        HashSet<Integer> prune = new HashSet();
        String output = getCharForNumber(key);
        
        HashSet<String> candidates = new HashSet();
        HashSet<String> prune2 = new HashSet();
        while(it.hasNext())
        {
            HashMap.Entry entry = (HashMap.Entry) it.next();
            String[] compare = (String[]) entry.getValue();
            for(int i=0;i<compare.length-4;i++)
            {
                if(compare[i].equals(output))
                {
                 for(int j=0;j<compare.length-4;j++)
                 {
                     
                     if(j!=i)
                     {
                         
                         candidates.add(compare[j]);
                     }
                 }
                }
            }
        }
        
        int sum_cand=0;
        int sum_out=0;
        for(String cand : candidates)
        {
          
          sum_cand=0;
          sum_out=0;
          it = operations.entrySet().iterator();
          while(it.hasNext())
          {
           HashMap.Entry entry = (HashMap.Entry) it.next();
           
           String[] compare = (String[]) entry.getValue();
           for(int i=0;i<compare.length-4;i++)
           {
            if(compare[i].equals(cand))
            {
                sum_cand+=1;
                
             for(int j=0;j<compare.length-4;j++)
             {
                 
              if(compare[j].equals(output))
              {
                  
                  sum_out+=1;
              }   
             }   
            }   
           }
          }
          
          if(sum_cand==sum_out)
          {
              
              int two = cand.charAt(0) - 'A' + 1;
             ;
              if(dist[0][key]!=-1)
              {
              dist2[two]+=dist[0][key];
              }
              else
              {
              dist2[two]+=findOperationCost(operations,key);
              }
              
          }
        }
        
        
        
       
    }
   
   private static String getCharForNumber(int i) {
    return i > 0 && i < 27 ? String.valueOf((char)(i + 64)) : null;
}
   
private static int findOperationCost(HashMap<Integer,String[]> operations, int key)
   {
       int result=11;
       String output;
        if(key<10)
        {
           output = "i0".concat(String.valueOf(key));
        }
        else
        {
            output = "i".concat(String.valueOf(key));
        }
       Iterator it = operations.entrySet().iterator();
       while(it.hasNext())
       {
           HashMap.Entry entry = (HashMap.Entry) it.next();
           String[] operation = (String[]) entry.getValue();
           if(operation[operation.length-4].equals(output))
           {
               if(Integer.parseInt(operation[operation.length-2])<result)
               {
                   result = Integer.parseInt(operation[operation.length-2]);
               }
           }
           
       }
       if(result==11)
       {
           
       }
       return result;
   }






/*private static double findStartingTime2(HashMap<Integer,String[]> operations, int key, HashMap<Integer,ArrayList<Double>> listOfEnd, HashMap<Integer, ArrayList<String>> listOfAllocations, Integer[] instances, Double current_time, HashMap<String,Integer> resources, HashMap<String,Double> times, Double starting_time)
{
        String[] operation = operations.get(key);
        String resource = operation[0];
        
        HashMap<String,Integer> resources_clone = (HashMap<String,Integer>) resources.clone();
        int resource_capacity = resources.get(resource);
        double max_end;
        int available=resource_capacity;
        if(resource_capacity==1)
        {
            max_end=0;
        }
        else
        {
            max_end=Integer.MAX_VALUE;
        }
        for(Integer j=0;j<instances.length;j++)
        {
            int inst = instances[j];
            //System.out.println("inst " + inst);
        ArrayList<Double> end = listOfEnd.get(inst);
        ArrayList<String> allocation = listOfAllocations.get(inst);
        
        //int index =0;
        if(resource_capacity==1)
        {
        if(!allocation.isEmpty())
        {
        for(int i=0;i<allocation.size();i++)
        {
           String current_resource = allocation.get(i);
           if(current_resource.equals(resource))
           {
               Double max_cand = end.get(i);
               available = resources_clone.get(resource);
               available = available -1;
               resources_clone.put(resource,available);
               if(max_cand > max_end)
               {
                   max_end = max_cand;
               }
               //max_end = end.get(i);
              
           }
        }
        }
        }
        else
        {
          if(!allocation.isEmpty())
          {
          for(int i=0;i<allocation.size();i++)
           {
            
            String current_resource = allocation.get(i);
            if(current_resource.equals(resource))
            {
               Double max_cand = end.get(i);
               available = resources_clone.get(resource);
               available = available -1;
               resources_clone.put(resource,available);
               if(max_cand < max_end)
               {
                   max_end = max_cand;
               }
               //max_end = end.get(i);
               
            }
           }  
          }
        }
        
        
        
   }
        if(available>0)
        {
            max_end =0;
        }
        
        ArrayList<String> input_elements = new ArrayList<>();
        for(int j=1;j<(operation.length-4);j++)
        {
            input_elements.add(operation[j]);
        }
        
        if(input_elements.size()!=0)
        {
        for(int j=0;j<input_elements.size();j++)
        {
            Double time_cand = times.get(input_elements.get(j));
            if(time_cand>max_end)
            {
                max_end = time_cand;
            }
        }
        }
        
        if(max_end==0)
        {
            max_end = starting_time;
        }
        return max_end;
}*/


private static double findStartingTime(HashMap<Integer,String[]> operations, int key, HashMap<Integer,ArrayList<Double>> listOfEnd, HashMap<Integer, ArrayList<String>> listOfAllocations, Integer[] instances, Double current_time, HashMap<String,Integer> resources, HashMap<String,Double> times, Double starting_time,double[][] indexes, int instance)
{
        String[] operation = operations.get(key);
        String resource = operation[0];
        Integer resource_mentions =0;
        int resource_index = 0;
        
        HashMap<String,Integer> resources_clone = (HashMap<String,Integer>) resources.clone();
        int resource_capacity = resources.get(resource);
        Iterator it = resources.entrySet().iterator();
        int q=0;
        while(it.hasNext())
        {
           Map.Entry<String,Integer> resource_pair = (Map.Entry<String,Integer>) it.next();
           if(resource_pair.getKey().equals(resource))
           {
               resource_index = q;
           }
           q++;
        }
        //System.out.println(resource + ", " + resource_index);
        double max_end;
        int save_index =0;
        int available=resource_capacity;
        if(resource_capacity==1)
        {
            max_end=0;
        }
        else
        {
            max_end=Double.MAX_VALUE;
        }
        for(Integer j=0;j<instances.length;j++)
        {
            int inst = instances[j];
            //System.out.println("inst " + inst);
        ArrayList<Double> end = listOfEnd.get(inst);
        ArrayList<String> allocation = listOfAllocations.get(inst);
        
        //int index =0;
        if(resource_capacity==1)
        {
        if(!allocation.isEmpty())
        {
        for(int i=0;i<allocation.size();i++)
        {
           String current_resource = allocation.get(i);
           if(current_resource.equals(resource))
           {
               resource_mentions+=1;
               Double max_cand = end.get(i);
               available = resources_clone.get(resource);
               
               if(max_cand > indexes[instance][resource_index])
               {
                  
                   available = available -1;
                   resources_clone.put(resource,available); //edo
                   
                   if(max_cand > max_end)
                   {
                       max_end = max_cand;
                   }
               }
               //max_end = end.get(i);
               
           }
        }
        }
        }
        else
        {
          if(!allocation.isEmpty())
          {
          for(int i=0;i<allocation.size();i++)
           {
            
            String current_resource = allocation.get(i);
            if(current_resource.equals(resource))
            {
               resource_mentions+=1;
               Double max_cand = end.get(i);
               available = resources_clone.get(resource);
               //available = available -1;
               
               resources_clone.put(resource,available);
               if(max_cand > indexes[instance][resource_index])
               {
                   
                available = available -1;
                
                resources_clone.put(resource,available);
                 if(max_cand < max_end)
                   {
                       max_end = max_cand;
                       save_index = i;
                   }
                     
                   
               }
              
              
            }
           }  
          }
        }
        
        
        
   }
        if(available>0)
        {
            max_end = starting_time;
        }
        
        if(available>0 && resource_mentions<resource_capacity)
        {
            max_end = 0;
        }
        

        
        if(available <=0)
        {
          
        }
        
        
        ArrayList<String> input_elements = new ArrayList<>();
        for(int j=1;j<(operation.length-4);j++)
        {
            input_elements.add(operation[j]);
        }
        
        if(input_elements.size()!=0)
        {
        for(int j=0;j<input_elements.size();j++)
        {
            Double time_cand = times.get(input_elements.get(j));
            if(time_cand>max_end)
            {
                max_end = time_cand;
            }
        }
        }
        double second_max = Double.MAX_VALUE;
        
        if( available <0)
        {
             
          for(int j=0;j<instances.length;j++)
          {
               int inst = instances[j];
            
               ArrayList<Double> end = listOfEnd.get(inst);
               ArrayList<String> allocation = listOfAllocations.get(inst);
               if(!allocation.isEmpty())
              {
               for(int i=0;i<allocation.size();i++)
               {
                String current_resource = allocation.get(i);
                if(current_resource.equals(resource))
                {
                  Double max_cand = end.get(i);
                  //available = resources_clone.get(resource);
                  //available = available -1;
                  //resources_clone.put(resource,available);
                  if(max_cand > max_end && max_cand < second_max)
                  {
                    max_end = max_cand;
                    second_max = max_end;
                  }
                  //max_end = end.get(i);
                 
                }
              }
             }
          }
        }else
        {
            
        }
        
       // System.out.println("available: " + available + " max_end: " + max_end);
        indexes[instance][resource_index] = max_end;
        
        
        return max_end;
}




private static double findWaitingTime(HashMap<Integer,String[]> operations, int key, HashMap<Integer,ArrayList<Double>> listOfEnd, HashMap<Integer, ArrayList<String>> listOfAllocations, Integer inst, Double current_time, HashMap<String,Integer> resources)
{
        String[] operation = operations.get(key);
        String resource = operation[0];
        Integer other_instance;
        if(inst==0)
        {
            other_instance=1;
        }
        else
        {
            other_instance=0;
        }
        
        ArrayList<Double> end = listOfEnd.get(other_instance);
        ArrayList<String> allocation = listOfAllocations.get(other_instance);
        double max_end=0;
        //int index =0;
        for(int i=0;i<allocation.size();i++)
        {
           String current_resource = allocation.get(i);
           if(current_resource.equals(resource))
           {
               max_end = end.get(i);
           }
        }
        
            //System.out.println(inst + ",,,,," + current_time + ",,,,,," + max_end);
        
       return (max_end-current_time);
        
        
        
            
}

private static double getDuration(ArrayList<Double> end)
{
    double max_end =0;
    
    Iterator it_end = end.iterator();
    
    
    while(it_end.hasNext())
    {
        double cand = (double) it_end.next();
        if(cand>max_end)
        {
            max_end = cand;
        }
    }
    
    return max_end;
    
}


private static int getMinValueIndex(double[] matrix)
{
    double min = Double.MAX_VALUE;
    int index =0;
    for(int i=0;i<matrix.length;i++)
    {
        if(matrix[i]<min && matrix[i]!=0)
        {
            min = matrix[i];
            
            index = i;
        }
    }
    
    return index;
}

private static int getMinValueIndex(int[] matrix)
{
    int min = Integer.MAX_VALUE;
    int index =0;
    for(int i=0;i<matrix.length;i++)
    {
        if(matrix[i]<min && matrix[i]!=0)
        {
            min = matrix[i];
            
            index = i;
        }
    }
    
    return index;
}



private static HashMap<String,Integer> findNumberAvailableResources(HashMap<Integer,String[]> executable ,HashMap<String,Integer> resources, HashMap<Integer, ArrayList<String>> listOfAllocations, HashMap<Integer,ArrayList<Double>> listOfStarts, Double starting_time)
{
   HashMap<String,Integer> resources_local = (HashMap<String,Integer>) resources.clone();
   HashMap<String,Integer> resources_mention = (HashMap<String,Integer>) resources.clone();
   
   Iterator mention_it = resources_mention.entrySet().iterator();
   while(mention_it.hasNext())
   {
       Map.Entry<String,Integer> resource_mention = (Map.Entry<String,Integer>) mention_it.next();
       resources_mention.replace(resource_mention.getKey(), 0);
       
   }
   boolean start = true;
   for(int i=0;i<listOfStarts.size();i++)
   {
       ArrayList<String> temp_allocation = listOfAllocations.get(i);
       if(!temp_allocation.isEmpty())
       {
        start = false;   
       }
   }
   if(start)
   {
       
       Iterator executable_it = executable.entrySet().iterator();
       while(executable_it.hasNext())
       {
         Map.Entry<Integer,String[]> operation_it = (Map.Entry<Integer,String[]>) executable_it.next();
         int resource_mention = resources_mention.get(operation_it.getValue()[0]);
         resource_mention+=1;
         resources_mention.put(operation_it.getValue()[0], resource_mention);
       }
       
       Iterator resources_local_it = resources_local.entrySet().iterator();
       while(resources_local_it.hasNext())
       {
         Map.Entry<String,Integer> next_entry = (Map.Entry<String,Integer>) resources_local_it.next();
        
         
           if(resources_mention.get(next_entry.getKey())==0)
           {
               resources_local.put(next_entry.getKey(),0);
               //count++;
           }
         
       //System.out.println(next_entry.getKey()  +  "  ,  " +  next_entry.getValue());
     }
         return resources_local;
     }
    
   for(int i=0;i<listOfStarts.size();i++)
   {
       ArrayList<Double> temp_start = listOfStarts.get(i);
       ArrayList<String> temp_allocation = listOfAllocations.get(i);
       
       for(int j=0;j<temp_allocation.size();j++)
       {
           
           double start_candidate = temp_start.get(j);
           if(start_candidate>starting_time)
           {
               String resource_candidate = temp_allocation.get(j);
               //System.out.println(resource_candidate);
               int candidate_availability = resources_local.get(resource_candidate);
               candidate_availability -= 1;
               resources_local.put(resource_candidate,candidate_availability);
               int candidate_mention = resources_mention.get(resource_candidate);
               candidate_mention += 1;
               resources_mention.put(resource_candidate,candidate_mention);
           }
       }
   }
   
   Iterator executable_it = executable.entrySet().iterator();
   while(executable_it.hasNext())
   {
       Map.Entry<Integer,String[]> operation_it = (Map.Entry<Integer,String[]>) executable_it.next();
       int resource_mention = resources_mention.get(operation_it.getValue()[0]);
       resource_mention+=1;
       resources_mention.put(operation_it.getValue()[0], resource_mention);
   }
   
   int count=0; // nubmer of resources with availability == zero
   int count2=0; // number of operations that feature this resource as their required resource
   Iterator resources_local_it = resources_local.entrySet().iterator();
   while(resources_local_it.hasNext())
   {
       Map.Entry<String,Integer> next_entry = (Map.Entry<String,Integer>) resources_local_it.next();
       int availability = next_entry.getValue();
       if(availability == 0)
       {
           count++;
       }
       else
       {
           if(resources_mention.get(next_entry.getKey())==0)
           {
               resources_local.put(next_entry.getKey(),0);
               count++;
           }
       }
      
   }
   
   return resources_local;
}

private static double findEarliestStartingTime(HashMap<String,Integer> resources, HashMap<Integer, ArrayList<String>> listOfAllocations, HashMap<Integer,ArrayList<Double>> listOfEnd, Double starting_time)
{
    HashMap<String,Integer> resources_local = (HashMap<String,Integer>) resources.clone();
    double min = Double.MAX_VALUE;
   for(int i=0;i<listOfEnd.size();i++)
   {
       ArrayList<Double> temp_end = listOfEnd.get(i);
       ArrayList<String> temp_allocation = listOfAllocations.get(i);
       
       for(int j=0;j<temp_end.size();j++)
       {
           double start_candidate = temp_end.get(j);
           if(start_candidate!=starting_time  && start_candidate>starting_time && start_candidate<min)
           {
               min = start_candidate;
               
           }
       }
   }
   
   return min;
}
}