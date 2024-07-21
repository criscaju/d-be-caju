package com.caju.desafio.application.merchants;

import com.caju.desafio.domain.merchants.Mcc;
import com.caju.desafio.domain.merchants.Merchant;
import com.caju.desafio.domain.merchants.MerchantName;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class MerchantService {

    public List<Merchant> getMerchantInitialLoad() {
        var m1 = Merchant.createMerchant(new MerchantName("UBER TRIP                   SAO PAULO BR"),
                new Mcc("6000"));

        var m2 = Merchant.createMerchant(new MerchantName("UBER EATS                   SAO PAULO BR"),
                new Mcc("5411"));

        var m3 = Merchant.createMerchant(new MerchantName("PAG*JoseDaSilva          RIO DE JANEI BR"),
                new Mcc("5811"));

        var m4 = Merchant.createMerchant(new MerchantName("PICPAY*BILHETEUNICO           GOIANIA BR"),
                new Mcc("6000"));

        return Arrays.asList(m1, m2, m3, m4);
    }
}
