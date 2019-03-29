package com.tronlink.core.wallet;

import com.tronlink.core.common.net.WalletUtils;

public class CreatWalletDemo {

    public void creat() {
        String name = "TronLink";
        String password = "12345678";
        try {
            Wallet wallet = new Wallet(true);
            if (wallet != null) {
                wallet.setWalletName(name);
                wallet.setCreateTime(System.currentTimeMillis());
                WalletUtils.saveWallet(wallet, password);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
