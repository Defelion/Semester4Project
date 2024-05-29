package dk.sdu.sem4.pro.webpage.serviceloader;

import dk.sdu.sem4.pro.common.services.IProduction;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

public class ProductionLoader {
    @Bean
    public static List<IProduction> getIProductionList(){
        List<IProduction> productionServices = new ArrayList<>();
        ServiceLoader<IProduction> productionLoader = ServiceLoader.load(IProduction.class);
        Iterator<IProduction> iterator = productionLoader.iterator();
        while(iterator.hasNext()){
            productionServices.add(iterator.next());
        }
        return productionServices;
    }
}
