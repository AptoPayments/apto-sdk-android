package me.ledge.link.sdk.sdk.storages;

import me.ledge.link.api.vos.responses.config.ProductDisclaimerListVo;

/**
 * Delegation interface for accessing the partner disclaimers list stored in the Config Storage.
 *
 * @author Adrian
 */
public interface PartnerDisclaimersDelegate extends ErrorDelegate {

    void partnerDisclaimersListRetrieved(ProductDisclaimerListVo partnerDisclaimerList);

}
