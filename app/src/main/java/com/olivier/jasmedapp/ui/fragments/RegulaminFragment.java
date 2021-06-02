package com.olivier.jasmedapp.ui.fragments;

import android.content.Intent;
import android.graphics.pdf.PdfRenderer;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.github.barteksc.pdfviewer.PDFView;
import com.olivier.jasmedapp.R;
import com.olivier.jasmedapp.contracts.RegulaminFragmentContract;
import com.olivier.jasmedapp.presenter.RegulaminFragmentPresenter;
import org.jetbrains.annotations.NotNull;

public class RegulaminFragment extends Fragment implements RegulaminFragmentContract.View {

    private RegulaminFragmentPresenter mRegulaminFragmentPresenter;
    private WebView webView;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRegulaminFragmentPresenter = new RegulaminFragmentPresenter();
        mRegulaminFragmentPresenter.attach(this);
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.regulamin_layout, container, false);

        webView = rootView.findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        mRegulaminFragmentPresenter.openPdfWithAndroidSDK();
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void setPdfView(String url) {
        webView.loadUrl(url);
    }
}
