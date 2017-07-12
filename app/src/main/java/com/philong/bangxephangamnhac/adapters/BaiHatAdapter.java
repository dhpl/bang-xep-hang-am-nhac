package com.philong.bangxephangamnhac.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.philong.bangxephangamnhac.R;
import com.philong.bangxephangamnhac.models.BaiHat;
import com.philong.bangxephangamnhac.utils.MediaPlayerService;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Long on 6/15/2017.
 */

public class BaiHatAdapter extends RecyclerView.Adapter<BaiHatAdapter.BaiHatViewHolder> {

    private String mLinkBaiHat;
    private List<BaiHat> mBaiHatList;
    private Context mContext;



    public BaiHatAdapter(List<BaiHat> baiHatList, Context context) {
        mBaiHatList = baiHatList;
        mContext = context;
    }



    @Override
    public BaiHatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_nhac, parent, false);
        return new BaiHatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BaiHatViewHolder holder, int position) {
        final BaiHat baiHat = mBaiHatList.get(position);
        holder.txtTenCaSi.setText(baiHat.getCaSi());
        holder.txtTenBaiHat.setText(baiHat.getTen());
        if(baiHat.getLinkHinh() == null || baiHat.getLinkHinh().equals("")){
            Picasso.with(mContext).load(R.drawable.placeholder).into(holder.imgHinh);
        }else{
            Picasso.with(mContext).load(baiHat.getLinkHinh()).error(R.drawable.placeholder).into(holder.imgHinh);
        }
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    System.out.println(baiHat.getLink());
                    mLinkBaiHat = new GetLinkBaiHat().execute(baiHat.getLink()).get();
                    System.out.println("Link: " + mLinkBaiHat);
                    Intent intent = new Intent(mContext, MediaPlayerService.class);
                    intent.putExtra("url", mLinkBaiHat);
                    Toast.makeText(mContext, baiHat.getTen(), Toast.LENGTH_SHORT).show();
                    mContext.startService(intent);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBaiHatList.size();
    }


    public class BaiHatViewHolder extends RecyclerView.ViewHolder{

        public TextView txtTenBaiHat;
        public TextView txtTenCaSi;
        public CardView cardView;
        public ImageView imgHinh;

        public BaiHatViewHolder(View itemView) {
            super(itemView);
            txtTenBaiHat = (TextView) itemView.findViewById(R.id.txtTenBaiHat);
            txtTenCaSi = (TextView) itemView.findViewById(R.id.txtTenCaSi);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
            imgHinh = (ImageView) itemView.findViewById(R.id.imgHinh);
        }
    }

    public class GetLinkBaiHat extends AsyncTask<String, Void, String>{


        @Override
        protected String doInBackground(String... params) {
            try {
                Document doc = Jsoup.connect(params[0]).get();
                String regex = "http://.*\\.mp3";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(doc.html());
                if(matcher.find()){
                    return matcher.group(0);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mLinkBaiHat = s;
        }
    }

}
