package android.estructurasii.lab2ed2.ZigZag;

public class algorithmZigZag
{
   public String Encryption(String plainText,int depth)throws Exception
    {
        plainText.replace(" ","פֿ");
        int r=depth,len=plainText.length();
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
                plainText = plainText+"φ";
            }
        }
        int newlen=plainText.length();
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
                    mat[j][i] = plainText.charAt(k++);
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
                mat[l][i] = plainText.charAt(k++);
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
        cipherText.replace(" ","");
        return cipherText;
    }

    public String Decryption(String cipherText,int depth)throws Exception
    {
        int r=depth,len=cipherText.length();
        int c=len/depth;
        char mat[][]=new char[r][c];
        int k=0;

        String plainText="";
        for(int i=0;i< r;i++)
        {
            for(int j=0;j< c;j++)
            {
                mat[i][j]=cipherText.charAt(k++);
            }
        }
        for(int i=0;i< c;i++)
        {
            for(int j=0;j< r;j++)
            {
                plainText+=mat[j][i];
            }
        }

        return plainText;
    }
}

