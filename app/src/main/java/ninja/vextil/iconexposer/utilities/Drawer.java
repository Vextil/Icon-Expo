package ninja.vextil.iconexposer.utilities;

import android.app.Activity;

public class Drawer extends com.mikepenz.materialdrawer.Drawer
{
    private Activity activity;

    @Override
    public com.mikepenz.materialdrawer.Drawer withActivity(Activity activity)
    {
        super.withActivity(activity);
        this.activity = activity;
        return this;
    }

    public Activity getActivity()
    {
        return activity;
    }
}
