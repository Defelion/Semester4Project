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
        try (Connection connection = conn.getConnection()) {
            String sql = "SELECT * FROM Component ";
            if (component.getId() > 0)
                sql += "WHERE id = ?";
            else if(component.getName() != null)
                sql += "WHERE name = ?";
            var selectSQL = connection.prepareStatement(sql);
            if(component.getId() > 0)
                selectSQL.setInt(1, component.getId());
            else if(component.getName() != null)
                selectSQL.setString(1, component.getName());
            ResultSet rs = selectSQL.executeQuery();
            while (rs.next()) {
                selectComponent.setId(rs.getInt("id"));
                selectComponent.setName(rs.getString("name"));
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return selectComponent;
    }

    public List<Component> getAllComponents () throws IOException {
        List<Component> components = new ArrayList<>();
        Conn conn = new Conn();
        try (Connection con = conn.getConnection()) {
            var SQL = "SELECT * FROM Component";
            var selectSQL = con.prepareStatement(SQL);
            ResultSet rs = selectSQL.executeQuery();
            while (rs.next()) {
                var component = new Component();
                component.setId(rs.getInt("id"));
                component.setName(rs.getString("name"));
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
        try (Connection connection = conn.getConnection()) {
            Component product = new Component();
            String sql = "SELECT * FROM Recipe ";
            if (recipe.getId() > 0)
                sql += "WHERE id = ?";
            else if (recipe.getProduct().getId() > 0 || recipe.getProduct().getName() != null)
                sql += "WHERE product_component_id = ?";
            var selectSQL = connection.prepareStatement(sql);
            if(recipe.getId() > 0)
                selectSQL.setInt(1, recipe.getId());
            else if(recipe.getProduct().getId() > 0) {
                product = getComponent(recipe.getProduct());
                selectSQL.setInt(1, recipe.getProduct().getId());
            }
            else if(recipe.getProduct().getName() != null) {
                product = getComponent(recipe.getProduct());
                selectSQL.setInt(1,product.getId());
            }
            ResultSet rs = selectSQL.executeQuery();
            while (rs.next()) {
                if(selectRecipe.getId() != 0) {
                    selectRecipe.setId(rs.getInt("id"));
                    if(product.getId() != 0) selectRecipe.setProduct(product);
                    else selectRecipe.setProduct(new Component(rs.getInt("product_component_id")));
                }
                selectRecipe.addComponent(
                        new Component(rs.getInt("material_component_id")),
                        rs.getInt("amount"));
                selectRecipe.setTimeEstimation(rs.getInt("timeestimation"));
            }
            rs.close();
            if(product.getId() != 0) selectRecipe.setProduct(getComponent(selectRecipe.getProduct()));
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
            String sql = "SELECT * FROM Recipe r " +
                    "join component c on c.id = r.material_component_id";
            var selectSQL = connection.prepareStatement(sql);
            ResultSet rs = selectSQL.executeQuery();
            boolean newRecipe = true;
            Recipe recipe = null;
            while (rs.next()) {
                if(recipe.getProduct().getId() != rs.getInt("product_component_id")){
                    if(recipe != null) recipes.add(recipe);
                    newRecipe = false;
                }
                if(newRecipe) {
                    recipe = new Recipe();
                    newRecipe = false;
                    recipe.setId(rs.getInt("id"));
                    recipe.setTimeEstimation(rs.getInt("timeestimation"));
                    recipe.setProduct(getComponent(new Component
                            (rs.getInt("product_component_id"))));
                }
                recipe.addComponent(
                        getComponent(
                                new Component(
                                        rs.getInt("material_component_id"))),
                        rs.getInt("amount"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return recipes;
    }
}
