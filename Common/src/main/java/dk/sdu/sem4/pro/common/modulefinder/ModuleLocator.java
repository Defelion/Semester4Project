package dk.sdu.sem4.pro.common.modulefinder;

import java.lang.module.Configuration;
import java.lang.module.ModuleDescriptor;
import java.lang.module.ModuleFinder;
import java.lang.module.ModuleReference;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class ModuleLocator {
    public static ModuleLayer getModuleLayer() {
        /*
        var modulesearch = ModuleFinder.of(Path.of("mods-mvn/"));
        var ModuleBoot = ModuleLayer.boot();
        var configuration = ModuleBoot.configuration().resolve(modulesearch, ModuleFinder.of(), Set.of());
        return ModuleBoot.defineModulesWithOneLoader(configuration,ClassLoader.getSystemClassLoader());*/
        Path modulesDir = Paths.get("mods-mvn");
        ModuleFinder finder = ModuleFinder.of(modulesDir);
        List<String> modules = finder.findAll()
                .stream()
                .map(ModuleReference::descriptor)
                .map(ModuleDescriptor::name)
                .collect(Collectors.toList());

        Configuration configuration = ModuleLayer.boot().configuration().resolve(finder,ModuleFinder.of(),modules);
        return ModuleLayer.boot().defineModulesWithOneLoader(configuration, ClassLoader.getSystemClassLoader());
    }
}
