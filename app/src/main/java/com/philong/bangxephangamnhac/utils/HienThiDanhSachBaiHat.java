package com.philong.bangxephangamnhac.utils;

import android.os.AsyncTask;

import com.philong.bangxephangamnhac.models.BaiHat;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Long on 6/15/2017.
 */

public class HienThiDanhSachBaiHat extends AsyncTask<String, Void, List<BaiHat>> {

    public AsyncRespone delegate = null;

    @Override
    protected List<BaiHat> doInBackground(String... params) {
        try {
            List<BaiHat> baiHatList = new ArrayList<>();
            Document doc = Jsoup.connect(params[0]).get();
            Elements elements = doc.select("div[class=h-center] div[class=list-r list-1]");
            for(Element element : elements){
                String linkHinh = element.select("div[class=texte1x]").select("img").attr("src");
                String linkBaiHat = element.select("div[class=text2 text2x]").select("a").attr("href");
                String tenBaiHat = element.select("div[class=text2 text2x]").select("a").text();
                String tenCaSi = element.select("div[class=text2 text2x]").select("p").text();
                baiHatList.add(new BaiHat(tenBaiHat, tenCaSi, linkBaiHat, linkHinh));
            }
            return baiHatList;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(List<BaiHat> baiHats) {
        super.onPostExecute(baiHats);
        delegate.processFinish(baiHats);
    }
}
