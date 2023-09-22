package org.auth.heuristic;

import org.auth.model.DataElement;
import org.auth.model.Operation;
import org.auth.model.PDM;

import java.util.HashMap;
import java.util.LinkedHashSet;
import org.auth.model.Resource;
import org.jgrapht.alg.util.Pair;

public interface Heuristic
{
    
    
    Pair<Operation,Resource> execute_nr (PDM pdm, LinkedHashSet<Operation> executable, LinkedHashSet<DataElement> available, HashMap<Operation, Boolean> prerun, Double current_time);
    
    Pair<Operation,Resource> execute (PDM pdm, LinkedHashSet<Operation> executable, LinkedHashSet<DataElement> available, HashMap<Operation, Boolean> prerun, Double current_time);
}
