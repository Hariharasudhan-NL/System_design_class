
package com.rest.services;

import java.util.List;
import com.rest.models.Menu;
import com.rest.repository.MenuRepository;

public class MenuServices {
    private final MenuRepository repository;

    public MenuServices() {
        repository = new MenuRepository();
    }

    public List<Menu> getAll() {
        return repository.show();
    }
}
