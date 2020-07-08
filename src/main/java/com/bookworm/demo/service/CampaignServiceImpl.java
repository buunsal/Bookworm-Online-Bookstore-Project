package com.bookworm.demo.service;

import com.bookworm.demo.exception.ResourceNotFoundException;
import com.bookworm.demo.model.Campaign;
import com.bookworm.demo.repository.CampaignRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.ejb.EJB;
import java.util.List;

@Service
public class CampaignServiceImpl implements CampaignService {

    @EJB
    private CampaignRepository campaignRepository;

    @Override
    @Transactional
    public List<Campaign> getCampaigns() {
        return campaignRepository.findAll();
    }

    @Override
    @Transactional
    public void saveCampaign(Campaign campaign) {
        campaignRepository.save(campaign);
    }

    @Override
    @Transactional
    public Campaign getCampaign(int id) throws ResourceNotFoundException {
        return campaignRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(id));
    }

    @Override
    @Transactional
    public void deleteCampaign(int id) {
        campaignRepository.deleteById(id);
    }

}
