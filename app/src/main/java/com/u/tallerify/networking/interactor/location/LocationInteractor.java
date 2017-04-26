package com.u.tallerify.networking.interactor.location;

import android.Manifest;
import android.support.annotation.NonNull;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceLikelihoodBuffer;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.u.tallerify.networking.interactor.Interactors;
import com.u.tallerify.utils.RouterInteractor;
import javax.annotation.Nullable;
import pl.charmas.android.reactivelocation.ReactiveLocationProvider;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;

/**
 * Created by saguilera on 4/5/17.
 */

public class LocationInteractor {

    private static final @NonNull LocationInteractor instance = new LocationInteractor();

    @NonNull BehaviorSubject<String> locationSubject;

    @Nullable Subscription providerSubscription;

    private LocationInteractor() {
        locationSubject = BehaviorSubject.create();
    }

    public static @NonNull LocationInteractor instance() {
        return instance;
    }

    public @NonNull Observable<String> observeLocations() {
        startObservingLocations();

        return locationSubject;
    }

    private void startObservingLocations() {
        if (providerSubscription != null && !providerSubscription.isUnsubscribed()) {
            providerSubscription.unsubscribe();
        }

        new RxPermissions(RouterInteractor.instance().mainRouter().getActivity())
            .request(Manifest.permission.ACCESS_FINE_LOCATION)
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .subscribe(new Action1<Boolean>() {
                @Override
                public void call(final Boolean granted) {
                    if (granted) {
                        providerSubscription = new ReactiveLocationProvider(
                                RouterInteractor.instance().mainRouter()
                                    .getActivity().getApplicationContext())
                            .getCurrentPlace(null)
                            .filter(new Func1<PlaceLikelihoodBuffer, Boolean>() {
                                @Override
                                public Boolean call(final PlaceLikelihoodBuffer placeLikelihoods) {
                                    boolean release = !placeLikelihoods.getStatus().isSuccess();

                                    if (release) {
                                        placeLikelihoods.release();
                                    }

                                    return !release;
                                }
                            })
                            .map(new Func1<PlaceLikelihoodBuffer, String>() {
                                @Override
                                public String call(final PlaceLikelihoodBuffer placeLikelihoods) {
                                    Place place = placeLikelihoods.get(0).getPlace();
                                    String address = place.getAddress().toString();
                                    placeLikelihoods.release();

                                    return address;
                                }
                            })
                            .subscribe(new Action1<String>() {
                                @Override
                                public void call(final String s) {
                                    locationSubject.onNext(s);
                                }
                            });
                    }
                }
            }, Interactors.ACTION_ERROR);
    }

}
