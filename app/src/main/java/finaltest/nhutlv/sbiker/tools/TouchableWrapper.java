package finaltest.nhutlv.sbiker.tools;

import android.content.Context;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import finaltest.nhutlv.sbiker.activities.RegisterRepairActivity;

public class TouchableWrapper extends FrameLayout {

    public TouchableWrapper(Context context) {
        super(context);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                RegisterRepairActivity.mMapIsTouched = true;
                break;

            case MotionEvent.ACTION_UP:
                RegisterRepairActivity.mMapIsTouched = false;
                break;
        }
        return super.dispatchTouchEvent(event);
    }
}