package ru.kuramov.view;

import ru.kuramov.dao.CatalogDao;
import ru.kuramov.dao.EditionDao;
import ru.kuramov.model.Catalog;
import ru.kuramov.model.Edition;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Created by valerii on 29.11.16.
 */
@Named
@RequestScoped
public class CatalogCreate {

    @Inject
    private FacesContext facesContext;

    @Inject
    private CatalogDao catalogDao;

    @Inject
    private EditionDao editionDao;

    private Catalog newCatalog;

    private Edition newEdition;

    @Produces
    @Named
    public Catalog getNewCatalog() {
        return newCatalog;
    }

    @Produces
    @Named
    public Edition getNewEdition() {
        return newEdition;
    }

    public void createCatalog() throws Exception {
        try {
            //catalogDao.create(newCatalog);

            editionDao.create(newEdition, newCatalog);

            facesContext.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Create Catalog!", "Create catalog successful"));
            //initNewMember();
        } catch (Exception e) {
            String errorMessage = getRootErrorMessage(e);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Registration Unsuccessful");
            facesContext.addMessage(null, m);
        }
    }

    @PostConstruct
    public void initNewInstanceModel() {
        newCatalog = new Catalog();
        newEdition = new Edition();
        //
//        Context context = null;
//        try {
//            context = new InitialContext();
//
//            CatalogDao catalogDao = (CatalogDao) context
//                    .lookup("java:app/ear_project_wildfly-ejb/CatalogDao!ru.kuramov.dao.CatalogDao");
//
//        } catch (NamingException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println(">>>>>> EJB CATALOG DAO "+catalogDao);
    }

    private String getRootErrorMessage(Exception e) {
        // Default to general error message that registration failed.
        String errorMessage = "Create Catalog failed. See server log for more information";
        if (e == null) {
            // This shouldn't happen, but return the default messages
            return errorMessage;
        }

        // Start with the exception and recurse to find the root cause
        Throwable t = e;
        while (t != null) {
            // Get the message from the Throwable class instance
            errorMessage = t.getLocalizedMessage();
            t = t.getCause();
        }
        // This is the root cause message
        return errorMessage;
    }

}
