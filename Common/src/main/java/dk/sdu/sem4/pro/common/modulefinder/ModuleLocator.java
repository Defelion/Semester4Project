package dk.sdu.sem4.pro.common.modulefinder;

import java.beans.BeanProperty;
import java.io.IOException;
import java.lang.module.Configuration;
import java.lang.module.ModuleDescriptor;
import java.lang.module.ModuleFinder;
import java.lang.module.ModuleReference;
import java.nio.file.*;
import java.util.List;
import java.util.Objects;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public abstract class ModuleLocator {
    public static ModuleLayer getModuleLayer() {
        System.out.println(System.getProperty("user.dir")+"/mods-mvn/");
        try {
            Path modulesDir = Paths.get(System.getProperty("user.dir")+"/mods-mvn/");

            Predicate<Path> filter = path -> path.getFileName().toString().endsWith("SNAPSHOT.jar");
            Stream<Path> modulesStream = Files.list(modulesDir).filter(filter);
                List<String> modules = modulesStream
                        .map(ModuleLocator::moduleNameFromJar)
                        .distinct()
                        .collect(Collectors.toList());
            for(String module : modules) {
                System.out.println(module);
            }

            Configuration configuration = ModuleLayer.boot()
                    .configuration()
                    .resolve(
                            ModuleFinder.of(modulesDir),
                            ModuleFinder.of(),
                            modules);
            return ModuleLayer.boot().defineModulesWithOneLoader(configuration, ClassLoader.getSystemClassLoader());
        } catch (IOException e) {
            System.out.println(e);
            return null;
        }
    }

    private static String moduleNameFromJar(Path path) {
        try (FileSystem jarFile = FileSystems.newFileSystem(path, (ClassLoader)null)) {
            String jarfileName = path.getFileName().toString().substring(0, path.getFileName().toString().indexOf("-"));
            //System.out.println(jarfileName);
            return jarfileName;
        } catch (IOException e) {
            System.out.println(e);
            return null;
        }
    }
}
