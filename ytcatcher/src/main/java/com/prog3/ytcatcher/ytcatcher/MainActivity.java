package com.prog3.ytcatcher.ytcatcher;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import java.util.regex.Pattern;


public class MainActivity extends ActionBarActivity {

    private Context ctx;
    private ClipboardManager cbm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.error_layout);
        ctx = this.getApplicationContext();
        cbm = (ClipboardManager) getSystemService(ctx.CLIPBOARD_SERVICE);
    }

    protected void onStart() {
        super.onStart();
        ClipData clip = cbm.getPrimaryClip();
        ClipData.Item it = clip.getItemAt(0);
        String url = it.coerceToText(ctx).toString();
        //url="http://www.youtube.com/watch?v=Cg-wn1NuL5w";
        Pattern pt = Pattern.compile("(http://|https://)?(www\\.)?((m\\.|it\\.)?youtube\\.com|youtu\\.be)/watch\\?v=[\\w\\-]{11}");
        if (pt.matcher(url).matches()) {
            String id = url.split("v=")[1];
            setContentView(R.layout.choice_layout);
            YTAsyncGet t = new YTAsyncGet(getApplicationContext(), (TextView) findViewById(R.id.titleView), (ListView) findViewById(R.id.listView));
            t.execute("http://www.youtube.com/get_video_info?video_id=" + id);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}