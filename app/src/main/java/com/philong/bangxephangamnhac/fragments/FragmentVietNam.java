package com.philong.bangxephangamnhac.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.philong.bangxephangamnhac.R;
import com.philong.bangxephangamnhac.adapters.BaiHatAdapter;
import com.philong.bangxephangamnhac.models.BaiHat;
import com.philong.bangxephangamnhac.utils.AsyncRespone;
import com.philong.bangxephangamnhac.utils.HienThiDanhSachBaiHat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Long on 6/15/2017.
 */

public class FragmentVietNam extends Fragment implements AsyncRespone{

    private static final String NHAC_VIET = "http://chiasenhac.vn/mp3/vietnam/";
    public HienThiDanhSachBaiHat mHienThiDanhSachBaiHat;

    private RecyclerView mRecyclerViewNhacViet;
    private BaiHatAdapter mBaiHatAdapter;
    private List<BaiHat> mBaiHats;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_viet_nam, container, false);

        mHienThiDanhSachBaiHat = new HienThiDanhSachBaiHat();
        mHienThiDanhSachBaiHat.delegate = this;
        mHienThiDanhSachBaiHat.execute(NHAC_VIET);
        mBaiHats = new ArrayList<>();
        mRecyclerViewNhacViet = (RecyclerView) view.findViewById(R.id.recyclerViewVietNam);

        mRecyclerViewNhacViet.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerViewNhacViet.setHasFixedSize(true);
        mRecyclerViewNhacViet.setNestedScrollingEnabled(false);
        mRecyclerViewNhacViet.setItemAnimator(new DefaultItemAnimator());

        return view;
    }

    @Override
    public void processFinish(List<BaiHat> output) {
        if(!mBaiHats.isEmpty()){
            mBaiHats.clear();
        }
        output.subList(19, output.size()).clear();
        mBaiHats = output;
        mBaiHatAdapter = new BaiHatAdapter(mBaiHats, getContext());
        mRecyclerViewNhacViet.setAdapter(mBaiHatAdapter);
        mBaiHatAdapter.notifyDataSetChanged();
    }
}
