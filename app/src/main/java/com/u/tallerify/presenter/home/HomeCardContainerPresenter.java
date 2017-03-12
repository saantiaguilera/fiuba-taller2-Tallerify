package com.u.tallerify.presenter.home;

import android.support.annotation.NonNull;
import com.google.gson.Gson;
import com.u.tallerify.contract.home.HomeCardContainerContract;
import com.u.tallerify.model.entity.Song;
import com.u.tallerify.supplier.home.card.SongCardSupplier;
import com.u.tallerify.supplier.home.card.TrendingSongsCardSupplier;
import com.u.tallerify.utils.adapter.GenericAdapter;
import com.u.tallerify.supplier.home.card.HeaderCardSupplier;
import com.u.tallerify.supplier.home.card.NoAccountCardSupplier;
import com.u.tallerify.presenter.Presenter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by saguilera on 3/12/17.
 */
public class HomeCardContainerPresenter extends Presenter<HomeCardContainerContract.View>
        implements HomeCardContainerContract.Presenter {

    @Override
    protected void onAttach(@NonNull final HomeCardContainerContract.View view) {
        List<GenericAdapter.ItemSupplier> mocks = new ArrayList<>();
        mocks.add(new HeaderCardSupplier(getContext(), "No Account View Mock"));
        mocks.add(new NoAccountCardSupplier(getContext()));

        mocks.add(new HeaderCardSupplier(getContext(), "Songs Mock"));
        addSongToMock(mocks);
        addSongToMock(mocks);
        addSongToMock(mocks);
        addSongToMock(mocks);
        addSongToMock(mocks);
        addSongToMock(mocks);
        addSongToMock(mocks);
        addSongToMock(mocks);
        addSongToMock(mocks);
        addSongToMock(mocks);
        addSongToMock(mocks);
        addSongToMock(mocks);
        addSongToMock(mocks);
        addSongToMock(mocks);
        addSongToMock(mocks);
        addSongToMock(mocks);
        addSongToMock(mocks);
        addSongToMock(mocks);
        addSongToMock(mocks);
        addSongToMock(mocks);
        addSongToMock(mocks);
        addSongToMock(mocks);
        addSongToMock(mocks);
        addSongToMock(mocks);
        addSongToMock(mocks);
        addSongToMock(mocks);
        addSongToMock(mocks);
        addSongToMock(mocks);
        addSongToMock(mocks);
        addSongToMock(mocks);
        addSongToMock(mocks);
        addSongToMock(mocks);
        addSongToMock(mocks);
        addSongToMock(mocks);

        mocks.add(new HeaderCardSupplier(getContext(), "Trending Songs Mock"));

        List<GenericAdapter.ItemSupplier> trendingMocks = new ArrayList<>();
        addSongToMock(trendingMocks);
        addSongToMock(trendingMocks);
        addSongToMock(trendingMocks);
        addSongToMock(trendingMocks);
        addSongToMock(trendingMocks);
        addSongToMock(trendingMocks);
        addSongToMock(trendingMocks);
        addSongToMock(trendingMocks);
        addSongToMock(trendingMocks);
        addSongToMock(trendingMocks);
        addSongToMock(trendingMocks);
        addSongToMock(trendingMocks);
        addSongToMock(trendingMocks);
        addSongToMock(trendingMocks);
        addSongToMock(trendingMocks);
        addSongToMock(trendingMocks);
        addSongToMock(trendingMocks);
        addSongToMock(trendingMocks);
        addSongToMock(trendingMocks);
        addSongToMock(trendingMocks);
        addSongToMock(trendingMocks);
        addSongToMock(trendingMocks);
        addSongToMock(trendingMocks);
        addSongToMock(trendingMocks);
        addSongToMock(trendingMocks);
        mocks.add(new TrendingSongsCardSupplier(getContext(), trendingMocks));

        view.setData(mocks);
    }

    private void addSongToMock(List<GenericAdapter.ItemSupplier> list) {
        list.add(new SongCardSupplier(getContext(), new Gson().fromJson(json, Song.class)));
    }

    private static final String json = "\n" +
        "{\n" +
        "  \"id\": 0,\n" +
        "  \"url\": \"heroku-endpoint.com/music/hash_of_song.mp3\",\n" +
        "  \"name\": \"Track 01\",\n" +
        "  \"album\": {\n" +
        "    \"id\": 0,\n" +
        "    \"picture\": {\n" +
        "      \"large\": \"http://direct.rhapsody.com/imageserver/images/Alb.132677771/500x500.jpg\",\n" +
        "      \"medium\": \"http://direct.rhapsody.com/imageserver/images/Alb.132677771/500x500.jpg\",\n" +
        "      \"thumb\": \"http://direct.rhapsody.com/imageserver/images/Alb.132677771/500x500.jpg\"\n" +
        "    },\n" +
        "    \"name\": \"Como lo hice yo\",\n" +
        "    \"artist\": {\n" +
        "      \"id\": 0,\n" +
        "      \"picture\": {\n" +
        "        \"large\": \"https://pbs.twimg.com/profile_images/1137553897/32442_458292663355_312742963355_6240333_3928282_n_400x400.jpg\",\n" +
        "        \"medium\": \"https://pbs.twimg.com/profile_images/1137553897/32442_458292663355_312742963355_6240333_3928282_n_400x400.jpg\",\n" +
        "        \"thumb\": \"https://pbs.twimg.com/profile_images/1137553897/32442_458292663355_312742963355_6240333_3928282_n_400x400.jpg\"\n" +
        "      },\n" +
        "      \"name\": \"Ricardo Dios Fort\"\n" +
        "    }\n" +
        "  }\n" +
        "}";

}
