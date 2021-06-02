package com.olivier.jasmedapp.presenter;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.pdf.PdfRenderer;
import android.net.Uri;
import android.os.Environment;
import androidx.annotation.RawRes;
import com.olivier.jasmedapp.R;
import com.olivier.jasmedapp.contracts.RegulaminFragmentContract;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static com.facebook.FacebookSdk.getCacheDir;

public class RegulaminFragmentPresenter extends BasePresenter<RegulaminFragmentContract.View> implements RegulaminFragmentContract.Presenter{

    public RegulaminFragmentPresenter() {
    }

    @Override
    public void openPdfWithAndroidSDK() {
        /** PDF reader code */

        //url to pdf file
        String path = "https://cdn.runmageddon.pl/2020-02/regulamin_bieg_w_runmageddon_01_2020.pdf";
        String url = "https://drive.google.com/viewerng/viewer?embedded=true&url=" + path;
        view.setPdfView(url);
    }

}
