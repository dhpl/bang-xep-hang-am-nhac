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
import com.philong.bangxephangamnhac.utils.HienThiDanhSachBaiHat2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Long on 6/15/2017.
 */

public class FragmentHoa extends Fragment implements AsyncRespone{

    private static final String NHAC_HOA = "http://chiasenhac.vn/mp3/chinese/";

    public HienThiDanhSachBaiHat2 mHienThiDanhSachBaiHat;

    private RecyclerView mRecyclerViewNhacHoa;
    private BaiHatAdapter mBaiHatAdapter;
    private List<BaiHat> mBaiHats;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hoa, container, false);
        mHienThiDanhSachBaiHat = new HienThiDanhSachBaiHat2();
        mHienThiDanhSachBaiHat.delegate = this;
        mHienThiDanhSachBaiHat.execute(NHAC_HOA);
        mBaiHats = new ArrayList<>();
        mRecyclerViewNhacHoa = (RecyclerView) view.findViewById(R.id.recyclerViewHoa);

        mRecyclerViewNhacHoa.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerViewNhacHoa.setHasFixedSize(true);
        mRecyclerViewNhacHoa.setNestedScrollingEnabled(false);
        mRecyclerViewNhacHoa.setItemAnimator(new DefaultItemAnimator());
        return view;
    }

    @Override
    public void processFinish(List<BaiHat> output) {
        if(!mBaiHats.isEmpty()){
            mBaiHats.clear();
        }
        mBaiHats = output;
        mBaiHatAdapter = new BaiHatAdapter(mBaiHats, getContext());
        mRecyclerViewNhacHoa.setAdapter(mBaiHatAdapter);
        mBaiHatAdapter.notifyDataSetChanged();
    }
}
