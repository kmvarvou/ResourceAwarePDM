package org.auth.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.jgrapht.alg.util.Pair;

public class Operation {
    private int _id;
    private String _name;
    private List<DataElement> _input;
    private DataElement _output;
    private double _time;
    private double _cost;
    private double _probability;
    private Resource _resource;
    private List<Resource> _backup;
    private HashMap<Resource,Double> _cost_resource;
    private HashMap<Resource,Double> _time_resource;
    private Resource _assignment;
    double _starting_time;

    public Operation(int id, String name, List<DataElement> input, DataElement output,
                     double time, double cost, double probability, Resource resource, List<Resource> backup_resources, HashMap<Resource,Double> cost_resource, HashMap<Resource,Double> time_resource) {
        _id = id;
        _name = name;
        _input = input;
        _output = output;
        _time = time;
        _cost = cost;
        _probability = probability;
        _resource = resource;
        _backup = backup_resources;
        _cost_resource = cost_resource;
        _time_resource = time_resource;
        
    }

    public Operation(Operation other) {
        _id = other._id;
        _name = other._name;
        _input = new ArrayList<>();
        for (DataElement el : other._input) {
            _input.add(new DataElement(el));
        }
        _output = new DataElement(other._output);
        _time = other._time;
        _cost = other._cost;
        _probability = other._probability;
        _resource = other._resource;
         _backup = other._backup;
         _cost_resource = other._cost_resource;
         _time_resource = other._time_resource;
    }

    public void randomize() {
        _time = new java.util.Random().nextInt(9) + 1;
        _cost = new java.util.Random().nextInt(9) + 1;
        HashMap<Resource, Double> cost_resource = _cost_resource;
            Resource r = null;
            Iterator it = cost_resource.entrySet().iterator();
            int i=0;
            Double base_cost =0.0,base_time =0.0;
            HashMap<Resource,Double> factors = new HashMap<Resource,Double>();
            while(it.hasNext())
             {
                Map.Entry<Resource, Double> map_entry = (Map.Entry<Resource, Double>) it.next();
                Double random_cost;
                r = map_entry.getKey();
                if(i==0)
                {
                     random_cost = (double) (new java.util.Random().nextInt(9) + 1);
                    base_cost = random_cost;
                }
                else
                {
                    double start = 0.5;
                    double end = 1.5;
                    double random = new Random().nextDouble();
                    double result = start + (random * (end - start));
                     random_cost = base_cost * result;
                    factors.put(r, result);
                    
                }
                 
                _cost_resource.replace(r, random_cost);
                i++;
                
            }
            i=0;
            it = _time_resource.entrySet().iterator();
            while(it.hasNext())
             {
                Map.Entry<Resource, Double> map_entry = (Map.Entry<Resource, Double>) it.next();
                r = map_entry.getKey();
                Double random_cost;
                if(i==0)
                {
                     random_cost = (double) (new java.util.Random().nextInt(9) + 1);
                    base_cost = random_cost;
                }
                else
                {
                     double result = factors.get(r);
                     random_cost = base_cost * result;
                   
                }
                 
                _time_resource.replace(r, random_cost);
                i++;
                
            }
        _probability = 0.001 + (1 - 0.001) * new java.util.Random().nextDouble();
    }
    
    public void randomize(List<Resource> resources) {
        _time = new java.util.Random().nextInt(9) + 1;
        _cost = new java.util.Random().nextInt(9) + 1;
        Random randomNum = new Random();
        ;
        int lowerBound = 1;
        int upperBound = resources.size();
        int randomNumber = randomNum.nextInt(upperBound - lowerBound) + lowerBound;
        _backup = resources.subList(1, randomNumber);
        _cost_resource = new HashMap<>();
        //HashMap<Resource, Double> cost_resource = _cost_resource;
           
            int i=0;
            Double base_cost =0.0,base_time =0.0;
            HashMap<Resource,Double> factors = new HashMap<Resource,Double>();
            for(Resource r : _backup)
             {
                //Map.Entry<Resource, Double> map_entry = (Map.Entry<Resource, Double>) it.next();
                Double random_cost;
                //r = map_entry.getKey();
                if(i==0)
                {
                     random_cost = (double) (new java.util.Random().nextInt(9) + 1);
                    base_cost = random_cost;
                }
                else
                {
                    double start = 0.5;
                    double end = 1.5;
                    double random = new Random().nextDouble();
                    double result = start + (random * (end - start));
                     random_cost = base_cost * result;
                    factors.put(r, result);
                    
                }
                 
                _cost_resource.put(r, random_cost);
                i++;
                
            }
            i=0;
            _time_resource = new HashMap<>();
            for(Resource r : _backup)
             {
                
                
                Double random_cost;
                if(i==0)
                {
                     random_cost = (double) (new java.util.Random().nextInt(9) + 1);
                    base_cost = random_cost;
                }
                else
                {
                     double result = factors.get(r);
                     random_cost = base_cost * result;
                   
                }
                 
                _time_resource.put(r, random_cost);
                i++;
                
            }
        _probability = 0.001 + (1 - 0.001) * new java.util.Random().nextDouble();
    }


