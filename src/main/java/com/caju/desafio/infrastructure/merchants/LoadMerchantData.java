package com.caju.desafio.infrastructure.merchants;

import com.caju.desafio.application.merchants.MerchantService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class LoadMerchantData implements ApplicationListener<ApplicationReadyEvent> {
    private final MongoMerchantRepository merchantRepository;
    private final MerchantService merchantService;

    public LoadMerchantData(MongoMerchantRepository merchantRepository, MerchantService merchantService) {
        this.merchantRepository = merchantRepository;
        this.merchantService = merchantService;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        var initialMerchants = merchantService.getMerchantInitialLoad();

        var merchantsInRepository = merchantRepository.findAll();
        if(merchantsInRepository.isEmpty()){
            merchantRepository.saveAll(initialMerchants);
        }
    }
}
