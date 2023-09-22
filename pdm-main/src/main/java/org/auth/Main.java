package org.auth;

import org.auth.heuristic.*;
import org.auth.heuristic.Random;
import org.auth.model.DataElement;
import org.auth.model.Operation;
import org.auth.model.PDM;
import org.auth.util.Utilities;
import org.auth.xml.PDMParser;
import picocli.CommandLine;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import org.auth.scheduler.*;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.*;
import org.auth.model.Resource;
import static org.auth.scheduler.OGScheduler.OGScheduler;
import static org.auth.scheduler.ParallelResourceAwareScheduler.ParallelResourceAwareScheduler;
import static org.auth.scheduler.ParallelResourceAwareSchedulerWaiting.ParallelResourceAwareSchedulerWaiting;
import static org.auth.scheduler.ResourceScheduler.ResourceScheduler;
import static org.auth.scheduler.ResourceSchedulerMeaningless.ResourceSchedulerMeaningless;
import static org.auth.scheduler.ResourceSchedulerMeaninglessWaiting.ResourceSchedulerMeaninglessWaiting;
import static org.auth.scheduler.ResourceSchedulerWaiting.ResourceSchedulerWaiting;
import org.jgrapht.alg.util.Pair;


@CommandLine.Command(name = "pdm_heuristics", mixinStandardHelpOptions = true, version = "PDM Heuristics v0.1")
public class Main implements Runnable {

    @Parameters(arity = "1", paramLabel = "PDM File", description = "File to process.")
    private File inputFile;

    @Option(names = {"-r", "--runs"}, description = "The number of runs (default: 100000)")
    private Integer runs = 10000;
    private Integer instances = 2;

