package com.shift.link.sdk.ui.presenters.loanapplication;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;

import com.shift.link.sdk.api.utils.loanapplication.LoanApplicationActionId;
import com.shift.link.sdk.api.vos.responses.loanapplication.LoanApplicationActionVo;
import com.shift.link.sdk.ui.R;
import com.shift.link.sdk.ui.models.loanapplication.documents.AddBankStatementModel;
import com.shift.link.sdk.ui.models.loanapplication.documents.AddDocumentModel;
import com.shift.link.sdk.ui.models.loanapplication.documents.AddDocumentsListModel;
import com.shift.link.sdk.ui.models.loanapplication.documents.AddOtherDocumentModel;
import com.shift.link.sdk.ui.models.loanapplication.documents.AddPhotoIdModel;
import com.shift.link.sdk.ui.models.loanapplication.documents.AddProofOfAddressModel;
import com.shift.link.sdk.ui.presenters.ActivityPresenter;
import com.shift.link.sdk.ui.presenters.Presenter;
import com.shift.link.sdk.ui.storages.LoanStorage;
import com.shift.link.sdk.ui.views.loanapplication.AddDocumentsListView;
import com.shift.link.sdk.ui.vos.DocumentVo;

import me.ledge.common.utils.android.AndroidUtils;

/**
 * Concrete {@link Presenter} for the add documents screen.
 * @author Wijnand
 */
public class AddDocumentsListPresenter
        extends ActivityPresenter<AddDocumentsListModel, AddDocumentsListView>
        implements Presenter<AddDocumentsListModel, AddDocumentsListView>, AddDocumentsListView.ViewListener {

    private static final int PICK_FILE_REQUEST_CODE = 9876;

    private AndroidUtils mUtil;
    private BottomSheetBehavior mBehavior;
    private AddDocumentModel mTargetModel;
    private AddDocumentsListDelegate mDelegate;

    /**
     * Creates a new {@link AddDocumentsListPresenter} instance.
     * @param activity Activity.
     */
    public AddDocumentsListPresenter(AppCompatActivity activity, AddDocumentsListDelegate delegate) {
        super(activity);
        mDelegate = delegate;
    }

    /** {@inheritDoc} */
    @Override
    protected void init() {
        super.init();
        mUtil = new AndroidUtils();
    }

    /**
     * Tries to launch the file browser.
     */
    private void launchFileBrowser() {
        try {
            Intent fileIntent = new Intent(Intent.ACTION_GET_CONTENT);
            fileIntent.setType("*/*");
            fileIntent.addCategory(Intent.CATEGORY_OPENABLE);
            fileIntent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);

            if (mUtil.isDeviceApiCompatible(Build.VERSION_CODES.JELLY_BEAN_MR2)) {
                fileIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            }

            mActivity.startActivityForResult(
                    Intent.createChooser(fileIntent, mActivity.getString(R.string.add_documents_library_chooser_title)),
                    PICK_FILE_REQUEST_CODE);
        } catch (ActivityNotFoundException anfe) {
            mView.displayErrorMessage(mActivity.getString(R.string.add_documents_bottom_sheet_library_error));
        }
    }

    /**
     * Adds the selected files to the target {@link AddDocumentModel}.
     * @param data The data received from the file picker Activity.
     */
    private void addFiles(Intent data) {
        if (mTargetModel == null) {
            return;
        }

        ContentResolver resolver = mActivity.getContentResolver();

        if (mUtil.isDeviceApiCompatible(Build.VERSION_CODES.JELLY_BEAN) && data.getClipData() != null) {
            // Multiple files.
            ClipData clipData = data.getClipData();
            ClipData.Item item;

            Uri file;
            String mimeType;

            for (int i = 0; i < clipData.getItemCount(); i++) {
                item = clipData.getItemAt(i);
                file = item.getUri();
                mimeType = resolver.getType(file);

                mTargetModel.addDocument(new DocumentVo(file, mimeType));
            }
        } else if (data.getData() != null) {
            // Single file.
            Uri file = data.getData();
            // Type isn't always set on the Intent. Using the ContentResolver instead.
            String mimeType = resolver.getType(file);

            mTargetModel.addDocument(new DocumentVo(file, mimeType));
        }

        mView.refresh();
    }

    /**
     * Parses the received Activity result.
     * @param requestCode Request code.
     * @param resultCode Result code.
     * @param data Result data.
     */
    public void handleActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null || resultCode != Activity.RESULT_OK) {
            mTargetModel = null;
            return;
        }

        switch (requestCode) {
            case PICK_FILE_REQUEST_CODE:
                addFiles(data);
                break;
            default:
                // Do nothing.
                break;
        }

        mTargetModel = null;
    }

    /** {@inheritDoc} */
    @Override
    public AddDocumentsListModel createModel() {
        return new AddDocumentsListModel(LoanStorage.getInstance().getCurrentLoanApplication());
    }

    /** {@inheritDoc} */
    @Override
    protected void setupToolbar() {
        initToolbar();
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

    @Override
    public void onBack() {
        mDelegate.addDocumentsListShowPrevious(mModel);
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

    /** {@inheritDoc}
     * @param model*/
    @Override
    public void cardClickHandler(AddDocumentModel model) {
        mTargetModel = model;
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
        launchFileBrowser();
    }

    /** {@inheritDoc} */
    @Override
    public void doneClickHandler() {
        mDelegate.addDocumentsListShowNext(mModel);
    }
}
