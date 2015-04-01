package ninja.vextil.iconexposer.launchers;

import android.content.Context;
import android.content.Intent;

public class LucidLauncher
{
    public LucidLauncher(Context context)
    {
        Intent lucidApply = context.getPackageManager().getLaunchIntentForPackage("com.powerpoint45.launcher");
        Intent lucid = new Intent("com.powerpoint45.action.APPLY_THEME");
        lucid.putExtra("icontheme", context.getPackageName());
        context.sendBroadcast(lucid);
        context.startActivity(lucidApply);
    }
}
