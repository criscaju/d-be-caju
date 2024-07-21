package com.caju.desafio.domain.merchants;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("merchants")
public class Merchant {

    private MerchantId merchantId;
    private MerchantName name;
    private Mcc mcc;

    public MerchantId getMerchantId() {
        return merchantId;
    }

    private void setMerchantId(MerchantId merchantId) {
        this.merchantId = merchantId;
    }

    public MerchantName getName() {
        return name;
    }

    private void setName(MerchantName name) {
        this.name = name;
    }

    public Mcc getMcc() {
        return mcc;
    }

    private void setMcc(Mcc mcc) {
        this.mcc = mcc;
    }

    private Merchant() {

    }

    public static Merchant createMerchant(MerchantName name, Mcc mcc) {
        var merchant = new Merchant();
        merchant.setMerchantId(new MerchantId());
        merchant.setName(name);
        merchant.setMcc(mcc);
        return merchant;
    }

}
