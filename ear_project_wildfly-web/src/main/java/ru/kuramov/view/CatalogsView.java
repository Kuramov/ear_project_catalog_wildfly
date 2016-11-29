package ru.kuramov.view;

import ru.kuramov.dao.CatalogDao;
import ru.kuramov.model.Catalog;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * Created by valerii on 29.11.16.
 */
@Named
@RequestScoped
public class CatalogsView {

    @Inject
    private CatalogDao catalogDao;

    public List<Catalog> getCatalogs() {
        return catalogDao.listAll();
    }

}
