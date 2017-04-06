package finaltest.nhutlv.sbiker.utils;

/**
 * Created by NhutDu on 21/02/2017.
 */

public interface Callback<T> {

    void onResult(T t);

    void onFailure();

}
