package com.aptopayments.mobile.data.user

import com.aptopayments.mobile.data.geo.Country
import java.io.Serializable

class IdDataPointConfiguration(val allowedDocumentTypes: Map<Country, List<IdDocumentDataPoint.Type>>) :
    DataPointConfiguration, Serializable
