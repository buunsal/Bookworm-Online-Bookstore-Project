package com.bookworm.demo.service;

import com.bookworm.demo.exception.ResourceNotFoundException;
import com.bookworm.demo.model.Campaign;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CampaignService {

    List<Campaign> getCampaigns();

    void saveCampaign(Campaign campaign);

    Campaign getCampaign(int id) throws ResourceNotFoundException;

    void deleteCampaign(int id) throws ResourceNotFoundException;

}
