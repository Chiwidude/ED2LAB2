package android.estructurasii.lab2ed2;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout myLayout;
    private final int STORAGE_REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        myLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,myLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        myLayout.addDrawerListener(toggle);
        toggle.syncState();
        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new cifradozzfragment()).commit();
            Toast.makeText(this,"Cifrado ZigZag",Toast.LENGTH_LONG).show();
            navigationView.setCheckedItem(R.id.nav_cifradozz); }


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.nav_cifradozz:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new cifradozzfragment()).commit();
                Toast.makeText(this,"Cifrado ZigZag",Toast.LENGTH_LONG).show();
                break;
            case R.id.nav_descifradozz:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new descifradozzfragment()).commit();
                Toast.makeText(this,"Descifrado ZigZag",Toast.LENGTH_LONG).show();
                break;
        }
        myLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(myLayout.isDrawerOpen(GravityCompat.START)){
            myLayout.closeDrawer(GravityCompat.START);

        }else {
            super.onBackPressed();
        }
    }



}