    public boolean hasInput() {
        return _input != null && _input.size() > 0;
    }

    public List<DataElement> input() {
        return _input;
    }

    public void setTime(double time) {
        _time = time;
    }

    public void setCost(double cost) {
        _cost = cost;
    }
    
    public void setStartingTime(double time)
    {
        _starting_time = time;
    }
    
    public Double getStartingTime()
    {
        return _starting_time;
    }

    public void setProbability(double probability) {
        _probability = probability;
    }
    
    public double getTime() {
        return _time;
    }
    
    public double getTime(Double current_time) {
        Resource r = this.getLowestCostResource(current_time);
        
        
         
         
          return _time_resource.get(r);
        
    }
    
    public double getCost(Double current_time) {
        Resource r = this.getLowestCostResource(current_time);
        
       
        
          
          return _cost_resource.get(r);
        
    }
    
    public HashMap<Resource,Double> getCostResource()
    {
        return _cost_resource;
    }
    
    public HashMap<Resource,Double> getTimeResource()
    {
        return _time_resource;
    }
    
    public double getCost() {
        
        
        return _cost;
    }
    
    public double getCost(Resource resource){
        return _cost_resource.get(resource);
    }
    
    public double getTime(Resource resource){
        if(_time_resource.get(resource)==null){System.out.println(resource.getName() + " , " + this.getName());}
        return _time_resource.get(resource);
    }
    
    public double getProbability() {
        return _probability;
    }
    
    public int getId() {
        return _id;
    }
    
    public String getName() {
        return _name;
    }
    
    public DataElement output() {
        return _output;
    }
    
    public Resource getResource() {
        return _resource;
    }
    
    public Resource getRandomResource(Double current_time)
    {
            HashMap<Resource, Double> cost_resource = _cost_resource;
            Resource r = null;
            Iterator it = cost_resource.entrySet().iterator();
            while(it.hasNext())
             {
                Map.Entry<Resource, Double> map_entry = (Map.Entry<Resource, Double>) it.next();
                Resource resource = map_entry.getKey();
                if(resource.getLiveCapacity(current_time)>0)
                {
                   return resource;
                }
                
            }
            return r;
            
    }
    
    public List<Resource> getResources()
    {
        return _backup;
    }
    
    public void assignResource(Resource r)
    {
        _assignment = r;
    }
    
    public Double getLowestCost( Double current_time)
    {
            Double min = Double.MAX_VALUE;
            HashMap<Resource, Double> cost_resource = _cost_resource;
            Iterator it = cost_resource.entrySet().iterator();
            while(it.hasNext())
             {
                Map.Entry<Resource, Double> map_entry = (Map.Entry<Resource, Double>) it.next();
                Resource resource = map_entry.getKey();
                if(resource.getLiveCapacity(current_time)>0)
                {
                   if(map_entry.getValue() < min)
                   {
                       
                       min = map_entry.getValue();
                       //op.assignResource(resource);
                   } 
                }
                
            }
            
            return min;
    }
    
    public Double getLowestTime( Double current_time)
    {
            Double min = Double.MAX_VALUE;
            HashMap<Resource, Double> time_resource = _time_resource;
            Iterator it = time_resource.entrySet().iterator();
            while(it.hasNext())
             {
                Map.Entry<Resource, Double> map_entry = (Map.Entry<Resource, Double>) it.next();
                Resource resource = map_entry.getKey();
                if(resource.getLiveCapacity(current_time)>0)
                {
                   if(map_entry.getValue() < min)
                   {
                       
                       min = map_entry.getValue();
                       //op.assignResource(resource);
                   } 
                }
                
            }
            
            return min;
    }
    
