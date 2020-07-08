package com.bookworm.demo.controller;

import com.bookworm.demo.exception.ResourceNotFoundException;
import com.bookworm.demo.model.Cargo;
import com.bookworm.demo.service.CargoService;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.List;
import java.util.Locale;

@Data
@ViewScoped
@Component(value = "cargoController")
public class CargoController implements Serializable {

    private static final long serialVersionUID = 4L;

    private List<Cargo> cargos;

    private Cargo cargo = new Cargo();

    private Cargo selectedCargo = new Cargo();

    private List<Cargo> filteredCargos;

    @EJB
    private CargoService cargoService;

    @PostConstruct
    public void init() {
        this.cargos = cargoService.getCargos();
    }


    public void add() {
        cargoService.saveCargo(cargo);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Create Successful", "Created Cargo:" + cargo.getName()));
        this.cargos = cargoService.getCargos();
        this.cargo = new Cargo();
    }

    public void delete(Cargo cargo) throws ResourceNotFoundException {
        cargoService.deleteCargo(cargo.getId());
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Delete Successful", "Deleted Cargo:" + cargo.getName()));
        cargos.remove(cargo);
    }

    public void update() {
        cargoService.saveCargo(selectedCargo);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Update Successful", "Updated Cargo:" + selectedCargo.getName()));
        this.selectedCargo = new Cargo();
    }

    public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
        if (filterText == null || filterText.equals("")) {
            return true;
        }

        Cargo cargo = (Cargo) value;
        return cargo.getPhoneNumber().startsWith(filterText)
                || cargo.getName().toLowerCase().contains(filterText)
                || cargo.getEmail().toLowerCase().contains(filterText)
                || cargo.getUrl().toLowerCase().contains(filterText);
    }
}
