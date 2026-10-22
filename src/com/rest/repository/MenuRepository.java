
package com.rest.repository;

import java.util.ArrayList;
import java.util.List;
import com.rest.models.Menu;

public class MenuRepository {
    private final List<Menu> menuItems = new ArrayList<>();

    public MenuRepository() {
        menuItems.add(new Menu(1, "Dosa", 40));
        menuItems.add(new Menu(2, "Idly", 30));
        menuItems.add(new Menu(3, "Biriyani", 120));
        menuItems.add(new Menu(4, "Fried Rice", 100));
    }

    public List<Menu> show() {
        return menuItems;
    }
}
