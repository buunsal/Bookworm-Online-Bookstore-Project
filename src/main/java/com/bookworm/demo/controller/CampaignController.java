package com.bookworm.demo.controller;

import com.bookworm.demo.exception.ResourceNotFoundException;
import com.bookworm.demo.model.Campaign;
import com.bookworm.demo.service.CampaignService;
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
@Component(value = "campaignController")
public class CampaignController implements Serializable {

    private static final long serialVersionUID = 4L;

    private List<Campaign> campaigns;

    private Campaign campaign = new Campaign();

    private Campaign selectedCampaign = new Campaign();

    private List<Campaign> filteredCampaigns;

    @EJB
    private CampaignService campaignService;

    @PostConstruct
    public void init() {
        this.campaigns = campaignService.getCampaigns();
    }


    public void add() {
        campaignService.saveCampaign(campaign);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Create Successful", "Created Campaign:" + campaign.getName()));
        this.campaigns = campaignService.getCampaigns();
        this.campaign = new Campaign();
    }

    public void delete(Campaign campaign) throws ResourceNotFoundException {
        campaignService.deleteCampaign(campaign.getId());
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Delete Successful", "Deleted Campaign:" + campaign.getName()));
        campaigns.remove(campaign);
    }

    public void update() {
        campaignService.saveCampaign(selectedCampaign);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Update Successful", "Updated Campaign:" + selectedCampaign.getName()));
        this.selectedCampaign = new Campaign();
    }

    public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
        if (filterText == null || filterText.equals("")) {
            return true;
        }

        Campaign campaign = (Campaign) value;
        return campaign.getDiscount_amount().toString().startsWith(filterText) || campaign.getName().toLowerCase().contains(filterText);
    }

}