    public Double getLowestCombo( Double current_time)
    {
            Double min = Double.MAX_VALUE;
            HashMap<Resource, Double> cost_resource = _cost_resource;
            HashMap<Resource, Double> time_resource = _time_resource;
            Iterator it = time_resource.entrySet().iterator();
            while(it.hasNext())
             {
                Map.Entry<Resource, Double> map_entry = (Map.Entry<Resource, Double>) it.next();
                Resource resource = map_entry.getKey();
                if(resource.getLiveCapacity(current_time)>0)
                {
                   Double test = map_entry.getValue() * cost_resource.get(map_entry.getKey());
                   if(test < min)
                   {
                       
                       min = test;
                       //op.assignResource(resource);
                   } 
                }
                
            }
            
            return min;
    }
    
    public Resource getDefaultResource()
    {
        Iterator it = _time_resource.entrySet().iterator();
        while(it.hasNext())
             {
                Map.Entry<Resource, Double> map_entry = (Map.Entry<Resource, Double>) it.next();
                if(map_entry.getKey().getName().equals("ResourceA"))
                {
                    return map_entry.getKey();
                }
             }
        return null;
    }
    
    
    
    
    

    @Override
    public String toString() {
        return "{\n" +
                    "\tid: " + _id + "\n" +
                    "\tname: " + _name + "\n" +
                    "\tinput: " + String.join(", ", _input.toString())+ "\n" +
                    "\toutput: " + (_output != null ? _output.toString() : "-") + "\n" +
                    "\ttime: " + _time + "\n" +
                    "\tcost: " + _cost + "\n" +
                    "\tprobability: " + _probability + "\n" +
                "}";
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + _id;
        result = 31 * result + _name.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Operation)) return false;
        Operation de = (Operation)o;
        
        return _id == ((Operation) o)._id && _name.equals(de._name);
        
    }
    
    
    public Resource getLowestCostResource(Double current_time)
    {
        Iterator it = _cost_resource.entrySet().iterator();
        Double min = Double.MAX_VALUE;
        Resource r = null;
        while(it.hasNext())
        {
            Map.Entry<Resource,Double> resource_entry = (Map.Entry<Resource,Double>) it.next();
            Resource resource_check = resource_entry.getKey();
            if(resource_check.getLiveCapacity(current_time)>0)
            {
                //System.out.println("ti fasi elegxos" );
                if(_cost_resource.get(resource_check)<min)
                {
                    min = _cost_resource.get(resource_check);
                    r = resource_check;
                }
            }
        }
        
        return r;
    }
    
    public Resource getLowestTimeResource(Double current_time)
    {
        Iterator it = _time_resource.entrySet().iterator();
        Double min = Double.MAX_VALUE;
        Resource r = null;
        while(it.hasNext())
        {
            Map.Entry<Resource,Double> resource_entry = (Map.Entry<Resource,Double>) it.next();
            Resource resource_check = resource_entry.getKey();
            if(resource_check.getLiveCapacity(current_time)>0)
            {
                if(_time_resource.get(resource_check)<min)
                {
                    min = _time_resource.get(resource_check);
                    r = resource_check;
                }
            }
        }
        
        return r;
    }
    
    public Resource getLowestComboResource(Double current_time)
    {
        Iterator it = _cost_resource.entrySet().iterator();
        Double min = Double.MAX_VALUE;
        Resource r = null;
        while(it.hasNext())
        {
            Map.Entry<Resource,Double> resource_entry = (Map.Entry<Resource,Double>) it.next();
            Resource resource_check = resource_entry.getKey();
            if(resource_check.getLiveCapacity(current_time)>0)
            {
                //System.out.println("ti fasi elegxos" );
                if((_cost_resource.get(resource_check) * _time_resource.get(resource_check) )<min)
                {
                    min = _cost_resource.get(resource_check) * _time_resource.get(resource_check);
                    r = resource_check;
                }
            }
        }
        
        return r;
    }
    
}



