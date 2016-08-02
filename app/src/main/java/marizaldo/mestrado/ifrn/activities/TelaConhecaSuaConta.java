package marizaldo.mestrado.ifrn.activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import marizaldo.mestrado.ifrn.R;
import marizaldo.mestrado.ifrn.helpers.ColorTool;

public class TelaConhecaSuaConta extends FragmentActivity implements View.OnTouchListener
{
    private RadioGroup rgTelaConhecaSuaConta;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_conheca_sua_conta);

        ImageView iv = (ImageView) findViewById (R.id.iv_tela_conheca_sua_conta_conta_de_luz);

        if (iv != null)
        {
            iv.setOnTouchListener (this);
        }
        toast("Toque nas regiões marcadas na conta");
    }

    public boolean onTouch (View v, MotionEvent ev)
    {
        boolean handledHere = false;

        final int action = ev.getAction();

        final int evX = (int) ev.getX();
        final int evY = (int) ev.getY();
        int nextImage = -1;

        ImageView imageView = (ImageView) v.findViewById (R.id.iv_tela_conheca_sua_conta_conta_de_luz);

        if (imageView == null)
        {
            return false;
        }

        Integer tagNum = (Integer) imageView.getTag();
        int currentResource = (tagNum == null) ? R.drawable.conta_de_luz : tagNum.intValue ();

        switch(action)
        {
            case MotionEvent.ACTION_DOWN :
                if (currentResource == R.drawable.conta_de_luz)
                {
                    nextImage = R.drawable.conta_de_luz;
                    handledHere = true;
                }
                else if (currentResource != R.drawable.conta_de_luz)
                {
                    nextImage = R.drawable.conta_de_luz;
                    handledHere = true;
                }
                else
                {
                    handledHere = true;
                }
                break;

            case MotionEvent.ACTION_UP :
                int touchColor = getHotspotColor (R.id.iv_tela_conheca_sua_conta_conta_de_luz_mascara, evX, evY);
                ColorTool ct = new ColorTool ();
                int tolerance = 20;

                nextImage = R.drawable.conta_de_luz;

                if(ct.closeMatch (Color.rgb(0, 0, 255), touchColor, tolerance))
                {
                    //toast("BLUE");
                    Intent intentTelaConhecaSuaConta = new Intent(getApplicationContext(), TelaConhecaSuaContaZoom.class);
                    intentTelaConhecaSuaConta.putExtra("codigoItem", 1);
                    startActivity(intentTelaConhecaSuaConta);
                }
                else if(ct.closeMatch (Color.rgb(255, 0, 255), touchColor, tolerance))
                {
                    //toast("FUCHSIA/MAGENTA");
                }
                else if(ct.closeMatch (Color.rgb(128, 0, 128), touchColor, tolerance))
                {
                    //toast("PURPLE");
                }
                else if(ct.closeMatch (Color.rgb(0, 191, 255), touchColor, tolerance))
                {
                    //toast("DEEPSKYBLUE");
                    Intent intentTelaConhecaSuaConta = new Intent(getApplicationContext(), TelaConhecaSuaContaZoom.class);
                    intentTelaConhecaSuaConta.putExtra("codigoItem", 6);
                    startActivity(intentTelaConhecaSuaConta);
                }
                else if(ct.closeMatch (Color.rgb(255, 0, 0), touchColor, tolerance))
                {
                    //toast("RED");
                    Intent intentTelaConhecaSuaConta = new Intent(getApplicationContext(), TelaConhecaSuaContaZoom.class);
                    intentTelaConhecaSuaConta.putExtra("codigoItem", 3);
                    startActivity(intentTelaConhecaSuaConta);
                }
                else if(ct.closeMatch (Color.rgb(255, 165, 0), touchColor, tolerance))
                {
                    //toast("ORANGE");
                    Intent intentTelaConhecaSuaConta = new Intent(getApplicationContext(), TelaConhecaSuaContaZoom.class);
                    intentTelaConhecaSuaConta.putExtra("codigoItem", 5);
                    startActivity(intentTelaConhecaSuaConta);
                }
                else if(ct.closeMatch (Color.rgb(0, 255, 255), touchColor, tolerance))
                {
                    //toast("AQUA/CYAN");
                }
                else if(ct.closeMatch (Color.rgb(0, 100, 0), touchColor, tolerance))
                {
                    //toast("DARKGREEN");
                }
                else if(ct.closeMatch (Color.rgb(0, 255, 0), touchColor, tolerance))
                {
                    //toast("LIME");
                    Intent intentTelaConhecaSuaConta = new Intent(getApplicationContext(), TelaConhecaSuaContaZoom.class);
                    intentTelaConhecaSuaConta.putExtra("codigoItem", 2);
                    startActivity(intentTelaConhecaSuaConta);
                }
                else if(ct.closeMatch (Color.rgb(255, 255, 0), touchColor, tolerance))
                {
                    //toast("YELLOW");
                    Intent intentTelaConhecaSuaConta = new Intent(getApplicationContext(), TelaConhecaSuaContaZoom.class);
                    intentTelaConhecaSuaConta.putExtra("codigoItem", 4);
                    startActivity(intentTelaConhecaSuaConta);
                }
                else if(ct.closeMatch (Color.rgb(0, 250, 154), touchColor, tolerance))
                {
                    //toast("MEDIUMSPRINGGREEN");
                }
                else if(ct.closeMatch (Color.rgb(210, 105, 30), touchColor, tolerance))
                {
                    //toast("CHOCOLATE");
                    Intent intentTelaConhecaSuaConta = new Intent(getApplicationContext(), TelaConhecaSuaContaZoom.class);
                    intentTelaConhecaSuaConta.putExtra("codigoItem", 7);
                    startActivity(intentTelaConhecaSuaConta);
                }
                else if(ct.closeMatch (Color.WHITE, touchColor, tolerance))
                {
                    nextImage = R.drawable.conta_de_luz;
                }

                if (currentResource == nextImage)
                {
                    nextImage = R.drawable.conta_de_luz;
                }
                handledHere = true;
                break;

            default:
                handledHere = false;
        }

        if (handledHere)
        {
            if (nextImage > 0)
            {
                imageView.setImageResource (nextImage);
                imageView.setTag (nextImage);
            }
        }
        return handledHere;
    }

    public int getHotspotColor (int hotspotId, int x, int y)
    {
        ImageView img = (ImageView) findViewById (hotspotId);
        img.setDrawingCacheEnabled(true);
        Bitmap hotspots = Bitmap.createBitmap(img.getDrawingCache());
        img.setDrawingCacheEnabled(false);
        return hotspots.getPixel(x, y);
    }

    public void toast (String msg)
    {
        Toast.makeText (getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
