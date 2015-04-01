package ninja.vextil.iconexposer.launchers;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class GoLauncher
{
    public GoLauncher(Context context)
    {
        Intent intent = context.getPackageManager().getLaunchIntentForPackage("com.gau.go.launcherex");
        Intent go = new Intent("com.gau.go.launcherex.MyThemes.mythemeaction");
        go.putExtra("type", 1);
        go.putExtra("pkgname", context.getPackageName());
        context.sendBroadcast(go);
        Toast.makeText(context, "Go Theme Applied!", Toast.LENGTH_SHORT).show();
        context.startActivity(intent);
    }
}
