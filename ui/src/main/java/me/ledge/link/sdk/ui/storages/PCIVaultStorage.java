package me.ledge.link.sdk.ui.storages;

import android.content.Context;

import com.verygoodsecurity.vaultsdk.VaultAPI;
import com.verygoodsecurity.vaultsdk.VaultAPIUICallback;

import java.net.MalformedURLException;
import java.net.URL;

import me.ledge.link.sdk.ui.R;

/**
 * Stores card related data.
 * @author Adrian
 */
public class PCIVaultStorage {

    private static PCIVaultStorage mInstance;

    /**
     * Creates a new {@link PCIVaultStorage} instance.
     */
    private PCIVaultStorage() { }

    /**
     * @return The single instance of this class.
     */
    public static synchronized PCIVaultStorage getInstance() {
        if (mInstance == null) {
            mInstance = new PCIVaultStorage();
        }

        return mInstance;
    }

    public void storeData(Context context, String sensitiveData, VaultAPIUICallback callback) throws MalformedURLException {
        URL baseUrl = new URL(context.getString(R.string.vgs_vault_url));
        VaultAPI vault = new VaultAPI(baseUrl, context.getString(R.string.vgs_publishable_key));
        vault.createToken(sensitiveData, callback);
    }
}
