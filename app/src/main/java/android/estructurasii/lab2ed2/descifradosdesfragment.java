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

public class descifradosdesfragment extends Fragment {
    private static final int READ_REQUEST_CODE = 42;
    private static final int READ_SELECT_CODE = 43;
    String resolver =  "/storage/emulated/0/Descifrados/";

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_cifradosdes, container,
                false);
        final Button sfile = view.findViewById(R.id.buttondcsdes);
        final Button sruta = view.findViewById(R.id.buttonrdcsdes);
        final Button zeroc = view.findViewById(R.id.zerod);
        final Button onec = view.findViewById(R.id.oned);
        final TextView key_ = view.findViewById(R.id.claved);
        return view;
    }
}
