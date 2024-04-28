package dk.sdu.sem4.pro.production;

import dk.sdu.sem4.pro.common.services.IProduction;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

public class ProductionLoader {
    public static List<IProduction> getIProductionList(){
        List<IProduction> selectServices = new ArrayList<>();
        ServiceLoader<IProduction> selectLoader = ServiceLoader.load(IProduction.class);
        Iterator<IProduction> iterator = selectLoader.iterator();
        while(iterator.hasNext()){
            selectServices.add(iterator.next());
        }
        return selectServices;
    }
}
