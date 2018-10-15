package android.estructurasii.lab2ed2;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class cifradosdesfragment extends Fragment {
    private static final int READ_REQUEST_CODE = 42;
    private static final int READ_SELECT_CODE = 43;
    String resolver =  "/storage/emulated/0/Cifrados/";
    String key = "";

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_cifradosdes, container,
                false);
        final Button sfile = view.findViewById(R.id.buttoncsdes);
        final Button sruta = view.findViewById(R.id.buttonrcsdes);
        final Button zeroc = view.findViewById(R.id.zeroc);
        final Button onec = view.findViewById(R.id.onec);
        final TextView key_ = view.findViewById(R.id.clavec);
        zeroc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(key.length()<10){
                    key = key.concat("0");
                    String temp = "Clave:" + key;
                    key_.setText(temp);
                } else {
                    Toast.makeText(getContext(), "Ya ingresó su clave", Toast.LENGTH_LONG).show();
                }
            }
        });
        onec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(key.length()<10){
                    key = key.concat("1");
                    String temp = "Clave:" + key;
                    key_.setText(temp);
                } else {
                    Toast.makeText(getContext(), "Ya ingresó su clave", Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }
}