    public void run() {
        try {

            PDMParser parser = new PDMParser();
            String filename =   "pdm_log.txt";
            PrintWriter pw = new PrintWriter(new FileWriter(filename));
            // parse input pdm xml file
            PDM inputPDM = parser.parse(inputFile.getAbsolutePath());

            // print the PDM's summary
            System.out.println(inputPDM.summary());

            List<String> heuristicNames = Arrays.asList(
                    "Random",
                    "LowestCost",
                    "ShortestTime",
                    "LowestFail",
                    "RootDistance",
                    "CostDistance",
                    "TimeDistance",
                    "Rank-Cost",
                    "Rank-Time",
                    "Rank-Combo",
                    "Rank-Extended-Cost",
                    "Rank-Extended-Time",
                    "Rank-Extended-Combo"
//                    new KnockoutPathTimePruneBothTime(),
//                    new KnockoutPathTimePruneBothCombo()
            );

            int numOfHeuristics = 13;
            int numOfSchedulers = 2;
            
            
            double maximum = 0;
            double[][] sum_cost = new double[numOfSchedulers][numOfHeuristics];
            
            
            double[][] sum_time = new double[numOfSchedulers][numOfHeuristics];
            double[][] num_of_operations = new double[numOfSchedulers][numOfHeuristics];
            double[][] num_of_operations_obj = new double[numOfSchedulers][numOfHeuristics];
            double []timesa = new double [runs];
            double [] costa = new double[runs];
            double [] costb = new double[runs];
            double [] timesb = new double[runs];
            int[][] obj_runs = new int[numOfSchedulers][numOfHeuristics];
            int[] invalidate_runs = new int[numOfHeuristics]; 
            int[] waiting_count = new int[numOfHeuristics];
            for (int j = 0; j < runs; j++) { // arxi 62
                HashMap<Integer,List<PDM>> pdmList_instances = new HashMap<>();
               
                List<PDM> pdmList;

                PDM randomPDM = new PDM(inputPDM, true);///THIMAME
                HashMap<Operation, Boolean> prerun = Utilities.prerun(randomPDM.operations());

                List<Heuristic> heuristics = Arrays.asList(
                        new Random(),
                        new LowestCost(),
                        new ShortestTime(),
                        new LowestFail(),
                        new RootDistance(randomPDM),
                        new CostDistance(randomPDM),
                        new TimeDistance(randomPDM),
                        new KnockoutPath(randomPDM),
                        new KnockoutPathTime(randomPDM),
                        new KnockoutPathCombo(randomPDM),
                        new KnockoutPathPrune(randomPDM),
                        new KnockoutPathPruneTime(randomPDM),
                        new KnockoutPathPruneCombo(randomPDM)
                );

                for(int f=0 ; f < numOfSchedulers ; f ++ ) {
                    pdmList = new ArrayList<>();
                for (int i = 0; i < heuristics.size(); i++) {
                    pdmList.add(new PDM(randomPDM, false));
                }
                pdmList_instances.put(f, pdmList);
                }
                
                
                for(int p =1 ; p< numOfSchedulers;p++){
                
                for (int i = 0; i < numOfHeuristics; i++) {
                    num_of_operations[p][i] = 0;
                     System.out.println("dogkanos :" + p + "," + i + "," + numOfHeuristics);// for each heuristic // heuristics.size()
                    //System.out.println("start of new execution");
                    pw.println("<instance>");
                    PDM pdm = pdmList_instances.get(p).get(i);
                    //System.out.println(pdm.getResourceCapacity(0.0));
                    pdm.resetResources();
                    //System.out.println(pdm.getResourceCapacity(0.0));
                    double cost = 0.0;
                    double time = 0.0;
                    double current_time = 0.0;
                    LinkedHashSet<Operation> og_operations = new LinkedHashSet<>();
                    og_operations = ( LinkedHashSet<Operation>) pdm.operations().clone();
                    LinkedHashSet<Operation> executable = new LinkedHashSet<>();
                    LinkedHashSet<String> available_string = new LinkedHashSet<>();
                    LinkedHashSet<DataElement> available = new LinkedHashSet<>();
                    LinkedHashSet<Operation> prune = new LinkedHashSet<>();
                    HashMap<DataElement,Double> executed = new HashMap<>();
                    HashMap<DataElement,Double> started = new HashMap<>();
                    boolean pass = true;
                    while (true) {
                        
                        
                        current_time = pdm.getNextTime(current_time,pass);
                        System.out.println("next current_time: " + current_time);
                        for (Operation op : pdm.operations()) { // for each operation
                            if (!op.hasInput()) { // operation has no input
                                executable.add(op);
                            } else {
                                if (available.containsAll(op.input())) {
                                    executable.add(op);
                                }
                            }
                        }
                        
                        if(i==10 || i==11 || i == 12)
                        {
                            for(Operation op : prune)
                            {
                                executable.remove(op);
                                //System.out.println("whatever");
                            }
                        }
                         System.out.println(pdm.operations().size() + " wtf");
                        if (executable.isEmpty() ) {
                            System.out.println("execution finished" + " scheduler " + p );
                            System.out.println("cost: " + cost);
                            System.out.println("time: " + time);
                            System.out.println();
                            //sum_cost[i] += cost;
                            //sum_time[i] += time;
                            //obj_runs += 1;
                            
                            Iterator it = started.entrySet().iterator();
                            while(it.hasNext())
                            {
                                Map.Entry<DataElement,Double> entry = (Map.Entry<DataElement,Double>) it.next();
                                System.out.println(entry.getKey().getName());
                                Operation whatever = null;
                                for(Operation op : og_operations)
                                {
                                    if(op.output().getName().equals(entry.getKey().getName()))
                                    {
                                        whatever = op;
                                    }
                                }
                                
                                if(whatever.hasInput())
                                {
                                List<DataElement> input = whatever.input();
                                for(DataElement d : input)
                               {
                                    if(executed.containsKey(d))
                                    {
                                    if(executed.get(d)> entry.getValue())
                                    {
                                        System.out.println("invalidate " + whatever.getName());
                                        invalidate_runs[i] +=1;
                                    }
                                    }
                                }
                                }
                                
                            }
                            pw.println("</instance>");
                            break;
                        }
                        List<Pair<Operation,Resource>> nextOp_list;
                        if(p==0)
                        {
                             nextOp_list = OGScheduler(pdm,executable,available,heuristics.get(10),prerun,0,current_time);
                            //nextOp_list = ResourceScheduler(pdm,executable,available,heuristics.get(10),prerun,0,current_time,executed);
                            //nextOp_list = ResourceScheduler(pdm,executable,available,heuristics.get(i),prerun,0,current_time,executed);
                        }
                        else if(p==1)
                        {
                             //nextOp_list = OGScheduler(pdm,executable,available,heuristics.get(10),prerun,0,current_time);
                            //nextOp_list = ParallelResourceAwareScheduler(pdm,executable,available,heuristics.get(i),prerun,0,current_time,executed);
                            //nextOp_list = ResourceSchedulerWaiting(pdm,executable,available,heuristics.get(i),prerun,0,current_time,waiting_count[i],executed);
                            nextOp_list = ResourceScheduler(pdm,executable,available,heuristics.get(i),prerun,0,current_time,executed);
                            num_of_operations[p][i] += nextOp_list.size();
                        }
                        else if(p==2)
                        {
                            nextOp_list = ResourceScheduler(pdm,executable,available,heuristics.get(i),prerun,0,current_time,executed);
                        }
                        else
                        {
                            nextOp_list = ResourceSchedulerMeaningless(pdm,executable,available,heuristics.get(i),prerun,0,current_time,executed);
                        }
                        
                        
                        
                      //List<Pair<Operation,Resource>> nextOp_list = ResourceScheduler(pdm,executable,available,heuristics.get(i),prerun,0,current_time,executed);
                       //List<Pair<Operation,Resource>> nextOp_list = OGScheduler(pdm,executable,available,heuristics.get(i),prerun,0,current_time);
                      //List<Pair<Operation,Resource>> nextOp_list = ResourceSchedulerMeaningless(pdm,executable,available,heuristics.get(i),prerun,0,current_time,executed);
                       //List<Pair<Operation,Resource>> nextOp_list = ParallelResourceAwareScheduler(pdm,executable,available,heuristics.get(i),prerun,0,current_time,executed);
                       //List<Pair<Operation,Resource>> nextOp_list = ResourceSchedulerWaiting(pdm,executable,available,heuristics.get(i),prerun,0,current_time,waiting_count[i],executed);
                       //List<Pair<Operation,Resource>> nextOp_list = ParallelResourceAwareSchedulerWaiting(pdm,executable,available,heuristics.get(i),prerun,0,current_time,executed);
                       //List<Pair<Operation,Resource>> nextOp_list = ResourceSchedulerMeaninglessWaiting(pdm,executable,available,heuristics.get(i),prerun,0,current_time,executed);
                        if(nextOp_list.isEmpty() || current_time >0)
                        {
                            pass=false;
                            
                        }
                        
                        for(Pair<Operation,Resource> nextOp_pair : nextOp_list)
                        {
                            Operation nextOp = nextOp_pair.getFirst();
                         if(prerun.get(nextOp))
                                 {
                                     available.add(nextOp.output());
                                 }
                            
                        if(available.contains(nextOp.output()))
                        {
                            System.out.println(heuristicNames.get(i) + ", " + nextOp.getName() + "," + nextOp.getStartingTime() + " - " + (nextOp.getStartingTime() +nextOp.getTime(nextOp_pair.getSecond())) + ", resource:" + nextOp_pair.getSecond().getName() + " cost: " + nextOp.getCost(nextOp_pair.getSecond()));
                            executed.put(nextOp.output(), (nextOp.getStartingTime() +nextOp.getTime(nextOp_pair.getSecond())));
                            started.put(nextOp.output(), nextOp.getStartingTime());
                            pw.println(nextOp.getName() + "," + nextOp.getStartingTime() + " - " + (nextOp.getStartingTime() +nextOp.getTime(nextOp_pair.getSecond())) + ", resource:" + nextOp_pair.getSecond().getName()  + " cost: " + nextOp.getCost(nextOp_pair.getSecond()));
                        }
                        else
                        {
                            prune(pdm.operations(),nextOp,prune,pdm,prerun);
                            executed.put(nextOp.output(), (nextOp.getStartingTime() +nextOp.getTime(nextOp_pair.getSecond())));
                            started.put(nextOp.output(), nextOp.getStartingTime());
                            System.out.println(heuristicNames.get(i) + ", " + nextOp.getName() + "," + nextOp.getStartingTime() + " - " + (nextOp.getStartingTime() +nextOp.getTime(nextOp_pair.getSecond())) + ", resource:" + nextOp_pair.getSecond().getName() + " fail" + " cost: " + nextOp.getCost(nextOp_pair.getSecond()));
                            pw.println(nextOp.getName() + "," + nextOp.getStartingTime() + " - " + (nextOp.getStartingTime() +nextOp.getTime(nextOp_pair.getSecond())) + ", resource:" + nextOp_pair.getSecond().getName() + " cost: " + nextOp.getCost(nextOp_pair.getSecond()));
                        }
                        
                        cost = cost + nextOp.getCost(nextOp_pair.getSecond());// malakia?
                        time =  pdm.getMaxTime();
                        Pair<Operation,Resource> pair = new Pair<>(nextOp,nextOp.getResource());
                        
                        executable.remove(nextOp);
                        
                        
                        
                       
                        
                    }
                        
                        if (available.contains(pdm.target()) ) {
                            System.out.println("execution finished objective + scheduler " + p);
                            num_of_operations_obj[p][i] += num_of_operations[p][i];
                            
                            System.out.println("cost: " + cost);
                            System.out.println("time: " + time);
                            System.out.println();
                            sum_cost[p][i] += cost;
                            sum_time[p][i] += pdm.getMaxTime();
                            obj_runs[p][i] += 1;
                            pw.println("</instance>");
                            Iterator it = started.entrySet().iterator();
                            while(it.hasNext())
                            {
                                Map.Entry<DataElement,Double> entry = (Map.Entry<DataElement,Double>) it.next();
                                 Operation whatever = null;
                                for(Operation op : og_operations)
                                {
                                    if(op.output().getName().equals(entry.getKey().getName()))
                                    {
                                        whatever = op;
                                    }
                                }
                                if(whatever.hasInput())
                                {
                                List<DataElement> input = whatever.input();
                                for(DataElement d : input)
                                {
                                    if(executed.containsKey(d))
                                    {
                                    if(executed.get(d)> entry.getValue())
                                    {
                                        System.out.println("invalidate " +  whatever.getName());
                                        invalidate_runs[i] +=1;
                                    }
                                    }
                                }
                                }
                                
                            }
                            if(p==0)
                            {
                                timesa[j] = time;
                                costa[j] = cost;
                            }
                            else
                            {
                                timesb[j] = time;
                                costb[j] = cost;
                            }
                            break;
                            
                        }
                        
                        


                    }
                }
            }
                
               
            }//telos 62
            pw.close();
            System.out.println(
                    String.format("%-30s", "Heuristic,") +
                            String.format("%-30s", "Cost," ) +
                            String.format("%-30s", "Time,")
            );
            
            for(int p=0; p <numOfSchedulers;p++){
                System.out.println("Scheduler: " + p + " , ");
            for (int i = 0; i < numOfHeuristics; i++) {
                System.out.println(
                        String.format("%-30s", heuristicNames.get(i) + ",") +
                                String.format("%-30s", sum_cost[p][i] / (double) obj_runs[p][i] +",") +
                                String.format("%-30s", sum_time[p][i] / (double) obj_runs[p][i])
                );
                
               
            }
            }
            
            
            for (int i = 0; i < numOfHeuristics; i++) {
                System.out.println(
                        String.format("%-30s", heuristicNames.get(i) + ",") +
                                String.format("%-30s", invalidate_runs[i]) 
                                
                );
                
               
            }
            
            double max_difference =0 ; 
            double max_cost =0;
            for(int i=0;i<runs;i++)
            {
                
                
                double difference = (timesa[i] - timesb[i])/timesa[i];
                double cost_difference = (costb[i] - costa[i])/costb[i];
                if(difference>max_difference && timesa[i]>0)
                {
                    max_difference = difference;
                    max_cost = cost_difference;
                    //System.out.println(timesa[i] + " , " + timesb[i] + " wtf run " + i);
                }
            }
            
            System.out.println(max_difference + " , " + max_cost);
            
            
            System.out.println(num_of_operations_obj[1][0]/obj_runs[1][0]);
            
            File myObj = new File("pdm_log.txt");   
            Scanner myReader = new Scanner(myObj);
            int count=0;
            HashMap<Operation,String> trace;
            while(myReader.hasNextLine())
            {
                
                String data = myReader.nextLine();
                if(data.contains("<instance>"))
                {
                    trace = new HashMap<>();
                }
                else if(data.contains("</instance"))
                {
                    
                }
                else
                {
                    String[] tokens = data.split(",");
                    
                   
                }
            }
            System.out.println(count);
            
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }


    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new Main()).execute(args);
        System.exit(exitCode);
    }
    
    private static void prune(LinkedHashSet<Operation> operations, Operation operation, LinkedHashSet<Operation> prune, PDM pdm, HashMap<Operation, Boolean> prerun)
    {
        int count=0;
        for(Operation op : operations)
        {
           if(op.output().equals(operation.output()))
           {
             count +=1;  
           }
        }
        
        LinkedHashSet<Operation> set = pdm.getProductionOperations(operation.output());
        if(set.size()==1)
        {
            
        }
        else
        {
           for(Operation op : set)
           {
               if(prerun.get(op))
               {
                   return;
               }
           } 
        }
        
        
        
        //Iterator it = operations.entrySet().iterator();
        //String[] operation = (String[])operations.get(key);
        //String output = operation[operation.length-4];
        //int count=0;
        
        LinkedHashSet<DataElement> candidates = new LinkedHashSet<>();
        LinkedHashSet<DataElement> prune2 = new LinkedHashSet<>();
        
        for(Operation op : operations)
        {
            if(op.input().contains(operation.output()))
            {
                for(DataElement cand : op.input())
                {
                    if(!cand.equals(operation.output()))
                    {
                        candidates.add(cand);
                    }
                }
            }
        }
         
         
         
        
        
        int sum_cand=0;
        int sum_out=0;
        for(DataElement cand : candidates)
        {
          //System.out.println(cand);
          sum_cand=0;
          sum_out=0;
          for(Operation op : operations)
          {
              if(op.input().contains(cand) && op.input().contains(operation.output()))
              {
                  sum_out+=1;
              }
              if(op.input().contains(cand) && !op.input().contains(operation.output()))
              {
                  sum_cand+=1;
              }
          }
          
          if(sum_out==sum_cand)
          {
              prune2.add(cand);
          }
        }
        
        for(DataElement data : prune2)
        {
             for(Operation op : operations)
             {
                 if(op.output().equals(data))
                 {
                     prune.add(op);
                 }
             }
            
        }
        
       
    }
    
    
     
}
