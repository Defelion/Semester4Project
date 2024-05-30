package dk.sdu.sem4.pro.datamanager.select;

import dk.sdu.sem4.pro.commondata.data.Component;
import dk.sdu.sem4.pro.commondata.data.Recipe;
import dk.sdu.sem4.pro.datamanager.connection.Conn;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectComponent {
    public Component getComponent (Component component) throws IOException {
        Conn conn = new Conn();
        Component selectComponent = new Component();
        //System.out.println("Component: " + component.getName());
        try (Connection connection = conn.getConnection()) {
            String sql = "SELECT * FROM Component ";
            if (component.getId() > 0)
                sql += "WHERE id = ?";
            else if(component.getName() != null)
                sql += "WHERE name = ?";
            //System.out.println("getComponent sql: "+sql);
            var selectSQL = connection.prepareStatement(sql);
            if(component.getId() > 0)
                selectSQL.setInt(1, component.getId());
            else if(component.getName() != null)
                selectSQL.setString(1, component.getName());
            ResultSet rs = selectSQL.executeQuery();
            while (rs.next()) {
                selectComponent.setId(rs.getInt("id"));
                selectComponent.setName(rs.getString("name"));
                selectComponent.setWishedAmount(rs.getInt("wishedamount"));
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return selectComponent;
    }

    public List<Component> getAllComponents () throws IOException {
        List<Component> components = new ArrayList<>();
        System.out.println("Getting all Components");
        Conn conn = new Conn();
        try (Connection con = conn.getConnection()) {
            var SQL = "SELECT * FROM component";
            //System.out.println("SQL Query: " + SQL);
            var selectSQL = con.prepareStatement(SQL);
            ResultSet rs = selectSQL.executeQuery();
            while (rs.next()) {
                Component component = new Component();
                component.setId(rs.getInt("id"));
                component.setName(rs.getString("name"));
                component.setWishedAmount(rs.getInt("wishedamount"));
                /*System.out.println(component.getId());
                System.out.println(component.getName());
                System.out.println(component.getWishedAmount());*/
                components.add(component);
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return components;
    }

    public Recipe getRecipe (Recipe recipe) throws IOException {
        Conn conn = new Conn();
        Recipe selectRecipe = new Recipe();
        selectRecipe.setId(0);
        try (Connection connection = conn.getConnection()) {
            Component product = recipe.getProduct();
            String sql = "SELECT * FROM recipe ";
            if (recipe.getProduct() != null)
                sql += "WHERE product_component_id = ?";
            var selectSQL = connection.prepareStatement(sql);
            if(recipe.getId() > 0)
                selectSQL.setInt(1, recipe.getId());
            else if(recipe.getProduct().getId() > 0) {
                selectSQL.setInt(1, product.getId());
            }
            ResultSet rs = selectSQL.executeQuery();
            if(selectRecipe.getComponentMap() == null) selectRecipe.setComponentList(new HashMap<>());
            while (rs.next()) {
                if(selectRecipe.getId() != rs.getInt("id")) {
                    selectRecipe.setId(rs.getInt("id"));
                    if(product.getId() > 0) selectRecipe.setProduct(product);
                    else selectRecipe.setProduct(new Component(rs.getInt("product_component_id")));
                }
                selectRecipe.addComponent(
                        new Component(rs.getInt("material_component_id")),
                        rs.getInt("amount"));
                selectRecipe.setTimeEstimation(rs.getInt("timeestimation"));
            }
            rs.close();
            if(recipe.getProduct() != product) selectRecipe.setProduct(getComponent(selectRecipe.getProduct()));
            Map<Component, Integer> componentIntegerEntryMap = new HashMap<>();
            for(Map.Entry<Component, Integer> componentIntegerEntry : selectRecipe.getComponentMap().entrySet()) {
                componentIntegerEntryMap.put(getComponent(componentIntegerEntry.getKey()), componentIntegerEntry.getValue());
            }
            selectRecipe.setComponentList(componentIntegerEntryMap);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return selectRecipe;
    }

    public List<Recipe> getAllRecipes() throws IOException {
        List<Recipe> recipes = new ArrayList<>();
        Conn conn = new Conn();
        try (Connection connection = conn.getConnection()) {
            String sql = "SELECT * FROM recipe r " +
                    "join component c on c.id = r.material_component_id " +
                    "order by r.product_component_id";
            var selectSQL = connection.prepareStatement(sql);
            ResultSet rs = selectSQL.executeQuery();
            boolean newRecipe = true;
            Recipe recipe = new Recipe(0);
            recipe.setProduct(new Component(0));
            int rows = 0;
            while (rs.next()) {
                rows += 1;
                if(recipe.getProduct().getId() != rs.getInt("product_component_id")){
                    if(recipe.getId() > 0) recipes.add(recipe);
                    //System.out.println("saved a new recipe: "+recipes.size());
                    newRecipe = true;
                }
                if(newRecipe) {
                    recipe = new Recipe();
                    newRecipe = false;
                    recipe.setId(rs.getInt("id"));
                    recipe.setTimeEstimation(rs.getInt("timeestimation"));
                    recipe.setProduct(new Component
                            (rs.getInt("product_component_id")));
                    //System.out.println("found a new recipe: "+recipe.getId());
                }
                recipe.addComponent(
                        getComponent(
                                new Component(
                                        rs.getInt("material_component_id"))),
                        rs.getInt("amount"));
            }
            rs.close();
            for (Recipe recipeComponent : recipes) {
                Map<Component, Integer> recipeEntrys = new HashMap<>();
                recipeComponent.setProduct(getComponent(recipeComponent.getProduct()));
                for(Map.Entry<Component, Integer> componentIntegerEntry : recipeComponent.getComponentMap().entrySet()) {
                    recipeEntrys.put(getComponent(componentIntegerEntry.getKey()), componentIntegerEntry.getValue());
                }
                recipeComponent.setComponentList(recipeEntrys);
            }
            //System.out.println("amount of Recipe rows found: "+rows);
            //System.out.println("amount of recipes found: "+recipes.size());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return recipes;
    }
}
