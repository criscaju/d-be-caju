package com.caju.desafio.infrastructure.merchants;

import com.caju.desafio.domain.merchants.IMerchantRepository;
import com.caju.desafio.domain.merchants.Merchant;
import com.caju.desafio.domain.merchants.MerchantName;
import org.springframework.stereotype.Repository;

@Repository
public class MerchantRepositoryImpl implements IMerchantRepository {

    private final MongoMerchantRepository merchantRepository;

    public MerchantRepositoryImpl(MongoMerchantRepository mongoMerchantRepository) {
        this.merchantRepository = mongoMerchantRepository;
    }

    @Override
    public Merchant findByName(MerchantName name) {
        return merchantRepository.findByName(name);
    }
}
