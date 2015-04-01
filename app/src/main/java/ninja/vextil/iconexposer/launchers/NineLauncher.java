package ninja.vextil.iconexposer.launchers;

import android.content.Context;
import android.content.Intent;

public class NineLauncher
{
    public NineLauncher(Context context)
    {
        Intent nineApply = context.getPackageManager().getLaunchIntentForPackage("com.gidappsinc.launcher");
        Intent nine = new Intent("com.gridappsinc.launcher.action.THEME");
        nine.putExtra("iconpkg", context.getPackageName());
        nine.putExtra("launch", true);
        context.sendBroadcast(nine);
        context.startActivity(nineApply);
    }
}
