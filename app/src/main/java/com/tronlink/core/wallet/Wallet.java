package com.tronlink.core.wallet;

import com.tronlink.core.common.StringTronUtil;
import com.tronlink.core.common.common.bip32.Bip32ECKeyPair;
import com.tronlink.core.common.common.crypto.ECKey;
import com.tronlink.core.common.common.crypto.MnemonicUtils;
import com.tronlink.core.common.common.utils.Utils;

import java.math.BigInteger;

import static com.tronlink.core.common.common.bip32.Bip32ECKeyPair.HARDENED_BIT;


public class Wallet {
    private ECKey mECKey = null;

    private boolean isWatchOnly = false;
    private String walletName = "";
    private String encPassword = "";
    public String address = "";
    private byte[] encPrivateKey;
    private byte[] publicKey;
    private String mnemonic;
    private int createType = -1;// tronconfig type
    private long createTime;


    public int getMnemonicLength() {
        return mnemonicLength;
    }

    public void setMnemonicLength(int mnemonicLength) {
        this.mnemonicLength = mnemonicLength;
    }

    private int mnemonicLength;


    public String getMnemonic() {
        return mnemonic;
    }

    public void setMnemonic(String mnemonic) {
        this.mnemonic = mnemonic;
    }


    public Wallet() {
    }

    public Wallet(boolean generateECKey) {
        if (generateECKey) {
            byte[] initialEntropy = new byte[16];
            Utils.getRandom().nextBytes(initialEntropy);

            mnemonic = MnemonicUtils.generateMnemonic(initialEntropy);
            byte[] seed = MnemonicUtils.generateSeed(mnemonic, null);

            Bip32ECKeyPair masterKeypair = Bip32ECKeyPair.generateKeyPair(seed);
            Bip32ECKeyPair bip44Keypair = generateBip44KeyPair(masterKeypair, false);
            mECKey = ECKey.fromPrivate(bip44Keypair.getPrivateKeyBytes33());
        }
    }



    public boolean isOpen() {
        return mECKey != null && mECKey.getPrivKeyBytes() != null;
    }


    public byte[] getPublicKey() {
        return mECKey != null ? mECKey.getPubKey() : publicKey;
    }

    public void setPublicKey(byte[] publicKey) {
        this.publicKey = publicKey;
    }

    public byte[] getPrivateKey() {
        return mECKey != null ? mECKey.getPrivKeyBytes() : null;
    }

    public void generateKeyForPrivateKey(String privateKey) {
        if (privateKey != null && !privateKey.isEmpty()) {
            ECKey tempKey = null;
            try {
                BigInteger priK = new BigInteger(privateKey, 16);
                tempKey = ECKey.fromPrivate(priK);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            mECKey = tempKey;
        } else {
            mECKey = null;
        }
    }

    public void generateKeyForMnemonic(String mnemonic) {
        if (mnemonic != null && !mnemonic.isEmpty()) {
            ECKey tempKey = null;
            try {
                byte[] seed = MnemonicUtils.generateSeed(mnemonic, null);
                Bip32ECKeyPair masterKeypair = Bip32ECKeyPair.generateKeyPair(seed);
                Bip32ECKeyPair bip44Keypair = generateBip44KeyPair(masterKeypair, false);
                tempKey = ECKey.fromPrivate(bip44Keypair.getPrivateKeyBytes33());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            mECKey = tempKey;
        } else {
            mECKey = null;
        }
    }

    public int getCreateType() {
        return createType;
    }

    public void setCreateType(int createType) {
        this.createType = createType;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public boolean isWatchOnly() {
        return isWatchOnly;
    }

    public void setWatchOnly(boolean watchOnly) {
        isWatchOnly = watchOnly;
    }

    public String getWalletName() {
        return walletName;
    }

    public void setWalletName(String walletName) {
        this.walletName = walletName;
    }

    public String getEncryptedPassword() {
        return encPassword;
    }

    public void setEncryptedPassword(String password) {
        this.encPassword = password;
    }

    public String getAddress() {
        if (mECKey != null) {
            return StringTronUtil.encode58Check(mECKey.getAddress());
        } else if (publicKey != null) {
            return StringTronUtil.encode58Check(ECKey.fromPublicOnly(publicKey).getAddress());
        } else {
            return address;
        }
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ECKey getECKey() {
        return mECKey;
    }

    public byte[] getEncryptedPrivateKey() {
        return encPrivateKey;
    }

    public void setEncryptedPrivateKey(byte[] encPrivateKey) {
        this.encPrivateKey = encPrivateKey;
    }

    public static Bip32ECKeyPair generateBip44KeyPair(Bip32ECKeyPair master, boolean testNet) {
        if (testNet) {
            final int[] path = {44 | HARDENED_BIT, 0 | HARDENED_BIT, 0 | HARDENED_BIT, 0};
            return Bip32ECKeyPair.deriveKeyPair(master, path);
        } else {
            final int[] path = {44 | HARDENED_BIT, 195 | HARDENED_BIT, 0 | HARDENED_BIT, 0, 0};
            return Bip32ECKeyPair.deriveKeyPair(master, path);
        }
    }


}