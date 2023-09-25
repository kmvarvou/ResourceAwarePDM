package org.auth.util;

import org.auth.model.DataElement;
import org.auth.model.Operation;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Random;
import org.auth.model.Resource;
import org.jgrapht.alg.util.Pair;

public class Utilities {
    public static boolean isExecuted(double prob) {
        Random r = new Random();
        return !(r.nextDouble() > prob);
    }

    public static int getRandomNumberInRange(int min, int max) {
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    public static Operation getOperationWithMinProbability(LinkedHashSet<Operation> operations) {
        Operation ret = null;
        double min = Double.MAX_VALUE;
        for (Operation op : operations) {
            if (op.getProbability() < min) {
                min = op.getProbability();
                ret = op;
            }
        }
        return ret;
    }

    public static Operation getOperationWithMaxProbability(LinkedHashSet<Operation> operations) {
        Operation ret = null;
        double max = Double.MIN_VALUE;
        for (Operation op : operations) {
            if (op.getProbability() > max) {
                max = op.getProbability();
                ret = op;
            }
        }
        return ret;
    }

    public static Operation getOperationWithMinCost(LinkedHashSet<Operation> operations) {
        Operation ret = null;
        double min = Double.MAX_VALUE;
        for (Operation op : operations) {
            if (op.getCost() < min) {
                min = op.getCost();
                ret = op;
            }
        }
        return ret;
    }
    
    public static Pair<Operation,Resource> getOperationWithMinCost(LinkedHashSet<Operation> operations, Double current_time) {
        Operation ret = null;
        double min = Double.MAX_VALUE;
        Resource r =null;
        Pair <Operation,Resource> result;
        for (Operation op : operations) {
            HashMap<Resource, Double> cost_resource = op.getCostResource();
            Iterator it = cost_resource.entrySet().iterator();
            while(it.hasNext())
             {
                Map.Entry<Resource, Double> map_entry = (Map.Entry<Resource, Double>) it.next();
                Resource resource = map_entry.getKey();
                if(resource.getLiveCapacity(current_time)>0)
                {
                   if(map_entry.getValue() < min)
                   {
                       ret = op;
                       min = map_entry.getValue();
                       r = resource;
                   } 
                }
                
            }
        }
        result = new Pair<Operation,Resource>(ret,r);
        return result;
    }

    public static Pair<Operation,Resource> getOperationWithMaxCost(LinkedHashSet<Operation> operations, Double current_time) {
        Operation ret = null;
        double max = Double.MIN_VALUE;
        Resource r =null;
        Pair <Operation,Resource> result;
        for (Operation op : operations) {
            HashMap<Resource, Double> cost_resource = op.getCostResource();
            Iterator it = cost_resource.entrySet().iterator();
            while(it.hasNext())
             {
                Map.Entry<Resource, Double> map_entry = (Map.Entry<Resource, Double>) it.next();
                Resource resource = map_entry.getKey();
                if(resource.getLiveCapacity(current_time)>0)
                {
                   if(map_entry.getValue() > max)
                   {
                       ret = op;
                       max = map_entry.getValue();
                       r = resource;
                   } 
                }
                
            }
        }
        result = new Pair <Operation,Resource>(ret,r);
        return result;
    }

    public static Operation getOperationWithMinTime(LinkedHashSet<Operation> operations) {
        Operation ret = null;
        double min = Double.MAX_VALUE;
        for (Operation op : operations) {
            if (op.getTime() < min) {
                min = op.getTime();
                ret = op;
            }
        }
        return ret;
    }
    
    public static Pair<Operation,Resource> getOperationWithMinTime(LinkedHashSet<Operation> operations, Double current_time) {
        Operation ret = null;
        double min = Double.MAX_VALUE;
        Resource r =null;
        Pair <Operation,Resource> result;
        for (Operation op : operations) {
            HashMap<Resource, Double> cost_resource = op.getTimeResource();
            Iterator it = cost_resource.entrySet().iterator();
            while(it.hasNext())
             {
                Map.Entry<Resource, Double> map_entry = (Map.Entry<Resource, Double>) it.next();
                Resource resource = map_entry.getKey();
                if(resource.getLiveCapacity(current_time)>0)
                {
                   if(map_entry.getValue() < min)
                   {
                       ret = op;
                       min = map_entry.getValue();
                       r = resource;
                       //op.assignResource(resource);
                       
                   } 
                }
                
            }
        }
        result = new Pair(ret,r);
        return result;
    }

    public static Operation getOperationWithMaxTime(LinkedHashSet<Operation> operations) {
        Operation ret = null;
        double max = Double.MIN_VALUE;
        for (Operation op : operations) {
            if (op.getTime() > max) {
                max = op.getTime();
                ret = op;
            }
        }
        return ret;
    }
    
     public static Pair<Operation,Resource> getOperationWithMaxTime(LinkedHashSet<Operation> operations, Double current_time) {
        Operation ret = null;
        double max = Double.MIN_VALUE;
        Resource r =null;
        Pair <Operation,Resource> result;
        for (Operation op : operations) {
            HashMap<Resource, Double> cost_resource = op.getTimeResource();
            Iterator it = cost_resource.entrySet().iterator();
            while(it.hasNext())
             {
                Map.Entry<Resource, Double> map_entry = (Map.Entry<Resource, Double>) it.next();
                Resource resource = map_entry.getKey();
                if(resource.getLiveCapacity(current_time)>0)
                {
                   if(map_entry.getValue() > max)
                   {
                       ret = op;
                       max = map_entry.getValue();
                       r = resource;
                   } 
                }
                
            }
            
        }
        result = new Pair<Operation, Resource>(ret,r);
        return result;
    }

    public static double getMinValue(double[][] numbers)
    {
        double minValue = numbers[0][1];
        int index = 0;
        for (int i = 1; i < numbers.length; i++) {
            if (numbers[i][1] < minValue) {
                minValue = numbers[i][1];
                index = i;
            }
        }
        
        return numbers[index][0];
    }


    public static double getMaxValue(double[][] numbers)
    {

        double maxValue = numbers[0][1];
        int index = 0;
        for (int i = 1; i < numbers.length; i++) {
            if (numbers[i][1] > maxValue) {
                maxValue = numbers[i][1];
                index = i;
            }
        }

        return numbers[index][0];
    }

    public static Operation getOperationWithMinOutputDistance(LinkedHashSet<Operation> operations, HashMap<DataElement, Double> distances)
    {
        Operation operation = null;
        double min = Double.MAX_VALUE;
        for (Operation op : operations) {
            if (min > distances.get(op.output())) {
                min = distances.get(op.output());
                operation = op;
            }
        }
        return operation;
    }
    public static Operation getOperationWithMaxOutputDistance(LinkedHashSet<Operation> operations, HashMap<DataElement, Double> distances)
    {
        Operation operation = null;
        double max = -1;
        for (Operation op : operations) {
            if (distances.get(op.output()) > max) {
                max = distances.get(op.output());
                operation = op;
            }
        }
        return operation;
    }
    
    
   

    public static HashMap<Operation, Boolean> prerun(LinkedHashSet<Operation> operations) {
        HashMap<Operation, Boolean> ret = new HashMap<>();
        for (Operation op : operations) {
            ret.put(op, Utilities.isExecuted(op.getProbability()));
        }
        return ret;
    }

}
