package com.aptopayments.core.data.user

import com.aptopayments.core.data.geo.Country

internal class IdDataPointConfiguration(val allowedDocumentTypes: Map<Country, List<IdDocumentDataPoint.Type>>) :
    DataPointConfiguration
