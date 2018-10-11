package android.estructurasii.lab2ed2.ZigZag;

public class algorithmZigZag
{
   public String Encryption(String plainText,int depth)throws Exception
    {
        String plainTextNoSpaces = plainText.replace(" ","פֿ");
        int r=depth,len=plainTextNoSpaces.length();
        int c=len/depth;
        int k=0;

        int tOlas = (depth * 2)-2;
        int cantOlas = len/tOlas;
        int cantOlasMod = len%tOlas;
        int carRelleno = tOlas - cantOlasMod;
        if(carRelleno > 0)
        {
            cantOlas = cantOlas + 1;
        }
        if (carRelleno > 0)
        {
            for (int i=0;i< carRelleno;i++)
            {
                plainTextNoSpaces = plainTextNoSpaces+"φ";
            }
        }
        int newlen=plainTextNoSpaces.length();
        int carxOla = newlen/cantOlas;
        char mat[][]=new char[r][newlen];
        String cipherText="";

        for(int i=0;i<(newlen);i++)
        {
            if(k == newlen)
            {
                break;
            }
            //int tOlaAux = 0;
            for(int j=0;j< r;j++)
            {
                if(k!=newlen)
                {
                    if(k == newlen)
                    {
                        break;
                    }
                    mat[j][i] = plainTextNoSpaces.charAt(k++);
                    //tOlaAux++;
                }
            }
            i++;
            for (int l=(r-2);l>0;l--)
            {
                if(k == newlen)
                {
                    break;
                }
                mat[l][i] = plainTextNoSpaces.charAt(k++);
                //tOlaAux++;
            }
        }
        for(int i=0;i< r;i++)
        {
            for(int j=0;j< newlen;j++)
            {
                cipherText+=mat[i][j];
            }
        }
        String cifradoNoSpaces = cipherText.replace("\u0000","");
        cifradoNoSpaces = cifradoNoSpaces.replace("פֿ"," ");
        return cifradoNoSpaces;
    }

    public String Decryption(String Text,int depth)throws Exception
    {
        //int r=depth,len=Text.length();
        //int c=len/depth;
        //char mat[][]=new char[r][c];
        //int k=0;

        //-----------------------
        int r=depth,len=Text.length();
        int c=len/depth;
        int k=0;

        int tOlas = (depth * 2)-2;
        int cantOlas = len/tOlas;
        int cantOlasMod = len%tOlas;
        /*int carRelleno = tOlas - cantOlasMod;
        if(carRelleno > 0)
        {
            cantOlas = cantOlas + 1;
        }*/
        int newlen=Text.length();
        int carxOla = newlen/cantOlas;
        char mat[][]=new char[r][newlen];
        String cipherText="";
        //-----------------------


        String plainText="";
        /*for(int i=0;i< r;i++)
        {
            for(int j=0;j< c;j++)
            {
                mat[i][j]=Text.charAt(k++);
            }
        }*/

        // Llena Inicial
        for(int j=0;j<(cantOlas);j++)
        {
            if(k!=newlen)
            {
                if(k == newlen)
                {
                    break;
                }
                mat[0][j] = Text.charAt(k++);
                //tOlaAux++;
            }
        }
        //Llenna Final
        String textoFinal = Text.substring((Text.length()-cantOlas), Text.length());
        k=0;
        for(int j=0;j<(cantOlas);j++)
        {
            if(k!=newlen)
            {
                if(k == newlen)
                {
                    break;
                }
                mat[r-1][j] = textoFinal.charAt(k++);
                //tOlaAux++;
            }
        }

        String textoRestante = Text.substring(cantOlas, (Text.length()-cantOlas));
        k=0;
        for(int i=1;i<(r-1);i++)
        {
            /*if(k == newlen)
            {
                break;
            }*/
            //int tOlaAux = 0;
            for(int j=0;j<(cantOlas*2);j++)
            {
                if(k!=newlen)
                {
                    /*if(k == newlen)
                    {
                        break;
                    }*/
                    mat[i][j] = textoRestante.charAt(k++);
                    //tOlaAux++;
                }
            }
        }

        for(int i=0;i< c;i++)
        {
            for(int j=0;j< r;j++)
            {
                plainText+=mat[j][i];
            }
            i++;
            for(int j=(r-2);j>0;j--)
            {
                plainText+=mat[j][i];
                StringBuffer cadena = new StringBuffer();
                for (int y=0;y<mat[j].length;y++){
                    cadena =cadena.append(mat[j][y]);
                }
                String cadenaIni = cadena.toString();
                String cadenaFin = cadena.toString();
                cadenaIni = cadenaIni.substring(0, i);
                cadenaFin = cadenaFin.substring((i+1), cadenaFin.length());
                StringBuffer cadena2 = new StringBuffer();
                cadena2 = cadena2.append(cadenaIni);
                cadena2 = cadena2.append(cadenaFin);
                String Aux = cadena2.toString();
                int p = 0;
                for(int z=0;z<(cadena2.length());z++)
                {
                    mat[j][z] = Aux.charAt(p++);
                }
            }
            i--;
        }
        String plainTextReturn = plainText.replaceAll("φ","");
        plainTextReturn = plainTextReturn.replace('ꡐ','\n');
        return plainTextReturn;
    }
}

