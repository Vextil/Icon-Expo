package ninja.vextil.iconexposer.utilities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;

import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.vextil.iconexposer.R;

import java.util.ArrayList;
import java.util.List;

public class DrawerBuilder
{
    private Drawer drawer = new Drawer();
    private DrawerItem drawerItem = new DrawerItem();
    private List<DrawerItem> drawerItems = new ArrayList<>();

    public Drawer settings()
    {
        return drawer;
    }

    public DrawerBuilder item(IDrawerItem iDrawerItem)
    {
        drawerItem.iDrawerItem = iDrawerItem;
        return this;
    }

    public void doesAction(DrawerItemActionInterface itemAction)
    {
        drawerItem.itemAction = itemAction;
        addItemToList();
    }

    public void opensActivity(Class activity)
    {
        drawerItem.activity = activity;
        addItemToList();
    }

    public void opensFragment(Class fragment)
    {
        drawerItem.fragment = fragment;
        addItemToList();
    }

    public void divider()
    {
        drawerItem.iDrawerItem = new DividerDrawerItem();
        addItemToList();
    }

    public Drawer.Result build()
    {
        for (DrawerItem item : drawerItems) {
            drawer.addDrawerItems(item.iDrawerItem);
        }
        drawer.withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l, IDrawerItem iDrawerItem) {
                DrawerItem item = drawerItems.get(i);

                if (item.activity != null) {

                    Intent intent = new Intent(drawer.getActivity(), item.activity);
                    drawer.getActivity().startActivity(intent);

                } else if (item.fragment != null) {

                    ActionBarActivity activity = (ActionBarActivity) drawer.getActivity();
                    activity.getSupportActionBar().setTitle(item.fragment.getName());
                    FragmentTransaction tx = activity.getSupportFragmentManager().beginTransaction();
                    tx.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                    tx.replace(R.id.main, Fragment.instantiate(activity, item.fragment.getName()));
                    tx.commit();

                }
            }
        });
        return drawer.build();
    }

    public class DrawerItem
    {
        DrawerItemActionInterface itemAction;
        Class activity;
        Class fragment;
        IDrawerItem iDrawerItem;
    }

    private void addItemToList()
    {
        drawerItems.add(drawerItem);
        drawerItem = new DrawerItem();
    }

}