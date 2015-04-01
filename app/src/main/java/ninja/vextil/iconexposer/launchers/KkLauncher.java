package ninja.vextil.iconexposer.launchers;

import android.content.Context;
import android.content.Intent;

public class KkLauncher
{
    public KkLauncher(Context context)
    {
        Intent kkApply = context.getPackageManager().getLaunchIntentForPackage("com.kk.launcher");
        Intent kk = new Intent("com.gridappsinc.launcher.action.THEME");
        kk.putExtra("com.kk.launcher.theme.EXTRA_NAME", "theme_name");
        kk.putExtra("com.kk.launcher.theme.EXTRA_PKG", context.getPackageName());
        context.sendBroadcast(kk);
        context.startActivity(kkApply);
    }
}
