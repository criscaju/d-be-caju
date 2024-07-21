package com.caju.desafio.domain.merchants;

public interface IMerchantRepository {
    Merchant findByName(MerchantName name);
}
