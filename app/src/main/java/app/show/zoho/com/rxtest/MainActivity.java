package app.show.zoho.com.rxtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;


import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function3;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Observable.zip(createObervable(1000), createObervable(2000), createObervable(3000), new Function3<Integer, Integer, Integer, Object>() {
            @Override
            public Object apply(Integer integer, Integer integer2, Integer integer3) throws Exception {
                return null;
            }
        }).observeOn(Schedulers.io()).subscribeOn(Schedulers.io()).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                Log.e("accept"," ");
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.e("error"," "+throwable.getLocalizedMessage());
            }
        }, new Action() {
            @Override
            public void run() throws Exception {
                Log.e("completed"," ");
            }
        });
    }

    public Observable<Integer> createObervable(final long time){
        return Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(returnInt(time));
            }
        });
    }

    int returnInt(long time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(time>0) {
            throw new RuntimeException("throw "+time);
        }
        return 1;
    }
}
