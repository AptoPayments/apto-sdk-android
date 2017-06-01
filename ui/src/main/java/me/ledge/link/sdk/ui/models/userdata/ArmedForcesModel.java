package me.ledge.link.sdk.ui.models.userdata;

import me.ledge.link.api.vos.datapoints.DataPointList;
import me.ledge.link.api.vos.datapoints.DataPointVo;
import me.ledge.link.api.vos.datapoints.ArmedForces;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.models.Model;

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