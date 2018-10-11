package android.estructurasii.lab2ed2;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.estructurasii.lab2ed2.ZigZag.algorithmZigZag;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import permissions.dispatcher.*;

@RuntimePermissions
public class cifradozzfragment extends Fragment {
    private static final int READ_REQUEST_CODE = 42;
    private static final int READ_SELECT_CODE = 43;
    String resolver =  "/storage/emulated/0/Compresiones/";
    algorithmZigZag ZigZag;
    private int Depht;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_cifradozz,container,
                false);
        final Button Wfile = view.findViewById(R.id.buttonczz);
        final Button Sruta = view.findViewById(R.id.buttonrzz);
        final EditText Niveles = view.findViewById(R.id.etNiveles);
        ZigZag = new algorithmZigZag();
        Wfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(!Niveles.getText().toString().isEmpty()&& Integer.parseInt(Niveles.getText().toString()) >= 2){
                    cifradozzfragmentPermissionsDispatcher.RequestFileWithPermissionCheck(cifradozzfragment.this);
                    Depht = Integer.parseInt(Niveles.getText().toString());
                    //NameToSave = NombreArchivo.getText().toString();
                    /*Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);*/
                    //intent.setType("*/*");
                    /*startActivityForResult(Intent.createChooser(intent,"Seleccione Archivo"), READ_REQUEST_CODE);*/
                } else{
                    Toast.makeText(getContext(), "Debe Ingresar Número de Niveles que sea mayor o igual a 2", Toast.LENGTH_LONG).show();

                }

            }
        });
        Sruta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(!Niveles.getText().toString().isEmpty() && Integer.parseInt(Niveles.getText().toString()) >= 2){
                    //NameToSave = NombreArchivo.getText().toString();
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_OPEN_DOCUMENT_TREE);

                    startActivityForResult(Intent.createChooser(intent,"Seleccione Ruta"),READ_SELECT_CODE);

                } else{
                    //cifradozzfragmentPermissionsDispatcher.RequestRouteWithPermissionCheck(cifradozzfragment.this);
                    Toast.makeText(getContext(), "Debe Ingresar Número de Niveles que sea mayor o igual a 2", Toast.LENGTH_LONG).show();
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

            //-------------------------------------------
            try {
                InputStream inputStream = getContext().getContentResolver().openInputStream(selectedFile);
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder str = new StringBuilder();
                ArrayList<String> stringsarray = new ArrayList<>();
                String linea;
                while((linea = br.readLine()) != null){
                    String sinSaltosDeLinea = linea.replaceAll("\n","ꡐ");
                    str.append(linea);
                    str.append("ꡐ");
                    stringsarray.add(linea);
                }
                br.close();
                inputStream.close();
                StringBuilder sb = new StringBuilder();
                for (String s : stringsarray)
                {
                    sb.append(s);
                    sb.append("ꡐ");
                }
                //Algorithm.fillDictionary(str.toString());
                ArrayList<String> resultArray = new ArrayList<>();
                String encriptado = ZigZag.Encryption(sb.toString(), Depht);
                resultArray.add(encriptado);
                //resultArray.add(Algorithm.TabladeDictionary());
                //for(int i = 0; i<stringsarray.size();i++){
                 //   String temp = Algorithm.compress(stringsarray.get(i));
                 //   temp = ChangeTroubleStrings(temp);
                 //   resultArray.add(temp);
                //}

                // se convierte el Uri a file para obtener el nombre del archivo
                File tempfile = new File(selectedFile.getPath());
                String name = tempfile.getName().replace('.','_'); // nombre de nuevo archivo igual al archivo elegido
                // archivo a escribir
                File newfile = new File(resolver,name+".cif");

                FileOutputStream outputStream = new FileOutputStream(newfile);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
                BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
                bufferedWriter.write(resultArray.get(0));
                bufferedWriter.newLine();
                for(int i = 1; i<resultArray.size(); i++){
                    bufferedWriter.write(resultArray.get(i).toCharArray());
                    bufferedWriter.write("ꡐ");
                }
                bufferedWriter.flush();
                bufferedWriter.close();
                Toast.makeText(getContext(),"Guardado en" +resolver+"/"+name+".cif", Toast.LENGTH_LONG).show();
                pathProvider provider = new pathProvider();
                //se guarda en bitácora
                //register.AddRegister(newfile.getPath(),provider.getPath(getContext(),selectedFile),"LZW");


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            //-------------------------------------------

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


        intent.setType("text/plain");

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
        cifradozzfragmentPermissionsDispatcher.onRequestPermissionsResult(this,requestCode,grantResults);

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
