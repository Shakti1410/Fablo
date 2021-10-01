package com.fabloplatforms.business.onboard.bottomsheet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fabloplatforms.business.databinding.SelectDocTypeBottomSheetBinding;

import com.fabloplatforms.business.onboard.model.AadhaarBackDocType;
import com.fabloplatforms.business.onboard.model.AadhaarFrontDocType;
import com.fabloplatforms.business.onboard.model.BankDocType;
import com.fabloplatforms.business.onboard.model.FssaiDocType;
import com.fabloplatforms.business.onboard.model.GstDocType;
import com.fabloplatforms.business.onboard.model.PanDocType;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

public class SelectDocTypeBottomSheet extends BottomSheetDialogFragment implements View.OnClickListener {
    SelectDocTypeBottomSheetBinding binding;
    String docType="";

    @Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = SelectDocTypeBottomSheetBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        initView();
        return view;
    }

    private void initView() {
        Bundle mArgs = getArguments();
        docType = mArgs.getString("docType");
        binding.tvDoc.setOnClickListener(this);
        binding.tvImage.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == binding.tvDoc) {
            setDoc();

        }
        if (v == binding.tvImage) {
            setImage();
        }
    }

    private void setImage() {
        if(docType.equals("BANK"))
        {
            BankDocType type = new BankDocType();
            type.setType("Image");
            EventBus.getDefault().post(type);
        }
        if(docType.equals("PAN"))
        {
            PanDocType type = new PanDocType();
            type.setType("Image");
            EventBus.getDefault().post(type);
        }
        if(docType.equals("GST"))
        {
            GstDocType type = new GstDocType();
            type.setType("Image");
            EventBus.getDefault().post(type);
        }
        if(docType.equals("FSSAI"))
        {
            FssaiDocType type = new FssaiDocType();
            type.setType("Image");
            EventBus.getDefault().post(type);
        }
        if(docType.equals("AADHAARFRONT"))
        {
            AadhaarFrontDocType type = new AadhaarFrontDocType();
            type.setType("Image");
            EventBus.getDefault().post(type);
        }
        if(docType.equals("AADHAARBACK"))
        {
            AadhaarBackDocType type = new AadhaarBackDocType();
            type.setType("Image");
            EventBus.getDefault().post(type);
        }
    }

    private void setDoc() {
        if(docType.equals("BANK"))
        {
            BankDocType type = new BankDocType();
            type.setType("Document");
            EventBus.getDefault().post(type);
        }
        if(docType.equals("PAN"))
        {
            PanDocType type = new PanDocType();
            type.setType("Document");
            EventBus.getDefault().post(type);
        }
        if(docType.equals("GST"))
        {
            GstDocType type = new GstDocType();
            type.setType("Document");
            EventBus.getDefault().post(type);
        }
        if(docType.equals("FSSAI"))
        {
            FssaiDocType type = new FssaiDocType();
            type.setType("Document");
            EventBus.getDefault().post(type);
        }
        if(docType.equals("AADHAARFRONT"))
        {
            AadhaarFrontDocType type = new AadhaarFrontDocType();
            type.setType("Document");
            EventBus.getDefault().post(type);
        }
        if(docType.equals("AADHAARBACK"))
        {
            AadhaarBackDocType type = new AadhaarBackDocType();
            type.setType("Document");
            EventBus.getDefault().post(type);
        }
    }
}
