package android.estructurasii.lab2ed2;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import permissions.dispatcher.*;

@RuntimePermissions
public class descifradozzfragment extends Fragment {
    private static final int READ_REQUEST_CODE = 42;
    private static final int READ_SELECT_CODE = 43;
    String resolver =  "/storage/emulated/0/Compresiones/";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_descifradozz,container,
                false);
        final Button sfile = view.findViewById(R.id.buttondzz);
        final Button sruta = view.findViewById(R.id.buttonrdzz);
        sfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                descifradozzfragmentPermissionsDispatcher.RequestFileWithPermissionCheck(descifradozzfragment.this);
            }
        });
        sruta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                descifradozzfragmentPermissionsDispatcher.RequestRouteWithPermissionCheck(descifradozzfragment.this);
            }
        });
        return view;
    }
    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void RequestFile(){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);


        intent.addCategory(Intent.CATEGORY_OPENABLE);


        intent.setType("*/*");

        startActivityForResult(Intent.createChooser(intent,"Seleccione Archivo"), READ_REQUEST_CODE);
    }
    @NeedsPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    void RequestRoute(){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT_TREE);

        startActivityForResult(Intent.createChooser(intent,"Seleccione Ruta"),READ_SELECT_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


    }
    @OnShowRationale(Manifest.permission.READ_EXTERNAL_STORAGE)
    void ShowRationaleForRead(final PermissionRequest request){
        new AlertDialog.Builder(getActivity())
                .setTitle("Permisos necesarios")
                .setMessage("Estos permisos son necesarios para utilizar la app")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.proceed();
                    }
                }).setNegativeButton("cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                request.cancel();
            }
        }).create().show();

    }
    @OnShowRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void ShowRationaleForWrite(final PermissionRequest request){
        new AlertDialog.Builder(getActivity())
                .setTitle("Permisos necesarios")
                .setMessage("Estos permisos son necesarios para utilizar la app")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.proceed();
                    }
                }).setNegativeButton("cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                request.cancel();
            }
        }).create().show();

    }
    @OnPermissionDenied(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void onWriteDenied()
    {
        Toast.makeText(getActivity(),"Permiso NO aceptado",Toast.LENGTH_SHORT).show();
    }
    @OnPermissionDenied(Manifest.permission.READ_EXTERNAL_STORAGE)
    void onReadDenied(){
        Toast.makeText(getActivity(),"Permiso NO aceptado",Toast.LENGTH_SHORT).show();
    }
}
