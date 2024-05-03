package dk.sdu.sem4.pro.webpage.serviceloader;

import dk.sdu.sem4.pro.commondata.services.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public abstract class DatabaseLoader {
    public static List<ISelect> getISelectList(){
        List<ISelect> selectServices = new ArrayList<>();
        selectServices = ServiceLoader.load(ISelect.class).stream().map(ServiceLoader.Provider::get).collect(toList());
        /*ServiceLoader<ISelect> selectLoader = ServiceLoader.load(ISelect.class);
        Iterator<ISelect> iterator = selectLoader.iterator();
        while(iterator.hasNext()){
            selectServices.add(iterator.next());
        }*/
        System.out.println(selectServices);
        return selectServices;
    }

    public static List<IDelete> getIDeleteList(){
        List<IDelete> deleteServices = new ArrayList<>();
        ServiceLoader<IDelete> deleteLoader = ServiceLoader.load(IDelete.class);
        Iterator<IDelete> iterator = deleteLoader.iterator();
        while(iterator.hasNext()){
            deleteServices.add(iterator.next());
        }
        return deleteServices;
    }

    public static List<IUpdate> getIUpdateList(){
        List<IUpdate> updateServices = new ArrayList<>();
        ServiceLoader<IUpdate> updateLoader = ServiceLoader.load(IUpdate.class);
        Iterator<IUpdate> iterator = updateLoader.iterator();
        while(iterator.hasNext()){
            updateServices.add(iterator.next());
        }
        return updateServices;
    }

    public static List<IInsert> getIInsertList(){
        List<IInsert> insertServices = new ArrayList<>();
        ServiceLoader<IInsert> insertLoader = ServiceLoader.load(IInsert.class);
        Iterator<IInsert> iterator = insertLoader.iterator();
        while(iterator.hasNext()){
            insertServices.add(iterator.next());
        }
        return insertServices;
    }

    public static List<IHash> getIHashList(){
        List<IHash> hashServices = new ArrayList<>();
        ServiceLoader<IHash> hashLoader = ServiceLoader.load(IHash.class);
        Iterator<IHash> iterator = hashLoader.iterator();
        while(iterator.hasNext()){
            hashServices.add(iterator.next());
        }
        return hashServices;
    }
}
