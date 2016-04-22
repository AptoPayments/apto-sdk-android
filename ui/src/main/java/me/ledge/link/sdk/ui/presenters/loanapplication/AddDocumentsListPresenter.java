package me.ledge.link.sdk.ui.presenters.loanapplication;

import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import me.ledge.link.api.utils.loanapplication.LoanApplicationActionId;
import me.ledge.link.api.vos.responses.loanapplication.LoanApplicationActionVo;
import me.ledge.link.sdk.ui.models.loanapplication.documents.AddBankStatementModel;
import me.ledge.link.sdk.ui.models.loanapplication.documents.AddDocumentModel;
import me.ledge.link.sdk.ui.models.loanapplication.documents.AddDocumentsListModel;
import me.ledge.link.sdk.ui.models.loanapplication.documents.AddOtherDocumentModel;
import me.ledge.link.sdk.ui.models.loanapplication.documents.AddPhotoIdModel;
import me.ledge.link.sdk.ui.models.loanapplication.documents.AddProofOfAddressModel;
import me.ledge.link.sdk.ui.presenters.ActivityPresenter;
import me.ledge.link.sdk.ui.presenters.Presenter;
import me.ledge.link.sdk.ui.storages.LoanStorage;
import me.ledge.link.sdk.ui.views.loanapplication.AddDocumentsListView;

/**
 * Concrete {@link Presenter} for the add documents screen.
 * @author Wijnand
 */
public class AddDocumentsListPresenter
        extends ActivityPresenter<AddDocumentsListModel, AddDocumentsListView>
        implements Presenter<AddDocumentsListModel, AddDocumentsListView>, AddDocumentsListView.ViewListener {

    private BottomSheetBehavior mBehavior;

    /**
     * Creates a new {@link AddDocumentsListPresenter} instance.
     * @param activity Activity.
     */
    public AddDocumentsListPresenter(AppCompatActivity activity) {
        super(activity);
    }

    /** {@inheritDoc} */
    @Override
    public AddDocumentsListModel createModel() {
        return new AddDocumentsListModel(LoanStorage.getInstance().getCurrentLoanApplication());
    }

    /** {@inheritDoc} */
    @Override
    public void attachView(AddDocumentsListView view) {
        super.attachView(view);

        AddDocumentModel[] viewData = createViewData(mModel.getRequiredActions());
        mView.setViewListener(this);
        mView.setData(viewData);

        mBehavior = BottomSheetBehavior.from(mView.getBottomSheet());
    }

    /** {@inheritDoc} */
    @Override
    public void detachView() {
        mView.setViewListener(null);
        super.detachView();
    }

    /**
     * @param actionsList Raw list of actions.
     * @return The list of {@link AddDocumentModel}s to display.
     */
    public AddDocumentModel[] createViewData(LoanApplicationActionVo[] actionsList) {
        if (actionsList == null || actionsList.length <= 0) {
            return null;
        }

        AddDocumentModel[] data = new AddDocumentModel[actionsList.length];
        LoanApplicationActionVo action;

        for (int i = 0; i < actionsList.length; i++) {
            action = actionsList[i];

            switch (action.action) {
                case LoanApplicationActionId.UPLOAD_BANK_STATEMENT:
                    data[i] = new AddBankStatementModel(action);
                    break;
                case LoanApplicationActionId.UPLOAD_PHOTO_ID:
                    data[i] = new AddPhotoIdModel(action);
                    break;
                case LoanApplicationActionId.UPLOAD_PROOF_OF_ADDRESS:
                    data[i] = new AddProofOfAddressModel(action);
                    break;
                default:
                    data[i] = new AddOtherDocumentModel(action);
                    break;
            }
        }

        return data;
    }

    /** {@inheritDoc} */
    @Override
    public void cardClickHandler() {
        mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    /** {@inheritDoc} */
    @Override
    public void photoClickHandler() {
        mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

    /** {@inheritDoc} */
    @Override
    public void libraryClickHandler() {
        mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

    /** {@inheritDoc} */
    @Override
    public void doneClickHandler() {
        startNextActivity();
    }
}
