package com.caju.desafio.infrastructure.merchants;

import com.caju.desafio.domain.merchants.Merchant;
import com.caju.desafio.domain.merchants.MerchantId;
import com.caju.desafio.domain.merchants.MerchantName;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface MongoMerchantRepository extends MongoRepository<Merchant, MerchantId> {

    @Query("{name: ?0}")
    Merchant findByName(MerchantName name);
}
