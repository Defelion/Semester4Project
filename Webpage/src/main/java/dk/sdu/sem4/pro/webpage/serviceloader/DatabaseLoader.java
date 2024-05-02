package dk.sdu.sem4.pro.webpage.serviceloader;

import dk.sdu.sem4.pro.commondata.services.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.*;
@Configuration
@ComponentScan(basePackages = "dk.sdu.sem4.pro.datamanager.select")
public class DatabaseLoader {

    public DatabaseLoader() {
    }

    @Bean
    public List<ISelect> getISelectList(){
        List<ISelect> selectServices = new ArrayList<>();
        ServiceLoader<ISelect> selectLoader = ServiceLoader.load(ISelect.class);

        System.out.println("Elements in ServiceLoader:" + selectLoader.stream().count());
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        System.out.println("ClassLoader for DatabaseLoader: " + classLoader);

        System.out.println("Paths searched by ServiceLoader:");
        try {
            Enumeration<URL> resources = classLoader.getResources("META-INF/services/dk.sdu.sem4.pro.commondata.services.ISelect");
            while (resources.hasMoreElements()) {
                URL url = resources.nextElement();
                System.out.println(url);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Iterator<ISelect> iterator = selectLoader.iterator();
        while(iterator.hasNext()){
            selectServices.add(iterator.next());
        }
        return selectServices;
    }

    @Bean
    public List<IDelete> getIDeleteList(){
        List<IDelete> deleteServices = new ArrayList<>();
        ServiceLoader<IDelete> deleteLoader = ServiceLoader.load(IDelete.class);
        Iterator<IDelete> iterator = deleteLoader.iterator();
        while(iterator.hasNext()){
            deleteServices.add(iterator.next());
        }
        return deleteServices;
    }

    @Bean
    public List<IUpdate> getIUpdateList(){
        List<IUpdate> updateServices = new ArrayList<>();
        ServiceLoader<IUpdate> updateLoader = ServiceLoader.load(IUpdate.class);
        Iterator<IUpdate> iterator = updateLoader.iterator();
        while(iterator.hasNext()){
            updateServices.add(iterator.next());
        }
        return updateServices;
    }

    @Bean
    public List<IInsert> getIInsertList(){
        List<IInsert> insertServices = new ArrayList<>();
        ServiceLoader<IInsert> insertLoader = ServiceLoader.load(IInsert.class);
        Iterator<IInsert> iterator = insertLoader.iterator();
        while(iterator.hasNext()){
            insertServices.add(iterator.next());
        }
        return insertServices;
    }

    @Bean
    public List<IHash> getIHashList(){
        List<IHash> hashServices = new ArrayList<>();
        ServiceLoader<IHash> hashLoader = ServiceLoader.load(IHash.class);
        Iterator<IHash> iterator = hashLoader.iterator();
        while(iterator.hasNext()){
            hashServices.add(iterator.next());
        }
        return hashServices;
    }
}
