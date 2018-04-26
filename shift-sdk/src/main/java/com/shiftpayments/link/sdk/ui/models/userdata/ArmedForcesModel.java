package com.shiftpayments.link.sdk.ui.models.userdata;

import com.shiftpayments.link.sdk.api.vos.datapoints.ArmedForces;
import com.shiftpayments.link.sdk.api.vos.datapoints.DataPointList;
import com.shiftpayments.link.sdk.api.vos.datapoints.DataPointVo;
import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.models.Model;

/**
 * Concrete {@link Model} for the member of the armed forces screen.
 * @author Adrian
 */
public class ArmedForcesModel extends AbstractUserDataModel {

    private ArmedForces mArmedForces;

    public ArmedForcesModel() {
        super();
        init();
    }

    private void init() {
        mArmedForces = new ArmedForces();
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasAllData() {
        return mArmedForces.isMemberOfArmedForces!=null;
    }

    /** {@inheritDoc} */
    @Override
    public int getActivityTitleResource() {
        return R.string.payday_loan_label;
    }

    /** {@inheritDoc} */
    @Override
    public DataPointList getBaseData() {
        DataPointList base = super.getBaseData();
        ArmedForces baseArmedForces = (ArmedForces) base.getUniqueDataPoint(
                DataPointVo.DataPointType.MemberOfArmedForces, new ArmedForces());
        baseArmedForces.isMemberOfArmedForces = this.isMemberOfArmedForces();
        return base;
    }

    /** {@inheritDoc} */
    @Override
    public void setBaseData(DataPointList base) {
        super.setBaseData(base);
        ArmedForces baseArmedForces = (ArmedForces) base.getUniqueDataPoint(
                DataPointVo.DataPointType.MemberOfArmedForces, null);
        if(baseArmedForces!=null) {
            setArmedForces(baseArmedForces.isMemberOfArmedForces);
        }
    }

    public Boolean isMemberOfArmedForces() {
        return mArmedForces.isMemberOfArmedForces;
    }

    public void setArmedForces(Boolean isMemberOfArmedForces) {
        mArmedForces.isMemberOfArmedForces = isMemberOfArmedForces;
    }
}
