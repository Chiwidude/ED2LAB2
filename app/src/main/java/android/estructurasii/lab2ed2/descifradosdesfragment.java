package android.estructurasii.lab2ed2;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.estructurasii.lab2ed2.SDES.SDES;
import android.net.Uri;
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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import permissions.dispatcher.*;

@RuntimePermissions
public class descifradosdesfragment extends Fragment {
    private static final int READ_REQUEST_CODE = 42;
    private static final int READ_SELECT_CODE = 43;
    String resolver =  "z";
    String key = "";
    SDES algorithm;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_descifradosdes, container,
                false);
        final Button sfile = view.findViewById(R.id.buttondcsdes);
        final Button sruta = view.findViewById(R.id.buttonrdcsdes);
        final Button zeroc = view.findViewById(R.id.zerod);
        final Button onec = view.findViewById(R.id.oned);
        final TextView key_ = view.findViewById(R.id.claved);
        algorithm = new SDES();
        sruta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                descifradosdesfragmentPermissionsDispatcher.RequestRouteWithPermissionCheck(descifradosdesfragment.this);

            }
        });
        sfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(key.length() <10){
                    Toast.makeText(getContext(), "Su clave debe ser de 10 caracteres", Toast.LENGTH_LONG).show();
                } else if(!resolver.isEmpty()) {
                    descifradosdesfragmentPermissionsDispatcher.RequestFileWithPermissionCheck(descifradosdesfragment.this);

                } else {
                    Toast.makeText(getContext(), "Eliga una ruta para su archivo descifrado", Toast.LENGTH_LONG).show();
                }
            }
        });
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
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);
        if(requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK)
        {
            Uri selectedFile = resultData.getData();
            File temp = new File(selectedFile.getPath());
            if(temp.getName().contains(".sdes")) {
                try {
                    InputStream inputStream = getContext().getContentResolver().openInputStream(selectedFile);
                    InputStreamReader reader = new InputStreamReader(inputStream,"UTF-8");
                    File sfile = new File(selectedFile.getPath());
                    String nfile = "descifrado_" + sfile.getName().replaceAll(".sdes", ".txt");
                    File newfile = new File(resolver, nfile);
                    FileOutputStream outputStream = new FileOutputStream(newfile);
                    OutputStreamWriter writer = new OutputStreamWriter(outputStream, "UTF-8");
                    algorithm.GenerateKeys(key);
                    int c;

                    while((c = reader.read()) != -1){

                     char s = algorithm.descipher(c);
                      writer.write(s);
                    }
                    reader.close();
                    writer.flush();
                    writer.close();


                    Toast.makeText(getContext(), "Guardado en" + resolver + "/" + nfile, Toast.LENGTH_LONG).show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getContext(),"Esto no es un archivo cifrado con sdes", Toast.LENGTH_LONG).show();

            }
        }
        if(requestCode == READ_SELECT_CODE && resultCode == Activity.RESULT_OK) {
            Uri selected = resultData.getData();
            pathProvider provider = new pathProvider();
            //convierte el uri en una ruta de directorio
            resolver = provider.getFullPathFromTreeUri(selected,getActivity());
            Toast.makeText(getContext(),"Ruta Seleccionada: " +resolver, Toast.LENGTH_LONG).show();
        }
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
}
